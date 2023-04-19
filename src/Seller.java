import java.util.ArrayList;
import java.util.HashMap;

/**
 * Seller class that extends user, contains arraylist of stores the seller owns.
 */
public class Seller extends User {
    private final HashMap<String, Store> stores;

    public Seller(String userName, String email, String password, ArrayList<String> inbox, HashMap<String, Store> stores) {
        super(userName, email, password, inbox);
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
        Store.addGlobalStore(store);
    }

    /**
     * viewCustomerCarts
     * <p>
     * Goes through all the customers, finds their shoppingCarts, and views the content of all the products.
     */
    public boolean viewCustomerCarts(String customerName) {
        for (Customer c : User.getCustomers().values()) {
            if (c.getUserName().equals(customerName)) {
                // Get customer shopping cart
                ArrayList<Product> currentCart = c.getShoppingCart();
                if (currentCart == null) {
                    break;
                }

                System.out.println("In " + c.getUserName() + "'s shopping cart: \n");
                for (Product p : currentCart) {
                    System.out.println("Product name: " + p.getName());
                    System.out.println("Seller: " + p.getSeller());
                    System.out.println("Store: " + p.getStore());
                    System.out.println("Product Description: \n" + p.getDescription());
                    System.out.println("# items in cart: " + p.getQuantity());
                    System.out.println("Price: " + p.getPrice());
                    System.out.println();
                }
                return true;
            }
        }

        System.out.println("Customer does not exist!");
        System.out.println("Advise the list above");
        return false;
    }
}