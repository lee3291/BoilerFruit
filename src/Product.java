public class Product {
    private String name;
    private String seller; //name of the seller selling this product
    private String store;
    private String description;
    private double price;
    private int quantity; // available products in store OR item purchased
    private String customerName;

    public Product(String name, String seller, String store, String description, double price, int quantity) {
        this.name = name;
        this.seller = seller;
        this.store = store;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.customerName = "<blank>"; // <blank> instead of empty string for better tracking.
    }

    // Overloaded constructor for a purchased product with customerName field existing.
    public Product(String name, String seller, String store, String description, double price,
                   int quantity, String customerName) {
        this.name = name;
        this.seller = seller;
        this.store = store;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.customerName = customerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String displayProduct() {
        return String.format("Product name: %s\n\tPrice: $%.2f\n\tQty: %d left\n", name, price, quantity);
    }

    public String page() { //for when the user views the product
        return String.format("%s\n\t%s\n\tPr: $%.2f\n\tQty: %d\n", name, description, price, quantity);
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String[] productDetails() {
        String[] product = new String[7];
        product[0] = name;
        product[1] = seller;
        product[2] = store;
        product[3] = description;
        product[4] = String.valueOf(price);
        product[5] = String.valueOf(quantity);
        product[6] = customerName;
        return product;
    }
}