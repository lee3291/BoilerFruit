import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    //TODO: Fix this
    /**
     * Export purchase history into a CSV file at filePath
     *
     * @param filePath the path to the file to be created
     */
    public boolean exportPurchaseHistory(String filePath) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (Product product : itemsPurchased) {
                String[] entries = product.productDetails();
                lines.add(entries);
            }

            // Write to CSV
            writer.writeAll(lines);
        } catch (FileNotFoundException e) { // Invalid file path error
            System.out.println("Invalid file path!");
            e.printStackTrace();
            return false;
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }


    //TODO: FIX EVERYTHING!!!

    /**
     * Import {@link #currentProducts} from the specified filePath
     *
     * @param filePath the filePath to the CSV file for importing product
     * @return an array list of products imported from the CSV file; will be empty if filePath is invalid
     */
    public ArrayList<Product> importCSV(String filePath) {
        ArrayList<Product> products = new ArrayList<>();

        List<String[]> strProduct;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).build()) {
            strProduct = reader.readAll(); // read all rows from CSV files
            for (String[] row : strProduct) { // loop through each row and create a new product per row
                Product curr = new Product(
                        row[0], // store name
                        row[1], //seller's name
                        row[2], //product's store
                        row[3], // description
                        Double.parseDouble(row[4]), // price
                        Integer.parseInt(row[5]) // quanity
                );
                addListing(curr);
            }
        } catch (FileNotFoundException e) { // Invalid file path error
            System.out.println("Invalid file path!");
            return products;
        } catch (Exception e) { // All other errors
            System.out.printf("Failed to import products due to: %s\n", e.getMessage());
            return products;
        }

        System.out.printf("Products are imported from\n\t%s\n", filePath);
        return products;
    }

    /**
     * Export {@link #currentProducts} to the specified filePath
     *
     * @param filePath the filePath to the CSV file for exporting product
     * @return true if success; false otherwise
     */
    public boolean exportCSV(String filePath) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath))
                .withSeparator(',')
                .build()) {

            // Create a String array of entries
            ArrayList<String[]> lines = new ArrayList<>();
            for (Product product : currentProducts) {
                String[] entries = product.productDetails();
                lines.add(entries);
            }

            // Write to CSV
            writer.writeAll(lines);
        } catch (FileNotFoundException e) { // Invalid file path error
            System.out.println("Invalid file path!");
            e.printStackTrace();
            return false;
        } catch (Exception e) { // All other errors
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
