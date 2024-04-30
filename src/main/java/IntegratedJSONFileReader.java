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

public class IntegratedJSONFileReader {

    public static void main(String[] args) {

        String categoryFolder = "Integrated Circuits (ICs)";

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
                    "Cable Opening","Inner Diameter - Recovered","Number of Keys","SFP/XFP Ports","Serial Interfaces",
                    "License Length","Memory","Controller Type","Pixel Size","Number of Filters",
                    "Cable Connectors","Fan Type","Gender","Auxiliary Outputs and Type","Module/Board Type",
                    "Maximum Pressure (PSI)","Switch Function/Rating","Cooling Type","Turn Off Time","Mating Orientation",
                    "Rated Functioning Temperature","Hysteresis","Tensile Strength","Lens Type","Max Propagation Delay @ V, Max CL",
                    "Orientation","Number of Positions or Pins (Grid)","Number of Monitor Fields","Diagonal Screen Size","Frequency Bands (Low / High)",
                    "Resistance in Ohms @ 25°C","Filter Type","Attachment Method","Control / Drive Type","Resistor-Ratio-Drift",
                    "Tip Style","Resistance Tolerance","Diameter - Outside","Fits Terminal Size","Signal Lines",
                    "Visual Field (Min)","Active Area","Pushing (MHz/V)","Strap Closure","Reset Time",
                    "Sensing Temperature","Voltage - Output (Typ) @ Distance","Current - Gate Trigger (Igt) (Max)","Operating Range","Control Method",
                    "2nd Connector Number of Positions Loaded","Tab Direction","Lens Mount","Bottom Termination","Board Material",
                    "Input Impedance","Write Cycle Time - Word, Page","Charge Time","Length","Rise Time (Typ)",
                    "Compatible Shell Size","Number of Stations","Function - Audible","Number of Circuits","Photon Detection Efficiency",
                    "Number of Motors","Operating Pressure","Diameter - Shaft","Installation Type","Packages Included",
                    "Base Type","Voltage - Input (Min)","Exterior Housing Material","Step Resolution","Conductor Material",
                    "Diameter - Outside, Non-Expanded","Terminal Block Type","Voltage - Rated AC","Voltage - I/O High","Melting Point",
                    "Modulator Type","SCR Type","Frequency - Transition","Operating Angle","Triac Type",
                    "Voltage - Load","Internal Connection","Voltage - Open Circuit","Common Pin","Contact Timing",
                    "Safety Category","Voltage - Supply Span (Min)","Maximum AC Volts","Length - Actual","Wheel Type",
                    "Load Current","Area (L x W)","Interior Housing Color","Center Contact Material","Form",
                    "Coil Current","Current - Output","Programmable Type","Voltage - Breakdown (Min)","Regulator Type",
                    "Character Size","Handle Length","Noise Figure","Hardware","FPGA Core Cells",
                    "Current - Non Rep. Surge 50, 60Hz (Itsm)","Lens Size","Number of Mixers","Crystal Material","Composition",
                    "FET Feature","Inductance Frequency - Test","Current - Quiescent (Iq)","Diameter - Inside","Bushing, Grommet Type",
                    "Fastening Type","Current - IEC","Indicator","Arrangement","Hardware Type",
                    "Fault Protection","Beam Pitch","Divider/Multiplier","Simplex/Duplex","Bundle Diameter",
                    "Current - DC Forward (If) (Max)","Diameter - Label","Voltage - Rated DC","Power Input","Number of Layers",
                    "Values","Shutter","Current - Output Source/Sink","Current - Charging","Outlet Types and Quantity",
                    "Sensor Type","On-State Resistance (Max)","Available Total Delays","Tool Method","Watt Density @ 12V",
                    "Current - Short Circuit Rating (SCCR)","1st Contact Gender","Accomodates a Fuse","Output Isolation","Programmable Features",
                    "Power (dBm)","Release Range","Base Oil","Termination Style","Rate",
                    "Return Loss (Low Band / High Band)","Current - Continuous Drain (Id) @ 25°C","Hole Diameter","Package Quantity","2nd Connector Type",
                    "Adapter Series","Stud/Tab Size","DAC Type","Voltage - Clamping","Free Length",
                    "Seat Type","Voltage - Output Supply","Adhesive","Frame Type","Synchronous Rectifier",
                    "BTU's","Jacket (Insulation) Thickness","Small Diameter Recovered","Flange Feature","Outline",
                    "Differential - Input:Output","Current - Output 5","Master fclk","Thickness - Backing, Carrier","Current - Output 2",
                    "Current - Output 1","Number of Outputs and Type","Vce(on) (Max) @ Vge, Ic","Length - Overall","Current - Output 4",
                    "Current - Output 3","Cleaning Width","Height (Inches)","RAM Controllers","Number of Positions Loaded",
                    "Acceleration","Gear Reduction Ratio","DC Current Gain (hFE) (Min) @ Ic, Vce","Wire Duct Type","Package Accepted",
                    "Diode Type","Lifting Power","Interior Type (Adapter End)","Construction Material","Disconnect Type",
                    "Diameter - Shank","Voltage - IEC","Lead Style","Number of Wire Entries","Length - Approximate",
                    "Tuning Word Width (Bits)","Must Release","Capacitance @ Vr, F","Driven Configuration","Voltage - Isolation",
                    "Fits Fan Size","Secondary Winding(s)","ESR (Equivalent Series Resistance)","Resistance","Number of Macrocells",
                    "Size - Display","Co-Processor","Output Fan","Connectivity","Field of View",
                    "Vce Saturation (Max)","Current - Off State (Max)","Cable Type","Spectral Range","INL/DNL (LSB)",
                    "Shell Material, Finish","Height Above Board","Internal Switch(s)","Sigma Delta","Pitch - Post",
                    "Function","Insulation Diameter","Lamp Output","Wire Gauge or Range - Coaxial","Voltage - Output",
                    "Inner Diameter - Supplied","P1dB","Touchscreen","Primary Material","Working Dimensions",
                    "Number of Bits per Element","Spectral Bandwidth","Height (Max)","Intended Chipset","Accuracy - Highest (Lowest)",
                    "Mandrel Diameter","Number of Lines","Contact Finish - Post","Isolation","Capacitance",
                    "Power (Typ) @ Conditions","Shell Style","Color - Actuator/Cap","Frequency Tolerance","Current - Peak Pulse (10/1000µs)",
                    "Closing Force","Insulation Height","Element Type","Voltage - Primary","Voltage - Supply, Dual (V±)",
                    "Condensation Capacity","Flex Strength","AC Outlets","Base Resonator","Utilized IC / Part",
                    "Frequency Group","Voltage - Max","Propagation Delay (Max)","Sensing Light","Port Size",
                    "Slotted/Unslotted","Number of SCRs, Diodes","Resistance @ 0°C","Switch Function","Arm",
                    "CMRR, PSRR (Typ)","Charge Current - Max","Conversion Type","Height - Seated (Max)","FWFT Support",
                    "Lumens/Watt @ Current - Test","Inductance","Card Size Range","Mounting Hole Diameter","Motor Type",
                    "Battery Chemistry","Voltage - Supply, Digital","Number of Positions","Safety Outputs and Type","Fluid Capacity",
                    "Abrasive Grit","Current Rating - AC","Tip Shape","Wavelength - Dominant","Propagation Delay","Noise",
                    "Steps per Revolution","Flow Direction","Pressure Type",
                    "Memory Type","Connector Impedance","Relay Type","Flux Type","Radiant Intensity (Ie) Min @ If",
                    "Batteries Required","Efficiency","Approved Countries","Voltage - Forward (Vf) (Typ)","Wall Thickness",
                    "Amplifier Type","Socket Depth","Center Tap","Air Flow - Low","Logic Type",
                    "Delay Time","Key Type","Vgs(th) (Max) @ Id","Power (Watts)","Mesh Type",
                    "Number of Stacks","Cable Length - Unexposed","Current Rating - DC","Tail Diameter","Fuse Type",
                    "Jaw Opening","Power - Output Surge","Voltage Range","Lamp Color","Plug/Mating Plug Diameter",
                    "Bandwidth","RF Type","Wavelength - Peak","Can Replace Lamps","Mounting Style",
                    "Capacitance @ Frequency","Rotation","Current Rating - Circuit Breaker","Number of Cores per Jack","Contact Layout, Typical",
                    "Maximum DC Volts","Interior Contact Finish","Strap Material","Load Side Connection Type","Dynamic Range, ADCs / DACs (db) Typ",
                    "Trip Status","For Use With/Related Manufacturer","Count","Density","Voltage Rating - AC",
                    "Sensitivity (LSB/(°/s))","Failure Rate","Coil Type","Blade Size","Test Condition",
                    "Approval Agency","Contact Finish Thickness","Supplied Tips/Nozzles","Nozzle Opening","Input Connector",
                    "Bike Qty (Max)","Fuse Size","Ripple Current @ High Frequency","Line Side Connections Per Pole","Current",
                    "Bin Quantity","Maximum Temperature Limit","Tab Width","Lens Transparency","Response Time",
                    "2nd Connector Mounting Feature","Battery Type, Function","Frequency - Center","Backset Spacing","Modulation",
                    "Contact Material","Return Loss","Color - Enhanced","Voltage - Supply (Vcc/Vdd)","Data Format",
                    "On-Chip RAM","Voltage - Gate Trigger (Vgt) (Max)","Vce Saturation (Max) @ Ib, Ic","Lift Height","Voltage - Output (Max)",
                    "Transformer Type","Contact Finish Thickness - Post","Oil Capacity","Type","Sensitivity",
                    "Voltage Rating - DC","Resistance - Wiper (Ohms) (Typ)","Torque - Rated (oz-in / mNm)","Pressure Range","1st Connector Orientation",
                    "Voltage Rating","Warning Range","Cable Material","2nd Connector Gender","Conduit Thread Size",
                    "Voltage - Supply, Analog","Input","Operating Force","Voltage - VCCA","Technology",
                    "Expansion Type","Refrigerated","Voltage - VCCB","Probe Type","Impulse Discharge Current (8/20µs)",
                    "Memory Organization","Connection Method","Varistor Voltage (Typ)","Pump Type","Memory Size",
                    "Shutdown","Number of Gates","Proto Board Type","Ripple","Idle Current, Typ @ 25°C",
                    "Fuse Holder, Drawer","Resolution","Core Processor","Current Transfer Ratio (Max)","Voltage - DC Spark Over (Nom)",
                    "Mating Cycles","Cable Impedance","Output Configuration","Td (on/off) @ 25°C","Shaft Detail",
                    "Link Range, Low Power","Secondary Attributes","Supply Voltage","Material - Frame","Range °/s",
                    "Input Type","Tool Type","Clock Sync","Number of Outputs","Head Height",
                    "Number of 5-Tie Point Terminals","Cable Diameter","Current - Collector Cutoff (Max)","Compatible D-Sub Size","Number of Batteries Included",
                    "Maximum Pressure","Screw, Thread Size","Size","Display Mode","Data Interface",
                    "Light Source","Noise - 10Hz to 10kHz","Design","S/N Ratio, ADCs / DACs (db) Typ","PoE Ports",
                    "For Use With/Related Products","Test Frequency","Expandable","Time Format","Efficiency - Type",
                    "Load Force (Dynamic)","Input Capacitance","Transfer Rate (Mb/s, MT/s, MHz)","Recovered Wall Thickness","2nd Connector",
                    "Qualification","Lamp/Magnifier Type","LED Driver Channels","Current - Output High, Low","Light Color",
                    "Adjustment Range","Pin Hole Diameter","Ingress Protection","Output Type","Actuator Length, Right Angle",
                    "CRI (Color Rendering Index)","Current - Input","Current - Output (Typ)","Rechargeability","Isolated Power",
                    "Insertion Loss (Max)","Number of Jaws","Connector - Sensor","Length - Below Head","SFP/XFP Type",
                    "Tab Length","Peripherals","Load - Max Switching","Tine Shape","Trip Range",
                    "Resistance - 25°C (Typ)","Coil Voltage","Shield Termination","Flow (CFM)","Character Format",
                    "Legend (Text)","Fiber Core Diameter","Contact Finish","Control/Display Type","Number of Positions/Bay/Row",
                    "Ejector Side","Flux @ 25°C, Current - Test","Wire Gauge or Range - AWG","Protection","For Measuring",
                    "Resistance @ 25°C","Current - Test","Battery Cell Size","Deflection Angle","Weight",
                    "Graphics Color","Current - Discharge (Max) (8/20µS)","Particle Size","Voltage - Nominal","Megapixels",
                    "Turn On / Turn Off Time (Typ)","Number of Cores","Wick Type","Number of ADCs / DACs","End Detail",
                    "Language","Weight (Pounds)","Measuring Range","Voltage - Start Up","Resistance (Ohms)",
                    "1st Connector Mounting Feature","Current - Startup","Delay Time - OFF","Speed","Width - Overall",
                    "Delta Tmax @ Th","Bearing Type","Coverage","Contact Shape","Conductor Strand",
                    "Delay Time tpd(1) Max","Diameter - Inside, Expanded","Accessory Type","Voltage - Auxiliary","Melting I²t",
                    "Head Diameter","Current - Switching","Tab Thickness","Contact Type","Response Frequency",
                    "Gain Bandwidth Product","Voltage - Collector Emitter Breakdown (Max)","Number of Cable Pairs","Sheath Opening","Feedback Type",
                    "Channel Type","Input Range","Work Area (Reach)","Sampling Rate (Per Second)","Edge Contacts",
                    "Distance","Voltage - Continuous Operating (Max) (MCOV)","FPGA Registers","Number of Cells","Length - Blade",
                    "Translator Type","Bulb","Material - Insulation","Outline L x W x H","With Modem Control",
                    "Number of A/D Converters","Visible Output","Boreholes","B25/100","Flow (SCFM)",
                    "Current - Dark (Id) (Max)","Legend (Symbol Only)","Input Resolution","Date Format","Energy",
                    "Must Operate Voltage","Voltage Dropout (Max)","Main Purpose","FIFO's","Phase Difference",
                    "Application Specifics","Power - Max","Material - Housing & Prism","Outer Dimension","Number of D/A Converters",
                    "Ignition Type","Noise - 0.1Hz to 10Hz","Center Contact Plating","Voltage - Peak Reverse (Max)","Encoder Type",
                    "Current - Input Bias","Impact Velocity","Power - Input","Package / Case","Diameter - Barrel",
                    "Current - Carry","Number of Independent Delays","Style/Size","Mounting Type","Number of Coils",
                    "Coating, Housing Type","Visibility","Outside Pipe Diameter (Min)","Process Side Connection","Panel Cutout Shape",
                    "Current - Output / Channel","Threaded/Unthreaded","Inductance @ Frequency","Frequency Range","Pump Rate",
                    "Reverse Recovery Time (trr)","Voltage - Input (Max)","Text Color","Read Out","Resistor - Base (R1)",
                    "Fluid Type","Number of Capacitors","Supplier Device Package","Cleaner, Treatment Type","PLL",
                    "Large Diameter Supplied","Data Rate","Cord Type","Finish","Actuator Height off PCB, Vertical",
                    "Number of Drivers","LED","Tool Type Feature","Opening Force","Counter/Timers",
                    "Frequency - Output 2","Frequency - Output 3","Frequency - Output 1","Configuration","Frequency - Output 4",
                    "Crosstalk","Frequency Stability","Voltage - On State (Vtm) (Max)","Number of Distribution Buses","Minimum Bend Radius",
                    "Flow Rate","2nd Connector Shell Size - Insert","(Select First, Then Apply Filters) Compatible Series","Number of Inputs","Voltage - DC Reverse (Vr) (Max)","Material - Body",
                    "Sensing Range","Insertion Loss","Construction","Number of Positions/Contacts","S/N Ratio",
                    "Circuit","License - User Details","NEMA Frame Size","Type Attributes","Input Logic Level - High",
                    "Current - Input Bias (Max)","Input Signal","Surface Mount Land Size","Shell Finish","Shell Size - Insert",
                    "Interrupt Output","Dimensions - Overall","Inlet/Outlet Size","Pitch - Mating","Voltage - Input",
                    "Contact Length - Mating","Current - Quiescent (Max)","Cable Termination","Fiber Cladding Diameter","Voltage - Supply, Single (V+)",
                    "Line Side Connection Type","Overload Protection","Shelf Life Start","Frequency","Noise Floor",
                    "Material Thickness","Washable","Varistor Voltage (Min)","Tip Type","Voltage - Protection Rating (VPR)",
                    "Process","Number of Turns","Copper Type","FFC, FCB Thickness","Wire Type",
                    "Thermal Resistance @ GPM","Shrinkage Ratio","Analog Input:Output","Size - Body","Number of Beams",
                    "Current - Surge","Timing Initiate Method","Wire Termination","Current - Saturation (Isat)","Architecture",
                    "Protection Range","Current - Drain (Idss) @ Vds (Vgs=0)","Current - Short Circuit (Isc)","Thread Size","Orifice Diameter",
                    "Depth Behind Panel","Row Spacing - Mating","Base Diameter","Tape Type","Sampling Frequency",
                    "Depth (Inches)","DC Voltage - Input (Max)","Phase Noise Typ (dBc/Hz)","Outside Pipe Diameter (Max)","RTD Material",
                    "Workstand","Primary Winding(s)","Pitch - Cable","Case Color","Base Unit",
                    "Number of Flutes","Input Range (Additional)","Termination Finish Thickness","Number of Conductors","Display & Interface Controllers",
                    "1st Connector Number of Positions Loaded","Backing, Carrier","Title","Fluid Temperature","Rds On (Typ)",
                    "Switching Cycles","Drain Type","Number of Poles","Shell Size","FPGA Gates",
                    "Location","NTC Thermistor","Regulator Current (Max)","Length - Barrel","Q @ Freq",
                    "Manufacturer Size Code","Cord Termination","Firmness","Current - Max","Tuning Voltage (VDC)",
                    "Capacity","Width (Inches)","Current Rating (Max)","Card Type","Operating Temperature - Junction",
                    "Max Output Power x Channels @ Load","Coil Insulation","Temperature - Test","Linearity","Dot Size",
                    "Current - Timekeeping (Max)","Data Converters","Tip Temperature","Co-Processors/DSP","Chipset Manufacturer",
                    "Legend","Thickness - Adhesive","Number of Bands","Release Force","Width / Cross Section",
                    "Connector Usage","Ripple Current @ Low Frequency","Thermal Conductivity","Modulation or Protocol","Reset Force",
                    "Supplied Contents","Current @ Pmpp","Resistive Material","Vgs (Max)","Cable Length",
                    "NPT Size","DC Resistance (DCR) (Max)","Must Operate","Actuator Level",
                    "Actuator Type","LED Color","End Style","Contact Form","Filter Pass Band",
                    "Travel Range","Temperature Range","Noise Figure (dB Typ @ f)","Messaging","Voltage - Rated AC (Phase to Phase)",
                    "Air Flow - High","Inputs - Side 1/Side 2","Impedance @ Frequency","Wattage","Lead Spacing",
                    "Number of Bits/Stages","Ratings","Exterior Contact Material","Cushioning","Guide Method",
                    "Liquid Protection","Sensing Angle","Connector Style","Bus Connection","Pitch",
                    "Port Location","Scan Rate","Linear Range","Bushing Thread","Top Termination",
                    "Gauss Strength","Kit Type","Port Style","Temperature Coefficient","Platform/Top Step Height",
                    "Switching Temperature","Voltage - Secondary (Full Load)","Display Characters - Height","Switching Energy","Settling Time",
                    "Door","Fiber Ports","Response","Duty Cycle (Max)","Body Material",
                    "Number of Inputs and Type","Thickness (Max)","Plating","Transfer Rate","Shelf Life",
                    "2nd Connector Orientation","Fuseholder Type","With False Start Bit Detection","Dielectric Material","Shield Type",
                    "Count Rate","Pin Size - Below Flange","Contact Finish Thickness - Mating","Humidity Range","Header Orientation",
                    "Material Flammability Rating","Aperture","Length - Flute","Conductor Type","Length - Threaded Portion Below Head",
                    "Regulator Topology","Signal Conditioning","Background Color","Sealed","Battery Series",
                    "Ratcheting","Must Release Voltage","Current - Peak Output (Source, Sink)","Bus Directional","Frequency - Max",
                    "Opening Size","Measurement Type","Current - Leakage (IS(off)) (Max)","Trigger Type","Length - Above Flange",
                    "Current Rating (Per Contact)","Specifications","Output","Circuit Pattern","Abrasive Material",
                    "Voltage - Supply, Battery","Total Length Supplied","1st Connector Mounting Type","Thickness - Overall","Number of Slots/Relays",
                    "Wind Direction","Current - Cathode","Locking Feature","Illumination Color","Container Type",
                    "Step Quantity","Trip Temperature Threshold","Interface","Power - Average Forward","Rated Life",
                    "Temperature Display","Load Type","Transistor Type","Storage Interface","High Side Voltage - Max (Bootstrap)",
                    "Dimensions - Panel","Material - Tip","Reel Capacity","Connector - AC Output","Responsivity @ nm",
                    "Current - Hold (Ih) (Max)","Motor Type - Stepper","Reference Type","Drive Size","Lens Diameter",
                    "Number of Columns","Storage","Output","Output Phases","Large Recovered Length",
                    "Abrasion Protection","Capacity (Pounds)","Installation Distance","Shelf Quantity","Holding Temperature",
                    "Outside Cable Diameter (Max)",
                    "1st Connector Shell Size - Insert","Reset Temperature","Connector/Contact Type","Legend Type","Bidirectional Channels",
                    "Fan Location","Applications","Length - Below Flange","Number of Modules","B.P.F. Center Frequency",
                    "Control Side Connection","Voltage Reference","Clock Frequency","Storage/Refrigeration Temperature","Profile (W x H)",
                    "Insertion, Removal Method","Frames per Second","Programmable","Label Size","Key Color",
                    "Delay Time - Propagation","Number of Bits","SATA","Printer Name","Load",
                    "Max Count Rate","Holding Force","Shielding","Detection Capability","Current - Supply",
                    "Fuel Capacity (Gallons)","Flange Diameter","Height - Overall","Timing","Pressure (PSI)",
                    "Shell Size - Insert (Convert From)","Diameter - Small Outside","Signal Types","Non-Volatile Memory","Channels per IC",
                    "Rise / Fall Time (Typ)","Watchdog Timer","Common Mode Transient Immunity (Min)","Number of Taps","Current - Supply (Main IC)",
                    "Wire Gauge","Direction","Power - Output","Depth","Duplex",
                    "Power Dissipation (Max)","Form Factor","Style","Current - Trip (It)","Width",
                    "Usage","Impedance - Unbalanced/Balanced","USB","RS-232 (422, 485)","Voltage - Input Offset",
                    "Switch Circuit","Reset Operation","Air Flow - Medium","Polarization","Program Memory Type",
                    "Air Flow","Temperature Coefficient (Typ)","Shell Size - Insert (Convert To)","Resistance @ If, F","Outer Diameter",
                    "DC Voltage - Input (Min)","Number of Cores/Bus Width","Reset","Pin Size - Above Flange","Load Capacitance",
                    "Shaft Size","Thread Length","Total RAM Bits","Small Recovered Length","Sensing Object",
                    "Mounting Hole Spacing","Lifetime @ Temp.","Control Features","Number of Logic Elements/Blocks","Color - Legend",
                    "Pretravel","Surface Type","Resistance - Channel (Ohms)","DigiKey Programmable","Connector - Cable",
                    "w/Sequencer","Plug Type","Tape Width - Max","Card Thickness","Accuracy",
                    "Format","Frequency - Switching","Wire/Cable Tie Type","Frequency - Self Resonant","Diameter - Inside, Non-Expanded",
                    "Leg Length","Voltage - Output (Min/Fixed)","Label Type","Memory Format","Convert From (Adapter End)",
                    "Differential Travel","Conduit Hub Size","Magnetization","With Auto Flow Control","Cut Edge",
                    "Drive Type","Length - Center to Center","Breaking Capacity @ Rated Voltage","Standards","Voltage Rating - Appliance Inlet",
                    "Overtravel","Circuit Type","Display Count","Body Finish","Tip Size",
                    "Run Time (Hours)","Total Length Recovered","Power Supply Type","Delay to 1st Tap","Barrier Type",
                    "Housing Material","Input Capacitance (Ciss) (Max) @ Vds","Insertion Angle","Operate Time","Torque - Holding (oz-in / mNm)",
                    "Compatible Tools","Wheel/Caster Diameter","Exterior Contact Finish","Shell Size, Connector Layout","Light Emitting Surface (LES)",
                    "Delay Time - ON","Functions, Extra","Number of LABs/CLBs","Rotational Life (Cycles Min)","Current - Bias",
                    "Number of Positions (Convert From)","Large Diameter Recovered","Current - Reverse Leakage @ Vr","Contact Size","Region Utilized",
                    "Contact Length - Post","Dimming","Material Finish","Number of Resistors","Strap Termination",
                    "Absolute Pull Range (APR)","Millicandela Rating","Core Type","Operate Range","Rotation Angle - Electrical, Mechanical",
                    "Access Time","Terminal Size","Image Type","Termination Finish","Turn On Time",
                    "Voltage - Reverse Standoff (Typ)","Coupler Type","Data Rate (Max)","Protocol","Resolution (Bits)",
                    "Terminal Style","Number of Gangs","Flash Size","Switch Type","Input Logic Level - Low",
                    "Material - Magnet","VSWR","Resistance - Initial (Ri) (Min)","Voltage - Core","Writable Memory",
                    "Display Style","CCT (K)","Pressure Regulation","Compliance","Resistance - RDS(On)",
                    "Wire Entry Location","Schmitt Trigger Input","Load Side Connections Per Pole","Current - Discharge (Nom) (8/20µS)","Voltage - Supply",
                    "Capacitance Range","Coded, Keyed","Stroke Length","Power (Watts) - Per Port","Print Type",
                    "Memory Interface","Rivet Length","Exterior Housing Color","Length - Tip","Wavelength",
                    "Full Scale","Voltage - Input Offset (Max)","Output Signal","Product Type","Core Size",
                    "Voltage - Zener (Nom) (Vz)","Output Function","Filament Material","Class","Amplitude (Level) Range",
                    "Power Dissipation @ Temperature Rise","Time to Trip","Current - Supply (Max)","Control Side Cable Retainer","Lumens",
                    "Voltage - Clamping (Max) @ Ipp","Features","Contact Rating @ Voltage","Coupling Factor","Temperature Rating",
                    "Current - Transmitting","Utilized LED","Tolerance","RF Family/Standard","Mounting Feature",
                    "Cable Group","Actuation Force","Timing Adjustment Method","Shrouding","Active Pixel Array",
                    "2nd Contact Gender","Housing Finish","Connector - Voltage Input","Impact Capacity per Cycle","Built in Switch",
                    "Capacity (Gallons)","Voltage - Threshold","Current - Sensing","Bulb Type/Size","Channel Capacitance (CS(off), CD(off))",
                    "Plug Wire Entry","w/Supervisor","Fan Accessory Type","Efficiency - dBA","IIP3",
                    "Length - Extended","Legend Color","Contact Rating (Current)","Terminal Type","Hardness",
                    "Length - Shaft and Bearing","Number of Axes","Backup Time - Max Load","MSCP (Mean Spherical Candle Power)","Power (Watts) - Max",
                    "Impedance (Max) (Zzt)","Rear Panel Wire Connection","Surge Rating (Watts)","Shape","Dark Count Rate",
                    "Center / Cutoff Frequency","Sensitivity (LSB/g)","Speed - Write","Sensing Method","Voltage/Current - Output 2",
                    "Voltage/Current - Output 3","Voltage/Current - Output 1","Filament Diameter","Charge Injection",
                    "Power Line Protection","Panel Cutout Dimensions","Acceleration Range","Resistance - Post Trip (R1) (Max)","Thickener",
                    "Crystal AR","Frequency Stability (Total)","Voltage - Output Difference (Typ) @ Distance","Flat Flex Type",
                    "Torque","FPGA SRAM","Beam Angle","Power - Cooling","MTU",
                    "Number of Channels","Actuator Orientation","Current Rating - Appliance Inlet","Additional Interfaces","Connection Type",
                    "Number of Elements","Graphics Acceleration","End - Size","Range of Values","Footprint",
                    "Cable End Type","Voltage - Secondary","B0/50","Grade","Length - Pin",
                    "Width - Emitting Surface","Thermal Resistance @ Forced Air Flow","RF Frequency","Step Angle","Drive Voltage (Max Rds On, Min Rds On)",
                    "Heat Transfer Type","Shell Material",
                    "Contact Termination","Overall Contact Length","Controller Series","Taper","Temperature",
                    "Viewing Angle","Voltage - Switching DC","Switch Actuation Level","Contact Mating Finish","Front Connection",
                    "Kit Contents","Voltage","Thermal Resistance @ Natural","Seat Height","Cable Exit",
                    "Current - Average Rectified (Io) (per Diode)","Nominal I.D.","Axis","Drawer Quantity","GPIO",
                    "Varistor Voltage (Max)","Length - Lead Wire","Cord Length","Luminous Flux","Data SRAM Bytes",
                    "Baud Rates","Actuator Diameter","Copper Ports","Tip Diameter","Number of Stages",
                    "Pulse Width Distortion (Max)","Current - UL","Panel Thickness","Return Loss (Min)","Included MCU/MPU Board(s)",
                    "Length - Emitting Surface","Unidirectional Channels","Embedded","Material - Core","Current - Average Rectified (Io)",
                    "Diameter (Inches)","Primary Attributes","IGBT Type","Switch Time (Ton, Toff) (Max)","Coverage Square Feet",
                    "Output Power","Communications","Current - On State (It (AV)) (Max)","Independent Circuits","2nd Connector Mounting Type",
                    "Thickness","Illumination Voltage (Nominal)","Rivet Diameter","Insulation Color","Screw Head Type",
                    "Input Range (Current, Voltage)","Mated Stacking Heights","Impedance","B25/85","Output Range",
                    "Suggested Programming Environment","B25/75","Channel-to-Channel Matching (ΔRon)","Media Lines Protected","w/LED Driver",
                    "Contact Finish - Mating","Flute Radius","Digits or Characters","Number of I/O","-3db Bandwidth",
                    "Mounting Rails","Voltage - Switching AC","Number of Tie Points (Total)","Publisher","Line Side Wire/Stud Size",
                    "Interconnect System","Board Type","Resistor Matching Ratio","Charger Included","Frequency - Cutoff or Center",
                    "Filtration Level","Voltage - Cutoff (VGS off) @ Id","Number of Regulators","Gate Type","Visual Field (Max)",
                    "Current - Output (Max)","Shell Plating","Diameter - Bit","Material - Cone","ET (Volt-Time)",
                    "Usable Temperature Range","Insulation","Color","Width - Outer Edges","Inner Dimension",
                    "Power - Output Continuous","Duration","Actuator Style/Size","Lamp Wattage","Module Capacity",
                    "Gate Charge","Ethernet","Outputs and Type","Ventilation","Contact Material - Mating",
                    "Lens Color","Sensor","Shrink Temperature","Dead Time","Dash Number",
                    "Current - Peak Output","Auxiliary Sense","Manual Override","Tap Increment","Sound Pressure Level (SPL)",
                    "Channels per Circuit","Wire Gauge or Range - mm²","Outer Diameter - Emitting Surface","Network Technology","Illumination Type, Color",
                    "Voltage - Supply (Min)","Material","Front Panel Wire Connection","Ratio - Input:Output","Switching Voltage",
                    "Index Stops","Center Gender","Power - Peak Pulse","1st Connector Type","EEPROM Size",
                    "Voltage - I/O Low","Wattage - Load","2nd Harmonic, Typ (dBc)","Sensitivity (mV/°/s)","Torque - Max Momentary (oz-in / mNm)",
                    "LO Frequency","Flux @ 85°C, Current - Test","Matrix (Columns x Rows)","Accepts Square Pin Size","Fail Short",
                    "Ferrule Material","Pitch - Connector","Adapter Type","Micron Size","DC Resistance (DCR)",
                    "Media Delivery Type","Antenna Type","Display Format","Contents","Capacitance - Input",
                    "Number of DAC's","Current - Dark (Typ)","Approval Agency Marking","Voltage - Supply, Single/Dual (±)","Diameter - Large Outside",
                    "Current Rating - Filter","Wavelength Range","Efficiency - Testing","Spread Spectrum Bandwidth","Ratio - S/H:ADC",
                    "Connector Type","Contact End","Number of Contacts","Structure","Diameter",
                    "Qmax @ Th","Number of Positions (Convert To)","Lock Type","Number of Drivers/Receivers","Reset Timeout",
                    "Fiber Type","Voltage - Off State","Stroke Per Jaw","Number of Pins","B25/50",
                    "Operating Temperature","Contact Material - Post","Key Removable Positions","Topology","Antenna Connector",
                    "Clock Rate","Positions / Poles","Flow Sensor Type","Small Diameter Supplied","Current - On State (It (RMS)) (Max)",
                    "Working Distance","Funnel","Panel Hole Size","Shelf Capacity (Pounds)","Convert To (Adapter End)",
                    "RPM","Includes","Power - Rated","Magnification Range","Author(s)",
                    "Horsepower","Detent","Number of Terminations","Height","Length (Inches)",
                    "Package Cooled","Video Outputs","Number of Rows","Outside Cable Diameter (Min)","Protective Height",
                    "Compensated Temperature","Mode","Angle of Throw","1st Connector Number of Positions","Lens Style/Size",
                    "Program SRAM Bytes","Speed - Read","Number of Drawers","Measurement Error","Camera Type",
                    "Rotation Angle","Actuator Material","Start Type","Attenuation Value","Current Ratio",
                    "Shell Size, MIL","Gate Charge (Qg) (Max) @ Vgs","Detector Type","Interior Housing Material","AC Voltage - Input",
                    "Cavity A","Voltage - Protection Level (VP) (2416)","Cavity F","Cavity C","Cavity B",
                    "Cavity E","Board Thickness","Cavity D","Expansion Site/Bus","Class Code",
                    "Number of Poles per Deck","Cross Section Shape","Abrasive Grade","Rds On (Max) @ Id, Vgs","Insulation Material",
                    "Jaw Type","Load Force (Static)","Diode Configuration","Jacket Color","Color - Background",
                    "Thread/Screw/Hole Size","1st Connector","Current Rating (Amps)","Voltage - Anode - Cathode (Vak)(Max)","Receiver Hysteresis",
                    "Lens Style","Programmable Flags Support","Stacking Direction","Propagation Delay tpLH / tpHL (Max)","Number of Dots",
                    "Output Alarm","Heat Protection","Tail Type","Voltage Supply - Internal","Security Features",
                    "Panel Mount Type","Sensitivity (mV/g)","Between Board Height","1st Connector Gender","Voltage - Forward (Vf) (Max) @ If",
                    "Environment Protection","Switch Features","Low Band Attenuation (min / max dB)","Rated Watts","Wire Diameter",
                    "Remote Capability","Sensing Distance","Sensing Temperature - Remote","Drain to Source Voltage (Vdss)","Maximum Payload",
                    "Display Type","Number of Taps/Steps","Current Transfer Ratio (Min)","Connector - AC Input","Release Time",
                    "Battery Pack Voltage","Fader Type","Accepts Pin Diameter","Thermal Resistivity","Chemical Component",
                    "B Value Tolerance","Coil Resistance","Standard Number","Assembly Configuration","Program Memory Size",
                    "Boot Color","Port Direction","Max Load","Number of Units","Voltage Supply Source",
                    "Lock Location","Channels","Fuel Type","Luminous Flux @ Current/Temperature","Frequency (Center/Band)",
                    "ISBN","Industry Recognized Mating Diameter","Handle Type","Positions Per Level","Bend Radius (Flow)",
                    "Static Pressure","RAM Size","Output Connector","Display Digits","Number of Characters Per Row",
                    "Number of Voltages Monitored","Current - Modulation","Voltage - UL","With IrDA Encoder/Decoder","Protection Features",
                    "Voltage - Rated AC (Phase to Ground/Neutral)","2nd Connector Number of Positions","Burst Pressure","Sensing Temperature - Local","Platform",
                    "Thermal Resistance","Inner Diameter","Input Capacitance (Cies) @ Vce","Voltage - Rated","Number of Levels",
                    "Engine Type","Icc Max","Operating Mode","Illumination","Supplied Iron, Tweezer, Handle",
                    "Ranging","Operating System","Control Interface","Multiplexer/Demultiplexer Circuit","Logic Voltage - VIL, VIH",
                    "Voltage - Output 6","Voltage @ Pmpp","Voltage - Output 2","Cooling Square Feet","Voltage - Output 3",
                    "Dot Pixels","Actuator Marking","Voltage - Output 4","Current - Collector (Ic) (Max)","Voltage - Output 5",
                    "Number of Terminal Strips","Voltage - Output 1","Size / Dimension","Oscillator Type","Edition",
                    "RAM Capacity/Installed","Lamp Type","Center Conductor Diameter","Digit/Alpha Size","Duty Cycle",
                    "Filter Order","Fin Height","Circuit per Deck","Number of Ports","Distance - Control To Sensor (Max)",
                    "Number of Logic Elements/Cells","Motor Type - AC, DC","Proximity Detection","Terminal - Width","Differential Output",
                    "Pin or Socket","Seal Rating","ESD Protection","Power Per Element","Number of Characters",
                    "Voltage - Test","Housing Color","Resistor - Emitter Base (R2)","Voltage - I/O","Optical Pattern",
                    "Jacket (Insulation) Diameter","Number of Decks","Voltage - Supply Span (Max)","Resistance - Input","Overall Impedance",
                    "Plating - Thickness","Retransmit Capability","Digital I/O Lines","Current - Receiving","Exterior Type (Adapter End)",
                    "Adjustment Type","Bin Size","PSRR","Measuring Type","Voltage - Breakdown",
                    "Carrier Network","Selectable Hysteresis","Driver Circuitry","Dropping Point","No Load Power Consumption",
                    "Available Frequency Range","High Band Attenuation (min / max dB)","Voltage - Limiting (Max)","Turns Ratio - Primary:Secondary","Pulses per Revolution",
                    "Gain","Operating Position","Viewing Area","Load Side Wire/Stud Size","Frequency - Operating",
                    "FET Type","Jacket (Insulation) Material","Interior Contact Material","Actuator Length","Operation",
                    "Voltage - Supply (Max)","Backlight","Meter Type","Number of LEDs","Breaker Type",
                    "Function - Lighting","Output Resolution","Quantity","Termination","Row Spacing",
                    "Slew Rate","Mat Type","Actual Diameter"
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