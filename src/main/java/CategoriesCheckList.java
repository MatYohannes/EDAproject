import java.io.*;

public class CategoriesCheckList {

    public static String findCategory(String filePath) {
        String value = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith("X")) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0) {
                        value = parts[0].trim();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void categoryComplete(String filePath, String searchString) {
        try {
            // Input file path

            File inputFile = new File(filePath);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the search string and ends with "X"
                if (line.contains(searchString) && line.endsWith("X")) {
                    // Replace the last character with "O"
                    line = line.substring(0, line.length() - 1) + "O";
                }
                // Write the modified line to the temporary file
                writer.write(line + System.getProperty("line.separator"));
            }
            // Close readers and writers
            writer.close();
            reader.close();

            // Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete the original file.");
                return;
            }

            // Rename the temporary file to the original file name
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temporary file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}