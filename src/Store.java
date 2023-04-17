import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Store class that contains data about its products, its customers, and its history of purchased.
 */
public class Store {
    private static HashMap<String, Store> stores; // all the stores

    private String name; // name of the store
    private String seller; // the store's owner
    private ArrayList<Product> history; // the sale history
    private ArrayList<Product> currentProducts; // the current products listing
    private HashMap<String, Customer> customers; // the shop's customers

    /**
     * Initialize fields with only name seller
     *
     * @param name   the store name
     * @param seller the seller's username
     */
    public Store(String name, String seller) {
        this.name = name;
        this.seller = seller;
        currentProducts = new ArrayList<>();
        history = new ArrayList<>();
        customers = new HashMap<>();
    }

    /**
     * Initialize fields with array lists
     *
     * @param name            the store name
     * @param seller          the seller's username
     * @param history         the sale history
     * @param currentProducts the current product listing
     * @param customers       the shop customers
     */
    public Store(String name, String seller, ArrayList<Product> history, ArrayList<Product> currentProducts,
                 HashMap<String, Customer> customers) {
        this.name = name;
        this.seller = seller;
        this.currentProducts = currentProducts;
        this.history = history;
        this.customers = customers;
    }

    /**
     * Get all stores from the stores hash map
     * @return all store in the marketplace
     */
    public static HashMap<String, Store> getStores() {
        return stores;
    }

    /**
     * Get a store from stores
     * @param storeName the name of the store to get
     * @return the store specified by storeName
     */
    public static Store getAStore(String storeName) {
        return stores.get(storeName);
    }

    /**
     * Check if a store name existed
     * @param storeName the store name to be checked
     * @return true of store name existed; false otherwise
     */
    public static boolean isStoreNameExisted(String storeName) {
        return getAStore(storeName) != null;
    }

    /**
     * Add a new store to the global stores
     * @param newStore the name of the new store
     */
    public static void addGlobalStore(Store newStore) {
        stores.put(newStore.getStoreName(), newStore);
    }

    /**
     * Add a new customer to the store's list of existing customers
     *
     * @param customer the new customer to be added
     * @return true if the customer is successfully added (new customer)
     * or false if the customer is not added (customer already existed)
     */
    public boolean addCustomer(Customer customer) {
        // Check if customer already exist; exit and return false if customer existed
        Customer potentialCustomer = customers.get(customer.getEmail());
        if (potentialCustomer != null) {
            return false;
        }

        // Add new customer to list if customer is new
        customers.put(customer.getEmail(), customer);
        return true;
    }

    /**
     * Get the name of the current store
     *
     * @return the name of the current store
     */
    public String getStoreName() {
        return name;
    }

    /**
     * Set the name of the current store
     *
     * @param name name of store
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the current product listing
     *
     * @return current products in the store
     */
    public ArrayList<Product> getCurrentProducts() {
        return currentProducts;
    }

    /**
     * Set the current product listing
     *
     * @param currentProducts the new product listing
     */
    public void setCurrentProducts(ArrayList<Product> currentProducts) {
        this.currentProducts = currentProducts;
    }

    /**
     * Return the owner of this store
     *
     * @return the owner of this store
     */
    public String getSeller() {
        return seller;
    }

    /**
     * Set the owner of this store
     *
     * @param seller the owner of this store
     */
    public void setSeller(String seller) {
        this.seller = seller;
    }

    /**
     * Add a new product to product listing; the product existed, increase the quantity by the product's quanity
     *
     * @param newProduct the product to be added
     */
    public void addListing(Product newProduct) {
        // Check if product already exist; increase the existing product quantity if it existed
        for (Product product : currentProducts) {
            if (product.getName().equalsIgnoreCase(newProduct.getName())) {
                product.setQuantity(newProduct.getQuantity() + product.getQuantity());
                return;
            }
        }

        // Add new product to listing if the product is new
        currentProducts.add(newProduct);
        Product.addGlobalProduct(newProduct);
    }

    /**
     * Add a list of new products to product listing; the product existed,
     * increase the quantity by the product's quanity
     *
     * @param products the array list of products to be added
     */
    public void addListing(ArrayList<Product> products) {
        for (Product product : products) {
            addListing(product);
        }
    }

