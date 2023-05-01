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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FileIO {
    private final String dataFolder = "./data/"; // the path to the data directory
    private final String usersFile = "allUsers.ser"; // the file name of the file contain all users

    /**
     * Write all user objects in users into a file (path: ./data/users.ser)
     *
     * @param users a concurrent hashmap of all users
     * @return true if success, false otherwise
     */
    public boolean writeUsers(ConcurrentHashMap<String, User> users) {
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
     * @return the ConcurrentHashMap<>()<String, User> if success, null otherwise
     */
    public ConcurrentHashMap<String, User> readUsers() {
        ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFolder + usersFile))) {
            User curr = (User) ois.readObject();
            while (curr != null) {
                // User is a seller
                if (curr instanceof Seller newSeller) {
                    users.put(curr.getEmail(), newSeller);
                } else if (curr instanceof Customer newCustomer) { // User is a customer
                    users.put(curr.getEmail(), newCustomer);
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
    public boolean exportPurchaseHistory(String filePath, ArrayList<String> purchaseHistory) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (String history : purchaseHistory) {
                String[] entries = history.split("\\|");
                for (int i = 0; i < entries.length; i++) {
                    entries[i] = entries[i].trim();
                }
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
                        row[2], // seller's email
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
}