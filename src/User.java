import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Abstraction of User class with constructor and getter.
 */
public abstract class User {
    // For ALL user instances
    private static HashMap<String, User> users; // customer + seller, all existing users.
    private static HashMap<String, Customer> customers; // all customers
    private static HashMap<String, Seller> sellers; // all sellers

    // For different user instance
    private String userName; // unique value per user
    private final String email; // unique value per user; user for Hashmap key
    private String password;
    private final ArrayList<String> inbox;

    // For signing up
    public final static int USERNAME_MAX_LENGTH = 15; // Max username length
    public final static int PASSWORD_MIN_LENGTH = 5; // Min password length
    public final static int PASSWORD_MAX_LENGTH = 15; // Max password length
    // password must have Minimum 5 and maximum 15 characters, at least one uppercase letter, one lowercase letter,
    // one number and one special character
    public final static
    String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{"
            + PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH + "}$";


    /**
     * Constructors have been overloaded for different situation.
     * Reading from file will use the first constructor, but user sign up will use second.
     *
     * @param userName {@link #userName}
     * @param email    {@link #email}
     * @param password {@link #password}
     * @param inbox    {@link #inbox}
     */
    public User(String userName, String email, String password, ArrayList<String> inbox) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.inbox = inbox;
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.inbox = new ArrayList<>();
    }

    /**
     * Return all customers in marketplace
     * @return the hash map contains all customers
     */
    public static HashMap<String, Customer> getCustomers() {
        return customers;
    }

    /**
     * Return all sellers in the marketplace
     * @return the hash map contains all sellers
     */
    public static HashMap<String, Seller> getSellers() {
        return sellers;
    }

    /**
     * Getters and Setters.
     * Notice that it hasn't been one created for password field because we would want it to be secure.
     *
     * @return {@link #userName}
     */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getInbox() {
        return inbox;
    }

    public void addInbox(String message) {
        this.inbox.add(message);
    }