    /**
     * Remove quantity products from product listing
     *
     * @param removeProduct the product class product to be existed
     * @param quantity the quantity to be removed
     */
    public boolean removeListing(String removeProduct, int quantity) {
        // Search currentListing for product
        int newQuantity;
        Product product;
        for (int i = 0; i < currentProducts.size(); i++) {
            product = currentProducts.get(i);
            if (product.getName().equalsIgnoreCase(removeProduct)) {
                // Reduce the product's listing quantity to as much 0
                newQuantity = Math.max(product.getQuantity() - quantity, 0);

                // New quantity is 0; remove the product from the hash map
                if (newQuantity == 0) {
                    currentProducts.remove(product);
                }

                // New quantity is larger than 0; reduce the listing quantity to the new value
                else {
                    product.setQuantity(newQuantity);
                }

                return true;
            }
        }

        // No removeProduct does not exist
        return false;
    }

    /**
     * Remove quantity products from product listing
     *
     * @param removeProduct the product class product to be existed
     * @param quantity the quantity to be removed
     */
    public boolean removeListing(Product removeProduct, int quantity) {
        // Search currentListing for product
        int newQuantity;
        Product product;
        for (int i = 0; i < currentProducts.size(); i++) {
            product = currentProducts.get(i);
            if (product.getName().equalsIgnoreCase(removeProduct.getName())) {
                // Reduce the product's listing quantity to as much 0
                newQuantity = Math.max(product.getQuantity() - quantity, 0);

                // New quantity is 0; remove the product from the hash map
                if (newQuantity == 0) {
                    currentProducts.remove(product);
                }

                // New quantity is larger than 0; reduce the listing quantity to the new value
                else {
                    product.setQuantity(newQuantity);
                }

                return true;
            }
        }

        // No removeProduct does not exist
        return false;
    }

    /**
     * Return the sales history of this store
     *
     * @return an array list product of history
     */
    public ArrayList<Product> getHistory() {
        return history;
    }

    /**
     * Set the {@link #history} to the passed in history
     *
     * @param history history to be saved
     */
    public void setHistory(ArrayList<Product> history) {
        this.history = history;
    }

    /**
     * Add new purchased item to history
     *
     * @param item the newly purchased item to be added to the list
     */
    public void addHistory(Product item) {
        history.add(item);
    }

    /**
     * Print the store history of purchases to the screen
     */
    public void viewHistory() {
        System.out.println("Store's purchases history");
        System.out.println("Product Name\t\t|\t\tPrice\t\t|\t\tBought By");

        for (Product product : history) {
            System.out.printf("%s\t\t|\t\t%.2f\t\t|\t\t%s",
                    product.getName(), product.getPrice(), product.getCustomerName());
        }
    }

    /**
     * Return the customers of this store
     *
     * @return an array list customer of this store
     */
    public HashMap<String, Customer> getCustomers() {
        return customers;
    }

    /**
     * Set {@link  #customers} to passed in customers
     *
     * @param customers an array list of customer
     */
    public void setCustomers(HashMap<String, Customer> customers) {
        this.customers = customers;
    }

    /**
     * Print the store's customers to the screen
     */
    public void viewCustomers() {
        System.out.printf("%s's Customers:\n", name);
        int i = 1;
        for (Customer customer : customers.values()) {
            System.out.println(i + ". " + customer.getUserName());
            i++;
        }
    }

    /**
     * Search for all products that are purchased by the specified customer
     *
     * @param customerName the customer name to search for their purchase products
     * @return an array list of all product purchased by the specified customer
     */
    public ArrayList<Product> searchProducts(String customerName) {
        ArrayList<Product> results = new ArrayList<>(); // array list of products results

        // Loop through history and append results with product that has the same customer's username
        for (Product product : history) {
            if (product.getCustomerName().equalsIgnoreCase(customerName)) {
                results.add(product);
            }
        }

        return results;
    }

    /**
     * Search for all products that are purchased by the specified customer
     *
     * @param customer the customer to search for their purchase products
     * @return an array list of all product purchased by the specified customer
     */
    public ArrayList<Product> searchProducts(Customer customer) {
        ArrayList<Product> results = new ArrayList<>(); // array list of products results

        // Loop through history and append results with product that has the same customer's username
        for (Product product : history) {
            if (product.getCustomerName().equalsIgnoreCase(customer.getUserName())) {
                results.add(product);
            }
        }

        return results;
    }

