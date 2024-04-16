import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
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

public class templateJSONFileReader {

    public static void main(String[] args) {

//        String categoryFolder = args[0];
        String categoryFolder = "Connectors, Interconnects";

        List<String> filesInDirectory = DirectoryFiler.getFileNamesInDirectory("Postman Exports/" + categoryFolder);

        System.out.println("Directory size: " + filesInDirectory.size());
        String KEYWORD = null;

        String filePath = null;
        HashSet<String> attributes = new HashSet<>();


        for (int k = 0; k < filesInDirectory.size(); k++) {
            KEYWORD = filesInDirectory.get(k);
            filePath = "Postman Exports/" + categoryFolder + "/" + KEYWORD;

            System.out.println("File read: " +KEYWORD);


            // List to store each row of the capacitor table
            List<Object> categoryTable = new ArrayList<>();

            // Map to store parameters for each capacitor
            Map<String, String> parametersList = new LinkedHashMap<>();

            Map<String, Long> priceBreakQuantity = new LinkedHashMap<>();
            Map<String, Double> priceUnitPrice = new LinkedHashMap<>();
            Map<String, Double> priceTotalPrice = new LinkedHashMap<>();

            // Array to store header values
            String[] header = {"manId","manName","manProductNumber","quantity","stat",

                    "baseProdId","baseProdName", "productParId","productParName"};

            // Variables to store individual capacitor attributes
            Long manId;
            String manName;
            String manProductNumber;
            Long quantity;
            String stat;

            Long baseProdId;
            String baseProdName;
            Long productParId;
            String productParName;


            // JSON Parser for reading the input file
            JSONParser parser = new JSONParser();

            JSONArray products = null;

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
                    String productsTempName = "Products" + Integer.toString(productIndexing);

                    JSONArray temp22 = (JSONArray) temp.get(productsTempName);

                    boolean nullCheck = true;
                    // If there was an error with assigning values to the key "Products" + # due to not existing
                    // the code below will increment the productIndexing to the next array
                    do {
                        if (temp22 == null) {
                            productIndexing++;
                            if (productIndexing == 1000) {
                                break;
                            }
                            productsTempName = "Products" + Integer.toString(productIndexing);
                            temp22 = (JSONArray) temp.get(productsTempName);
                        }
                        else {
                            nullCheck = false;
                        }
                    } while (nullCheck);


                    Iterator temp33 = temp22.iterator();

                    while (temp33.hasNext()) {
                        JSONObject temp44 = (JSONObject) temp33.next();
//                String description = temp.get("Description").toString();

                        // Extracting manufacturer information
                        Map manufacturer = (Map) temp44.get("Manufacturer");
                        Long manufacturerId = (Long) manufacturer.get("Id");
                        String manufacturerName = (String) manufacturer.get("Name");
                        String manufacturerProductNumber = temp44.get("ManufacturerProductNumber").toString();

                        // Assigning values to global variables
                        manId = manufacturerId;
                        manName = manufacturerName;
                        manProductNumber = manufacturerProductNumber;

                        // Extracting unit price
//                Double unitPrice = (Double) temp44.get("UnitPrice");

//                String productUrl = temp44.get("ProductUrl").toString();
//                String dataSheetUrl = temp44.get("DatasheetUrl").toString();
//                String photoUrl = temp44.get("PhotoUrl").toString();

                        // Extracting product variations
                        JSONArray productVariations = (JSONArray) temp44.get("ProductVariations");
                        Iterator iteratorProductV = productVariations.iterator();

                        // Iterating through each product variation
                        while (iteratorProductV.hasNext()) {
                            JSONObject temp2 = (JSONObject) iteratorProductV.next();
//                    String digiKeyProductNumber = temp2.get("DigiKeyProductNumber").toString();
                            Map packageType = (Map) temp2.get("PackageType");
                            if (packageType != null) {
                                String packageName = packageType.get("Name").toString();
                            }
                            JSONArray standardPricing = (JSONArray) temp2.get("StandardPricing");
                            Iterator iteratorPricing = standardPricing.iterator();

                            // Iterating through each Standard Pricing
                            while (iteratorPricing.hasNext()) {
                                JSONObject priceItem = (JSONObject) iteratorPricing.next();

                                // Extracting pricing attributes
                                Long breakQuantity = (Long) priceItem.get("BreakQuantity");
                                Double unitPrice = (Double) priceItem.get("UnitPrice");
                                Double totalPrice = (Double) priceItem.get("TotalPrice");


                                // Adding pricing information to the parameters list
//                            if (breakQuantity == 1) {
//                                priceBreakQuantity.put(packageName + " Break Quantity", breakQuantity);
//                                priceUnitPrice.put(packageName + " Unit Price", unitPrice);
//                                priceTotalPrice.put(packageName + " Total Price", totalPrice);
//                            }

                            }

//                    String myPricing = temp2.get("MyPricing").toString();
//                    String marketPlace = temp2.get("MarketPlace").toString();
//                    String tariffActive = temp2.get("TariffActive").toString();
//                    String supplier = temp2.get("Supplier").toString();
//
//                    String quantityAvailableforPackageType = temp2.get("QuantityAvailableforPackageType").toString();
//                    String maxQuantityForDistribution = temp2.get("MaxQuantityForDistribution").toString();
//                    String minimumOrderQuantity = temp2.get("MinimumOrderQuantity").toString();
//                    String standardPackage = temp2.get("StandardPackage").toString();
//                    String digiReelFee = temp2.get("DigiReelFee").toString();
                        }

                        // Extracting quantity available
                        Long quantityAvailable = (Long) temp44.get("QuantityAvailable");
                        quantity = quantityAvailable;

                        // Extracting product status
                        Map productStatus = (Map) temp44.get("ProductStatus");
                        String status = (String) productStatus.get("Status");
                        stat = status;

//                String backOrderNotAllowed = (String) temp44.get("BackOrderNotAllowed");
//                String normallyStocking = (String) temp44.get("NormallyStocking");
//                String discontinued = (String) temp44.get("Discontinued");
//                String endOfLife = (String) temp44.get("EndOfLife");
//                String ncnr = (String) temp44.get("Ncnr");
//                String primaryVideoUrl = (String) temp44.get("PrimaryVideoUrl");

                        // Extracting parameters for each capacitor
                        JSONArray parameters = (JSONArray) temp44.get("Parameters");
                        Iterator iteratorParameters = parameters.iterator();

                        // Iterating through each parameter
                        while (iteratorParameters.hasNext()) {
                            JSONObject temp3 = (JSONObject) iteratorParameters.next();

                            // Extracting parameter attributes
//                    Long parameterId = (Long) temp3.get("ParameterId");
                            String ParameterText = (String) temp3.get("ParameterText");
//                    String ParameterType = (String) temp3.get("ParameterType");
//                    String ValueId = (String) temp3.get("ValueId");
                            String ValueText = (String) temp3.get("ValueText");

                            // Adding parameters to the parameters list
                            parametersList.put(ParameterText, ValueText);
                        }

                        // Assigning parameter values to corresponding variables
                        for (Map.Entry<String, String> entry : parametersList.entrySet()) {
                            switch (entry.getKey()) {
//
                                default:
//                                    System.out.println(entry.getKey());
                                    attributes.add(entry.getKey());
                                    break;
                            }
                        }

                        // Extracting base product information
                        Map baseProductNumber = (Map) temp44.get("BaseProductNumber");
                        if (baseProductNumber == null) {
                            // If no base product information available
                            String baseProductName = "null";
                            Long baseProductId = (long) 0;

                            baseProdName = baseProductName;
                            baseProdId = baseProductId;
                        }
                        else {
                            // Extracting base product attributes
                            Long baseProductId = (Long) baseProductNumber.get("Id");

                            baseProdName = (String) baseProductNumber.get("Name");
                            baseProdId = baseProductId;

                        }
                        // Extracting category information
                        Map category = (Map) temp44.get("Category");
                        Long categoryId = (Long) category.get("CategoryId");
                        Long parentId = (Long) category.get("ParentId");
                        String categoryName = (String) category.get("Name");

                        productParId = parentId;
                        productParName = categoryName;

//                Long productCount = (Long) category.get("ProductCount");
//                Long newProductCount = (Long) category.get("NewProductCount");
//                String imageUrl = (String) category.get("ImageUrl");
//                String seoDescription = (String) category.get("SeoDescription");

                        // Extracting child categories
                        JSONArray childCategories = (JSONArray) category.get("ChildCategories");
                        Iterator iteratorChildCategories = childCategories.iterator();

                        // Iterating through each child category
//                while (iteratorChildCategories.hasNext()) {
//                    JSONObject temp4 = (JSONObject) iteratorChildCategories.next();
//                    Map childCategory = (Map) temp4.get("Category");
//                    Long childCategoryId = (Long) temp4.get("CategoryId");
//                    Long childParentId = (Long) temp4.get("ParentId");
//                    String childCategoryName = (String) temp4.get("Name");
//                    Long childProductCount = (Long) temp4.get("ProductCount");
//                    Long childNewProductCount = (Long) temp4.get("NewProductCount");
//                    String childImageUrl = (String) temp4.get("ImageUrl");
//                    String childSeoDescription = (String) temp4.get("SeoDescription");
//                }

//                String dateLastBuyChance = (String) temp44.get("DateLastBuyChance");
//                String manufacturerLeadWeeks = (String) temp44.get("ManufacturerLeadWeeks");
//                Long manufacturerPublicQuantity = (Long) temp44.get("ManufacturerPublicQuantity");
//                Object series = (Object) temp44.get("Series").toString();
//                String shippingInfo = (String) temp44.get("ShippingInfo");
//                Map classifications = (Map) temp44.get("Classifications");
//                String reachStatus = (String) classifications.get("ReachStatus");
//                String rohsStatus = (String) classifications.get("RohsStatus");
//                String moistureSensitivityLevel = (String) classifications.get("MoistureSensitivityLevel");
//                String exportControlClassNumber = (String) classifications.get("ExportControlClassNumber");
//                String htsusCode = (String) classifications.get("HtsusCode");

                        // Adding Product to Array
                        Object[] row = new Object[]{manId, manName, manProductNumber, quantity, stat,

                                baseProdId, baseProdName, productParId, productParName
                        };
                        categoryTable.add(row);
                    }
                }

//                int tableColumns = 419;
//
//                // Converting list to a 2D array for easy printing
//                Object[][] table = new Object[categoryTable.size()][tableColumns];
//                for (int i = 0; i < categoryTable.size(); i++) {
//                    Object[] rowData = (Object[]) categoryTable.get(i);
//                    for (int j = 0; j < tableColumns; j++) {
//                        table[i][j] = rowData[j];
//                    }
//                }
//
//                // Printing header
//                for (int j = 0; j < tableColumns; j++) {
////                System.out.printf("%-40s", header[j]);
//                }
////            System.out.println();
//
//                // Printing table content
//                for (Object[] objects : table) {
//                    for (int j = 0; j < tableColumns; j++) {
////                    System.out.printf("%-40s", objects[j]);
//                    }
////                System.out.println();
//                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }


        }

        System.out.println();
        for (String str: attributes) {
            System.out.println(str);
        }

    }
}