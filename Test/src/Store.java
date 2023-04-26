import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;

public class Store implements Serializable {
    public String name;
    public ArrayList<String> products;

    public Store(String name, ArrayList<String> products) {
        this.name = name;
        this.products = products;
    }

    public void printStore() {
        System.out.println("Store: " + name);
        System.out.println("Product: ");
        for (String p : products) {
            System.out.println(p);
        }
        System.out.println("----------");
    }
}