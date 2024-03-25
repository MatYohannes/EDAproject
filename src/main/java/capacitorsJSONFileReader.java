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

public class capacitorsJSONFileReader {

    private static final String KEYWORD = "Mica and PTFE Capacitors";

    private static List<Map<String, String>> convertToObjectList(Object[][] objectArray) {
        // Convert Object[][] to List<Map<String, String>>
        List<Map<String, String>> list = new ArrayList<>();

        for (Object[] row : objectArray) {
            Map<String, String> map = new HashMap<>();
            // Assuming the first column is "manID", second is "manProductNumber", and third is "productParName"
            map.put("manID", String.valueOf(row[0]));
            map.put("manProductNumber", String.valueOf(row[1]));
            map.put("productParName", String.valueOf(row[2]));
            list.add(map);
        }

        return list;
    }


    public static void main(String[] args) {

        // List to store each row of the capacitor table
        List<Object> capacitorTable = new ArrayList<>();

        // Map to store parameters for each capacitor
        Map<String, String> parametersList = new LinkedHashMap<>();

        Map<String, Long> priceBreakQuantity = new LinkedHashMap<>();
        Map<String, Double> priceUnitPrice = new LinkedHashMap<>();
        Map<String, Double> priceTotalPrice = new LinkedHashMap<>();

        // SQL tables
        /*
            Membership Table
            Attribute Columns = [Custom ID, Category ID, Manufacturer, Manufacturer Part #, DigiKey Order #, Mouser Order #]

            Package Table
            Attribute Columns = [Manufacturer, Manufacturer Part #, Package]

            Order Number Table
            Attribute Columns = [DigiKey Order #, Mouser Order #, Order #]

            Characteristics Table
            Attributes Columns = [Custom ID, Attribute Name, Value]

            The below table will sorted in the
            Categories Table
            Attributes Columns = [Category ID, Category Name, Parent ID]
         */


        // Array to store header values
        String[] header = {"manId","manName","manProductNumber","quantity","stat",
                "baseProdId","baseProdName", "productParId","productParName",
                "capacitance", "tolerance", "voltageRated", "temperatureCoefficient", "operatingTemperature",
                "dieletricMaterial", "termination", "eSR", "lifetimeTemp", "rippleCurrLowFreq",
                "rippleCurrHighFreq", "surfaceMLS", "type", "impedance", "height", "manufSizeCode",
                "numOfCapacitors", "circuitType", "supplierDevicePackage", "capacitanceRange", "adjustmentType",
                "qFreq", "voltageBreakdown", "esl", "currentLeakage", "dissipationFactor",
                "accessoryType", "relatedProducts", "fitsFanSize", "deviceSize", "specifications",
                "width", "fanAccessoryType", "powerInWatts", "voltagePeakReverseMax", "diodeType",
                "kitType", "quantity2", "packagesIncluded", "shape", "usage",
                "material", "color", "lengthz", "diameterOutside", "diameterInside",
                "resistance", "composition", "currentMax", "features", "ratings",
                "applications", "mountingType", "packageCase", "sizeDimension",
                "heightSeatedMax", "leadSpacing", "capacitanceVrF", "resistanceIfF", "powerDissipationMax"};

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

        // Parameters
        String capacitance = null;
        String tolerance = null;
        String voltageRated = null;
        String temperatureCoefficient = null;
        String operatingTemperature = null;
        String dieletricMaterial = null;
        String termination = null;
        String eSR = null;
        String lifetimeTemp = null;
        String rippleCurrLowFreq = null;
        String rippleCurrHighFreq = null;
        String surfaceMLS = null;
        String type = null;
        String impedance = null;
        String height = null;
        String manufSizeCode = null;
        String numOfCapacitors = null;
        String circuitType = null;
        String supplierDevicePackage = null;
        String capacitanceRange = null;
        String adjustmentType = null;
        String qFreq = null;
        String voltageBreakdown = null;
        String esl = null;
        String currentLeakage = null;
        String dissipationFactor = null;
        String accessoryType = null;
        String relatedProducts = null;
        String fitsFanSize = null;
        String deviceSize = null;
        String specifications = null;
        String width = null;
        String fanAccessoryType = null;
        String powerInWatts = null;
        String voltagePeakReverseMax = null;
        String diodeType = null;
        String kitType = null;
        String quantity2 = null;
        String packagesIncluded = null;
        String shape = null;
        String usage = null;
        String material = null;
        String color = null;
        String lengthz = null;
        String diameterOutside = null;
        String diameterInside = null;
        String resistance = null;
        String composition = null;
        String currentMax = null;
        String features = null;
        String ratings = null;
        String applications = null;
        String mountingType = null;
        String packageCase = null;
        String sizeDimension = null;
        String heightSeatedMax = null;
        String leadSpacing = null;
        String capacitanceVrF = null;
        String resistanceIfF = null;
        String powerDissipationMax = null;



        // JSON Parser for reading the input file
        JSONParser parser = new JSONParser();

        JSONArray products = null;

        String filePath = "Postman Exports/Capacitors/" + KEYWORD + ".json";

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
                JSONObject temp = (JSONObject) iteratorProducts.next();
                productIndexing++;
                String productsTempName = "Products" + Integer.toString(productIndexing);
                JSONArray temp22 = (JSONArray) temp.get(productsTempName);
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
                            case "Capacitance":
                                capacitance = entry.getValue();
                                break;
                            case "Tolerance":
                                tolerance = entry.getValue();
                                break;
                            case "Voltage - Rated":
                                voltageRated = entry.getValue();
                                break;
                            case "Temperature Coefficient":
                                temperatureCoefficient = entry.getValue();
                                break;
                            case "Operating Temperature":
                                operatingTemperature = entry.getValue();
                                break;
                            case "Features":
                                features = entry.getValue();
                                break;
                            case "Ratings":
                                ratings = entry.getValue();
                                break;
                            case "Applications":
                                applications = entry.getValue();
                                break;
                            case "Mounting Type":
                                mountingType = entry.getValue();
                                break;
                            case "Package / Case":
                                packageCase = entry.getValue();
                                break;
                            case "Size / Dimension":
                                sizeDimension = entry.getValue();
                                break;
                            case "Height - Seated (Max)":
                                heightSeatedMax = entry.getValue();
                                break;
                            case "Lead Spacing":
                                leadSpacing = entry.getValue();
                                break;
                            case "Capacitance @ Vr, F":
                                capacitanceVrF = entry.getValue();
                                break;
                            case "Resistance @ If, F":
                                resistanceIfF = entry.getValue();
                                break;
                            case "Power Dissipation (Max)":
                                powerDissipationMax = entry.getValue();
                                break;
                            case "Dielectric Material":
                                dieletricMaterial = entry.getValue();
                                break;
                            case "Termination":
                                termination = entry.getValue();
                                break;
                            case "ESR (Equivalent Series Resistance)":
                                eSR = entry.getValue();
                                break;
                            case "Lifetime @ Temp.":
                                lifetimeTemp = entry.getValue();
                                break;
                            case "Ripple Current @ Low Frequency":
                                rippleCurrLowFreq = entry.getValue();
                                break;
                            case "Ripple Current @ High Frequency":
                                rippleCurrHighFreq = entry.getValue();
                                break;
                            case "Surface Mount Land Size":
                                surfaceMLS = entry.getValue();
                                break;
                            case "Type":
                                type = entry.getValue();
                                break;
                            case "Impedance":
                                impedance = entry.getValue();
                                break;
                            case "Height":
                                height = entry.getValue();
                                break;
                            case "Manufacturer Size Code":
                                manufSizeCode = entry.getValue();
                                break;
                            case "Number of Capacitors":
                                numOfCapacitors = entry.getValue();
                                break;
                            case "Circuit Type":
                                circuitType = entry.getValue();
                                break;
                            case "Supplier Device Package":
                                supplierDevicePackage = entry.getValue();
                                break;
                            case "Capacitance Range":
                                capacitanceRange = entry.getValue();
                                break;
                            case "Adjustment Type":
                                adjustmentType = entry.getValue();
                                break;
                            case "Q @ Freq":
                                qFreq = entry.getValue();
                                break;
                            case "Voltage - Breakdown":
                                voltageBreakdown = entry.getValue();
                                break;
                            case "ESL (Equivalent Series Inductance)":
                                esl = entry.getValue();
                                break;
                            case "Current - Leakage":
                                currentLeakage = entry.getValue();
                                break;
                            case "Dissipation Factor":
                                dissipationFactor = entry.getValue();
                                break;
                            case "Accessory Type":
                                accessoryType = entry.getValue();
                                break;
                            case "For Use With/Related Products":
                                relatedProducts = entry.getValue();
                                break;
                            case "Fits Fan Size":
                                fitsFanSize = entry.getValue();
                                break;
                            case "Device Size":
                                deviceSize = entry.getValue();
                                break;
                            case "Specifications":
                                specifications = entry.getValue();
                                break;
                            case "Width":
                                width = entry.getValue();
                                break;
                            case "Fan Accessory Type":
                                fanAccessoryType = entry.getValue();
                                break;
                            case "Power (Watts)":
                                powerInWatts = entry.getValue();
                                break;
                            case "Voltage - Peak Reverse (Max)":
                                voltagePeakReverseMax = entry.getValue();
                                break;
                            case "Diode Type":
                                diodeType = entry.getValue();
                                break;
                            case "Kit Type":
                                kitType = entry.getValue();
                                break;
                            case "Quantity":
                                quantity2 = entry.getValue();
                                break;
                            case "Packages Included":
                                packagesIncluded = entry.getValue();
                                break;
                            case "Shape":
                                shape = entry.getValue();
                                break;
                            case "Usage":
                                usage = entry.getValue();
                                break;
                            case "Material":
                                material = entry.getValue();
                                break;
                            case "Color":
                                color = entry.getValue();
                                break;
                            case "Length":
                               lengthz = entry.getValue();
                                break;
                            case "Diameter - Outside":
                                diameterOutside = entry.getValue();
                                break;
                            case "Diameter - Inside":
                                diameterInside = entry.getValue();
                                break;
                            case "Resistance":
                                resistance = entry.getValue();
                                break;
                            case "Composition":
                                composition = entry.getValue();
                                break;
                            case "Current - Max":
                                currentMax = entry.getValue();
                                break;
                            default:
                                System.out.println(entry.getKey());
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
                            baseProdId, baseProdName, productParId, productParName,
                            capacitance, tolerance, voltageRated, temperatureCoefficient, operatingTemperature,
                            dieletricMaterial, termination, eSR, lifetimeTemp, rippleCurrLowFreq,
                            rippleCurrHighFreq, surfaceMLS, type, impedance, height, manufSizeCode,
                            numOfCapacitors, circuitType, supplierDevicePackage, capacitanceRange, adjustmentType,
                            qFreq, voltageBreakdown, esl, currentLeakage, dissipationFactor,
                            accessoryType, relatedProducts, fitsFanSize, deviceSize, specifications,
                            width, fanAccessoryType, powerInWatts, voltagePeakReverseMax, diodeType,
                            kitType, quantity2, packagesIncluded, shape, usage,
                            material, color, lengthz, diameterOutside, diameterInside,
                            resistance, composition, currentMax, features, ratings,
                            applications, mountingType, packageCase, sizeDimension,
                            heightSeatedMax, leadSpacing, capacitanceVrF, resistanceIfF, powerDissipationMax
                            };
                    capacitorTable.add(row);
                }
            }

            int tableColumns = 69;

            // Converting list to a 2D array for easy printing
            Object[][] table = new Object[capacitorTable.size()][tableColumns];
            for (int i = 0; i < capacitorTable.size(); i++) {
                Object[] rowData = (Object[]) capacitorTable.get(i);
                for (int j = 0; j < tableColumns; j++) {
                    table[i][j] = rowData[j];
                }
            }
            // Printing out full table
            // Printing header
            for (int j = 0; j < tableColumns; j++) {
                System.out.printf("%-40s", header[j]);
            }
            System.out.println();

            // Printing table content
            for (Object[] objects : table) {
                for (int j = 0; j < tableColumns; j++) {
                    System.out.printf("%-40s", objects[j]);
                }
                System.out.println();
            }


            Object[][] membershipTableTemp = membershipTable.createCustomTable(table);
            Arrays.sort(membershipTableTemp, Comparator.comparing(row -> (Comparable) row[0]));
            List<String> membershipHeader = Arrays.asList("Custom ID", "Manufacturer", "Manufacturer Part #", "Category ID");
            List<String> tableHeader = Arrays.asList("manId","manName","manProductNumber","quantity","stat",
                    "baseProdId","baseProdName", "productParId","productParName",
                    "capacitance", "tolerance", "voltageRated", "temperatureCoefficient", "operatingTemperature",
                    "dieletricMaterial", "termination", "eSR", "lifetimeTemp", "rippleCurrLowFreq",
                    "rippleCurrHighFreq", "surfaceMLS", "type", "impedance", "height", "manufSizeCode",
                    "numOfCapacitors", "circuitType", "supplierDevicePackage", "capacitanceRange", "adjustmentType",
                    "qFreq", "voltageBreakdown", "esl", "currentLeakage", "dissipationFactor",
                    "accessoryType", "relatedProducts", "fitsFanSize", "deviceSize", "specifications",
                    "width", "fanAccessoryType", "powerInWatts", "voltagePeakReverseMax", "diodeType",
                    "kitType", "quantity2", "packagesIncluded", "shape", "usage",
                    "material", "color", "lengthz", "diameterOutside", "diameterInside",
                    "resistance", "composition", "currentMax", "features", "ratings",
                    "applications", "mountingType", "packageCase", "sizeDimension",
                    "heightSeatedMax", "leadSpacing", "capacitanceVrF", "resistanceIfF", "powerDissipationMax");
            List<String> characteristicsHeader = Arrays.asList("Custom ID", "Attribute Name", "Value");

//            Object[][] characteristicsTableTemp = characteristicsTable.createCustomTable(membershipTableTemp, table, membershipHeader, tableHeader);
//            // Printing characteristics table
//            for (int i = 0; i < 3; i++) {
//                System.out.printf("%-20s", characteristicsHeader.get(i));
//            }
//            System.out.println();
//
//            for (Object[] objects : characteristicsTableTemp) {
//                for (int i = 0; i < 3 ; i++) {
//                    System.out.printf("%-20s", objects[i]);
//                }
//                System.out.println();
//            }

//            // Printing membership table
//            // Printing header
//            for (int i = 0; i < 4; i++) {
//                System.out.printf("%-20s", membershipHeader.get(i));
//            }
//            System.out.println();
//
//            for (Object[] objects : membershipTableTemp) {
//                for (int i = 0; i < 4; i++) {
//                    System.out.printf("%-20s", objects[i]);
//                }
//                System.out.println();
//            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}