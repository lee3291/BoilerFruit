import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Store class that contains data about its products, its customers, and its history of purchased.
 */
public class Store implements Serializable {
    private final String name; // name of the store
    private final String sellerName; // the store's owner, We may need to change to object
    private double totalRevenue; // total revenue of the store.
    private ArrayList<Product> currentProducts; // the current products listing
    private ArrayList<String> saleHistory; // the sale history: item name, quantity, revenue(price), customerName
    private ArrayList<String> customerEmails; // Customers who shopped at this store.

    public Store(String name, String sellerName, double totalRevenue, ArrayList<Product> currentProducts, ArrayList<String> saleHistory,
                 ArrayList<String> customerEmails) {
        this.name = name;
        this.sellerName = sellerName;
        this.totalRevenue = totalRevenue;
        this.currentProducts = currentProducts;
        this.saleHistory = saleHistory;
        this.customerEmails = customerEmails;
    }

    public Store(String name, String sellerName) {
        this.name = name;
        this.sellerName = sellerName;
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

    public String getSellerName() {
        return sellerName;
    }

    /**
     * Add a new product to product listing; the product existed, increase the quantity by the product's quantity
     *
     * @param newProduct the product to be added
     */
    public void addProduct(Product newProduct) {
        // Check if product already exist; increase the existing product quantity if it existed
        for (Product product : currentProducts) {
            if (product.getName().equalsIgnoreCase(newProduct.getName())) {
                product.setQuantity(newProduct.getQuantity() + product.getQuantity());
                return;
            }
        }
        // Add new product to listing if the product is new
        currentProducts.add(newProduct);
    }

    /**
     * Add a list of new products to product listing; the product existed,
     * increase the quantity by the product's quantity
     *
     * @param products the array list of products to be added
     */
    public void addMultipleProducts(ArrayList<Product> products) {
        for (Product product : products) {
            addProduct(product);
        }
    }

    /**
     * Remove quantity products from product listing
     *
     * @param productName the product class product to be existed
     * @param quantity    the quantity to be removed
     */
    public boolean removeProduct(String productName, int quantity) {
        // Search currentListing for product
        int newQuantity;
        Product product;
        for (int i = 0; i < currentProducts.size(); i++) {
            product = currentProducts.get(i);
            if (product.getName().equalsIgnoreCase(productName)) {
                // Reduce the product's quantity no below than 0.
                newQuantity = Math.max(product.getQuantity() - quantity, 0);

                // New quantity is 0; remove the product from the hash map
                if (newQuantity == 0) {
                    currentProducts.remove(product);
                }
                // New quantity is larger than 0; reduce the listing quantity to the new value
                else {
                    product.setQuantity(newQuantity);
                }
                return true;
            }
        }
        // cannot remove because product does not exist
        return false;
    }

    /**
     * Remove quantity products from product listing
     *
     * @param productToRemove the product to be removed
     * @param quantity        the quantity to be removed
     */
    public boolean removeProduct(Product productToRemove, int quantity) {
        // Search currentListing for product
        int newQuantity;
        Product product;
        for (int i = 0; i < currentProducts.size(); i++) {
            product = currentProducts.get(i);
            if (product.getName().equalsIgnoreCase(productToRemove.getName())) {
                // Reduce the product's listing quantity to as much 0
                newQuantity = Math.max(product.getQuantity() - quantity, 0);

                // New quantity is 0; remove the product from the hash map
                if (newQuantity == 0) {
                    currentProducts.remove(product);
                }
                // New quantity is larger than 0; reduce the listing quantity to the new value
                else {
                    product.setQuantity(newQuantity);
                }
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

    //TODO: FIX THIS

    /**
     * Sort the input products by ascending or descending order by price
     *
     * @param products    the product ArrayList to be sorted
     * @param isAscending whether to sort in ascending or descending
     * @return the sorted ArrayList
     */
    public static ArrayList<Product> sortByPrice(ArrayList<Product> products, boolean isAscending) {
        ArrayList<Product> sorted = (ArrayList<Product>) products.clone();

        // Insertion sort ascending
        if (isAscending) {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getPrice() > curr.getPrice()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        } else {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getPrice() < curr.getPrice()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        }

        return sorted;
    }


    //TODO: Fix this

    /**
     * Sort the input products by ascending or descending order by quantity
     *
     * @param products    the product ArrayList to be sorted
     * @param isAscending whether to sort in ascending or descending
     * @return the sorted ArrayList
     */
    public static ArrayList<Product> sortByQuantity(ArrayList<Product> products, boolean isAscending) {
        ArrayList<Product> sorted = (ArrayList<Product>) products.clone();

        // Insertion sort ascending
        if (isAscending) {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getQuantity() > curr.getQuantity()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        } else {
            for (int i = 1; i < sorted.size(); i++) {
                Product curr = sorted.get(i);
                int j = i - 1;

                while (j >= 0 && sorted.get(j).getQuantity() < curr.getQuantity()) {
                    sorted.set(j + 1, sorted.get(j));
                    j = j - 1;
                }
                sorted.set(j + 1, curr);
            }
        }
        return sorted;
    }
}