    /**
     * Search and return the user that matched the given username
     * @param userName username of the user to be searched
     * @return the matched user; null if user does not exist
     */
    public static User userGetterByName(String userName) {
        User user;
        for (String email : users.keySet()) {
            user = users.get(email);
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Search and return the user that matched the given email
     * @param email email of the user to be searched
     * @return the matched user; null if user does not exist
     */
    public static User userGetterByEmail(String email) {
        return users.get(email);
    }

    /**
     * Check if the passed in userName already existed
     *
     * @param userName userName to be checked
     * @return true if username existed; false otherwise
     */
    public static boolean userNameExisted(String userName) {
        return userGetterByName(userName) != null;
    }

    /**
     * Check if the email passed in already existed
     *
     * @param email email to be checked
     * @return true if email existed; false otherwise
     */
    public static boolean emailExisted(String email) {
        return userGetterByEmail(email) != null;
    }

    /**
     * Prompt user to create a valid email
     * @param scanner for input
     * @return email that is created; null if user decided to stop
     */
    public static String createEmail(Scanner scanner) {
        String email;
        while (true) {
            System.out.println("Enter your email (QUIT to cancel sign up):");
            email = scanner.nextLine();

            // User want to cancel the signup process
            if (email.equals("QUIT")) {
                return null;
            }

            // Email is not valid
            else if (!(email.contains("@") && email.contains(".") && !email.contains(","))) {
                System.out.println("Email must be in format [abc@xyz.com]\nAnd there is no \",\" allowed!\n");
            }

            // Email existed
            else if (emailExisted(email)) {
                System.out.println("Email is taken by another user. Please try a different email!\n");
            }

            // Email is good
            else {
                System.out.println("Email is good to go!\n");
                return email;
            }
        }
    }

    /**
     * Prompt user to create a valid username
     * @param scanner for input
     * @return the username created; null if user decided to stop
     */
    public static String createUsername(Scanner scanner) {
        System.out.println("Create a username (QUIT to cancel signup):");
        String username = scanner.nextLine();
        while (userNameExisted(username) || username.length() > User.USERNAME_MAX_LENGTH || username.equals("QUIT")) {
            // User want to cancel the signup process
            if (username.equals("QUIT")) {
                return null;
            }

            // Username existed
            else if (userNameExisted(username)) {
                System.out.println("""
                        This username already exists!

                        Please enter another one (QUIT to cancel signup):""");
            }

            // Username is too long
            else {
                System.out.printf("""
                                Username cannot exceed %d characters.

                                Please enter another one (QUIT to cancel signup):
                                """,
                        User.USERNAME_MAX_LENGTH);
            }
            username = scanner.nextLine();
        }

        System.out.println("Username is good to go!\n");
        return username;
    }

    /**
     * Prompt user to create a valid password
     * @param scanner for input
     * @return the password created; null if user decided to QUIT
     */
    public static String createPassword(Scanner scanner) {
        String password; // 1st entered password
        String password2nd; // 2nd entered password (to check against the 1st)
        while (true) {
            // Make sure password meet requirement
            while (true) {
                System.out.println("Create a password (QUIT to cancel):");
                System.out.printf("""
                            \tPassword must have:
                            \t\tMinimum %d characters
                            \t\tMaximum %d characters
                            \t\tAt least one UPPERCASE letter
                            \t\tAt least one lowercase letter
                            \t\tAt least one number and one special character
                            """,
                        User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH);
                password = scanner.nextLine();

                // User want to cancel signup
                if (password.equals("QUIT")) {
                    return null;
                }

                // Password is not valid
                else if (!(password.matches(User.PASSWORD_REGEX))) {
                    System.out.println("Password does not meet the requirement. Please try again!\n");
                }

                // Password is good
                else {
                    System.out.println("Password is good to go!\n");
                    break;
                }
            }

            // Retype password
            while (true) {
                System.out.println("Please enter your password again: ");
                System.out.println("QUIT to cancel signup; 0 to choose a new password");
                password2nd = scanner.nextLine();

                // User want to cancel signup
                if (password2nd.equals("QUIT")) {
                    return null;
                }

                // User want to type a new password
                else if (password2nd.equals("0")) {
                    break;
                }

                // User failed to retype password
                else if (!password2nd.equals(password)) {
                    System.out.println("Your password does not matched! Please try again!\n");
                }

                else {
                    System.out.println("Password matched! Good to go!\n");
                    return password;
                }
            }
        }
    }

    /**
     * Create a new user (customer or seller) and add the new user to its corresponding hash map
     *
     * @param scanner for input
     * @param type    1 for customer, 2 for seller
     * @return new customer/seller with user's typed info; null if user exit during the process
     */
    public static User userSignUp(Scanner scanner, int type) {
        // Create email
        String email = createEmail(scanner);
        if (email == null) {
            return null;
        }

        // Create username
        String username = createUsername(scanner);
        if (username == null) {
            return null;
        }

        // Take in password and validate password
        String password = createPassword(scanner);
        if (password == null) {
            return null;
        }

        // Return the newly created user; added to its corresponding hashmap
        if (type == 1) {
            Customer newCustomer = new Customer(username, email, password);
            users.put(email, newCustomer);
            customers.put(email, newCustomer);
            return newCustomer;
        } else {
            Seller newSeller = new Seller(username, email, password);
            users.put(email, newSeller);
            sellers.put(email, newSeller);
            return new Seller(username, email, password);
        }
    }

    /**
     * Find a user with email/username matched userId, checked if password matched
     * @param userID email or username
     * @param password password for the current user
     * @return matched User if password matched; null if there is no user with userID or userID does not match password
     */
    public static User userLogIn(String userID, String password) {
        User user; // logging in user

        // Passed in userID is username
        if ((user = userGetterByName(userID)) != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }

            else {
                System.out.println("Password is incorrect!");
                return null;
            }
        }

        // Passed in userID is email
        else if ((user = userGetterByEmail(userID)) != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }

            else {
                System.out.println("Password is incorrect!");
                return null;
            }
        }

        // No matched username or email
        else {
            System.out.println("No user found!");
            return null;
        }
    }

