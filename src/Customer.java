import java.io.Serializable;
import java.util.ArrayList;

/**
 * Customer class that extends User, contains shoppingCart field and itemsPurchased field.
 */
public class Customer extends User implements Serializable {
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

    public void addToPurchaseHistory(Product product, int quantity) {
        this.purchaseHistory.add(String.format("Store: %s; Product: %s; Price: $%.2f; Qty: %d", product.getStoreName(), product.getName(), product.getPrice(), quantity));
    }
}