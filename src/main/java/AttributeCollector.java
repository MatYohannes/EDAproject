import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This code essentially goes through a collection of JSON files, extracts certain attribute values from them, and prints
 * out a list of unique attributes found across all files.
 */
public class AttributeCollector {

    public static void main(String[] args) {

// Category file is passed as an inline argument
        String categoryFolder = args[0];

        // Directory on server where files are found
//        String directory = "/root/EDAProject/Postman Exports/";
        String directory = "Postman Exports/";

        List<String> filesInDirectory = DirectoryFiler.getFileNamesInDirectory(directory + categoryFolder);
//        System.out.println("Directory size: " + filesInDirectory.size());
        String KEYWORD;
        String filePath;
        HashSet<String> attributes = new HashSet<>();

        // Iterating through directory
        for (String s : filesInDirectory) {
            KEYWORD = s;
            // Creating file path
            filePath = directory + categoryFolder + "/" + KEYWORD;

//            System.out.println("File read: " + KEYWORD);
            // Map to store parameters for each capacitor
            Map<String, String> parametersList = new LinkedHashMap<>();

            // JSON Parser for reading the input file
            JSONParser parser = new JSONParser();
            JSONArray products;

            try {
                // Reading JSON file
                Object obj = parser.parse(new FileReader(filePath));
                JSONObject jsonObject = (JSONObject) obj;

                // Getting the array of products

                products = (JSONArray) jsonObject.get("Products");
                Iterator iteratorProducts = products.iterator();

                int productIndexing = 0;

                // Iterating through each product
                while (iteratorProducts.hasNext()) {
                    Object a = iteratorProducts.next();
                    String b = a.toString().substring(0,1);
                    // Checking to see if there is an error value that starts with a number is in file.
                    // If so, skip it.
                    if (b.matches(".*\\d+.*")) {
//                        System.out.println("ERRORS");
                        break;
                    }
                    JSONObject temp = (JSONObject) a;

//                    productIndexing++;
                    String productsTempName = "Products" + productIndexing;

                    JSONArray temp22 = (JSONArray) temp.get(productsTempName);

                    boolean nullCheck = true;
                    // If there was an error with assigning values to the key "Products" + # due to not existing
                    // the code below will increment the productIndexing to the next array
                    do {
                        if (temp22 == null) {
                            productIndexing++;
                            if (productIndexing == 2000) {
                                break;
                            }
                            productsTempName = "Products" + productIndexing;
                            temp22 = (JSONArray) temp.get(productsTempName);
                        }
                        else {
                            nullCheck = false;
                        }
                    } while (nullCheck);

                    Iterator temp33 = temp22.iterator();

                    while (temp33.hasNext()) {
                        JSONObject temp44 = (JSONObject) temp33.next();
                        // Extracting parameters for each capacitor
                        JSONArray parameters = (JSONArray) temp44.get("Parameters");
                        Iterator iteratorParameters = parameters.iterator();

                        // Iterating through each parameter
                        while (iteratorParameters.hasNext()) {
                            JSONObject temp3 = (JSONObject) iteratorParameters.next();

                            // Extracting parameter attributes
                            String ParameterText = (String) temp3.get("ParameterText");

                            // Adding parameters to the parameters list
                            parametersList.put(ParameterText, null);
                        }
                        for (Map.Entry<String, String> entry : parametersList.entrySet()) {
                            attributes.add(entry.getKey());
                        }
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        // Printing out attribute set
        for (String str: attributes) {
            System.out.println(str);
        }
    }
}