import java.util.HashMap;

/**
 * Seller class that extends user, contains arraylist of stores the seller owns.
 */
public class Seller extends User {
    private final HashMap<String, Store> stores; // Key is store name.

    public Seller(String userName, String email, String password, HashMap<String, Store> stores) {
        super(userName, email, password);
        this.stores = stores;
    }

    public Seller(String userName, String email, String password) {
        super(userName, email, password);
        this.stores = new HashMap<>();
        // pre-existing stores will be read from a file in a different method, and assigned
        // after instantiation of the object.
    }

    public HashMap<String, Store> getStores() {
        return stores;
    }

    /**
     * Add new store to this seller's stores field
     *
     * @param store the store to be added
     */
    public void addStore(Store store) {
        stores.put(store.getStoreName(), store);
    }
}