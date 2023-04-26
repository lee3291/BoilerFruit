public class Product {
    private final String name;
    private final String storeName; // unique storeName
    private final String description;
    private final double price;
    private int quantity; // available products in store OR item purchased

    public Product(String name, String storeName, String description, double price, int quantity) {
        this.name = name;
        this.storeName = storeName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getStore() {
        return storeName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Product name: %s\tPrice: $%.2f\tQty: %d", name, price, quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Return the current product's detail in terms of String[] that has all the product's field
     *
     * @return the String[] with the product's fields
     */
    public String[] productDetails() {
        String[] productDetail = new String[5];
        productDetail[0] = name;
        productDetail[1] = storeName;
        productDetail[2] = description;
        productDetail[3] = String.valueOf(price);
        productDetail[4] = String.valueOf(quantity);
        return productDetail;
    }
}