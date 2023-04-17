import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Customer class that extends User, contains shoppingCart field and itemsPurchased field.
 */
public class Customer extends User {
    private ArrayList<Product> shoppingCart;
    private ArrayList<Product> itemsPurchased;

    public Customer(String userName, String email, String password, ArrayList<String> inbox,
                    ArrayList<Product> shoppingCart, ArrayList<Product> itemsPurchased) {
        super(userName, email, password, inbox);
        this.shoppingCart = shoppingCart;
        this.itemsPurchased = itemsPurchased;
    }

    public Customer(String userName, String email, String password) {
        super(userName, email, password);
        this.shoppingCart = new ArrayList<>();
        this.itemsPurchased = new ArrayList<>();
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<Product> getItemsPurchased() {
        return itemsPurchased;
    }

    public void setItemsPurchased(ArrayList<Product> itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }

    public void addToShoppingCart(Product product) {
        this.shoppingCart.add(product);
    }

    /**
     * Add # quantity products to cart
     *
     * @param product  the product to be added
     * @param quantity the quantity of the product
     */
    public void addToShoppingCart(Product product, int quantity) {
        this.shoppingCart.add(new Product(
                product.getName(), product.getSeller(),
                product.getStore(), product.getDescription(),
                product.getPrice(), quantity, super.getUserName()));
    }

    public void addToItemsPurchased(Product product) {
        this.itemsPurchased.add(product);
    }

    public void clearCart() {
        this.shoppingCart.clear();
    }

    // Data will include a list of stores by number of products sold and a list of stores by the products
    // purchased by that particular customer.
    public void viewDashboard(Market market, boolean isAscending) {
        //Customers can choose to sort the dashboard.
        ArrayList<Integer> quantities = new ArrayList<>();
        int[] sales = new int[market.getStores().size()];
        ArrayList<String> purchasedFrom = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();

        // Loop through all stores on the marketplace
        for (int i = 0; i < market.getStores().size(); i++) {
            // For each store, loop through its history and compute its net sales
            Store ref = market.getStores().get(i);
            int netSales = 0;
            for (int j = 0; j < ref.getHistory().size(); j++) {
                Product purchased = ref.getHistory().get(j);
                netSales += purchased.getQuantity();
            }
            sales[i] = netSales;
            quantities.add(netSales);

            if (ref.getCustomers().contains(this)) {
                purchasedFrom.add(ref.getStoreName());
            }
            stores.add(ref);
        }

        Arrays.sort(sales);
        System.out.println("Store Name\t\t\tItems purchased");
        if (isAscending) {
            for (int qtySold : sales) {
                int matchStore = quantities.indexOf(qtySold);
                System.out.printf("%s\t\t\t%d\n", stores.get(matchStore).getStoreName(), qtySold);
                stores.remove(stores.get(matchStore));
            }
        } else {
            for (int i = sales.length - 1; i >= 0; i--) {
                int qtySold = sales[i];
                int matchStore = quantities.indexOf(qtySold);
                System.out.printf("%s\t\t\t%d\n", stores.get(matchStore).getStoreName(), qtySold);
                stores.remove(stores.get(matchStore));
            }
        }
        System.out.println(purchasedFrom);
    }

    /**
     * Export purchase history into a CSV file at filePath
     *
     * @param filePath the path to the file to be created
     */
    public void exportPurchaseHistory(String filePath) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (Product product : itemsPurchased) {
                String[] entries = product.productDetails();
                lines.add(entries);
            }

            // Write to CSV
            writer.writeAll(lines);
        } catch (FileNotFoundException e) { // Invalid file path error
            System.out.println("Invalid file path!");
            e.printStackTrace();
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.printf("History is written into\n\t%s\n", filePath);
    }

    /**
     * removeFromShoppingCart method
     *
     * @param productName      is the product type to remove from shopping cart.
     * @param quantityToRemove is the quantity to remove.
     *                         If "quantity of product" in the cart is greater than the "quantity to be removed",
     *                         the "quantity to be removed" is subtracted from "quantity of product."
     *                         else the product is removed from cart.
     */
    public void removeFromShoppingCart(String productName, int quantityToRemove) {
        Product deleteItem = null;
        for (Product product : this.shoppingCart) {
            if (product.getName().equals(productName)) {
                if ((product.getQuantity() - quantityToRemove) > 0) {
                    product.setQuantity(product.getQuantity() - quantityToRemove);
                } else {
                    deleteItem = product;
                }
                break;
            }
        }
        if (deleteItem != null) {
            this.shoppingCart.remove(deleteItem);
        }
    }
}