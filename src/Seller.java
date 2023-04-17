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

                System.out.println("In " + c.getUserName() + "'s shopping cart!\n");
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