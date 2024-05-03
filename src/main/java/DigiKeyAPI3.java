import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class DigiKeyAPI3 {

    // OAuth 2.0 authorization endpoints
    private static final String ACCESS_TOKEN_URL = "https://api.digikey.com/v1/oauth2/token";

    // API endpoint
    private static final String API_URL = "https://api.digikey.com/products/v4/search/keyword";
    private static final String CLIENTLIST = "ClientList.txt";
    private static final String CATEGORYLIST = "CategoriesList.txt";

    /**
     * Selects the appropriate file name based on the provided keyword.
     *
     * @param keyword The keyword used to determine the file name.
     * @return The selected file name.
     */
    private static String selectingFileName(String keyword) {
        String fileName;

        switch (keyword) {
            // Capacitor Subcategories
            case "Capacitor Accessories" -> fileName = "Capacitor Accessories";
            case "Aluminum - Polymer Capacitors" -> fileName = "Aluminum - Polymer Capacitors";
            case "Aluminum Electrolytic Capacitors" -> fileName = "Aluminum Electrolytic Capacitors";
            case "Capacitor Networks, Arrays" -> fileName = "Capacitor Networks, Arrays";
            case "Ceramic Capacitors" -> fileName = "Ceramic Capacitors";
            case "Electric Double Layer Capacitors (EDLC), Supercapacitors" -> fileName = "Electric Double Layer Capacitors (EDLC), Supercapacitors";
            case "Film Capacitors" -> fileName = "Film Capacitors";
            case "Mica and PTFE Capacitors" -> fileName = "Mica and PTFE Capacitors";
            case "Niobium Oxide Capacitors" -> fileName = "Niobium Oxide Capacitors";
            case "Silicon Capacitors" -> fileName = "Silicon Capacitors";
            case "Tantalum - Polymer Capacitors" -> fileName = "Tantalum - Polymer Capacitors";
            case "Tantalum Capacitors" -> fileName = "Tantalum Capacitors";
            case "Thin Film Capacitors" -> fileName = "Thin Film Capacitors";
            case "Trimmers, Variable Capacitors" -> fileName = "Trimmers, Variable Capacitors";

            // Connectors, Interconnects
            case "Plugs and Receptacles" -> fileName = "Plugs and Receptacles";
            case "Power Entry Connector Accessories" -> fileName = "Power Entry Connector Accessories";
            case "Power Entry Modules (PEM)" -> fileName = "Power Entry Modules (PEM)";
            case "ARINC" -> fileName = "ARINC";
            case "ARINC Inserts" -> fileName = "ARINC Inserts";
            case "Backplane Connector Accessories" -> fileName = "Backplane Connector Accessories";
            case "Backplane Connector Contacts" -> fileName = "Backplane Connector Contacts";
            case "Backplane Connector Housings" -> fileName = "Backplane Connector Housings";
            case "DIN 41612" -> fileName = "DIN 41612";
            case "Hard Metric, Standard" -> fileName = "Hard Metric, Standard";
            case "Backplane Connectors Specialized" -> fileName = "Backplane Connectors Specialized";
            case "Banana and Tip Connector Accessories" -> fileName = "Banana and Tip Connector Accessories";
            case "Banana and Tip Connector Adapters" -> fileName = "Banana and Tip Connector Adapters";
            case "Binding Posts" -> fileName = "Binding Posts";
            case "Jacks, Plugs" -> fileName = "Jacks, Plugs";
            case "Audio Connectors" -> fileName = "Audio Connectors";
            case "Barrel Connector Accessories" -> fileName = "Barrel Connector Accessories";
            case "Barrel Connector Adapters" -> fileName = "Barrel Connector Adapters";
            case "Power Connectors" -> fileName = "Power Connectors";
            case "Between Series Adapters" -> fileName = "Between Series Adapters";
            case "Blade Type Power Connector Accessories" -> fileName = "Blade Type Power Connector Accessories";
            case "Blade Type Power Connector Assemblies" -> fileName = "Blade Type Power Connector Assemblies";
            case "Blade Type Power Connector Contacts" -> fileName = "Blade Type Power Connector Contacts";
            case "Blade Type Power Connector Housings" -> fileName = "Blade Type Power Connector Housings";
            case "Card Edge Connector Accessories" -> fileName = "Card Edge Connector Accessories";
            case "Card Edge Connector Adapters" -> fileName = "Card Edge Connector Adapters";
            case "Card Edge Connector Contacts" -> fileName = "Card Edge Connector Contacts";
            case "Card Edge Connector Housings" -> fileName = "Card Edge Connector Housings";
            case "Edgeboard Connectors" -> fileName = "Edgeboard Connectors";
            case "Backshells and Cable Clamps" -> fileName = "Backshells and Cable Clamps";
            case "Circular Connector Accessories" -> fileName = "Circular Connector Accessories";
            case "Circular Connector Adapters" -> fileName = "Circular Connector Adapters";
            case "Circular Connector Assemblies" -> fileName = "Circular Connector Assemblies";
            case "Circular Connector Contacts" -> fileName = "Circular Connector Contacts";
            case "Circular Connector Housings" -> fileName = "Circular Connector Housings";
            case "Coaxial Connector (RF) Accessories" -> fileName = "Coaxial Connector (RF) Accessories";
            case "Coaxial Connector (RF) Adapters" -> fileName = "Coaxial Connector (RF) Adapters";
            case "Coaxial Connector (RF) Assemblies" -> fileName = "Coaxial Connector (RF) Assemblies";
            case "Coaxial Connector (RF) Contacts" -> fileName = "Coaxial Connector (RF) Contacts";
            case "Coaxial Connector (RF) Terminators" -> fileName = "Coaxial Connector (RF) Terminators";
            case "Contacts, Spring Loaded (Pogo Pins), and Pressure" -> fileName = "Contacts, Spring Loaded (Pogo Pins), and Pressure";
            case "Leadframe" -> fileName = "Leadframe";
            case "Multi Purpose" -> fileName = "Multi Purpose";
            case "Centronics Connectors" -> fileName = "Centronics Connectors";
            case "D-Sub Connector Assemblies" -> fileName = "D-Sub Connector Assemblies";
            case "D-Sub, D-Shaped Connector Accessories" -> fileName = "D-Sub, D-Shaped Connector Accessories";
            case "D-Sub, D-Shaped Connector Adapters" -> fileName = "D-Sub, D-Shaped Connector Adapters";
            case "D-Sub, D-Shaped Connector Backshells, Hoods" -> fileName = "D-Sub, D-Shaped Connector Backshells, Hoods";
            case "D-Sub, D-Shaped Connector Contacts" -> fileName = "D-Sub, D-Shaped Connector Contacts";
            case "D-Sub, D-Shaped Connector Housings" -> fileName = "D-Sub, D-Shaped Connector Housings";
            case "D-Sub, D-Shaped Connector Jackscrews" -> fileName = "D-Sub, D-Shaped Connector Jackscrews";
            case "D-Sub, D-Shaped Connector Terminators" -> fileName = "D-Sub, D-Shaped Connector Terminators";
            case "FFC, FPC (Flat Flexible) Connector Accessories" -> fileName = "FFC, FPC (Flat Flexible) Connector Accessories";
            case "FFC, FPC (Flat Flexible) Connector Assemblies" -> fileName = "FFC, FPC (Flat Flexible) Connector Assemblies";
            case "FFC, FPC (Flat Flexible) Connector Contacts" -> fileName = "FFC, FPC (Flat Flexible) Connector Contacts";
            case "FFC, FPC (Flat Flexible) Connector Housings" -> fileName = "FFC, FPC (Flat Flexible) Connector Housings";
            case "Fiber Optic Connector Accessories" -> fileName = "Fiber Optic Connector Accessories";
            case "Fiber Optic Connector Adapters" -> fileName = "Fiber Optic Connector Adapters";
            case "Fiber Optic Connector Assemblies" -> fileName = "Fiber Optic Connector Assemblies";
            case "Fiber Optic Connector Housings" -> fileName = "Fiber Optic Connector Housings";
            case "Heavy Duty Connector Accessories" -> fileName = "Heavy Duty Connector Accessories";
            case "Heavy Duty Connector Assemblies" -> fileName = "Heavy Duty Connector Assemblies";
            case "Heavy Duty Connector Contacts" -> fileName = "Heavy Duty Connector Contacts";
            case "Heavy Duty Connector Frames" -> fileName = "Heavy Duty Connector Frames";
            case "Heavy Duty Connector Housings, Hoods, Bases" -> fileName = "Heavy Duty Connector Housings, Hoods, Bases";
            case "Heavy Duty Connector Inserts, Modules" -> fileName = "Heavy Duty Connector Inserts, Modules";
            case "Keystone Connector Accessories" -> fileName = "Keystone Connector Accessories";
            case "Keystone Faceplates, Frames" -> fileName = "Keystone Faceplates, Frames";
            case "Keystone Inserts" -> fileName = "Keystone Inserts";
            case "LGH Connectors" -> fileName = "LGH Connectors";
            case "Inline Module Sockets" -> fileName = "Inline Module Sockets";
            case "Memory Connector Accessories" -> fileName = "Memory Connector Accessories";
            case "PC Card Sockets" -> fileName = "PC Card Sockets";
            case "PC Cards - Adapters" -> fileName = "PC Cards - Adapters";
            case "Modular Connector Accessories" -> fileName = "Modular Connector Accessories";
            case "Modular Connector Adapters" -> fileName = "Modular Connector Adapters";
            case "Modular Connector Jacks" -> fileName = "Modular Connector Jacks";
            case "Modular Connector Jacks With Magnetics" -> fileName = "Modular Connector Jacks With Magnetics";
            case "Modular Connector Plug Housings" -> fileName = "Modular Connector Plug Housings";
            case "Modular Connector Plugs" -> fileName = "Modular Connector Plugs";
            case "Modular Connector Wiring Blocks" -> fileName = "Modular Connector Wiring Blocks";
            case "Modular Connector Wiring Blocks Accessories" -> fileName = "Modular Connector Wiring Blocks Accessories";
            case "Photovoltaic (Solar Panel) Connector Accessories" -> fileName = "Photovoltaic (Solar Panel) Connector Accessories";
            case "Photovoltaic (Solar Panel) Connector Assemblies" -> fileName = "Photovoltaic (Solar Panel) Connector Assemblies";
            case "Photovoltaic (Solar Panel) Connector Contacts" -> fileName = "Photovoltaic (Solar Panel) Connector Contacts";
            case "Pluggable Connector Accessories" -> fileName = "Pluggable Connector Accessories";
            case "Pluggable Connector Assemblies" -> fileName = "Pluggable Connector Assemblies";
            case "Arrays, Edge Type, Mezzanine (Board to Board)" -> fileName = "Arrays, Edge Type, Mezzanine (Board to Board)";
            case "Board In, Direct Wire to Board" -> fileName = "Board In, Direct Wire to Board";
            case "Board Spacers, Stackers (Board to Board)" -> fileName = "Board Spacers, Stackers (Board to Board)";
            case "Free Hanging, Panel Mount" -> fileName = "Free Hanging, Panel Mount";
            case "Headers, Male Pins" -> fileName = "Headers, Male Pins";
            case "Headers, Receptacles, Female Sockets" -> fileName = "Headers, Receptacles, Female Sockets";
            case "Headers, Specialty Pin" -> fileName = "Headers, Specialty Pin";
            case "Rectangular Connector Accessories" -> fileName = "Rectangular Connector Accessories";
            case "Rectangular Connector Adapters" -> fileName = "Rectangular Connector Adapters";
            case "Rectangular Connector Contacts" -> fileName = "Rectangular Connector Contacts";
            case "Rectangular Connector Housings" -> fileName = "Rectangular Connector Housings";
            case "Spring Loaded" -> fileName = "Spring Loaded";
            case "IC Sockets" -> fileName = "IC Sockets";
            case "Socket Accessories" -> fileName = "Socket Accessories";
            case "Socket Adapters" -> fileName = "Socket Adapters";
            case "Solid State Lighting Connector Accessories" -> fileName = "Solid State Lighting Connector Accessories";
            case "Solid State Lighting Connector Assemblies" -> fileName = "Solid State Lighting Connector Assemblies";
            case "Solid State Lighting Connector Contacts" -> fileName = "Solid State Lighting Connector Contacts";
            case "Barrier Blocks" -> fileName = "Barrier Blocks";
            case "Din Rail, Channel" -> fileName = "Din Rail, Channel";
            case "Headers, Plugs and Sockets" -> fileName = "Headers, Plugs and Sockets";
            case "Interface Modules" -> fileName = "Interface Modules";
            case "Panel Mount" -> fileName = "Panel Mount";
            case "Power Distribution" -> fileName = "Power Distribution";
            case "Terminal Blocks Specialized" -> fileName = "Terminal Blocks Specialized";
            case "Terminal Block Accessories" -> fileName = "Terminal Block Accessories";
            case "Terminal Block Adapters" -> fileName = "Terminal Block Adapters";
            case "Terminal Block Contacts" -> fileName = "Terminal Block Contacts";
            case "Wire to Board" -> fileName = "Wire to Board";
            case "Terminal Junction Systems" -> fileName = "Terminal Junction Systems";
            case "Terminal Strips and Turret Boards" -> fileName = "Terminal Strips and Turret Boards";
            case "Barrel, Bullet Connectors" -> fileName = "Barrel, Bullet Connectors";
            case "Foil Connectors" -> fileName = "Foil Connectors";
            case "Housings, Boots" -> fileName = "Housings, Boots";
            case "Knife Connectors" -> fileName = "Knife Connectors";
            case "Lugs" -> fileName = "Lugs";
            case "Magnetic Wire Connectors" -> fileName = "Magnetic Wire Connectors";
            case "PC Pin Receptacles, Socket Connectors" -> fileName = "PC Pin Receptacles, Socket Connectors";
            case "PC Pin, Single Post Connectors" -> fileName = "PC Pin, Single Post Connectors";
            case "Quick Connects, Quick Disconnect Connectors" -> fileName = "Quick Connects, Quick Disconnect Connectors";
            case "Ring Connectors" -> fileName = "Ring Connectors";
            case "Screw Connectors" -> fileName = "Screw Connectors";
            case "Solder Lug Connectors" -> fileName = "Solder Lug Connectors";
            case "Spade Connectors" -> fileName = "Spade Connectors";
            case "Specialized Connectors" -> fileName = "Specialized Connectors";
            case "Terminal Accessories" -> fileName = "Terminal Accessories";
            case "Terminal Adapters" -> fileName = "Terminal Adapters";
            case "Turret Connectors" -> fileName = "Turret Connectors";
            case "Wire Pin Connectors" -> fileName = "Wire Pin Connectors";
            case "Wire Splice Connectors" -> fileName = "Wire Splice Connectors";
            case "Wire to Board Connectors" -> fileName = "Wire to Board Connectors";
            case "USB, DVI, HDMI Connector Accessories" -> fileName = "USB, DVI, HDMI Connector Accessories";
            case "USB, DVI, HDMI Connector Adapters" -> fileName = "USB, DVI, HDMI Connector Adapters";
            case "USB, DVI, HDMI Connector Assemblies" -> fileName = "USB, DVI, HDMI Connector Assemblies";

            // Crystals, Oscillators, Resonators Categories
            case "Crystal, Oscillator, Resonator Accessories" -> fileName = "Crystal, Oscillator, Resonator Accessories";
            case "Crystals" -> fileName = "Crystals";
            case "Oscillators" -> fileName = "Oscillators";
            case "Pin Configurable/Selectable Oscillators" -> fileName = "Pin Configurable-Selectable Oscillators";
            case "Programmable Oscillators" -> fileName = "Programmable Oscillators";
            case "Resonators" -> fileName = "Resonators";
            case "Stand Alone Programmers" -> fileName = "Stand Alone Programmers";
            case "VCOs (Voltage Controlled Oscillators)" -> fileName = "VCOs (Voltage Controlled Oscillators)";

            // Inductors, Coils, Chokes Subcategories
            case "Adjustable Inductors" -> fileName = "Adjustable Inductors";
            case "Arrays, Signal Transformers" -> fileName = "Arrays, Signal Transformers";
            case "Inductors, Coils, Chokes Delay Lines" -> fileName = "Delay Lines";
            case "Fixed Inductors" -> fileName = "Fixed Inductors";
            case "Wireless Charging Coils" -> fileName = "Wireless Charging Coils";

            // Integrated Circuits (ICs) Subcategories
            case "Audio Special Purpose" -> fileName = "Audio Special Purpose";
            case "Application Specific Clock/Timing" -> fileName = "Application Specific Clock-Timing";
            case "Clock Buffers, Drivers" -> fileName = "Clock Buffers, Drivers";
            case "Clock Generators, PLLs, Frequency Synthesizers" -> fileName = "Clock Generators, PLLs, Frequency Synthesizers";
            case "Clock/Timing Delay Lines" -> fileName = "Clock-Timing Delay Lines";
            case "IC Batteries" -> fileName = "IC Batteries";
            case "Programmable Timers and Oscillators" -> fileName = "Programmable Timers and Oscillators";
            case "Real Time Clocks" -> fileName = "Real Time Clocks";
            case "ADCs/DACs - Special Purpose" -> fileName = "ADCs-DACs - Special Purpose";
            case "Analog Front End (AFE)" -> fileName = "Analog Front End (AFE)";
            case "Analog to Digital Converters (ADC)" -> fileName = "Analog to Digital Converters (ADC)";
            case "Digital Potentiometers" -> fileName = "Digital Potentiometers";
            case "Digital to Analog Converters (DAC)" -> fileName = "Digital to Analog Converters (DAC)";
            case "Touch Screen Controllers" -> fileName = "Touch Screen Controllers";
            case "Application Specific Microcontrollers" -> fileName = "Application Specific Microcontrollers";
            case "CPLDs (Complex Programmable Logic Devices)" -> fileName = "CPLDs (Complex Programmable Logic Devices)";
            case "DSP (Digital Signal Processors)" -> fileName = "DSP (Digital Signal Processors)";
            case "FPGAs (Field Programmable Gate Array)" -> fileName = "FPGAs (Field Programmable Gate Array)";
            case "FPGAs (Field Programmable Gate Array) with Microcontrollers" -> fileName = "FPGAs (Field Programmable Gate Array) with Microcontrollers";
            case "Microcontrollers" -> fileName = "Microcontrollers";
            case "Microcontrollers, Microprocessor, FPGA Modules" -> fileName = "Microcontrollers, Microprocessor, FPGA Modules";
            case "Microprocessors" -> fileName = "Microprocessors";
            case "PLDs (Programmable Logic Device)" -> fileName = "PLDs (Programmable Logic Device)";
            case "System On Chip (SoC)" -> fileName = "System On Chip (SoC)";
            case "Analog Switches - Special Purpose" -> fileName = "Analog Switches - Special Purpose";
            case "Analog Switches, Multiplexers, Demultiplexers" -> fileName = "Analog Switches, Multiplexers, Demultiplexers";
            case "CODECS" -> fileName = "CODECS";
            case "Interface Controllers" -> fileName = "Interface Controllers";
            case "Direct Digital Synthesis (DDS)" -> fileName = "Direct Digital Synthesis (DDS)";
            case "Drivers, Receivers, Transceivers" -> fileName = "Drivers, Receivers, Transceivers";
            case "Encoders, Decoders, Converters" -> fileName = "Encoders, Decoders, Converters";
            case "Filters - Active" -> fileName = "Filters - Active";
            case "I/O Expanders" -> fileName = "I-O Expanders";
            case "Modems - ICs and Modules" -> fileName = "Modems - ICs and Modules";
            case "Modules" -> fileName = "Modules";
            case "Sensor and Detector Interfaces" -> fileName = "Sensor and Detector Interfaces";
            case "Sensor, Capacitive Touch" -> fileName = "Sensor, Capacitive Touch";
            case "Serializers, Deserializers" -> fileName = "Serializers, Deserializers";
            case "Signal Buffers, Repeaters, Splitters" -> fileName = "Signal Buffers, Repeaters, Splitters";
            case "Signal Terminators" -> fileName = "Signal Terminators";
            case "Interface Specialized" -> fileName = "Interface Specialized";
            case "Telecom" -> fileName = "Telecom";
            case "UARTs (Universal Asynchronous Receiver Transmitter)" -> fileName = "UARTs (Universal Asynchronous Receiver Transmitter)";
            case "Voice Record and Playback" -> fileName = "Voice Record and Playback";
            case "Amplifiers" -> fileName = "Amplifiers";
            case "Analog Multipliers, Dividers" -> fileName = "Analog Multipliers, Dividers";
            case "Comparators" -> fileName = "Comparators";
            case "Video Processing" -> fileName = "Video Processing";
            case "Buffers, Drivers, Receivers, Transceivers" -> fileName = "Buffers, Drivers, Receivers, Transceivers";
            case "Counters, Dividers" -> fileName = "Counters, Dividers";
            case "FIFOs Memory" -> fileName = "FIFOs Memory";
            case "Flip Flops" -> fileName = "Flip Flops";
            case "Gates and Inverters" -> fileName = "Gates and Inverters";
            case "Gates and Inverters - Multi-Function, Configurable" -> fileName = "Gates and Inverters - Multi-Function, Configurable";
            case "Latches" -> fileName = "Latches";
            case "Multivibrators" -> fileName = "Multivibrators";
            case "Parity Generators and Checkers" -> fileName = "Parity Generators and Checkers";
            case "Shift Registers" -> fileName = "Shift Registers";
            case "Signal Switches, Multiplexers, Decoders" -> fileName = "Signal Switches, Multiplexers, Decoders";
            case "Specialty Logic" -> fileName = "Specialty Logic";
            case "Translators, Level Shifters" -> fileName = "Translators, Level Shifters";
            case "Universal Bus Functions" -> fileName = "Universal Bus Functions";
            case "Batteries" -> fileName = "Batteries";
            case "Configuration PROMs for FPGAs" -> fileName = "Configuration PROMs for FPGAs";
            case "Memory Controllers" -> fileName = "Memory Controllers";
            case "Memory" -> fileName = "Memory";
            case "AC DC Converters, Offline Switches" -> fileName = "AC DC Converters, Offline Switches";
            case "Battery Chargers" -> fileName = "Battery Chargers";
            case "Battery Management" -> fileName = "Battery Management";
            case "Current Regulation/Management" -> fileName = "Current Regulation-Management";
            case "DC DC Switching Controllers" -> fileName = "DC DC Switching Controllers";
            case "Display Drivers" -> fileName = "Display Drivers";
            case "Energy Metering" -> fileName = "Energy Metering";
            case "Full Half-Bridge (H Bridge) Drivers" -> fileName = "Full Half-Bridge (H Bridge) Drivers";
            case "Gate Drivers" -> fileName = "Gate Drivers";
            case "Hot Swap Controllers" -> fileName = "Hot Swap Controllers";
            case "Laser Drivers" -> fileName = "Laser Drivers";
            case "LED Drivers" -> fileName = "LED Drivers";
            case "Lighting, Ballast Controllers" -> fileName = "Lighting, Ballast Controllers";
            case "Motor Drivers, Controllers" -> fileName = "Motor Drivers, Controllers";
            case "OR Controllers, Ideal Diodes" -> fileName = "OR Controllers, Ideal Diodes";
            case "PFC (Power Factor Correction)" -> fileName = "PFC (Power Factor Correction)";
            case "Power Distribution Switches, Load Drivers" -> fileName = "Power Distribution Switches, Load Drivers";
            case "Power Management - Specialized" -> fileName = "Power Management - Specialized";
            case "Power Over Ethernet (PoE) Controllers" -> fileName = "Power Over Ethernet (PoE) Controllers";
            case "Power Supply Controllers, Monitors" -> fileName = "Power Supply Controllers, Monitors";
            case "RMS to DC Converters" -> fileName = "RMS to DC Converters";
            case "Special Purpose Regulators" -> fileName = "Special Purpose Regulators";
            case "Supervisors" -> fileName = "Supervisors";
            case "Thermal Management" -> fileName = "Thermal Management";
            case "V/F and F/V Converters" -> fileName = "V-F and F-V Converters";
            case "Voltage Reference" -> fileName = "Voltage Reference";
            case "Voltage Regulators - DC DC Switching Regulators" -> fileName = "Voltage Regulators - DC DC Switching Regulators";
            case "Voltage Regulators - Linear + Switching" -> fileName = "Voltage Regulators - Linear + Switching";
            case "Voltage Regulators - Linear Regulator Controllers" -> fileName = "Voltage Regulators - Linear Regulator Controllers";
            case "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators" -> fileName = "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators";
            case "Specialized ICs" -> fileName = "Specialized ICs";

            // Potentiometers, Variable Resistors Subcategories
            case "Potentiometers, Variable Resistors Accessories" -> fileName = "Potentiometers, Variable Resistors Accessories";
            case "Adjustable Power Resistor" -> fileName = "Adjustable Power Resistor";
            case "Joystick Potentiometers" -> fileName = "Joystick Potentiometers";
            case "Rotary Potentiometers, Rheostats" -> fileName = "Rotary Potentiometers, Rheostats";
            case "Scale Dials" -> fileName = "Scale Dials";
            case "Slide Potentiometers" -> fileName = "Slide Potentiometers";
            case "Thumbwheel Potentiometers" -> fileName = "Thumbwheel Potentiometers";
            case "Trimmer Potentiometers" -> fileName = "Trimmer Potentiometers";
            case "Value Display Potentiometers" -> fileName = "Value Display Potentiometers";

            // Relays Subcategories
            case "Relays Accessories" -> fileName = "Relays Accessories";
            case "Automotive Relays" -> fileName = "Automotive Relays";
            case "Contactors (Electromechanical)" -> fileName = "Contactors (Electromechanical)";
            case "Contactors (Solid State)" -> fileName = "Contactors (Solid State)";
            case "High Frequency (RF) Relays" -> fileName = "High Frequency (RF) Relays";
            case "I/O Relay Module Racks" -> fileName = "IO Relay Module Racks";
            case "I/O Relay Modules" -> fileName = "IO Relay Modules";
            case "Power Relays, Over 2 Amps" -> fileName = "Power Relays, Over 2 Amps";
            case "Reed Relays" -> fileName = "Reed Relays";
            case "Relay Sockets" -> fileName = "Relay Sockets";
            case "Safety Relays" -> fileName = "Safety Relays";
            case "Signal Relays, Up to 2 Amps" -> fileName = "Signal Relays, Up to 2 Amps";
            case "Solid State Relays" -> fileName = "Solid State Relays";

            // Resistors Subcategories
            case "Resistors Accessories" -> fileName = "Resistors Accessories";
            case "Chassis Mount Resistors" -> fileName = "Chassis Mount Resistors";
            case "Chip Resistor - Surface Mount" -> fileName = "Chip Resistor - Surface Mount";
            case "Precision Trimmed Resistors" -> fileName = "Precision Trimmed Resistors";
            case "Resistor Networks, Arrays" -> fileName = "Resistor Networks, Arrays";
            case "Specialized Resistors" -> fileName = "Specialized Resistors";
            case "Through Hole Resistors" -> fileName = "Through Hole Resistors";

            // Switches Subcategories
            case "Switches Accessories" -> fileName = "Switches Accessories";
            case "Accessories - Boots, Seals" -> fileName = "Accessories - Boots, Seals";
            case "Accessories - Caps" -> fileName = "Accessories - Caps";
            case "Cable Pull Switches" -> fileName = "Cable Pull Switches";
            case "Configurable Switch Bodies" -> fileName = "Configurable Switch Bodies";
            case "Configurable Switch Contact Blocks" -> fileName = "Configurable Switch Contact Blocks";
            case "Configurable Switch Illumination Sources" -> fileName = "Configurable Switch Illumination Sources";
            case "Configurable Switch Lens" -> fileName = "Configurable Switch Lens";
            case "DIP Switches" -> fileName = "DIP Switches";
            case "Disconnect Switch Components" -> fileName = "Disconnect Switch Components";
            case "Emergency Stop (E-Stop) Switches" -> fileName = "Emergency Stop (E-Stop) Switches";
            case "Interlock Switches" -> fileName = "Interlock Switches";
            case "Keylock Switches" -> fileName = "Keylock Switches";
            case "Keypad Switches" -> fileName = "Keypad Switches";
            case "Limit Switches" -> fileName = "Limit Switches";
            case "Magnetic, Reed Switches" -> fileName = "Magnetic, Reed Switches";
            case "Navigation Switches, Joystick" -> fileName = "Navigation Switches, Joystick";
            case "Programmable Display Switches" -> fileName = "Programmable Display Switches";
            case "Pushbutton Switches" -> fileName = "Pushbutton Switches";
            case "Pushbutton Switches - Hall Effect" -> fileName = "Pushbutton Switches - Hall Effect";
            case "Rocker Switches" -> fileName = "Rocker Switches";
            case "Rotary Switches" -> fileName = "Rotary Switches";
            case "Selector Switches" -> fileName = "Selector Switches";
            case "Slide Switches" -> fileName = "Slide Switches";
            case "Tactile Switches" -> fileName = "Tactile Switches";
            case "Thumbwheel Switches" -> fileName = "Thumbwheel Switches";
            case "Toggle Switches" -> fileName = "Toggle Switches";
            default -> {
                System.out.println("Keyword selected for search does not match expected category name. Default file name will be provided.");
                System.out.println("File named: Default.json");
                fileName = "Default";
            }
        }
        return fileName;
    }

    /**
     * Selects the appropriate file folder based on the provided keyword.
     *
     * @param keyword The keyword used to determine the file folder.
     * @return The selected file folder.
     */
    private static String selectingFileFolder(String keyword) {

        String fileFolder = switch (keyword) {
            // Capacitors subcategories
            case "Capacitor Accessories", "Aluminum - Polymer Capacitors", "Aluminum Electrolytic Capacitors", "Capacitor Networks, Arrays", "Ceramic Capacitors",
                    "Electric Double Layer Capacitors (EDLC), Supercapacitors", "Film Capacitors", "Mica and PTFE Capacitors", "Niobium Oxide Capacitors", "Silicon Capacitors",
                    "Tantalum - Polymer Capacitors", "Tantalum Capacitors", "Thin Film Capacitors", "Trimmers, Variable Capacitors" -> "Capacitors/";

            // Connectors, Interconnects subcategories
            case "Plugs and Receptacles", "Power Entry Connector Accessories", "Power Entry Modules (PEM)", "ARINC", "ARINC Inserts",
                    "Backplane Connector Accessories", "Backplane Connector Contacts", "Backplane Connector Housings", "DIN 41612", "Hard Metric, Standard",
                    "Backplane Connectors Specialized", "Banana and Tip Connector Accessories", "Banana and Tip Connector Adapters", "Binding Posts", "Jacks, Plugs",
                    "Audio Connectors", "Barrel Connector Accessories", "Barrel Connector Adapters", "Power Connectors", "Between Series Adapters",
                    "Blade Type Power Connector Accessories", "Blade Type Power Connector Assemblies", "Blade Type Power Connector Contacts", "Blade Type Power Connector Housings", "Card Edge Connector Accessories",
                    "Card Edge Connector Adapters", "Card Edge Connector Contacts", "Card Edge Connector Housings", "Edgeboard Connectors",
                    "Backshells and Cable Clamps", "Circular Connector Accessories", "Circular Connector Adapters", "Circular Connector Assemblies", "Circular Connector Contacts",
                    "Circular Connector Housings", "Coaxial Connector (RF) Accessories", "Coaxial Connector (RF) Adapters", "Coaxial Connector (RF) Assemblies", "Coaxial Connector (RF) Contacts",
                    "Coaxial Connector (RF) Terminators", "Contacts, Spring Loaded (Pogo Pins), and Pressure", "Leadframe", "Multi Purpose", "Centronics Connectors",
                    "D-Sub Connector Assemblies", "D-Sub, D-Shaped Connector Accessories", "D-Sub, D-Shaped Connector Adapters", "D-Sub, D-Shaped Connector Backshells, Hoods",
                    "D-Sub, D-Shaped Connector Contacts", "D-Sub, D-Shaped Connector Housings", "D-Sub, D-Shaped Connector Jackscrews", "D-Sub, D-Shaped Connector Terminators", "FFC, FPC (Flat Flexible) Connector Accessories",
                    "FFC, FPC (Flat Flexible) Connector Assemblies", "FFC, FPC (Flat Flexible) Connector Contacts", "FFC, FPC (Flat Flexible) Connector Housings", "Fiber Optic Connector Accessories",
                    "Fiber Optic Connector Adapters", "Fiber Optic Connector Assemblies", "Fiber Optic Connector Housings", "Heavy Duty Connector Accessories", "Heavy Duty Connector Assemblies",
                    "Heavy Duty Connector Contacts", "Heavy Duty Connector Frames", "Heavy Duty Connector Housings, Hoods, Bases", "Heavy Duty Connector Inserts, Modules", "Keystone Connector Accessories",
                    "Keystone Faceplates, Frames", "Keystone Inserts", "LGH Connectors", "Inline Module Sockets", "Memory Connector Accessories", "PC Card Sockets",
                    "PC Cards - Adapters", "Modular Connector Accessories", "Modular Connector Adapters", "Modular Connector Jacks", "Modular Connector Jacks With Magnetics",
                    "Modular Connector Plug Housings", "Modular Connector Plugs", "Modular Connector Wiring Blocks", "Modular Connector Wiring Blocks Accessories", "Photovoltaic (Solar Panel) Connector Accessories",
                    "Photovoltaic (Solar Panel) Connector Assemblies", "Photovoltaic (Solar Panel) Connector Contacts", "Pluggable Connector Accessories", "Pluggable Connector Assemblies", "Arrays, Edge Type, Mezzanine (Board to Board)",
                    "Board In, Direct Wire to Board", "Board Spacers, Stackers (Board to Board)", "Free Hanging, Panel Mount", "Headers, Male Pins", "Headers, Receptacles, Female Sockets",
                    "Headers, Specialty Pin", "Rectangular Connector Accessories", "Rectangular Connector Adapters", "Rectangular Connector Contacts", "Rectangular Connector Housings", "Spring Loaded",
                    "IC Sockets", "Socket Accessories", "Socket Adapters", "Solid State Lighting Connector Accessories", "Solid State Lighting Connector Assemblies",
                    "Solid State Lighting Connector Contacts", "Barrier Blocks", "Din Rail, Channel", "Headers, Plugs and Sockets", "Interface Modules", "Panel Mount",
                    "Power Distribution", "Terminal Blocks Specialized", "Terminal Block Accessories", "Terminal Block Adapters", "Terminal Block Contacts", "Wire to Board",
                    "Terminal Junction Systems", "Terminal Strips and Turret Boards", "Barrel, Bullet Connectors", "Foil Connectors", "Housings, Boots", "Knife Connectors", "Lugs",
                    "Magnetic Wire Connectors", "PC Pin Receptacles, Socket Connectors", "PC Pin, Single Post Connectors", "Quick Connects, Quick Disconnect Connectors", "Ring Connectors",
                    "Screw Connectors", "Solder Lug Connectors", "Spade Connectors", "Specialized Connectors", "Terminal Accessories", "Terminal Adapters", "Turret Connectors",
                    "Wire Pin Connectors", "Wire Splice Connectors", "Wire to Board Connectors", "USB, DVI, HDMI Connector Accessories", "USB, DVI, HDMI Connector Adapters",
                    "USB, DVI, HDMI Connector Assemblies" -> "Connectors, Interconnects/";

            // Crystal, Oscillators, Resonators subcategories
            case
                    "Crystal, Oscillator, Resonator Accessories", "Crystals", "Oscillators", "Pin Configurable-Selectable Oscillators", "Programmable Oscillators",
                            "Resonators", "Stand Alone Programmers", "VCOs (Voltage Controlled Oscillators)" -> "Crystals, Oscillators, Resonators/";

            // Inductors, Coil, Chokes subcategories

            case "Adjustable Inductors", "Arrays, Signal Transformers", "Delay Lines", "Fixed Inductors", "Wireless Charging Coils" -> "Inductors, Coils, Chokes/";

            // Integrated Circuits (ICs) subcategories
            case "Audio Special Purpose", "Application Specific Clock-Timing", "Clock Buffers, Drivers", "Clock Generators, PLLs, Frequency Synthesizers", "Clock-Timing Delay Lines",
                    "IC Batteries", "Programmable Timers and Oscillators", "Real Time Clocks", "ADCs-DACs - Special Purpose", "Analog Front End (AFE)",
                    "Analog to Digital Converters (ADC)", "Digital Potentiometers", "Digital to Analog Converters (DAC)", "Touch Screen Controllers",
                    "Application Specific Microcontrollers", "CPLDs (Complex Programmable Logic Devices)", "DSP (Digital Signal Processors)", "FPGAs (Field Programmable Gate Array)", "FPGAs (Field Programmable Gate Array) with Microcontrollers",
                    "Microcontrollers", "Microcontrollers, Microprocessor, FPGA Modules", "Microprocessors", "PLDs (Programmable Logic Device)", "System On Chip (SoC)",
                    "Analog Switches - Special Purpose", "Analog Switches, Multiplexers, Demultiplexers", "CODECS", "Interface Controllers", "Direct Digital Synthesis (DDS)",
                    "Drivers, Receivers, Transceivers", "Encoders, Decoders, Converters", "Filters - Active", "IO Expanders",
                    "Modems - ICs and Modules", "Modules", "Sensor and Detector Interfaces", "Sensor, Capacitive Touch", "Serializers, Deserializers",
                    "Signal Buffers, Repeaters, Splitters", "Signal Terminators", "Interface Specialized", "Telecom", "UARTs (Universal Asynchronous Receiver Transmitter)",
                    "Voice Record and Playback", "Amplifiers", "Analog Multipliers, Dividers", "Comparators", "Video Processing",
                    "Buffers, Drivers, Receivers, Transceivers", "Counters, Dividers", "FIFOs Memory", "Flip Flops", "Gates and Inverters",
                    "Gates and Inverters - Multi-Function, Configurable", "Latches", "Multivibrators", "Parity Generators and Checkers", "Shift Registers",
                    "Signal Switches, Multiplexers, Decoders", "Specialty Logic", "Translators, Level Shifters", "Universal Bus Functions", "Batteries",
                    "Configuration PROMs for FPGAs", "Memory Controllers", "Memory", "AC DC Converters, Offline Switches", "Battery Chargers",
                    "Battery Management", "Current Regulation-Management", "DC DC Switching Controllers", "Display Drivers",
                    "Energy Metering", "Full Half-Bridge (H Bridge) Drivers", "Gate Drivers", "Hot Swap Controllers", "Laser Drivers",
                    "LED Drivers", "Lighting, Ballast Controllers", "Motor Drivers, Controllers", "OR Controllers, Ideal Diodes", "PFC (Power Factor Correction)",
                    "Power Distribution Switches, Load Drivers", "Power Management - Specialized", "Power Over Ethernet (PoE) Controllers", "Power Supply Controllers, Monitors", "RMS to DC Converters",
                    "Special Purpose Regulators", "Supervisors", "Thermal Management", "V-F and F-V Converters", "Voltage Reference",
                    "Voltage Regulators - DC DC Switching Regulators", "Voltage Regulators - Linear + Switching",
                    "Voltage Regulators - Linear Regulator Controllers", "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators",
                    "Specialized ICs" -> "Integrated Circuits (ICs)/";

            // Potentiometers, Variable Resistors subcategories
            case
                    "Potentiometers, Variable Resistors Accessories", "Adjustable Power Resistor", "Joystick Potentiometers", "Rotary Potentiometers, Rheostats", "Scale Dials",
                            "Slide Potentiometers", "Thumbwheel Potentiometers", "Trimmer Potentiometers", "Value Display Potentiometers" -> "Potentiometers, Variable Resistors/";

            // Relays subcategories
            case
                    "Relays Accessories", "Automotive Relays", "Contactors (Electromechanical)", "Contactors (Solid State)", "High Frequency (RF) Relays",
                            "IO Relay Module Racks", "IO Relay Modules", "Power Relays, Over 2 Amps", "Reed Relays", "Relay Sockets",
                            "Safety Relays", "Signal Relays, Up to 2 Amps", "Solid State Relays" -> "Relays/";

            // Resistor subcategories
            case "Resistors Accessories", "Chassis Mount Resistors", "Chip Resistor - Surface Mount", "Precision Trimmed Resistors", "Resistor Networks, Arrays",
                    "Specialized Resistors", "Through Hole Resistors" -> "Resistors/";

            // Switches subcategories
            case "Switches Accessories", "Accessories - Boots, Seals", "Accessories - Caps", "Cable Pull Switches", "Configurable Switch Bodies",
                    "Configurable Switch Contact Blocks", "Configurable Switch Illumination Sources", "Configurable Switch Lens", "DIP Switches", "Disconnect Switch Components",
                    "Emergency Stop (E-Stop) Switches", "Interlock Switches", "Keylock Switches", "Keypad Switches", "Limit Switches",
                    "Magnetic, Reed Switches", "Navigation Switches, Joystick", "Programmable Display Switches", "Pushbutton Switches", "Pushbutton Switches - Hall Effect",
                    "Rocker Switches", "Rotary Switches", "Selector Switches", "Slide Switches", "Tactile Switches",
                    "Thumbwheel Switches", "Toggle Switches" -> "Switches/";
            default -> "";
        };
        return fileFolder;
    }

    /**
     * Checks the existence of a file and performs actions accordingly.
     * If the file exists, it updates the offset and product index.
     * If the file does not exist, it creates a new file using a template.
     *
     * @param fileName   The name of the file to be checked.
     * @param fileFolder The folder where the file is located.
     * @param offset     The current offset value.
     * @param keyword    The keyword for the file content.
     * @return An array containing file-related information: [filePath, offset, productIndex].
     */
    private static String[] checkingFile(String fileName, String fileFolder, int offset, String keyword) {

        int productIndex;

        // index 0 is filePath
        // index 1 is offset
        // index 2 is productIndex
        String[] tempArray = new String[3];

        String filePath = "Postman Exports/" + fileFolder + fileName + ".json";
        tempArray[0] = filePath;
        File fileCheck = new File(filePath);

        try {
            if (fileCheck.exists()) {
                System.out.println(fileName + " file exists");
                // Locating the last response body inputted and get the offset value
                productIndex = arrayCount(filePath) + 1;
                tempArray[2] = Integer.toString(productIndex);
                // update the offset value for new entries
                offset = (productIndex * 50) - 50;
                tempArray[1] = Integer.toString(offset);
            }
            else {
                // Insert template into the file
                System.out.println("File does not exist. Creating new file.");
                insertTemplate(filePath);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return tempArray;
    }

    /**
     * Resets the Client List by replacing "O" with "X" for all lines if all lines end with "O".
     * Otherwise, prints a message indicating open Client IDs and Client Secrets.
     */
    public static void clientListUsedUp() {
        // Define the file name
        String fileName = "ClientList.txt";

        try {
            // Open the file for reading
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // Create a StringBuilder to store modified lines
            StringBuilder modifiedContent = new StringBuilder();

            // Read each line from the file
            String line;
            boolean allEndWithO = true;
            while ((line = reader.readLine()) != null) {
                // Check if the last character of the line is "O"
                if (!line.endsWith("O")) {
                    allEndWithO = false;
                    break;
                }
                // Append the modified line (replace "O" with "X")
                modifiedContent.append(line.substring(0, line.length() - 1)).append("X").append("\n");
            }

            // Close the reader
            reader.close();

            // If all lines end with "O", update the file
            if (allEndWithO) {
                // Open the file for writing
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

                // Write the modified content to the file
                writer.write(modifiedContent.toString());
                System.out.println("Client List has been reset.");

                // Close the writer
                writer.close();
            } else {
                System.out.println("Client List still has open Client ID and Client Secret.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Checks if each line in the specified file ends with "O".
     *
     * @param filename The name of the file to be checked.
     * @return True if all lines end with "O", otherwise false.
     */
    public static boolean categoryListComplete(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Check if the line ends with "O"
                if (!line.endsWith("O")) {
                    return false; // If any line doesn't end with "O", return false
                }
            }
            // If all lines end with "O", return true
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false in case of any exception
        }
    }

    /**
     * Counts the number of elements in a JSON array stored in a file.
     *
     * @param filePath The path of the file containing the JSON array.
     * @return The number of elements in the JSON array.
     * @throws IOException    If an I/O error occurs.
     * @throws ParseException If there is an error parsing the JSON content.
     */
    public static int arrayCount(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray innerArray;
        Object obj = parser.parse(new FileReader(filePath));
        org.json.simple.JSONObject jsonObject = (JSONObject) obj;
        innerArray = (JSONArray) jsonObject.get("Products");

        return innerArray.size();
    }

    /**
     * Inserts an array of lines at the end of a file.
     *
     * @param filePath The path to the file.
     * @param body     The array of lines to be inserted.
     * @throws IOException If an I/O error occurs.
     */
    private static void insertArrayLines(String filePath, ArrayList<String> body) throws IOException {
        // Reading existing content from the file
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        reader.close();

        // Append the lines from the body ArrayList to the end of the content
        for (String lineToAdd : body) {
            content.append(lineToAdd).append(System.lineSeparator());
        }

        // Writing the modified content back to the file
        try (FileWriter writer = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(content.toString());
        }
    }

    /**
     * Inserts a new line into a file at the second to last position.
     *
     * @param filePath The path to the file.
     * @param newLine  The new line to be inserted.
     * @throws IOException If an I/O error occurs.
     */
    private static void insertNewLines(String filePath, String newLine) throws IOException {
        // Reading existing content from the file
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        reader.close();

        // Insert the new line into the second to last position
        int totalLines = content.toString().split(System.lineSeparator()).length;
        int insertLineNumber = Math.max(totalLines - 1, 0); // Ensure it's at least 0

        // Insert the new line at the specified line number
        content.insert(getPosition(content.toString(), insertLineNumber), newLine + System.lineSeparator());

        // Writing the modified content back to the file
        try (FileWriter writer = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(content.toString());
        }
    }

    /**
     * Calculates the position in the content string to insert a new line at a given line number.
     *
     * @param content    The content string in which the position is to be calculated.
     * @param lineNumber The line number at which the new line should be inserted.
     * @return The position in the content string to insert the new line.
     */
    private static int getPosition(String content, int lineNumber) {
        // Calculate the position to insert the new line
        // Initialize the position to 0
        int position = 0;

        // Iterate through the content to find the position of the line separator for the specified line number
        for (int i = 0; i < lineNumber; i++) {
            // Find the index of the line separator in the content starting from the current position
            position = content.indexOf(System.lineSeparator(), position) + 1;

            // If the line separator is not found, break out of the loop
            if (position == 0) {
                break; // Break if the line separator is not found
            }
        }
        // Return the calculated position
        return position;
    }

    /**
     * Inserts a template into a file.
     *
     * @param filePath The path to the file.
     * @throws IOException If an I/O error occurs.
     */
    private static void insertTemplate(String filePath) throws IOException {
        // Inserting template
        String template = "{\"Products\":[\n\n] }";
        FileWriter writer = new FileWriter(filePath);
        writer.write(template);
        writer.close();
    }

    /**
     * Obtains an access token from an API using client credentials.
     *
     * @param clientId     The client ID.
     * @param clientSecret The client secret.
     * @return The access token.
     * @throws Exception If an error occurs while obtaining the access token.
     */
    private static String getAccessToken(String clientId, String clientSecret) throws Exception {
        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create HTTP request to obtain access token
        HttpPost tokenRequest = new HttpPost(ACCESS_TOKEN_URL);
        tokenRequest.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

        // Set request parameters (client credentials grant type)
        String requestBody = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
        tokenRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_FORM_URLENCODED));

        // Execute the request
        HttpResponse tokenResponse = httpClient.execute(tokenRequest);

        // Check the response status
        int statusCode = tokenResponse.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Token request failed with status code: " + statusCode);
        }

        // Read and return the access token from the response body
        HttpEntity tokenEntity = tokenResponse.getEntity();
        String accessToken = EntityUtils.toString(tokenEntity);

        // Close the HTTP client
        httpClient.close();

        return accessToken;
    }

    /**
     * Sends a POST request to an API.
     *
     * @param apiUrl     The API URL.
     * @param accessToken The access token.
     * @param offset      The offset.
     * @param limit       The limit.
     * @param keyword     The keyword.
     * @param clientID    The client ID.
     * @return The response body.
     * @throws Exception If an error occurs while sending the request.
     */
    private static String sendPostRequest(String apiUrl, String accessToken, int offset, int limit, String keyword, String clientID) throws Exception {
        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create HTTP request to API endpoint
        HttpPost apiRequest = new HttpPost(apiUrl);
        apiRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        apiRequest.addHeader("X-DIGIKEY-Client-Id", clientID);

        // Set request body with current offset and limit
        String requestBody = "{\"Keywords\": \""+ keyword + "\", \"limit\": " + limit + ", \"offset\":" + offset + "}";
        apiRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

        // Execute the request
        HttpResponse apiResponse = httpClient.execute(apiRequest);
        HttpEntity apiEntity = apiResponse.getEntity();
        String responseBody = EntityUtils.toString(apiEntity);

        // Close the HTTP client
        httpClient.close();

        return responseBody;
    }

    /**
     * The main method that orchestrates the process of retrieving data from an API based on provided keywords.
     *
     * <p>This method controls the flow of data retrieval, including handling file operations,
     * API requests, and error conditions.</p>
     *
     */
    public static void main(String[] args) {
        try {
            /*
                GUI team can create a window with a drop-down or a write-in opinion for the
                client to select which keyword to pull.
                Action listener?
             */

            // Checking if CategoriesList.txt is complete
            if (categoryListComplete(CATEGORYLIST)) {
                System.out.println("CategoriesList.txt file is complete.");
                System.out.println("Reset CategoriesList.txt file to recollect data.");
                System.out.println("Ending program.");
                System.exit(0); // Terminate the program with status code 0 (indicating successful termination)
            }

            // Checking if client list is exhausted, method below resets the "ClientList.txt" file
            // else print out status and continue to the next code
            clientListUsedUp();

            String[] clientKey = ClientScanner.findFreeClient(CLIENTLIST);
            // OAuth 2.0 client credentials
            String CLIENT_ID = clientKey[0];
            String CLIENT_SECRET = clientKey[1];
            System.out.println("Client ID and Secret:" + Arrays.toString(clientKey));

            // CategoriesCheckList reads CategoriesList.txt for the next category to pull
            String KEYWORD = CategoriesCheckList.findCategory(CATEGORYLIST);
            System.out.println("Pulling from subcategory: " + KEYWORD);

            // Assigning file folder with KEYWORD match
            String fileFolder = selectingFileFolder(KEYWORD);

            String fileName;

            /*
                - Check the folder to see if fileName exists
                - If fileName exist, count how many files with KEYWORD as prefix
                - If there is only one, have the fileName unchanged
                - If there are a # of files, then concat " #" to fileName
                - That fileName will be used
             */

            // offset is used to keep track of placement when get API response
            // limit of rows per response is 50
            int offset = 0;
            int limit = 50;

            int filePrefixCount = 1;
            int directoryFileCount = DirectoryFiler.fileCounter("Postman Exports/" + fileFolder, KEYWORD);

            if (directoryFileCount > 1) {
                filePrefixCount = DirectoryFiler.fileCounter("Postman Exports/" + fileFolder, KEYWORD);
                fileName = selectingFileName(KEYWORD) + " " + filePrefixCount;

                // Reading existing content from the file
                BufferedReader reader = new BufferedReader(new FileReader("Postman Exports/" + fileFolder + "/" + fileName + ".json"));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
                reader.close();

                // Insert the new line into the second to last position
                int totalLines = content.toString().split(System.lineSeparator()).length;

                // insertLineNumber will contain the latest numbered file item count
                // It is contained since it is not a complete file with 25000 items
                // It will be added to the offset below
                int insertLineNumber = Math.max((totalLines - 1) * 50, 0); // Ensure it's at least 0

                offset = (filePrefixCount - 1) * 25000 + insertLineNumber;

            }
            else {
                // Assigning file name with KEYWORD match
                fileName = selectingFileName(KEYWORD);
            }

            // Assigning to the correct parent category
            int productIndex;
            int apiCount = 0;
            boolean offsetLoopAdjust = false;

            ArrayList<String> responseCollector = new ArrayList<>();

            do {
                String filePath;

                // Assigning file Path after checking file status

                String[] returnedArray = checkingFile(fileName, fileFolder, offset, KEYWORD);
                filePath = returnedArray[0];
                if (returnedArray[1] == null && filePrefixCount != 0) {
                    offset = 0;
                }

                if (returnedArray[2] == null) {
                    productIndex = 1;
                }
                else {
                    productIndex = Integer.parseInt(returnedArray[2]);
                }

                // Step 1: Obtain OAuth 2.0 access token
                clientListUsedUp();
                String accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);
            /*
                The accessToken above provides a dictionary in the following format:
                Access Token:
                            {
                            "access_token":"MVOSd7RVyfid6zgiB9A7PNIUPxYL",
                            "expires_in":599,
                            "token_type":"Bearer"
                            }
             */
                // The variable below extracts the access_token value
                String newAccessToken = accessToken.substring(43, 71);

                while (true) {
                    // offsetLoopAdjust is used to loop out of the comparison using MODULE against the offset.
                    // With the offsetLoopAdjust, we make sure that the program is not stuck on the offset, to
                    // prevent creating a new file, setting the offset to the offset limit, and being stuck in an infinite loop
                    if (offsetLoopAdjust) {
                        offset += 50;
                        offsetLoopAdjust = false;
                    }
                    String responseBody = sendPostRequest(API_URL, newAccessToken, offset, limit, KEYWORD, CLIENT_ID);

                    apiCount++;

                    String prefix = responseBody.substring(0, 10);
                    String suffix = responseBody.substring(10);
                    String indexedResponseBody = prefix + productIndex + suffix;

                    // When responseBody has no more content, break while loop
                    if (suffix.startsWith("\":[]")) {
                        CategoriesCheckList.categoryComplete(CATEGORYLIST, KEYWORD);
                        System.out.println("Writing to file.");
                        insertArrayLines(filePath, responseCollector);
                        responseCollector.clear();
                        System.out.println(KEYWORD + " file is complete.");
                        System.out.println("Checking next subcategory.");

                        if (categoryListComplete(CATEGORYLIST)) {
                            System.out.println("CategoriesList.txt file is complete.");
                            System.out.println("Ending program.");
                            System.exit(0); // Terminate the program with status code 0 (indicating successful termination)
                        }

                        KEYWORD = CategoriesCheckList.findCategory(CATEGORYLIST);
                        System.out.println("Pull from subcategory: " + KEYWORD);

                        // Assigning file name with KEYWORD match
                        fileName = selectingFileName(KEYWORD);

                        // Assigning file folder with KEYWORD match
                        fileFolder = selectingFileFolder(KEYWORD);

                        // Assigning file Path after checking file status
                        String[] returnedArray2 = checkingFile(fileName, fileFolder, offset, KEYWORD);

                        filePath = returnedArray2[0];

                        if (returnedArray2[2] == null) {
                            productIndex = 0;
                        }
                        else {
                            productIndex = Integer.parseInt(returnedArray2[2]);
                        }
                        if(returnedArray2[1] == null) {
                            offset = 0;
                        }
                        else {
                            offset = Integer.parseInt(returnedArray2[1]);
                        }
                        break;
                    }
                    if (prefix.startsWith("{\"fault\"")) {
                        System.out.println("""
                            Execution of ServiceCallout SC-Quota failed

                            Resource Limitations: Your system or the API server may have resource limitations that are exceeded when handling a large amount of data.
                            Rate Limiting: Some APIs impose rate limits to prevent abuse. If you exceed the allowed rate, you might encounter rate-limiting errors.""");
                    offset -= limit;
                    }
                    if (responseBody.contains("An error occurred while processing your request.")) {
                        // Decrementing offset to re-request Error Request

                        System.out.println("An error occurred while processing your request.");
                        System.out.println("Re-requesting response body.");
                        offset -= limit;
                    }
                    else if (responseBody.contains("BurstLimit exceeded.")) {
                        System.out.println("BurstLimit exceeded. Please try again after the number of seconds in the Retry-After header");
                        Thread.sleep(5000);
                        ClientScanner.clientUsed(CLIENTLIST, CLIENT_ID);
                        clientListUsedUp();
                        offset -= limit;

                        if (!ClientScanner.clientListComplete(CLIENTLIST)) {
                            System.out.println("\nGetting new Client Key.");

                            clientListUsedUp();

                            clientKey = ClientScanner.findFreeClient(CLIENTLIST);
                            CLIENT_ID = clientKey[0];
                            CLIENT_SECRET = clientKey[1];

                            System.out.println("Client ID and Secret:" + Arrays.toString(clientKey));
                            accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);
                            newAccessToken = accessToken.substring(43, 71);
                            apiCount = 0;
                        }
                    }
                    else if (responseBody.contains("Bearer token is expired. Please use your refresh token to obtain a new Bearer token, or acquire a new set of tokens from the OAuth endpoint.")) {
                        System.out.println("Bearer token is expired. Please use your refresh token to obtain a new Bearer token, or acquire a new set of tokens from the OAuth endpoint.");
                        System.out.println("Refreshing access token.");
                        accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);
                        newAccessToken = accessToken.substring(43, 71);

                        System.out.println("Re-requesting response body.");
                        offset -= limit;
                    }
                    else if (responseBody.contains("Bad Gateway")) {
                        System.out.println("Bad Gateway");
                        System.out.println("Re-requesting response body.");
                        offset -= limit;
                    }
                    else if (responseBody.contains("clientId used to get Bearer token doesnot match the X-DIGIKEY-Client-Id")) {
                        System.out.println("clientId used to get Bearer token does not match the X-DIGIKEY-Client-Id");
                        offset -= limit;
                    }
                    else if (responseBody.contains("Internal Server Error")) {
                        System.out.println("Internal Server Error");
                        System.out.println("Reached API daily limit.");

                        System.out.println("\nClient Key used up.");
                        ClientScanner.clientUsed(CLIENTLIST, CLIENT_ID);
                        clientListUsedUp();
                        offset -= limit;

                        if (!ClientScanner.clientListComplete(CLIENTLIST)) {
                            System.out.println("\nGetting new Client Key.");

                            clientListUsedUp();

                            clientKey = ClientScanner.findFreeClient(CLIENTLIST);
                            CLIENT_ID = clientKey[0];
                            CLIENT_SECRET = clientKey[1];

                            System.out.println("Client ID and Secret:" + Arrays.toString(clientKey));
                            accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);
                            newAccessToken = accessToken.substring(43, 71);
                            apiCount = 0;
                        }
                        else {
                            clientListUsedUp();
                        }
                    }
                    responseCollector.add(indexedResponseBody);
                    productIndex++;

                    // Process the current page of results
                    System.out.println("API Response (Offset: " + offset + "):");
                    System.out.println("API Count: " + apiCount);

                    if (offset % 25000 == 0 && offset != 0) {
                        System.out.println("Writing to file.");
                        insertArrayLines(filePath, responseCollector);
                        responseCollector.clear();

                        fileName = DirectoryFiler.createJSONFileWithPrefix("Postman Exports/" + fileFolder, KEYWORD);
                        filePath = "Postman Exports/" + fileFolder + fileName + ".json";
                        insertTemplate(filePath);
                        offsetLoopAdjust = true;
                        break;
                    }
                    // Increment offset for the next page
                    offset += limit;
                }
            } while (apiCount < 1200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}