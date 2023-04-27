import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server implements Runnable {
    User currentUser; // the client user
    Socket socket; // the client socket
    public final static Object sentinel = new Object(); // gatekeeper object for concurrency
    private static HashMap<String, User> users; // all the users

    /**
     * Initiate a new server object
     * @param socket the client object associate with this server
     */
    public Server(Socket socket) {
        this.socket = socket;
    }

    /**
     * Send an ArrayList of String containing the {@link #currentUser}'s purchase history
     * @param output the output stream to communicate with client
     */
    private void getPurchaseHistory(ObjectOutputStream output) throws IOException {
        // @Ethan
        // Assume this user must be customer
        output.writeObject(((Customer) this.currentUser).getPurchaseHistory());
        output.flush();
    }

    /**
     * Decrease the specified product quantity by the specified purchase quantity
     * Increase the Store's revenue
     * Add the current {@link #currentUser} to the {@link Store}'s customer field
     * Add the new sale history to the {@link Store}'s saleHistory field
     * Add the new purchase history to the {@link #currentUser} purchaseHistory field
     * @param output the output stream to communicate with client
     * @param sellerEmail the store's sellerName (used for quicker searching)
     * @param storeName the product's storeName
     * @param productName the product's name
     * @param purchaseQty the quantity purchase
     */
    private void buyItem(ObjectOutputStream output,
                         String sellerEmail, String storeName, String productName, int purchaseQty) {

    }

    /**
     * Loop through all Seller in the Market and collect all the Store
     * @return an ArrayList of Store currently in the Marketplace
     */
    private ArrayList<Store> collectMarketStore() {
        // @Ethan
        ArrayList<Store> allStores = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Seller) {
                // Get a user's stores into an arraylist.
                ArrayList<Store> userStores = new ArrayList<>(((Seller) user).getStores().values());

                // Adds all the stores of a user to the collection of all the stores in the market.
                allStores.addAll(userStores);
            }
        }
        return allStores;
    }

    /**
     * Loop through all Store in the Marketplace and collect all the Product
     * @return an ArrayList of Product currently in the Marketplace
     */
    private ArrayList<Product> collectMarketProduct() {
        // @Ethan
        ArrayList<Product> allProducts = new ArrayList<>();
        for (Store store : collectMarketStore()) {
            // add a store's products to all products
            allProducts.addAll(store.getCurrentProducts());
        }
        return allProducts;
    }

    /**
     * Get an ArrayList contains the Product from all existing Store within the Marketplace
     * If searchKey is '-1', send an ArrayList of all Product in the Marketplace
     * <p>
     * If searchKey IS NOT '-1',
     * send an ArrayList of Products that contain the searchKey in their name or description to client
     * ArrayList is empty if there is no matching Product
     * @param output the output stream to communicate with client
     * @param searchKey the key to search (i.e. the product name or description); '-1' if no search is needed
     */
    private void getMarketProduct(ObjectOutputStream output, String searchKey) throws IOException {
        // @Ethan
        ArrayList<Product> allProducts = collectMarketProduct();
        // No search
        if (searchKey.equals("-1")) {
            output.writeObject(allProducts);
            output.flush();

        } else { // Searching
            searchKey = searchKey.toLowerCase();
            ArrayList<Product> matchingProducts = new ArrayList<>();
            for (Product p : allProducts) {
                if (p.getName().toLowerCase().contains(searchKey) || // <- searchKey is in name
                        (p.getDescription().toLowerCase().contains(searchKey))) { // <- or searchKey is in description
                    // add matching product to matchingProducts
                    matchingProducts.add(p);
                }
            }
            output.writeObject(matchingProducts);
            output.flush();
        }
    }

    /**
     * Get an ArrayList contains the Product from all existing Store within the Marketplace
     * Sort the ArrayList in the ascending order by price
     * @param output the output stream to communicate with client
     */
    private void sortProductPrice(ObjectOutputStream output) throws IOException {
        // @Ethan
        ArrayList<Product> allProduct = collectMarketProduct();
    }

    /**
     * Get an ArrayList contains the Product from all existing Store within the Marketplace
     * Sort the ArrayList in the ascending order by quantity
     * @param output the output stream to communicate with client
     */
    private void sortProductQty(ObjectOutputStream output) throws IOException {
        ArrayList<Product> allProduct = collectMarketProduct();
    }

    /**
     * Remove the product from the specified store's product list
     * Send a TRUE boolean object to client if success
     * Send a FALSE boolean object to client if failed (i.e. the product does not exist in the specified store)
     * @param output output the output stream to communicate with client
     * @param sellerEmail the store's sellerName (used for quicker searching)
     * @param storeName the name of the Store that contain the product
     * @param productName the name of the product
     */
    private void deleteProduct(ObjectOutputStream output, String sellerEmail,
                               String storeName, String productName) throws IOException {

    }

    /** TODO: Can we make sure price and quantity to be > 0 in GUI?
     * Modify an existing product in the specified store, which exist in the {@link #currentUser}'s store field
     * Send a TRUE boolean object to client if success
     * Send a FALSE boolean object to client if failed (i.e. product's name is taken within the same store)
     * @param output the output stream to communicate with client
     * @param name the new product name (not allowed to be changed; used to search)
     * @param storeName the store name (not allowed to be changed; used to search)
     * @param description the description of the product (same if description is not changed)
     * @param price the price of the product (same if description is not changed)
     * @param quantity the quantity of the product (same if description is not changed)
     */
    private void modifyProduct(ObjectOutputStream output,
                            String name, String storeName, String description, double price, int quantity) throws IOException {

    }

    /** TODO: Can we make sure price and quantity to be > 0 in GUI?
     * Create a new product in the specified store, which exist in the {@link #currentUser}'s store field
     * Send a TRUE boolean object to client if success
     * Send a FALSE boolean object to client if failed (i.e. product's name is taken within the same store)
     * @param output the output stream to communicate with client
     * @param name the new product name
     * @param storeName the product's store
     * @param description the description of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     */
    private void addProduct(ObjectOutputStream output, String name, String storeName, String description,
                            double price, int quantity) throws IOException {

    }

    /**
     * Create an ArrayList contains the Product from the specified store
     * If searchKey is '-1', send an ArrayList of all Product in the Store to client
     * <p>
     * If searchKey IS NOT '-1',
     * send an ArrayList of Products that contain the searchKey in their name or description to client
     * ArrayList is empty if there is no matching Product
     * @param output the output stream to communicate with client
     * @param sellerEmail the store's sellerName (used for quicker searching)
     * @param searchKey the key to search (i.e. the product name or description); '-1' if no search is needed
     */
    private void getStoreProduct(ObjectOutputStream output, String sellerEmail,
                                 String storeName, String searchKey) throws IOException {

    }

    /**
     * Create an ArrayList contains all the Store from the {@link #currentUser}'s store field
     * If searchKey is '-1', send an ArrayList of all Store to client
     * <p>
     * If searchKey IS NOT '-1',
     * send an ArrayList of Store that contain the searchKey in their name to client
     * ArrayList is empty if there is no matching Store
     * @param output the output stream to communicate with client
     * @param searchKey the key to search (i.e. the store name); '-1' if no search is needed
     */
    private void getSellerStores(ObjectOutputStream output, String searchKey) throws IOException {

    }

    /**
     * Delete a store in the {@link #currentUser}'s stores field
     * Send a TRUE boolean object to client if success
     * Send a FALSE boolean object to client if failed (i.e. there is no store with matching name)
     * @param output the output stream to communicate with client
     * @param storeName the name of the store to be deleted
     */
    private void deleteStore(ObjectOutputStream output, String storeName) throws IOException {

    }

    /**
     * Create a new store in the {@link #currentUser}'s stores field
     * Send a TRUE boolean object to client if success
     * Send a FALSE boolean object to client if failed (i.e. seller also has the store with similar name)
     * @param storeName the new store's name
     * @param output the output stream to communicate with client
     */
    private void createStore(ObjectOutputStream output, String storeName) throws IOException {

    }

    /**
     * Send a string object containing the {@link #currentUser}'s username to the client
     * @param output the output stream to communicate with client
     */
    private void getUserType(ObjectOutputStream output) throws IOException {

    }

    /**
     * Send a string object containing the {@link #currentUser}'s email to the client
     * @param output the output stream to communicate with client
     */
    private void getUserEmail(ObjectOutputStream output) throws IOException {

    }

    /**
     * Send a string object containing the {@link #currentUser}'s username to the client
     * @param output the output stream to communicate with client
     */
    private void getUserName(ObjectOutputStream output) throws IOException {

    }

    /**
     * Delete the user in {@link #users} associate with the given email
     * @param email the email of the account to be deleted
     */
    private void deleteAccount(String email) {

    }

    /**
     * Validate the username, and password; TODO: Can this be done in the GUI?
     * Modify the {@link #users}'s username and password
     * Send a TRUE boolean object to client if success;
     * Send a FALSE boolean object to client if failed (i.e. username is taken)
     * @param output the output stream to communicate with client
     * @param email the user's email (not allow to be changed; used to search user)
     * @param username the username of the new user
     * @param password the password of the new user
     */
    private void modifyAccount(ObjectOutputStream output, String email,
                               String username, String password) throws IOException {

    }

    /**
     * Close the socket
     */
    private void logOut() throws IOException {
        socket.close();
    }

    /**
     * Find the user with a matching id (username or email) and email
     * Set the {@link #currentUser} to the newly created User if success;
     * Send a TRUE boolean object to client if success;
     * Send a FALSE boolean object to client if failed.
     * @param output the output stream to communicate with client
     * @param id username or email of the client
     * @param password password of the client
     */
    private void logIn(ObjectOutputStream output, String id, String password) throws IOException {

    }

    /**
     * Validate the username, email, and password; TODO: Can this be done in the GUI?
     * Create a new user in {@link #users} and set the {@link #currentUser} to the newly created User if success;
     * Send a  1 integer object to client if success;
     * Send a  0 integer object to client if email is taken
     * Send a -1 integer object to client if username is taken)
     * @param output the output stream to communicate with client
     * @param username the username of the new user
     * @param email the email of the new user
     * @param password the password of the new user
     */
    private void signUp(ObjectOutputStream output, String username, String email, String password) throws IOException {

    }

    /**
     * Process the query get from the Client GUI and call the appropriate method with appropriate parameters
     * Query will always be in the form: command_pram1_param2_..., where:
     * command is the command (used to decide which method to be called)
     * param* are the parameters for the method to be called.
     * This function only called other methods IFF the there are enough parameter to do so.
     * Print to terminal the query and method called; print FAILED if params are missing // TODO: delete after finish debugging
     * @param query the query from the Client
     * @param output the ObjectOutputStream to send object to client
     */
    private void processCommand(String query, ObjectOutputStream output) throws IOException {
        String[] queryPart = query.split("_");

        String command = queryPart[0];
        switch (command) {
            // Signing up (Query: SIGNUP_username_email_password)
            case "SIGNUP" -> {

            }

            // Logging in (Query: LOGIN_username/email_password)
            case "LOGIN" -> {

            }


            // Logging out (Query: LOGOUT)
            case "LOGOUT" -> {

            }


            // Modifying account (Query: MODACC_email_username_password)
            case "MODACC" -> {

            }

            // Deleting account (Query: DELACC_username)
            case "DELACC" -> {

            }

            // Getting username (Query: NAME)
            case "NAME" -> {

            }

            // Getting email (Query: EMAIL)
            case "EMAIL" -> {

            }

            // Getting usertype (Query: TYPE)
            case "TYPE" -> {

            }

            // Creating a store (Query: CRTSTR_storeName)
            case "CRTSTR" -> {

            }

            // Deleting a store (Query: DELSTR_storeName)
            case "DELSTR" -> {

            }

            // Getting a list of stores (can be used for searching) (Query: GETSELLSTR_searchKey)
            case "GETSELLSTR" -> {

            }

            // Getting a store's list of products (can be used for searching) (Query: GETSTRPROD_searchKey)
            case "GETSTRPROD" -> {

            }

            // Adding a new product to a store (Query: ADDPROD_productName_storeName_description_price_quantity)
            case "ADDPROD" -> {

            }

            // Modifying a store's product (Query: MODPROD_productName_storeName_description_price_quantity)
            case "MODPROD" -> {

            }

            // Deleting a store's product (Query: DELPROD_sellerEmail_storeName_productName)
            case "DELPROD" -> {

            }

            // Sorting market's product by price (Query: SORTP)
            case "SORTP" -> {

            }

            // Sorting market's product by quantity (Query: SORTQ)
            case "SORTQ" -> {

            }

            // Getting the market's list of products (can be used for searching) (Query: GETMRKPROD_searchKey)
            case "GETMRKPROD" -> {

            }

            // Buying a product (Query: BUY_sellerEmail_storeName_productName_quantity)
            case "BUY" -> {

            }

            // Getting customer history (Query: GETHIS)
            case "GETHIS" -> {

            }
        }
    }

    /**
     * The run method for Concurrency
     */
    public void run() {
        System.out.printf("Connection received from %s\n", socket);
        try (Scanner input = new Scanner(socket.getInputStream())) {
            try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                while (input.hasNextLine()) {
                    String command = input.nextLine();
                    System.out.printf("Received command: %s", command);
                    processCommand(command, output);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException { // TODO: handle exception instead
//        ArrayList<String> product = new ArrayList<>();
//        product.add("apple");
//        product.add("pen");
//        product.add("pencil");
//        Store store1 = new Store("Amazon", product);
//
//        ArrayList<String> product2 = new ArrayList<>();
//        product2.add("banana");
//        product2.add("eraser");
//        product2.add("computer");
//        Store store2 = new Store("Lazada", product2);
//
//
//        ArrayList<String> inbox = new ArrayList<>();
//        inbox.add("alo");
//        inbox.add("dmm");
//
//        ArrayList<Store> stores = new ArrayList<>();
//        stores.add(store1);
//        stores.add(store2);
//
//        Seller seller1 = new Seller("bao2803", "phan43@purdue.edu", "Abc@1", inbox, stores);
//        seller1.printSeller();
//        Seller seller2 = new Seller("bao2003", "phan34@purdue.edu", "Abc@2", inbox, stores);
//        seller2.printSeller();

//        HashMap<String, Seller> users = new HashMap<>();
//        users.put(seller1.getEmail(), seller1);
//        users.put(seller2.getEmail(), seller2);
//
//        Server.setUsers(users);

        // Reading in existing users
        FileIO fileIO = new FileIO();
        Server.users = fileIO.readUsers();

        // Allocate server socket at given port...
        ServerSocket serverSocket = new ServerSocket(8080);

        // infinite server loop: accept connection,
        // spawn thread to handle...
        while (true) {
            System.out.printf("Socket open, waiting for connections on %s\n",
                    serverSocket); // TODO: delete all print statements after debugging
            Socket socket = serverSocket.accept();
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }
}
