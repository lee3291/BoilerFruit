import java.util.ArrayList;

/**
 * Customer class that extends User, contains shoppingCart field and itemsPurchased field.
 */
public class Customer extends User {
    private ArrayList<String> purchaseHistory; // Each string will contain information about one purchase.
    // item name, quantity, price, store,

    public Customer(String userName, String email, String password, ArrayList<String> purchaseHistory) {
        super(userName, email, password);
        this.purchaseHistory = purchaseHistory;
    }

    public Customer(String userName, String email, String password) {
        super(userName, email, password);
        this.purchaseHistory = new ArrayList<>();
    }

    public ArrayList<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void addToPurchaseHistory(String purchaseInfo) { // A method in product will generate the string.
        this.purchaseHistory.add(purchaseInfo);
    }
}