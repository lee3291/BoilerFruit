import java.util.ArrayList;
import java.util.Scanner;

public class Marketplace {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating a new market object
        Market market = new Market(); // initiate new Market; read existing data if needed
        System.out.println("Welcome to Secondhand Shop!");
        System.out.println("We connect customers and sellers through a sustainable marketplace.");

        // Program
        while (true) {
            // Choosing to sign up, login, or quit
            System.out.println("""
                    Would you like to...
                    \t0.Quit
                    \t1.Sign up
                    \t2.Log in""");
            String initialState;
            while (true) {
                initialState = scanner.nextLine();
                if (!initialState.equals("0") && !initialState.equals("1") && !initialState.equals("2")) {
                    System.out.println("Please enter an integer from 0 to 2 inclusively");
                } else {
                    break;
                }
            }

            // Quit
            if (initialState.equals("0")) {
                System.out.println("Good bye!");
                break;
            }

            // Sign Up
            if (initialState.equals("1")) {
                System.out.println("----------Signing up----------");

                // Taking user input and validate it
                System.out.println("""
                        Would you like to...
                        \t0.Go back (log in)
                        \t1.Sign up customer
                        \t2.Sign up seller""");
                String userType;
                while (true) {
                    userType = scanner.nextLine();
                    if (!userType.equals("0") && !userType.equals("1") && !userType.equals("2")) {
                        System.out.println("Please enter an integer from 0 to 2 inclusively");
                    } else {
                        break;
                    }
                }

                // User choose to go back
                if (userType.equals("0")) {
                    continue;
                }

                // Customer sign up
                if (userType.equals("1")) {
                    Customer customer = (Customer) userSignUp(scanner, market, 1);
                    market.addCustomer(customer);
                    System.out.println("Account successfully created.");
                    customerFunctions(scanner, customer, market);
                }

                // Seller sign up
                else {
                    Seller seller = (Seller) userSignUp(scanner, market, 2);
                    market.addSeller(seller);
                    System.out.println("Account successfully created.");
                    sellerFunctions(scanner, seller, market);
                }
            }

            // Log in
            else {
                System.out.println("-----------Logging in-----------");

                // Taking user input and validate it
                System.out.println("""
                        Would you like to...
                        \t0.Go back (sign up)
                        \t1.Log in customer
                        \t2.Log in seller""");
                String userType;
                while (true) {
                    userType = scanner.nextLine();
                    if (!userType.equals("0") && !userType.equals("1") && !userType.equals("2")) {
                        System.out.println("Please enter an integer from 0 to 2 inclusively");
                    } else {
                        break;
                    }
                }

                // User choose to go back
                if (userType.equals("0")) {
                    continue;
                }

                // User log in as a customer
                if (userType.equals("1")) {
                    // Taking customer's email
                    System.out.println("Enter an email: ");
                    String email = scanner.nextLine();

                    // Search database for that user; exit program if user does no exit
                    User temp = market.userGetterByEmail(email);
                    if (temp == null) {
                        System.out.printf("There is no user with email %s.\n", email);
                        continue;
                    }

                    // Making sure user is a customer; exit if not
                    Customer customer;
                    if (temp instanceof Customer) {
                        customer = (Customer) temp;
                    } else {
                        System.out.println("This is not a customer email");
                        continue;
                    }

                    // Taking username and password and validate it
                    String username;
                    String password;
                    int attempt = 3; // user's attempt to log in
                    while (true) {
                        System.out.println("Enter a username: ");
                        username = scanner.nextLine();
                        System.out.println("Enter a password: ");
                        password = scanner.nextLine();

                        // Log in successfully
                        if (customer.logIn(username, password)) {
                            customerFunctions(scanner, customer, market); // a while-true loop
                            break;
                        }

                        // Fail to log in
                        attempt--;
                        System.out.printf("Username or password is incorrect. " +
                                "Please try again. Attempts: %d/3\n", attempt);
                        if (attempt == 0) {
                            System.out.println("You exceeds the attempts limit!");
                            break;
                        }
                    }
                }

                // User log in as a seller
                else {
                    // Taking seller's email
                    System.out.println("Enter an email: ");
                    String email = scanner.nextLine();

                    // Search database for that user; exit program if user does no exit
                    User temp = market.userGetterByEmail(email);
                    if (temp == null) {
                        System.out.printf("There is no user with email %s.\n", email);
                        continue;
                    }

                    // Making sure user is a seller; exit if not
                    Seller seller;
                    if (temp instanceof Seller) {
                        seller = (Seller) temp;
                    } else {
                        System.out.println("This is not a seller email");
                        continue;
                    }

                    // Taking username and password and validate it
                    String username;
                    String password;
                    int attempt = 3; // user's attempt to log in
                    while (true) {
                        System.out.println("Enter a username: ");
                        username = scanner.nextLine();
                        System.out.println("Enter a password: ");
                        password = scanner.nextLine();

                        // Log in successfully
                        if (seller.logIn(username, password)) {
                            sellerFunctions(scanner, seller, market); // a while-true loop
                            break;
                        }

                        // Log in failed
                        else {
                            attempt--;
                            System.out.printf("Username or password is incorrect. " +
                                    "Please try again. Attempts: %d/3\n", attempt);
                            if (attempt == 0) {
                                System.out.println("You exceeds the attempts limit!");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Create a new user (customer or seller)
     *
     * @param scanner for input
     * @param market  database
     * @param type    1 for customer, 2 for seller
     * @return new customer/seller with user's typed info
     */
    public static User userSignUp(Scanner scanner, Market market, int type) {
        // Get email; make sure it is in the right format
        String email;
        while (true) {
            System.out.println("Enter your email:");
            email = scanner.nextLine();

            if (!(email.contains("@") && email.contains(".") && !email.contains(","))) {
                System.out.println("Email must be in format [abc@xyz.com]\nAnd there is no \",\" allowed!");
            } else if (market.emailExisted(email)) {
                System.out.println("Email is taken by another user. Please try a different email!");
            } else {
                break;
            }
        }

        // Take in username and validate username
        System.out.println("Create a username:");
        String username = scanner.nextLine();
        while (market.userNameExisted(username) || username.length() > User.USERNAME_MAX_LENGTH) {
            if (market.userNameExisted(username)) {
                System.out.println("This username already exists, please enter another one:");
            } else {
                System.out.printf("Username cannot exceed %d characters. Please enter another one:\n",
                        User.USERNAME_MAX_LENGTH);
            }
            username = scanner.nextLine();
        }
        System.out.println("Username successfully created!");

        // Take in password and validate password
        String password;
        System.out.println("Create a password:");
        while (true) {
            System.out.printf("""
                            \tPassword must have:
                            \t\tMinimum %d
                            \t\tMaximum %d characters
                            \t\tAt least one uppercase letter
                            \t\tAt least one lowercase letter
                            \t\tAt least one number and one special character
                            """,
                    User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH);
            password = scanner.nextLine();
            if (!(password.matches(User.PASSWORD_REGEX))) {
                System.out.println("Password does not meet the requirement. Please try again!");
            } else {
                break;
            }
        }
        System.out.println("Password successfully created!");

        // Return the newly created user
        if (type == 1) {
            return new Customer(username, email, password);
        } else {
            return new Seller(username, email, password);
        }
    }

    /**
     * The seller interface
     *
     * @param scanner for input
     * @param seller  current user and role
     * @param market  for database
     */
    public static void sellerFunctions(Scanner scanner, Seller seller, Market market) {
        while (true) {
            // Menu and take command
            System.out.println("----------Main Menu----------");
            System.out.println("Welcome, " + seller.getUserName() + "!");
            System.out.println("\t0. Logout");
            System.out.println("\t1. View all stores");
            System.out.println("\t2. View stores' statistic");
            System.out.println("\t3. Create Store");
            System.out.println("\t4. View Customer Carts");
            System.out.println("\t5. View Mail Page");
            System.out.println("\t6. Edit profile");
            System.out.println("Enter a command:");
            String command = scanner.nextLine();

            // Log out; save all data
            if (command.equals("0")) {
                System.out.println("Logging out...");
                market.writeUserCredentials();
                market.writeProductInfo();
                market.writeStoreInfo();
                market.writeCustomerInfo();
                break;
            }

            // if user chooses view all stores.
            else if (command.equals("1")) {
                // Get seller's stores
                ArrayList<Store> stores = seller.getStores();
                if (stores == null || stores.isEmpty()) {
                    System.out.println("You do not have any store yet!");
                    continue;
                }

                // Display stores
                for (int i = 1; i <= stores.size(); i++) {
                    System.out.println((i) + ". " + stores.get(i - 1).getStoreName());
                }

                // Getting user's interested store
                System.out.println("Enter number to view details of the store:");
                int storeNum = -1;
                do {
                    try {
                        storeNum = scanner.nextInt();
                        scanner.nextLine();
                    } catch (Exception e) {
                        scanner.nextLine();
                        System.out.println("Please enter a valid number!");
                        continue;
                    }
                    if (storeNum <= 0) {
                        System.out.println("Enter positive integer!");
                    } else if (storeNum > stores.size()) {
                        System.out.printf("There is less than %d stores!\n", storeNum);
                    }
                } while ((storeNum <= 0) || (storeNum > stores.size()));
                Store store = stores.get(storeNum - 1);

                // Store's menu
                int action = -1;
                while (true) {
                    System.out.println(store.getStoreName() + ":");
                    System.out.println("\t0. Go back to Main menu");
                    System.out.println("\t1. Add listing");
                    System.out.println("\t2. Remove listing");
                    System.out.println("\t3. Show sales");
                    System.out.println("\t4. Import products csv");
                    System.out.println("\t5. Export products csv");

                    // Get user's input and validate
                    System.out.println("Enter a number");
                    do {
                        try {
                            action = scanner.nextInt();
                            scanner.nextLine();
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Please enter a valid number!");
                            continue;
                        }
                        if (action < 0) {
                            System.out.println("Enter positive integer!");
                        } else if (action > 6) {
                            System.out.println("Please enter a valid option!");
                        }
                    } while (action < 0 || action > 6);

                    // Going back to Main menu
                    if (action == 0) {
                        System.out.println("Going back to Main menu...");
                        break;
                    }

                    // Add listing
                    else if (action == 1) {
                        System.out.println("Enter product name:");
                        String productName = scanner.nextLine();

                        System.out.println("Enter product description:");
                        String productDescription = scanner.nextLine();

                        int productQty = -1;
                        double productPrice = -1;
                        do {
                            try {
                                System.out.println("Enter product quantity:");
                                productQty = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Enter product price:");
                                productPrice = scanner.nextDouble();
                                scanner.nextLine();

                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid number!");
                                continue;
                            }
                            if (productQty <= 0 || productPrice <= 0) {
                                System.out.println("Enter positive number!");
                            }
                        } while (productQty <= 0 || productPrice <= 0);

                        // Add listing
                        Product addProduct = new Product(productName, seller.getUserName(), store.getStoreName(),
                                productDescription, productPrice, productQty);
                        store.addListing(addProduct);
                        market.addProduct(addProduct);
                    }

                    // Remove listing.
                    else if (action == 2) {
                        // Getting Product
                        System.out.println("Enter product name: ");
                        String productName = scanner.nextLine();
                        Product removeProduct = market.productGetter(productName);
                        if (removeProduct == null) {
                            System.out.println("Product does not exist!");
                            continue;
                        }

                        // Take user input and validate it
                        System.out.println("Enter quantity to remove (0 to cancel):");
                        int quantity;
                        while (true) {
                            try {
                                quantity = scanner.nextInt();
                                scanner.nextLine();

                                if (quantity < 0 || quantity > removeProduct.getQuantity()) {
                                    System.out.printf("Please enter a number between 0 and %d\n",
                                            removeProduct.getQuantity());
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                scanner.nextLine();
                                System.out.println("Invalid input, please try again.");
                            }
                        }

                        // Remove product
                        store.removeListing(removeProduct, quantity);
                        market.removeProduct(removeProduct, quantity);
                    }

                    // Show sales
                    else if (action == 3) {
                        store.showSale();
                    }

                    // Import product csv
                    else if (action == 4) {
                        System.out.println("Enter file path:");
                        String filepath = scanner.nextLine();

                        ArrayList<Product> products = store.importCSV(filepath, market);

                        // Add listing if products is not empty
                        if (!products.isEmpty()) {
                            store.addListing(products);
                            market.addProduct(products);
                            System.out.println("Products successfully added!");
                        } else {
                            System.out.println("There is no product to add!");
                        }
                    }

                    // Export csv file
                    else {
                        System.out.println("Enter file path:");
                        String filepath = scanner.nextLine();
                        store.exportCSV(filepath);
                    }
                }
            }

            // Statistic for all stores
            else if (command.equals("2")) {
                ArrayList<Store> stores = (ArrayList<Store>) seller.getStores().clone();
                if (stores == null || stores.isEmpty()) {
                    System.out.println("You do not have any store yet!");
                    continue;
                }

                System.out.println("Do you want to sort the stores by name?");
                System.out.println("Press 1 for yes. Press any key to display the statistics without sorting");
                String ans = scanner.nextLine();

                // Sort stores if needed
                if (ans.equals("1")) {

                    System.out.println("Do you want to sort in descending order?");
                    System.out.println("Press 1 for yes. Press any key to display the statistics without sorting");
                    ans = scanner.nextLine();

                    // Ascending
                    if (ans.equals("1")) {
                        for (int i = 1; i < stores.size(); i++) {
                            Store curr = stores.get(i);
                            int j = i - 1;

                            while (j >= 0 &&
                                    Character.getNumericValue(stores.get(j).getStoreName().indexOf(0))
                                            > Character.getNumericValue(curr.getStoreName().indexOf(0))) {
                                stores.set(j + 1, stores.get(j));
                                j = j - 1;
                            }
                            stores.set(j + 1, curr);
                        }
                    }

                    // Descending
                    else {
                        for (int i = 1; i < stores.size(); i++) {
                            Store curr = stores.get(i);
                            int j = i - 1;

                            while (j >= 0 &&
                                    Character.getNumericValue(stores.get(j).getStoreName().indexOf(0))
                                            < Character.getNumericValue(curr.getStoreName().indexOf(0))) {
                                stores.set(j + 1, stores.get(j));
                                j = j - 1;
                            }
                            stores.set(j + 1, curr);
                        }
                    }
                }

                // Display statistics
                for (Store store : stores) {
                    store.showStatistic();
                }
            }

            // Create new store and add to Market class list
            else if (command.equals("3")) {
                // Take store name and make sure it is unique
                System.out.println("Enter name of new store:");
                String storeName = scanner.nextLine();
                while (market.storeNameExisted(storeName)) {
                    System.out.println("Store name existed. Please try a different name: ");
                    storeName = scanner.nextLine();
                }

                // Create new store
                Store newStore = new Store(storeName, seller.getUserName());
                market.addStore(newStore);
                seller.addStore(newStore);
                System.out.println("Store successfully created!");
            }

            // View customer shopping cart
            else if (command.equals("4")) {
                // Get list of customer
                ArrayList<Customer> allCustomers = market.getCustomers();
                if (allCustomers == null || allCustomers.isEmpty()) {
                    System.out.println("There is no customer yet. Please try again later");
                    continue;
                }

                // Display all seller's customers
                for (Store store : seller.getStores()) {
                    store.viewCustomers();
                }

                // Display chosen customer's cart
                String customerName;
                do {
                    System.out.println("Enter customer username: ");
                    System.out.println("Type QUIT to quit.");
                    customerName = scanner.nextLine();

                    if (customerName.equals("QUIT")) {
                        break;
                    }
                } while (!market.viewCustomerCarts(customerName));
            }

            // Mailing page
            else if (command.equals("5")) {
                mailPage(seller, scanner, market);
            }

            // Editing profile
            else if (command.equals("6")) {
                if (editProfile(scanner, seller, market)) {
                    System.out.println("Logging out...");
                    market.writeUserCredentials();
                    market.writeProductInfo();
                    market.writeStoreInfo();
                    market.writeCustomerInfo();
                    break;
                }
            }

            // Invalid input cover
            else {
                System.out.println("Invalid input");
            }
        }
    }

    /**
     * Customer's interface; ensure data are saved before logging out
     *
     * @param scanner  for inputs
     * @param customer current user and role
     * @param market   for database
     */
    public static void customerFunctions(Scanner scanner, Customer customer, Market market) {
        while (true) {
            // Display all available products
            System.out.println("----------Current Market Listing---------");
            for (int i = 0; i < market.getProducts().size(); i++) {
                System.out.println(market.getProducts().get(i).displayProduct());
            }

            // Display menu for customers
            System.out.println("Welcome, " + customer.getUserName() + "!");
            System.out.println("----------Main Menu----------");
            System.out.println("Do you want to...");
            System.out.println("\t0. Logout");
            System.out.println("\t1. Search by stores");
            System.out.println("\t2. Search by products");
            System.out.println("\t3. Sort products"); //sort price and quantity
            System.out.println("\t4. View Shopping Cart");
            System.out.println("\t5. View or Export purchase history");
            System.out.println("\t6. View Mail Page");
            System.out.println("\t7. View dashboard");
            System.out.println("\t8. Edit profile");

            // Take user input and validate it
            String command; // the command input from customer
            String test = "012345678";
            while (true) {
                command = scanner.nextLine();
                if (command.length() != 1 && !test.contains(command)) {
                    System.out.println("Make sure to enter an integer from 0 to 8 inclusively");
                } else {
                    break;
                }
            }

            // Log out; save all data
            if (command.equals("0")) {
                System.out.println("Logging out...");
                market.writeUserCredentials();
                market.writeProductInfo();
                market.writeStoreInfo();
                market.writeCustomerInfo();
                break;
            }

            // Customer want to search products by store
            else if (command.equals("1")) {
                ArrayList<Store> stores = market.getStores(); // get stores in market
                if (stores == null || stores.isEmpty()) { // there are no store
                    System.out.println("There are no stores yet, please come back later.");
                    System.out.println("Going back to Main menu...");
                    continue;
                }

                // Infinite loop for customer to interact with stores' products
                while (true) {
                    System.out.println("----------Stores----------");
                    // Display all stores in the market
                    for (int i = 0; i < stores.size(); i++) {
                        System.out.println((i + 1) + ". " + stores.get(i).getStoreName());
                    }
                    System.out.println("Press 0 to exit");
                    System.out.println("Enter store number to view details of the store:");

                    // Take user's choice of store and validate it
                    int storeNum = -1;
                    do {
                        try {
                            storeNum = scanner.nextInt();
                            scanner.nextLine();
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.printf("Please enter an integer from 0 to %d\n", stores.size() - 1);
                        }
                    } while (storeNum < 0 || storeNum >= stores.size());

                    // User choose to quit
                    if (storeNum == 0) {
                        System.out.println("Going back to Main menu...");
                        break;
                    }

                    // Get the chosen store
                    storeNum--;
                    Store store = stores.get(storeNum);

                    // Display store and seller info; get store's products
                    System.out.println(store.getStoreName());
                    System.out.println("Seller: " + store.getSeller());
                    ArrayList<Product> productsArrayList = store.getCurrentProducts();
                    if (productsArrayList == null || productsArrayList.isEmpty()) {
                        System.out.println("Store currently has no product! Please comeback later");
                        System.out.println("Going back to Main menu...");
                        break;
                    }

                    // User interact with products
                    System.out.println("Products:");
                    interactWithProduct(scanner, market, productsArrayList, customer);
                }
            }

            // Customer want to search product by name/description
            else if (command.equals("2")) {
                ArrayList<Product> marketProduct = market.getProducts(); // get all market products
                if (marketProduct == null || marketProduct.isEmpty()) { // no product
                    System.out.println("Market does not have any products yet, please come back later!");
                    System.out.println("Going back to Main menu...");
                    continue;
                }

                // Infinite loop for customer to search products
                while (true) {
                    System.out.println("----------Search----------");
                    System.out.println("Type the name/description of the product (QUIT to quit)");
                    String productToSearch = scanner.nextLine();

                    // User choose to quit
                    if (productToSearch.equals("QUIT")) {
                        System.out.println("Going back to Main menu...");
                        break;
                    }

                    // Search through the database for matching products; add the matched product to results
                    ArrayList<Product> results = new ArrayList<>();
                    for (Product product : marketProduct) {
                        if (product.getName().equalsIgnoreCase(productToSearch) ||
                                (product.getDescription().toLowerCase()).contains(productToSearch.toLowerCase())) {
                            results.add(product);
                        }
                    }

                    // User interact with results
                    if (!results.isEmpty()) {
                        interactWithProduct(scanner, market, results, customer);
                    } else {
                        System.out.println("No matched product!");
                    }
                }
            }

            // Sorting
            else if (command.equals("3")) {
                // Get all market products; make sure there are products listed
                ArrayList<Product> marketProduct = market.getProducts();
                if (marketProduct == null || marketProduct.isEmpty()) { // no product
                    System.out.println("There are no products in the market yet, please come back later!");
                    System.out.println("Going back to Main menu...");
                    continue;
                }

                while (true) {
                    System.out.println("Sort by?");
                    System.out.println("0. Quit\n1. Sort by price\n2. Sort by quantity");

                    // Take user sorting option and validate
                    int sortOption; // sort by price or quantity
                    while (true) {
                        try {
                            sortOption = scanner.nextInt();
                            scanner.nextLine();
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Please enter an integer from 0 to 2 inclusively.");
                            continue;
                        }

                        if (sortOption < 0 || sortOption > 2) {
                            System.out.println("Please enter an integer from 0 to 2 inclusively.");
                        } else {
                            break;
                        }
                    }

                    // Going back to Main menu
                    if (sortOption == 0) {
                        System.out.println("Going back to Main menu...");
                        break;
                    }

                    // Taking sorting order and validate
                    System.out.println("Sort order?");
                    System.out.println("0. Quit\n1. Lowest-highest\n2. Highest-lowest");
                    int sortOrder; // 1. ascending or 2. descending
                    while (true) {
                        try {
                            sortOrder = scanner.nextInt();
                            scanner.nextLine();

                            if (sortOrder < 0 || sortOrder > 2) {
                                System.out.println("Please enter an integer from 0 to 2 inclusively.");
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Please enter an integer from 0 to 2 inclusively.");
                        }
                    }

                    // Going back to Main menu
                    if (sortOrder == 0) {
                        System.out.println("Going back to Main menu...");
                        break;
                    }

                    // Actual sorting
                    ArrayList<Product> sortProducts;

                    // Ascending by price
                    if (sortOption == 1 && sortOrder == 1) {
                        sortProducts = sortByPrice(marketProduct, true);
                    }
                    // Descending by price
                    else if (sortOption == 1 && sortOrder == 2) {
                        sortProducts = sortByPrice(marketProduct, false);
                    }
                    // Ascending by quantity
                    else if (sortOption == 2 && sortOrder == 1) {
                        sortProducts = sortByQuantity(marketProduct, true);
                    }
                    // Descending by quantity
                    else {
                        sortProducts = sortByQuantity(marketProduct, false);
                    }

                    // Interact with sorted products
                    interactWithProduct(scanner, market, sortProducts, customer);
                }
            }

            // View cart
            else if (command.equals("4")) {
                ArrayList<Product> cart = customer.getShoppingCart();
                if (cart == null || cart.isEmpty()) {
                    System.out.println("Your shopping cart is empty!");
                    continue;
                }

                // Display products
                System.out.println("----------Shopping Cart----------");
                for (int i = 0; i < cart.size(); i++) {
                    System.out.println((i + 1) + ". " + cart.get(i).page());
                }

                // Inspect each product
                System.out.println("--------------------");
                System.out.println("Enter a number to view details of a product");
                System.out.println("Press 0 to exit");
                System.out.println("Press -1 to Buy All");
                System.out.println("Press -2 to Clear cart");

                // Take customer's choice and validate
                int choice;
                while (true) {
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice < -2 || choice >= cart.size()) {
                            System.out.printf("Please enter an integer from -2 to %d\n", cart.size() - 1);
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        scanner.nextLine();
                        System.out.printf("Please enter an integer from -2 to %d\n", cart.size() - 1);
                    }
                }

                // Buy all
                if (choice == -1) {
                    market.buyAllItem(customer);
                }

                // Clear cart
                else if (choice == -2) {
                    customer.clearCart();
                }

                // Display cart
                else {
                    while (true) {
                        // Display products
                        System.out.println("----------Shopping cart----------");
                        for (int i = 0; i < cart.size(); i++) {
                            System.out.println((i + 1) + ". " + cart.get(i).page());
                        }


                        // Inspect each product
                        // Take customer's choice and validate
                        while (true) {
                            try {
                                System.out.println("Enter a number to view details of a product:");
                                System.out.println("Press 0 to exit");
                                choice = scanner.nextInt();
                                scanner.nextLine();

                                if (choice < 0 || choice >= cart.size()) {
                                    System.out.printf("Please enter an integer from 0 to %d\n", cart.size() - 1);
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                scanner.nextLine();
                                System.out.printf("Please enter an integer from 0 to %d\n", cart.size() - 1);
                            }
                        }

                        // Customer choose to go back
                        if (choice == 0) {
                            System.out.println("Going back...");
                            break;
                        }

                        // Customer picked a product
                        choice--;
                        Product product = cart.get(choice);

                        // Display chosen product detail and options
                        System.out.println(product.page());
                        System.out.println("0. Go back");
                        System.out.println("1. Buy");
                        System.out.println("2. Add to cart");
                        System.out.println("3. Remove from cart");
                        System.out.println("4. Contact seller");

                        // Take user input and validate
                        while (true) {
                            try {
                                choice = scanner.nextInt();
                                scanner.nextLine();

                                if (choice < 0 || choice > 4) {
                                    System.out.println("Please pick a number from 0 to 4 inclusively");
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                scanner.nextLine();
                                System.out.println("Please pick a number from 0 to 4 inclusively");
                            }
                        }

                        // If going back
                        if (choice == 0) {
                            System.out.println("Going back...");
                        }

                        // If user buy item
                        else if (choice == 1) {
                            System.out.println("Enter quantity to buy (0 to cancel):");

                            // Take user input and validate it
                            while (true) {
                                try {
                                    choice = scanner.nextInt();
                                    scanner.nextLine();

                                    if (choice < 0 || choice > product.getQuantity()) {
                                        System.out.printf("Please enter a number between 0 and %d\n",
                                                product.getQuantity());
                                    } else {
                                        break;
                                    }
                                } catch (Exception e) {
                                    scanner.nextLine();
                                    System.out.println("Invalid input, please try again.");
                                }
                            }

                            // Buy if choice is not 0 (cancel)
                            if (choice != 0) {
                                market.buyItem(customer, product, choice);
                            }
                        }

                        // If user add item to cart
                        else if (choice == 2) {
                            System.out.println("Enter quantity to add to cart (0 to cancel):");

                            // Take user input and validate it
                            while (true) {
                                try {
                                    choice = scanner.nextInt();
                                    scanner.nextLine();

                                    if (choice < 0 || choice > product.getQuantity()) {
                                        System.out.printf("Please enter an integer between 0 and %d\n",
                                                product.getQuantity());
                                    } else {
                                        break;
                                    }
                                } catch (Exception e) {
                                    scanner.nextLine();
                                    System.out.printf("Please enter an integer between 0 and %d\n",
                                            product.getQuantity());
                                }
                            }

                            // Add product to shopping cart
                            if (choice != 0) {
                                customer.addToShoppingCart(product, choice);
                            }
                        }

                        // If user remove item from cart
                        else if (choice == 3) {
                            System.out.println("Enter quantity to remove from cart (0 to cancel):");

                            // Take user input and validate it
                            while (true) {
                                try {
                                    choice = scanner.nextInt();
                                    scanner.nextLine();

                                    if (choice < 0 || choice > product.getQuantity()) {
                                        System.out.printf("Please enter an integer between 0 and %d\n",
                                                product.getQuantity());
                                    } else {
                                        break;
                                    }
                                } catch (Exception e) {
                                    scanner.nextLine();
                                    System.out.printf("Please enter an integer between 0 and %d\n",
                                            product.getQuantity());
                                }
                            }

                            // Remove product to shopping cart
                            if (choice != 0) {
                                customer.removeFromShoppingCart(product.getName(), choice);
                            }
                        }

                        // If user want to contact seller
                        else {   //send message
                            System.out.println("Message:");
                            String message = scanner.nextLine();

                            if (customer.sendMessage(market.userGetterByName(product.getSeller()), message)) {
                                System.out.println("Message sent!");
                            } else {
                                System.out.println("Failed to send message!");
                            }
                        }
                    }
                }
            }

            // View/export purchase history
            else if (command.equals("5")) {
                ArrayList<Product> purchaseHistory = customer.getItemsPurchased();
                if (purchaseHistory == null || purchaseHistory.isEmpty()) {
                    System.out.println("Purchase history is empty");
                    continue;
                }

                // Display purchase history
                for (Product product : purchaseHistory) {
                    System.out.println(product.getName() + "    " + product.getPrice());
                }

                // Export history
                System.out.println("Do you want to export the history of purchase?");
                System.out.println("0. No\n1.Yes");

                // Take user input and validate
                int choice;
                while (true) {
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice != 0 && choice != 1) {
                            System.out.println("Enter 0 or 1");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        scanner.nextLine();
                        System.out.println("Enter 0 or 1");
                    }
                }

                if (choice == 1) {
                    System.out.println("Enter the file path: ");
                    String filePath = scanner.nextLine();
                    customer.exportPurchaseHistory(filePath);
                }
            }

            // View mail
            else if (command.equals("6")) {
                mailPage(customer, scanner, market);
            }

            // View dashboard
            else if (command.equals("7")) {
                System.out.println("Do you want to sort the dashboard in descending order?");
                System.out.println("Press 1 for descending. Press any other key for ascending order.");
                String ans = scanner.nextLine();
                customer.viewDashboard(market, ans.equals("1"));
            }

            // Edit profile
            else {
                if (editProfile(scanner, customer, market)) {
                    System.out.println("Logging out...");
                    market.writeUserCredentials();
                    market.writeProductInfo();
                    market.writeStoreInfo();
                    market.writeCustomerInfo();
                    break;
                }
            }
        }
    }

    /**
     * The customer's interaction with the current product list, i.e. a user can view a product in detail and
     * decide to buy, add to cart, or contact seller
     *
     * @param scanner  for input
     * @param market   for database
     * @param products current list of product (sorted/by store/etc)
     * @param customer current customer
     */
    public static void interactWithProduct(Scanner scanner, Market market,
                                           ArrayList<Product> products, Customer customer) {
        while (true) {
            // Display products
            System.out.println("----------Products----------");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i).page());
            }

            // Inspect each product
            System.out.println("Enter a number to view details of a product:");
            System.out.println("Press 0 to exit");

            // Take customer's choice and validate
            int choice;
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice < 0 || choice >= products.size()) {
                        System.out.printf("Please enter an integer from 0 to %d\n", products.size() - 1);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.printf("Please enter an integer from 0 to %d\n", products.size() - 1);
                }
            }

            // Customer choose to go back
            if (choice == 0) {
                System.out.println("Going back...");
                break;
            }

            // Customer picked a product
            choice--;
            Product product = products.get(choice);

            // Display chosen product detail and options
            System.out.println(product.page());
            System.out.println("0. Go back");
            System.out.println("1. Buy");
            System.out.println("2. Add to cart");
            System.out.println("3. Contact seller");

            // Take user input and validate
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice < 0 || choice > 3) {
                        System.out.println("Please pick a number from 0 to 3 inclusively");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Please pick a number from 0 to 3 inclusively");
                }
            }

            // If going back
            if (choice == 0) {
                System.out.println("Going back...");
            }

            // If user buy item
            else if (choice == 1) {
                System.out.println("Enter quantity to buy (0 to cancel):");

                // Take user input and validate it
                while (true) {
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice < 0 || choice > product.getQuantity()) {
                            System.out.printf("Please enter a number between 0 and %d\n",
                                    product.getQuantity());
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        scanner.nextLine();
                        System.out.println("Invalid input, please try again.");
                    }
                }

                // Buy if choice is not 0 (cancel)
                if (choice != 0) {
                    market.buyItem(customer, product, choice);
                }
            }

            // If user add item to cart
            else if (choice == 2) {
                System.out.println("Enter quantity to add to cart (0 to cancel):");

                // Take user input and validate it
                while (true) {
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        if (choice < 0 || choice > product.getQuantity()) {
                            System.out.printf("Please enter an integer between 0 and %d\n",
                                    product.getQuantity());
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        scanner.nextLine();
                        System.out.printf("Please enter an integer between 0 and %d\n",
                                product.getQuantity());
                    }
                }

                // Add product to shopping cart
                if (choice != 0) {
                    customer.addToShoppingCart(product, choice);
                }
            }

            // If user want to contact seller
            else {   //send message
                System.out.println("Message:");
                String message = scanner.nextLine();

                if (customer.sendMessage(market.userGetterByName(product.getSeller()), message)) {
                    System.out.println("Message sent!");
                } else {
                    System.out.println("Failed to send message!");
                }
            }
        }
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
     * Mail page interface for user
     *
     * @param user    current user (customer/seller)
     * @param scanner for input
     * @param market  for database
     */
    public static void mailPage(User user, Scanner scanner, Market market) {
        String response;
        do {
            System.out.println("----------Mailing----------");
            System.out.println("1. View Inbox");
            System.out.println("2. Send Message");
            System.out.println("3. Quit");
            response = scanner.nextLine();
            if (response.equals("1")) {
                user.readInbox();
            } else if (response.equals("2")) {
                System.out.println("Enter recipient: ");
                String receiver = scanner.nextLine();
                System.out.println("Enter Message: ");
                String message = scanner.nextLine();
                if (user.sendMessage(market.userGetterByName(receiver), message)) {
                    System.out.println("Message Sent!");
                } else {
                    System.out.println("Failed to send message!");
                    System.out.println("Recipient does not exist or is the same user type as you!");
                }
            } else if (!response.equals("3")) {
                System.out.println("Please enter valid input!");
            }
        } while (!response.equals("3"));
    }

    /**
     * Edit the current user username/password or delete the account
     *
     * @param scanner for input
     * @param user    current user and role
     * @param market  for database
     * @return false if account is NOT DELETED; true if account is DELETED
     */
    public static boolean editProfile(Scanner scanner, User user, Market market) {
        while (true) {
            System.out.println("----------Edit Profile----------");
            System.out.println("Select an action:");
            System.out.println("0. Exit");
            System.out.println("1. Change username");
            System.out.println("2. Change password");
            System.out.println("3. Delete account");

            // Take input and validate
            int action;
            while (true) {
                try {
                    action = scanner.nextInt();
                    scanner.nextLine();

                    if (action < 0 || action > 3) {
                        System.out.println("Please enter an integer from 0 to 3 inclusively");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Please enter an integer from 0 to 3 inclusively");
                }
            }

            // Exit
            if (action == 0) {
                System.out.println("Exiting...");
                return false;
            }

            // Change username
            else if (action == 1) {
                System.out.println("Current username: " + user.getUserName());

                String newUsername = scanner.nextLine();
                while (market.userNameExisted(newUsername) || newUsername.length() > User.USERNAME_MAX_LENGTH) {
                    System.out.println("Enter new username:");
                    if (market.userNameExisted(newUsername)) {
                        System.out.println("This username already exists, please enter another one:");
                    } else {
                        System.out.printf("Username cannot exceed %d characters\n", User.USERNAME_MAX_LENGTH);
                    }
                    newUsername = scanner.nextLine();
                }

                user.setUserName(newUsername);
                System.out.println("Username updated!");
            }

            // Change password
            else if (action == 2) {
                System.out.println("Current password: " + user.getPassword());

                System.out.println("Enter new password:");
                String newPassword;
                while (true) {
                    System.out.printf("""
                                    \tPassword must have:
                                    \t\tMinimum %d characters
                                    \t\tMaximum %d characters
                                    \t\tAt least one uppercase letter
                                    \t\tAt least one lowercase letter
                                    \t\tAt least one number and one special character
                                    """,
                            User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH);
                    newPassword = scanner.nextLine();
                    if (!(newPassword.matches(User.PASSWORD_REGEX))) {
                        System.out.println("Password does not meet the requirement. Please try again!");
                    } else {
                        break;
                    }
                }

                user.setPassword(newPassword);
                System.out.println("Password updated!");
            }

            // Delete account
            else {
                market.deleteUser(user);
                System.out.println("Account is being deleted...");
                return true;
            }
        }
    }
}