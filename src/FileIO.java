import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;

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

}
