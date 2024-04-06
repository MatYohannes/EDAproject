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


public class DigiKeyAPI3 {

    /*
    !!!!!!!!!!!!!!!!NEW USER
    !!!!!!!!!!!!!!!!!!!!!!!!!
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        When updating client id and client secret, search for CLIENT_ID and CLIENT_SECRET
     */

    // OAuth 2.0 client credentials
    private static final String CLIENT_ID = "wNGv9df3Jw9hZt6DasTMOjKYN4PZz1Fi";
    private static final String CLIENT_SECRET = "R4hJSM1YzaLGOjfq";

    // OAuth 2.0 authorization endpoints
    //private static final String AUTH_URL = "https://api.digikey.com/v1/oauth2/authorize"; // Not needed
    private static final String ACCESS_TOKEN_URL = "https://api.digikey.com/v1/oauth2/token";

    // API endpoint
    private static final String API_URL = "https://api.digikey.com/products/v4/search/keyword";

    private static String selectingFileName(String keyword) {
        String fileName;

        switch (keyword) {
            // Capacitor Subcategories
            case "Capacitor Accessories" -> fileName = "Capacitor Accessories";
            case "Resistors" -> fileName = "Resistors";
            case "Capacitors" -> fileName = "Capacitors";
            case "Switches" -> fileName = "Switches";
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
            case "ARINC Inserts" -> fileName = "ARINC Inserts";
            case "Backplane Connector Accessories" -> fileName = "Backplane Connector Accessories";
            case "Backplane Connector Contacts" -> fileName = "Backplane Connector Contacts";
            case "Backplane Connector Housings" -> fileName = "Backplane Connector Housings";
            case "DIN 41612" -> fileName = "DIN 41612";
            case "Hard Metric, Standard" -> fileName = "Hard Metric, Standard";
            case "Backplane Connectors Specialized" -> fileName = "Backplane Connectors Specialized";
            case "Banana and Tip Connector Adapters" -> fileName = "Banana and Tip Connector Adapters";
            case "Binding Posts" -> fileName = "Binding Posts";
            case "Jacks, Plugs" -> fileName = "Jacks, Plugs";
            case "Audio Connectors" -> fileName = "Audio Connectors";
            case "Barrel Connector Adapters" -> fileName = "Barrel Connector Adapters";
            case "Power Connectors" -> fileName = "Power Connectors";
            case "Blade Type Power Connector Accessories" -> fileName = "Blade Type Power Connector Accessories";
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
            case "Inductors, Coils, Chokes Delay Lines" -> fileName = "Inductors, Coils, Chokes Delay Lines Delay Lines";
            case "Fixed Inductors" -> fileName = "Fixed Inductors";
            case "Wireless Charging Coils" -> fileName = "Wireless Charging Coils";

            // Integrated Circuits (ICs) Subcategories
            case "Audio Special Purpose" -> fileName = "Audio_Special_Purpose";
            case "Application Specific Clock/Timing" -> fileName = "Application_Specific_Clock_Timing";
            case "Clock Buffers, Drivers" -> fileName = "Clock_Buffers_Drivers";
            case "Clock Generators, PLLs, Frequency Synthesizers" -> fileName = "Clock_Generators_PLLs_Frequency_Synthesizers";
            case "Inductors, Coils, Chokes Delay Lines Delay Lines" -> fileName = "Inductors, Coils, Chokes Delay Lines";
            case "IC Batteries" -> fileName = "IC_Batteries";
            case "Programmable Timers and Oscillators" -> fileName = "Programmable_Timers_and_Oscillators";
            case "Real Time Clocks" -> fileName = "Real_Time_Clocks";
            case "ADCs/DACs - Special Purpose" -> fileName = "ADCs_DACs_Special_Purpose";
            case "Analog Front End (AFE)" -> fileName = "Analog_Front_End_AFE";
            case "Analog to Digital Converters (ADC)" -> fileName = "Analog_to_Digital_Converters_ADC";
            case "Digital Potentiometers" -> fileName = "Digital_Potentiometers";
            case "Digital to Analog Converters (DAC)" -> fileName = "Digital_to_Analog_Converters_DAC";
            case "Touch Screen Controllers" -> fileName = "Touch_Screen_Controllers";
            case "Application Specific Microcontrollers" -> fileName = "Application_Specific_Microcontrollers";
            case "CPLDs (Complex Programmable Logic Devices)" -> fileName = "CPLDs_Complex_Programmable_Logic_Devices";
            case "DSP (Digital Signal Processors)" -> fileName = "DSP_Digital_Signal_Processors";
            case "FPGAs (Field Programmable Gate Array)" -> fileName = "FPGAs_Field_Programmable_Gate_Array";
            case "FPGAs (Field Programmable Gate Array) with Microcontrollers" -> fileName = "FPGAs_Field_Programmable_Gate_Array_with_Microcontrollers";
            case "Microcontrollers" -> fileName = "Microcontrollers";
            case "Microcontrollers, Microprocessor, FPGA Modules" -> fileName = "Microcontrollers_Microprocessor_FPGA_Modules";
            case "Microprocessors" -> fileName = "Microprocessors";
            case "PLDs (Programmable Logic Device)" -> fileName = "PLDs_Programmable_Logic_Device";
            case "System On Chip (SoC)" -> fileName = "System_On_Chip_SoC";
            case "Analog Switches - Special Purpose" -> fileName = "Analog_Switches_Special_Purpose";
            case "Analog Switches, Multiplexers, Demultiplexers" -> fileName = "Analog_Switches_Multiplexers_Demultiplexers";
            case "CODECS" -> fileName = "CODECS";
            case "Interface Controllers" -> fileName = "Interface Controllers";
            case "Direct Digital Synthesis (DDS)" -> fileName = "Direct_Digital_Synthesis_DDS";
            case "Drivers, Receivers, Transceivers" -> fileName = "Drivers_Receivers_Transceivers";
            case "Encoders, Decoders, Converters" -> fileName = "Encoders_Decoders_Converters";
            case "Filters - Active" -> fileName = "Filters_Active";
            case "I/O Expanders" -> fileName = "I_O_Expanders";
            case "Modems - ICs and Modules" -> fileName = "Modems_ICs_and_Modules";
            case "Modules" -> fileName = "Modules";
            case "Sensor and Detector Interfaces" -> fileName = "Sensor_and_Detector_Interfaces";
            case "Sensor, Capacitive Touch" -> fileName = "Sensor_Capacitive_Touch";
            case "Serializers, Deserializers" -> fileName = "Serializers_Deserializers";
            case "Signal Buffers, Repeaters, Splitters" -> fileName = "Signal_Buffers_Repeaters_Splitters";
            case "Signal Terminators" -> fileName = "Signal_Terminators";
            case "Specialized" -> fileName = "Specialized";
            case "Telecom" -> fileName = "Telecom";
            case "UARTs (Universal Asynchronous Receiver Transmitter)" -> fileName = "UARTs_Universal_Asynchronous_Receiver_Transmitter";
            case "Voice Record and Playback" -> fileName = "Voice_Record_and_Playback";
            case "Amplifiers" -> fileName = "Amplifiers";
            case "Analog Multipliers, Dividers" -> fileName = "Analog_Multipliers_Dividers";
            case "Comparators" -> fileName = "Comparators";
            case "Video Processing" -> fileName = "Video_Processing";
            case "Buffers, Drivers, Receivers, Transceivers" -> fileName = "Buffers_Drivers_Receivers_Transceivers";
            case "Counters, Dividers" -> fileName = "Counters_Dividers";
            case "FIFOs Memory" -> fileName = "FIFOs_Memory";
            case "Flip Flops" -> fileName = "Flip_Flops";
            case "Gates and Inverters" -> fileName = "Gates_and_Inverters";
            case "Gates and Inverters - Multi-Function, Configurable" -> fileName = "Gates_and_Inverters_Multi_Function_Configurable";
            case "Latches" -> fileName = "Latches";
            case "Multivibrators" -> fileName = "Multivibrators";
            case "Parity Generators and Checkers" -> fileName = "Parity_Generators_and_Checkers";
            case "Shift Registers" -> fileName = "Shift_Registers";
            case "Signal Switches, Multiplexers, Decoders" -> fileName = "Signal_Switches_Multiplexers_Decoders";
            case "Specialty Logic" -> fileName = "Specialty_Logic";
            case "Translators, Level Shifters" -> fileName = "Translators_Level_Shifters";
            case "Universal Bus Functions" -> fileName = "Universal_Bus_Functions";
            case "Batteries" -> fileName = "Batteries";
            case "Configuration PROMs for FPGAs" -> fileName = "Configuration_PROMs_for_FPGAs";
            case "Memory Controllers" -> fileName = "Memory Controllers";
            case "Memory" -> fileName = "Memory";
            case "AC DC Converters, Offline Switches" -> fileName = "AC_DC_Converters_Offline_Switches";
            case "Battery Chargers" -> fileName = "Battery_Chargers";
            case "Battery Management" -> fileName = "Battery_Management";
            case "Current Regulation/Management" -> fileName = "Current_Regulation_Management";
            case "DC DC Switching Controllers" -> fileName = "DC_DC_Switching_Controllers";
            case "Display Drivers" -> fileName = "Display_Drivers";
            case "Energy Metering" -> fileName = "Energy_Metering";
            case "Full Half-Bridge (H Bridge) Drivers" -> fileName = "Full_Half_Bridge_H_Bridge_Drivers";
            case "Gate Drivers" -> fileName = "Gate_Drivers";
            case "Hot Swap Controllers" -> fileName = "Hot_Swap_Controllers";
            case "Laser Drivers" -> fileName = "Laser_Drivers";
            case "LED Drivers" -> fileName = "LED_Drivers";
            case "Lighting, Ballast Controllers" -> fileName = "Lighting_Ballast_Controllers";
            case "Motor Drivers, Controllers" -> fileName = "Motor_Drivers_Controllers";
            case "OR Controllers, Ideal Diodes" -> fileName = "OR_Controllers_Ideal_Diodes";
            case "PFC (Power Factor Correction)" -> fileName = "PFC_Power_Factor_Correction";
            case "Power Distribution Switches, Load Drivers" -> fileName = "Power_Distribution_Switches_Load_Drivers";
            case "Power Management - Specialized" -> fileName = "Power_Management_Specialized";
            case "Power Over Ethernet (PoE) Controllers" -> fileName = "Power_Over_Ethernet_PoE_Controllers";
            case "Power Supply Controllers, Monitors" -> fileName = "Power_Supply_Controllers_Monitors";
            case "RMS to DC Converters" -> fileName = "RMS_to_DC_Converters";
            case "Special Purpose Regulators" -> fileName = "Special_Purpose_Regulators";
            case "Supervisors" -> fileName = "Supervisors";
            case "Thermal Management" -> fileName = "Thermal_Management";
            case "V/F and F/V Converters" -> fileName = "V_F_and_F_V_Converters";
            case "Voltage Reference" -> fileName = "Voltage_Reference";
            case "Voltage Regulators - DC DC Switching Regulators" -> fileName = "Voltage_Regulators_DC_DC_Switching_Regulators";
            case "Voltage Regulators - Linear + Switching" -> fileName = "Voltage_Regulators_Linear_Switching";
            case "Voltage Regulators - Linear Regulator Controllers" -> fileName = "Voltage_Regulators_Linear_Regulator_Controllers";
            case "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators" -> fileName = "Voltage_Regulators_Linear_Low_Drop_Out_LDO_Regulators";
            case "Specialized ICs" -> fileName = "Specialized_ICs";

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
            case "Resistor Accessories" -> fileName = "Resistor Accessories";
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

    private static String selectingFileFolder(String keyword) {

        String fileFolder = switch (keyword) {
            // Capacitors subcategories
            case "Capacitor Accessories", "Aluminum - Polymer Capacitors", "Aluminum Electrolytic Capacitors",
                    "Capacitor Networks, Arrays", "Ceramic Capacitors", "Electric Double Layer Capacitors (EDLC), Supercapacitors",
                    "Film Capacitors", "Mica and PTFE Capacitors", "Niobium Oxide Capacitors", "Silicon Capacitors",
                    "Tantalum - Polymer Capacitors", "Tantalum Capacitors", "Thin Film Capacitors",
                    "Trimmers, Variable Capacitors" -> "Capacitors/";

            // Connectors, Interconnects subcategories
            case "Plugs and Receptacles", "Power Entry Connector Accessories", "Power Entry Modules (PEM)",
                    "ARINC Inserts", "Backplane Connector Accessories", "Backplane Connector Contacts",
                    "Backplane Connector Housings", "DIN 41612", "Hard Metric, Standard", "Backplane Connectors Specialized",
                    "Banana and Tip Connector Accessories", "Banana and Tip Connector Adapters", "Binding Posts",
                    "Jacks, Plugs", "Audio Connectors", "Barrel Connector Accessories", "Barrel Connector Adapters",
                    "Power Connectors", "Blade Type Power Connector Accessories", "Blade Type Power Connector Assemblies",
                    "Blade Type Power Connector Contacts", "Blade Type Power Connector Housings",
                    "Card Edge Connector Accessories", "Card Edge Connector Adapters", "Card Edge Connector Contacts",
                    "Card Edge Connector Housings", "Edgeboard Connectors", "Backshells and Cable Clamps",
                    "Circular Connector Accessories", "Circular Connector Adapters", "Circular Connector Assemblies",
                    "Circular Connector Contacts", "Circular Connector Housings", "Coaxial Connector (RF) Accessories",
                    "Coaxial Connector (RF) Adapters", "Coaxial Connector (RF) Assemblies", "Coaxial Connector (RF) Contacts",
                    "Coaxial Connector (RF) Terminators", "Contacts, Spring Loaded (Pogo Pins), and Pressure", "Leadframe",
                    "Multi Purpose", "Centronics Connectors", "D-Sub Connector Assemblies", "D-Sub, D-Shaped Connector Accessories",
                    "D-Sub, D-Shaped Connector Adapters", "D-Sub, D-Shaped Connector Backshells, Hoods",
                    "D-Sub, D-Shaped Connector Contacts", "D-Sub, D-Shaped Connector Housings", "D-Sub, D-Shaped Connector Jackscrews",
                    "D-Sub, D-Shaped Connector Terminators", "FFC, FPC (Flat Flexible) Connector Accessories",
                    "FFC, FPC (Flat Flexible) Connector Assemblies", "FFC, FPC (Flat Flexible) Connector Contacts",
                    "FFC, FPC (Flat Flexible) Connector Housings", "Fiber Optic Connector Accessories",
                    "Fiber Optic Connector Adapters", "Fiber Optic Connector Assemblies", "Fiber Optic Connector Housings",
                    "Heavy Duty Connector Accessories", "Heavy Duty Connector Assemblies", "Heavy Duty Connector Contacts",
                    "Heavy Duty Connector Frames", "Heavy Duty Connector Housings, Hoods, Bases",
                    "Heavy Duty Connector Inserts, Modules", "Keystone Connector Accessories", "Keystone Faceplates, Frames",
                    "Keystone Inserts", "Inline Module Sockets", "Memory Connector Accessories", "PC Card Sockets",
                    "PC Cards - Adapters", "Modular Connector Accessories", "Modular Connector Adapters", "Modular Connector Jacks",
                    "Modular Connector Jacks With Magnetics", "Modular Connector Plug Housings", "Modular Connector Plugs",
                    "Modular Connector Wiring Blocks", "Modular Connector Wiring Blocks Accessories",
                    "Photovoltaic (Solar Panel) Connector Accessories", "Photovoltaic (Solar Panel) Connector Assemblies",
                    "Photovoltaic (Solar Panel) Connector Contacts", "Pluggable Connector Accessories",
                    "Pluggable Connector Assemblies", "Rectangular Connector Accessories", "Rectangular Connector Adapters",
                    "Rectangular Connector Contacts", "Rectangular Connector Housings", "Spring Loaded", "IC Sockets",
                    "Socket Accessories", "Socket Adapters", "Solid State Lighting Connector Accessories",
                    "Solid State Lighting Connector Assemblies", "Solid State Lighting Connector Contacts", "Barrier Blocks",
                    "Din Rail, Channel", "Headers, Plugs and Sockets", "Interface Modules", "Panel Mount", "Power Distribution",
                    "Terminal Blocks Specialized", "Terminal Block Accessories", "Terminal Block Adapters", "Terminal Block Contacts",
                    "Wire to Board", "USB, DVI, HDMI Connector Accessories", "USB, DVI, HDMI Connector Adapters",
                    "USB, DVI, HDMI Connector Assemblies" -> "Connectors, Interconnects/";

            // Crystal, Oscillators, Resonators subcategories
            case "Crystal, Oscillator, Resonator Accessories", "Crystals", "Oscillators", "Pin Configurable-Selectable Oscillators",
                    "Programmable Oscillators", "Resonators", "Stand Alone Programmers",
                    "VCOs (Voltage Controlled Oscillators)" -> "Crystals, Oscillators, Resonators/";

            // Inductors, Coil, Chokes subcategories

            case "Adjustable Inductors", "Arrays, Signal Transformers", "Inductors, Coils, Chokes Delay Lines", "Fixed Inductors",
                    "Wireless Charging Coils" -> "Inductors, Coils, Chokes/";

            // Integrated Circuits (ICs) subcategories
            case "Audio Special Purpose", "Application Specific Clock/Timing", "Clock Buffers, Drivers",
                    "Clock Generators, PLLs, Frequency Synthesizers", "Delay Lines", "IC Batteries",
                    "Programmable Timers and Oscillators", "Real Time Clocks", "ADCs/DACs - Special Purpose", "Analog Front End (AFE)",
                    "Analog to Digital Converters (ADC)", "Digital Potentiometers", "Digital to Analog Converters (DAC)",
                    "Touch Screen Controllers", "Application Specific Microcontrollers", "CPLDs (Complex Programmable Logic Devices)",
                    "DSP (Digital Signal Processors)", "FPGAs (Field Programmable Gate Array)",
                    "FPGAs (Field Programmable Gate Array) with Microcontrollers", "Microcontrollers",
                    "Microcontrollers, Microprocessor, FPGA Modules", "Microprocessors", "PLDs (Programmable Logic Device)",
                    "System On Chip (SoC)", "Analog Switches - Special Purpose", "Analog Switches, Multiplexers, Demultiplexers",
                    "CODECS", "Interface Controllers", "Direct Digital Synthesis (DDS)", "Drivers, Receivers, Transceivers",
                    "Encoders, Decoders, Converters", "Filters - Active", "I/O Expanders", "Modems - ICs and Modules", "Modules",
                    "Sensor and Detector Interfaces", "Sensor, Capacitive Touch", "Serializers, Deserializers",
                    "Signal Buffers, Repeaters, Splitters", "Signal Terminators", "Specialized", "Telecom",
                    "UARTs (Universal Asynchronous Receiver Transmitter)", "Voice Record and Playback", "Amplifiers",
                    "Analog Multipliers, Dividers", "Comparators", "Video Processing", "Buffers, Drivers, Receivers, Transceivers",
                    "Counters, Dividers", "FIFOs Memory", "Flip Flops", "Gates and Inverters",
                    "Gates and Inverters - Multi-Function, Configurable", "Latches", "Multivibrators",
                    "Parity Generators and Checkers", "Shift Registers", "Signal Switches, Multiplexers, Decoders", "Specialty Logic",
                    "Translators, Level Shifters", "Universal Bus Functions", "Batteries", "Configuration PROMs for FPGAs",
                    "Memory Controllers", "Memory", "AC DC Converters, Offline Switches", "Battery Chargers", "Battery Management",
                    "Current Regulation/Management", "DC DC Switching Controllers", "Display Drivers", "Energy Metering",
                    "Full Half-Bridge (H Bridge) Drivers", "Gate Drivers", "Hot Swap Controllers", "Laser Drivers", "LED Drivers",
                    "Lighting, Ballast Controllers", "Motor Drivers, Controllers", "OR Controllers, Ideal Diodes",
                    "PFC (Power Factor Correction)", "Power Distribution Switches, Load Drivers", "Power Management - Specialized",
                    "Power Over Ethernet (PoE) Controllers", "Power Supply Controllers, Monitors", "RMS to DC Converters",
                    "Special Purpose Regulators", "Supervisors", "Thermal Management", "V/F and F/V Converters", "Voltage Reference",
                    "Voltage Regulators - DC DC Switching Regulators", "Voltage Regulators - Linear + Switching",
                    "Voltage Regulators - Linear Regulator Controllers", "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators",
                    "Specialized ICs" -> "Integrated Circuits (ICs)/";

            // Potentiometers, Variable Resistors subcategories
            case "Potentiometers, Variable Resistors Accessories", "Adjustable Power Resistor", "Joystick Potentiometers",
                    "Rotary Potentiometers, Rheostats", "Scale Dials", "Slide Potentiometers", "Thumbwheel Potentiometers",
                    "Trimmer Potentiometers", "Value Display Potentiometers" -> "Potentiometers, Variable Resistors/";

            // Relays subcategories
            case "Relays Accessories", "Automotive Relays", "Contactors (Electromechanical)", "Contactors (Solid State)",
                    "High Frequency (RF) Relays", "I/O Relay Module Racks", "I/O Relay Modules", "Power Relays, Over 2 Amps",
                    "Reed Relays", "Relay Sockets", "Safety Relays", "Signal Relays, Up to 2 Amps", "Solid State Relays" -> "Relays/";

            // Resistor subcategories
            case "Resistor Accessories", "Chassis Mount Resistors", "Chip Resistor - Surface Mount", "Precision Trimmed Resistors",
                    "Resistor Networks, Arrays", "Specialized Resistors", "Through Hole Resistors", "Accessories" -> "Resistors/";

            // Switches subcategories
            case "Switches Accessories", "Accessories - Boots, Seals", "Accessories - Caps", "Cable Pull Switches",
                    "Configurable Switch Bodies", "Configurable Switch Contact Blocks", "Configurable Switch Illumination Sources",
                    "Configurable Switch Lens", "DIP Switches", "Disconnect Switch Components", "Emergency Stop (E-Stop) Switches",
                    "Interlock Switches", "Keylock Switches", "Keypad Switches", "Limit Switches", "Magnetic, Reed Switches",
                    "Navigation Switches, Joystick", "Programmable Display Switches", "Pushbutton Switches",
                    "Pushbutton Switches - Hall Effect", "Rocker Switches", "Rotary Switches", "Selector Switches", "Slide Switches",
                    "Tactile Switches", "Thumbwheel Switches", "Toggle Switches" -> "Switches/";
            default -> "";
        };
        return fileFolder;
    }

    private static String[] checkingFile(String fileName, String fileFolder, int offset) {

        int productIndex = 1;

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
//                    System.out.println(arrayCount(filePath));
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

    public static void main(String[] args) {
        try {
            // offset is used to keep track of placement when get API response
            // limit of rows per response is 50
            int offset = 0;
            int limit = 50;

            /*
                GUI team can create a window with a drop-down or a write-in opinion for the
                client to select which keyword to pull.
                Action listener?
             */

            // CategoriesCheckList reads CategoriesList.txt for the next category to pull
            String KEYWORD = CategoriesCheckList.findCategory("CategoriesList.txt");
            System.out.println("Pulling from subcategory: " + KEYWORD);

            // Assigning file name with KEYWORD match
            String fileName = selectingFileName(KEYWORD);

            // Assigning file folder with KEYWORD match
            String fileFolder = selectingFileFolder(KEYWORD);

            // Assigning to the correct parent category
            int productIndex = 1;

            // Assigning file Path after checking file status

            String[] returnedArray = checkingFile(fileName, fileFolder, offset);
            String filePath = returnedArray[0];
            if (returnedArray[1] == null) {
                offset = 0;
            }
            else {
                offset = Integer.parseInt(returnedArray[1]);
            }
            if (returnedArray[2] == null) {
                productIndex = 1;
            }
            else {
                productIndex = Integer.parseInt(returnedArray[2]);
            }


            int apiCount = 0;

            do {
                // Step 1: Obtain OAuth 2.0 access token
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
                    // Send API requests with current offset
                    String responseBody = sendPostRequest(API_URL, newAccessToken, offset, limit, KEYWORD);

                    apiCount++;

                    String prefix = responseBody.substring(0, 10);
                    String suffix = responseBody.substring(10);
                    String indexedResponseBody = prefix + productIndex + suffix;

                    // When responseBody has no more content, break while loop
                    if (suffix.startsWith("\":[]")) {
                        CategoriesCheckList.categoryComplete("CategoriesList.txt", KEYWORD);
                        System.out.println(KEYWORD + " file is complete.");
                        System.out.println("Checking next subcategory.");
                        KEYWORD = CategoriesCheckList.findCategory("CategoriesList.txt");
                        System.out.println("Pull from subcategory: " + KEYWORD);

                        // Assigning file name with KEYWORD match
                        fileName = selectingFileName(KEYWORD);

                        // Assigning file folder with KEYWORD match
                        fileFolder = selectingFileFolder(KEYWORD);

                        // Assigning to the correct parent category
//                        productIndex = 1;

                        // Assigning file Path after checking file status
                        String[] returnedArray2 = checkingFile(fileName, fileFolder, offset);

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
                    else if (responseBody.contains("Internal Server Error")) {
                        System.out.println("Internal Server Error");
                        System.out.println("Reached API daily limit.");
                        apiCount = 1000;
                        break;
                    }
                    if (!responseBody.contains("Description") && !responseBody.contains("Internal Server Error") &&
                            !responseBody.contains("Bad Gateway") && !responseBody.contains("Bearer token is expired." +
                            " Please use your refresh token to obtain a new Bearer token, or acquire a new set " +
                            "of tokens from the OAuth endpoint.") &&
                            !responseBody.contains("An error occurred while processing your request.")) {
                        System.out.println(indexedResponseBody);
                    }

                    insertNewLines(filePath, indexedResponseBody);
                    productIndex++;

                    // Process the current page of results
                    System.out.println("API Response (Offset: " + offset + "):");
                    System.out.println("API Count: " + apiCount);

                    // Increment offset for the next page
                    offset += limit;
                }

            } while (apiCount < 999);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int arrayCount(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray innerArray;
        Object obj = parser.parse(new FileReader(filePath));
        org.json.simple.JSONObject jsonObject = (JSONObject) obj;
        innerArray = (JSONArray) jsonObject.get("Products");

        return innerArray.size();
    }


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

    private static int getPosition(String content, int lineNumber) {
        // Calculate the position to insert the new line
        int position = 0;
        for (int i = 0; i < lineNumber; i++) {
            position = content.indexOf(System.lineSeparator(), position) + 1;
            if (position == 0) {
                break; // Break if the line separator is not found
            }
        }
        return position;
    }

    private static void insertTemplate(String filePath) throws IOException {
        // Inserting template
        String template = "{\"Products\":[\n\n] }";
        FileWriter writer = new FileWriter(filePath);
        writer.write(template);
        writer.close();
    }

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

    private static String sendPostRequest(String apiUrl, String accessToken, int offset, int limit, String keyword) throws Exception {
        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create HTTP request to API endpoint
        HttpPost apiRequest = new HttpPost(apiUrl);
        apiRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        apiRequest.addHeader("X-DIGIKEY-Client-Id", "wNGv9df3Jw9hZt6DasTMOjKYN4PZz1Fi");

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
}