    /**
     * Print out the store sales
     * Sale will have 2 parts, customer list, and total revenue.
     * Customer list will be display by: Customer name: total purchases/price
     * Total revenue will display the total revenue of al purchases
     */
    public void showSale() {
        System.out.printf("-----%s Sale-----\n", name);

        // Customer list
        System.out.println("Customer\t\tTotal items purchased\t\tRevenue from customer");
        ArrayList<Product> currentHistory;
        double totalPaid = 0;
        for (Customer customer : customers.values()) {
            double currPaid = 0;
            // Get customer total paid
            currentHistory = searchProducts(customer);
            for (Product item : currentHistory) {
                currPaid += item.getPrice();
            }

            // Print current customer stat and update total paid
            System.out.printf("%s\t\t%d\t%.2f\n", customer.getUserName(), currentHistory.size(), currPaid);
            totalPaid += currPaid;
        }

        // Total revenue
        System.out.println("-------------------");
        System.out.printf("Total revenue: %.2f\n", totalPaid);
    }

    /**
     * Print out the statistic for the current store.
     * Statistic will include 2 parts: customer list, and product list.
     * Customer list will have customer and a list of their purchased products.
     * Product list will have a list of sold items and the sales quanity.
     */
    public void showStatistic() {
        System.out.printf("-----Statistic for %s-----\n", name);

        // Customer list
        System.out.println("Customers List:");
        ArrayList<Product> currentHistory;
        for (Customer customer : customers.values()) {
            // Customer email
            System.out.printf("\t%s :\n", customer.getEmail());

            // List of products purchased by this customer
            currentHistory = searchProducts(customer);
            for (Product item : currentHistory) {
                System.out.printf("\t\t%s\t\t%d\n", item.getName(), item.getQuantity());
            }
        }

        // Product list
        System.out.println("-------------------");
        viewHistory();
    }

