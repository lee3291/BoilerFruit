import java.util.ArrayList;
import java.io.Serializable;

public class Seller implements Serializable {
    private String userName; // unique value per user
    private String email; // Will work not work as username anymore.
    private String password;
    private ArrayList<String> inbox;
    private ArrayList<Store> stores;

    public Seller(String userName, String email, String password, ArrayList<String> inbox, ArrayList<Store> stores) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.inbox = inbox;
        this.stores = stores;
    }

    public void printSeller() {
        System.out.printf("Seller: %s|%s|%s\n", userName, email, password);
        System.out.println("Inbox");
        for (String s : inbox) {
            System.out.println(s);
        }
        for (Store s : stores) {
            s.printStore();
        }
    }
}
