import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * This class reads JSON files containing product information,
 * processes the data, and inserts it into a database. The category is passed as an inline argument.
 * It iterates through each file in a given directory, extracts relevant information, maps it to
 * appropriate headers, and then inserts it into the database tables using JDBC.
 */

public class JSONFileReader {

    /**
     * Main method to read JSON files, process data, and insert into a database.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        // Category file is passed as an inline argument
        String categoryFolder = args[0]; // Category name is passed, must mactch DigiKey category name.
        String sqlUploadChoice = args[1]; // for sql uploading choices are membership/member or characteristic/character

        // Directory on server where files are found
        String directory = "/root/EDAProject/Postman Exports/";
//        String directory = "Postman Exports/";

        // DirectoryFiler: provides methods for working with directories and files
        // getFileNamesInDirectory: Retrieves the names of JSON files in a directory.
        List<String> filesInDirectory = DirectoryFiler.getFileNamesInDirectory(directory + categoryFolder);

        System.out.println("Directory size: " + filesInDirectory.size());
        String KEYWORD;
        String filePath;
        String baseProductName = "null";
        Long baseProductId = (long) 0;
        String baseProdName;
        Long baseProdId;

        // Iterating through directory
        for (String s : filesInDirectory) {
            KEYWORD = s;
            // Creating file path
            filePath = directory + categoryFolder + "/" + KEYWORD;

            System.out.println("File read: " + KEYWORD);

            // List to store each item per row
            List<Object[]> categoryTable = new ArrayList<>();

            // Map to store parameters for each item
            Map<String, String> parametersList = new LinkedHashMap<>();

            // Array to store header values
            String[] header;

            // CategoryHeaders: provides a method for selecting header names based on a given category.
            // seelctCatagory: selects and returns an array of header names based on the given category.
            header = CategoryHeaders.selectCategory(categoryFolder);

            // Converting String[] to List<String>
            List<String> headerList = new ArrayList<>();
            for (String item : header) {
                headerList.add(item);
            }

            // Inserting item attribute names into parametersList with null as paired value
            // null will be replaced by appropriate value
            for (String value : header) {
                parametersList.put(value, null);
            }

            // JSON Parser for reading the input file
            JSONParser parser = new JSONParser();

            // Structure of json file is as such:
            // JSONObject called "Products" contains multiple JSONArrays which are label "Products" + #
            JSONArray products;

            try {
                // Reading JSON file
                Object obj = parser.parse(new FileReader(filePath));
                JSONObject jsonObject = (JSONObject) obj;

                // Getting the JSONArrays of products

                products = (JSONArray) jsonObject.get("Products");
                Iterator iteratorProducts = products.iterator();

                int productIndexing = 0;

                // Iterating through each JSONArray
                while (iteratorProducts.hasNext()) {
                    Object a = iteratorProducts.next();
                    String b = a.toString().substring(0, 1);
                    // Checking to see if there is an error value that starts with a number is in file.
                    // If so, skip it.
                    if (b.matches(".*\\d+.*")) {
                        break;
                    }
                    JSONObject temp = (JSONObject) a;

                    // Reading and storing the Products with assigned number
                    // Starting with number 0
                    String productsTempName = "Products" + productIndexing;

                    // Getting the Products# if present
                    JSONArray temp22 = (JSONArray) temp.get(productsTempName);

                    boolean nullCheck = true;
                    // If there was an error with assigning values to the key "Products" + # due to not existing
                    // the code below will increment the productIndexing to the next array
                    do {
                        if (temp22 == null) {
                            productIndexing++;
                            // productIndex set to 10,000 since number will never been assigned
                            if (productIndexing == 10000) {
                                break;
                            }
                            productsTempName = "Products" + productIndexing;
                            temp22 = (JSONArray) temp.get(productsTempName);
                        } else {
                            nullCheck = false;
                        }
                    } while (nullCheck);

                    // If the data was inserted by the ArrayList in the reserve order.
                    do {
                        if (temp22 == null) {
                            productIndexing--;
                            if (productIndexing == 0) {
                                break;
                            }
                            productsTempName = "Products" + productIndexing;
                            temp22 = (JSONArray) temp.get(productsTempName);
                        } else {
                            nullCheck = false;
                        }
                    } while (nullCheck);

                    Iterator temp33 = temp22.iterator();

                    while (temp33.hasNext()) {
                        JSONObject temp44 = (JSONObject) temp33.next();

                        // Extracting manufacturer information
                        Map manufacturer = (Map) temp44.get("Manufacturer");
                        Long manufacturerId = (Long) manufacturer.get("Id");
                        String manufacturerName = (String) manufacturer.get("Name");
                        String manufacturerProductNumber = temp44.get("ManufacturerProductNumber").toString();

                        // Assigning values to global variables
                        parametersList.put("manId", String.valueOf(manufacturerId));
                        parametersList.put("manName", String.valueOf(manufacturerName));
                        parametersList.put("manProductNumber", String.valueOf(manufacturerProductNumber));

                        // Extracting product variations
                        JSONArray productVariations = (JSONArray) temp44.get("ProductVariations");
                        Iterator iteratorProductV = productVariations.iterator();

                        // Iterating through each product variation
                        while (iteratorProductV.hasNext()) {
                            JSONObject temp2 = (JSONObject) iteratorProductV.next();
                            Map packageType = (Map) temp2.get("PackageType");
                            if (packageType != null) {
                                String packageName = packageType.get("Name").toString();
                            }
                            JSONArray standardPricing = (JSONArray) temp2.get("StandardPricing");
                            Iterator iteratorPricing = standardPricing.iterator();
                        }

                        // Extracting quantity available
                        Long quantityAvailable = (Long) temp44.get("QuantityAvailable");
                        parametersList.put("quantity", String.valueOf(quantityAvailable));

                        // Extracting product status
                        Map productStatus = (Map) temp44.get("ProductStatus");
                        String status = (String) productStatus.get("Status");
                        parametersList.put("stat", String.valueOf(status));

                        // Extracting parameters for each capacitor
                        JSONArray parameters = (JSONArray) temp44.get("Parameters");
                        Iterator iteratorParameters = parameters.iterator();

                        // Iterating through each parameter
                        while (iteratorParameters.hasNext()) {
                            JSONObject temp3 = (JSONObject) iteratorParameters.next();

                            // Extracting parameter attributes
                            String ParameterText = (String) temp3.get("ParameterText");
                            String ValueText = (String) temp3.get("ValueText");

                            // Adding parameters to the parameters list
                            parametersList.put(ParameterText, ValueText);
                        }

                        // Extracting base product information
                        Map baseProductNumber = (Map) temp44.get("BaseProductNumber");

                        if (baseProductNumber == null) {
                            // If no base product information available
                            baseProdName = baseProductName;
                            baseProdId = baseProductId;
                        } else {
                            // Extracting base product attributes
                            baseProductId = (Long) baseProductNumber.get("Id");

                            baseProdName = (String) baseProductNumber.get("Name");
                            baseProdId = baseProductId;
                        }

                        parametersList.put("baseProdName", String.valueOf(baseProdName));
                        parametersList.put("baseProdId", String.valueOf(baseProdId));

                        // Extracting category information
                        Map category = (Map) temp44.get("Category");
                        Long categoryId = (Long) category.get("CategoryId");
                        Long parentId = (Long) category.get("ParentId");
                        String categoryName = (String) category.get("Name");

                        parametersList.put("productParId", String.valueOf(parentId));
                        parametersList.put("productParName", String.valueOf(categoryName));

                        // Extracting child categories
                        JSONArray childCategories = (JSONArray) category.get("ChildCategories");
                        Iterator iteratorChildCategories = childCategories.iterator();

                        Object[] row = new Object[header.length];

                        // Create a HashSet to store the header values for faster lookup
                        Set<String> headerSet = new HashSet<>(Arrays.asList(header));

                        for (Map.Entry<String, String> entry: parametersList.entrySet()) {
                            if (headerSet.contains(entry.getKey())) {
                                int index = Arrays.asList(header).indexOf(entry.getKey());
                                row[index] = entry.getValue();
                            }
                        }
                        categoryTable.add(row);
                    }
                }

                // Converting list to a 2D array for easy printing
//                int tableColumns = header.length;

//                Object[][] table = new Object[categoryTable.size()][tableColumns];
//                for (int i = 0; i < categoryTable.size(); i++) {
//                    Object[] rowData = (Object[]) categoryTable.get(i);
//                    for (int j = 0; j < tableColumns; j++) {
//                        table[i][j] = rowData[j];
//                    }
//                }

                // Printing header
//                for (int j = 0; j < tableColumns; j++) {
//                System.out.printf("%-40s", header[j]);
//                }
//            System.out.println();
//
//                // Printing table content
//                for (Object[] objects : table) {
//                    for (int j = 0; j < tableColumns; j++) {
//                    System.out.printf("%-40s", objects[j]);
//                    }
//                System.out.println();
//                }

                //JDBC (Java Database Connectivity): class for interacting with a MySQL database
                JDBC dbConnector = new JDBC();
                // Establishing connection with server
                Connection connection = dbConnector.Connection();

                // membershipTable: class provides a method to create a custom membership table based on an original table.
                // createCustomTable: creates a custom membership table based on the original table.
                List<Object[]> customTable = membershipTable.createCustomTable(categoryTable, connection);

                // Column names of the membership sql table
                List<String> membershipHeaders = Arrays.asList("custom_id", "category_id", "manufacturer", "manufacturer_part_num");

                List<Characteristics> characterTable;
                if (sqlUploadChoice.toLowerCase().strip().equals("membership") ||
                        sqlUploadChoice.toLowerCase().strip().equals("member") ||
                sqlUploadChoice.toLowerCase().strip().equals("m"))
                {
                    // Inserts data into the 'membership' table.
                    // Column names : { custom_id, category_id, manufacturer, manufacturer_part_num }
                    dbConnector.insertMembership(customTable);

                }
                else if (sqlUploadChoice.toLowerCase().strip().equals("characteristics") ||
                        sqlUploadChoice.toLowerCase().strip().equals("character") ||
                        sqlUploadChoice.toLowerCase().strip().equals("c")) {
                    // characteristics2Table: provides a method to create a custom characteristics table based on original tables.
                    // Creates a custom characteristics table based on original tables.
                    characterTable = characteristics2Table.createCustomTable(categoryTable, membershipHeaders, headerList, connection);
                    dbConnector.insertCharacteristics(characterTable);
                }

                // Print Characteristic's TABLE with header
                // Print header
//                for (String label : characteristicsHeaders) {
//                    System.out.printf("%-40s", label);
//                }
//                System.out.println();
//
//                // Print data
//                for (Characteristics entry : characterTable) {
//                    System.out.printf("%-40s %-40s %-40s\n", entry.getCustomId(), entry.getAttributes(), entry.getValue());
//                }
                
                

                // Inserts data into the 'characteristics' table.
                // Column names : { custom_id, attribute_name, value }


            } catch (IOException | ParseException | java.text.ParseException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}