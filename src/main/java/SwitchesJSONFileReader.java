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

public class SwitchesJSONFileReader {

    public static void main(String[] args) {

        String categoryFolder = "Switches";

        String directory = "/root/EDAProject/Postman Exports/";
//        String directory = "Postman Exports/";

        List<String> filesInDirectory = DirectoryFiler.getFileNamesInDirectory(directory + categoryFolder);

        System.out.println("Directory size: " + filesInDirectory.size());
        String KEYWORD;

        String filePath;
        HashSet<String> attributes = new HashSet<>();

        String baseProductName = "null";
        Long baseProductId = (long) 0;

        String baseProdName = null;
        Long baseProdId = null;

        for (String s : filesInDirectory) {
            KEYWORD = s;
            filePath = directory + categoryFolder + "/" + KEYWORD;

            System.out.println("File read: " + KEYWORD);


            // List to store each row of the capacitor table
            List<Object> categoryTable = new ArrayList<>();

            // Map to store parameters for each capacitor
            Map<String, String> parametersList = new LinkedHashMap<>();

            // Array to store header values
            String[] header = {"manId","manName","manProductNumber","quantity","stat",
                    "baseProdId","baseProdName", "productParId","productParName",
                    "Cable Opening","Number of Keys","SFP/XFP Ports","Serial Interfaces","Actuator Diameter",
                    "Dielectric Material","Size","Copper Ports","Number of Filters","Display Mode",
                    "Section Width","Gender","Material Flammability Rating","Light Source","Current - UL",
                    "Panel Thickness","For Use With/Related Products","Included MCU/MPU Board(s)","Expandable","Test Frequency",
                    "Load Force (Dynamic)","Embedded","Primary Attributes","Switch Time (Ton, Toff) (Max)","Communications",
                    "Independent Circuits","Orientation","Illumination Voltage (Nominal)","Thickness","Qualification",
                    "Insulation Color","Screw Head Type","Maximum Cable Length","Filter Type","LED Driver Channels",
                    "Impedance","Current - Output High, Low","Light Color","Current - Leakage (IS(off)) (Max)","Ingress Protection",
                    "Output Type","Suggested Programming Environment","Actuator Length, Right Angle","Diameter - Outside","Signal Lines",
                    "Channel-to-Channel Matching (ΔRon)","Specifications","Output","Media Lines Protected","Contact Finish - Mating",
                    "End Caps","Zero Crossing Circuit","Length - Below Head","SFP/XFP Type","Device Size",
                    "-3db Bandwidth","Voltage - Switching AC","Peripherals","Number of Slots/Relays","Interconnect System",
                    "Length","Function - Audible","Number of Circuits","Frequency - Cutoff or Center","Interface",
                    "Operating Pressure","Legend (Text)","Contact Finish","Control/Display Type","Current - Output (Max)",
                    "Voltage - Input (Min)","Ejector Side","Transistor Type","Wire Gauge or Range - AWG","Connector - AC Output",
                    "Frequency - Transition","Operating Angle","Color","For Measuring","Voltage - Load",
                    "Output Phases","Contact Timing","Current - Output","Reset Temperature","Language",
                    "Legend Type","Voltage - Start Up","Current - Startup","Wire Gauge or Range - mm²","Delay Time - OFF",
                    "Lens Size","Illumination Type, Color","Mating Length/Depth","Speed","Applications",
                    "Diameter - Inside","Voltage - Supply (Min)","Material","Ratio - Input:Output","Barrel Depth",
                    "Fastening Type","Current - IEC","Indicator","Arrangement","Hardware Type",
                    "Fault Protection","Accessory Type","Current - DC Forward (If) (Max)","Diameter - Label","Current - Switching",
                    "Head Diameter","Index Stops","Insertion, Removal Method","Current - Output Source/Sink","Voltage - Collector Emitter Breakdown (Max)",
                    "On-State Resistance (Max)","Sensor Type","Feedback Type","Key Color","Accomodates a Fuse",
                    "Output Isolation","Programmable Features","Matrix (Columns x Rows)","Release Range","Holding Force",
                    "Termination Style","Shielding","Current - Supply","Contents","Flange Diameter",
                    "Approval Agency Marking","Voltage - Supply, Single/Dual (±)","Hole Diameter","Current Rating - Filter","Legend (Symbol Only)",
                    "Power - Output","Energy","Connector Type","Style","Width",
                    "Diameter","Main Purpose","Usage","Synchronous Rectifier","Application Specifics",
                    "Outline","Length - Overall","Number of Outputs and Type","Switch Circuit","Power - Max",
                    "Acceleration","Gear Reduction Ratio","DC Current Gain (hFE) (Min) @ Ic, Vce","Reset Operation","Polarization",
                    "Air Flow","Package Accepted","Voltage - Peak Reverse (Max)","Diode Type","Fiber Type",
                    "Resistance @ If, F","Voltage - Off State","Package / Case","Disconnect Type","Voltage - IEC",
                    "Operating Temperature","Shaft Size","Static dV/dt (Min)","Current - Carry","Must Release",
                    "Key Removable Positions","Capacitance @ Vr, F","Fits Fan Size","Voltage - Isolation","Topology",
                    "Mounting Type","ESR (Equivalent Series Resistance)","Face Size","Resistance","Control Features",
                    "Color - Legend","Pretravel","Connectivity","Outside Pipe Diameter (Min)","Current - Output / Channel",
                    "DigiKey Programmable","Frequency Range","Plug Type","Voltage - Input (Max)","Read Out",
                    "Height Above Board","Internal Switch(s)","Supplier Device Package","Includes","Function",
                    "Accuracy","Number of Sections","Power - Rated","Data Rate","Frequency - Switching",
                    "Voltage - Output","Voltage - Output (Min/Fixed)","Height","P1dB","Differential Travel",
                    "Primary Material","Actuator Height off PCB, Vertical","Mode","Angle of Throw","Drive Type",
                    "Standards","Accuracy - Highest (Lowest)","Voltage Rating - Appliance Inlet","Overtravel","Capacitance",
                    "Isolation","Body Finish","Rotation Angle","Color - Actuator/Cap","Actuator Material",
                    "Configuration","Housing Material","Crosstalk","Shell Size, MIL","Voltage - Supply, Dual (V±)",
                    "AC Outlets","(Select First, Then Apply Filters) Compatible Series","Operate Time","Number of Inputs",
                    "Compatible Tools","Utilized IC / Part","Material - Body","Sensing Range","Board Thickness",
                    "Insertion Loss","Construction","Number of Positions/Contacts","Number of Poles per Deck","Functions, Extra",
                    "Delay Time - ON","Port Size","Circuit","Switch Function","Region Utilized",
                    "Load Force (Static)","Dimming",
                    "Color - Background","Height - Seated (Max)","Current Rating (Amps)","Shell Finish","Operate Range",
                    "Lens Style","Shell Size - Insert","Turn On Time","Motor Type","Data Rate (Max)",
                    "Protocol","Voltage - Input","Number of Positions","Flash Size","Switch Type",
                    "Material - Magnet","Voltage - Supply, Single (V+)","Overload Protection","Frequency","Current Rating - AC",
                    "Switch Features","Washable","Sensing Temperature - Remote","Sensing Distance","Pressure Type",
                    "Copper Type","Relay Type","Display Type","Connector - AC Input","Efficiency",
                    "Voltage - Supply","Approved Countries","Coded, Keyed","Release Time","Size - Body",
                    "Voltage - Forward (Vf) (Typ)","Stroke Length","Print Type","Timing Initiate Method","Architecture",
                    "Fader Type","Delay Time","Key Type","Thread Size","Wavelength",
                    "Depth Behind Panel","Power (Watts)","Number of Stacks","Standard Number","Current Rating - DC",
                    "Fuse Type","Outside Pipe Diameter (Max)","Current - Supply (Max)","Voltage Supply Source","Workstand",
                    "Industry Recognized Mating Diameter","Base Unit","Features","RF Type","Contact Rating @ Voltage",
                    "Current - Transmitting","RAM Size","Tolerance","Number of Characters Per Row","Rds On (Typ)",
                    "RF Family/Standard","Mounting Feature","Switching Cycles","Current Rating - Circuit Breaker","Voltage - UL",
                    "Number of Poles","Timing Adjustment Method","Location","Sensing Temperature - Local","Platform",
                    "For Use With/Related Manufacturer","Current - Max","Voltage - Rated","Current - Sensing","Number of Levels",
                    "Channel Capacitance (CS(off), CD(off))","Output Code","Voltage Rating - AC","Illumination","Supplied Iron, Tweezer, Handle",
                    "Multiplexer/Demultiplexer Circuit","Control Interface","Fan Accessory Type","Card Type","IIP3",
                    "Test Condition","Legend Color","Voltage - Output 2","Actuator Marking","Voltage - Output 3",
                    "Current - Collector (Ic) (Max)","Voltage - Output 4","Approval Agency","Supplied Tips/Nozzles","Contact Finish Thickness",
                    "Nozzle Opening","Voltage - Output 1","Size / Dimension","Lamp Type","Linearity",
                    "Duty Cycle","Current","Legend","Shape","Filter Order",
                    "Release Force","Lens Transparency","Response Time","Sensing Method","Circuit per Deck",
                    "Number of Ports","Supplied Contents","Modulation","Contact Material","Pixel Format",
                    "Charge Injection","Cable Length","Voltage - Supply (Vcc/Vdd)","Proximity Detection","Must Operate",
                    "Actuator Level","Actuator Type","LED Color","Panel Cutout Dimensions","Vce Saturation (Max) @ Ib, Ic",
                    "Voltage - Output (Max)","Terminal - Width","Type","Voltage Rating - DC","Sensitivity",
                    "Travel Range","Temperature Range","Optical Pattern","Number of Decks","Voltage Rating",
                    "Wattage","Conduit Thread Size","Lead Spacing","Ratings","Operating Force",
                    "Actuator Orientation","Number of Channels","Technology","Current - Receiving","Current Rating - Appliance Inlet",
                    "Adjustment Type","Requires","Grade","Voltage - Breakdown","Connection Method",
                    "Memory Size","Connector Style","Pitch","Proto Board Type","Shell Material",
                    "Resolution","Fuse Holder, Drawer","Core Processor","Operating Position","Viewing Area",
                    "Bushing Thread","Viewing Angle","Voltage - Switching DC","Kit Type","FET Type",
                    "Port Style","Output Configuration","Actuator Length","Switching Temperature","Kit Contents",
                    "Voltage - Supply (Max)","Voltage","Secondary Attributes","Supply Voltage","Breaker Type",
                    "Function - Lighting","Display Characters - Height","Tool Type","Input Type","Current - Hold (Ih)",
                    "Quantity","Termination","Current - LED Trigger (Ift) (Max)","Slew Rate","Clock Sync",
                    "Number of Outputs","Actual Diameter","Fiber Ports","Duty Cycle (Max)","Body Material",
                    "Head Height","Cord Length","Number of Inputs and Type","Plating","Current - Collector Cutoff (Max)",
                    "Maximum Pressure"
            };

            for (int i = 0; i < header.length; i++) {
                parametersList.put(header[i], null);
            }

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
                    String b = a.toString().substring(0, 1);
                    // Checking to see if there is an error value that starts with a number is in file.
                    // If so, skip it.
                    if (b.matches(".*\\d+.*")) {
//                        System.out.println("ERRORS");
                        break;
                    }
                    JSONObject temp = (JSONObject) a;

                    String productsTempName = "Products" + Integer.toString(productIndexing);

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
                            productsTempName = "Products" + Integer.toString(productIndexing);
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
                            productsTempName = "Products" + Integer.toString(productIndexing);
                            temp22 = (JSONArray) temp.get(productsTempName);
                        } else {
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
                        parametersList.put("manId", String.valueOf(manufacturerId));
                        parametersList.put("manName", String.valueOf(manufacturerName));
                        parametersList.put("manProductNumber", String.valueOf(manufacturerProductNumber));

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

                int tableColumns = header.length;

                // Converting list to a 2D array for easy printing
                Object[][] table = new Object[categoryTable.size()][tableColumns];
                for (int i = 0; i < categoryTable.size(); i++) {
                    Object[] rowData = (Object[]) categoryTable.get(i);
                    for (int j = 0; j < tableColumns; j++) {
                        table[i][j] = rowData[j];
                    }
                }

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


                // Code below to upload to MYSQL
                // catch clause is added "java.text.ParseException" when uncommented

//            JDBC dbConnector = new JDBC();
//            dbConnector.insertMembership(table);
//            dbConnector.insertCharacteristics(table);
//            dbConnector.closeConnection();

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        for (String str: attributes) {
            System.out.println(str);
        }
    }
}