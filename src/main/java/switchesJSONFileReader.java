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

public class switchesJSONFileReader {

    public static void main(String[] args) {

//        String categoryFolder = args[0];
        String categoryFolder = "Switches";

        List<String> filesInDirectory = DirectoryFiler.getFileNamesInDirectory("Postman Exports/" + categoryFolder);

        System.out.println("Directory size: " + filesInDirectory.size());
        String KEYWORD = null;

        String filePath = null;
        HashSet<String> attributes = new HashSet<>();


        for (int k = 0; k < filesInDirectory.size(); k++) {
            KEYWORD = filesInDirectory.get(k);
            filePath = "Postman Exports/" + categoryFolder + "/" + KEYWORD;

            System.out.println("File read: " + KEYWORD);


            // List to store each row of the capacitor table
            List<Object> categoryTable = new ArrayList<>();

            // Map to store parameters for each capacitor
            Map<String, String> parametersList = new LinkedHashMap<>();

            Map<String, Long> priceBreakQuantity = new LinkedHashMap<>();
            Map<String, Double> priceUnitPrice = new LinkedHashMap<>();
            Map<String, Double> priceTotalPrice = new LinkedHashMap<>();

            // Array to store header values
            String[] header = {"manId", "manName", "manProductNumber", "quantity", "stat",
                    "baseProdId", "baseProdName", "productParId", "productParName",
                    "cableOpening", "numberOfKeys", "SFPXFPPorts", "serialInterfaces", "actuatorDiameter", "dielectricMaterial", "size", "copperPorts", "numberOfFilters", "displayMode",
                    "sectionWidth", "gender", "materialFlammabilityRating", "lightSource", "currentUL", "panelThickness", "forUseWithRelatedProducts", "includedMCUMPUBoards", "expandable",
                    "testFrequency", "loadForceDynamic", "embedded", "primaryAttributes", "switchTimeTonToffMax", "communications", "independentCircuits", "orientation", "illuminationVoltageNominal",
                    "thickness", "qualification", "insulationColor", "screwHeadType", "maximumCableLength", "filterType", "LEDdriverChannels", "impedance", "currentOutputHighLow", "lightColor",
                    "currentLeakageISoffMax", "ingressProtection", "outputType", "suggestedProgrammingEnvironment", "actuatorLengthRightAngle", "diameterOutside", "signalLines",
                    "channeltoChannelMatchingRon", "specifications", "output", "mediaLinesProtected", "contactFinishMating", "endCaps", "zeroCrossingCircuit", "lengthBelowHead", "SFPXFPIType",
                    "deviceSize", "minus3dbBandwidth", "voltageSwitchingAC", "peripherals", "numberOfSlotsRelays", "interconnectSystem", "length", "functionAudible", "numberOfCircuits",
                    "frequencyCutoffOrCenter", "interface", "operatingPressure", "legendText", "contactFinish", "controlDisplayType", "currentOutputMax", "voltageInputMin", "ejectorSide",
                    "transistorType", "wireGaugeOrRangeAWG", "connectorACOutput", "frequencyTransition", "operatingAngle", "color", "forMeasuring", "voltageLoad", "outputPhases",
                    "contactTiming", "currentOutput", "resetTemperature", "language", "legendType", "voltageStartUp", "currentStartup", "wireGaugeOrRangemm", "delayTimeOFF", "lensSize",
                    "illuminationTypeColor", "matingLengthDepth", "speed", "applications", "diameterInside", "voltageSupplyMin", "material", "ratioInputOutput", "barrelDepth", "fasteningType",
                    "currentIEC", "indicator", "arrangement", "hardwareType", "faultProtection", "accessoryType", "currentDCForwardIfMax", "diameterLabel", "currentSwitching", "headDiameter",
                    "indexStops", "insertionRemovalMethod", "currentOutputSourceSink", "voltageCollectorEmitterBreakdownMax", "onStateResistanceMax", "sensorType", "feedbackType", "keyColor",
                    "accomodatesAFuse", "outputIsolation", "programmableFeatures", "matrixColumnsxRows", "releaseRange", "holdingForce", "terminationStyle", "shielding", "currentSupply",
                    "contents", "flangeDiameter", "approvalAgencyMarking", "voltageSupplySingleDual", "holeDiameter", "currentRatingFilter", "legendSymbolOnly", "powerOutput", "energy",
                    "connectorType", "style", "width", "diameter", "mainPurpose", "usage", "synchronousRectifier", "applicationSpecifics", "outline", "lengthOverall", "numberOfOutputsAndType",
                    "switchCircuit", "powerMax", "acceleration", "gearReductionRatio", "DCcurrentGainhFEMinIcVce", "resetOperation", "polarization", "airFlow", "packageAccepted",
                    "voltagePeakReverseMax", "diodeType", "fiberType", "resistanceIfF", "voltageOffState", "packageCase", "disconnectType", "voltageIEC", "operatingTemperature",
                    "shaftSize", "staticdvdtMin", "currentCarry", "mustRelease", "keyRemovablePositions", "capacitanceVrF", "fitsFanSize", "voltageIsolation", "topology", "mountingType",
                    "ESREquivalentSeriesResistance", "faceSize", "resistance", "controlFeatures", "colorLegend", "pretravel", "connectivity", "outsidePipeDiameterMin", "currentOutputChannel",
                    "DigiKeyProgrammable", "frequencyRange", "plugType", "voltageInputMax", "readOut", "heightAboveBoard", "internalSwitchs", "supplierDevicePackage", "includes", "function",
                    "accuracy", "numberOfSections", "powerRated", "dataRate", "frequencySwitching", "voltageOutput", "voltageOutputMinFixed", "height", "P1dB", "differentialTravel",
                    "primaryMaterial", "actuatorHeightOffPCBVertical", "mode", "angleOfThrow", "driveType", "standards", "accuracyHighestLowest", "voltageRatingApplianceInlet",
                    "overtravel", "capacitance", "isolation", "bodyFinish", "rotationAngle", "colorActuatorCap", "actuatorMaterial", "configuration", "housingMaterial", "crosstalk",
                    "shellSizeMIL", "voltageSupplyDualV", "ACOutlets", "selectFirstThenApplyFiltersCompatibleSeries", "operateTime", "numberOfInputs", "compatibleTools", "utilizedICPart",
                    "materialBody", "sensingRange", "boardThickness", "insertionLoss", "construction", "numberOfPositionsContacts", "numberOfPolesPerDeck", "functionsExtra", "delayTimeON",
                    "portSize", "circuit", "switchFunction", "regionUtilized", "loadForceStatic", "dimming", "colorBackground", "heightSeatedMax", "currentRatingAmps", "shellFinish",
                    "operateRange", "lensStyle", "shellSizeInsert", "turnOnTime", "motorType", "dataRateMax", "protocol", "voltageInput", "numberOfPositions", "flashSize", "switchType",
                    "materialMagnet", "voltageSupplySingleV", "overloadProtection", "frequency", "currentRatingAC", "switchFeatures", "washable", "sensingTemperatureRemote", "sensingDistance",
                    "pressureType", "copperType", "relayType", "displayType", "connectorACInput", "efficiency", "voltageSupply", "approvedCountries", "codedKeyed", "releaseTime", "sizeBody",
                    "voltageForwardVfTyp", "strokeLength", "printType", "timingInitiateMethod", "architecture", "faderType", "delayTime", "keyType", "threadSize", "wavelength", "depthBehindPanel",
                    "powerWatts", "numberOfStacks", "standardNumber", "currentRatingDC", "fuseType", "outsidePipeDiameterMax", "currentSupplyMax", "voltageSupplySource", "workstand",
                    "industryRecognizedMatingDiameter", "baseUnit", "features", "RFType", "contactRatingVoltage", "currentTransmitting", "RAMSize", "tolerance", "numberOfCharactersPerRow",
                    "RdsOnTyp", "RFFamilyStandard", "mountingFeature", "switchingCycles", "currentRatingCircuitBreaker", "voltageUL", "numberOfPoles", "timingAdjustmentMethod", "location",
                    "sensingTemperatureLocal", "platform", "forUseWithRelatedManufacturer", "currentMax", "voltageRated", "currentSensing", "numberOfLevels", "channelCapacitanceCSoffCDooff",
                    "outputCode", "voltageRatingAC", "illumination", "suppliedIronTweezerHandle", "multiplexerDemultiplexerCircuit", "controlInterface", "fanAccessoryType", "cardType", "IIP3",
                    "testCondition", "legendColor", "voltageOutput2", "actuatorMarking", "voltageOutput3", "currentCollectorIcMax", "voltageOutput4", "approvalAgency", "suppliedTipsNozzles",
                    "contactFinishThickness", "nozzleOpening", "voltageOutput1", "sizeDimension", "lampType", "linearity", "dutyCycle", "current", "legend", "shape", "filterOrder", "releaseForce",
                    "lensTransparency", "responseTime", "sensingMethod", "circuitPerDeck", "numberOfPorts", "suppliedContents", "modulation", "contactMaterial", "pixelFormat", "chargeInjection",
                    "cableLength", "voltageSupplyVccVdd", "proximityDetection", "mustOperate", "actuatorLevel", "actuatorType", "LEDColor", "panelCutoutDimensions", "VceSaturationMaxIbIc",
                    "voltageOutputMax", "terminalWidth", "type", "voltageRatingDC", "sensitivity", "travelRange", "temperatureRange", "opticalPattern", "numberOfDecks", "voltageRating", "wattage",
                    "conduitThreadSize", "leadSpacing", "ratings", "operatingForce", "actuatorOrientation", "numberOfChannels", "technology", "currentReceiving", "currentRatingApplianceInlet",
                    "adjustmentType", "requires", "grade", "voltageBreakdown", "connectionMethod", "memorySize", "connectorStyle", "pitch", "protoBoardType", "shellMaterial", "resolution",
                    "fuseHolderDrawer", "coreProcessor", "operatingPosition", "viewingArea", "bushingThread", "viewingAngle", "voltageSwitchingDC", "kitType", "FETType", "portStyle",
                    "outputConfiguration", "actuatorLength", "switchingTemperature", "kitContents", "voltageSupplyMax", "voltage", "secondaryAttributes", "supplyVoltage", "breakerType",
                    "functionLighting", "displayCharactersHeight", "toolType", "inputType", "currentHoldIh", "switchesQuantity", "termination", "currentLEDTriggerIftMax", "slewRate", "clockSync",
                    "numberOfOutputs", "actualDiameter", "fiberPorts", "dutyCycleMax", "bodyMaterial", "headHeight", "cordLength", "numberOfInputsAndType", "plating", "currentCollectorCutoffMax",
                    "maximumPressure"
            };

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
            String cableOpening = null;
            String numberOfKeys = null;
            String SFPXFPPorts = null;
            String serialInterfaces = null;
            String actuatorDiameter = null;
            String dielectricMaterial = null;
            String size = null;
            String copperPorts = null;
            String numberOfFilters = null;
            String displayMode = null;
            String sectionWidth = null;
            String gender = null;
            String materialFlammabilityRating = null;
            String lightSource = null;
            String currentUL = null;
            String panelThickness = null;
            String forUseWithRelatedProducts = null;
            String includedMCUMPUBoards = null;
            String expandable = null;
            String testFrequency = null;
            String loadForceDynamic = null;
            String embedded = null;
            String primaryAttributes = null;
            String switchTimeTonToffMax = null;
            String communications = null;
            String independentCircuits = null;
            String orientation = null;
            String illuminationVoltageNominal = null;
            String thickness = null;
            String qualification = null;
            String insulationColor = null;
            String screwHeadType = null;
            String maximumCableLength = null;
            String filterType = null;
            String LEDdriverChannels = null;
            String impedance = null;
            String currentOutputHighLow = null;
            String lightColor = null;
            String currentLeakageISoffMax = null;
            String ingressProtection = null;
            String outputType = null;
            String suggestedProgrammingEnvironment = null;
            String actuatorLengthRightAngle = null;
            String diameterOutside = null;
            String signalLines = null;
            String channeltoChannelMatchingRon = null;
            String specifications = null;
            String output = null;
            String mediaLinesProtected = null;
            String contactFinishMating = null;
            String endCaps = null;
            String zeroCrossingCircuit = null;
            String lengthBelowHead = null;
            String SFPXFPIType = null;
            String deviceSize = null;
            String minus3dbBandwidth = null;
            String voltageSwitchingAC = null;
            String peripherals = null;
            String numberOfSlotsRelays = null;
            String interconnectSystem = null;
            String length = null;
            String functionAudible = null;
            String numberOfCircuits = null;
            String frequencyCutoffOrCenter = null;
            String switchesInterface = null;
            String operatingPressure = null;
            String legendText = null;
            String contactFinish = null;
            String controlDisplayType = null;
            String currentOutputMax = null;
            String voltageInputMin = null;
            String ejectorSide = null;
            String transistorType = null;
            String wireGaugeOrRangeAWG = null;
            String connectorACOutput = null;
            String frequencyTransition = null;
            String operatingAngle = null;
            String color = null;
            String forMeasuring = null;
            String voltageLoad = null;
            String outputPhases = null;
            String contactTiming = null;
            String currentOutput = null;
            String resetTemperature = null;
            String language = null;
            String legendType = null;
            String voltageStartUp = null;
            String currentStartup = null;
            String wireGaugeOrRangemm = null;
            String delayTimeOFF = null;
            String lensSize = null;
            String illuminationTypeColor = null;
            String matingLengthDepth = null;
            String speed = null;
            String applications = null;
            String diameterInside = null;
            String voltageSupplyMin = null;
            String material = null;
            String ratioInputOutput = null;
            String barrelDepth = null;
            String fasteningType = null;
            String currentIEC = null;
            String indicator = null;
            String arrangement = null;
            String hardwareType = null;
            String faultProtection = null;
            String accessoryType = null;
            String currentDCForwardIfMax = null;
            String diameterLabel = null;
            String currentSwitching = null;
            String headDiameter = null;
            String indexStops = null;
            String insertionRemovalMethod = null;
            String currentOutputSourceSink = null;
            String voltageCollectorEmitterBreakdownMax = null;
            String onStateResistanceMax = null;
            String sensorType = null;
            String feedbackType = null;
            String keyColor = null;
            String accomodatesAFuse = null;
            String outputIsolation = null;
            String programmableFeatures = null;
            String matrixColumnsxRows = null;
            String releaseRange = null;
            String holdingForce = null;
            String terminationStyle = null;
            String shielding = null;
            String currentSupply = null;
            String contents = null;
            String flangeDiameter = null;
            String approvalAgencyMarking = null;
            String voltageSupplySingleDual = null;
            String holeDiameter = null;
            String currentRatingFilter = null;
            String legendSymbolOnly = null;
            String powerOutput = null;
            String energy = null;
            String connectorType = null;
            String style = null;
            String width = null;
            String diameter = null;
            String mainPurpose = null;
            String usage = null;
            String synchronousRectifier = null;
            String applicationSpecifics = null;
            String outline = null;
            String lengthOverall = null;
            String numberOfOutputsAndType = null;
            String switchCircuit = null;
            String powerMax = null;
            String acceleration = null;
            String gearReductionRatio = null;
            String DCcurrentGainhFEMinIcVce = null;
            String resetOperation = null;
            String polarization = null;
            String airFlow = null;
            String packageAccepted = null;
            String voltagePeakReverseMax = null;
            String diodeType = null;
            String fiberType = null;
            String resistanceIfF = null;
            String voltageOffState = null;
            String packageCase = null;
            String disconnectType = null;
            String voltageIEC = null;
            String operatingTemperature = null;
            String shaftSize = null;
            String staticdvdtMin = null;
            String currentCarry = null;
            String mustRelease = null;
            String keyRemovablePositions = null;
            String capacitanceVrF = null;
            String fitsFanSize = null;
            String voltageIsolation = null;
            String topology = null;
            String mountingType = null;
            String ESREquivalentSeriesResistance = null;
            String faceSize = null;
            String resistance = null;
            String controlFeatures = null;
            String colorLegend = null;
            String pretravel = null;
            String connectivity = null;
            String outsidePipeDiameterMin = null;
            String currentOutputChannel = null;
            String DigiKeyProgrammable = null;
            String frequencyRange = null;
            String plugType = null;
            String voltageInputMax = null;
            String readOut = null;
            String heightAboveBoard = null;
            String internalSwitchs = null;
            String supplierDevicePackage = null;
            String includes = null;
            String function = null;
            String accuracy = null;
            String numberOfSections = null;
            String powerRated = null;
            String dataRate = null;
            String frequencySwitching = null;
            String voltageOutput = null;
            String voltageOutputMinFixed = null;
            String height = null;
            String P1dB = null;
            String differentialTravel = null;
            String primaryMaterial = null;
            String actuatorHeightOffPCBVertical = null;
            String mode = null;
            String angleOfThrow = null;
            String driveType = null;
            String standards = null;
            String accuracyHighestLowest = null;
            String voltageRatingApplianceInlet = null;
            String overtravel = null;
            String capacitance = null;
            String isolation = null;
            String bodyFinish = null;
            String rotationAngle = null;
            String colorActuatorCap = null;
            String actuatorMaterial = null;
            String configuration = null;
            String housingMaterial = null;
            String crosstalk = null;
            String shellSizeMIL = null;
            String voltageSupplyDualV = null;
            String ACOutlets = null;
            String selectFirstThenApplyFiltersCompatibleSeries = null;
            String operateTime = null;
            String numberOfInputs = null;
            String compatibleTools = null;
            String utilizedICPart = null;
            String materialBody = null;
            String sensingRange = null;
            String boardThickness = null;
            String insertionLoss = null;
            String construction = null;
            String numberOfPositionsContacts = null;
            String numberOfPolesPerDeck = null;
            String functionsExtra = null;
            String delayTimeON = null;
            String portSize = null;
            String circuit = null;
            String switchFunction = null;
            String regionUtilized = null;
            String loadForceStatic = null;
            String dimming = null;
            String colorBackground = null;
            String heightSeatedMax = null;
            String currentRatingAmps = null;
            String shellFinish = null;
            String operateRange = null;
            String lensStyle = null;
            String shellSizeInsert = null;
            String turnOnTime = null;
            String motorType = null;
            String dataRateMax = null;
            String protocol = null;
            String voltageInput = null;
            String numberOfPositions = null;
            String flashSize = null;
            String switchType = null;
            String materialMagnet = null;
            String voltageSupplySingleV = null;
            String overloadProtection = null;
            String frequency = null;
            String currentRatingAC = null;
            String switchFeatures = null;
            String washable = null;
            String sensingTemperatureRemote = null;
            String sensingDistance = null;
            String pressureType = null;
            String copperType = null;
            String relayType = null;
            String displayType = null;
            String connectorACInput = null;
            String efficiency = null;
            String voltageSupply = null;
            String approvedCountries = null;
            String codedKeyed = null;
            String releaseTime = null;
            String sizeBody = null;
            String voltageForwardVfTyp = null;
            String strokeLength = null;
            String printType = null;
            String timingInitiateMethod = null;
            String architecture = null;
            String faderType = null;
            String delayTime = null;
            String keyType = null;
            String threadSize = null;
            String wavelength = null;
            String depthBehindPanel = null;
            String powerWatts = null;
            String numberOfStacks = null;
            String standardNumber = null;
            String currentRatingDC = null;
            String fuseType = null;
            String outsidePipeDiameterMax = null;
            String currentSupplyMax = null;
            String voltageSupplySource = null;
            String workstand = null;
            String industryRecognizedMatingDiameter = null;
            String baseUnit = null;
            String features = null;
            String RFType = null;
            String contactRatingVoltage = null;
            String currentTransmitting = null;
            String RAMSize = null;
            String tolerance = null;
            String numberOfCharactersPerRow = null;
            String RdsOnTyp = null;
            String RFFamilyStandard = null;
            String mountingFeature = null;
            String switchingCycles = null;
            String currentRatingCircuitBreaker = null;
            String voltageUL = null;
            String numberOfPoles = null;
            String timingAdjustmentMethod = null;
            String location = null;
            String sensingTemperatureLocal = null;
            String platform = null;
            String forUseWithRelatedManufacturer = null;
            String currentMax = null;
            String voltageRated = null;
            String currentSensing = null;
            String numberOfLevels = null;
            String channelCapacitanceCSoffCDooff = null;
            String outputCode = null;
            String voltageRatingAC = null;
            String illumination = null;
            String suppliedIronTweezerHandle = null;
            String multiplexerDemultiplexerCircuit = null;
            String controlInterface = null;
            String fanAccessoryType = null;
            String cardType = null;
            String IIP3 = null;
            String testCondition = null;
            String legendColor = null;
            String voltageOutput2 = null;
            String actuatorMarking = null;
            String voltageOutput3 = null;
            String currentCollectorIcMax = null;
            String voltageOutput4 = null;
            String approvalAgency = null;
            String suppliedTipsNozzles = null;
            String contactFinishThickness = null;
            String nozzleOpening = null;
            String voltageOutput1 = null;
            String sizeDimension = null;
            String lampType = null;
            String linearity = null;
            String dutyCycle = null;
            String current = null;
            String legend = null;
            String shape = null;
            String filterOrder = null;
            String releaseForce = null;
            String lensTransparency = null;
            String responseTime = null;
            String sensingMethod = null;
            String circuitPerDeck = null;
            String numberOfPorts = null;
            String suppliedContents = null;
            String modulation = null;
            String contactMaterial = null;
            String pixelFormat = null;
            String chargeInjection = null;
            String cableLength = null;
            String voltageSupplyVccVdd = null;
            String proximityDetection = null;
            String mustOperate = null;
            String actuatorLevel = null;
            String actuatorType = null;
            String LEDColor = null;
            String panelCutoutDimensions = null;
            String VceSaturationMaxIbIc = null;
            String voltageOutputMax = null;
            String terminalWidth = null;
            String type = null;
            String voltageRatingDC = null;
            String sensitivity = null;
            String travelRange = null;
            String temperatureRange = null;
            String opticalPattern = null;
            String numberOfDecks = null;
            String voltageRating = null;
            String wattage = null;
            String conduitThreadSize = null;
            String leadSpacing = null;
            String ratings = null;
            String operatingForce = null;
            String actuatorOrientation = null;
            String numberOfChannels = null;
            String technology = null;
            String currentReceiving = null;
            String currentRatingApplianceInlet = null;
            String adjustmentType = null;
            String requires = null;
            String grade = null;
            String voltageBreakdown = null;
            String connectionMethod = null;
            String memorySize = null;
            String connectorStyle = null;
            String pitch = null;
            String protoBoardType = null;
            String shellMaterial = null;
            String resolution = null;
            String fuseHolderDrawer = null;
            String coreProcessor = null;
            String operatingPosition = null;
            String viewingArea = null;
            String bushingThread = null;
            String viewingAngle = null;
            String voltageSwitchingDC = null;
            String kitType = null;
            String FETType = null;
            String portStyle = null;
            String outputConfiguration = null;
            String actuatorLength = null;
            String switchingTemperature = null;
            String kitContents = null;
            String voltageSupplyMax = null;
            String voltage = null;
            String secondaryAttributes = null;
            String supplyVoltage = null;
            String breakerType = null;
            String functionLighting = null;
            String displayCharactersHeight = null;
            String toolType = null;
            String inputType = null;
            String currentHoldIh = null;
            String switchesQuantity = null;
            String termination = null;
            String currentLEDTriggerIftMax = null;
            String slewRate = null;
            String clockSync = null;
            String numberOfOutputs = null;
            String actualDiameter = null;
            String fiberPorts = null;
            String dutyCycleMax = null;
            String bodyMaterial = null;
            String headHeight = null;
            String cordLength = null;
            String numberOfInputsAndType = null;
            String plating = null;
            String currentCollectorCutoffMax = null;
            String maximumPressure = null;


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
                                case "cableOpening":
                                    cableOpening = entry.getValue();
                                    break;
                                case "numberOfKeys":
                                    numberOfKeys = entry.getValue();
                                    break;
                                case "SFPXFPPorts":
                                    SFPXFPPorts = entry.getValue();
                                    break;
                                case "serialInterfaces":
                                    serialInterfaces = entry.getValue();
                                    break;
                                case "actuatorDiameter":
                                    actuatorDiameter = entry.getValue();
                                    break;
                                case "dielectricMaterial":
                                    dielectricMaterial = entry.getValue();
                                    break;
                                case "size":
                                    size = entry.getValue();
                                    break;
                                case "copperPorts":
                                    copperPorts = entry.getValue();
                                    break;
                                case "numberOfFilters":
                                    numberOfFilters = entry.getValue();
                                    break;
                                case "displayMode":
                                    displayMode = entry.getValue();
                                    break;
                                case "sectionWidth":
                                    sectionWidth = entry.getValue();
                                    break;
                                case "gender":
                                    gender = entry.getValue();
                                    break;
                                case "materialFlammabilityRating":
                                    materialFlammabilityRating = entry.getValue();
                                    break;
                                case "lightSource":
                                    lightSource = entry.getValue();
                                    break;
                                case "currentUL":
                                    currentUL = entry.getValue();
                                    break;
                                case "panelThickness":
                                    panelThickness = entry.getValue();
                                    break;
                                case "forUseWithRelatedProducts":
                                    forUseWithRelatedProducts = entry.getValue();
                                    break;
                                case "includedMCUMPUBoards":
                                    includedMCUMPUBoards = entry.getValue();
                                    break;
                                case "expandable":
                                    expandable = entry.getValue();
                                    break;
                                case "testFrequency":
                                    testFrequency = entry.getValue();
                                    break;
                                case "loadForceDynamic":
                                    loadForceDynamic = entry.getValue();
                                    break;
                                case "embedded":
                                    embedded = entry.getValue();
                                    break;
                                case "primaryAttributes":
                                    primaryAttributes = entry.getValue();
                                    break;
                                case "switchTimeTonToffMax":
                                    switchTimeTonToffMax = entry.getValue();
                                    break;
                                case "communications":
                                    communications = entry.getValue();
                                    break;
                                case "independentCircuits":
                                    independentCircuits = entry.getValue();
                                    break;
                                case "orientation":
                                    orientation = entry.getValue();
                                    break;
                                case "illuminationVoltageNominal":
                                    illuminationVoltageNominal = entry.getValue();
                                    break;
                                case "thickness":
                                    thickness = entry.getValue();
                                    break;
                                case "qualification":
                                    qualification = entry.getValue();
                                    break;
                                case "insulationColor":
                                    insulationColor = entry.getValue();
                                    break;
                                case "screwHeadType":
                                    screwHeadType = entry.getValue();
                                    break;
                                case "maximumCableLength":
                                    maximumCableLength = entry.getValue();
                                    break;
                                case "filterType":
                                    filterType = entry.getValue();
                                    break;
                                case "LEDdriverChannels":
                                    LEDdriverChannels = entry.getValue();
                                    break;
                                case "impedance":
                                    impedance = entry.getValue();
                                    break;
                                case "currentOutputHighLow":
                                    currentOutputHighLow = entry.getValue();
                                    break;
                                case "lightColor":
                                    lightColor = entry.getValue();
                                    break;
                                case "currentLeakageISoffMax":
                                    currentLeakageISoffMax = entry.getValue();
                                    break;
                                case "ingressProtection":
                                    ingressProtection = entry.getValue();
                                    break;
                                case "outputType":
                                    outputType = entry.getValue();
                                    break;
                                case "suggestedProgrammingEnvironment":
                                    suggestedProgrammingEnvironment = entry.getValue();
                                    break;
                                case "actuatorLengthRightAngle":
                                    actuatorLengthRightAngle = entry.getValue();
                                    break;
                                case "diameterOutside":
                                    diameterOutside = entry.getValue();
                                    break;
                                case "signalLines":
                                    signalLines = entry.getValue();
                                    break;
                                case "channeltoChannelMatchingRon":
                                    channeltoChannelMatchingRon = entry.getValue();
                                    break;
                                case "specifications":
                                    specifications = entry.getValue();
                                    break;
                                case "output":
                                    output = entry.getValue();
                                    break;
                                case "mediaLinesProtected":
                                    mediaLinesProtected = entry.getValue();
                                    break;
                                case "contactFinishMating":
                                    contactFinishMating = entry.getValue();
                                    break;
                                case "endCaps":
                                    endCaps = entry.getValue();
                                    break;
                                case "zeroCrossingCircuit":
                                    zeroCrossingCircuit = entry.getValue();
                                    break;
                                case "lengthBelowHead":
                                    lengthBelowHead = entry.getValue();
                                    break;
                                case "SFPXFPIType":
                                    SFPXFPIType = entry.getValue();
                                    break;
                                case "deviceSize":
                                    deviceSize = entry.getValue();
                                    break;
                                case "minus3dbBandwidth":
                                    minus3dbBandwidth = entry.getValue();
                                    break;
                                case "voltageSwitchingAC":
                                    voltageSwitchingAC = entry.getValue();
                                    break;
                                case "peripherals":
                                    peripherals = entry.getValue();
                                    break;
                                case "numberOfSlotsRelays":
                                    numberOfSlotsRelays = entry.getValue();
                                    break;
                                case "interconnectSystem":
                                    interconnectSystem = entry.getValue();
                                    break;
                                case "length":
                                    length = entry.getValue();
                                    break;
                                case "functionAudible":
                                    functionAudible = entry.getValue();
                                    break;
                                case "numberOfCircuits":
                                    numberOfCircuits = entry.getValue();
                                    break;
                                case "frequencyCutoffOrCenter":
                                    frequencyCutoffOrCenter = entry.getValue();
                                    break;
                                case "switchesInterface":
                                    switchesInterface = entry.getValue();
                                    break;
                                case "operatingPressure":
                                    operatingPressure = entry.getValue();
                                    break;
                                case "legendText":
                                    legendText = entry.getValue();
                                    break;
                                case "contactFinish":
                                    contactFinish = entry.getValue();
                                    break;
                                case "controlDisplayType":
                                    controlDisplayType = entry.getValue();
                                    break;
                                case "currentOutputMax":
                                    currentOutputMax = entry.getValue();
                                    break;
                                case "voltageInputMin":
                                    voltageInputMin = entry.getValue();
                                    break;
                                case "ejectorSide":
                                    ejectorSide = entry.getValue();
                                    break;
                                case "transistorType":
                                    transistorType = entry.getValue();
                                    break;
                                case "wireGaugeOrRangeAWG":
                                    wireGaugeOrRangeAWG = entry.getValue();
                                    break;
                                case "connectorACOutput":
                                    connectorACOutput = entry.getValue();
                                    break;
                                case "frequencyTransition":
                                    frequencyTransition = entry.getValue();
                                    break;
                                case "operatingAngle":
                                    operatingAngle = entry.getValue();
                                    break;
                                case "color":
                                    color = entry.getValue();
                                    break;
                                case "forMeasuring":
                                    forMeasuring = entry.getValue();
                                    break;
                                case "voltageLoad":
                                    voltageLoad = entry.getValue();
                                    break;
                                case "outputPhases":
                                    outputPhases = entry.getValue();
                                    break;
                                case "contactTiming":
                                    contactTiming = entry.getValue();
                                    break;
                                case "currentOutput":
                                    currentOutput = entry.getValue();
                                    break;
                                case "resetTemperature":
                                    resetTemperature = entry.getValue();
                                    break;
                                case "language":
                                    language = entry.getValue();
                                    break;
                                case "legendType":
                                    legendType = entry.getValue();
                                    break;
                                case "voltageStartUp":
                                    voltageStartUp = entry.getValue();
                                    break;
                                case "currentStartup":
                                    currentStartup = entry.getValue();
                                    break;
                                case "wireGaugeOrRangemm":
                                    wireGaugeOrRangemm = entry.getValue();
                                    break;
                                case "delayTimeOFF":
                                    delayTimeOFF = entry.getValue();
                                    break;
                                case "lensSize":
                                    lensSize = entry.getValue();
                                    break;
                                case "illuminationTypeColor":
                                    illuminationTypeColor = entry.getValue();
                                    break;
                                case "matingLengthDepth":
                                    matingLengthDepth = entry.getValue();
                                    break;
                                case "speed":
                                    speed = entry.getValue();
                                    break;
                                case "applications":
                                    applications = entry.getValue();
                                    break;
                                case "diameterInside":
                                    diameterInside = entry.getValue();
                                    break;
                                case "voltageSupplyMin":
                                    voltageSupplyMin = entry.getValue();
                                    break;
                                case "material":
                                    material = entry.getValue();
                                    break;
                                case "ratioInputOutput":
                                    ratioInputOutput = entry.getValue();
                                    break;
                                case "barrelDepth":
                                    barrelDepth = entry.getValue();
                                    break;
                                case "fasteningType":
                                    fasteningType = entry.getValue();
                                    break;
                                case "currentIEC":
                                    currentIEC = entry.getValue();
                                    break;
                                case "indicator":
                                    indicator = entry.getValue();
                                    break;
                                case "arrangement":
                                    arrangement = entry.getValue();
                                    break;
                                case "hardwareType":
                                    hardwareType = entry.getValue();
                                    break;
                                case "faultProtection":
                                    faultProtection = entry.getValue();
                                    break;
                                case "accessoryType":
                                    accessoryType = entry.getValue();
                                    break;
                                case "currentDCForwardIfMax":
                                    currentDCForwardIfMax = entry.getValue();
                                    break;
                                case "diameterLabel":
                                    diameterLabel = entry.getValue();
                                    break;
                                case "currentSwitching":
                                    currentSwitching = entry.getValue();
                                    break;
                                case "headDiameter":
                                    headDiameter = entry.getValue();
                                    break;
                                case "indexStops":
                                    indexStops = entry.getValue();
                                    break;
                                case "insertionRemovalMethod":
                                    insertionRemovalMethod = entry.getValue();
                                    break;
                                case "currentOutputSourceSink":
                                    currentOutputSourceSink = entry.getValue();
                                    break;
                                case "voltageCollectorEmitterBreakdownMax":
                                    voltageCollectorEmitterBreakdownMax = entry.getValue();
                                    break;
                                case "onStateResistanceMax":
                                    onStateResistanceMax = entry.getValue();
                                    break;
                                case "sensorType":
                                    sensorType = entry.getValue();
                                    break;
                                case "feedbackType":
                                    feedbackType = entry.getValue();
                                    break;
                                case "keyColor":
                                    keyColor = entry.getValue();
                                    break;
                                case "accomodatesAFuse":
                                    accomodatesAFuse = entry.getValue();
                                    break;
                                case "outputIsolation":
                                    outputIsolation = entry.getValue();
                                    break;
                                case "programmableFeatures":
                                    programmableFeatures = entry.getValue();
                                    break;
                                case "matrixColumnsxRows":
                                    matrixColumnsxRows = entry.getValue();
                                    break;
                                case "releaseRange":
                                    releaseRange = entry.getValue();
                                    break;
                                case "holdingForce":
                                    holdingForce = entry.getValue();
                                    break;
                                case "terminationStyle":
                                    terminationStyle = entry.getValue();
                                    break;
                                case "shielding":
                                    shielding = entry.getValue();
                                    break;
                                case "currentSupply":
                                    currentSupply = entry.getValue();
                                    break;
                                case "contents":
                                    contents = entry.getValue();
                                    break;
                                case "flangeDiameter":
                                    flangeDiameter = entry.getValue();
                                    break;
                                case "approvalAgencyMarking":
                                    approvalAgencyMarking = entry.getValue();
                                    break;
                                case "voltageSupplySingleDual":
                                    voltageSupplySingleDual = entry.getValue();
                                    break;
                                case "holeDiameter":
                                    holeDiameter = entry.getValue();
                                    break;
                                case "currentRatingFilter":
                                    currentRatingFilter = entry.getValue();
                                    break;
                                case "legendSymbolOnly":
                                    legendSymbolOnly = entry.getValue();
                                    break;
                                case "powerOutput":
                                    powerOutput = entry.getValue();
                                    break;
                                case "energy":
                                    energy = entry.getValue();
                                    break;
                                case "connectorType":
                                    connectorType = entry.getValue();
                                    break;
                                case "style":
                                    style = entry.getValue();
                                    break;
                                case "width":
                                    width = entry.getValue();
                                    break;
                                case "diameter":
                                    diameter = entry.getValue();
                                    break;
                                case "mainPurpose":
                                    mainPurpose = entry.getValue();
                                    break;
                                case "usage":
                                    usage = entry.getValue();
                                    break;
                                case "synchronousRectifier":
                                    synchronousRectifier = entry.getValue();
                                    break;
                                case "applicationSpecifics":
                                    applicationSpecifics = entry.getValue();
                                    break;
                                case "outline":
                                    outline = entry.getValue();
                                    break;
                                case "lengthOverall":
                                    lengthOverall = entry.getValue();
                                    break;
                                case "numberOfOutputsAndType":
                                    numberOfOutputsAndType = entry.getValue();
                                    break;
                                case "switchCircuit":
                                    switchCircuit = entry.getValue();
                                    break;
                                case "powerMax":
                                    powerMax = entry.getValue();
                                    break;
                                case "acceleration":
                                    acceleration = entry.getValue();
                                    break;
                                case "gearReductionRatio":
                                    gearReductionRatio = entry.getValue();
                                    break;
                                case "DCcurrentGainhFEMinIcVce":
                                    DCcurrentGainhFEMinIcVce = entry.getValue();
                                    break;
                                case "resetOperation":
                                    resetOperation = entry.getValue();
                                    break;
                                case "polarization":
                                    polarization = entry.getValue();
                                    break;
                                case "airFlow":
                                    airFlow = entry.getValue();
                                    break;
                                case "packageAccepted":
                                    packageAccepted = entry.getValue();
                                    break;
                                case "voltagePeakReverseMax":
                                    voltagePeakReverseMax = entry.getValue();
                                    break;
                                case "diodeType":
                                    diodeType = entry.getValue();
                                    break;
                                case "fiberType":
                                    fiberType = entry.getValue();
                                    break;
                                case "resistanceIfF":
                                    resistanceIfF = entry.getValue();
                                    break;
                                case "voltageOffState":
                                    voltageOffState = entry.getValue();
                                    break;
                                case "packageCase":
                                    packageCase = entry.getValue();
                                    break;
                                case "disconnectType":
                                    disconnectType = entry.getValue();
                                    break;
                                case "voltageIEC":
                                    voltageIEC = entry.getValue();
                                    break;
                                case "operatingTemperature":
                                    operatingTemperature = entry.getValue();
                                    break;
                                case "shaftSize":
                                    shaftSize = entry.getValue();
                                    break;
                                case "staticdvdtMin":
                                    staticdvdtMin = entry.getValue();
                                    break;
                                case "currentCarry":
                                    currentCarry = entry.getValue();
                                    break;
                                case "mustRelease":
                                    mustRelease = entry.getValue();
                                    break;
                                case "keyRemovablePositions":
                                    keyRemovablePositions = entry.getValue();
                                    break;
                                case "capacitanceVrF":
                                    capacitanceVrF = entry.getValue();
                                    break;
                                case "fitsFanSize":
                                    fitsFanSize = entry.getValue();
                                    break;
                                case "voltageIsolation":
                                    voltageIsolation = entry.getValue();
                                    break;
                                case "topology":
                                    topology = entry.getValue();
                                    break;
                                case "mountingType":
                                    mountingType = entry.getValue();
                                    break;
                                case "ESREquivalentSeriesResistance":
                                    ESREquivalentSeriesResistance = entry.getValue();
                                    break;
                                case "faceSize":
                                    faceSize = entry.getValue();
                                    break;
                                case "resistance":
                                    resistance = entry.getValue();
                                    break;
                                case "controlFeatures":
                                    controlFeatures = entry.getValue();
                                    break;
                                case "colorLegend":
                                    colorLegend = entry.getValue();
                                    break;
                                case "pretravel":
                                    pretravel = entry.getValue();
                                    break;
                                case "connectivity":
                                    connectivity = entry.getValue();
                                    break;
                                case "outsidePipeDiameterMin":
                                    outsidePipeDiameterMin = entry.getValue();
                                    break;
                                case "currentOutputChannel":
                                    currentOutputChannel = entry.getValue();
                                    break;
                                case "DigiKeyProgrammable":
                                    DigiKeyProgrammable = entry.getValue();
                                    break;
                                case "frequencyRange":
                                    frequencyRange = entry.getValue();
                                    break;
                                case "plugType":
                                    plugType = entry.getValue();
                                    break;
                                case "voltageInputMax":
                                    voltageInputMax = entry.getValue();
                                    break;
                                case "readOut":
                                    readOut = entry.getValue();
                                    break;
                                case "heightAboveBoard":
                                    heightAboveBoard = entry.getValue();
                                    break;
                                case "internalSwitchs":
                                    internalSwitchs = entry.getValue();
                                    break;
                                case "supplierDevicePackage":
                                    supplierDevicePackage = entry.getValue();
                                    break;
                                case "includes":
                                    includes = entry.getValue();
                                    break;
                                case "function":
                                    function = entry.getValue();
                                    break;
                                case "accuracy":
                                    accuracy = entry.getValue();
                                    break;
                                case "numberOfSections":
                                    numberOfSections = entry.getValue();
                                    break;
                                case "powerRated":
                                    powerRated = entry.getValue();
                                    break;
                                case "dataRate":
                                    dataRate = entry.getValue();
                                    break;
                                case "frequencySwitching":
                                    frequencySwitching = entry.getValue();
                                    break;
                                case "voltageOutput":
                                    voltageOutput = entry.getValue();
                                    break;
                                case "voltageOutputMinFixed":
                                    voltageOutputMinFixed = entry.getValue();
                                    break;
                                case "height":
                                    height = entry.getValue();
                                    break;
                                case "P1dB":
                                    P1dB = entry.getValue();
                                    break;
                                case "differentialTravel":
                                    differentialTravel = entry.getValue();
                                    break;
                                case "primaryMaterial":
                                    primaryMaterial = entry.getValue();
                                    break;
                                case "actuatorHeightOffPCBVertical":
                                    actuatorHeightOffPCBVertical = entry.getValue();
                                    break;
                                case "mode":
                                    mode = entry.getValue();
                                    break;
                                case "angleOfThrow":
                                    angleOfThrow = entry.getValue();
                                    break;
                                case "driveType":
                                    driveType = entry.getValue();
                                    break;
                                case "standards":
                                    standards = entry.getValue();
                                    break;
                                case "accuracyHighestLowest":
                                    accuracyHighestLowest = entry.getValue();
                                    break;
                                case "voltageRatingApplianceInlet":
                                    voltageRatingApplianceInlet = entry.getValue();
                                    break;
                                case "overtravel":
                                    overtravel = entry.getValue();
                                    break;
                                case "capacitance":
                                    capacitance = entry.getValue();
                                    break;
                                case "isolation":
                                    isolation = entry.getValue();
                                    break;
                                case "bodyFinish":
                                    bodyFinish = entry.getValue();
                                    break;
                                case "rotationAngle":
                                    rotationAngle = entry.getValue();
                                    break;
                                case "colorActuatorCap":
                                    colorActuatorCap = entry.getValue();
                                    break;
                                case "actuatorMaterial":
                                    actuatorMaterial = entry.getValue();
                                    break;
                                case "configuration":
                                    configuration = entry.getValue();
                                    break;
                                case "housingMaterial":
                                    housingMaterial = entry.getValue();
                                    break;
                                case "crosstalk":
                                    crosstalk = entry.getValue();
                                    break;
                                case "shellSizeMIL":
                                    shellSizeMIL = entry.getValue();
                                    break;
                                case "voltageSupplyDualV":
                                    voltageSupplyDualV = entry.getValue();
                                    break;
                                case "ACOutlets":
                                    ACOutlets = entry.getValue();
                                    break;
                                case "selectFirstThenApplyFiltersCompatibleSeries":
                                    selectFirstThenApplyFiltersCompatibleSeries = entry.getValue();
                                    break;
                                case "operateTime":
                                    operateTime = entry.getValue();
                                    break;
                                case "numberOfInputs":
                                    numberOfInputs = entry.getValue();
                                    break;
                                case "compatibleTools":
                                    compatibleTools = entry.getValue();
                                    break;
                                case "utilizedICPart":
                                    utilizedICPart = entry.getValue();
                                    break;
                                case "materialBody":
                                    materialBody = entry.getValue();
                                    break;
                                case "sensingRange":
                                    sensingRange = entry.getValue();
                                    break;
                                case "boardThickness":
                                    boardThickness = entry.getValue();
                                    break;
                                case "insertionLoss":
                                    insertionLoss = entry.getValue();
                                    break;
                                case "construction":
                                    construction = entry.getValue();
                                    break;
                                case "numberOfPositionsContacts":
                                    numberOfPositionsContacts = entry.getValue();
                                    break;
                                case "numberOfPolesPerDeck":
                                    numberOfPolesPerDeck = entry.getValue();
                                    break;
                                case "functionsExtra":
                                    functionsExtra = entry.getValue();
                                    break;
                                case "delayTimeON":
                                    delayTimeON = entry.getValue();
                                    break;
                                case "portSize":
                                    portSize = entry.getValue();
                                    break;
                                case "circuit":
                                    circuit = entry.getValue();
                                    break;
                                case "switchFunction":
                                    switchFunction = entry.getValue();
                                    break;
                                case "regionUtilized":
                                    regionUtilized = entry.getValue();
                                    break;
                                case "loadForceStatic":
                                    loadForceStatic = entry.getValue();
                                    break;
                                case "dimming":
                                    dimming = entry.getValue();
                                    break;
                                case "colorBackground":
                                    colorBackground = entry.getValue();
                                    break;
                                case "heightSeatedMax":
                                    heightSeatedMax = entry.getValue();
                                    break;
                                case "currentRatingAmps":
                                    currentRatingAmps = entry.getValue();
                                    break;
                                case "shellFinish":
                                    shellFinish = entry.getValue();
                                    break;
                                case "operateRange":
                                    operateRange = entry.getValue();
                                    break;
                                case "lensStyle":
                                    lensStyle = entry.getValue();
                                    break;
                                case "shellSizeInsert":
                                    shellSizeInsert = entry.getValue();
                                    break;
                                case "turnOnTime":
                                    turnOnTime = entry.getValue();
                                    break;
                                case "motorType":
                                    motorType = entry.getValue();
                                    break;
                                case "dataRateMax":
                                    dataRateMax = entry.getValue();
                                    break;
                                case "protocol":
                                    protocol = entry.getValue();
                                    break;
                                case "voltageInput":
                                    voltageInput = entry.getValue();
                                    break;
                                case "numberOfPositions":
                                    numberOfPositions = entry.getValue();
                                    break;
                                case "flashSize":
                                    flashSize = entry.getValue();
                                    break;
                                case "switchType":
                                    switchType = entry.getValue();
                                    break;
                                case "materialMagnet":
                                    materialMagnet = entry.getValue();
                                    break;
                                case "voltageSupplySingleV":
                                    voltageSupplySingleV = entry.getValue();
                                    break;
                                case "overloadProtection":
                                    overloadProtection = entry.getValue();
                                    break;
                                case "frequency":
                                    frequency = entry.getValue();
                                    break;
                                case "currentRatingAC":
                                    currentRatingAC = entry.getValue();
                                    break;
                                case "switchFeatures":
                                    switchFeatures = entry.getValue();
                                    break;
                                case "washable":
                                    washable = entry.getValue();
                                    break;
                                case "sensingTemperatureRemote":
                                    sensingTemperatureRemote = entry.getValue();
                                    break;
                                case "sensingDistance":
                                    sensingDistance = entry.getValue();
                                    break;
                                case "pressureType":
                                    pressureType = entry.getValue();
                                    break;
                                case "copperType":
                                    copperType = entry.getValue();
                                    break;
                                case "relayType":
                                    relayType = entry.getValue();
                                    break;
                                case "displayType":
                                    displayType = entry.getValue();
                                    break;
                                case "connectorACInput":
                                    connectorACInput = entry.getValue();
                                    break;
                                case "efficiency":
                                    efficiency = entry.getValue();
                                    break;
                                case "voltageSupply":
                                    voltageSupply = entry.getValue();
                                    break;
                                case "approvedCountries":
                                    approvedCountries = entry.getValue();
                                    break;
                                case "codedKeyed":
                                    codedKeyed = entry.getValue();
                                    break;
                                case "releaseTime":
                                    releaseTime = entry.getValue();
                                    break;
                                case "sizeBody":
                                    sizeBody = entry.getValue();
                                    break;
                                case "voltageForwardVfTyp":
                                    voltageForwardVfTyp = entry.getValue();
                                    break;
                                case "strokeLength":
                                    strokeLength = entry.getValue();
                                    break;
                                case "printType":
                                    printType = entry.getValue();
                                    break;
                                case "timingInitiateMethod":
                                    timingInitiateMethod = entry.getValue();
                                    break;
                                case "architecture":
                                    architecture = entry.getValue();
                                    break;
                                case "faderType":
                                    faderType = entry.getValue();
                                    break;
                                case "delayTime":
                                    delayTime = entry.getValue();
                                    break;
                                case "keyType":
                                    keyType = entry.getValue();
                                    break;
                                case "threadSize":
                                    threadSize = entry.getValue();
                                    break;
                                case "wavelength":
                                    wavelength = entry.getValue();
                                    break;
                                case "depthBehindPanel":
                                    depthBehindPanel = entry.getValue();
                                    break;
                                case "powerWatts":
                                    powerWatts = entry.getValue();
                                    break;
                                case "numberOfStacks":
                                    numberOfStacks = entry.getValue();
                                    break;
                                case "standardNumber":
                                    standardNumber = entry.getValue();
                                    break;
                                case "currentRatingDC":
                                    currentRatingDC = entry.getValue();
                                    break;
                                case "fuseType":
                                    fuseType = entry.getValue();
                                    break;
                                case "outsidePipeDiameterMax":
                                    outsidePipeDiameterMax = entry.getValue();
                                    break;
                                case "currentSupplyMax":
                                    currentSupplyMax = entry.getValue();
                                    break;
                                case "voltageSupplySource":
                                    voltageSupplySource = entry.getValue();
                                    break;
                                case "workstand":
                                    workstand = entry.getValue();
                                    break;
                                case "industryRecognizedMatingDiameter":
                                    industryRecognizedMatingDiameter = entry.getValue();
                                    break;
                                case "baseUnit":
                                    baseUnit = entry.getValue();
                                    break;
                                case "features":
                                    features = entry.getValue();
                                    break;
                                case "RFType":
                                    RFType = entry.getValue();
                                    break;
                                case "contactRatingVoltage":
                                    contactRatingVoltage = entry.getValue();
                                    break;
                                case "currentTransmitting":
                                    currentTransmitting = entry.getValue();
                                    break;
                                case "RAMSize":
                                    RAMSize = entry.getValue();
                                    break;
                                case "tolerance":
                                    tolerance = entry.getValue();
                                    break;
                                case "numberOfCharactersPerRow":
                                    numberOfCharactersPerRow = entry.getValue();
                                    break;
                                case "RdsOnTyp":
                                    RdsOnTyp = entry.getValue();
                                    break;
                                case "RFFamilyStandard":
                                    RFFamilyStandard = entry.getValue();
                                    break;
                                case "mountingFeature":
                                    mountingFeature = entry.getValue();
                                    break;
                                case "switchingCycles":
                                    switchingCycles = entry.getValue();
                                    break;
                                case "currentRatingCircuitBreaker":
                                    currentRatingCircuitBreaker = entry.getValue();
                                    break;
                                case "voltageUL":
                                    voltageUL = entry.getValue();
                                    break;
                                case "numberOfPoles":
                                    numberOfPoles = entry.getValue();
                                    break;
                                case "timingAdjustmentMethod":
                                    timingAdjustmentMethod = entry.getValue();
                                    break;
                                case "location":
                                    location = entry.getValue();
                                    break;
                                case "sensingTemperatureLocal":
                                    sensingTemperatureLocal = entry.getValue();
                                    break;
                                case "platform":
                                    platform = entry.getValue();
                                    break;
                                case "forUseWithRelatedManufacturer":
                                    forUseWithRelatedManufacturer = entry.getValue();
                                    break;
                                case "currentMax":
                                    currentMax = entry.getValue();
                                    break;
                                case "voltageRated":
                                    voltageRated = entry.getValue();
                                    break;
                                case "currentSensing":
                                    currentSensing = entry.getValue();
                                    break;
                                case "numberOfLevels":
                                    numberOfLevels = entry.getValue();
                                    break;
                                case "channelCapacitanceCSoffCDooff":
                                    channelCapacitanceCSoffCDooff = entry.getValue();
                                    break;
                                case "outputCode":
                                    outputCode = entry.getValue();
                                    break;
                                case "voltageRatingAC":
                                    voltageRatingAC = entry.getValue();
                                    break;
                                case "illumination":
                                    illumination = entry.getValue();
                                    break;
                                case "suppliedIronTweezerHandle":
                                    suppliedIronTweezerHandle = entry.getValue();
                                    break;
                                case "multiplexerDemultiplexerCircuit":
                                    multiplexerDemultiplexerCircuit = entry.getValue();
                                    break;
                                case "controlInterface":
                                    controlInterface = entry.getValue();
                                    break;
                                case "fanAccessoryType":
                                    fanAccessoryType = entry.getValue();
                                    break;
                                case "cardType":
                                    cardType = entry.getValue();
                                    break;
                                case "IIP3":
                                    IIP3 = entry.getValue();
                                    break;
                                case "testCondition":
                                    testCondition = entry.getValue();
                                    break;
                                case "legendColor":
                                    legendColor = entry.getValue();
                                    break;
                                case "voltageOutput2":
                                    voltageOutput2 = entry.getValue();
                                    break;
                                case "actuatorMarking":
                                    actuatorMarking = entry.getValue();
                                    break;
                                case "voltageOutput3":
                                    voltageOutput3 = entry.getValue();
                                    break;
                                case "currentCollectorIcMax":
                                    currentCollectorIcMax = entry.getValue();
                                    break;
                                case "voltageOutput4":
                                    voltageOutput4 = entry.getValue();
                                    break;
                                case "approvalAgency":
                                    approvalAgency = entry.getValue();
                                    break;
                                case "suppliedTipsNozzles":
                                    suppliedTipsNozzles = entry.getValue();
                                    break;
                                case "contactFinishThickness":
                                    contactFinishThickness = entry.getValue();
                                    break;
                                case "nozzleOpening":
                                    nozzleOpening = entry.getValue();
                                    break;
                                case "voltageOutput1":
                                    voltageOutput1 = entry.getValue();
                                    break;
                                case "sizeDimension":
                                    sizeDimension = entry.getValue();
                                    break;
                                case "lampType":
                                    lampType = entry.getValue();
                                    break;
                                case "linearity":
                                    linearity = entry.getValue();
                                    break;
                                case "dutyCycle":
                                    dutyCycle = entry.getValue();
                                    break;
                                case "current":
                                    current = entry.getValue();
                                    break;
                                case "legend":
                                    legend = entry.getValue();
                                    break;
                                case "shape":
                                    shape = entry.getValue();
                                    break;
                                case "filterOrder":
                                    filterOrder = entry.getValue();
                                    break;
                                case "releaseForce":
                                    releaseForce = entry.getValue();
                                    break;
                                case "lensTransparency":
                                    lensTransparency = entry.getValue();
                                    break;
                                case "responseTime":
                                    responseTime = entry.getValue();
                                    break;
                                case "sensingMethod":
                                    sensingMethod = entry.getValue();
                                    break;
                                case "circuitPerDeck":
                                    circuitPerDeck = entry.getValue();
                                    break;
                                case "numberOfPorts":
                                    numberOfPorts = entry.getValue();
                                    break;
                                case "suppliedContents":
                                    suppliedContents = entry.getValue();
                                    break;
                                case "modulation":
                                    modulation = entry.getValue();
                                    break;
                                case "contactMaterial":
                                    contactMaterial = entry.getValue();
                                    break;
                                case "pixelFormat":
                                    pixelFormat = entry.getValue();
                                    break;
                                case "chargeInjection":
                                    chargeInjection = entry.getValue();
                                    break;
                                case "cableLength":
                                    cableLength = entry.getValue();
                                    break;
                                case "voltageSupplyVccVdd":
                                    voltageSupplyVccVdd = entry.getValue();
                                    break;
                                case "proximityDetection":
                                    proximityDetection = entry.getValue();
                                    break;
                                case "mustOperate":
                                    mustOperate = entry.getValue();
                                    break;
                                case "actuatorLevel":
                                    actuatorLevel = entry.getValue();
                                    break;
                                case "actuatorType":
                                    actuatorType = entry.getValue();
                                    break;
                                case "LEDColor":
                                    LEDColor = entry.getValue();
                                    break;
                                case "panelCutoutDimensions":
                                    panelCutoutDimensions = entry.getValue();
                                    break;
                                case "VceSaturationMaxIbIc":
                                    VceSaturationMaxIbIc = entry.getValue();
                                    break;
                                case "voltageOutputMax":
                                    voltageOutputMax = entry.getValue();
                                    break;
                                case "terminalWidth":
                                    terminalWidth = entry.getValue();
                                    break;
                                case "type":
                                    type = entry.getValue();
                                    break;
                                case "voltageRatingDC":
                                    voltageRatingDC = entry.getValue();
                                    break;
                                case "sensitivity":
                                    sensitivity = entry.getValue();
                                    break;
                                case "travelRange":
                                    travelRange = entry.getValue();
                                    break;
                                case "temperatureRange":
                                    temperatureRange = entry.getValue();
                                    break;
                                case "opticalPattern":
                                    opticalPattern = entry.getValue();
                                    break;
                                case "numberOfDecks":
                                    numberOfDecks = entry.getValue();
                                    break;
                                case "voltageRating":
                                    voltageRating = entry.getValue();
                                    break;
                                case "wattage":
                                    wattage = entry.getValue();
                                    break;
                                case "conduitThreadSize":
                                    conduitThreadSize = entry.getValue();
                                    break;
                                case "leadSpacing":
                                    leadSpacing = entry.getValue();
                                    break;
                                case "ratings":
                                    ratings = entry.getValue();
                                    break;
                                case "operatingForce":
                                    operatingForce = entry.getValue();
                                    break;
                                case "actuatorOrientation":
                                    actuatorOrientation = entry.getValue();
                                    break;
                                case "numberOfChannels":
                                    numberOfChannels = entry.getValue();
                                    break;
                                case "technology":
                                    technology = entry.getValue();
                                    break;
                                case "currentReceiving":
                                    currentReceiving = entry.getValue();
                                    break;
                                case "currentRatingApplianceInlet":
                                    currentRatingApplianceInlet = entry.getValue();
                                    break;
                                case "adjustmentType":
                                    adjustmentType = entry.getValue();
                                    break;
                                case "requires":
                                    requires = entry.getValue();
                                    break;
                                case "grade":
                                    grade = entry.getValue();
                                    break;
                                case "voltageBreakdown":
                                    voltageBreakdown = entry.getValue();
                                    break;
                                case "connectionMethod":
                                    connectionMethod = entry.getValue();
                                    break;
                                case "memorySize":
                                    memorySize = entry.getValue();
                                    break;
                                case "connectorStyle":
                                    connectorStyle = entry.getValue();
                                    break;
                                case "pitch":
                                    pitch = entry.getValue();
                                    break;
                                case "protoBoardType":
                                    protoBoardType = entry.getValue();
                                    break;
                                case "shellMaterial":
                                    shellMaterial = entry.getValue();
                                    break;
                                case "resolution":
                                    resolution = entry.getValue();
                                    break;
                                case "fuseHolderDrawer":
                                    fuseHolderDrawer = entry.getValue();
                                    break;
                                case "coreProcessor":
                                    coreProcessor = entry.getValue();
                                    break;
                                case "operatingPosition":
                                    operatingPosition = entry.getValue();
                                    break;
                                case "viewingArea":
                                    viewingArea = entry.getValue();
                                    break;
                                case "bushingThread":
                                    bushingThread = entry.getValue();
                                    break;
                                case "viewingAngle":
                                    viewingAngle = entry.getValue();
                                    break;
                                case "voltageSwitchingDC":
                                    voltageSwitchingDC = entry.getValue();
                                    break;
                                case "kitType":
                                    kitType = entry.getValue();
                                    break;
                                case "FETType":
                                    FETType = entry.getValue();
                                    break;
                                case "portStyle":
                                    portStyle = entry.getValue();
                                    break;
                                case "outputConfiguration":
                                    outputConfiguration = entry.getValue();
                                    break;
                                case "actuatorLength":
                                    actuatorLength = entry.getValue();
                                    break;
                                case "switchingTemperature":
                                    switchingTemperature = entry.getValue();
                                    break;
                                case "kitContents":
                                    kitContents = entry.getValue();
                                    break;
                                case "voltageSupplyMax":
                                    voltageSupplyMax = entry.getValue();
                                    break;
                                case "voltage":
                                    voltage = entry.getValue();
                                    break;
                                case "secondaryAttributes":
                                    secondaryAttributes = entry.getValue();
                                    break;
                                case "supplyVoltage":
                                    supplyVoltage = entry.getValue();
                                    break;
                                case "breakerType":
                                    breakerType = entry.getValue();
                                    break;
                                case "functionLighting":
                                    functionLighting = entry.getValue();
                                    break;
                                case "displayCharactersHeight":
                                    displayCharactersHeight = entry.getValue();
                                    break;
                                case "toolType":
                                    toolType = entry.getValue();
                                    break;
                                case "inputType":
                                    inputType = entry.getValue();
                                    break;
                                case "currentHoldIh":
                                    currentHoldIh = entry.getValue();
                                    break;
                                case "switchesQuantity":
                                    switchesQuantity = entry.getValue();
                                    break;
                                case "termination":
                                    termination = entry.getValue();
                                    break;
                                case "currentLEDTriggerIftMax":
                                    currentLEDTriggerIftMax = entry.getValue();
                                    break;
                                case "slewRate":
                                    slewRate = entry.getValue();
                                    break;
                                case "clockSync":
                                    clockSync = entry.getValue();
                                    break;
                                case "numberOfOutputs":
                                    numberOfOutputs = entry.getValue();
                                    break;
                                case "actualDiameter":
                                    actualDiameter = entry.getValue();
                                    break;
                                case "fiberPorts":
                                    fiberPorts = entry.getValue();
                                    break;
                                case "dutyCycleMax":
                                    dutyCycleMax = entry.getValue();
                                    break;
                                case "bodyMaterial":
                                    bodyMaterial = entry.getValue();
                                    break;
                                case "headHeight":
                                    headHeight = entry.getValue();
                                    break;
                                case "cordLength":
                                    cordLength = entry.getValue();
                                    break;
                                case "numberOfInputsAndType":
                                    numberOfInputsAndType = entry.getValue();
                                    break;
                                case "plating":
                                    plating = entry.getValue();
                                    break;
                                case "currentCollectorCutoffMax":
                                    currentCollectorCutoffMax = entry.getValue();
                                    break;
                                case "maximumPressure":
                                    maximumPressure = entry.getValue();
                                    break;
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
                        } else {
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
                                cableOpening, numberOfKeys, SFPXFPPorts, serialInterfaces, actuatorDiameter, dielectricMaterial, size, copperPorts, numberOfFilters, displayMode, sectionWidth, gender, materialFlammabilityRating,
                                lightSource, currentUL, panelThickness, forUseWithRelatedProducts, includedMCUMPUBoards, expandable, testFrequency, loadForceDynamic, embedded, primaryAttributes, switchTimeTonToffMax,
                                communications, independentCircuits, orientation, illuminationVoltageNominal, thickness, qualification, insulationColor, screwHeadType, maximumCableLength, filterType, LEDdriverChannels,
                                impedance, currentOutputHighLow, lightColor, currentLeakageISoffMax, ingressProtection, outputType, suggestedProgrammingEnvironment, actuatorLengthRightAngle, diameterOutside, signalLines,
                                channeltoChannelMatchingRon, specifications, output, mediaLinesProtected, contactFinishMating, endCaps, zeroCrossingCircuit, lengthBelowHead, SFPXFPIType, deviceSize, minus3dbBandwidth,
                                voltageSwitchingAC, peripherals, numberOfSlotsRelays, interconnectSystem, length, functionAudible, numberOfCircuits, frequencyCutoffOrCenter, switchesInterface, operatingPressure, legendText,
                                contactFinish, controlDisplayType, currentOutputMax, voltageInputMin, ejectorSide, transistorType, wireGaugeOrRangeAWG, connectorACOutput, frequencyTransition, operatingAngle, color,
                                forMeasuring, voltageLoad, outputPhases, contactTiming, currentOutput, resetTemperature, language, legendType, voltageStartUp, currentStartup, wireGaugeOrRangemm, delayTimeOFF, lensSize,
                                illuminationTypeColor, matingLengthDepth, speed, applications, diameterInside, voltageSupplyMin, material, ratioInputOutput, barrelDepth, fasteningType, currentIEC, indicator, arrangement,
                                hardwareType, faultProtection, accessoryType, currentDCForwardIfMax, diameterLabel, currentSwitching, headDiameter, indexStops, insertionRemovalMethod, currentOutputSourceSink,
                                voltageCollectorEmitterBreakdownMax, onStateResistanceMax, sensorType, feedbackType, keyColor, accomodatesAFuse, outputIsolation, programmableFeatures, matrixColumnsxRows, releaseRange,
                                holdingForce, terminationStyle, shielding, currentSupply, contents, flangeDiameter, approvalAgencyMarking, voltageSupplySingleDual, holeDiameter, currentRatingFilter, legendSymbolOnly,
                                powerOutput, energy, connectorType, style, width, diameter, mainPurpose, usage, synchronousRectifier, applicationSpecifics, outline, lengthOverall, numberOfOutputsAndType, switchCircuit,
                                powerMax, acceleration, gearReductionRatio, DCcurrentGainhFEMinIcVce, resetOperation, polarization, airFlow, packageAccepted, voltagePeakReverseMax, diodeType, fiberType, resistanceIfF,
                                voltageOffState, packageCase, disconnectType, voltageIEC, operatingTemperature, shaftSize, staticdvdtMin, currentCarry, mustRelease, keyRemovablePositions, capacitanceVrF, fitsFanSize,
                                voltageIsolation, topology, mountingType, ESREquivalentSeriesResistance, faceSize, resistance, controlFeatures, colorLegend, pretravel, connectivity, outsidePipeDiameterMin,
                                currentOutputChannel, DigiKeyProgrammable, frequencyRange, plugType, voltageInputMax, readOut, heightAboveBoard, internalSwitchs, supplierDevicePackage, includes, function, accuracy,
                                numberOfSections, powerRated, dataRate, frequencySwitching, voltageOutput, voltageOutputMinFixed, height, P1dB, differentialTravel, primaryMaterial, actuatorHeightOffPCBVertical, mode,
                                angleOfThrow, driveType, standards, accuracyHighestLowest, voltageRatingApplianceInlet, overtravel, capacitance, isolation, bodyFinish, rotationAngle, colorActuatorCap, actuatorMaterial,
                                configuration, housingMaterial, crosstalk, shellSizeMIL, voltageSupplyDualV, ACOutlets, selectFirstThenApplyFiltersCompatibleSeries, operateTime, numberOfInputs, compatibleTools,
                                utilizedICPart, materialBody, sensingRange, boardThickness, insertionLoss, construction, numberOfPositionsContacts, numberOfPolesPerDeck, functionsExtra, delayTimeON, portSize, circuit,
                                switchFunction, regionUtilized, loadForceStatic, dimming, colorBackground, heightSeatedMax, currentRatingAmps, shellFinish, operateRange, lensStyle, shellSizeInsert, turnOnTime, motorType,
                                dataRateMax, protocol, voltageInput, numberOfPositions, flashSize, switchType, materialMagnet, voltageSupplySingleV, overloadProtection, frequency, currentRatingAC, switchFeatures,
                                washable, sensingTemperatureRemote, sensingDistance, pressureType, copperType, relayType, displayType, connectorACInput, efficiency, voltageSupply, approvedCountries, codedKeyed,
                                releaseTime, sizeBody, voltageForwardVfTyp, strokeLength, printType, timingInitiateMethod, architecture, faderType, delayTime, keyType, threadSize, wavelength, depthBehindPanel, powerWatts,
                                numberOfStacks, standardNumber, currentRatingDC, fuseType, outsidePipeDiameterMax, currentSupplyMax, voltageSupplySource, workstand, industryRecognizedMatingDiameter, baseUnit, features,
                                RFType, contactRatingVoltage, currentTransmitting, RAMSize, tolerance, numberOfCharactersPerRow, RdsOnTyp, RFFamilyStandard, mountingFeature, switchingCycles, currentRatingCircuitBreaker,
                                voltageUL, numberOfPoles, timingAdjustmentMethod, location, sensingTemperatureLocal, platform, forUseWithRelatedManufacturer, currentMax, voltageRated, currentSensing, numberOfLevels,
                                channelCapacitanceCSoffCDooff, outputCode, voltageRatingAC, illumination, suppliedIronTweezerHandle, multiplexerDemultiplexerCircuit, controlInterface, fanAccessoryType, cardType, IIP3,
                                testCondition, legendColor, voltageOutput2, actuatorMarking, voltageOutput3, currentCollectorIcMax, voltageOutput4, approvalAgency, suppliedTipsNozzles, contactFinishThickness,
                                nozzleOpening, voltageOutput1, sizeDimension, lampType, linearity, dutyCycle, current, legend, shape, filterOrder, releaseForce, lensTransparency, responseTime, sensingMethod,
                                circuitPerDeck, numberOfPorts, suppliedContents, modulation, contactMaterial, pixelFormat, chargeInjection, cableLength, voltageSupplyVccVdd, proximityDetection, mustOperate, actuatorLevel,
                                actuatorType, LEDColor, panelCutoutDimensions, VceSaturationMaxIbIc, voltageOutputMax, terminalWidth, type, voltageRatingDC, sensitivity, travelRange, temperatureRange, opticalPattern,
                                numberOfDecks, voltageRating, wattage, conduitThreadSize, leadSpacing, ratings, operatingForce, actuatorOrientation, numberOfChannels, technology, currentReceiving,
                                currentRatingApplianceInlet, adjustmentType, requires, grade, voltageBreakdown, connectionMethod, memorySize, connectorStyle, pitch, protoBoardType, shellMaterial, resolution,
                                fuseHolderDrawer, coreProcessor, operatingPosition, viewingArea, bushingThread, viewingAngle, voltageSwitchingDC, kitType, FETType, portStyle, outputConfiguration, actuatorLength,
                                switchingTemperature, kitContents, voltageSupplyMax, voltage, secondaryAttributes, supplyVoltage, breakerType, functionLighting, displayCharactersHeight, toolType, inputType,
                                currentHoldIh, switchesQuantity, termination, currentLEDTriggerIftMax, slewRate, clockSync, numberOfOutputs, actualDiameter, fiberPorts, dutyCycleMax, bodyMaterial, headHeight, cordLength,
                                numberOfInputsAndType, plating, currentCollectorCutoffMax, maximumPressure
                        };
                        categoryTable.add(row);
                    }
                }

                int tableColumns = header.length;
//                System.out.println(header.length);
//
//                // Converting list to a 2D array for easy printing
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

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }


        }

        System.out.println();
        for (String str : attributes) {
            System.out.println(str);
        }

    }
}