import java.io.Serializable;

/**
 * Abstraction of User class with constructor and getter.
 */
public abstract class User implements Serializable {
    // For different user instance
    private String userName; // unique value per user
    private final String email; // unique value per user; Hashmap key for user
    private String password;
    private boolean isOnline;

    /**
     * Constructors have been overloaded for different situation.
     * Reading from file will use the first constructor, but user sign up will use second.
     *
     * @param userName {@link #userName}
     * @param email    {@link #email}
     * @param password {@link #password}
     */
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isOnline = false;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}