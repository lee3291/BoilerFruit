//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Scanner;
//
//public class Marketplace {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        // Read in user data
//        User.readUserCredentials();
//        User.readUserInfo();
////        Product.readProductInfo();
//        Store.readStoreInfo();
//
//        // Program
//        System.out.println("Welcome to Secondhand Shop!");
//        System.out.println("We connect customers and sellers through a sustainable marketplace.");
//        while (true) {
//            // Choosing to sign up, login, or quit
//            String initialState;
//            while (true) {
//                System.out.println("\n-----------Welcome-----------");
//                System.out.println("""
//                    Would you like to...
//                    \t0.Quit
//                    \t1.Sign up
//                    \t2.Log in""");
//                initialState = scanner.nextLine();
//                if (!initialState.matches("[0-2]")) {
//                    System.out.println("Please enter an integer from 0 to 2 inclusively");
//                } else {
//                    break;
//                }
//            }
//
//            // Quit
//            if (initialState.equals("0")) {
//                System.out.println("Good bye!");
//                break;
//            }
//
//            // Sign Up
//            if (initialState.equals("1")) {
//
//                // Taking user type
//                String userType;
//                while (true) {
//                    System.out.println("\n----------Signing up----------");
//                    System.out.println("""
//                            Would you like to...
//                            \t0.Go back (log in)
//                            \t1.Sign up customer
//                            \t2.Sign up seller""");
//                    userType = scanner.nextLine();
//                    if (!userType.matches("[0-2]")) {
//                        System.out.println("Please enter an integer from 0 to 2 inclusively");
//                    } else {
//                        break;
//                    }
//                }
//
//                // User choose to go back
//                if (userType.equals("0")) {
//                    continue;
//                }
//
//                // Customer sign up
//                if (userType.equals("1")) {
//                    Customer customer = (Customer) User.userSignUp(scanner, 1);
//
//                    // User sign up successfully
//                    if (customer != null) {
//                        System.out.println("Account successfully created.");
//                        customerFunctions(scanner, customer);
//                    }
//
//                    // Failed to create account
//                    else {
//                        System.out.println("Failed to create account. Please try again later!");
//                    }
//                }
//
//                // Seller sign up
//                else {
//                    Seller seller = (Seller) User.userSignUp(scanner, 2);
//
//                    // User sign up successfully
//                    if (seller != null) {
//                        System.out.println("Account successfully created.");
//                        sellerFunctions(scanner, seller);
//                    }
//
//                    // Failed to create account
//                    else {
//                        System.out.println("Failed to create account. Please try again later!");
//                    }
//                }
//            }
//
//            // Log in
//            else {
//                // Taking username
//                int attempt = 3;
//                User user;
//                while (true) {
//                    System.out.println("\n-----------Logging in-----------");
//                    // Taking in username
//                    System.out.println("Enter a username or email (QUIT to cancel): ");
//                    String userID;
//                    userID = scanner.nextLine();
//                    if (userID.equals("QUIT")) {
//                        break;
//                    }
//
//                    // Taking in password
//                    System.out.println("Enter a password (QUIT to cancel): ");
//                    String password;
//                    password = scanner.nextLine();
//                    if (password.equals("QUIT")) {
//                        break;
//                    }
//
//                    // Logging in
//                    user = User.userLogIn(userID, password);
//
//                    // Login success
//                    if (user != null) {
//                        // If user is a customer
//                        if (user instanceof Customer customer) {
//                            System.out.println("Log in successful.");
//                            customerFunctions(scanner, customer);
//                        }
//
//                        // If user is a seller
//                        else if (user instanceof Seller seller) {
//                            System.out.println("Log in successful.");
//                            sellerFunctions(scanner, seller);
//                        }
//
//                        break;
//                    }
//
//                    // Log in failed
//                    else {
//                        System.out.printf("Username or password is incorrect. " +
//                                "Please try again. Attempts: %d/3\n", --attempt);
//                        if (attempt == 0) {
//                            System.out.println("You exceeds the attempts limit!");
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * The seller interface
//     *
//     * @param scanner for input
//     * @param seller  current user and role
//     */
//    public static void sellerFunctions(Scanner scanner, Seller seller) {
//        while (true) {
//            // Menu and take command
//            System.out.println("----------Main Menu----------");
//            System.out.println("Welcome, " + seller.getUserName() + "!");
//            System.out.println("\t0. Logout");
//            System.out.println("\t1. View all stores");
//            System.out.println("\t2. View stores' statistic");
//            System.out.println("\t3. Create Store");
//            System.out.println("\t4. View Customer Carts");
//            System.out.println("\t5. View Mail Page");
//            System.out.println("\t6. Edit profile");
//            System.out.println("Enter a command:");
//            String command = scanner.nextLine();
//
//            // Log out; save all data
//            if (command.equals("0")) {
//                System.out.println("Logging out...");
//                User.writeUserCredentials();
//                User.writeUserInfo();
//                Product.writeProductInfo();
//                Store.writeStoreInfo();
//                break;
//            }
//
//            // User chooses view all stores.
//            else if (command.equals("1")) {
//                // Get seller's stores
//                HashMap<String, Store> stores = seller.getStores();
//                if (stores == null || stores.isEmpty()) {
//                    System.out.println("You do not have any store yet!");
//                    continue;
//                }
//
//                // Display stores
//                HashMap<Integer, Store> optionStores = new HashMap<>();
//                int i = 1;
//                for (Store store : stores.values()) {
//                    System.out.println((i) + ". " + store.getStoreName());
//                    optionStores.put(i, store);
//                    i++;
//                }
//
//                // Getting user's interested store
//                System.out.println("Enter number to view details of the store (0 to cancel):");
//                int storeNum = -1;
//                do {
//                    try {
//                        storeNum = scanner.nextInt();
//                        scanner.nextLine();
//                    } catch (Exception e) {
//                        scanner.nextLine();
//                        System.out.println("Please enter a valid number!");
//                        continue;
//                    }
//                    if (storeNum < 0) {
//                        System.out.println("Enter positive integer!");
//                    } else if (storeNum > stores.size()) {
//                        System.out.printf("There is less than %d stores!\n", storeNum);
//                    }
//                } while ((storeNum < 0) || (storeNum > stores.size()));
//
//                if (storeNum == 0) {
//                    System.out.println("Going back to Main menu...");
//                    continue;
//                }
//
//                // Store's menu
//                Store store = optionStores.get(storeNum); // get store
//                int action = -1;
//                while (true) {
//                    // Get user's input and validate
//                    do {
//                        System.out.println(store.getStoreName() + ":");
//                        System.out.println("\t0. Go back to Main menu");
//                        System.out.println("\t1. Add listing");
//                        System.out.println("\t2. Remove listing");
//                        System.out.println("\t3. Show sales");
//                        System.out.println("\t4. Import products csv");
//                        System.out.println("\t5. Export products csv");
//                        System.out.println("Enter a number");
//                        try {
//                            action = scanner.nextInt();
//                            scanner.nextLine();
//                        } catch (Exception e) {
//                            scanner.nextLine();
//                            System.out.println("Please enter a valid number!");
//                            continue;
//                        }
//                        if (action < 0) {
//                            System.out.println("Enter positive integer!");
//                        } else if (action > 6) {
//                            System.out.println("Please enter a valid option!");
//                        }
//                    } while (action < 0 || action > 6);
//
//                    // Going back to Main menu
//                    if (action == 0) {
//                        System.out.println("Going back to Main menu...");
//                        break;
//                    }
//
//                    // Add listing
//                    else if (action == 1) {
//                        System.out.println("Enter product name:");
//                        String productName = scanner.nextLine();
//
//                        System.out.println("Enter product description:");
//                        String productDescription = scanner.nextLine();
//
//                        int productQty = -1;
//                        double productPrice = -1;
//                        do {
//                            try {
//                                System.out.println("Enter product quantity:");
//                                productQty = scanner.nextInt();
//                                scanner.nextLine();
//
//                                System.out.println("Enter product price:");
//                                productPrice = scanner.nextDouble();
//                                scanner.nextLine();
//
//                            } catch (NumberFormatException e) {
//                                scanner.nextLine();
//                                System.out.println("Please enter a valid number!");
//                                continue;
//                            }
//                            if (productQty <= 0 || productPrice <= 0) {
//                                System.out.println("Enter positive number!");
//                            }
//                        } while (productQty <= 0 || productPrice <= 0);
//
//                        // Add listing
//                        Product addProduct = new Product(productName, seller.getUserName(), store.getStoreName(),
//                                productDescription, productPrice, productQty);
//                        store.addListing(addProduct);
//                    }
//
//                    // Remove listing.
//                    else if (action == 2) {
//                        // Getting Product
//                        System.out.println("Enter product name: ");
//                        String removeProduct = scanner.nextLine();
//
//                        // Take user input and validate it
//                        System.out.println("Enter quantity to remove (0 to cancel):");
//                        int quantity;
//                        while (true) {
//                            try {
//                                quantity = scanner.nextInt();
//                                scanner.nextLine();
//
//                                if (quantity < 0) {
//                                    System.out.println("Please enter a number larger than 0");
//                                } else {
//                                    break;
//                                }
//                            } catch (Exception e) {
//                                scanner.nextLine();
//                                System.out.println("Invalid input, please try again.");
//                            }
//                        }
//
//                        // Remove product
//                        if (store.removeListing(removeProduct, quantity)) {
//                            System.out.printf("Success! Removed %d %s from %s\n", quantity, removeProduct,
//                                    store.getStoreName());
//                        } else {
//                            System.out.printf("Cannot find %s in %s!\n", removeProduct, store.getStoreName());
//                        }
//                    }
//
//                    // Show sales
//                    else if (action == 3) {
//                        store.showSale();
//                    }
//
//                    // Import product csv
//                    else if (action == 4) {
//                        System.out.println("Enter file path:");
//                        String filepath = scanner.nextLine();
//
//                        ArrayList<Product> products = store.importCSV(filepath);
//
//                        // Add listing if products is not empty
//                        if (!products.isEmpty()) {
//                            store.addListing(products);
//                            System.out.println("Products successfully added!");
//                        } else {
//                            System.out.println("There is no product to add!");
//                        }
//                    }
//
//                    // Export csv file
//                    else {
//                        System.out.println("Enter file path:");
//                        String filePath = scanner.nextLine();
//                        if (store.exportCSV(filePath)) {
//                            System.out.printf("Products are written into\n\t%s\n", filePath);
//                        } else {
//                            System.out.println("Failed to write products. Please try again!");
//                        }
//                    }
//                }
//            }
//
//            // Statistic for all stores
//            else if (command.equals("2")) {
//                HashMap<String, Store> stores = seller.getStores();
//                if (stores == null || stores.isEmpty()) {
//                    System.out.println("You do not have any store yet!");
//                    continue;
//                }
//                ArrayList<Store> sortStores = new ArrayList<>(stores.values()); // to array list to sort
//
//                System.out.println("Do you want to sort the stores by name?");
//                System.out.println("Press 1 for yes. Press any key to display the statistics without sorting");
//                String ans = scanner.nextLine();
//
//                // Sort stores if needed
//                if (ans.equals("1")) {
//
//                    System.out.println("Do you want to sort in descending order?");
//                    System.out.println("Press 1 for descending order. " +
//                            "Press any key for ascending order");
//                    ans = scanner.nextLine();
//
//                    // Ascending
//                    if (ans.equals("1")) {
//                        for (int i = 1; i < sortStores.size(); i++) {
//                            Store curr = sortStores.get(i);
//                            int j = i - 1;
//
//                            while (j >= 0 &&
//                                    Character.getNumericValue(sortStores.get(j).getStoreName().indexOf(0))
//                                            > Character.getNumericValue(curr.getStoreName().indexOf(0))) {
//                                sortStores.set(j + 1, sortStores.get(j));
//                                j = j - 1;
//                            }
//                            sortStores.set(j + 1, curr);
//                        }
//                    }
//
//                    // Descending
//                    else {
//                        for (int i = 1; i < sortStores.size(); i++) {
//                            Store curr = sortStores.get(i);
//                            int j = i - 1;
//
//                            while (j >= 0 &&
//                                    Character.getNumericValue(sortStores.get(j).getStoreName().indexOf(0))
//                                            < Character.getNumericValue(curr.getStoreName().indexOf(0))) {
//                                sortStores.set(j + 1, sortStores.get(j));
//                                j = j - 1;
//                            }
//                            sortStores.set(j + 1, curr);
//                        }
//                    }
//                }
//
//                // Display statistics
//                for (Store store : sortStores) {
//                    store.showStatistic();
//                }
//            }
//
//            // Create new store and add to Market class list
//            else if (command.equals("3")) {
//                // Take store name and make sure it is unique
//                System.out.println("Enter name of new store:");
//                String storeName = scanner.nextLine();
//                while (Store.isStoreNameExisted(storeName)) {
//                    System.out.println("Store name existed. Please try a different name: ");
//                    storeName = scanner.nextLine();
//                }
//
//                // Create new store
//                Store newStore = new Store(storeName, seller.getUserName());
//                seller.addStore(newStore);
//                System.out.println("Store successfully created!");
//            }
//
//            // View customer shopping cart
//            else if (command.equals("4")) {
//                // Get list of customer
//                HashMap<String, Customer> allCustomers = User.getCustomers();
//                if (allCustomers == null || allCustomers.isEmpty()) {
//                    System.out.println("There is no customer yet. Please try again later");
//                    continue;
//                }
//
//                // Display all seller's customers
//                for (Store store : seller.getStores().values()) {
//                    store.viewCustomers();
//                }
//
//                // Display chosen customer's cart
//                String customerName;
//                do {
//                    System.out.println("Enter customer username: ");
//                    System.out.println("Type QUIT to quit.");
//                    customerName = scanner.nextLine();
//
//                    if (customerName.equals("QUIT")) {
//                        break;
//                    }
//                } while (!seller.viewCustomerCarts(customerName));
//            }
//
//            // Mailing page
//            else if (command.equals("5")) {
//                seller.mailPage(scanner);
//            }
//
//            // Editing profile
//            else if (command.equals("6")) {
//                if (seller.editProfile(scanner)) {
//                    System.out.println("Logging out...");
//                    User.writeUserCredentials();
//                    User.writeUserInfo();
//                    Product.writeProductInfo();
//                    Store.writeStoreInfo();
//                    break;
//                }
//            }
//
//            // Invalid input cover
//            else {
//                System.out.println("Invalid input");
//            }
//        }
//    }
//
//    /**
//     * Customer's interface; ensure data are saved before logging out
//     *
//     * @param scanner  for inputs
//     * @param customer current user and role
//     */
//    public static void customerFunctions(Scanner scanner, Customer customer) {
//        // Display all available products
//        System.out.println("----------Current Market Listing---------");
//        System.out.println(Product.getProducts().size());
//        for (Product product : Product.getProducts()) {
//            System.out.println(product.displayProduct());
//        }
//        while (true) {
//            // Display menu for customers
//            System.out.println("----------Main Menu----------");
//            System.out.println("Welcome, " + customer.getUserName() + "!");
//            System.out.println("Do you want to...");
//            System.out.println("\t0. Logout");
//            System.out.println("\t1. View Current Marketplace Product");
//            System.out.println("\t2. Search by stores");
//            System.out.println("\t3. Search by products");
//            System.out.println("\t4. Sort products"); //sort price and quantity
//            System.out.println("\t5. View Shopping Cart");
//            System.out.println("\t6. View or Export purchase history");
//            System.out.println("\t7. View Mail Page");
//            System.out.println("\t8. View dashboard");
//            System.out.println("\t9. Edit profile");
//
//            // Take user input and validate it
//            String command; // the command input from customer
//            String test = "0123456789";
//            while (true) {
//                command = scanner.nextLine();
//                if (command.length() != 1 && !test.contains(command)) {
//                    System.out.println("Make sure to enter an integer from 0 to 8 inclusively");
//                } else {
//                    break;
//                }
//            }
//
//            // Log out; save all data
//            if (command.equals("0")) {
//                System.out.println("Logging out...");
//                User.writeUserCredentials();
//                User.writeUserInfo();
//                Product.writeProductInfo();
//                Store.writeStoreInfo();
//                break;
//            }
//
//            // View all
//            else if (command.equals("1")) {
//                System.out.println("----------Current Market Listing---------");
//                System.out.println(Product.getProducts().size());
//                for (Product product : Product.getProducts()) {
//                    System.out.println(product.displayProduct());
//                }
//            }
//
//
//            // Customer want to search products by store
//            else if (command.equals("2")) {
//                HashMap<String, Store> stores = Store.getStores(); // get stores in market
//                if (stores == null || stores.isEmpty()) { // there are no store
//                    System.out.println("There are no stores yet, please come back later.");
//                    System.out.println("Going back to Main menu...");
//                    continue;
//                }
//
//                // Infinite loop for customer to interact with stores' products
//                while (true) {
//                    System.out.println("----------Stores----------");
//                    // Display all stores in the market
//                    HashMap<Integer, Store> optionStore = new HashMap<>();
//                    int i = 1;
//                    for (Store store : stores.values()) {
//                        System.out.println(i + ". " + store.getStoreName());
//                        optionStore.put(i, store);
//                        i++;
//                    }
//                    System.out.println("Press 0 to exit");
//                    System.out.println("Enter store number to view details of the store:");
//
//                    // Take user's choice of store
//                    int storeNum = -1;
//                    do {
//                        try {
//                            storeNum = scanner.nextInt();
//                            scanner.nextLine();
//                        } catch (Exception e) {
//                            scanner.nextLine();
//                            System.out.printf("Please enter an integer from 0 to %d\n", stores.size() - 1);
//                        }
//                    } while (storeNum < 0 || storeNum >= stores.size());
//
//                    // User choose to quit
//                    if (storeNum == 0) {
//                        System.out.println("Going back to Main menu...");
//                        break;
//                    }
//
//                    // Get the chosen store
//                    storeNum--;
//                    Store store = optionStore.get(storeNum);
//
//                    // Display store and seller info; get store's products
//                    System.out.println(store.getStoreName());
//                    System.out.println("Seller: " + store.getSeller());
//                    ArrayList<Product> storeProducts = store.getCurrentProducts();
//                    if (storeProducts == null || storeProducts.isEmpty()) {
//                        System.out.println("Store currently has no product! Please comeback later");
//                        System.out.println("Going back to Main menu...");
//                        break;
//                    }
//
//                    // User interact with products
//                    interactWithProduct(scanner, storeProducts, customer);
//                }
//            }
//
//            // Customer want to search product by name/description
//            else if (command.equals("3")) {
//                ArrayList<Product> marketProduct = Product.getProducts(); // get all market products
//                if (marketProduct == null || marketProduct.isEmpty()) { // no product
//                    System.out.println("Market does not have any products yet, please come back later!");
//                    System.out.println("Going back to Main menu...");
//                    continue;
//                }
//
//                // Infinite loop for customer to search products
//                while (true) {
//                    System.out.println("----------Search----------");
//                    System.out.println("Type the name/description of the product (QUIT to quit)");
//                    String productToSearch = scanner.nextLine();
//
//                    // User choose to quit
//                    if (productToSearch.equals("QUIT")) {
//                        System.out.println("Going back to Main menu...");
//                        break;
//                    }
//
//                    // Search through the database for matching products; add the matched product to results
//                    ArrayList<Product> results = new ArrayList<>();
//                    for (Product product : marketProduct) {
//                        if ((product.getName().toLowerCase()).contains(productToSearch.toLowerCase()) ||
//                                (product.getDescription().toLowerCase()).contains(productToSearch.toLowerCase())) {
//                            results.add(product);
//                        }
//                    }
//
//                    // User interact with results
//                    if (!results.isEmpty()) {
//                        interactWithProduct(scanner, results, customer);
//                    } else {
//                        System.out.println("No matched product!");
//                    }
//                }
//            }
//
//            // Sorting
//            else if (command.equals("4")) {
//                // Get all market products; make sure there are products listed
//                ArrayList<Product> marketProduct = Product.getProducts();
//                if (marketProduct == null || marketProduct.isEmpty()) { // no product
//                    System.out.println("There are no products in the market yet, please come back later!");
//                    System.out.println("Going back to Main menu...");
//                    continue;
//                }
//
//                while (true) {
//                    System.out.println("Sort by?");
//                    System.out.println("0. Quit\n1. Sort by price\n2. Sort by quantity");
//
//                    // Take user sorting option and validate
//                    int sortOption; // sort by price or quantity
//                    while (true) {
//                        try {
//                            sortOption = scanner.nextInt();
//                            scanner.nextLine();
//                        } catch (Exception e) {
//                            scanner.nextLine();
//                            System.out.println("Please enter an integer from 0 to 2 inclusively.");
//                            continue;
//                        }
//
//                        if (sortOption < 0 || sortOption > 2) {
//                            System.out.println("Please enter an integer from 0 to 2 inclusively.");
//                        } else {
//                            break;
//                        }
//                    }
//
//                    // Going back to Main menu
//                    if (sortOption == 0) {
//                        System.out.println("Going back to Main menu...");
//                        break;
//                    }
//
//                    // Taking sorting order and validate
//                    System.out.println("Sort order?");
//                    System.out.println("0. Quit\n1. Lowest-highest\n2. Highest-lowest");
//                    int sortOrder; // 1. ascending or 2. descending
//                    while (true) {
//                        try {
//                            sortOrder = scanner.nextInt();
//                            scanner.nextLine();
//
//                            if (sortOrder < 0 || sortOrder > 2) {
//                                System.out.println("Please enter an integer from 0 to 2 inclusively.");
//                            } else {
//                                break;
//                            }
//                        } catch (Exception e) {
//                            scanner.nextLine();
//                            System.out.println("Please enter an integer from 0 to 2 inclusively.");
//                        }
//                    }
//
//                    // Going back to Main menu
//                    if (sortOrder == 0) {
//                        System.out.println("Going back to Main menu...");
//                        break;
//                    }
//
//                    /// Actual sorting
//                    ArrayList<Product> sortProducts;
//
//                    // Ascending by price
//                    if (sortOption == 1 && sortOrder == 1) {
//                        sortProducts = Product.sortByPrice(marketProduct, true);
//                    }
//                    // Descending by price
//                    else if (sortOption == 1 && sortOrder == 2) {
//                        sortProducts = Product.sortByPrice(marketProduct, false);
//                    }
//                    // Ascending by quantity
//                    else if (sortOption == 2 && sortOrder == 1) {
//                        sortProducts = Product.sortByQuantity(marketProduct, true);
//                    }
//                    // Descending by quantity
//                    else {
//                        sortProducts = Product.sortByQuantity(marketProduct, false);
//                    }
//
//                    // Interact with sorted products
//                    interactWithProduct(scanner, sortProducts, customer);
//                }
//            }
//
//            // View cart
//            else if (command.equals("5")) {
//                ArrayList<Product> cart = customer.getShoppingCart();
//                if (cart == null || cart.isEmpty()) {
//                    System.out.println("Your shopping cart is empty!");
//                    continue;
//                }
//
//                // Display products
//                System.out.println("----------Shopping Cart----------");
//                for (int i = 0; i < cart.size(); i++) {
//                    System.out.println((i + 1) + ". " + cart.get(i).page());
//                }
//
//                // Inspect each product
//                System.out.println("--------------------");
//                System.out.println("Enter a number to view details of a product");
//                System.out.println("Press 0 to exit");
//                System.out.println("Press -1 to Buy All");
//                System.out.println("Press -2 to Clear cart");
//
//                // Take customer's choice and validate
//                int choice;
//                while (true) {
//                    try {
//                        choice = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice < -2 || choice >= cart.size()) {
//                            System.out.printf("Please enter an integer from -2 to %d\n", cart.size() - 1);
//                        } else {
//                            break;
//                        }
//                    } catch (Exception e) {
//                        scanner.nextLine();
//                        System.out.printf("Please enter an integer from -2 to %d\n", cart.size() - 1);
//                    }
//                }
//
//                // Buy all
//                if (choice == -1) {
//                    customer.buyAllItem();
//                }
//
//                // Clear cart
//                else if (choice == -2) {
//                    customer.clearCart();
//                }
//
//                // Display cart
//                else {
//                    while (true) {
//                        // Display products
//                        System.out.println("----------Shopping cart----------");
//                        for (int i = 0; i < cart.size(); i++) {
//                            System.out.println((i + 1) + ". " + cart.get(i).page());
//                        }
//
//
//                        // Inspect each product
//                        // Take customer's choice and validate
//                        while (true) {
//                            try {
//                                System.out.println("Enter a number to view details of a product:");
//                                System.out.println("Press 0 to exit");
//                                choice = scanner.nextInt();
//                                scanner.nextLine();
//
//                                if (choice < 0 || choice >= cart.size()) {
//                                    System.out.printf("Please enter an integer from 0 to %d\n", cart.size() - 1);
//                                } else {
//                                    break;
//                                }
//                            } catch (Exception e) {
//                                scanner.nextLine();
//                                System.out.printf("Please enter an integer from 0 to %d\n", cart.size() - 1);
//                            }
//                        }
//
//                        // Customer choose to go back
//                        if (choice == 0) {
//                            System.out.println("Going back...");
//                            break;
//                        }
//
//                        // Customer picked a product
//                        choice--;
//                        Product product = cart.get(choice);
//
//                        // Display chosen product detail and options
//                        System.out.println(product.page());
//                        System.out.println("0. Go back");
//                        System.out.println("1. Buy");
//                        System.out.println("2. Add to cart");
//                        System.out.println("3. Remove from cart");
//                        System.out.println("4. Contact seller");
//
//                        // Take user input and validate
//                        while (true) {
//                            try {
//                                choice = scanner.nextInt();
//                                scanner.nextLine();
//
//                                if (choice < 0 || choice > 4) {
//                                    System.out.println("Please pick a number from 0 to 4 inclusively");
//                                } else {
//                                    break;
//                                }
//                            } catch (Exception e) {
//                                scanner.nextLine();
//                                System.out.println("Please pick a number from 0 to 4 inclusively");
//                            }
//                        }
//
//                        // If going back
//                        if (choice == 0) {
//                            System.out.println("Going back...");
//                        }
//
//                        // If user buy item
//                        else if (choice == 1) {
//                            System.out.println("Enter quantity to buy (0 to cancel):");
//
//                            // Take user input and validate it
//                            while (true) {
//                                try {
//                                    choice = scanner.nextInt();
//                                    scanner.nextLine();
//
//                                    if (choice < 0 || choice > product.getQuantity()) {
//                                        System.out.printf("Please enter a number between 0 and %d\n",
//                                                product.getQuantity());
//                                    } else {
//                                        break;
//                                    }
//                                } catch (Exception e) {
//                                    scanner.nextLine();
//                                    System.out.println("Invalid input, please try again.");
//                                }
//                            }
//
//                            // Buy if choice is not 0 (cancel)
//                            if (choice != 0) {
//                                customer.buyItem(product, choice);
//                            }
//                        }
//
//                        // If user add item to cart
//                        else if (choice == 2) {
//                            System.out.println("Enter quantity to add to cart (0 to cancel):");
//
//                            // Take user input and validate it
//                            while (true) {
//                                try {
//                                    choice = scanner.nextInt();
//                                    scanner.nextLine();
//
//                                    if (choice < 0 || choice > product.getQuantity()) {
//                                        System.out.printf("Please enter an integer between 0 and %d\n",
//                                                product.getQuantity());
//                                    } else {
//                                        break;
//                                    }
//                                } catch (Exception e) {
//                                    scanner.nextLine();
//                                    System.out.printf("Please enter an integer between 0 and %d\n",
//                                            product.getQuantity());
//                                }
//                            }
//
//                            // Add product to shopping cart
//                            if (choice != 0) {
//                                customer.addToShoppingCart(product, choice);
//                            }
//                        }
//
//                        // If user remove item from cart
//                        else if (choice == 3) {
//                            System.out.println("Enter quantity to remove from cart (0 to cancel):");
//
//                            // Take user input and validate it
//                            while (true) {
//                                try {
//                                    choice = scanner.nextInt();
//                                    scanner.nextLine();
//
//                                    if (choice < 0 || choice > product.getQuantity()) {
//                                        System.out.printf("Please enter an integer between 0 and %d\n",
//                                                product.getQuantity());
//                                    } else {
//                                        break;
//                                    }
//                                } catch (Exception e) {
//                                    scanner.nextLine();
//                                    System.out.printf("Please enter an integer between 0 and %d\n",
//                                            product.getQuantity());
//                                }
//                            }
//
//                            // Remove product to shopping cart
//                            if (choice != 0) {
//                                customer.removeFromShoppingCart(product.getName(), choice);
//                            }
//                        }
//
//                        // If user want to contact seller
//                        else {   //send message
//                            System.out.println("Message:");
//                            String message = scanner.nextLine();
//
//                            if (customer.sendMessage(User.userGetterByName(product.getSeller()), message)) {
//                                System.out.println("Message sent!");
//                            } else {
//                                System.out.println("Failed to send message!");
//                            }
//                        }
//                    }
//                }
//            }
//
//            // View/export purchase history
//            else if (command.equals("6")) {
//                ArrayList<Product> purchaseHistory = customer.getItemsPurchased();
//                if (purchaseHistory == null || purchaseHistory.isEmpty()) {
//                    System.out.println("Purchase history is empty");
//                    continue;
//                }
//
//                // Display purchase history
//                System.out.println("---------History----------");
//                for (Product product : purchaseHistory) {
//                    System.out.println(product.page());
//                }
//
//                // Export history
//                System.out.println("Do you want to export the history of purchase?");
//                System.out.println("0. No\n1.Yes");
//
//                // Take user input
//                int choice;
//                while (true) {
//                    try {
//                        choice = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice != 0 && choice != 1) {
//                            System.out.println("Enter 0 or 1");
//                        } else {
//                            break;
//                        }
//                    } catch (Exception e) {
//                        scanner.nextLine();
//                        System.out.println("Enter 0 or 1");
//                    }
//                }
//
//                // Write to file
//                if (choice == 1) {
//                    System.out.println("Enter the file path: ");
//                    String filePath = scanner.nextLine();
//                    if (customer.exportPurchaseHistory(filePath)) {
//                        System.out.printf("History is written into\n\t%s\n", filePath);
//                    } else {
//                        System.out.println("Failed to write purchase history. Please try again later!");
//                    }
//                }
//            }
//
//            // View mail
//            else if (command.equals("7")) {
//                customer.mailPage(scanner);
//            }
//
//            // View dashboard
//            else if (command.equals("8")) {
//                System.out.println("Do you want to sort the dashboard in descending order?");
//                System.out.println("Press 1 for descending. Press any other key for ascending order.");
//                String ans = scanner.nextLine();
//                customer.viewDashboard(ans.equals("1"));
//            }
//
//            // Edit profile
//            else {
//                if (customer.editProfile(scanner)) {
//                    System.out.println("Logging out...");
//                    User.writeUserCredentials();
//                    User.writeUserInfo();
//                    Product.writeProductInfo();
//                    Store.writeStoreInfo();
//                    break;
//                }
//            }
//        }
//    }
//
//    /**
//     * The customer's interaction with the current product list, i.e. a user can view a product in detail and
//     * decide to buy, add to cart, or contact seller
//     *
//     * @param scanner  for input
//     * @param products current list of product (sorted/by store/etc)
//     * @param customer current customer
//     */
//    public static void interactWithProduct(Scanner scanner, ArrayList<Product> products, Customer customer) {
//        while (true) {
//            // Display products
//            System.out.println("----------Products----------");
//            for (int i = 0; i < products.size(); i++) {
//                System.out.println((i + 1) + ". " + products.get(i).getName());
//            }
//
//            // Inspect each product
//            System.out.println("Enter a number to view details of a product:");
//            System.out.println("Press 0 to exit");
//
//            // Take customer's choice and validate
//            int choice;
//            while (true) {
//                try {
//                    choice = scanner.nextInt();
//                    scanner.nextLine();
//
//                    if (choice < 0 || choice > products.size()) {
//                        System.out.printf("Please enter an integer from 0 to %d\n", products.size() - 1);
//                    } else {
//                        break;
//                    }
//                } catch (Exception e) {
//                    scanner.nextLine();
//                    System.out.printf("Please enter an integer from 0 to %d\n", products.size() - 1);
//                }
//            }
//
//            // Customer choose to go back
//            if (choice == 0) {
//                System.out.println("Going back...");
//                break;
//            }
//
//            // Customer picked a product
//            choice--;
//            Product product = products.get(choice);
//            int productIndex = choice; // the index to access in buy
//
//            // Display chosen product detail and options
//            System.out.println(product.page());
//            System.out.println("0. Go back");
//            System.out.println("1. Buy");
//            System.out.println("2. Add to cart");
//            System.out.println("3. Contact seller");
//
//            // Take user input and validate
//            while (true) {
//                try {
//                    choice = scanner.nextInt();
//                    scanner.nextLine();
//
//                    if (choice < 0 || choice > 3) {
//                        System.out.println("Please pick a number from 0 to 3 inclusively");
//                    } else {
//                        break;
//                    }
//                } catch (Exception e) {
//                    scanner.nextLine();
//                    System.out.println("Please pick a number from 0 to 3 inclusively");
//                }
//            }
//
//            // If going back
//            if (choice == 0) {
//                System.out.println("Going back...");
//            }
//
//            // If user buy item
//            else if (choice == 1) {
//                System.out.println("Enter quantity to buy (0 to cancel):");
//
//                // Take user input and validate it
//                while (true) {
//                    try {
//                        choice = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice < 0 || choice > product.getQuantity()) {
//                            System.out.printf("Please enter a number between 0 and %d\n",
//                                    product.getQuantity());
//                        } else {
//                            break;
//                        }
//                    } catch (Exception e) {
//                        scanner.nextLine();
//                        System.out.println("Invalid input, please try again.");
//                    }
//                }
//
//                // Buy if choice is not 0 (cancel)
//                if (choice != 0) {
//                    Product afterPurchase = customer.buyItem(product, choice);
//
//                    Product newProductAfterPurchase = products.get(productIndex);
//                    newProductAfterPurchase.setQuantity(Math.max(0,
//                            newProductAfterPurchase.getQuantity() - afterPurchase.getQuantity()));
//                }
//            }
//
//            // If user add item to cart
//            else if (choice == 2) {
//                System.out.println("Enter quantity to add to cart (0 to cancel):");
//
//                // Take user input and validate it
//                while (true) {
//                    try {
//                        choice = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice < 0 || choice > product.getQuantity()) {
//                            System.out.printf("Please enter an integer between 0 and %d\n",
//                                    product.getQuantity());
//                        } else {
//                            break;
//                        }
//                    } catch (Exception e) {
//                        scanner.nextLine();
//                        System.out.printf("Please enter an integer between 0 and %d\n",
//                                product.getQuantity());
//                    }
//                }
//
//                // Add product to shopping cart
//                if (choice != 0) {
//                    customer.addToShoppingCart(product, choice);
//                }
//            }
//
//            // If user want to contact seller
//            else {   //send message
//                System.out.println("Message:");
//                String message = scanner.nextLine();
//
//                if (customer.sendMessage(User.userGetterByName(product.getSeller()), message)) {
//                    System.out.println("Message sent!");
//                } else {
//                    System.out.println("Failed to send message!");
//                }
//            }
//        }
//    }
//}