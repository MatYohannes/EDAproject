import org.json.simple.*;
import org.json.simple.parser.*;

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

public class resistorJSONFileReader {

    private static final String KEYWORD = "Resistor Accessories";

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
                "capacitance","tolerance","voltageRated","temperatureCoefficient",
                "operatingTemperature","features","ratings","applications","mountingType",
                "packageCase","sizeDimension","heightSeatedMax","thicknessMax",
                "leadSpacing","leadStyle","failureRate", "Current", "dcResistance", "insertionLoss", "heightMax", "threadSize",
                "voltageRatingAC", "voltageRatingDC", "dielectricMaterial", "termination",
                "ESR", "lifetimeTemp", "polarization", "rippleCurrLowFreq", "rippleCurrHighFreq", "surfaceMLS",
                "type", "frequency", "frequencyStability", "frequencyTolerance", "impedance", "height", "manufSizeCode",
                "numOfCapacitors", "circuitType", "supplierDevicePackage",
                "voltageClamping", "technology", "numOfCircuits",
                "function", "rfType", "secondaryAttributes", "capacitanceRange", "adjustmentType", "qFreq",
                "voltageBreakdown", "esl", "currentLeakage", "dissipationFactor", "topology", "internalSwitch",
                "numOfOutputs", "voltageSupplyMin", "voltageSupplyMax", "voltageOutput", "currentOutputChannel",
                "dimming", "accessoryType", "relatedProduct", "digiKeyProgrammable",
                 "currentSupply", "voltageSupply", "filterType", "freqCutOfforCenter", "numOfFilters", "filterOrder", "deviceSize",
                 "specifications", "batteryChemistry", "batteryCellSize", "capacity", "terminationStyle", "width", "airFlow",
                 "staticPressure", "bearingType", "fanType", "noise", "powerInWatts", "RPM", "ingressProtection", "approvalAgency",
                 "weight", "kitType", "quantity2","packagesIncluded",
                 "shape", "usage", "material", "color", "length", "diameterOutside", "diameterInside",
                 "resistance", "composition", "numOfTerminations",
                 "resistanceInOhms", "numOfResistors", "resistorMatchingRatio", "resistorRatioDrift", "numOfPins",
                 "powerPerElement", "grade", "qualification", "numOfTurns", "resistiveMaterial", "sensingDistance",
                 "sensingMethod", "outputConfig", "currentDCForwardIfMax", "currentCollectorIcMax", "voltageCollectorEmitBreakdownMax",
                 "responseTime", "coilType", "coilCurrent", "coilVoltage", "contactForm", "contactRatingCurrent",
                 "switchingVoltage", "mustOperateVoltage", "mustReleaseVoltage", "operateTime", "releaseTime", "builtInSwitch",
                 "taper", "numOfGangs", "rotation", "actuatorType", "actuatorLength", "actuatorDiameter", "bushingThread",
                 "coatingHousingType", "mountingFeature", "connectorType", "contactType", "numOfPositions", "pitch",
                 "numOfRows", "rowSpacing", "contactTermination", "fasteningType", "insulationMaterial", "count",
                 "connectorStyle", "currentRatingAppInlet", "voltageRatingAppInlet", "currentRatingFilter",
                 "switchFeatures", "currentRatingCircuitBreaker", "accomodatesAFuse", "fuseHolderDrawer", "panelCutoutDim",
                 "panelThickness", "materialFlammRating", "configuration", "transferRate", "wireGauge", "shielding",
                 "cordType", "cordTermination", "cordLength", "strapClosure", "strapMaterial", "strapTermination", "size",
                 "inputType", "outputType", "shaftSize", "coreProcessor", "programMemoryType",
                "controllerSeries", "ramSize", "interfaceType", "numberOfIO", "numberOfConductors",
                "shellSizeInsert", "shellSizeMIL", "orientation", "primaryMaterial", "shellMaterial",
                "shellFinish", "contactFinishMating", "currentRatingAmps", "voltageRating",
                "cableOpeningDiameter", "operatingForce", "output", "sensorType",
                "compensatedTemperature", "actuatorStyleSize", "includes", "displayType",
                "displayMode", "touchscreen", "diagonalScreenSize", "viewingArea",
                "backlight", "dotPixels", "controllerType", "graphicsColor",
                "backgroundColor", "numberOfCharacters", "contactSize", "moduleCapacity",
                "numberOfInputsType", "numberOfOutputsType", "expandable", "numberOfCharactersPerRow",
                "communications", "memorySize", "forMeasuring", "cableOpening", "diameter",
                "circuit", "switchFunction", "contactRatingAtVoltage", "actuatorHeightOffPcbVertical", "actuatorLengthRightAngle",
                "actuatorOrientation", "outline", "illumination", "illuminationTypeColor", "illuminationVoltageNominal",
                "shieldTermination", "cableGroup", "frequencyMax", "numberOfPorts", "housingColor", "centerContactMaterial",
                "switchType", "ratioInputOutput", "voltageLoad", "voltageSupplyVccVdd", "currentOutputMax", "rdsOnTyp",
                "faultProtection", "switchCircuit", "multiplexerDemultiplexerCircuit", "onStateResistanceMax",
                "channelToChannelMatchingDeltaRon", "voltageSupplySingleV", "voltageSupplyDualV", "switchTimeTonToffMax",
                "threeDbBandwidth", "chargeInjection", "channelCapacitanceCsOffCdOff", "currentLeakageIsOffMax", "crosstalk",
                "sensingRange", "testCondition", "currentSupplyMax", "voltageInputMin", "voltageInputMax", "voltageOutputMinFixed",
                "voltageOutputMax", "currentOutput", "frequencySwitching", "synchronousRectifier", "frequencyRange", "isolation",
                "testFrequency", "p1dB", "iip3", "independentCircuits", "currentOutputHighLow", "voltageSupplySource",
                "actuatorLevel", "contactMaterial", "contactFinish", "heightAboveBoard", "washable", "numberOfChannels",
                "releaseForce", "operatingPosition", "pretravel", "differentialTravel", "overtravel", "outputIsolation",
                "dutyCycle", "controlFeatures", "gender", "industryRecognizedMatingDiameter", "actualDiameter",
                "numberOfPositionsContacts", "insulationColor", "matingLengthDepth", "contactTiming", "signalLines", "operateRange",
                "releaseRange", "currentSwitching", "voltageSwitchingDC", "powerRated", "lengthOverall", "sizeBody", "currentCarry",
                "outputPhases", "dutyCycleMax", "clockSync", "serialInterfaces", "voltageSwitchingAC", "sensingTemperatureLocal",
                "sensingTemperatureRemote", "resolution", "accuracyHighestLowest", "colorActuatorCap", "actuatorMarking",
                "loadMaxSwitching", "sealRating", "coilInsulation", "relayType",
                "cardType", "insertRemovalMethod", "ejectorSide", "contactFinishThickness", "functionsExtra",
                 "fetType", "voltageStartUp", "timeFormat", "dateFormat", "voltageSupplyBattery", "currentTimekeepingMax",
                 "voltageCurrentOutput1", "voltageCurrentOutput2", "voltageCurrentOutput3", "withLEDDriver", "withSupervisor",
                 "withSequencer", "accuracy", "voltageInput", "numOfCells", "currentCharging", "programmableFeatures", "chargeCurrentMax",
                 "batteryPackVoltage", "actuatorMaterial", "mustOperate", "mustRelease", "intendedChipset",
                "chipsetManufacturer", "voltagePrimary", "voltageAuxiliary", "voltageIsolation",
                "inductanceAtFrequency", "footprint", "disconnectType", "numberOfLevels",
                "terminalWidth", "currentIEC", "voltageIEC", "currentUL",
                "voltageUL", "wireGaugeRangeAWG", "wireGaugeRangeMM2", "fuseType",
                "protocol", "standards", "delayTimeOn", "delayTimeOff",
                "amplifierType", "slewRate", "gainBandwidthProduct", "currentInputBias",
                "voltageInputOffset", "voltageSupplySpanMin", "voltageSupplySpanMax", "style",
                "keyRemovablePositions", "angleOfThrow", "indexStops", "numberOfDecks",
                "numberOfPolesPerDeck", "circuitPerDeck", "depthBehindPanel", "logicType",
                "supplyVoltage", "numberOfBits", "requiresCompatibleSeries", "outputCode",
                "readOut", "numberOfSections", "displayCharactersHeight", "endCaps",
                "sectionWidth", "voltage", "Indicator", "firstConnector", "secondConnector", "approvalAgencyMarking",
                "approvedCountries", "numberOfKeys", "matrixColumnsRows", "legendType",
                "keyType", "legend", "legendColor", "keyColor",
                "currentSensing", "sensitivity", "linearity", "resetOperation",
                "flowSensorType", "portSize", "switchFunctionRating", "materialBody",
                "baseProdId","baseProdName", "productParId","productParName"};

        // Variables to store individual capacitor attributes
        Long manId;
        String manName;
        String manProductNumber;
        Long quantity;
        String stat;
        String capacitance = null;
        String tolerance = null;
        String voltageRated = null;
        String temperatureCoefficient = null;
        String operatingTemperature = null;
        String features = null;
        String ratings = null;
        String applications = null;
        String mountingType = null;
        String packageCase = null;
        String sizeDimension = null;
        String heightSeatedMax = null;
        String thicknessMax = null;
        String leadSpacing = null;
        String leadStyle = null;
        String failureRate = "-";
        String current = null;
        String dcResistance = null;
        String insertionLoss = null;
        String heightMax = null;
        String threadSize = null;
        String voltageRatingAC = null;
        String voltageRatingDC = null;
        String dieletricMaterial = null;
        String termination = null;
        String eSR = null;
        String lifetimeTemp = null;
        String polarization= null;
        String rippleCurrLowFreq = null;
        String rippleCurrHighFreq = null;
        String surfaceMLS = null;
        String type = null;
        String frequency = null;
        String frequencyStability = null;
        String frequencyTolerance = null;
        String impedance = null;
        String height = null;
        String manufSizeCode = null;
        String numOfCapacitors = null;
        String circuitType = null;
        String supplierDevicePackage = null;
        String voltageClamping = null;
        String technology = null;
        String numOfCircuits = null;
        String function = null;
        String rfType = null;
        String secondaryAttributes = null;
        String capacitanceRange = null;
        String adjustmentType = null;
        String qFreq = null;
        String voltageBreakdown = null;
        String esl = null;
        String currentLeakage = null;
        String dissipationFactor = null;
        String topology = null;
        String internalSwitch = null;
        String numOfOutputs = null;
        String voltageSupplyMin = null;
        String voltageSupplyMax = null;
        String voltageOutput = null;
        String currentOutputChannel = null;
        String dimming = null;
        String accessoryType = null;
        String relatedProducts = null;
        String digiKeyProgrammable = null;
        String currentSupply = null;
        String voltageSupply = null;
        String filterType = null;
        String freqCutofforCenter = null;
        String numOfFilters = null;
        String filterOrder = null;
        String deviceSize = null;
        String specifications = null;
        String batteryChemistry = null;
        String batteryCellSize = null;
        String capacity = null;
        String terminationStyle = null;
        String width = null;
        String airFlow = null;
        String staticPressure = null;
        String bearingType = null;
        String fanType = null;
        String noise = null;
        String powerInWatts = null;
        String rpm = null;
        String ingressProtection = null;
        String approvalAgency = null;
        String weight = null;
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
        String numOfTerminations = null;
        String resistanceINOhms = null;
        String numOfResistors = null;
        String resistorMatchingRatio = null;
        String resistorRatioDrift = null;
        String numOfPins = null;
        String powerPerElement = null;
        String grade = null;
        String qualification = null;
        String numOfTurns = null;
        String resistiveMaterial = null;
        String sensingDistance = null;
        String sensingMethod = null;
        String outputConfig = null;
        String currentDCForwardIfMax = null;
        String currentCollectorIcMax = null;
        String voltageCollectorEmitBreakdownMax = null;
        String responseTime = null;
        String coilType = null;
        String coilCurrent = null;
        String coilVoltage = null;
        String contactForm = null;
        String contactRatingCurrent = null;
        String switchingVoltage = null;
        String mustOperateVoltage = null;
        String mustReleaseVoltage = null;
        String operatingTime = null;
        String releaseTime = null;
        String builtInSwitch = null;
        String taper = null;
        String numOfGangs = null;
        String rotation = null;
        String actuatorType = null;
        String actuatorLength = null;
        String actuatorDiameter = null;
        String bushingThread = null;
        String coatingHousingType = null;
        String mountingFeature = null;
        String connectorType = null;
        String contactType = null;
        String numOfPositions = null;
        String pitch = null;
        String numberOfRows = null;
        String rowSpacing = null;
        String contactTermination = null;
        String fasteningType = null;
        String insulationMaterial = null;
        String count = null;
        String connectorStyle = null;
        String currentRatingAppInlet = null;
        String voltageRatingAppInlet = null;
        String currentRatingFilter = null;
        String switchFeatures = null;
        String currentRatingCircuitBreaker = null;
        String accomodatesAFuse = null;
        String fuseHolderDrawer = null;
        String panelCutoutDim = null;
        String panelThickness = null;
        String materialFlammRating = null;
        String configuration = null;
        String transferRate = null;
        String wireGauge = null;
        String shielding = null;
        String cordType = null;
        String cordTermination = null;
        String cordLength = null;
        String strapClosure = null;
        String strapMaterial = null;
        String strapTermination = null;
        String size = null;
        String inputType = null;
        String outputType = null;
        String shaftSize = null;
        String coreProcessor = null;
        String programMemoryType = null;
        String controllerSeries = null;
        String ramSize = null;
        String interfaceType = null;
        String numberOfIO = null;
        String numberOfConductors = null;
        String shellSizeInsert = null;
        String shellSizeMIL = null;
        String orientation = null;
        String primaryMaterial = null;
        String shellMaterial = null;
        String shellFinish = null;
        String contactFinishMating = null;
        String currentRatingAmps = null;
        String voltageRating = null;
        String cableOpeningDiameter = null;
        String operatingForce = null;
        String output = null;
        String sensorType = null;
        String compensatedTemperature = null;
        String actuatorStyleSize = null;
        String includes = null;
        String displayType = null;
        String displayMode = null;
        String touchscreen = null;
        String diagonalScreenSize = null;
        String viewingArea = null;
        String backlight = null;
        String dotPixels = null;
        String controllerType = null;
        String graphicsColor = null;
        String backgroundColor = null;
        String numberOfCharacters = null;
        String contactSize = null;
        String moduleCapacity = null;
        String numberOfInputsType = null;
        String numberOfOutputsType = null;
        String expandable = null;
        String numberOfCharactersPerRow = null;
        String communications = null;
        String memorySize = null;
        String forMeasuring = null;
        String cableOpening = null;
        String diameter = null;
        String circuit = null;
        String switchFunction = null;
        String contactRatingAtVoltage = null;
        String actuatorHeightOffPcbVertical = null;
        String actuatorLengthRightAngle = null;
        String actuatorOrientation = null;
        String outline = null;
        String illumination = null;
        String illuminationTypeColor = null;
        String illuminationVoltageNominal = null;
        String shieldTermination = null;
        String cableGroup = null;
        String frequencyMax = null;
        String numberOfPorts = null;
        String housingColor = null;
        String centerContactMaterial = null;
        String switchType = null;
        String ratioInputOutput = null;
        String voltageLoad = null;
        String voltageSupplyVccVdd = null;
        String currentOutputMax = null;
        String rdsOnTyp = null;
        String faultProtection = null;
        String switchCircuit = null;
        String multiplexerDemultiplexerCircuit = null;
        String onStateResistanceMax = null;
        String channelToChannelMatchingDeltaRon = null;
        String voltageSupplySingleV = null;
        String voltageSupplyDualV = null;
        String switchTimeTonToffMax = null;
        String threedbBandwidth = null;
        String chargeInjection = null;
        String channelCapacitanceCsOffCdOff = null;
        String currentLeakageIsOffMax = null;
        String crosstalk = null;
        String sensingRange = null;
        String testCondition = null;
        String currentSupplyMax = null;
        String voltageInputMin = null;
        String voltageInputMax = null;
        String voltageOutputMinFixed = null;
        String voltageOutputMax = null;
        String currentOutput = null;
        String frequencySwitching = null;
        String synchronousRectifier = null;
        String frequencyRange = null;
        String isolation = null;
        String testFrequency = null;
        String p1dB = null;
        String iip3 = null;
        String independentCircuits = null;
        String currentOutputHighLow = null;
        String voltageSupplySource = null;
        String actuatorLevel = null;
        String contactMaterial = null;
        String contactFinish = null;
        String heightAboveBoard = null;
        String washable = null;
        String numberOfChannels = null;
        String releaseForce = null;
        String operatingPosition = null;
        String pretravel = null;
        String differentialTravel = null;
        String overtravel = null;
        String outputIsolation = null;
        String dutyCycle = null;
        String controlFeatures = null;
        String gender = null;
        String industryRecognizedMatingDiameter = null;
        String actualDiameter = null;
        String numberOfPositionsContacts = null;
        String insulationColor = null;
        String matingLengthDepth = null;
        String contactTiming = null;
        String signalLines = null;
        String operateRange = null;
        String releaseRange = null;
        String currentSwitching = null;
        String voltageSwitchingDC = null;
        String powerRated = null;
        String lengthOverall = null;
        String sizeBody = null;
        String currentCarry = null;
        String outputPhases = null;
        String dutyCycleMax = null;
        String clockSync = null;
        String serialInterfaces = null;
        String voltageSwitchingAC = null;
        String sensingTemperatureLocal = null;
        String sensingTemperatureRemote = null;
        String resolution = null;
        String accuracyHighestLowest = null;
        String colorActuatorCap = null;
        String actuatorMarking = null;
        String loadMaxSwitching = null;
        String sealRating = null;
        String coilInsulation = null;
        String relayType = null;
        Long baseProdId;
        String baseProdName;
        Long productParId;
        String productParName;

        String cardType = null;
        String insertRemovalMethod = null;
        String ejectorSide = null;
        String contactFinishThickness = null;
        String functionsExtra = null;
        String fetType = null;
        String voltageStartUp = null;
        String timeFormat = null;
        String dateFormat = null;
        String voltageSupplyBattery = null;
        String currentTimekeepingMax = null;
        String voltageCurrentOutput1 = null;
        String voltageCurrentOutput2 = null;
        String voltageCurrentOutput3 = null;
        String withLEDDriver = null;
        String withSupervisor = null;
        String withSequencer = null;
        String accuracy = null;
        String voltageInput = null;
        String numOfCells = null;
        String currentCharging = null;
        String programmableFeatures = null;
        String chargeCurrentMax = null;
        String batteryPackVoltage = null;

        String actuatorMaterial = null;
        String mustOperate = null;
        String mustRelease = null;
        String intendedChipset = null;
        String chipsetManufacturer = null;
        String voltagePrimary = null;
        String voltageAuxiliary = null;
        String voltageIsolation = null;
        String inductanceAtFrequency = null;
        String footprint = null;
        String disconnectType = null;
        String numberOfLevels = null;
        String terminalWidth = null;
        String currentIEC = null;
        String voltageIEC = null;
        String currentUL = null;
        String voltageUL = null;
        String wireGaugeRangeAWG = null;
        String wireGaugeRangeMM2 = null;
        String fuseType = null;
        String protocol = null;
        String standards = null;
        String delayTimeOn = null;
        String delayTimeOff = null;
        String amplifierType = null;
        String slewRate = null;
        String gainBandwidthProduct = null;
        String currentInputBias = null;
        String voltageInputOffset = null;
        String voltageSupplySpanMin = null;
        String voltageSupplySpanMax = null;
        String style = null;
        String keyRemovablePositions = null;
        String angleOfThrow = null;
        String indexStops = null;
        String numberOfDecks = null;
        String numberOfPolesPerDeck = null;
        String circuitPerDeck = null;
        String depthBehindPanel = null;
        String logicType = null;
        String supplyVoltage = null;
        String numberOfBits = null;
        String requiresCompatibleSeries = null;
        String outputCode = null;
        String readOut = null;
        String numberOfSections = null;
        String displayCharactersHeight = null;
        String endCaps = null;
        String sectionWidth = null;
        String voltage = null;
        String indicator = null;
        String firstConnector = null;
        String secondConnector = null;
        String approvalAgencyMarking = null;
        String approvedCountries = null;
        String numberOfKeys = null;
        String matrixColumnsRows = null;
        String legendType = null;
        String keyType = null;
        String legend = null;
        String legendColor = null;
        String keyColor = null;
        String currentSensing = null;
        String sensitivity = null;
        String linearity = null;
        String resetOperation = null;
        String flowSensorType = null;
        String portSize = null;
        String switchFunctionRating = null;
        String materialBody = null;







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
//                            case "Capacitance":
//                                capacitance = entry.getValue();
//                                break;
//                            case "Tolerance":
//                                tolerance = entry.getValue();
//                                break;
//                            case "Voltage - Rated":
//                                voltageRated = entry.getValue();
//                                break;
//                            case "Temperature Coefficient":
//                                temperatureCoefficient = entry.getValue();
//                                break;
//                            case "Operating Temperature":
//                                operatingTemperature = entry.getValue();
//                                break;
//                            case "Features":
//                                features = entry.getValue();
//                                break;
//                            case "Ratings":
//                                ratings = entry.getValue();
//                                break;
//                            case "Applications":
//                                applications = entry.getValue();
//                                break;
//                            case "Mounting Type":
//                                mountingType = entry.getValue();
//                                break;
//                            case "Package / Case":
//                                packageCase = entry.getValue();
//                                break;
//                            case "Size / Dimension":
//                                sizeDimension = entry.getValue();
//                                break;
//                            case "Height - Seated (Max)":
//                                heightSeatedMax = entry.getValue();
//                                break;
//                            case "Thickness (Max)":
//                                thicknessMax = entry.getValue();
//                                break;
//                            case "Lead Spacing":
//                                leadSpacing = entry.getValue();
//                                break;
//                            case "Lead Style":
//                                leadStyle = entry.getValue();
//                                break;
//                            case "Failure Rate":
//                                failureRate = entry.getValue();
//                                break;
//                            case "Thread Size":
//                                threadSize = entry.getValue();
//                                break;
//                            case "Current":
//                                current = entry.getValue();
//                                break;
//                            case "DC Resistance (DCR) (Max)":
//                                dcResistance = entry.getValue();
//                                break;
//                            case "Insertion Loss":
//                                insertionLoss = entry.getValue();
//                                break;
//                            case "Height (Max)":
//                                heightMax = entry.getValue();
//                                break;
//                            case "Voltage Rating - AC":
//                                voltageRatingAC = entry.getValue();
//                                break;
//                            case "Voltage Rating - DC":
//                                voltageRatingDC = entry.getValue();
//                                break;
//                            case "Dielectric Material":
//                                dieletricMaterial = entry.getValue();
//                                break;
//                            case "Termination":
//                                termination = entry.getValue();
//                                break;
//                            case "ESR (Equivalent Series Resistance)":
//                                eSR = entry.getValue();
//                                break;
//                            case "Lifetime @ Temp.":
//                                lifetimeTemp = entry.getValue();
//                                break;
//                            case "Polarization":
//                                polarization = entry.getValue();
//                                break;
//                            case "Ripple Current @ Low Frequency":
//                                rippleCurrLowFreq = entry.getValue();
//                                break;
//                            case "Ripple Current @ High Frequency":
//                                rippleCurrHighFreq = entry.getValue();
//                                break;
//                            case "Surface Mount Land Size":
//                                surfaceMLS = entry.getValue();
//                                break;
//                            case "Type":
//                                type = entry.getValue();
//                                break;
//                            case "Frequency":
//                                frequency = entry.getValue();
//                                break;
//                            case "Frequency Stability":
//                                frequencyStability = entry.getValue();
//                                break;
//                            case "Frequency Tolerance":
//                                frequencyTolerance = entry.getValue();
//                                break;
//                            case "Impedance":
//                                impedance = entry.getValue();
//                                break;
//                            case "Height":
//                                height = entry.getValue();
//                                break;
//                            case "Manufacturer Size Code":
//                                manufSizeCode = entry.getValue();
//                                break;
//                            case "Number of Capacitors":
//                                numOfCapacitors = entry.getValue();
//                                break;
//                            case "Circuit Type":
//                                circuitType = entry.getValue();
//                                break;
//                            case "Supplier Device Package":
//                                supplierDevicePackage = entry.getValue();
//                                break;
//                            case "Voltage - Clamping":
//                                circuitType = entry.getValue();
//                                break;
//                            case "Technology":
//                                circuitType = entry.getValue();
//                                break;
//                            case "Number of Circuits":
//                                numOfCircuits = entry.getValue();
//                                break;
//                            case "Function":
//                                function = entry.getValue();
//                                break;
//                            case "RF Type":
//                                rfType = entry.getValue();
//                                break;
//                            case "Secondary Attributes":
//                                secondaryAttributes = entry.getValue();
//                                break;
//                            case "Capacitance Range":
//                                capacitanceRange = entry.getValue();
//                                break;
//                            case "Adjustment Type":
//                                adjustmentType = entry.getValue();
//                                break;
//                            case "Q @ Freq":
//                                qFreq = entry.getValue();
//                                break;
//                            case "Voltage - Breakdown":
//                                voltageBreakdown = entry.getValue();
//                                break;
//                            case "ESL (Equivalent Series Inductance)":
//                                esl = entry.getValue();
//                                break;
//                            case "Current - Leakage":
//                                currentLeakage = entry.getValue();
//                                break;
//                            case "Dissipation Factor":
//                                dissipationFactor = entry.getValue();
//                                break;
//                            case "Topology":
//                                topology = entry.getValue();
//                                break;
//                            case "Internal Switch(s)":
//                                internalSwitch = entry.getValue();
//                                break;
//                            case "Number of Outputs":
//                                numOfOutputs = entry.getValue();
//                                break;
//                            case "Voltage - Supply (Min)":
//                                voltageSupplyMin = entry.getValue();
//                                break;
//                            case "Voltage - Supply (Max)":
//                                voltageSupplyMax = entry.getValue();
//                                break;
//                            case "Voltage - Output":
//                                voltageOutput = entry.getValue();
//                                break;
//                            case "Current - Output / Channel":
//                                currentOutputChannel = entry.getValue();
//                                break;
//                            case "Dimming":
//                                dimming = entry.getValue();
//                                break;
//                            case "Accessory Type":
//                                accessoryType = entry.getValue();
//                                break;
//                            case "For Use With/Related Products":
//                                relatedProducts = entry.getValue();
//                                break;
//                            case "DigiKey Programmable":
//                                digiKeyProgrammable = entry.getValue();
//                                break;
//                            case "Current - Supply":
//                                currentSupply = entry.getValue();
//                                break;
//                            case "Voltage - Supply":
//                                voltageSupply = entry.getValue();
//                                break;
//                            case "Frequency - Cutoff or Center":
//                                freqCutofforCenter = entry.getValue();
//                                break;
//                            case "Number of Filters":
//                                numOfFilters = entry.getValue();
//                                break;
//                            case "Filter Order":
//                                filterOrder = entry.getValue();
//                                break;
//                            case "Device Size":
//                                deviceSize = entry.getValue();
//                                break;
//                            case "Specifications":
//                                specifications = entry.getValue();
//                                break;
//                            case "Battery Chemistry":
//                                batteryChemistry = entry.getValue();
//                                break;
//                            case "Battery Cell Size":
//                                batteryCellSize = entry.getValue();
//                                break;
//                            case "Capacity":
//                                capacity = entry.getValue();
//                                break;
//                            case "Termination Style":
//                                terminationStyle = entry.getValue();
//                                break;
//                            case "Width":
//                                width = entry.getValue();
//                                break;
//                            case "Air Flow":
//                                airFlow = entry.getValue();
//                                break;
//                            case "Static Pressure":
//                                staticPressure = entry.getValue();
//                                break;
//                            case "Bearing Type":
//                               bearingType  = entry.getValue();
//                                break;
//                            case "Fan Type":
//                                fanType = entry.getValue();
//                                break;
//                            case "Noise":
//                                noise = entry.getValue();
//                                break;
//                            case "Power (Watts)":
//                                powerInWatts = entry.getValue();
//                                break;
//                            case "RPM":
//                               rpm = entry.getValue();
//                                break;
//                            case "Ingress Protection":
//                                ingressProtection = entry.getValue();
//                                break;
//                            case "Approval Agency":
//                                approvalAgency = entry.getValue();
//                                break;
//                            case "Weight":
//                                weight = entry.getValue();
//                                break;
//                            case "Kit Type":
//                                kitType = entry.getValue();
//                                break;
//                            case "Quantity":
//                                quantity2 = entry.getValue();
//                                break;
//                            case "Packages Included":
//                                packagesIncluded = entry.getValue();
//                                break;
//                            case "Shape":
//                                shape = entry.getValue();
//                                break;
//                            case "Usage":
//                                usage = entry.getValue();
//                                break;
//                            case "Material":
//                                material = entry.getValue();
//                                break;
//                            case "Color":
//                                color = entry.getValue();
//                                break;
//                            case "Length":
//                               lengthz = entry.getValue();
//                                break;
//                            case "Diameter - Outside":
//                                diameterOutside = entry.getValue();
//                                break;
//                            case "Diameter - Inside":
//                                diameterInside = entry.getValue();
//                                break;
//                            case "Resistance":
//                                resistance = entry.getValue();
//                                break;
//                            case "Composition":
//                                composition = entry.getValue();
//                                break;
//                            case "Number of Terminations":
//                                numOfTerminations = entry.getValue();
//                                break;
//                            case "Resistance (Ohms)":
//                                resistanceINOhms = entry.getValue();
//                                break;
//                            case "Number of Resistors":
//                                numOfResistors = entry.getValue();
//                                break;
//                            case "Resistor Matching Ratio":
//                                resistorMatchingRatio = entry.getValue();
//                                break;
//                            case "Resistor-Ratio-Drift":
//                                resistorRatioDrift = entry.getValue();
//                                break;
//                            case "Number of Pins":
//                                numOfPins = entry.getValue();
//                                break;
//                            case "Power Per Element":
//                                powerPerElement = entry.getValue();
//                                break;
//                            case "Grade":
//                                grade = entry.getValue();
//                                break;
//                            case "Qualification":
//                                qualification = entry.getValue();
//                                break;
//                            case "Number of Turns":
//                                numOfTurns = entry.getValue();
//                                break;
//                            case "Resistive Material":
//                                resistiveMaterial = entry.getValue();
//                                break;
//                            case "Sensing Distance":
//                                sensingDistance = entry.getValue();
//                                break;
//                            case "Sensing Method":
//                                sensingMethod = entry.getValue();
//                                break;
//                            case "Output Configuration":
//                                outputConfig = entry.getValue();
//                                break;
//                            case "Current - DC Forward (If) (Max)":
//                                currentDCForwardIfMax = entry.getValue();
//                                break;
//                            case "Current - Collector (Ic) (Max)":
//                                currentCollectorIcMax = entry.getValue();
//                                break;
//                            case "Voltage - Collector Emitter Breakdown (Max)":
//                                voltageCollectorEmitBreakdownMax = entry.getValue();
//                                break;
//                            case "Response Time":
//                                responseTime = entry.getValue();
//                                break;
//                            case "Coil Type":
//                                coilType = entry.getValue();
//                                break;
//                            case "Coil Current":
//                                coilCurrent = entry.getValue();
//                                break;
//                            case "Coil Voltage":
//                                coilVoltage = entry.getValue();
//                                break;
//                            case "Contact Form":
//                                contactForm = entry.getValue();
//                                break;
//                            case "Contact Rating (Current)":
//                                contactRatingCurrent = entry.getValue();
//                                break;
//                            case "Switching Voltage":
//                                switchingVoltage = entry.getValue();
//                                break;
//                            case "Must Operate Voltage":
//                                mustOperateVoltage = entry.getValue();
//                                break;
//                            case "Must Release Voltage":
//                                mustReleaseVoltage = entry.getValue();
//                                break;
//                            case "Operate Time":
//                                operatingTime = entry.getValue();
//                                break;
//                            case "Release Time":
//                                releaseTime = entry.getValue();
//                                break;
//                            case "Built in Switch":
//                                builtInSwitch = entry.getValue();
//                                break;
//                            case "Taper":
//                                taper = entry.getValue();
//                                break;
//                            case "Number of Gangs":
//                                numOfGangs = entry.getValue();
//                                break;
//                            case "Rotation":
//                                rotation = entry.getValue();
//                                break;
//                            case "Actuator Type":
//                                actuatorType = entry.getValue();
//                                break;
//                            case "Actuator Length":
//                                actuatorLength = entry.getValue();
//                                break;
//                            case "Actuator Diameter":
//                                actuatorDiameter = entry.getValue();
//                                break;
//                            case "Bushing Thread":
//                                bushingThread = entry.getValue();
//                                break;
//                            case "Coating, Housing Type":
//                                coatingHousingType = entry.getValue();
//                                break;
//                            case "Mounting Feature":
//                                mountingFeature = entry.getValue();
//                                break;
//                            case "Connector Type":
//                                connectorType = entry.getValue();
//                                break;
//                            case "Contact Type":
//                                contactType = entry.getValue();
//                                break;
//                            case "Number of Positions":
//                                numOfPositions = entry.getValue();
//                                break;
//                            case "Pitch":
//                                pitch = entry.getValue();
//                                break;
//                            case "Number of Rows":
//                                numberOfRows = entry.getValue();
//                                break;
//                            case "Row Spacing":
//                                rowSpacing = entry.getValue();
//                                break;
//                            case "Contact Termination":
//                                contactTermination = entry.getValue();
//                                break;
//                            case "Fastening Type":
//                                fasteningType = entry.getValue();
//                                break;
//                            case "Insulation Material":
//                                insulationMaterial = entry.getValue();
//                                break;
//                            case "Count":
//                                count = entry.getValue();
//                                break;
//                            case "Connector Style":
//                                connectorStyle = entry.getValue();
//                                break;
//                            case "Current Rating - Appliance Inlet":
//                                currentRatingAppInlet = entry.getValue();
//                                break;
//                            case "Voltage Rating - Appliance Inlet":
//                                voltageRatingAppInlet = entry.getValue();
//                                break;
//                            case "Current Rating - Filter":
//                                currentRatingFilter = entry.getValue();
//                                break;
//                            case "Switch Features":
//                                switchFeatures = entry.getValue();
//                                break;
//                            case "Current Rating - Circuit Breaker":
//                                currentRatingCircuitBreaker = entry.getValue();
//                                break;
//                            case "Accomodates a Fuse":
//                                accomodatesAFuse = entry.getValue();
//                                break;
//                            case "Fuse Holder, Drawer":
//                                fuseHolderDrawer = entry.getValue();
//                                break;
//                            case "Panel Cutout Dimensions":
//                                panelCutoutDim = entry.getValue();
//                                break;
//                            case "Panel Thickness":
//                                panelThickness = entry.getValue();
//                                break;
//                            case "Material Flammability Rating":
//                                materialFlammRating = entry.getValue();
//                                break;
//                            case "Configuration":
//                                configuration = entry.getValue();
//                                break;
//                            case "Transfer Rate":
//                                transferRate = entry.getValue();
//                                break;
//                            case "Wire Gauge":
//                                wireGauge = entry.getValue();
//                                break;
//                            case "Shielding":
//                                shielding = entry.getValue();
//                                break;
//                            case "Cord Type":
//                                cordType = entry.getValue();
//                                break;
//                            case "Cord Termination":
//                                cordTermination = entry.getValue();
//                                break;
//                            case "Cord Length":
//                                cordLength = entry.getValue();
//                                break;
//                            case "Strap Closure":
//                                strapClosure = entry.getValue();
//                                break;
//                            case "Strap Material":
//                                strapMaterial = entry.getValue();
//                                break;
//                            case "Strap Termination":
//                                strapTermination = entry.getValue();
//                                break;
//                            case "Size":
//                                size = entry.getValue();
//                                break;
//                            case "Filter Type":
//                                filterType = entry.getValue();
//                                break;
//                            case "Input Type":
//                                inputType = entry.getValue();
//                                break;
//                            case "Output Type":
//                                outputType = entry.getValue();
//                                break;
//                            case "Shaft Size":
//                                shaftSize = entry.getValue();
//                                break;
//                            case "Core Processor":
//                                coreProcessor = entry.getValue();
//                                break;
//                            case "Program Memory Type":
//                                programMemoryType = entry.getValue();
//                                break;
//                            case "Controller Series":
//                                controllerSeries = entry.getValue();
//                                break;
//                            case "RAM Size":
//                                ramSize = entry.getValue();
//                                break;
//                            case "Interface":
//                                interfaceType = entry.getValue();
//                                break;
//                            case "Number of I/O":
//                                numberOfIO = entry.getValue();
//                                break;
//                            case "Number of Conductors":
//                                numberOfConductors = entry.getValue();
//                                break;
//                            case "Shell Size - Insert":
//                                shellSizeInsert = entry.getValue();
//                                break;
//                            case "Shell Size, MIL":
//                                shellSizeMIL = entry.getValue();
//                                break;
//                            case "Orientation":
//                                orientation = entry.getValue();
//                                break;
//                            case "Primary Material":
//                                primaryMaterial = entry.getValue();
//                                break;
//                            case "Shell Material":
//                                shellMaterial = entry.getValue();
//                                break;
//                            case "Shell Finish":
//                                shellFinish = entry.getValue();
//                                break;
//                            case "Contact Finish - Mating":
//                                contactFinishMating = entry.getValue();
//                                break;
//                            case "Current Rating (Amps)":
//                                currentRatingAmps = entry.getValue();
//                                break;
//                            case "Voltage Rating":
//                                voltageRating = entry.getValue();
//                                break;
//                            case "Cable Opening Diameter":
//                                cableOpeningDiameter = entry.getValue();
//                                break;
//                            case "Operating Force":
//                                operatingForce = entry.getValue();
//                                break;
//                            case "Output":
//                                output = entry.getValue();
//                                break;
//                            case "Sensor Type":
//                                sensorType = entry.getValue();
//                                break;
//                            case "Compensated Temperature":
//                                compensatedTemperature = entry.getValue();
//                                break;
//                            case "Actuator Style/Size":
//                                actuatorStyleSize = entry.getValue();
//                                break;
//                            case "Includes":
//                                includes = entry.getValue();
//                                break;
//                            case "Display Type":
//                                displayType = entry.getValue();
//                                break;
//                            case "Display Mode":
//                                displayMode = entry.getValue();
//                                break;
//                            case "Touchscreen":
//                                touchscreen = entry.getValue();
//                                break;
//                            case "Diagonal Screen Size":
//                                diagonalScreenSize = entry.getValue();
//                                break;
//                            case "Viewing Area":
//                                viewingArea = entry.getValue();
//                                break;
//                            case "Backlight":
//                                backlight = entry.getValue();
//                                break;
//                            case "Dot Pixels":
//                                dotPixels = entry.getValue();
//                                break;
//                            case "Controller Type":
//                                controllerType = entry.getValue();
//                                break;
//                            case "Graphics Color":
//                                graphicsColor = entry.getValue();
//                                break;
//                            case "Background Color":
//                                backgroundColor = entry.getValue();
//                                break;
//                            case "Number of Characters":
//                                numberOfCharacters = entry.getValue();
//                                break;
//                            case "Contact Size":
//                                contactSize = entry.getValue();
//                                break;
//                            case "Module Capacity":
//                                moduleCapacity = entry.getValue();
//                                break;
//                            case "Number of Inputs and Type":
//                                numberOfInputsType = entry.getValue();
//                                break;
//                            case "Number of Outputs and Type":
//                                numberOfOutputsType = entry.getValue();
//                                break;
//                            case "Expandable":
//                                expandable = entry.getValue();
//                                break;
//                            case "Number of Characters Per Row":
//                                numberOfCharactersPerRow = entry.getValue();
//                                break;
//                            case "Communications":
//                                communications = entry.getValue();
//                                break;
//                            case "Memory Size":
//                                memorySize = entry.getValue();
//                                break;
//                            case "For Measuring":
//                                forMeasuring = entry.getValue();
//                                break;
//                            case "Cable Opening":
//                                cableOpening = entry.getValue();
//                                break;
//                            case "Diameter":
//                                diameter = entry.getValue();
//                                break;
//                            case "Circuit":
//                                circuit = entry.getValue();
//                                break;
//                            case "Switch Function":
//                                switchFunction = entry.getValue();
//                                break;
//                            case "Contact Rating @ Voltage":
//                                contactRatingAtVoltage = entry.getValue();
//                                break;
//                            case "Actuator Height off PCB, Vertical":
//                                actuatorHeightOffPcbVertical = entry.getValue();
//                                break;
//                            case "Actuator Length, Right Angle":
//                                actuatorLengthRightAngle = entry.getValue();
//                                break;
//                            case "Actuator Orientation":
//                                actuatorOrientation = entry.getValue();
//                                break;
//                            case "Outline":
//                                outline = entry.getValue();
//                                break;
//                            case "Illumination":
//                                illumination = entry.getValue();
//                                break;
//                            case "Illumination Type, Color":
//                                illuminationTypeColor = entry.getValue();
//                                break;
//                            case "Illumination Voltage (Nominal)":
//                                illuminationVoltageNominal = entry.getValue();
//                                break;
//                            case "Shield Termination":
//                                shieldTermination = entry.getValue();
//                                break;
//                            case "Cable Group":
//                                cableGroup = entry.getValue();
//                                break;
//                            case "Frequency - Max":
//                                frequencyMax = entry.getValue();
//                                break;
//                            case "Number of Ports":
//                                numberOfPorts = entry.getValue();
//                                break;
//                            case "Housing Color":
//                                housingColor = entry.getValue();
//                                break;
//                            case "Center Contact Material":
//                                centerContactMaterial = entry.getValue();
//                                break;
//                            case "Switch Type":
//                                switchType = entry.getValue();
//                                break;
//                            case "Ratio - Input:Output":
//                                ratioInputOutput = entry.getValue();
//                                break;
//                            case "Voltage - Load":
//                                voltageLoad = entry.getValue();
//                                break;
//                            case "Voltage - Supply (Vcc/Vdd)":
//                                voltageSupplyVccVdd = entry.getValue();
//                                break;
//                            case "Current - Output (Max)":
//                                currentOutputMax = entry.getValue();
//                                break;
//                            case "Rds On (Typ)":
//                                rdsOnTyp = entry.getValue();
//                                break;
//                            case "Fault Protection":
//                                faultProtection = entry.getValue();
//                                break;
//                            case "Switch Circuit":
//                                switchCircuit = entry.getValue();
//                                break;
//                            case "Multiplexer/Demultiplexer Circuit":
//                                multiplexerDemultiplexerCircuit = entry.getValue();
//                                break;
//                            case "On-State Resistance (Max)":
//                                onStateResistanceMax = entry.getValue();
//                                break;
//                            case "Channel-to-Channel Matching (ΔRon)":
//                                channelToChannelMatchingDeltaRon = entry.getValue();
//                                break;
//                            case "Voltage - Supply, Single (V+)":
//                                voltageSupplySingleV = entry.getValue();
//                                break;
//                            case "Voltage - Supply, Dual (V±)":
//                                voltageSupplyDualV = entry.getValue();
//                                break;
//                            case "Switch Time (Ton, Toff) (Max)":
//                                switchTimeTonToffMax = entry.getValue();
//                                break;
//                            case "-3db Bandwidth":
//                                threedbBandwidth = entry.getValue();
//                                break;
//                            case "Charge Injection":
//                                chargeInjection = entry.getValue();
//                                break;
//                            case "Channel Capacitance (CS(off), CD(off))":
//                                channelCapacitanceCsOffCdOff = entry.getValue();
//                                break;
//                            case "Current - Leakage (IS(off)) (Max)":
//                                currentLeakageIsOffMax = entry.getValue();
//                                break;
//                            case "Crosstalk":
//                                crosstalk = entry.getValue();
//                                break;
//                            case "Sensing Range":
//                                sensingRange = entry.getValue();
//                                break;
//                            case "Test Condition":
//                                testCondition = entry.getValue();
//                                break;
//                            case "Current - Supply (Max)":
//                                currentSupplyMax = entry.getValue();
//                                break;
//                            case "Voltage - Input (Min)":
//                                voltageInputMin = entry.getValue();
//                                break;
//                            case "Voltage - Input (Max)":
//                                voltageInputMax = entry.getValue();
//                                break;
//                            case "Voltage - Output (Min/Fixed)":
//                                voltageOutputMinFixed = entry.getValue();
//                                break;
//                            case "Voltage - Output (Max)":
//                                voltageOutputMax = entry.getValue();
//                                break;
//                            case "Current - Output":
//                                currentOutput = entry.getValue();
//                                break;
//                            case "Frequency - Switching":
//                                frequencySwitching = entry.getValue();
//                                break;
//                            case "Synchronous Rectifier":
//                                synchronousRectifier = entry.getValue();
//                                break;
//                            case "Frequency Range":
//                                frequencyRange = entry.getValue();
//                                break;
//                            case "Isolation":
//                                isolation = entry.getValue();
//                                break;
//                            case "Test Frequency":
//                                testFrequency = entry.getValue();
//                                break;
//                            case "P1dB":
//                                p1dB = entry.getValue();
//                                break;
//                            case "IIP3":
//                                iip3 = entry.getValue();
//                                break;
//                            case "Independent Circuits":
//                                independentCircuits = entry.getValue();
//                                break;
//                            case "Current - Output High, Low":
//                                currentOutputHighLow = entry.getValue();
//                                break;
//                            case "Voltage Supply Source":
//                                voltageSupplySource = entry.getValue();
//                                break;
//                            case "Actuator Level":
//                                actuatorLevel = entry.getValue();
//                                break;
//                            case "Contact Material":
//                                contactMaterial = entry.getValue();
//                                break;
//                            case "Contact Finish":
//                                contactFinish = entry.getValue();
//                                break;
//                            case "Height Above Board":
//                                heightAboveBoard = entry.getValue();
//                                break;
//                            case "Washable":
//                                washable = entry.getValue();
//                                break;
//                            case "Number of Channels":
//                                numberOfChannels = entry.getValue();
//                                break;
//                            case "Release Force":
//                                releaseForce = entry.getValue();
//                                break;
//                            case "Operating Position":
//                                operatingPosition = entry.getValue();
//                                break;
//                            case "Pretravel":
//                                pretravel = entry.getValue();
//                                break;
//                            case "Differential Travel":
//                                differentialTravel = entry.getValue();
//                                break;
//                            case "Overtravel":
//                                overtravel = entry.getValue();
//                                break;
//                            case "Output Isolation":
//                                outputIsolation = entry.getValue();
//                                break;
//                            case "Duty Cycle":
//                                dutyCycle = entry.getValue();
//                                break;
//                            case "Control Features":
//                                controlFeatures = entry.getValue();
//                                break;
//                            case "Gender":
//                                gender = entry.getValue();
//                                break;
//                            case "Industry Recognized Mating Diameter":
//                                industryRecognizedMatingDiameter = entry.getValue();
//                                break;
//                            case "Actual Diameter":
//                                actualDiameter = entry.getValue();
//                                break;
//                            case "Number of Positions/Contacts":
//                                numberOfPositionsContacts = entry.getValue();
//                                break;
//                            case "Insulation Color":
//                                insulationColor = entry.getValue();
//                                break;
//                            case "Mating Length/Depth":
//                                matingLengthDepth = entry.getValue();
//                                break;
//                            case "Contact Timing":
//                                contactTiming = entry.getValue();
//                                break;
//                            case "Signal Lines":
//                                signalLines = entry.getValue();
//                                break;
//                            case "Operate Range":
//                                operateRange = entry.getValue();
//                                break;
//                            case "Release Range":
//                                releaseRange = entry.getValue();
//                                break;
//                            case "Current - Switching":
//                                currentSwitching = entry.getValue();
//                                break;
//                            case "Voltage - Switching DC":
//                                voltageSwitchingDC = entry.getValue();
//                                break;
//                            case "Power - Rated":
//                                powerRated = entry.getValue();
//                                break;
//                            case "Length - Overall":
//                                lengthOverall = entry.getValue();
//                                break;
//                            case "Size - Body":
//                                sizeBody = entry.getValue();
//                                break;
//                            case "Current - Carry":
//                                currentCarry = entry.getValue();
//                                break;
//                            case "Output Phases":
//                                outputPhases = entry.getValue();
//                                break;
//                            case "Duty Cycle (Max)":
//                                dutyCycleMax = entry.getValue();
//                                break;
//                            case "Clock Sync":
//                                clockSync = entry.getValue();
//                                break;
//                            case "Serial Interfaces":
//                                serialInterfaces = entry.getValue();
//                                break;
//                            case "Voltage - Switching AC":
//                                voltageSwitchingAC = entry.getValue();
//                                break;
//                            case "Sensing Temperature - Local":
//                                sensingTemperatureLocal = entry.getValue();
//                                break;
//                            case "Sensing Temperature - Remote":
//                                sensingTemperatureRemote = entry.getValue();
//                                break;
//                            case "Resolution":
//                                resolution = entry.getValue();
//                                break;
//                            case "Accuracy - Highest (Lowest)":
//                                accuracyHighestLowest = entry.getValue();
//                                break;
//                            case "Color - Actuator/Cap":
//                                colorActuatorCap = entry.getValue();
//                                break;
//                            case "Actuator Marking":
//                                actuatorMarking = entry.getValue();
//                                break;
//                            case "Load - Max Switching":
//                                loadMaxSwitching = entry.getValue();
//                                break;
//                            case "Seal Rating":
//                                sealRating = entry.getValue();
//                                break;
//                            case "Coil Insulation":
//                                coilInsulation = entry.getValue();
//                                break;
//                            case "Relay Type":
//                                relayType = entry.getValue();
//                                break;
//                            case "Card Type":
//                                cardType = entry.getValue();
//                                break;
//                            case "Insertion, Removal Method":
//                                insertRemovalMethod = entry.getValue();
//                                break;
//                            case "Ejector Side":
//                                ejectorSide = entry.getValue();
//                                break;
//                            case "Contact Finish Thickness":
//                                contactFinishThickness = entry.getValue();
//                                break;
//                            case "Functions, Extra":
//                                functionsExtra = entry.getValue();
//                                break;
//                            case "FET Type":
//                                fetType = entry.getValue();
//                                break;
//                            case "Voltage - Start Up":
//                                voltageStartUp = entry.getValue();
//                                break;
//                            case "Time Format":
//                                timeFormat = entry.getValue();
//                                break;
//                            case "Date Format":
//                                dateFormat = entry.getValue();
//                                break;
//                            case "Voltage - Supply, Battery":
//                                voltageSupplyBattery = entry.getValue();
//                                break;
//                            case "Current - Timekeeping (Max)":
//                                currentTimekeepingMax = entry.getValue();
//                                break;
//                            case "Voltage/Current - Output 1":
//                                voltageCurrentOutput1 = entry.getValue();
//                                break;
//                            case "Voltage/Current - Output 2":
//                                voltageCurrentOutput2 = entry.getValue();
//                                break;
//                            case "Voltage/Current - Output 3":
//                                voltageCurrentOutput3 = entry.getValue();
//                                break;
//                            case "w/LED Driver":
//                                withLEDDriver = entry.getValue();
//                                break;
//                            case "w/Supervisor":
//                                withSupervisor = entry.getValue();
//                                break;
//                            case "w/Sequencer":
//                                withSequencer = entry.getValue();
//                                break;
//                            case "Accuracy":
//                                accuracy = entry.getValue();
//                                break;
//                            case "Voltage - Input":
//                                voltageInput = entry.getValue();
//                                break;
//                            case "Number of Cells":
//                                numOfCells = entry.getValue();
//                                break;
//                            case "Current - Charging":
//                                currentCharging = entry.getValue();
//                                break;
//                            case "Programmable Features":
//                                programmableFeatures = entry.getValue();
//                                break;
//                            case "Charge Current - Max":
//                                chargeCurrentMax = entry.getValue();
//                                break;
//                            case "Battery Pack Voltage":
//                                batteryPackVoltage = entry.getValue();
//                                break;
//                            case "Actuator Material":
//                                actuatorMaterial = entry.getValue();
//                                break;
//                            case "Must Operate":
//                                mustOperate = entry.getValue();
//                                break;
//                            case "Must Release":
//                                mustRelease = entry.getValue();
//                                break;
//                            case "Intended Chipset":
//                                intendedChipset = entry.getValue();
//                                break;
//                            case "Chipset Manufacturer":
//                                chipsetManufacturer = entry.getValue();
//                                break;
//                            case "Voltage - Primary":
//                                voltagePrimary = entry.getValue();
//                                break;
//                            case "Voltage - Auxiliary":
//                                voltageAuxiliary = entry.getValue();
//                                break;
//                            case "Voltage - Isolation":
//                                voltageIsolation = entry.getValue();
//                                break;
//                            case "Inductance @ Frequency":
//                                inductanceAtFrequency = entry.getValue();
//                                break;
//                            case "Footprint":
//                                footprint = entry.getValue();
//                                break;
//                            case "Disconnect Type":
//                                disconnectType = entry.getValue();
//                                break;
//                            case "Number of Levels":
//                                numberOfLevels = entry.getValue();
//                                break;
//                            case "Terminal - Width":
//                                terminalWidth = entry.getValue();
//                                break;
//                            case "Current - IEC":
//                                currentIEC = entry.getValue();
//                                break;
//                            case "Voltage - IEC":
//                                voltageIEC = entry.getValue();
//                                break;
//                            case "Current - UL":
//                                currentUL = entry.getValue();
//                                break;
//                            case "Voltage - UL":
//                                voltageUL = entry.getValue();
//                                break;
//                            case "Wire Gauge or Range - AWG":
//                                wireGaugeRangeAWG = entry.getValue();
//                                break;
//                            case "Wire Gauge or Range - mm²":
//                                wireGaugeRangeMM2 = entry.getValue();
//                                break;
//                            case "Fuse Type":
//                                fuseType = entry.getValue();
//                                break;
//                            case "Protocol":
//                                protocol = entry.getValue();
//                                break;
//                            case "Standards":
//                                standards = entry.getValue();
//                                break;
//                            case "Delay Time - ON":
//                                delayTimeOn = entry.getValue();
//                                break;
//                            case "Delay Time - OFF":
//                                delayTimeOff = entry.getValue();
//                                break;
//                            case "Amplifier Type":
//                                amplifierType = entry.getValue();
//                                break;
//                            case "Slew Rate":
//                                slewRate = entry.getValue();
//                                break;
//                            case "Gain Bandwidth Product":
//                                gainBandwidthProduct = entry.getValue();
//                                break;
//                            case "Current - Input Bias":
//                                currentInputBias = entry.getValue();
//                                break;
//                            case "Voltage - Input Offset":
//                                voltageInputOffset = entry.getValue();
//                                break;
//                            case "Voltage - Supply Span (Min)":
//                                voltageSupplySpanMin = entry.getValue();
//                                break;
//                            case "Voltage - Supply Span (Max)":
//                                voltageSupplySpanMax = entry.getValue();
//                                break;
//                            case "Style":
//                                style = entry.getValue();
//                                break;
//                            case "Key Removable Positions":
//                                keyRemovablePositions = entry.getValue();
//                                break;
//                            case "Angle of Throw":
//                                angleOfThrow = entry.getValue();
//                                break;
//                            case "Index Stops":
//                                indexStops = entry.getValue();
//                                break;
//                            case "Number of Decks":
//                                numberOfDecks = entry.getValue();
//                                break;
//                            case "Number of Poles per Deck":
//                                numberOfPolesPerDeck = entry.getValue();
//                                break;
//                            case "Circuit per Deck":
//                                circuitPerDeck = entry.getValue();
//                                break;
//                            case "Depth Behind Panel":
//                                depthBehindPanel = entry.getValue();
//                                break;
//                            case "Logic Type":
//                                logicType = entry.getValue();
//                                break;
//                            case "Supply Voltage":
//                                supplyVoltage = entry.getValue();
//                                break;
//                            case "Number of Bits":
//                                numberOfBits = entry.getValue();
//                                break;
//                            case "Requires (Select First, Then Apply Filters) Compatible Series":
//                                requiresCompatibleSeries = entry.getValue();
//                                break;
//                            case "Output Code":
//                                outputCode = entry.getValue();
//                                break;
//                            case "Read Out":
//                                readOut = entry.getValue();
//                                break;
//                            case "Number of Sections":
//                                numberOfSections = entry.getValue();
//                                break;
//                            case "Display Characters - Height":
//                                displayCharactersHeight = entry.getValue();
//                                break;
//                            case "End Caps":
//                                endCaps = entry.getValue();
//                                break;
//                            case "Section Width":
//                                sectionWidth = entry.getValue();
//                                break;
//                            case "Voltage":
//                                voltage = entry.getValue();
//                                break;
//                            case "Indicator":
//                                indicator = entry.getValue();
//                                break;
//                            case "1st Connector":
//                                firstConnector = entry.getValue();
//                                break;
//                            case "2nd Connector":
//                                secondConnector = entry.getValue();
//                                break;
//                            case "Approval Agency Marking":
//                                approvalAgencyMarking = entry.getValue();
//                                break;
//                            case "Approved Countries":
//                                approvedCountries = entry.getValue();
//                                break;
//                            case "Number of Keys":
//                                numberOfKeys = entry.getValue();
//                                break;
//                            case "Matrix (Columns x Rows)":
//                                matrixColumnsRows = entry.getValue();
//                                break;
//                            case "Legend Type":
//                                legendType = entry.getValue();
//                                break;
//                            case "Key Type":
//                                keyType = entry.getValue();
//                                break;
//                            case "Legend":
//                                legend = entry.getValue();
//                                break;
//                            case "Legend Color":
//                                legendColor = entry.getValue();
//                                break;
//                            case "Key Color":
//                                keyColor = entry.getValue();
//                                break;
//                            case "Current - Sensing":
//                                currentSensing = entry.getValue();
//                                break;
//                            case "Sensitivity":
//                                sensitivity = entry.getValue();
//                                break;
//                            case "Linearity":
//                                linearity = entry.getValue();
//                                break;
//                            case "Reset Operation":
//                                resetOperation = entry.getValue();
//                                break;
//                            case "Flow Sensor Type":
//                                flowSensorType = entry.getValue();
//                                break;
//                            case "Port Size":
//                                portSize = entry.getValue();
//                                break;
//                            case "Switch Function/Rating":
//                                switchFunctionRating = entry.getValue();
//                                break;
//                            case "Material - Body":
//                                materialBody = entry.getValue();
//                                break;
                                
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
                    Object[] row = new Object[]{manId, manName, manProductNumber, quantity, stat, capacitance,
                            tolerance, voltageRated, temperatureCoefficient, operatingTemperature, features, ratings,
                            applications, mountingType, packageCase, sizeDimension, heightSeatedMax, thicknessMax,
                            leadSpacing, leadStyle, failureRate, current, dcResistance, insertionLoss, heightMax, threadSize,
                            voltageRatingAC, voltageRatingDC, dieletricMaterial, termination,
                            eSR, lifetimeTemp, polarization, rippleCurrLowFreq, rippleCurrHighFreq, surfaceMLS,
                            type, frequency, frequencyStability, frequencyTolerance, impedance, height, manufSizeCode,
                             numOfCapacitors, circuitType, supplierDevicePackage,
                            voltageClamping, technology, numOfCircuits,
                             function, rfType, secondaryAttributes, capacitanceRange, adjustmentType, qFreq,
                            voltageBreakdown, esl, currentLeakage, dissipationFactor, topology, internalSwitch,
                            numOfOutputs, voltageSupplyMin, voltageSupplyMax, voltageOutput, currentOutputChannel,
                            dimming, accessoryType, relatedProducts, digiKeyProgrammable,
                             currentSupply, voltageSupply, filterType, freqCutofforCenter, numOfFilters, filterOrder,
                             deviceSize, specifications, batteryChemistry, batteryCellSize, capacity, terminationStyle,
                             width, airFlow, staticPressure, bearingType, fanType, noise, powerInWatts, rpm, ingressProtection,
                             approvalAgency, weight, kitType, quantity2, packagesIncluded,
                            shape, usage, material, color, lengthz, diameterOutside, diameterInside,
                             resistance, composition, numOfTerminations,
                            resistanceINOhms, numOfResistors, resistorMatchingRatio, resistorRatioDrift, numOfPins,
                            powerPerElement, grade, qualification, numOfTurns, resistiveMaterial,
                            sensingDistance, sensingMethod, outputConfig, currentDCForwardIfMax, currentCollectorIcMax,
                            voltageCollectorEmitBreakdownMax, responseTime, coilType, coilCurrent, coilVoltage,
                            contactForm, contactRatingCurrent, switchingVoltage, mustOperateVoltage, mustReleaseVoltage,
                            operatingTime, releaseTime, builtInSwitch, taper, numOfGangs,
                            rotation, actuatorType, actuatorLength, actuatorDiameter, bushingThread,
                            coatingHousingType, mountingFeature, connectorType, contactType, numOfPositions,
                            pitch, numberOfRows, rowSpacing, contactTermination, fasteningType,
                            insulationMaterial, count, connectorStyle, currentRatingAppInlet, voltageRatingAppInlet,
                            currentRatingFilter, switchFeatures, currentRatingCircuitBreaker, accomodatesAFuse, fuseHolderDrawer,
                            panelCutoutDim, panelThickness, materialFlammRating, configuration, transferRate,
                            wireGauge, shielding, cordType, cordTermination, cordLength,
                            strapClosure, strapMaterial, strapTermination, size,
                            inputType, outputType, shaftSize, coreProcessor, programMemoryType,
                            controllerSeries, ramSize, interfaceType, numberOfIO, numberOfConductors,
                            shellSizeInsert, shellSizeMIL, orientation, primaryMaterial, shellMaterial,
                            shellFinish, contactFinishMating, currentRatingAmps, voltageRating,
                            cableOpeningDiameter, operatingForce, output, sensorType,
                            compensatedTemperature, actuatorStyleSize, includes, displayType,
                            displayMode, touchscreen, diagonalScreenSize, viewingArea,
                            backlight, dotPixels, controllerType, graphicsColor,
                            backgroundColor, numberOfCharacters, contactSize, moduleCapacity,
                            numberOfInputsType, numberOfOutputsType, expandable, numberOfCharactersPerRow,
                            communications, memorySize, forMeasuring, cableOpening, diameter,
                            circuit, switchFunction, contactRatingAtVoltage, actuatorHeightOffPcbVertical, actuatorLengthRightAngle,
                            actuatorOrientation, outline, illumination, illuminationTypeColor, illuminationVoltageNominal,
                            shieldTermination, cableGroup, frequencyMax, numberOfPorts, housingColor,
                            centerContactMaterial, switchType, ratioInputOutput, voltageLoad, voltageSupplyVccVdd,
                            currentOutputMax, rdsOnTyp, faultProtection, switchCircuit, multiplexerDemultiplexerCircuit,
                            onStateResistanceMax, channelToChannelMatchingDeltaRon, voltageSupplySingleV, voltageSupplyDualV,
                            switchTimeTonToffMax, threedbBandwidth, chargeInjection, channelCapacitanceCsOffCdOff, currentLeakageIsOffMax,
                            crosstalk, sensingRange, testCondition, currentSupplyMax, voltageInputMin,
                            voltageInputMax, voltageOutputMinFixed, voltageOutputMax, currentOutput,
                            frequencySwitching, synchronousRectifier, frequencyRange, isolation, testFrequency,
                            p1dB, iip3, independentCircuits, currentOutputHighLow, voltageSupplySource,
                            actuatorLevel, contactMaterial, contactFinish, heightAboveBoard, washable,
                            numberOfChannels, releaseForce, operatingPosition, pretravel, differentialTravel,
                            overtravel, outputIsolation, dutyCycle, controlFeatures, gender,
                            industryRecognizedMatingDiameter, actualDiameter, numberOfPositionsContacts, insulationColor, matingLengthDepth,
                            contactTiming, signalLines, operateRange, releaseRange, currentSwitching,
                            voltageSwitchingDC, powerRated, lengthOverall, sizeBody, currentCarry,
                            outputPhases, dutyCycleMax, clockSync, serialInterfaces, voltageSwitchingAC,
                            sensingTemperatureLocal, sensingTemperatureRemote, resolution, accuracyHighestLowest, colorActuatorCap,
                            actuatorMarking, loadMaxSwitching, sealRating, coilInsulation, relayType,
                            cardType, insertRemovalMethod, ejectorSide, contactFinishThickness, functionsExtra,
                            fetType, voltageStartUp, timeFormat, dateFormat, voltageSupplyBattery,
                            currentTimekeepingMax, voltageCurrentOutput1, voltageCurrentOutput2, voltageCurrentOutput3, withLEDDriver,
                            withSupervisor, withSequencer, accuracy, voltageInput, numOfCells,
                            currentCharging, programmableFeatures, chargeCurrentMax, batteryPackVoltage,
                            actuatorMaterial, mustOperate, mustRelease, intendedChipset,
                            chipsetManufacturer, voltagePrimary, voltageAuxiliary, voltageIsolation,
                            inductanceAtFrequency, footprint, disconnectType, numberOfLevels,
                            terminalWidth, currentIEC, voltageIEC, currentUL,
                            voltageUL, wireGaugeRangeAWG, wireGaugeRangeMM2, fuseType,
                            protocol, standards, delayTimeOn, delayTimeOff,
                            amplifierType, slewRate, gainBandwidthProduct, currentInputBias,
                            voltageInputOffset, voltageSupplySpanMin, voltageSupplySpanMax, style,
                            keyRemovablePositions, angleOfThrow, indexStops, numberOfDecks,
                            numberOfPolesPerDeck, circuitPerDeck, depthBehindPanel, logicType,
                            supplyVoltage, numberOfBits, requiresCompatibleSeries, outputCode,
                            readOut, numberOfSections, displayCharactersHeight, endCaps,
                            sectionWidth, voltage, indicator, firstConnector, secondConnector, approvalAgencyMarking,
                            approvedCountries, numberOfKeys, matrixColumnsRows, legendType,
                            keyType, legend, legendColor, keyColor,
                            currentSensing, sensitivity, linearity, resetOperation,
                            flowSensorType, portSize, switchFunctionRating, materialBody,
                            baseProdId, baseProdName, productParId, productParName
                            };
                    capacitorTable.add(row);
                }
            }

            int tableColumns = 419;

            // Converting list to a 2D array for easy printing
            Object[][] table = new Object[capacitorTable.size()][tableColumns];
            for (int i = 0; i < capacitorTable.size(); i++) {
                Object[] rowData = (Object[]) capacitorTable.get(i);
                for (int j = 0; j < tableColumns; j++) {
                    table[i][j] = rowData[j];
                }
            }

            // Printing header
            for (int j = 0; j < tableColumns; j++) {
//                System.out.printf("%-40s", header[j]);
            }
//            System.out.println();

            // Printing table content
            for (Object[] objects : table) {
                for (int j = 0; j < tableColumns; j++) {
//                    System.out.printf("%-40s", objects[j]);
                }
//                System.out.println();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}