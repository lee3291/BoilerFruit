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
    private final ArrayList<Product> shoppingCart;
    private final ArrayList<Product> itemsPurchased;

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

    public ArrayList<Product> getItemsPurchased() {
        return itemsPurchased;
    }

    public void addToShoppingCart(Product product) {
        this.shoppingCart.add(product);
    }

    /**
     * Add # quantity products to cart
     *
     * @param newProduct  the product to be added
     * @param quantity the quantity of the product
     */
    public void addToShoppingCart(Product newProduct, int quantity) {
        // If product is in shopping cart, increase the quantity
        for (Product product : shoppingCart) {
            if (product.getName().equals(newProduct.getName()) && product.getStore().equals(newProduct.getStore())) {
                product.setQuantity(product.getQuantity() + quantity);
            }
            return;
        }

        // Add new instance if product is not in shopping cart
        this.shoppingCart.add(new Product(
                newProduct.getName(), newProduct.getSeller(),
                newProduct.getStore(), newProduct.getDescription(),
                newProduct.getPrice(), quantity, super.getEmail()));
    }
    public void addToItemsPurchased(Product newProduct) {
        this.itemsPurchased.add(newProduct);
    }

    public void clearCart() {
        this.shoppingCart.clear();
    }

    /**
     * buyItem method.
     * <p>
     * It will move a product in the shoppingCart into the itemsPurchased.
     * quantity: quantity purchased
     * decrement from store of the product.
     */
    public Product buyItem(Product product, int quantity) {
        // Get store and remove product from store shelf
        Store store = Store.getAStore(product.getStore()); // <- store object
        if (store.removeListing(product, quantity)) {
            System.out.printf("Successfully bought %d of %s\n", quantity, product.getName());
            removeFromShoppingCart(product.getName(), quantity);
        }

        // Update store and customer history
        Product productPurchased = new Product(product.getName(), product.getSeller(), product.getStore(),
                product.getDescription(), product.getPrice(), quantity, this.getEmail());

        store.addHistory(productPurchased);
        store.addCustomer(this);
        this.addToItemsPurchased(productPurchased);

        return productPurchased;
    }

    /**
     * buyAllItem method.
     * <p>
     * It will move all the products in the shoppingCart into the itemsPurchased.
     * only allowed to buy at shopping cart page.
     * decrement from store of the product.
     */
    public void buyAllItem() {
        for (Product p : this.getShoppingCart()) {
            Store store = Store.getAStore(p.getStore()); // <- store object
            store.removeListing(p, p.getQuantity());
            store.addHistory(p);
            store.addCustomer(this);
            this.addToItemsPurchased(p);
        }

        this.clearCart(); // empties customer shopping cart.
        System.out.println("All items are bought. Cart is now empty!");
        System.out.println("Going back...");
    }

    // Data will include a list of stores by number of products sold and a list of stores by the products
    // purchased by that particular customer.
    public void viewDashboard(boolean isAscending) {
        //Customers can choose to sort the dashboard.
        ArrayList<Integer> quantities = new ArrayList<>();
        int[] sales = new int[Store.getStores().size()];
        ArrayList<String> purchasedFrom = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();

        // Loop through all stores on the marketplace
        int index = 0;
        for (String storeName : Store.getStores().keySet()) {
            // For each store, loop through its history and compute its net sales
            Store ref = Store.getStores().get(storeName);
            int netSales = 0;
            for (int j = 0; j < ref.getHistory().size(); j++) {
                Product purchased = ref.getHistory().get(j);
                netSales += purchased.getQuantity();
            }
            sales[index] = netSales;
            quantities.add(netSales);

            if (ref.getCustomers().containsValue(this)) {
                purchasedFrom.add(ref.getStoreName());
            }
            stores.add(ref);

            index++;
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
    public boolean exportPurchaseHistory(String filePath) {
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
            return false;
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
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