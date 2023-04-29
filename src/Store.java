import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Store class that contains data about its products, its customers, and its history of purchased.
 */
public class Store implements Serializable {
    private final String name; // name of the store
    private final String sellerEmail; // the store's owner
    private double totalRevenue; // total revenue of the store.
    private ArrayList<Product> currentProducts; // the current products listing
    private ArrayList<String> saleHistory; // the sale history: item name, quantity, revenue(price), customerName
    private ArrayList<String> customerEmails; // Customers who shopped at this store.

    public Store(String name, String sellerEmail, double totalRevenue, ArrayList<Product> currentProducts,
                 ArrayList<String> saleHistory, ArrayList<String> customerEmails) {
        this.name = name;
        this.sellerEmail = sellerEmail;
        this.totalRevenue = totalRevenue;
        this.currentProducts = currentProducts;
        this.saleHistory = saleHistory;
        this.customerEmails = customerEmails;
    }

    public Store(String name, String sellerEmail) {
        this.name = name;
        this.sellerEmail = sellerEmail;
        this.totalRevenue = 0;
        this.currentProducts = new ArrayList<>();
        this.saleHistory = new ArrayList<>();
        this.customerEmails = new ArrayList<>();
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getStoreName() {
        return name;
    }

    public ArrayList<Product> getCurrentProducts() {
        return currentProducts;
    }

    public void setCurrentProducts(ArrayList<Product> currentProducts) {
        this.currentProducts = currentProducts;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    /**
     * Add a new product to product listing; the product existed, increase the quantity by the product's quantity
     *
     * @param newProduct the product to be added
     */
    public boolean addProduct(Product newProduct) {
        // Check if product already exist; increase the existing product quantity if it existed
        for (Product product : currentProducts) {
            if (product.getName().equalsIgnoreCase(newProduct.getName())) {
                return false;
            }
        }
        // Add new product to listing if the product is new
        currentProducts.add(newProduct);
        System.out.println("Current Products: ");
        for (Product p : currentProducts) {
            System.out.println(p.toString());
            System.out.println("-----------");
        }
        return true;
    }

    /**
     * Remove a product from the current product listing
     *
     * @param productName the product class product to be existed
     * @return true if a product is removed; false if no product is removed (no matching productName)
     */
    public boolean removeProduct(String productName) {
        // Search currentListing for product
        int newQuantity;
        Product product;
        for (int i = 0; i < currentProducts.size(); i++) {
            product = currentProducts.get(i);
            if (product.getName().equalsIgnoreCase(productName)) {
                currentProducts.remove(i);
                return true;
            }
        }
        // cannot remove because product does not exist
        return false;
    }

    public ArrayList<String> getSaleHistory() {
        return saleHistory;
    }

    public void setHistory(ArrayList<String> saleHistory) {
        this.saleHistory = saleHistory;
    }

    /**
     * Add new purchased item to history
     *
     * @param saleDetail the details of the sale, including: item name, quantity, revenue(price), customerName
     */
    public void addToSaleHistory(String saleDetail) {
        saleHistory.add(saleDetail);
    }

    public void setSaleHistory(ArrayList<String> saleHistory) {
        this.saleHistory = saleHistory;
    }

    /**
     * Print the store saleHistory in the format: item name, saleQuantity, revenue(price), customerName
     *
     * @param product      the product sold.
     * @param saleQuantity how many units the product is sold. Different from the product.getQuantity().
     * @param customerName Name of customer who buys the product.
     * @return String with "itemName, saleQuantity, revenue, customerName"
     */
    public String makeSaleDetail(Product product, int saleQuantity, String customerName) {
        return String.format("%s, %d, %.2f, %s", product.getName(), saleQuantity, product.getPrice(), customerName);
    }

    public ArrayList<String> getCustomerEmails() {
        return customerEmails;
    }

    public void setCustomerEmails(ArrayList<String> customerEmails) {
        this.customerEmails = customerEmails;
    }

    /**
     * Add a new customer to the store's email list IFF the customer is new
     *
     * @param customerEmail the potential new customer email
     */
    public void addCustomerEmail(String customerEmail) {
        for (String email : customerEmails) {
            if (email.equals(customerEmail)) {
                return;
            }
        }

        customerEmails.add(customerEmail);
    }

    @Override
    public String toString() {
        return String.format("%s's %s", sellerEmail, name);
    }
}
