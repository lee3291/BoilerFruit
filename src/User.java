import java.util.ArrayList;

/**
 * Abstraction of User class with constructor and getter.
 */
public abstract class User {
    private String userName; // I think we need a separate userName. This will be unique. Due to requirements,
    // we will keep the email field.
    private String email; // Will work not work as username anymore.
    private String password;
    private ArrayList<String> inbox;
    public final static int USERNAME_MAX_LENGTH = 15; // Max username length
    public final static int PASSWORD_MIN_LENGTH = 5; // Min password length
    public final static int PASSWORD_MAX_LENGTH = 15; // Max password length
    public final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{"
            + PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH + "}$";
    // password must have Minimum 5 and maximum 10 characters, at least one uppercase letter, one lowercase letter,
    // one number and one special character

    // May need functionality to keep track of the order that the messages were sent and received.

    //TODO: Users are able to create delete or edit accounts. -> main method


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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { // Added for sign up
        this.password = password;
    }

    public ArrayList<String> getInbox() {
        return inbox;
    }

    public void setInbox(ArrayList<String> inbox) {
        this.inbox = inbox;
    }

    public void addInbox(String message) {
        this.inbox.add(message);
    }

    /**
     * LogIn method
     *
     * @param userName userName of the person trying to log in.
     * @param password password of the person trying to log in.
     * @return compares if they're both equal and returns boolean.
     */
    public boolean logIn(String userName, String password) {
        return (userName.equals(this.userName)) && (password.equals(this.password));
    }

    public boolean usernameExists(String userName) { // Added for sign up
        return (userName.equals(this.userName));
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
}