    /**
     * Import {@link #currentProducts} from the specified filePath
     *
     * @param filePath the filePath to the CSV file for importing product
     * @return an array list of products imported from the CSV file; will be empty if filePath is invalid
     */
    public ArrayList<Product> importCSV(String filePath) {
        ArrayList<Product> products = new ArrayList<>();

        List<String[]> strProduct;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).build()) {
            strProduct = reader.readAll(); // read all rows from CSV files
            for (String[] row : strProduct) { // loop through each row and create a new product per row
                Product curr = new Product(
                        row[0], // store name
                        row[1], //seller's name
                        row[2], //product's store
                        row[3], // description
                        Double.parseDouble(row[4]), // price
                        Integer.parseInt(row[5]) // quanity
                );
                products.add(curr);
                Product.addGlobalProduct(curr);
            }
        } catch (FileNotFoundException e) { // Invalid file path error
            System.out.println("Invalid file path!");
            return products;
        } catch (Exception e) { // All other errors
            System.out.printf("Failed to import products due to: %s\n", e.getMessage());
            return products;
        }

        System.out.printf("Products are imported from\n\t%s\n", filePath);
        return products;
    }

    /**
     * Export {@link #currentProducts} to the specified filePath
     *
     * @param filePath the filePath to the CSV file for exporting product
     */
    public void exportCSV(String filePath) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (Product product : currentProducts) {
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

        System.out.printf("Products are written into\n\t%s\n", filePath);
    }



    /**
     * Write store objects from {@link #stores} into a csv file.
     * Create 2 type of file:
     * Stores.txt:
     * storeName1,storeName1's seller
     * storeName2,storeName2's seller
     * ...
     * <p>
     * ExampleStore.csv:
     * ExampleStore's name,ExampleStore's seller
     * <history>
     * p1's name,p1's seller,p1's store,p1's description,p1's quantity purchased, p1's customer
     * </history>
     * <listing>
     * p1's name,p1's seller,p1's store,p1's description,p1's quantity listing, p1's customer (will be <blank>)
     * </listing>
     * <customers>
     * customer1's email
     * customer2's email
     * ...
     * </customers>
     */
    public static void writeStoreInfo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("Stores.txt"))) {
            for (Store s : stores.values()) {
                // Write a file containing all the unique store names
                String line = String.format("%s,%s", s.getStoreName(), s.getSeller());
                pw.println(line);

                // For each store, write a CSV file for that store
                try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(s.getStoreName() +
                        ".csv"))
                        .withSeparator(',')
                        .build()) {
                    ArrayList<String[]> lines = new ArrayList<>();

                    String[] firstLine = new String[2];
                    firstLine[0] = s.getStoreName();
                    firstLine[1] = s.getSeller();
                    lines.add(firstLine);

                    lines.add(new String[]{"<history>"});
                    for (Product p : s.getHistory()) {
                        String[] productFields = p.productDetails();
                        lines.add(productFields);
                    }
                    lines.add(new String[]{"</history>"});

                    lines.add(new String[]{"<listing>"});
                    for (Product p : s.getCurrentProducts()) {
                        String[] productFields = p.productDetails();
                        lines.add(productFields);
                    }
                    lines.add(new String[]{"</listing>"});

                    lines.add(new String[]{"<customers>"});
                    for (Customer c : s.getCustomers().values()) {
                        String[] customerNames = {c.getUserName()};
                        lines.add(customerNames);
                    }
                    lines.add(new String[]{"</customers>"});

                    // write to csv file.
                    writer.writeAll(lines);
                }
            }
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Read the Stores.txt from local storage, then read all the store that is listed in Stores.txt to initiate the
     * {@link #stores} field
     */
    public static void readStoreInfo() {
        stores = new HashMap<>();

        // Initiate all stores from Stores.txt
        try (BufferedReader bfr = new BufferedReader(new FileReader("Stores.txt"))) {
            String line = bfr.readLine();
            String name; // current store's name
            String seller; // current store's seller

            // Get each line's name and seller and create a new corresponding Store object
            while (line != null) {
                name = line.split(",")[0];
                seller = line.split(",")[1];
                stores.put(name, new Store(name, seller));
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // first time running
            System.out.println("Running readStoreInfo for the first time"); //TODO: delete when finish debugging
            return;
        } catch (IOException e) {
            System.out.println("Failed to load customer files!");
            e.printStackTrace();
        }

        // Loop through each store to find its correspond file; initiate its HashMap with the new info
        for (Store store : stores.values()) {
            try (CSVReader reader = new CSVReaderBuilder(new FileReader(store.getStoreName() + ".csv"))
                    .build()) {
                // Read name/seller
                String[] row = reader.readNext();
                String name = row[0]; // current store's name
                String seller = row[1]; // current store's seller
                if (!name.equals(store.getStoreName()) || !seller.equals(store.getSeller())) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    System.out.println("Store name and/or seller name does not matched!");
                    System.out.printf("Seller: %s; Store Name: {%s}\n", seller, name);
                    continue;
                }

                // Add store to seller
                Seller currUser = (Seller) User.userGetterByName(seller);
                if (currUser == null) {
                    System.out.printf("Seller {%s} is not found for store {%s}\n", seller, name);
                    continue;
                }
                currUser.addStore(store);

                /// Read sale history
                store.setHistory(new ArrayList<>()); // initialize store's history

                // Make sure file is ok
                row = reader.readNext();
                if (!row[0].equals("<history>")) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    System.out.println("No <history> found");
                    continue;
                }

                // Reading
                row = reader.readNext();
                while (row != null && !row[0].equals("</history>")) {
                    Product curr = new Product(
                            row[0], // store name
                            row[1], //seller's name
                            row[2], //product's store
                            row[3], // description
                            Double.parseDouble(row[4]), // price
                            Integer.parseInt(row[5]), // quanity
                            row[6] // customer
                    );
                    store.addHistory(curr);
                    row = reader.readNext();
                }

                /// Read listing
                store.setCurrentProducts(new ArrayList<>());

                // Make sure file is ok
                row = reader.readNext();
                if (row == null) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    System.out.println("Reach end of file");
                    continue;
                } else if (!row[0].equals("<listing>")) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    System.out.printf("Expecting <listing> but get %s\n", row[0]);
                    continue;
                }

                // Start reading
                else {
                    row = reader.readNext();
                    while (row != null && !row[0].equals("</listing>")) {
                        Product curr = new Product(
                                row[0], // store name
                                row[1], //seller's name
                                row[2], //product's store
                                row[3], // description
                                Double.parseDouble(row[4]), // price
                                Integer.parseInt(row[5]) // quanity
                        );
                        store.addListing(curr);
                        row = reader.readNext();
                    }
                }

                /// Read customers
                store.setCustomers(new HashMap<>());

                // Make sure file is ok
                if (row == null) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    System.out.println("Reach end of file");
                } else if (!row[0].equals("<customer>")) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    System.out.printf("Expecting <customer> but get %s\n", row[0]);
                    continue;
                }

                // Reading
                else {
                    row = reader.readNext();
                    while (row != null && !row[0].equals("</customer>")) {
                        User temp = User.userGetterByName(row[0]);
                        if (temp instanceof Customer) {
                            store.addCustomer((Customer) temp);
                        } else {
                            System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                            continue;
                        }
                        row = reader.readNext();
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to load customer files!");
                e.printStackTrace();
                return;
            } catch (CsvValidationException e) {
                System.out.println("CSV error occur!");
                e.printStackTrace();
                return;
            }
        }
    }
}
