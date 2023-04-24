import java.io.*;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<String> product = new ArrayList<>();
        product.add("apple");
        product.add("pen");
        product.add("pencil");
        Store store1 = new Store("Amazon", product);

        ArrayList<String> product2 = new ArrayList<>();
        product2.add("banana");
        product2.add("eraser");
        product2.add("computer");
        Store store2 = new Store("Lazada", product2);


        ArrayList<String> inbox = new ArrayList<>();
        inbox.add("alo");
        inbox.add("dmm");


        ArrayList<Store> stores = new ArrayList<>();
        stores.add(store1);
        stores.add(store2);

        Seller seller1 = new Seller("bao2803", "phan43@purdue.edu", "Abc@1", inbox, stores);
        seller1.printSeller();
        Seller seller2 = new Seller("bao2003", "phan34@purdue.edu", "Abc@2", inbox, stores);
        seller2.printSeller();

        try {
            new File("./data/").mkdirs();
            FileOutputStream fos = new FileOutputStream("./data/SellerObjects2.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(seller1);
            oos.writeObject(seller2);
            System.out.println("Done");
            // closing resources
            oos.close();
            fos.close();

            FileInputStream is = new FileInputStream("./data/SellerObjects2.ser");
            ObjectInputStream ois = new ObjectInputStream(is);
            Seller seller1In = (Seller) ois.readObject();
            Seller seller2In = (Seller) ois.readObject();

            ois.close();
            is.close();
            seller1In.printSeller();
            System.out.println();
            seller2In.printSeller();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
