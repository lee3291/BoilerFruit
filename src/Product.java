import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private static ArrayList<Product> products = new ArrayList<>(); // all products in the Marketplace
    private final String name;
    private final String seller; //name of the seller selling this product
    private final String store;
    private final String description;
    private final double price;
    private int quantity; // available products in store OR item purchased
    private final String customerName;

    public Product(String name, String seller, String store, String description, double price, int quantity) {
        this.name = name;
        this.seller = seller;
        this.store = store;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.customerName = "<blank>"; // <blank> instead of empty string for better tracking.
    }

    // Overloaded constructor for a purchased product with customerName field existing.
    public Product(String name, String seller, String store, String description, double price,
                   int quantity, String customerName) {
        this.name = name;
        this.seller = seller;
        this.store = store;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.customerName = customerName;
    }

    /**
     * Get the global products list
     * @return all products currently in marketplace
     */
    public static ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Add a new product to the marketplace's products array list
     * @param newProduct new product to be added
     */
    public static void addGlobalProduct(Product newProduct) {
        products.add(newProduct);
    }

    /**
     * Remove the specified product from the global products array list
     * @param removeProduct the name of the product to be removed
     * @param quantity the quantity to be removed
     */
    public static void removeGlobalProduct(String removeProduct, String storeName, int quantity) {
        // Search currentListing for product
        int newQuantity;
        Product product;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);
            if (product.getName().equalsIgnoreCase(removeProduct) && product.getStore().equals(storeName)) {
                // Reduce the product's listing quantity to as much 0
                newQuantity = Math.max(product.getQuantity() - quantity, 0);

                // New quantity is 0; remove the product from the hash map
                if (newQuantity == 0) {
                    products.remove(product);
                }

                // New quantity is larger than 0; reduce the listing quantity to the new value
                else {
                    product.setQuantity(newQuantity);
                }

                return;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getStore() {
        return store;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String displayProduct() {
        return String.format("Product name: %s\n\tPrice: $%.2f\n\tQty: %d left\n", name, price, quantity);
    }

    public String page() { //for when the user views the product
        return String.format("%s\n\t%s\n\tPr: $%.2f\n\tQty: %d\n", name, description, price, quantity);
    }

    public String getSeller() {
        return seller;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sort the input products by ascending or descending order by price
     *
     * @param products    the product ArrayList to be sorted
     * @param isAscending whether to sort in ascending or descending
     * @return the sorted ArrayList
     */
    public static ArrayList<Product> sortByPrice(ArrayList<Product> products, boolean isAscending) {
        ArrayList<Product> sorted = (ArrayList<Product>) products.clone();

        // Insertion sort ascending
        if (isAscending) {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getPrice() > curr.getPrice()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        } else {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getPrice() < curr.getPrice()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        }

        return sorted;
    }

    /**
     * Sort the input products by ascending or descending order by quantity
     *
     * @param products    the product ArrayList to be sorted
     * @param isAscending whether to sort in ascending or descending
     * @return the sorted ArrayList
     */
    public static ArrayList<Product> sortByQuantity(ArrayList<Product> products, boolean isAscending) {
        ArrayList<Product> sorted = (ArrayList<Product>) products.clone();

        // Insertion sort ascending
        if (isAscending) {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getQuantity() > curr.getQuantity()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        } else {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getQuantity() < curr.getQuantity()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        }

        return sorted;
    }

    /**
     * Search and return a product that matched the product name and store name
     * @param productName the product name want ot search
     * @param storeName the product's store
     * @return the matched product object; null if none is found
     */
    public static Product productGetter(String productName, String storeName) {
        for (Product product : products) {
            if (product.getName().equals(productName) && product.getStore().equals(storeName)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Return the current product's detail in terms of String[] that has all the product's field
     * @return the String[] with the product's fields
     */
    public String[] productDetails() {
        String[] product = new String[7];
        product[0] = name;
        product[1] = seller;
        product[2] = store;
        product[3] = description;
        product[4] = String.valueOf(price);
        product[5] = String.valueOf(quantity);
        product[6] = customerName;
        return product;
    }

    /**
     * Write {@link #products} to the AllProducts.csv
     */
    public static void writeProductInfo() {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter("AllProducts.csv"))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (Product product : products) {
                String[] entries = product.productDetails();
                lines.add(entries);
            }

            // Write to CSV
            writer.writeAll(lines);
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Product data is written!");
    }

    /**
     * Read {@link #products} from the AllProducts.csv
     */
    public static void readProductInfo() {
        products = new ArrayList<>();

        // Getting all the products from CSV
        List<String[]> strProduct;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader("AllProducts.csv")).build()) {
            strProduct = reader.readAll(); // read all rows from CSV files
            for (String[] row : strProduct) { // loop through each row and create a new product per row
                Product curr = new Product(
                        row[0], // store name
                        row[1], //seller's name
                        row[2], //product's store
                        row[3], // description
                        Double.parseDouble(row[4]), // price
                        Integer.parseInt(row[5]), // quanity
                        row[6] // customer info
                );
                products.add(curr);
            }
        } catch (FileNotFoundException e) { // First time running
            System.out.println("First time running readProductInfo"); // TODO: delete after finish debugging
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Product data is loaded!"); // TODO: delete after finish debugging
    }
}