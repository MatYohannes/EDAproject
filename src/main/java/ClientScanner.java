import java.io.*;

/**
 * The ClientScanner class provides methods for managing client keys.
 * It includes functionality to find free client keys, mark used client keys, and check if the client list is complete.
 */
public class ClientScanner {

    /**
     * Finds a free client key from the provided file path.
     *
     * @param filePath The path to the file containing client keys.
     * @return An array containing the client ID and client secret of the free client key.
     */
    public static String[] findFreeClient(String filePath) {

        String[] clientHolder = new String[2]; // Array to hold client ID and client secret
        String clientID;
        String clientSecret;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith("X")) { // Check if the line ends with "X" to identify a free client key
                    String[] parts = line.split("\\|");

                    if (parts.length > 0) {
                        clientID = parts[0].trim();
                        clientSecret = parts[1].trim();

                        clientHolder[0] = clientID;
                        clientHolder[1] = clientSecret;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientHolder;
    }

    /**
     * Marks a client key as used in the client key list file.
     *
     * @param filePath          The path to the client key list file.
     * @param completedClientKey The completed client key to be marked as used.
     */
    public static void clientUsed(String filePath, String completedClientKey) {
        try {
            // Input file path

            File inputFile = new File(filePath);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the search string and ends with "X"
                if (line.contains(completedClientKey) && line.endsWith("X")) {
                    // Replace the last character with "O"
                    line = line.substring(0, line.length() - 1) + "O"; // Mark the client key as used by changing "X" to "O"
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

    /**
     * Checks if the client list is complete.
     *
     * @param filePath The path to the client key list file.
     * @return True if the client list is complete (all client keys are used), false otherwise.
     */
    public static boolean clientListComplete(String filePath) {
        boolean check = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the last character of the line is "O"
                if (line.trim().endsWith("X")) {  // Check if any line ends with "X" indicating a free client key
                    check = false;
                    break; // Exit the loop if "O" is found in any line
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }
        return check;
    }
}
