import java.util.ArrayList;

/**
 * Seller class that extends user, contains arraylist of stores the seller owns.
 */
public class Seller extends User {
    private ArrayList<Store> stores;

    public Seller(String userName, String email, String password, ArrayList<String> inbox, ArrayList<Store> stores) {
        super(userName, email, password, inbox);
        this.stores = stores;
    }

    public Seller(String userName, String email, String password) {
        super(userName, email, password);
        this.stores = new ArrayList<>();
        // pre-existing stores will be read from a file in a different method, and assigned
        // after instantiation of the object.
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    /**
     * Add new store to this seller's stores field
     *
     * @param store the store to be added
     */
    public void addStore(Store store) {
        this.stores.add(store);
    }
}