import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A Store class that contains data about its products, its customers, and its history of purchased.
 */
public class Store {
    private String name; // name of the store
    private String seller; // the store's owner
    private ArrayList<Product> history; // the sale history
    private ArrayList<Product> currentProducts; // the current products listing
    private ArrayList<Customer> customers; // the shop customers

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
        customers = new ArrayList<>();
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
                 ArrayList<Customer> customers) {
        this.name = name;
        this.seller = seller;
        this.currentProducts = currentProducts;
        this.history = history;
        this.customers = customers;
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
        for (Customer curr : customers) {
            if (curr.getEmail().equalsIgnoreCase(customer.getEmail())) {
                return false;
            }
        }

        // Add new customer to list if customer is new
        customers.add(customer);
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
     * @param product the product to be added
     */
    public void addListing(Product product) {
        // Check if product already exist; exit and return false if product existed
        for (Product curr : currentProducts) {
            if (curr.getName().equalsIgnoreCase(product.getName())) {
                curr.setQuantity(curr.getQuantity() + product.getQuantity());
                return;
            }
        }

        // Add new product to listing if the product is new
        currentProducts.add(product);
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
     * Remove an existed product from product listing
     *
     * @param product  the product class product to be existed
     * @param quantity the quantity to be removed
     */
    public void removeListing(Product product, int quantity) {
        Product deleteItem = null;
        // Find the product in the listing; remove the product and return true
        for (Product curr : currentProducts) {
            if (curr.getName().equalsIgnoreCase(product.getName())) {
                if (curr.getQuantity() != 0) {
                    curr.setQuantity(curr.getQuantity() - quantity);

                    // Quantity reach 0 after purchase
                    if (curr.getQuantity() == 0) {
                        deleteItem = curr;
                    }
                } else {
                    deleteItem = curr;
                }
                break;
            }
        }

        // Product is not listed; exit and return false
        if (deleteItem != null) {
            currentProducts.remove(deleteItem);
        }
    }

    /**
     * Remove an existed product from product listing (regardless of quantity)
     *
     * @param productName the string product to be existed
     * @return true if the product is successfully removed (existed product)
     * or false if the product is not removed (new product)
     */
    public boolean removeListing(String productName) {
        Product deleteItem = null;
        // Find the product in the listing; remove the product and return true
        for (Product curr : currentProducts) {
            if (curr.getName().equalsIgnoreCase(productName)) {
                deleteItem = curr;
            }
        }

        // Product is not listed; exit and return false
        if (deleteItem != null) {
            currentProducts.remove(deleteItem);
            return true;
        } else {
            return false;
        }
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
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Set {@link  #customers} to passed in customers
     *
     * @param customers an array list of customer
     */
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Print the store's customers to the screen
     */
    public void viewCustomers() {
        System.out.printf("%s's Customers:\n", name);
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getUserName());
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
        for (Customer customer : customers) {
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
        for (Customer customer : customers) {
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
    public ArrayList<Product> importCSV(String filePath, Market market) {
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
                market.addProduct(curr);
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
}