    /**
     * sendMessage method
     * Checks if this user is trying to message a user of the same class. If so, returns false.
     * If they're of different class, i.g: customer -> seller, or seller -> customer,
     * Sends message to user by adding the "sender's name & email + message" to the receiver's inbox arraylist.
     * Also adds this message to the sender's messages sent field.
     *
     * @param receiver is the user that receives the message. Use userGetter() method from Market class.
     * @param message  is the message to be sent.
     * @return true if message is sent without exception,
     * false if the receiver is the same class of the sender,
     * or NullPointerException occurs because receiver does not exist.
     */
    public boolean sendMessage(User receiver, String message) {
        if (this instanceof Customer && receiver instanceof Customer) {
            return false;
        }
        if (this instanceof Seller && receiver instanceof Seller) {
            return false;
        }
        String messageReformat = String.format("%s\n%s\n\n%s\n", this.getUserName(), this.getEmail(), message);
        try {
            receiver.addInbox(messageReformat);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * readInbox method
     * prints contents of inbox
     */
    public void readInbox() {
        for (String s : this.inbox) {
            System.out.println(s);
        }
    }

    /**
     * Mail page interface for user
     *
     * @param scanner for input
     */
    public void mailPage(Scanner scanner) {
        String response;
        do {
            System.out.println("----------Mailing----------");
            System.out.println("1. View Inbox");
            System.out.println("2. Send Message");
            System.out.println("3. Quit");
            response = scanner.nextLine();
            if (response.equals("1")) {
                this.readInbox();
            } else if (response.equals("2")) {
                System.out.println("Enter recipient email: ");
                String receiver = scanner.nextLine();
                System.out.println("Enter Message: ");
                String message = scanner.nextLine();
                if (this.sendMessage(userGetterByEmail(receiver), message)) {
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
     * writeUserCredentials
     * <p>
     * Writes every user credentials information into the userCredentials.txt file.
     * Reflects newly added users to the file.
     */
    public static void writeUserCredentials() {
        // i.g: <customer, student1, student1@purdue.edu, zxcv1243>
        try (PrintWriter pw = new PrintWriter(new FileWriter("userCredentials.txt"))) {
            for (String customerEmail : customers.keySet()) {
                Customer customer = customers.get(customerEmail);
                String line = String.format("customer, %s, %s, %s", customer.getUserName(), customerEmail,
                        customer.getPassword());
                pw.println(line);
            }
            for (String sellerEmail : sellers.keySet()) {
                Seller seller = sellers.get(sellerEmail);
                String line = String.format("seller, %s, %s, %s", seller.getUserName(), sellerEmail,
                        seller.getPassword());
                pw.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: ");
            e.printStackTrace();
        }

        System.out.println("Users credential is written!");
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
    public static void readUserCredentials() {
        // reads info from file and creates new User objects, sorts them into customer and sellers.
        users = new HashMap<>();
        customers = new HashMap<>();
        sellers = new HashMap<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("userCredentials.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] temp = line.split(", ");
                for (int i = 0; i < temp.length; i++) {
                    String trimmer = temp[i].trim();
                    temp[i] = trimmer;
                }
                if (temp[0].equals("customer")) { // create new customer object
                    Customer customer = new Customer(temp[1], temp[2], temp[3]);
                    customers.put(temp[2], customer);
                    users.put(temp[2], customer);
                } else if (temp[0].equals("seller")) { // create new seller object
                    Seller seller = new Seller(temp[1], temp[2], temp[3]);
                    sellers.put(temp[2], seller);
                    users.put(temp[2], seller);
                }
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) {
            // first time running, ignore error and do nothing
            System.out.println("File not found credential!"); // TODO: remove after debugging
        } catch (IOException e) {
            System.out.println("Failed to load User info files!");
            e.printStackTrace();
        }

        System.out.println("Users credential is loaded!");
    }

    /**
     * writeUserInfo
     * <p>
     * Writes user information by creating new files, or overwriting pre-existing user files.
     * Files are named after user's username.
     * <p>
     * <p>
     * File structure:
     * <inbox>
     * message1
     * message2
     * message3
     * ...
     * </inbox>
     * <shoppingCart>
     * item1
     * item2
     * item3
     * ...
     * </shoppingCart> (the following only apply to customer)
     * <itemsPurchased>
     * item1
     * item2
     * item3
     * ...
     * </itemsPurchased>
     */
    public static void writeUserInfo() {
        for (String email : users.keySet()) {
            User current = users.get(email);
            try (PrintWriter pw = new PrintWriter(new FileWriter(email + ".txt"))) {
                pw.println("<inbox>");
                for (int i = 0; i < current.getInbox().size(); i++) {
                    pw.println(current.getInbox().get(i));
                }
                pw.println("</inbox>");

                if (current instanceof Customer customer) {
                    pw.println("<shoppingCart>");
                    for (Product product : customer.getShoppingCart()) {
                        String[] detail = product.productDetails();
                        for (int i = 0; i < detail.length - 1; i++) {
                            pw.print(detail[i] + "_");
                        }
                        pw.print(detail[detail.length - 1]);
                    }
                    pw.println("</shoppingCart>");

                    pw.println("<itemsPurchased>");
                    for (Product product : customer.getItemsPurchased()) {
                        String[] detail = product.productDetails();
                        for (int i = 0; i < detail.length - 1; i++) {
                            pw.print(detail[i] + "_");
                        }
                        pw.print(detail[detail.length - 1]);
                    }
                    pw.println("</itemsPurchased>");
                }
            } catch (IOException e) {
                System.out.println("Failed to write user files!");
                e.printStackTrace();
            }
        }

        System.out.println("User data is written!");
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
    public static void readUserInfo() { // will work for both customer and seller
        for (String email : users.keySet()) {
            User currentUser = users.get(email);
            try (BufferedReader bfr = new BufferedReader(new FileReader(email + ".txt"))) {
                String line = bfr.readLine();

                // Read inbox
                if (line.equals("<inbox>")) {
                    line = bfr.readLine();
                    while (!line.equals("</inbox>")) {
                        currentUser.addInbox(line);
                        line = bfr.readLine();
                    }
                    line = bfr.readLine(); // advance 1 more line (to escape the </inbox> line)
                } else { // TODO: remove after finish debugging
                    System.out.println("Something is wrong inbox");
                    System.out.println(line);
                    return;
                }

                // Line is not null when user is customer; continue reading
                if (line != null) {
                    Customer customer = (Customer) currentUser;
                    String[] temp;
                    if (line.equals("<shoppingCart>")) {
                        line = bfr.readLine();
                        while (!line.equals("</shoppingCart>")) {
                            temp = line.split("_");
                            customer.addToShoppingCart(new Product(temp[0], temp[1], temp[2], temp[3],
                                    Double.parseDouble(temp[4]), Integer.parseInt(temp[5]), temp[6]));
                            line = bfr.readLine();
                        }
                        line = bfr.readLine(); // advance 1 more line (to escape the </shoppingCart> line)
                    } else { // TODO: remove after finish debugging
                        System.out.println("Something is wrong cart");
                        System.out.println(line);
                        return;
                    }

                    if (line.equals("<itemsPurchased>")) {
                        line = bfr.readLine();
                        while (!line.equals("</itemsPurchased>")) {
                            temp = line.split("_");
                            customer.addToItemsPurchased(new Product(temp[0], temp[1], temp[2], temp[3],
                                    Double.parseDouble(temp[4]), Integer.parseInt(temp[5]), temp[6]));
                            line = bfr.readLine();
                        }
                        line = bfr.readLine(); // advance 1 more line (to escape the </itemsPurchased> line)
                    } else { // TODO: remove after finish debugging
                        System.out.println("Something is wrong history");
                        System.out.println(line);
                        return;
                    }

                    if (line == null) {
                        System.out.println("User data is loaded!");
                    } else { // TODO: remove after finish debugging
                        System.out.println("Something is wrong out");
                        System.out.println(line);
                    }
                }
                return;
            } catch (FileNotFoundException e) { // initial run
                System.out.println("File not found readUserInfo!"); // TODO: remove after finish debugging
                break;
            } catch (IOException e) {
                System.out.println("Failed to load customer files!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Edit the current user username/password or delete the account
     *
     * @param scanner for input
     * @return false if account is NOT DELETED; true if account is DELETED
     */
    public boolean editProfile(Scanner scanner) {
        while (true) {
            System.out.println("----------Edit Profile----------");

            // Take input and validate
            String action;
            while (true) {
                System.out.println("Select an action:");
                System.out.println("\t0. Exit");
                System.out.println("\t1. Change username");
                System.out.println("\t2. Change password");
                System.out.println("\t3. Delete account");
                action = scanner.nextLine();

                if (!action.matches("[0-3]")) {
                    System.out.println("Please enter an integer from 0 to 3 inclusively");
                } else {
                    break;
                }
            }

            // Exit
            if (action.equals("0")) {
                System.out.println("Going back to Main menu...");
                return false;
            }

            // Change username
            else if (action.equals("1")) {
                System.out.println("Current username: " + this.getUserName());

                // Get new username
                String newUsername = createUsername(scanner);
                if (newUsername == null) {
                    System.out.println("Going back to Main menu...");
                    return false;
                }

                // Set new username
                this.setUserName(newUsername);
                System.out.println("Username updated!");
            }

            // Change password
            else if (action.equals("2")) {
                System.out.println("Current password: " + this.getPassword());

                // Get new password
                String newPassword = createPassword(scanner);
                if (newPassword == null) {
                    System.out.println("Going back to Main menu...");
                    return false;
                }

                // Set new password
                this.setPassword(newPassword);
                System.out.println("Password updated!");
            }

            // Delete account
            else {
                // User is customer
                if (this instanceof Customer) {
                    customers.remove(this.getUserName());
                }

                // User is seller
                else {
                    sellers.remove(this.getUserName());
                }

                // Remove from users and return
                users.remove(this.getUserName());
                System.out.println("Account is being deleted...");
                return true;
            }
        }
    }
}