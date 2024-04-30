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

public class ConnectorJSONFileReader {

    public static void main(String[] args) {

        String categoryFolder = "Connectors, Interconnects";

//        String directory = "/root/EDAProject/Postman Exports/";
        String directory = "Postman Exports/";

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
                    "Cable Opening", "Inner Diameter - Recovered", "Number of Keys", "Fuseholder Type", "Serial Interfaces",
                    "Dielectric Material", "License Length", "Shield Type", "Pin Size - Below Flange", "Board-Side Pitch",
                    "Memory", "Count Rate", "Controller Type", "Contact Finish Thickness - Mating", "Cable Connectors",
                    "Fan Type", "Gender", "Material Flammability Rating", "Header Orientation", "Auxiliary Outputs and Type",
                    "Length - Threaded Portion Below Head", "Module/Board Type", "Regulator Topology", "Cooling Type", "Turn Off Time",
                    "Signal Conditioning", "Background Color", "Mating Orientation", "Lens Type", "Number of Turrets",
                    "Sealed", "Battery Series", "Ratcheting", "Orientation", "Must Release Voltage",
                    "Number of Positions or Pins (Grid)", "Diagonal Screen Size", "Resistance in Ohms @ 25°C", "Filter Type", "Attachment Method",
                    "Frequency - Max", "Opening Size", "Control / Drive Type", "Resistance Tolerance", "Length - Above Flange",
                    "Diameter - Outside", "Signal Lines", "Fits Terminal Size", "Current Rating (Per Contact)", "Visual Field (Min)",
                    "Specifications", "Material - Cap", "Output", "Length - Stack Height", "Abrasive Material",
                    "Voltage - Supply, Battery", "Strap Closure", "Zero Crossing Circuit", "Current - Gate Trigger (Igt) (Max)", "2nd Connector Number of Positions Loaded",
                    "Device Size", "Control Method", "1st Connector Mounting Type", "Tab Direction", "Bottom Termination",
                    "Board Material", "Input Impedance", "Locking Feature", "Charge Time", "Write Cycle Time - Word, Page",
                    "Length", "Rise Time (Typ)", "Compatible Shell Size", "Illumination Color", "Function - Audible",
                    "Container Type", "Step Quantity", "Number of Circuits", "Interface", "Number of Motors",
                    "Operating Pressure", "Diameter - Shaft", "Rated Life", "Packages Included", "Load Type",
                    "Base Type", "Voltage - Input (Min)", "Transistor Type", "Exterior Housing Material", "Storage Interface",
                    "Step Resolution", "Conductor Material", "Terminal Block Type", "Voltage - Rated AC", "Material - Tip",
                    "Connector - AC Output", "Reel Capacity", "Current - Hold (Ih) (Max)", "Frequency - Transition", "Voltage - Load",
                    "Motor Type - Stepper", "Drive Size", "Number of Columns", "Storage", "Common Pin",
                    "Contact Timing", "Voltage - Supply Span (Min)", "Safety Category", "Installation Distance", "Load Current",
                    "Area (L x W)", "Interior Housing Color", "Center Contact Material", "Form", "Coil Current",
                    "Current - Output", "Voltage - Breakdown (Min)", "Maximum Working Height", "Staking Side ID",
                    "Character Size", "Outside Cable Diameter (Max)", "1st Connector Shell Size - Insert", "Reset Temperature", "Connector/Contact Type",
                    "Legend Type", "Noise Figure", "Hardware", "Lens Size", "Current - Non Rep. Surge 50, 60Hz (Itsm)",
                    "Mating Length/Depth", "Number of Mixers", "Bidirectional Channels", "Composition", "Applications",
                    "Diameter - Inside", "Length - Below Flange", "Bushing, Grommet Type", "Barrel Depth", "Fastening Type",
                    "Current - IEC", "Indicator", "Arrangement", "Hardware Type", "Number of Modules",
                    "Fault Protection", "Simplex/Duplex", "Control Side Connection", "Voltage Reference", "Color - Cap",
                    "Diameter - Label", "Current - DC Forward (If) (Max)", "Voltage - Rated DC", "Storage/Refrigeration Temperature",
                    "Shell Finish Thickness", "Profile (W x H)", "Values", "Insertion, Removal Method", "Label Size",
                    "Sensor Type", "On-State Resistance (Max)", "Tool Method", "Key Color", "1st Contact Gender",
                    "Accommodates a Fuse", "Number of Bits", "SATA", "Printer Name", "Holding Force",
                    "Termination Style", "Color - Wire", "Shielding", "Current - Supply", "Probe Temperature Range",
                    "Flange Diameter", "Hole Diameter", "2nd Connector Type", "Shell Size - Insert (Convert From)", "Adapter Series",
                    "Channels per IC", "Rise / Fall Time (Typ)", "Watchdog Timer", "Common Mode Transient Immunity (Min)", "Current - Supply (Main IC)",
                    "Wire Gauge", "Direction", "Depth", "Power - Output", "Duplex",
                    "Stud/Tab Size", "DAC Type", "Voltage - Clamping", "Form Factor", "Power Dissipation (Max)",
                    "Style", "Width", "Adhesive", "Frame Type", "Impedance - Primary (Ohms)",
                    "Usage", "USB", "Pair Number", "Jacket (Insulation) Thickness", "Flange Feature",
                    "Outline", "RS-232 (422, 485)", "Master fclk", "Voltage - Input Offset", "Thickness - Backing, Carrier",
                    "Current - Output 1", "Number of Outputs and Type", "Length - Overall", "Vce(on) (Max) @ Vge, Ic", "Switch Circuit",
                    "Height (Inches)", "RAM Controllers", "Number of Positions Loaded", "Acceleration", "Gear Reduction Ratio",
                    "DC Current Gain (hFE) (Min) @ Ic, Vce", "Reset Operation", "Staking Side OD", "Polarization", "Air Flow",
                    "Wire Duct Type", "Package Accepted", "Diode Type", "Interior Type (Adapter End)", "Construction Material",
                    "Shell Size - Insert (Convert To)", "DC Voltage - Input (Min)", "Disconnect Type", "Number of Cores/Bus Width", "Voltage - IEC",
                    "Pin Size - Above Flange", "Number of Wire Entries", "Lead Style", "Reset", "Tuning Word Width (Bits)",
                    "Static dV/dt (Min)", "Must Release", "Capacitance @ Vr, F", "Thread Length", "Voltage - Isolation",
                    "Secondary Winding(s)", "Fits Fan Size", "Mounting Hole Spacing", "ESR (Equivalent Series Resistance)", "Lifetime @ Temp.",
                    "Resistance", "Control Features", "Size - Display", "Co-Processor", "Color - Legend",
                    "Pretravel", "Resistance - Channel (Ohms)", "Vce Saturation (Max)", "Field of View", "DigiKey Programmable",
                    "Connector - Cable", "Cable Type", "Shell Material, Finish", "Plug Type", "Height Above Board",
                    "Internal Switch(s)", "Sigma Delta", "Card Thickness", "Pitch - Post", "Function",
                    "Accuracy", "Number of Sections", "Insulation Diameter", "Wire Gauge or Range - Coaxial", "Frequency - Switching",
                    "Voltage - Output", "Frequency - Self Resonant", "Inner Diameter - Supplied", "P1dB", "Label Type",
                    "Memory Format", "Convert From (Adapter End)", "Differential Travel", "Conduit Hub Size", "Primary Material",
                    "Touchscreen", "Magnetization", "Drive Type", "Breaking Capacity @ Rated Voltage", "Height (Max)",
                    "Standards", "Accuracy - Highest (Lowest)", "Voltage Rating - Appliance Inlet", "Number of Lines", "Termination Thread",
                    "Overtravel", "Contact Finish - Post", "Capacitance", "Isolation", "Inductance Range", "Display Count",
                    "Body Finish", "Tip Size",
                    "Color - Actuator/Cap", "Power (Typ) @ Conditions", "Shell Style", "Power Supply Type", "Current - Peak Pulse (10/1000µs)",
                    "Insulation Height", "Barrier Type", "Housing Material", "Voltage - Primary", "Voltage - Supply, Dual (V±)",
                    "Insertion Angle", "AC Outlets", "Operate Time", "Torque - Holding (oz-in / mNm)", "Compatible Tools",
                    "Wheel/Caster Diameter", "Utilized IC / Part", "Exterior Contact Finish", "Shell Size, Connector Layout", "Frequency Group",
                    "Voltage - Max", "Hook Type", "Light Emitting Surface (LES)", "Functions, Extra", "Port Size",
                    "Slotted/Unslotted", "Rotational Life (Cycles Min)", "Number of Positions (Convert From)", "Number of SCRs, Diodes", "Current - Reverse Leakage @ Vr",
                    "Contact Size", "Resistance @ 0°C", "Switch Function", "Region Utilized", "Contact Length - Post",
                    "Dimming", "Material Finish", "Strap Termination", "Conversion Type", "Charge Current - Max",
                    "Height - Seated (Max)", "Lumens/Watt @ Current - Test", "Millicandela Rating", "Rotation Angle - Electrical, Mechanical", "Inductance",
                    "Mounting Hole Diameter", "Image Type", "Terminal Size", "Access Time", "Termination Finish",
                    "Operating Force - Initial", "Turn On Time", "Voltage - Reverse Standoff (Typ)", "Battery Chemistry", "Motor Type",
                    "Data Rate (Max)", "Coupler Type", "Protocol", "Terminal Style", "Resolution (Bits)",
                    "Number of Gangs", "Voltage - Supply, Digital", "Number of Positions", "Flash Size", "Safety Outputs and Type",
                    "Switch Type", "Material - Magnet", "VSWR", "Current Rating - AC", "Display Style",
                    "CCT (K)", "Compliance", "Noise", "Steps per Revolution", "Wire Entry Location",
                    "Pressure Type", "Connector Impedance", "Plug Color", "Memory Type", "Relay Type",
                    "Load Side Connections Per Pole", "Efficiency", "Approved Countries", "Voltage - Supply", "Capacitance Range",
                    "Coded, Keyed", "Voltage - Forward (Vf) (Typ)", "Stroke Length", "Amplifier Type", "Power (Watts) - Per Port",
                    "Print Type", "Socket Depth", "Memory Interface", "Center Tap", "Delay Time",
                    "Key Type", "Exterior Housing Color", "Length - Tip", "Wavelength", "Power (Watts)",
                    "Output Signal", "Number of Stacks", "Class", "Power Dissipation @ Temperature Rise", "Cable Length - Unexposed",
                    "Current Rating - DC", "Tail Diameter", "Fuse Type", "Jaw Opening", "Current - Supply (Max)",
                    "Voltage Range", "Lamp Color", "Control Side Cable Retainer", "Operating Force - Mid Compression", "Voltage - Clamping (Max) @ Ipp",
                    "Plug/Mating Plug Diameter", "Bandwidth", "Features", "RF Type", "Wavelength - Peak",
                    "Contact Rating @ Voltage", "Can Replace Lamps", "Coupling Factor", "Current - Transmitting", "Capacitance @ Frequency",
                    "Tolerance", "RF Family/Standard", "Rotation", "Mounting Feature", "Number of Cores per Jack",
                    "Current Rating - Circuit Breaker", "Cable Group", "Shrouding", "Timing Adjustment Method", "Contact Layout, Typical",
                    "Interior Contact Finish", "Load Side Connection Type", "Strap Material", "Dynamic Range, ADCs / DACs (db) Typ", "2nd Contact Gender",
                    "Housing Finish", "For Use With/Related Manufacturer", "Built in Switch", "Current - Sensing", "Voltage Rating - AC",
                    "Plug Wire Entry", "Failure Rate", "Coil Type", "Length - Tail", "Fan Accessory Type",
                    "Blade Size", "Efficiency - dBA", "Test Condition", "Legend Color", "Contact Rating (Current)",
                    "Contact Finish Thickness", "Terminal Type", "Approval Agency", "Nozzle Opening", "Input Connector",
                    "Length - Shaft and Bearing", "Fuse Size", "Ripple Current @ High Frequency", "Line Side Connections Per Pole", "Rear Panel Wire Connection",
                    "Current", "Bin Quantity", "Shape", "Tab Width", "Lens Transparency",
                    "Response Time", "Center / Cutoff Frequency", "Sensing Method", "2nd Connector Mounting Feature", "Length - Overall Pin",
                    "Battery Type, Function", "Backset Spacing", "Modulation", "Contact Material", "Return Loss",
                    "Voltage - Supply (Vcc/Vdd)", "Data Format", "Power Line Protection", "Voltage - Gate Trigger (Vgt) (Max)", "Panel Cutout Dimensions",
                    "Voltage - Output (Max)", "Acceleration Range", "Transformer Type", "Contact Finish Thickness - Post", "Clip Style",
                    "Type", "Voltage Rating - DC", "Sensitivity", "Flat Flex Type", "Torque - Rated (oz-in / mNm)",
                    "Pressure Range", "1st Connector Orientation", "Voltage Rating", "Cable Material", "2nd Connector Gender",
                    "Conduit Thread Size", "Voltage - Supply, Analog", "Input", "Operating Force", "Number of Channels",
                    "Current Rating - Appliance Inlet", "Technology", "Expansion Type", "Additional Interfaces", "Connection Type",
                    "Shipping Info", "Graphics Acceleration", "Probe Type", "Range of Values", "Cable End Type",
                    "B0/50", "Requires", "Probe Material", "Impulse Discharge Current (8/20µs)", "Memory Organization",
                    "Grade", "Length - Pin", "Connection Method", "Cable Insulation", "Thermal Resistance @ Forced Air Flow",
                    "Memory Size", "Proto Board Type", "Minimum Working Height", "Step Angle", "Shell Material",
                    "Contact Termination", "Overall Contact Length", "Taper", "Fuse Holder, Drawer", "Resolution",
                    "Core Processor", "Current Transfer Ratio (Max)", "Stacking Method", "Mating Cycles", "Viewing Angle",
                    "Voltage - DC Spark Over (Nom)", "Cable Impedance", "Output Configuration", "Switch Actuation Level", "Contact Mating Finish",
                    "Shaft Detail", "Contact Finish - Post (Mating)", "Kit Contents", "Voltage", "Secondary Attributes",
                    "Thermal Resistance @ Natural", "Cable Exit", "Current - Average Rectified (Io) (per Diode)", "Axis", "Tool Type",
                    "Input Type", "Length - Post (Mating)", "Current - LED Trigger (Ift) (Max)", "Number of Outputs", "Length - Lead Wire",
                    "DC Resistance (DCR) - Secondary", "Head Height", "Cord Length", "DC Resistance (DCR) - Primary", "Cable Diameter",
                    "Current - Collector Cutoff (Max)", "Compatible D-Sub Size", "Maximum Pressure", "Screw, Thread Size", "Luminous Flux",
                    "Actuator Diameter", "Baud Rates", "Size", "Data Interface", "Display Mode",
                    "Tip Diameter", "Section Width", "Light Source", "Clip Length", "Design",
                    "Current - UL", "S/N Ratio, ADCs / DACs (db) Typ", "Panel Thickness", "For Use With/Related Products", "Included MCU/MPU Board(s)",
                    "Test Frequency", "Expandable", "Unidirectional Channels", "Load Force (Dynamic)", "Efficiency - Type",
                    "Time Format", "Embedded", "Current - Average Rectified (Io)", "Material - Core", "Primary Attributes",
                    "IGBT Type", "Power Level", "Communications", "Recovered Wall Thickness", "2nd Connector",
                    "Current - On State (It (AV)) (Max)",
                    "2nd Connector Mounting Type", "Thickness", "Illumination Voltage (Nominal)", "Qualification", "Length - Exposed Ends",
                    "Insulation Color", "Screw Head Type", "Input Range (Current, Voltage)", "Mated Stacking Heights", "LED Driver Channels",
                    "Impedance", "B25/85", "Light Color", "Pin Hole Diameter", "Ingress Protection",
                    "Output Type", "Output Range", "Suggested Programming Environment", "CRI (Color Rendering Index)", "Contact Finish Thickness - Post (Mating)",
                    "Rechargeability", "B25/75", "Media Lines Protected", "Contact Finish - Mating", "Level, Class",
                    "End Caps", "Connector - Sensor", "Length - Below Head", "Tab Length", "-3db Bandwidth",
                    "Line Side Wire/Stud Size", "Load - Max Switching", "Interconnect System", "Trip Range", "Board Type",
                    "Coil Voltage", "Shield Termination", "Character Format", "Fiber Core Diameter", "Legend (Text)",
                    "Contact Finish", "Visual Field (Max)", "Current - Output (Max)", "Shell Plating", "Number of Positions/Bay/Row",
                    "Ejector Side", "Impedance - Secondary (Ohms)", "Material - Cone", "ET (Volt-Time)", "Wire Gauge or Range - AWG",
                    "Insulation", "Color", "Width - Outer Edges", "For Measuring", "Current - Test",
                    "Battery Cell Size", "Duration", "Weight", "Graphics Color", "Lamp Wattage",
                    "Voltage - Nominal", "Module Capacity", "Ethernet", "Outputs and Type", "Ventilation",
                    "Contact Material - Mating", "Lens Color", "Turn On / Turn Off Time (Typ)", "Number of Cores", "Shrink Temperature",
                    "Number of ADCs / DACs", "Current - Peak Output", "Screw Size", "Sound Pressure Level (SPL)", "Language",
                    "Measuring Range", "1st Connector Mounting Feature", "Resistance (Ohms)", "Wire Gauge or Range - mm²", "Illumination Type, Color",
                    "Speed", "Width - Overall", "Voltage - Supply (Min)", "Bearing Type", "Front Panel Wire Connection",
                    "Material", "Ratio - Input:Output", "Contact Shape", "Depth - Overall", "Conductor Strand",
                    "Accessory Type", "Current - Switching", "Head Diameter", "Switching Voltage", "Tab Thickness",
                    "Index Stops", "Center Gender", "Contact Type", "Response Frequency", "Gain Bandwidth Product",
                    "1st Connector Type", "Voltage - Collector Emitter Breakdown (Max)", "Power - Peak Pulse", "Number of Cable Pairs", "Wattage - Load",
                    "Sheath Opening", "Feedback Type", "Torque - Max Momentary (oz-in / mNm)", "Input Range", "Matrix (Columns x Rows)",
                    "Sampling Rate (Per Second)", "Accepts Square Pin Size", "Fail Short", "Number of Cells", "Ferrule Material",
                    "Pitch - Connector", "Adapter Type", "Micron Size", "Media Delivery Type", "DC Resistance (DCR)",
                    "Length - Blade", "Antenna Type", "Display Format", "Contents", "Capacitance - Input",
                    "Number of DAC's", "Approval Agency Marking", "Board Size", "Voltage - Supply, Single/Dual (±)", "Material - Insulation",
                    "Outline L x W x H", "Current Rating - Filter", "Length - Above Board", "Efficiency - Testing", "Number of A/D Converters",
                    "B25/100", "Legend (Symbol Only)", "Date Format", "Energy", "Connector Type",
                    "Must Operate Voltage", "Number of Contacts", "Contact End", "Structure", "Diameter",
                    "Main Purpose", "Application Specifics", "Number of Positions (Convert To)", "Power - Max", "Plating - Body",
                    "Material - Housing & Prism", "Number of Drivers/Receivers", "Center Contact Plating", "Voltage - Peak Reverse (Max)", "Encoder Type",
                    "Fiber Type", "Current - Input Bias", "Voltage - Off State", "Package / Case", "Diameter - Barrel",
                    "Number of Pins", "Pin Diameter", "B25/50", "Operating Temperature", "Key Removable Positions",
                    "Contact Material - Post", "Topology", "Mounting Type", "Antenna Connector", "Coating, Housing Type",
                    "Process Side Connection", "Positions / Poles", "Outside Pipe Diameter (Min)", "Panel Cutout Shape", "Current - Output / Channel",
                    "Threaded/Unthreaded", "Working Distance", "Frequency Range", "Reverse Recovery Time (trr)", "Voltage - Input (Max)",
                    "Read Out", "Text Color", "Panel Hole Size", "Convert To (Adapter End)", "Supplier Device Package",
                    "RPM", "Cleaner, Treatment Type", "Includes", "Power - Rated", "Data Rate",
                    "Magnification Range", "Detent", "Number of Terminations", "Height", "Cord Type",
                    "Length (Inches)", "Finish", "Package Cooled", "Video Outputs", "Number of Rows",
                    "Outside Cable Diameter (Min)", "Mode", "Angle of Throw", "1st Connector Number of Positions", "Tool Type Feature",
                    "Camera Type", "Actuator Material", "Attenuation Value", "Current Ratio", "Configuration",
                    "Shell Size, MIL", "Recommended Working Height", "Interior Housing Material", "AC Voltage - Input", "Pin Length",
                    "2nd Connector Shell Size - Insert", "Cavity A", "(Select First, Then Apply Filters) Compatible Series", "Number of Inputs", "Cavity F",
                    "Voltage - DC Reverse (Vr) (Max)", "Material - Body", "Sensing Range", "Cavity C", "Cavity B",
                    "Board Thickness", "Cavity E", "Cavity D", "Expansion Site/Bus", "Insertion Loss", "Construction",
                    "Number of Positions/Contacts", "Class Code", "S/N Ratio", "Number of Poles per Deck", "Abrasive Grade",
                    "Circuit", "License - User Details", "NEMA Frame Size", "Type Attributes", "Insulation Material",
                    "Load Force (Static)", "Diode Configuration", "Jacket Color", "Head Style", "Color - Background",
                    "Input Signal", "1st Connector", "Current Rating (Amps)", "Surface Mount Land Size", "Shell Finish",
                    "Receiver Hysteresis", "Lens Style", "Shell Size - Insert", "Stacking Direction", "Pitch - Mating",
                    "Propagation Delay tpLH / tpHL (Max)", "Contact Length - Mating", "Voltage - Input", "Cable Termination", "Fiber Cladding Diameter",
                    "Foil Crimp Area", "Tail Type", "Pad Layout Dimension", "Security Features", "Clip Type",
                    "Panel Mount Type", "Sensitivity (mV/g)", "Voltage - Supply, Single (V+)", "Line Side Connection Type", "Between Board Height",
                    "Overload Protection", "1st Connector Gender", "Shelf Life Start", "Voltage - Forward (Vf) (Max) @ If", "Frequency",
                    "Switch Features", "Material Thickness", "Tip Type", "Sensing Distance", "Sensing Temperature - Remote",
                    "Number of Turns", "Diameter - Turret Head", "FFC, FCB Thickness", "Wire Type", "Shrinkage Ratio",
                    "Display Type", "Current Transfer Ratio (Min)", "Analog Input:Output", "Connector - AC Input", "Release Time",
                    "Wire Termination", "Timing Initiate Method", "Accepts Pin Diameter", "Thermal Resistivity", "Thread Size",
                    "B Value Tolerance", "Coil Resistance", "Depth Behind Panel", "Row Spacing - Mating", "Standard Number",
                    "Assembly Configuration", "Boot Color", "Port Direction", "Tape Type", "Sampling Frequency",
                    "DC Voltage - Input (Max)", "Outside Pipe Diameter (Max)", "RTD Material", "Voltage Supply Source", "Lock Location",
                    "Channels", "Workstand", "Primary Winding(s)", "Pitch - Cable", "Luminous Flux @ Current/Temperature",
                    "Case Color", "Industry Recognized Mating Diameter", "Frequency (Center/Band)", "Input Range (Additional)", "Termination Finish Thickness",
                    "Number of Conductors", "Display & Interface Controllers", "1st Connector Number of Positions Loaded", "Probe Length", "Positions Per Level",
                    "Material - Board", "Static Pressure", "Output Connector", "RAM Size", "Number of Characters Per Row",
                    "Backing, Carrier", "Rds On (Typ)", "Switching Cycles", "Hook, Pincer Opening", "Voltage - UL",
                    "Number of Poles", "2nd Connector Number of Positions", "Shell Size", "Voltage - Rated AC (Phase to Ground/Neutral)", "Location",
                    "Sensing Temperature - Local", "NTC Thermistor", "Platform", "Length - Barrel", "Q @ Freq",
                    "Input Capacitance (Cies) @ Vce", "Cord Termination", "Current - Max", "Voltage - Rated", "Number of Levels",
                    "Output Code", "Capacity", "Width (Inches)", "Illumination", "Operating Mode",
                    "Ranging", "Operating System", "Multiplexer/Demultiplexer Circuit", "Control Interface", "Card Type",
                    "Operating Temperature - Junction", "Voltage - Output 6", "Voltage - Output 2", "Actuator Marking", "Voltage - Output 3",
                    "Dot Pixels", "Voltage - Output 4", "Current - Collector (Ic) (Max)", "Voltage - Output 5", "Voltage - Output 1",
                    "Size / Dimension", "Edition", "Oscillator Type", "Coil Insulation", "RAM Capacity/Installed",
                    "Lamp Type", "Center Conductor Diameter", "Temperature - Test", "Linearity", "Digit/Alpha Size",
                    "Dot Size", "Current - Timekeeping (Max)", "Duty Cycle", "Tip Temperature",
                    "Co-Processors/DSP", "Legend", "Filter Order", "Thickness - Adhesive", "Number of Bands",
                    "Release Force", "Connector Usage", "Thermal Conductivity", "Ripple Current @ Low Frequency", "Modulation or Protocol",
                    "Fin Height", "Circuit per Deck", "Number of Ports", "Supplied Contents", "Resistive Material",
                    "Cable Length", "Distance - Control To Sensor (Max)", "Frequency Response", "Motor Type - AC, DC", "Proximity Detection",
                    "Must Operate", "Frame Width", "LED Color", "Actuator Type", "Terminal - Width",
                    "Pin or Socket", "Contact Form", "Seal Rating", "ESD Protection", "Filter Pass Band",
                    "Number of Characters", "Housing Color", "Temperature Range", "Noise Figure (dB Typ @ f)", "Messaging",
                    "Voltage - I/O", "Voltage - Rated AC (Phase to Phase)", "Optical Pattern", "Number of Decks", "Jacket (Insulation) Diameter",
                    "Voltage - Supply Span (Max)", "Resistance - Input", "Overall Impedance", "Plating - Thickness", "Lead Spacing",
                    "Digital I/O Lines", "Ratings", "Current - Receiving", "Exterior Type (Adapter End)", "Constant Current Load - Max",
                    "Frame Thickness", "Adjustment Type", "Exterior Contact Material", "Measuring Type", "Driver Circuitry",
                    "No Load Power Consumption", "Cable Pitch", "Connector Style", "Pitch", "Pulses per Revolution",
                    "Turns Ratio - Primary:Secondary", "Port Location", "Scan Rate", "Gain", "Linear Range",
                    "Operating Position", "Viewing Area", "Load Side Wire/Stud Size", "Bushing Thread", "Frequency - Operating",
                    "Top Termination", "Gauss Strength", "Kit Type", "Port Style", "Interior Contact Material",
                    "Jacket (Insulation) Material", "Actuator Length", "Temperature Coefficient", "Platform/Top Step Height", "Switching Temperature",
                    "Voltage - Supply (Max)", "Backlight", "Voltage - Secondary (Full Load)", "Breaker Type", "Number of LEDs",
                    "Function - Lighting", "Height Stacking (Mating)", "Display Characters - Height", "Current - Hold (Ih)", "Quantity",
                    "Termination", "Row Spacing", "Settling Time", "Slew Rate", "Actual Diameter",
                    "Response", "Body Material", "Number of Inputs and Type", "Thickness (Max)", "Plating",
                    "Transfer Rate", "Plunger Size", "Shelf Life", "2nd Connector Orientation"
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