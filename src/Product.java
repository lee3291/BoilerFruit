import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private final String storeName; // unique storeName
    private final String sellerEmail; // the seller's email of the product
    private String description;
    private double price;
    private int quantity; // available products in store OR item purchased

    public Product(String name, String storeName, String sellerEmail, String description, double price, int quantity) {
        this.name = name;
        this.storeName = storeName;
        this.sellerEmail = sellerEmail;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Store: %s\nProduct: %s\nPrice: $%.2f\nQty: %d\n", storeName, name, price, quantity);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
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
        String[] productDetail = new String[6];
        productDetail[0] = name;
        productDetail[1] = storeName;
        productDetail[2] = sellerEmail;
        productDetail[3] = description;
        productDetail[4] = String.valueOf(price);
        productDetail[5] = String.valueOf(quantity);
        return productDetail;
    }
}