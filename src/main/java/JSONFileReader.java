import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/*
    Code Explanation
        The provided Java code reads information from a JSON file containing data about
    capacitors and processes it to create a formatted table. The code utilizes the
    JSON simple library to parse the JSON file, extracting details such as
    manufacturer information, product variations, and parameters for each capacitor.
        It then organizes this information into a table structure, including headers
    and rows, where each row represents a capacitor and its attributes. The code
    handles various data types, such as Long, Double, and String, and uses a
    switch statement to map parameter values to specific attributes.
        Additionally, comments have been added throughout the code to explain its
    functionality, variable assignments, and data extraction processes.
    The resulting table is printed to the console, presenting the capacitor
    data in a readable format.

    Addition Comment
    The code incorporates commented-out lines that contain information presently
    considered nonessential by the client. Should the client express a desire
    to append supplementary information, the code has been meticulously organized
    and stands ready for the incorporation of additional details.
 */

public class JSONFileReader {

    public static void main(String[] args) {

        // Category file is passed my inline argument
        String categoryFolder = args[0];

        String directory = "/root/EDAProject/Postman Exports/";
//        String directory = "Postman Exports/";

        List<String> filesInDirectory = DirectoryFiler.getFileNamesInDirectory(directory + categoryFolder);

        System.out.println("Directory size: " + filesInDirectory.size());
        String KEYWORD;

        String filePath;
        String baseProductName = "null";
        Long baseProductId = (long) 0;

        String baseProdName;
        Long baseProdId;

        for (String s : filesInDirectory) {
            KEYWORD = s;
            filePath = directory + categoryFolder + "/" + KEYWORD;

            System.out.println("File read: " + KEYWORD);


            // List to store each row of the capacitor table
            List<Object[]> categoryTable = new ArrayList<>();

            // Map to store parameters for each capacitor
            Map<String, String> parametersList = new LinkedHashMap<>();

            // Array to store header values
            String[] header;
            header = CategoryHeaders.selectCategory(categoryFolder);

            List<String> headerList = new ArrayList<>();
            for (String item : header) {
                headerList.add(item);
            }

            for (String value : header) {
                parametersList.put(value, null);
            }

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
                    String b = a.toString().substring(0, 1);
                    // Checking to see if there is an error value that starts with a number is in file.
                    // If so, skip it.
                    if (b.matches(".*\\d+.*")) {
//                        System.out.println("ERRORS");
                        break;
                    }
                    JSONObject temp = (JSONObject) a;

                    String productsTempName = "Products" + productIndexing;

                    JSONArray temp22 = (JSONArray) temp.get(productsTempName);

                    boolean nullCheck = true;
                    // If there was an error with assigning values to the key "Products" + # due to not existing
                    // the code below will increment the productIndexing to the next array
                    do {
                        if (temp22 == null) {
                            productIndexing++;
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

                JDBC dbConnector = new JDBC();
                Connection connection = dbConnector.Connection();

                List<Object[]> customTable = membershipTable.createCustomTable(categoryTable, connection);

                List<String> membershipHeaders = Arrays.asList("custom_id", "category_id", "manufacturer", "manufacturer_part_num");

            dbConnector.insertMembership(customTable);
                List<Characteristics> characterTable = characteristics2Table.createCustomTable(categoryTable, membershipHeaders, headerList, connection);

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

                dbConnector.insertCharacteristics(characterTable);

            } catch (IOException | ParseException | java.text.ParseException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}