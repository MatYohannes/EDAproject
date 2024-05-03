import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class DirectoryFiler {

    /**
     * Counts the number of files in a directory that start with a given prefix.
     *
     * @param directoryPath The path of the directory to search.
     * @param prefix        The prefix to match file names against.
     * @return The number of files that match the prefix.
     */
    public static int fileCounter(String directoryPath, String prefix) {
        int count = 0;

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(FileSystems.getDefault().getPath(directoryPath))) {
            // Iterate through the directory stream
            for (Path filePath : dirStream) {
                String fileName = filePath.getFileName().toString();
                if (fileName.startsWith(prefix)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Creates a JSON file containing the names of files in a directory that start with a given prefix.
     *
     * @param directoryPath The path of the directory to search.
     * @param prefix        The prefix to match file names against.
     * @return The name of the created JSON file.
     */
    public static String createJSONFileWithPrefix(String directoryPath, String prefix) {
        int fileCount = 1;
        JSONArray jsonArray = new JSONArray();
        String jsonFileName = null;

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(FileSystems.getDefault().getPath(directoryPath))) {
            // Iterate through the directory stream
            for (Path filePath : dirStream) {
                String fileName = filePath.getFileName().toString();
                if (fileName.startsWith(prefix)) {
                    // Create JSON object with file name
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("filename ", fileName);
                    jsonArray.put(jsonObject);
                    fileCount++;
                }
            }

            // Create new JSON file with the concatenated prefix and file count
            jsonFileName = prefix + " " + fileCount + ".json";
            try (FileWriter fileWriter = new FileWriter(directoryPath+ "/" + jsonFileName)) {
                jsonArray.write(fileWriter);
            }

            System.out.println("JSON file created: " + jsonFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFileName.substring(0,jsonFileName.length() - 5);
    }

    /**
     * Retrieves the names of JSON files in a directory.
     *
     * @param directoryPath The path of the directory to search.
     * @return A list of names of JSON files in the directory.
     */
    public static List<String> getFileNamesInDirectory(String directoryPath) {
        List<String> matchingFileNames = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".json".toLowerCase())) {
                    matchingFileNames.add(file.getName());
                }
            }
        }
        return matchingFileNames;
    }
}

