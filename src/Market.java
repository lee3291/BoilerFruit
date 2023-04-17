import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Market {
    private ArrayList<User> users; // customer + seller, all existing users.
    private ArrayList<Customer> customers;
    private ArrayList<Seller> sellers;
    private ArrayList<Product> products;
    private ArrayList<Store> stores;

    /**
     * Constructor, when the main method is run, it has to initialize all the fields in the Market class.
     * this constructor will call all the methods that read file.
     * <p>
     * Note: Call order matters because some fields are dependent on others.
     * for example, product file needs to be initialized so the Store file can have product objects in it.
     */
    public Market() { // Deleted parameter for String fileName. We won't get user input. file name will be constant.
        // method call order from main method
        readUserCredentials(); // <- DO NOT change orders of these methods.
        readProductInfo();
        readUserInfo(); // reads seller information as well.
        readCustomerInfo();
        readStoreInfo();
    }

    /**
     * Getters and Setters
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(ArrayList<Seller> sellers) {
        this.sellers = sellers;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    /**
     * Adders and Deletes
     * Adds objects into the Market class fields.
     * <p>
     * #NOTE: This method is necessary because you can't do
     * <p>
     * User user;
     * market.getUsers().add(user);
     * <p>
     * This code above will not update fields in the Market object.
     * What you did is you've just created a copy of "users" and added "user" to that arraylist.
     * <p>
     * What you would want to do is,
     * <p>
     * market.setUsers(market.getUsers().add(user));
     * <p>
     * But this is too tedious, so use the Adder methods below.
     */

    public void addProduct(Product product) {
        // If product existed, append the quantity
        for (Product curr : products) {
            if (curr.getName().equalsIgnoreCase(product.getName())
                    && curr.getStore().equalsIgnoreCase(product.getStore())) {
                curr.setQuantity(curr.getQuantity() + product.getQuantity());
                return;
            }
        }

        // Product does not exist
        products.add(product);
    }

    public void addProduct(ArrayList<Product> products) {
        // If product existed, append the quantity
        for (Product product : products) {
            addProduct(product);
        }
    }

    /**
     * Remove a specified product from the marketplace
     *
     * @param product  the product to be removed
     * @param quantity the quantity to be removed
     */
    public void removeProduct(Product product, int quantity) {
        Product deleteItem = null;
        // Find the product in the listing; remove the product and return true
        for (Product curr : products) {
            if (curr.getName().equalsIgnoreCase(product.getName())
                    && curr.getStore().equalsIgnoreCase(product.getName())) {
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
            products.remove(deleteItem);
        }
    }

    public void addSeller(Seller seller) {
        sellers.add(seller);
        users.add(seller);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        users.add(customer);
    }

    public void deleteUser(User user) { // deletes user from arraylists, users, customers, sellers
        if (user instanceof Customer) {
            Customer deleteCustomer = null;
            for (Customer c : customers) {
                if (c.getUserName().equals(user.getUserName())) {
                    deleteCustomer = c;
                    break;
                }
            }

            if (deleteCustomer != null) {
                customers.remove(deleteCustomer);
                users.remove(deleteCustomer);
            }
        } else if (user instanceof Seller) {
            Seller deleteSeller = null;
            for (Seller s : sellers) {
                if (s.getUserName().equals(user.getUserName())) {
                    deleteSeller = s;
                    break;
                }
            }

            if (deleteSeller != null) {
                sellers.remove(deleteSeller);
                users.remove(deleteSeller);
            }
        }
    }

    public void addStore(Store store) {
        this.stores.add(store);
    }

    /**
     * Object getters
     * <p>
     * Allows you to get Objects by passing its name field or other unique fields into the parameter.
     * <p>
     * #Note: If the object you're looking for is not instantiated in the market object, it will not be there.
     * It loops through the arraylist fields of the market object
     */
    public User userGetterByEmail(String email) {
        for (User user : this.users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User userGetterByName(String userName) {
        for (User user : this.users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Check if the passed in userName already existed
     *
     * @param userName userName to be checked
     * @return true if username existed; false otherwise
     */
    public boolean userNameExisted(String userName) {
        return userGetterByName(userName) != null;
    }

    /**
     * Check if the email passed in already existed
     *
     * @param email email to be checked
     * @return true if email existed; false otherwise
     */
    public boolean emailExisted(String email) {
        return userGetterByEmail(email) != null;
    }

    public Product productGetter(String productName) {
        for (Product product : this.products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public Store storeGetter(String storeName) {
        for (Store store : this.stores) {
            if (store.getStoreName().equals(storeName)) {
                return store;
            }
        }
        return null;
    }

    /**
     * Check if the passed in storeName is already existed
     *
     * @param storeName storeName to be checked
     * @return true if store existed; false otherwise
     */
    public boolean storeNameExisted(String storeName) {
        return storeGetter(storeName) != null;
    }

    /**
     * readUserCredentials method.
     * Reads text file containing user information in the format of
     * [usertype, username, email, password]
     * This file will ALWAYS be in this format.
     * This file will ALWAYS be named "userCredentials.txt"
     * See userCredentials.txt file for reference.
     * <p>
     * As it reads through the file, it will create a new User object, and sort it into the market object fields.
     */
    private void readUserCredentials() {
        // reads info from file and creates new User objects, sorts them into customer and sellers..
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Seller> sellers = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("userCredentials.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] temp = line.split(",");
                for (int i = 0; i < temp.length; i++) {
                    String trimmer = temp[i].trim();
                    temp[i] = trimmer;
                }
                if (temp[0].equals("customer")) { // create new customer object
                    Customer customer = new Customer(temp[1], temp[2], temp[3]);
                    customers.add(customer);
                    users.add(customer);
                } else if (temp[0].equals("seller")) { // create new seller object
                    Seller seller = new Seller(temp[1], temp[2], temp[3]);
                    sellers.add(seller);
                    users.add(seller);
                }
                line = bfr.readLine();
            }
            this.users = users;
            this.customers = customers;
            this.sellers = sellers;
        } catch (FileNotFoundException e) { // first time running
            this.users = new ArrayList<>();
            this.customers = new ArrayList<>();
            this.sellers = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Failed to load User info files!");
            e.printStackTrace();
        }
    }

    /**
     * readUserInfo method
     * <p>
     * reads through all existing individual user information text files, finds the matching user object from market,
     * and initiates the inbox field.
     * <p>
     * All the customer and seller fields will be initialized up to inbox.
     * All sellers are read and initialized by now.
     * <p>
     * Customers still need to initialize more fields, this will be done by readCustomerInfo()
     * <p>
     * #Note: This method must be called AFTER readUserCredentials()
     */
    private void readUserInfo() { // will work for both customer and seller
        for (User user : this.users) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(user.getUserName() + ".txt"))) {
                String line = bfr.readLine();
                while (line != null) {
                    if (line.equals(user.getUserName())) {
                        line = bfr.readLine();
                    }
                    if (line.equals("<inbox>")) {
                        line = bfr.readLine();
                        while (!line.equals("<shoppingCart>")) {
                            user.addInbox(line);
                            line = bfr.readLine();
                        }
                        // reached <shoppingCart>
                    } else {
                        line = bfr.readLine();
                    }
                }
            } catch (FileNotFoundException e) { // initial run
                break;
            } catch (IOException e) {
                System.out.println("Failed to load customer files!");
                e.printStackTrace();
            }
        }
    }

    /**
     * readCustomerInfo
     * <p>
     * Reads individual customer information text file consisting of shoppingCart, itemsPurchased fields.
     * <p>
     * #NOTE: This method must be called AFTER the readUserInfo() method.
     */
    private void readCustomerInfo() {
        for (Customer c : customers) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(c.getUserName() + ".txt"))) {
                String line = bfr.readLine();
                while (line != null) {
                    if (line.equals(c.getUserName())) {
                        line = bfr.readLine();
                    }
                    if (line.equals("<inbox>")) {
                        line = bfr.readLine();
                        while (!line.equals("<shoppingCart>")) {
                            line = bfr.readLine();
                        }
                    }
                    if (line.equals("<shoppingCart>")) {
                        line = bfr.readLine();
                        while (!line.equals("<itemsPurchased>")) {
                            c.addToShoppingCart(productGetter(line)); // need to get Object.
                            line = bfr.readLine();
                        }
                        // reached <itemsPurchased>
                    } else if (line.equals("<itemsPurchased>")) {
                        line = bfr.readLine();
                        while (line != null) {
                            String[] temp = line.split(",");
                            c.addToItemsPurchased(productGetter(temp[0])); // need to get object.
                            line = bfr.readLine();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to load customer files!");
                e.printStackTrace();
            }
        }
    }

    /**
     * writeUserCredentials
     * <p>
     * Writes every user credentials information into the userCredentials.txt file.
     * Reflects newly added users to the file.
     */
    public void writeUserCredentials() {
        // i.g: <customer, student1, tudent1@purdue.edu, zxcv1243>
        try (PrintWriter pw = new PrintWriter(new FileWriter("userCredentials.txt"))) {
            for (Customer c : this.customers) {
                String line = String.format("customer, %s, %s, %s", c.getUserName(), c.getEmail(), c.getPassword());
                pw.println(line);
            }
            for (Seller s : this.sellers) {
                String line = String.format("seller, %s, %s, %s", s.getUserName(), s.getEmail(), s.getPassword());
                pw.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: ");
            e.printStackTrace();
        }
    }

    /**
     * writeCustomerInfo
     * <p>
     * Writes customer information by creating new files, or overwriting pre-existing customer files.
     * Files are named after customer name.
     */
    public void writeCustomerInfo() {
        for (Customer c : this.customers) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(c.getUserName() + ".txt"))) {
                pw.println(c.getUserName());
                pw.println("<inbox>");
                for (int i = 0; i < c.getInbox().size(); i++) {
                    pw.println(c.getInbox().get(i));
                }
                pw.println("<shoppingCart>");
                for (int i = 0; i < c.getShoppingCart().size(); i++) {
                    pw.println(c.getShoppingCart().get(i).getName() + "_" + c.getShoppingCart().get(i).getStore());
                }
                pw.println("<itemsPurchased>");
                for (int i = 0; i < c.getItemsPurchased().size(); i++) {
                    pw.println(c.getItemsPurchased().get(i).getName() + "_" + c.getItemsPurchased().get(i).getStore());
                }
            } catch (IOException e) {
                System.out.println("Failed to write customer files!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Read the Stores.txt from local storage, then read all the store that is listed in Stores.txt to initiate the
     * {@link #stores} field
     * Stores.txt format:
     * storeName1,seller1
     * storeName2,seller2
     * storeName3,seller3
     * ...
     */
    public void readStoreInfo() {
        stores = new ArrayList<>();

        // Initiate all stores from Stores.txt
        try (BufferedReader bfr = new BufferedReader(new FileReader("Stores.txt"))) {
            String line = bfr.readLine();
            String name; // current store's name
            String seller; // current store's seller

            // Get each line's name and seller and create a new corresponding Store object
            while (line != null) {
                name = line.split(",")[0];
                seller = line.split(",")[1];
                stores.add(new Store(name, seller));
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // first time running
            return;
        } catch (IOException e) {
            System.out.println("Failed to load customer files!");
            e.printStackTrace();
        }

        // Loop through each store to find its correspond file; initiate its ArrayList with the new info
        for (Store store : stores) {
            try (CSVReader reader = new CSVReaderBuilder(new FileReader(store.getStoreName() + ".csv"))
                    .build()) {
                // Read name/seller
                String[] row = reader.readNext();
                String name = row[0]; // current store's name
                String seller = row[1]; // current store's seller
                if (!name.equalsIgnoreCase(store.getStoreName()) || !seller.equals(store.getSeller())) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    continue;
                }

//                // Add store to seller
                Seller currUser = (Seller) userGetterByName(seller);
                currUser.addStore(store);

                // Read sale history
                row = reader.readNext();
                if (!row[0].equals("<history>")) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    continue;
                }
                ArrayList<Product> currHistory = new ArrayList<>();
                row = reader.readNext();
                while (!(row == null) && !row[0].equals("<listing>")) {
                    Product curr = new Product(
                            row[0], // store name
                            row[1], //seller's name
                            row[2], //product's store
                            row[3], // description
                            Double.parseDouble(row[4]), // price
                            Integer.parseInt(row[5]) // quanity
                    );
                    currHistory.add(curr);
                    row = reader.readNext();
                }

                // Read listing
                ArrayList<Product> currListing = new ArrayList<>();
                if (row == null) {
                    System.out.println(store.getStoreName() + ".csv" + " is corrupted!");
                    continue;
                } else {
                    row = reader.readNext();
                    while (!(row == null) && !row[0].equals("<customers>")) {
                        Product curr = new Product(
                                row[0], // store name
                                row[1], //seller's name
                                row[2], //product's store
                                row[3], // description
                                Double.parseDouble(row[4]), // price
                                Integer.parseInt(row[5]) // quanity
                        );
                        currListing.add(curr);
                        row = reader.readNext();
                    }
                }

                // Read customers
                ArrayList<Customer> currCustomer = new ArrayList<>();
                if (row == null) {
                    System.out.println(store.getStoreName() + ".txt" + " is corrupted!");
                } else {
                    row = reader.readNext();
                    while (!(row == null)) {
                        User temp = userGetterByName(row[0]);
                        if (temp instanceof Customer) {
                            temp = new Customer(temp.getUserName(), temp.getEmail(), temp.getPassword());
                        } else {
                            System.out.println(store.getStoreName() + ".txt" + " is corrupted!");
                            continue;
                        }
                        currCustomer.add((Customer) temp);
                        row = reader.readNext();
                    }
                }

                // Set new array list to current store
                store.setHistory(currHistory);
                store.setCurrentProducts(currListing);
                store.setCustomers(currCustomer);
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

    // write store objects from market.stores into a csv file. Use productDetail method.
    // write a file containing store names.
    public void writeStoreInfo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("Stores.txt"))) {
            for (Store s : stores) {
                // Write a file containing all the unique store names.
                String line = String.format("%s,%s", s.getStoreName(), s.getSeller());
                pw.println(line);

                try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(s.getStoreName() + ".csv"))
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

                    lines.add(new String[]{"<listing>"});
                    for (Product p : s.getCurrentProducts()) {
                        String[] productFields = p.productDetails();
                        lines.add(productFields);
                    }

                    lines.add(new String[]{"<customers>"});
                    for (Customer c : s.getCustomers()) {
                        String[] customerNames = {c.getUserName()};
                        lines.add(customerNames);
                    }

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
     * Read {@link #products} from the AllProducts.csv
     */
    public void readProductInfo() {
        ArrayList<Product> products = new ArrayList<>();

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
            this.products = new ArrayList<>();
            return;
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        this.products = products;
    }

    /**
     * Write {@link #products} to the AllProducts.csv
     */
    public void writeProductInfo() {
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
    }

    /**
     * buyItem method.
     * <p>
     * It will move a product in the shoppingCart into the itemsPurchased.
     * quantity: quantity purchased
     * decrement from store of the product.
     */
    public void buyItem(Customer customer, Product product, int quantity) {
        // Get store and remove product from store shelf
        Store store = storeGetter(product.getStore()); // <- store object
        store.removeListing(product, quantity);

        // Update store and customer history
        Product productPurchased = new Product(product.getName(), product.getSeller(), product.getStore(),
                product.getDescription(), product.getPrice(), quantity);
        store.addHistory(productPurchased);
        customer.addToItemsPurchased(productPurchased);
    }

    /**
     * buyAllItem method.
     * <p>
     * It will move all the products in the shoppingCart into the itemsPurchased.
     * only allowed to buy at shopping cart page.
     * decrement from store of the product.
     */
    public void buyAllItem(Customer customer) {
        for (Product p : customer.getShoppingCart()) {
            Store store = storeGetter(p.getStore()); // <- store object
            store.removeListing(p, p.getQuantity());
            customer.addToItemsPurchased(p);
        }
        customer.clearCart(); // empties customer shopping cart.
        System.out.println("All items are bought. Cart is now empty!");
        System.out.println("Going back...");
    }

    /**
     * viewCustomerCarts
     * <p>
     * Goes through all the customers, finds their shoppingCarts, and views the content of all the products.
     */
    public boolean viewCustomerCarts(String customerName) {
        for (Customer c : customers) {
            if (c.getUserName().equals(customerName)) {
                // Get customer shopping cart
                ArrayList<Product> currentCart = c.getShoppingCart();
                if (currentCart == null) {
                    break;
                }

                System.out.println("In " + c.getUserName() + "'s shopping cart!\n");
                for (Product p : currentCart) {
                    System.out.println("Product name: " + p.getName());
                    System.out.println("Seller: " + p.getSeller());
                    System.out.println("Store: " + p.getStore());
                    System.out.println("Product Description: \n" + p.getDescription());
                    System.out.println("# items in cart: " + p.getQuantity());
                    System.out.println("Price: " + p.getPrice());
                    System.out.println();
                }
                return true;
            }
        }

        System.out.println("Customer does not exist!");
        System.out.println("Advise the list above");
        return false;
    }
}