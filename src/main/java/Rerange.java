import java.io.*;


public class Rerange {
    public static boolean checkTemplate(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] template = {"{\"Products\":[", "", "] }"}; // Template to match

            // Read the first three lines
            String[] firstThreeLines = new String[3];
            for (int i = 0; i < 3; i++) {
                firstThreeLines[i] = reader.readLine();
            }

            // Check if the first three lines match the template
            for (int i = 0; i < 3; i++) {
                if (!firstThreeLines[i].equals(template[i])) {
                    return false;
                }
            }

            return true; // All three lines match the template
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error occurred or file not found
        }
    }
    public static void processFile(String filename, String directory) {
        try {
            // Check if the file starts with the template
            String fullPath = directory + filename;
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            boolean isValidFile = true;

            if (checkTemplate(fullPath)) {
                for (int i = 0; i < 1; i++) {
                    String line = reader.readLine();
                    if (line == null || !line.trim().equals("{\"Products\":[")) {
                        isValidFile = false;
                        break;
                    }
                }
                reader.close();

                if (!isValidFile) {
                    System.out.println(filename + " does not match the template. Skipping...");
                    return;
                }
                // Read the body content
                StringBuilder body = new StringBuilder();
                String line;
                reader = new BufferedReader(new FileReader(fullPath));
                reader.readLine(); // Skip the first line
                reader.readLine(); // Skip the second line
                reader.readLine(); // Skip the third line
                while ((line = reader.readLine()) != null) {
                    body.append(line).append("\n");

                }

                reader.close();
                // Create a new file with "X" appended to the original filename
                String newFilename = filename.substring(0, filename.lastIndexOf('.')) + "X" + filename.substring(filename.lastIndexOf('.'));
                String newPath = directory + newFilename;
                BufferedWriter writer = new BufferedWriter(new FileWriter(newPath));
                // Write the template
                writer.write("{\"Products\":[\n");
                // Write the body
                writer.write(body.toString());
                // Write the end of the template
                writer.write("] }\n");
                // Close the writer
                writer.close();

                System.out.println("File processed successfully. New file created: " + newFilename);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Specify the directory path
        String directoryPath = args[0];
//        String directoryPath = "Postman Exports/";
        // Create a File object representing the directory
        File directory = new File(directoryPath);
        // Ensure that the specified path points to a directory
        if (directory.isDirectory()) {
            // Get the list of files in the directory
            File[] files = directory.listFiles();
            // Iterate through each file and print its name
            if (files != null) {
                for (File file : files) {

                    if (file.isFile() && !file.toString().contains(".ini")) {
                        System.out.println(file);
                        processFile(file.getName(), directoryPath);
                    }
                }
            } else {
                System.out.println("The directory is empty.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }
    }
}