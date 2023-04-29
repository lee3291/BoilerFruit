import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileIO {
    private final String dataFolder = "./data/"; // the path to the data directory
    private final String usersFile = "allUsers.ser"; // the file name of the file contain all users

    /**
     * Write all user objects in users into a file (path: ./data/users.ser)
     *
     * @param users a hashmap of all users
     * @return true if success, false otherwise
     */
    public boolean writeUsers(HashMap<String, User> users) {
        new File(dataFolder).mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFolder + usersFile))) {
            // Write object to file
            for (User user : users.values()) {
                if (user != null) { // make sure there is no null object (will break the readUsers)
                    oos.writeObject(user);
                }
            }
            oos.writeObject(null); // used to terminate the readUsers
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Read all user object from data file (path: ./data/users.ser)
     *
     * @return the HashMap<String, User> if success, null otherwise
     */
    public HashMap<String, User> readUsers() {
        HashMap<String, User> users = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFolder + usersFile))) {
            User curr = (User) ois.readObject();
            while (curr != null) {
                // User is a seller
                if (curr instanceof Seller newSeller) {
                    users.put(curr.getUserName(), newSeller);
                } else if (curr instanceof Customer newCustomer) { // User is a customer
                    users.put(curr.getUserName(), newCustomer);
                }

                curr = (User) ois.readObject();
            }
        } catch (FileNotFoundException ef) {
            System.out.println("First time running readUsers!"); // First time running the program, no user
            return users;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return users;
    }

    /**
     * Export purchase history into a CSV file at filePath
     *
     * @param filePath        the path to the file to be created
     * @param purchaseHistory the purchase history
     */
    public boolean exportPurchaseHistory(String filePath, ArrayList<Product> purchaseHistory) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (Product product : purchaseHistory) {
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
     * Import a list of products from the specified filePath.
     *
     * @param filePath the filePath to the CSV file for importing product
     * @return an array list of products imported from the CSV file; will be null if unsuccessful
     */
    public ArrayList<Product> importCSV(String filePath) {
        ArrayList<Product> products = new ArrayList<>();

        List<String[]> strProduct;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).build()) {
            strProduct = reader.readAll(); // read all rows from CSV files
            for (String[] row : strProduct) { // loop through each row and create a new product per row
                Product curr = new Product(
                        row[0], // product name
                        row[1], // store's name
                        row[2], // description
                        Double.parseDouble(row[4]), // price
                        Integer.parseInt(row[5]) // quanity
                );
                products.add(curr);
            }
        } catch (FileNotFoundException e) { // Invalid file path error
            System.out.println("Invalid file path!");
            return null;
        } catch (Exception e) { // All other errors
            System.out.printf("Failed to import products due to: %s\n", e.getMessage());
            return null;
        }

        System.out.printf("Products are imported from\n\t%s\n", filePath);
        return products;
    }

    /**
     * Export the currentProducts to the specified filePath
     *
     * @param filePath        the filePath to the CSV file for exporting product
     * @param currentProducts the current product
     * @return true if success; false otherwise
     */
    public boolean exportCSV(String filePath, ArrayList<Product> currentProducts) {
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
            return false;
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        System.out.printf("Products are exported to \n\t%s\n", filePath);
        return true;
    }

    // TESTING
//    public static void printUser(User user) {
//        System.out.println("--------------------");
//        if (user instanceof Seller seller) {
//            System.out.printf("Seller %s\n", seller.getUserName());
//
//            System.out.println("Stores: ");
//            for (Store store : seller.getStores().values()) {
//                System.out.printf("%s; %.2f: \n", store.getStoreName(), store.getTotalRevenue());
//                for (Product product : store.getCurrentProducts()) {
//                    System.out.println(product.productInfo());
//                }
//            }
//        } else if (user instanceof Customer customer) {
//            System.out.printf("Customer %s\n", customer.getUserName());
//
//            System.out.println("Purchase history: ");
//            for (String his : customer.getPurchaseHistory()) {
//                System.out.println(his);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        HashMap<String, User> users = new HashMap<>();
//
//        // Example products
//        Product product1 = new Product("Apple", "Store", "Some Apples", 2.00,
//                2);
//        Product product2 = new Product("Banana", "Store", "Some Bananas", 3.00,
//                3);
//        Product product3 = new Product("Oranges", "Store", "Some Oranges", 4.00,
//                12);
//        ArrayList<Product> exampleProducts = new ArrayList<>();
//        exampleProducts.add(product1);
//        exampleProducts.add(product2);
//        exampleProducts.add(product3);
//
//        // Example Sale history
//        String saleHistory1 = "Apple, 1, 2.00, Bao2803";
//        String saleHistory2 = "Oranges, 3, 3.00, Bao2803";
//        ArrayList<String> exampleSaleHistory = new ArrayList<>();
//        exampleSaleHistory.add(saleHistory1);
//        exampleSaleHistory.add(saleHistory2);
//
//        String customerEmail1 = "bp@purdue.edu";
//        ArrayList<String> customerEmails = new ArrayList<>();
//        customerEmails.add(customerEmail1);
//
//        // Example stores
//        Store store1 = new Store("Amazon", "Bezos", 11, exampleProducts,
//                exampleSaleHistory, customerEmails);
//        Store store2 = new Store("Tiki", "Bezos");
//        Store store3 = new Store("Lazada", "Bezos");
//        HashMap<String, Store> exampleStores = new HashMap<>();
//        exampleStores.put(store1.getStoreName(), store1);
//        exampleStores.put(store2.getStoreName(), store2);
//        exampleStores.put(store3.getStoreName(), store3);
//
//        // Example history
//        String history1 = "Apple, 1, 2.00, Amazon";
//        String history2 = "Oranges, 3, 3.00, Amazon";
//        ArrayList<String> exampleHistory = new ArrayList<>();
//        exampleHistory.add(history1);
//        exampleHistory.add(history2);
//
//        // Seller
//        Seller seller = new Seller("Bezos", "b@amazon.com", "password", exampleStores);
//        users.put(seller.getEmail(), seller);
//
//        Seller scamSeller = new Seller("Bezos2", "b2@amazon.com", "password2", exampleStores);
//        users.put(scamSeller.getEmail(), scamSeller);
//
//        // Customer
//        Customer customer = new Customer("Bao2803", "bp@purdue.edu", "notPassword",
//                exampleHistory);
//        users.put(customer.getEmail(), customer);
//
//        // Print users
//        for (User user : users.values()) {
//            printUser(user);
//        }
//        System.out.println("#####################################");
//
//        // Write to file
//        FileIO fileIO = new FileIO();
//        fileIO.writeUsers(users);
//
//        // Read from file and print
//        HashMap<String, User> usersIn = fileIO.readUsers();
//        for (User user : usersIn.values()) {
//            printUser(user);
//        }
//    }
}
