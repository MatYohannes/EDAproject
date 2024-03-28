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
import java.util.Iterator;

public class DigiKeyAPI3 {

    /*
        When updating client id and client secret, search for CLIENT_ID and CLIENT_SECRET
        To change the item to search for, search for KEYWORD
     */

    private static final String KEYWORD = "Aluminum Electrolytic Capacitors";

    // OAuth 2.0 client credentials
    private static final String CLIENT_ID = "wNGv9df3Jw9hZt6DasTMOjKYN4PZz1Fi";
    private static final String CLIENT_SECRET = "R4hJSM1YzaLGOjfq";

    // OAuth 2.0 authorization endpoints
    private static final String AUTH_URL = "https://api.digikey.com/v1/oauth2/authorize";
    private static final String ACCESS_TOKEN_URL = "https://api.digikey.com/v1/oauth2/token";

    // API endpoint
    private static final String API_URL = "https://api.digikey.com/products/v4/search/keyword";



    public static void main(String[] args) {
        try {

            // Step 2: Send POST request to API endpoint with access token
            int offset = 0;
            int limit = 50;

            /*
                GUI team can create a window with a drop-down or a write-in opinion for the
                client to select which keyword to pull.
                Action listener?
             */

            String fileName = "";

            switch(KEYWORD) {
                // Capacitor Subcategories
                case "Capacitor Accessories":
                    fileName = "Capacitor Accessories";
                    break;
                case "Resistors":
                    fileName = "Resistors";
                    break;
                case "Capacitors":
                    fileName = "Capacitors";
                    break;
                case "Switches":
                    fileName = "Switches";
                    break;
                case "Aluminum - Polymer Capacitors":
                    fileName = "Aluminum - Polymer Capacitors";
                    break;
                case "Aluminum Electrolytic Capacitors":
                    fileName = "Aluminum Electrolytic Capacitors";
                    break;
                case "Capacitor Networks, Arrays":
                    fileName = "Capacitor Networks, Arrays";
                    break;
                case "Ceramic Capacitors":
                    fileName = "Ceramic Capacitors";
                    break;
                case "Electric Double Layer Capacitors (EDLC), Supercapacitors":
                    fileName = "Electric Double Layer Capacitors (EDLC), Supercapacitors";
                    break;
                case "Film Capacitors":
                    fileName = "Film Capacitors";
                    break;
                case "Mica and PTFE Capacitors":
                    fileName = "Mica and PTFE Capacitors";
                    break;
                case "Niobium Oxide Capacitors":
                    fileName = "Niobium Oxide Capacitors";
                    break;
                case "Silicon Capacitors":
                    fileName = "Silicon Capacitors";
                    break;
                case "Tantalum - Polymer Capacitors":
                    fileName = "Tantalum - Polymer Capacitors";
                    break;
                case "Tantalum Capacitors":
                    fileName = "Tantalum Capacitors";
                    break;
                case "Thin Film Capacitors":
                    fileName = "Thin Film Capacitors";
                    break;
                case "Trimmers, Variable Capacitors":
                    fileName = "Trimmers, Variable Capacitors";
                    break;

                    // Connectors, Interconnects
                case "Plugs and Receptacles":
                    fileName = "Plugs and Receptacles";
                    break;
                case "Power Entry Connector Accessories":
                    fileName = "Power Entry Connector Accessories";
                    break;
                case "Power Entry Modules (PEM)":
                    fileName = "Power Entry Modules (PEM)";
                    break;
                case "ARINC Inserts":
                    fileName = "ARINC Inserts";
                    break;
                case "Backplane Connector Accessories":
                    fileName = "Backplane Connector Accessories";
                    break;
                case "Backplane Connector Contacts":
                    fileName = "Backplane Connector Contacts";
                    break;
                case "Backplane Connector Housings":
                    fileName = "Backplane Connector Housings";
                    break;
                case "DIN 41612":
                    fileName = "DIN 41612";
                    break;
                case "Hard Metric, Standard":
                    fileName = "Hard Metric, Standard";
                    break;
                case "Backplane Connectors Specialized":
                    fileName = "Backplane Connectors Specialized";
                    break;
                case "Banana and Tip Connector Adapters":
                    fileName = "Banana and Tip Connector Adapters";
                    break;
                case "Binding Posts":
                    fileName = "Binding Posts";
                    break;
                case "Jacks, Plugs":
                    fileName = "Jacks, Plugs";
                    break;
                case "Audio Connectors":
                    fileName = "Audio Connectors";
                    break;
                case "Barrel Connector Adapters":
                    fileName = "Barrel Connector Adapters";
                    break;
                case "Power Connectors":
                    fileName = "Power Connectors";
                    break;
                case "Blade Type Power Connector Accessories":
                    fileName = "Blade Type Power Connector Accessories";
                    break;
                case "Blade Type Power Connector Contacts":
                    fileName = "Blade Type Power Connector Contacts";
                    break;
                case "Blade Type Power Connector Housings":
                    fileName = "Blade Type Power Connector Housings";
                    break;
                case "Card Edge Connector Accessories":
                    fileName = "Card Edge Connector Accessories";
                    break;
                case "Card Edge Connector Adapters":
                    fileName = "Card Edge Connector Adapters";
                    break;
                case "Card Edge Connector Contacts":
                    fileName = "Card Edge Connector Contacts";
                    break;
                case "Card Edge Connector Housings":
                    fileName = "Card Edge Connector Housings";
                    break;
                case "Edgeboard Connectors":
                    fileName = "Edgeboard Connectors";
                    break;
                case "Backshells and Cable Clamps":
                    fileName = "Backshells and Cable Clamps";
                    break;
                case "Circular Connector Accessories":
                    fileName = "Circular Connector Accessories";
                    break;
                case "Circular Connector Adapters":
                    fileName = "Circular Connector Adapters";
                    break;
                case "Circular Connector Assemblies":
                    fileName = "Circular Connector Assemblies";
                    break;
                case "Circular Connector Contacts":
                    fileName = "Circular Connector Contacts";
                    break;
                case "Circular Connector Housings":
                    fileName = "Circular Connector Housings";
                    break;
                case "Coaxial Connector (RF) Accessories":
                    fileName = "Coaxial Connector (RF) Accessories";
                    break;
                case "Coaxial Connector (RF) Adapters":
                    fileName = "Coaxial Connector (RF) Adapters";
                    break;
                case "Coaxial Connector (RF) Assemblies":
                    fileName = "Coaxial Connector (RF) Assemblies";
                    break;
                case "Coaxial Connector (RF) Contacts":
                    fileName = "Coaxial Connector (RF) Contacts";
                    break;
                case "Coaxial Connector (RF) Terminators":
                    fileName = "Coaxial Connector (RF) Terminators";
                    break;
                case "Contacts, Spring Loaded (Pogo Pins), and Pressure":
                    fileName = "Contacts, Spring Loaded (Pogo Pins), and Pressure";
                    break;
                case "Leadframe":
                    fileName = "Leadframe";
                    break;
                case "Multi Purpose":
                    fileName = "Multi Purpose";
                    break;
                case "D-Sub Connector Assemblies":
                    fileName = "D-Sub Connector Assemblies";
                    break;
                case "D-Sub, D-Shaped Connector Accessories":
                    fileName = "D-Sub, D-Shaped Connector Accessories";
                    break;
                case "D-Sub, D-Shaped Connector Adapters":
                    fileName = "D-Sub, D-Shaped Connector Adapters";
                    break;
                case "D-Sub, D-Shaped Connector Backshells, Hoods":
                    fileName = "D-Sub, D-Shaped Connector Backshells, Hoods";
                    break;
                case "D-Sub, D-Shaped Connector Contacts":
                    fileName = "D-Sub, D-Shaped Connector Contacts";
                    break;
                case "D-Sub, D-Shaped Connector Housings":
                    fileName = "D-Sub, D-Shaped Connector Housings";
                    break;
                case "D-Sub, D-Shaped Connector Jackscrews":
                    fileName = "D-Sub, D-Shaped Connector Jackscrews";
                    break;
                case "D-Sub, D-Shaped Connector Terminators":
                    fileName = "D-Sub, D-Shaped Connector Terminators";
                    break;
                case "FFC, FPC (Flat Flexible) Connector Accessories":
                    fileName = "FFC, FPC (Flat Flexible) Connector Accessories";
                    break;
                case "FFC, FPC (Flat Flexible) Connector Assemblies":
                    fileName = "FFC, FPC (Flat Flexible) Connector Assemblies";
                    break;
                case "FFC, FPC (Flat Flexible) Connector Contacts":
                    fileName = "FFC, FPC (Flat Flexible) Connector Contacts";
                    break;
                case "FFC, FPC (Flat Flexible) Connector Housings":
                    fileName = "FFC, FPC (Flat Flexible) Connector Housings";
                    break;
                case "Fiber Optic Connector Accessories":
                    fileName = "Fiber Optic Connector Accessories";
                    break;
                case "Fiber Optic Connector Adapters":
                    fileName = "Fiber Optic Connector Adapters";
                    break;
                case "Fiber Optic Connector Assemblies":
                    fileName = "Fiber Optic Connector Assemblies";
                    break;
                case "Fiber Optic Connector Housings":
                    fileName = "Fiber Optic Connector Housings";
                    break;
                case "Heavy Duty Connector Accessories":
                    fileName = "Heavy Duty Connector Accessories";
                    break;
                case "Heavy Duty Connector Assemblies":
                    fileName = "Heavy Duty Connector Assemblies";
                    break;
                case "Heavy Duty Connector Contacts":
                    fileName = "Heavy Duty Connector Contacts";
                    break;
                case "Heavy Duty Connector Frames":
                    fileName = "Heavy Duty Connector Frames";
                    break;
                case "Heavy Duty Connector Housings, Hoods, Bases":
                    fileName = "Heavy Duty Connector Housings, Hoods, Bases";
                    break;
                case "Heavy Duty Connector Inserts, Modules":
                    fileName = "Heavy Duty Connector Inserts, Modules";
                    break;
                case "Keystone Connector Accessories":
                    fileName = "Keystone Connector Accessories";
                    break;
                case "Keystone Faceplates, Frames":
                    fileName = "Keystone Faceplates, Frames";
                    break;
                case "Keystone Inserts":
                    fileName = "Keystone Inserts";
                    break;
                case "Modular Connector Accessories":
                    fileName = "Modular Connector Accessories";
                    break;
                case "Modular Connector Adapters":
                fileName = "Modular Connector Adapters";
                break;
                case "Modular Connector Jacks":
                    fileName = "Modular Connector Jacks";
                    break;
                case "Modular Connector Jacks With Magnetics":
                    fileName = "Modular Connector Jacks With Magnetics";
                    break;
                case "Modular Connector Plug Housings":
                    fileName = "Modular Connector Plug Housings";
                    break;
                case "Modular Connector Plugs":
                    fileName = "Modular Connector Plugs";
                    break;
                case "Modular Connector Wiring Blocks":
                    fileName = "Modular Connector Wiring Blocks";
                    break;
                case "Modular Connector Wiring Blocks Accessories":
                    fileName = "Modular Connector Wiring Blocks Accessories";
                    break;
                case "Photovoltaic (Solar Panel) Connector Accessories":
                    fileName = "Photovoltaic (Solar Panel) Connector Accessories";
                    break;
                case "Photovoltaic (Solar Panel) Connector Assemblies":
                    fileName = "Photovoltaic (Solar Panel) Connector Assemblies";
                    break;
                case "Photovoltaic (Solar Panel) Connector Contacts":
                    fileName = "Photovoltaic (Solar Panel) Connector Contacts";
                    break;
                case "Pluggable Connector Accessories":
                    fileName = "Pluggable Connector Accessories";
                    break;
                case "Pluggable Connector Assemblies":
                    fileName = "Pluggable Connector Assemblies";
                    break;
                case "Rectangular Connector Accessories":
                    fileName = "Rectangular Connector Accessories";
                    break;
                case "Rectangular Connector Adapters":
                    fileName = "Rectangular Connector Adapters";
                    break;
                case "Rectangular Connector Contacts":
                    fileName = "Rectangular Connector Contacts";
                    break;
                case "Rectangular Connector Housings":
                    fileName = "Rectangular Connector Housings";
                    break;
                case "Spring Loaded":
                    fileName = "Spring Loaded";
                    break;
                case "IC Sockets":
                    fileName = "IC Sockets";
                    break;
                case "Socket Accessories":
                    fileName = "Socket Accessories";
                    break;
                case "Socket Adapters":
                    fileName = "Socket Adapters";
                    break;
                case "Solid State Lighting Connector Accessories":
                    fileName = "Solid State Lighting Connector Accessories";
                    break;
                case "Solid State Lighting Connector Assemblies":
                    fileName = "Solid State Lighting Connector Assemblies";
                    break;
                case "Solid State Lighting Connector Contacts":
                    fileName = "Solid State Lighting Connector Contacts";
                    break;
                case "Barrier Blocks":
                    fileName = "Barrier Blocks";
                    break;
                case "Din Rail, Channel":
                    fileName = "Din Rail, Channel";
                    break;
                case "Headers, Plugs and Sockets":
                    fileName = "Headers, Plugs and Sockets";
                    break;
                case "Interface Modules":
                    fileName = "Interface Modules";
                    break;
                case "Panel Mount":
                    fileName = "Panel Mount";
                    break;
                case "Power Distribution":
                    fileName = "Power Distribution";
                    break;
                case "Terminal Blocks Specialized":
                    fileName = "Terminal Blocks Specialized";
                    break;
                case "Terminal Block Accessories":
                    fileName = "Terminal Block Accessories";
                    break;
                case "Terminal Block Adapters":
                    fileName = "Terminal Block Adapters";
                    break;
                case "Terminal Block Contacts":
                    fileName = "Terminal Block Contacts";
                    break;
                case "Wire to Board":
                    fileName = "Wire to Board";
                    break;
                case "USB, DVI, HDMI Connector Accessories":
                    fileName = "USB, DVI, HDMI Connector Accessories";
                    break;
                case "USB, DVI, HDMI Connector Adapters":
                    fileName = "USB, DVI, HDMI Connector Adapters";
                    break;
                case "USB, DVI, HDMI Connector Assemblies":
                    fileName = "USB, DVI, HDMI Connector Assemblies";
                    break;


                // Crystals, Oscillators, Resonators Categories
                case "Crystal, Oscillator, Resonator Accessories":
                    fileName = "Crystal, Oscillator, Resonator Accessories";
                    break;
                case "Crystals":
                    fileName = "Crystals";
                    break;
                case "Oscillators":
                    fileName = "Oscillators";
                    break;
                case "Pin Configurable/Selectable Oscillators":
                    fileName = "Pin Configurable-Selectable Oscillators";
                    break;
                case "Programmable Oscillators":
                    fileName = "Programmable Oscillators";
                    break;
                case "Resonators":
                    fileName = "Resonators";
                    break;
                case "Stand Alone Programmers":
                    fileName = "Stand Alone Programmers";
                    break;
                case "VCOs (Voltage Controlled Oscillators)":
                    fileName = "VCOs (Voltage Controlled Oscillators)";
                    break;

                // Inductors, Coils, Chokes Subcategories
                case "Adjustable Inductors":
                    fileName = "Adjustable Inductors";
                    break;
                case "Arrays, Signal Transformers":
                    fileName = "Arrays, Signal Transformers";
                    break;
                case "Inductors, Coils, Chokes Delay Lines":
                    fileName = "Inductors, Coils, Chokes Delay Lines Delay Lines";
                    break;
                case "Fixed Inductors":
                    fileName = "Fixed Inductors";
                    break;
                case "Wireless Charging Coils":
                    fileName = "Wireless Charging Coils";
                    break;

                // Integrated Circuits (ICs) Subcategories

                case "Audio Special Purpose":
                    fileName = "Audio_Special_Purpose";
                    break;
                case "Application Specific Clock/Timing":
                    fileName = "Application_Specific_Clock_Timing";
                    break;
                case "Clock Buffers, Drivers":
                    fileName = "Clock_Buffers_Drivers";
                    break;
                case "Clock Generators, PLLs, Frequency Synthesizers":
                    fileName = "Clock_Generators_PLLs_Frequency_Synthesizers";
                    break;
                case "Inductors, Coils, Chokes Delay Lines Delay Lines":
                    fileName = "Inductors, Coils, Chokes Delay Lines";
                    break;
                case "IC Batteries":
                    fileName = "IC_Batteries";
                    break;
                case "Programmable Timers and Oscillators":
                    fileName = "Programmable_Timers_and_Oscillators";
                    break;
                case "Real Time Clocks":
                    fileName = "Real_Time_Clocks";
                    break;
                case "ADCs/DACs - Special Purpose":
                    fileName = "ADCs_DACs_Special_Purpose";
                    break;
                case "Analog Front End (AFE)":
                    fileName = "Analog_Front_End_AFE";
                    break;
                case "Analog to Digital Converters (ADC)":
                    fileName = "Analog_to_Digital_Converters_ADC";
                    break;
                case "Digital Potentiometers":
                    fileName = "Digital_Potentiometers";
                    break;
                case "Digital to Analog Converters (DAC)":
                    fileName = "Digital_to_Analog_Converters_DAC";
                    break;
                case "Touch Screen Controllers":
                    fileName = "Touch_Screen_Controllers";
                    break;
                case "Application Specific Microcontrollers":
                    fileName = "Application_Specific_Microcontrollers";
                    break;
                case "CPLDs (Complex Programmable Logic Devices)":
                    fileName = "CPLDs_Complex_Programmable_Logic_Devices";
                    break;
                case "DSP (Digital Signal Processors)":
                    fileName = "DSP_Digital_Signal_Processors";
                    break;
                case "FPGAs (Field Programmable Gate Array)":
                    fileName = "FPGAs_Field_Programmable_Gate_Array";
                    break;
                case "FPGAs (Field Programmable Gate Array) with Microcontrollers":
                    fileName = "FPGAs_Field_Programmable_Gate_Array_with_Microcontrollers";
                    break;
                case "Microcontrollers":
                    fileName = "Microcontrollers";
                    break;
                case "Microcontrollers, Microprocessor, FPGA Modules":
                    fileName = "Microcontrollers_Microprocessor_FPGA_Modules";
                    break;
                case "Microprocessors":
                    fileName = "Microprocessors";
                    break;
                case "PLDs (Programmable Logic Device)":
                    fileName = "PLDs_Programmable_Logic_Device";
                    break;
                case "System On Chip (SoC)":
                    fileName = "System_On_Chip_SoC";
                    break;
                case "Analog Switches - Special Purpose":
                    fileName = "Analog_Switches_Special_Purpose";
                    break;
                case "Analog Switches, Multiplexers, Demultiplexers":
                    fileName = "Analog_Switches_Multiplexers_Demultiplexers";
                    break;
                case "CODECS":
                    fileName = "CODECS";
                    break;
                case "Interface Controllers":
                    fileName = "Interface Controllers";
                    break;
                case "Direct Digital Synthesis (DDS)":
                    fileName = "Direct_Digital_Synthesis_DDS";
                    break;
                case "Drivers, Receivers, Transceivers":
                    fileName = "Drivers_Receivers_Transceivers";
                    break;
                case "Encoders, Decoders, Converters":
                    fileName = "Encoders_Decoders_Converters";
                    break;
                case "Filters - Active":
                    fileName = "Filters_Active";
                    break;
                case "I/O Expanders":
                    fileName = "I_O_Expanders";
                    break;
                case "Modems - ICs and Modules":
                    fileName = "Modems_ICs_and_Modules";
                    break;
                case "Modules":
                    fileName = "Modules";
                    break;
                case "Sensor and Detector Interfaces":
                    fileName = "Sensor_and_Detector_Interfaces";
                    break;
                case "Sensor, Capacitive Touch":
                    fileName = "Sensor_Capacitive_Touch";
                    break;
                case "Serializers, Deserializers":
                    fileName = "Serializers_Deserializers";
                    break;
                case "Signal Buffers, Repeaters, Splitters":
                    fileName = "Signal_Buffers_Repeaters_Splitters";
                    break;
                case "Signal Terminators":
                    fileName = "Signal_Terminators";
                    break;
                case "Specialized":
                    fileName = "Specialized";
                    break;
                case "Telecom":
                    fileName = "Telecom";
                    break;
                case "UARTs (Universal Asynchronous Receiver Transmitter)":
                    fileName = "UARTs_Universal_Asynchronous_Receiver_Transmitter";
                    break;
                case "Voice Record and Playback":
                    fileName = "Voice_Record_and_Playback";
                    break;
                case "Amplifiers":
                    fileName = "Amplifiers";
                    break;
                case "Analog Multipliers, Dividers":
                    fileName = "Analog_Multipliers_Dividers";
                    break;
                case "Comparators":
                    fileName = "Comparators";
                    break;
                case "Video Processing":
                    fileName = "Video_Processing";
                    break;
                case "Buffers, Drivers, Receivers, Transceivers":
                    fileName = "Buffers_Drivers_Receivers_Transceivers";
                    break;
                case "Counters, Dividers":
                    fileName = "Counters_Dividers";
                    break;
                case "FIFOs Memory":
                    fileName = "FIFOs_Memory";
                    break;
                case "Flip Flops":
                    fileName = "Flip_Flops";
                    break;
                case "Gates and Inverters":
                    fileName = "Gates_and_Inverters";
                    break;
                case "Gates and Inverters - Multi-Function, Configurable":
                    fileName = "Gates_and_Inverters_Multi_Function_Configurable";
                    break;
                case "Latches":
                    fileName = "Latches";
                    break;
                case "Multivibrators":
                    fileName = "Multivibrators";
                    break;
                case "Parity Generators and Checkers":
                    fileName = "Parity_Generators_and_Checkers";
                    break;
                case "Shift Registers":
                    fileName = "Shift_Registers";
                    break;
                case "Signal Switches, Multiplexers, Decoders":
                    fileName = "Signal_Switches_Multiplexers_Decoders";
                    break;
                case "Specialty Logic":
                    fileName = "Specialty_Logic";
                    break;
                case "Translators, Level Shifters":
                    fileName = "Translators_Level_Shifters";
                    break;
                case "Universal Bus Functions":
                    fileName = "Universal_Bus_Functions";
                    break;
                case "Batteries":
                    fileName = "Batteries";
                    break;
                case "Configuration PROMs for FPGAs":
                    fileName = "Configuration_PROMs_for_FPGAs";
                    break;
                case "Memory Controllers":
                    fileName = "Memory Controllers";
                    break;
                case "Memory":
                    fileName = "Memory";
                    break;
                case "AC DC Converters, Offline Switches":
                    fileName = "AC_DC_Converters_Offline_Switches";
                    break;
                case "Battery Chargers":
                    fileName = "Battery_Chargers";
                    break;
                case "Battery Management":
                    fileName = "Battery_Management";
                    break;
                case "Current Regulation/Management":
                    fileName = "Current_Regulation_Management";
                    break;
                case "DC DC Switching Controllers":
                    fileName = "DC_DC_Switching_Controllers";
                    break;
                case "Display Drivers":
                    fileName = "Display_Drivers";
                    break;
                case "Energy Metering":
                    fileName = "Energy_Metering";
                    break;
                case "Full Half-Bridge (H Bridge) Drivers":
                    fileName = "Full_Half_Bridge_H_Bridge_Drivers";
                    break;
                case "Gate Drivers":
                    fileName = "Gate_Drivers";
                    break;
                case "Hot Swap Controllers":
                    fileName = "Hot_Swap_Controllers";
                    break;
                case "Laser Drivers":
                    fileName = "Laser_Drivers";
                    break;
                case "LED Drivers":
                    fileName = "LED_Drivers";
                    break;
                case "Lighting, Ballast Controllers":
                    fileName = "Lighting_Ballast_Controllers";
                    break;
                case "Motor Drivers, Controllers":
                    fileName = "Motor_Drivers_Controllers";
                    break;
                case "OR Controllers, Ideal Diodes":
                    fileName = "OR_Controllers_Ideal_Diodes";
                    break;
                case "PFC (Power Factor Correction)":
                    fileName = "PFC_Power_Factor_Correction";
                    break;
                case "Power Distribution Switches, Load Drivers":
                    fileName = "Power_Distribution_Switches_Load_Drivers";
                    break;
                case "Power Management - Specialized":
                    fileName = "Power_Management_Specialized";
                    break;
                case "Power Over Ethernet (PoE) Controllers":
                    fileName = "Power_Over_Ethernet_PoE_Controllers";
                    break;
                case "Power Supply Controllers, Monitors":
                    fileName = "Power_Supply_Controllers_Monitors";
                    break;
                case "RMS to DC Converters":
                    fileName = "RMS_to_DC_Converters";
                    break;
                case "Special Purpose Regulators":
                    fileName = "Special_Purpose_Regulators";
                    break;
                case "Supervisors":
                    fileName = "Supervisors";
                    break;
                case "Thermal Management":
                    fileName = "Thermal_Management";
                    break;
                case "V/F and F/V Converters":
                    fileName = "V_F_and_F_V_Converters";
                    break;
                case "Voltage Reference":
                    fileName = "Voltage_Reference";
                    break;
                case "Voltage Regulators - DC DC Switching Regulators":
                    fileName = "Voltage_Regulators_DC_DC_Switching_Regulators";
                    break;
                case "Voltage Regulators - Linear + Switching":
                    fileName = "Voltage_Regulators_Linear_Switching";
                    break;
                case "Voltage Regulators - Linear Regulator Controllers":
                    fileName = "Voltage_Regulators_Linear_Regulator_Controllers";
                    break;
                case "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators":
                    fileName = "Voltage_Regulators_Linear_Low_Drop_Out_LDO_Regulators";
                    break;
                case "Specialized ICs":
                    fileName = "Specialized_ICs";
                    break;


                // Potentiometers, Variable Resistors Subcategories
                case "Potentiometers, Variable Resistors Accessories":
                    fileName = "Potentiometers, Variable Resistors Accessories";
                    break;
                case "Adjustable Power Resistor":
                    fileName = "Adjustable Power Resistor";
                    break;
                case "Joystick Potentiometers":
                    fileName = "Joystick Potentiometers";
                    break;
                case "Rotary Potentiometers, Rheostats":
                    fileName = "Rotary Potentiometers, Rheostats";
                    break;
                case "Scale Dials":
                    fileName = "Scale Dials";
                    break;
                case "Slide Potentiometers":
                    fileName = "Slide Potentiometers";
                    break;
                case "Thumbwheel Potentiometers":
                    fileName = "Thumbwheel Potentiometers";
                    break;
                case "Trimmer Potentiometers":
                    fileName = "Trimmer Potentiometers";
                    break;
                case "Value Display Potentiometers":
                    fileName = "Value Display Potentiometers";
                    break;


                // Relays Subcategories
                case "Relays Accessories":
                    fileName = "Relays Accessories";
                    break;
                case "Automotive Relays":
                    fileName = "Automotive Relays";
                    break;
                case "Contactors (Electromechanical)":
                    fileName = "Contactors (Electromechanical)";
                    break;
                case "Contactors (Solid State)":
                    fileName = "Contactors (Solid State)";
                    break;
                case "High Frequency (RF) Relays":
                    fileName = "High Frequency (RF) Relays";
                    break;
                case "I/O Relay Module Racks":
                    fileName = "IO Relay Module Racks";
                    break;
                case "I/O Relay Modules":
                    fileName = "IO Relay Modules";
                    break;
                case "Power Relays, Over 2 Amps":
                    fileName = "Power Relays, Over 2 Amps";
                    break;
                case "Reed Relays":
                    fileName = "Reed Relays";
                    break;
                case "Relay Sockets":
                    fileName = "Relay Sockets";
                    break;
                case "Safety Relays":
                    fileName = "Safety Relays";
                    break;
                case "Signal Relays, Up to 2 Amps":
                    fileName = "Signal Relays, Up to 2 Amps";
                    break;
                case "Solid State Relays":
                    fileName = "Solid State Relays";
                    break;
                // Resistors Subcategories
                case "Resistor Accessories":
                    fileName = "Resistor Accessories";
                    break;
                case "Chassis Mount Resistors":
                    fileName = "Chassis Mount Resistors";
                    break;
                case "Chip Resistor - Surface Mount":
                    fileName = "Chip Resistor - Surface Mount";
                    break;
                case "Precision Trimmed Resistors":
                    fileName = "Precision Trimmed Resistors";
                    break;
                case "Resistor Networks, Arrays":
                    fileName = "Resistor Networks, Arrays";
                    break;
                case "Specialized Resistors":
                    fileName = "Specialized Resistors";
                    break;
                case "Through Hole Resistors":
                    fileName = "Through Hole Resistors";
                    break;

                    // Switches Subcategories
                case "Switches Accessories":
                    fileName = "Switches Accessories";
                    break;
                case "Accessories - Boots, Seals":
                    fileName = "Accessories - Boots, Seals";
                    break;
                case "Accessories - Caps":
                    fileName = "Accessories - Caps";
                    break;
                case "Configurable Switch Bodies":
                    fileName = "Configurable Switch Bodies";
                    break;
                case "Configurable Switch Contact Blocks":
                    fileName = "Configurable Switch Contact Blocks";
                    break;
                case "Configurable Switch Illumination Sources":
                    fileName = "Configurable Switch Illumination Sources";
                    break;
                    case "Configurable Switch Lens":
                    fileName = "Configurable Switch Lens";
                    break;
                case "DIP Switches":
                    fileName = "DIP Switches";
                    break;
                case "Disconnect Switch Components":
                    fileName = "Disconnect Switch Components";
                    break;
                case "Emergency Stop (E-Stop) Switches":
                    fileName = "Emergency Stop (E-Stop) Switches";
                    break;
                case "Interlock Switches":
                    fileName = "Interlock Switches";
                    break;
                case "Keylock Switches":
                    fileName = "Keylock Switches";
                    break;
                case "Keypad Switches":
                    fileName = "Keypad Switches";
                    break;
                case "Limit Switches":
                    fileName = "Limit Switches";
                    break;
                case "Magnetic, Reed Switches":
                    fileName = "Magnetic, Reed Switches";
                    break;
                case "Navigation Switches, Joystick":
                    fileName = "Navigation Switches, Joystick";
                    break;
                case "Programmable Display Switches":
                    fileName = "Programmable Display Switches";
                    break;
                case "Pushbutton Switches":
                    fileName = "Pushbutton Switches";
                    break;
                case "Pushbutton Switches - Hall Effect":
                    fileName = "Pushbutton Switches - Hall Effect";
                    break;
                case "Rocker Switches":
                    fileName = "Rocker Switches";
                    break;
                case "Rotary Switches":
                    fileName = "Rotary Switches";
                    break;
                case "Selector Switches":
                    fileName = "Selector Switches";
                    break;
                case "Slide Switches":
                    fileName = "Slide Switches";
                    break;
                case "Tactile Switches":
                    fileName = "Tactile Switches";
                    break;
                case "Thumbwheel Switches":
                    fileName = "Thumbwheel Switches";
                    break;
                case "Toggle Switches":
                    fileName = "Toggle Switches";
                    break;

                default:
                    System.out.println("Keyword selected for search does not match expected category name. Default file name will be provided.");
                    System.out.println("File named: Default.json");
                    fileName = "Default";
            }

            String fileFolder;

            switch (KEYWORD) {
                // Capacitors subcategories
                case "Capacitor Accessories":
                case "Aluminum - Polymer Capacitors":
                case "Aluminum Electrolytic Capacitors":
                case "Capacitor Networks, Arrays":
                case "Ceramic Capacitors":
                case "Electric Double Layer Capacitors (EDLC), Supercapacitors":
                case "Film Capacitors":
                case "Mica and PTFE Capacitors":
                case "Niobium Oxide Capacitors":
                case "Silicon Capacitors":
                case "Tantalum - Polymer Capacitors":
                case "Tantalum Capacitors":
                case "Thin Film Capacitors":
                case "Trimmers, Variable Capacitors":
                    fileFolder = "Capacitors/";
                    break;

                // Connectors, Interconnects subcategories
                case "Plugs and Receptacles":
                case "Power Entry Connector Accessories":
                case "Power Entry Modules (PEM)":
                case "ARINC Inserts":
                case "Backplane Connector Accessories":
                case "Backplane Connector Contacts":
                case "Backplane Connector Housings":
                case "DIN 41612":
                case "Hard Metric, Standard":
                case "Backplane Connectors Specialized":
                case "Banana and Tip Connector Accessories":
                case "Banana and Tip Connector Adapters":
                case "Binding Posts":
                case "Jacks, Plugs":
                case "Audio Connectors":
                case "Barrel Connector Accessories":
                case "Barrel Connector Adapters":
                case "Power Connectors":
                case "Blade Type Power Connector Accessories":
                case "Blade Type Power Connector Assemblies":
                case "Blade Type Power Connector Contacts":
                case "Blade Type Power Connector Housings":
                case "Card Edge Connector Accessories":
                case "Card Edge Connector Adapters":
                case "Card Edge Connector Contacts":
                case "Card Edge Connector Housings":
                case "Edgeboard Connectors":
                case "Backshells and Cable Clamps":
                case "Circular Connector Accessories":
                case "Circular Connector Adapters":
                case "Circular Connector Assemblies":
                case "Circular Connector Contacts":
                case "Circular Connector Housings":
                case "Coaxial Connector (RF) Accessories":
                case "Coaxial Connector (RF) Adapters":
                case "Coaxial Connector (RF) Assemblies":
                case "Coaxial Connector (RF) Contacts":
                case "Coaxial Connector (RF) Terminators":
                case "Contacts, Spring Loaded (Pogo Pins), and Pressure":
                case "Leadframe":
                case "Multi Purpose":
                case "Centronics Connectors":
                case "D-Sub Connector Assemblies":
                case "D-Sub, D-Shaped Connector Accessories":
                case "D-Sub, D-Shaped Connector Adapters":
                case "D-Sub, D-Shaped Connector Backshells, Hoods":
                case "D-Sub, D-Shaped Connector Contacts":
                case "D-Sub, D-Shaped Connector Housings":
                case "D-Sub, D-Shaped Connector Jackscrews":
                case "D-Sub, D-Shaped Connector Terminators":
                case "FFC, FPC (Flat Flexible) Connector Accessories":
                case "FFC, FPC (Flat Flexible) Connector Assemblies":
                case "FFC, FPC (Flat Flexible) Connector Contacts":
                case "FFC, FPC (Flat Flexible) Connector Housings":
                case "Fiber Optic Connector Accessories":
                case "Fiber Optic Connector Adapters":
                case "Fiber Optic Connector Assemblies":
                case "Fiber Optic Connector Housings":
                case "Heavy Duty Connector Accessories":
                case "Heavy Duty Connector Assemblies":
                case "Heavy Duty Connector Contacts":
                case "Heavy Duty Connector Frames":
                case "Heavy Duty Connector Housings, Hoods, Bases":
                case "Heavy Duty Connector Inserts, Modules":
                case "Keystone Connector Accessories":
                case "Keystone Faceplates, Frames":
                case "Keystone Inserts":
                case "Inline Module Sockets":
                case "Memory Connector Accessories":
                case "PC Card Sockets":
                case "PC Cards - Adapters":
                case "Modular Connector Accessories":
                case "Modular Connector Adapters":
                case "Modular Connector Jacks":
                case "Modular Connector Jacks With Magnetics":
                case "Modular Connector Plug Housings":
                case "Modular Connector Plugs":
                case "Modular Connector Wiring Blocks":
                case "Modular Connector Wiring Blocks Accessories":
                case "Photovoltaic (Solar Panel) Connector Accessories":
                case "Photovoltaic (Solar Panel) Connector Assemblies":
                case "Photovoltaic (Solar Panel) Connector Contacts":
                case "Pluggable Connector Accessories":
                case "Pluggable Connector Assemblies":
                case "Rectangular Connector Accessories":
                case "Rectangular Connector Adapters":
                case "Rectangular Connector Contacts":
                case "Rectangular Connector Housings":
                case "Spring Loaded":
                case "IC Sockets":
                case "Socket Accessories":
                case "Socket Adapters":
                case "Solid State Lighting Connector Accessories":
                case "Solid State Lighting Connector Assemblies":
                case "Solid State Lighting Connector Contacts":
                case "Barrier Blocks":
                case "Din Rail, Channel":
                case "Headers, Plugs and Sockets":
                case "Interface Modules":
                case "Panel Mount":
                case "Power Distribution":
                case "Terminal Blocks Specialized":
                case "Terminal Block Accessories":
                case "Terminal Block Adapters":
                case "Terminal Block Contacts":
                case "Wire to Board":
                case "USB, DVI, HDMI Connector Accessories":
                case "USB, DVI, HDMI Connector Adapters":
                case "USB, DVI, HDMI Connector Assemblies":
                    fileFolder = "Connectors, Interconnects/";
                    break;

                // Crystal, Oscillators, Resonators subcategories
                case "Crystal, Oscillator, Resonator Accessories":
                case "Crystals":
                case "Oscillators":
                case "Pin Configurable-Selectable Oscillators":
                case "Programmable Oscillators":
                case "Resonators":
                case "Stand Alone Programmers":
                case "VCOs (Voltage Controlled Oscillators)":
                    fileFolder = "Crystals, Oscillators, Resonators/";
                    break;

                    // Inductors, Coil, Chokes subcategories

                case "Adjustable Inductors":
                case "Arrays, Signal Transformers":
                case "Inductors, Coils, Chokes Delay Lines":
                case "Fixed Inductors":
                case "Wireless Charging Coils":
                    fileFolder = "Inductors, Coils, Chokes/";
                    break;

                // Integrated Circuits (ICs) subcategories
                case "Audio Special Purpose":
                case "Application Specific Clock/Timing":
                case "Clock Buffers, Drivers":
                case "Clock Generators, PLLs, Frequency Synthesizers":
                case "Delay Lines":
                case "IC Batteries":
                case "Programmable Timers and Oscillators":
                case "Real Time Clocks":
                case "ADCs/DACs - Special Purpose":
                case "Analog Front End (AFE)":
                case "Analog to Digital Converters (ADC)":
                case "Digital Potentiometers":
                case "Digital to Analog Converters (DAC)":
                case "Touch Screen Controllers":
                case "Application Specific Microcontrollers":
                case "CPLDs (Complex Programmable Logic Devices)":
                case "DSP (Digital Signal Processors)":
                case "FPGAs (Field Programmable Gate Array)":
                case "FPGAs (Field Programmable Gate Array) with Microcontrollers":
                case "Microcontrollers":
                case "Microcontrollers, Microprocessor, FPGA Modules":
                case "Microprocessors":
                case "PLDs (Programmable Logic Device)":
                case "System On Chip (SoC)":
                case "Analog Switches - Special Purpose":
                case "Analog Switches, Multiplexers, Demultiplexers":
                case "CODECS":
                case "Interface Controllers":
                case "Direct Digital Synthesis (DDS)":
                case "Drivers, Receivers, Transceivers":
                case "Encoders, Decoders, Converters":
                case "Filters - Active":
                case "I/O Expanders":
                case "Modems - ICs and Modules":
                case "Modules":
                case "Sensor and Detector Interfaces":
                case "Sensor, Capacitive Touch":
                case "Serializers, Deserializers":
                case "Signal Buffers, Repeaters, Splitters":
                case "Signal Terminators":
                case "Specialized":
                case "Telecom":
                case "UARTs (Universal Asynchronous Receiver Transmitter)":
                case "Voice Record and Playback":
                case "Amplifiers":
                case "Analog Multipliers, Dividers":
                case "Comparators":
                case "Video Processing":
                case "Buffers, Drivers, Receivers, Transceivers":
                case "Counters, Dividers":
                case "FIFOs Memory":
                case "Flip Flops":
                case "Gates and Inverters":
                case "Gates and Inverters - Multi-Function, Configurable":
                case "Latches":
                case "Multivibrators":
                case "Parity Generators and Checkers":
                case "Shift Registers":
                case "Signal Switches, Multiplexers, Decoders":
                case "Specialty Logic":
                case "Translators, Level Shifters":
                case "Universal Bus Functions":
                case "Batteries":
                case "Configuration PROMs for FPGAs":
                case "Memory Controllers":
                case "Memory":
                case "AC DC Converters, Offline Switches":
                case "Battery Chargers":
                case "Battery Management":
                case "Current Regulation/Management":
                case "DC DC Switching Controllers":
                case "Display Drivers":
                case "Energy Metering":
                case "Full Half-Bridge (H Bridge) Drivers":
                case "Gate Drivers":
                case "Hot Swap Controllers":
                case "Laser Drivers":
                case "LED Drivers":
                case "Lighting, Ballast Controllers":
                case "Motor Drivers, Controllers":
                case "OR Controllers, Ideal Diodes":
                case "PFC (Power Factor Correction)":
                case "Power Distribution Switches, Load Drivers":
                case "Power Management - Specialized":
                case "Power Over Ethernet (PoE) Controllers":
                case "Power Supply Controllers, Monitors":
                case "RMS to DC Converters":
                case "Special Purpose Regulators":
                case "Supervisors":
                case "Thermal Management":
                case "V/F and F/V Converters":
                case "Voltage Reference":
                case "Voltage Regulators - DC DC Switching Regulators":
                case "Voltage Regulators - Linear + Switching":
                case "Voltage Regulators - Linear Regulator Controllers":
                case "Voltage Regulators - Linear, Low Drop Out (LDO) Regulators":
                case "Specialized ICs":
                    fileFolder = "Integrated Circuits (ICs)/";
                    break;

                // Potentiometers, Variable Resistors subcategories
                case "Potentiometers, Variable Resistors Accessories":
                case "Adjustable Power Resistor":
                case "Joystick Potentiometers":
                case "Rotary Potentiometers, Rheostats":
                case "Scale Dials":
                case "Slide Potentiometers":
                case "Thumbwheel Potentiometers":
                case "Trimmer Potentiometers":
                case "Value Display Potentiometers":
                    fileFolder = "Potentiometers, Variable Resistors/";
                    break;

                // Relays subcategories
                case "Relays Accessories":
                case "Automotive Relays":
                case "Contactors (Electromechanical)":
                case "Contactors (Solid State)":
                case "High Frequency (RF) Relays":
                case "I/O Relay Module Racks":
                case "I/O Relay Modules":
                case "Power Relays, Over 2 Amps":
                case "Reed Relays":
                case "Relay Sockets":
                case "Safety Relays":
                case "Signal Relays, Up to 2 Amps":
                case "Solid State Relays":
                    fileFolder = "Relays/";
                    break;


                    // Resistor subcategories
                case "Resistor Accessories":
                case "Chassis Mount Resistors":
                case "Chip Resistor - Surface Mount":
                case "Precision Trimmed Resistors":
                case "Resistor Networks, Arrays":
                case "Specialized Resistors":
                case "Through Hole Resistors":
                case "Accessories":
                    fileFolder = "Resistors/";
                    break;

                    // Switches subcategories
                case "Switches Accessories":
                case "Accessories - Boots, Seals":
                case "Accessories - Caps":
                case "Cable Pull Switches":
                case "Configurable Switch Bodies":
                case "Configurable Switch Contact Blocks":
                case "Configurable Switch Illumination Sources":
                case "Configurable Switch Lens":
                case "DIP Switches":
                case "Disconnect Switch Components":
                case "Emergency Stop (E-Stop) Switches":
                case "Interlock Switches":
                case "Keylock Switches":
                case "Keypad Switches":
                case "Limit Switches":
                case "Magnetic, Reed Switches":
                case "Navigation Switches, Joystick":
                case "Programmable Display Switches":
                case "Pushbutton Switches":
                case "Pushbutton Switches - Hall Effect":
                case "Rocker Switches":
                case "Rotary Switches":
                case "Selector Switches":
                case "Slide Switches":
                case "Tactile Switches":
                case "Thumbwheel Switches":
                case "Toggle Switches":
                    fileFolder = "Switches/";
                    break;
                default:
                    fileFolder = "";
            }

            String filePath = "Postman Exports/" + fileFolder + fileName + ".json";
            File fileCheck = new File(filePath);
            int productIndex = 1;


            int startIndex = 0;
            try {
                if (fileCheck.exists()) {
                    System.out.println("File exists");
                 // Locating the last response body inputted and get the offset value
                    productIndex = arrayCount(filePath) + 1;
//                    System.out.println(arrayCount(filePath));
                    // update the offset value for new entries
                    offset = (productIndex * 50) - 50;


                }
                else {
                    // Insert template into the file
                    System.out.println("File does not exist. Creating new file.");
                    insertTemplate(filePath);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }




//            int apiCount = 0;
//
//
//            do {
//
//
//            } while (apiCount == 1000);

            // Insert GetAccesToken here


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

            /*
                The variable below extracts the access_token value
             */
            String newAccessToken = accessToken.substring(43, 71);



            while (true) {
                // Send API requests with current offset
                String responseBody = sendPostRequest(API_URL, newAccessToken, offset, limit, KEYWORD);

                String prefix = responseBody.substring(0, 10);
                String suffix = responseBody.substring(10);
                String indexedResponseBody = prefix + Integer.toString(productIndex) + suffix;

                // When responseBody has no more content, break while loop
                if (suffix.substring(0,4).equals("\":[]")) {
                    break;
                }
                if (prefix.substring(0,8).equals("{\"fault\"")) {
                    System.out.println("""
                            Execution of ServiceCallout SC-Quota failed

                            Resource Limitations: Your system or the API server may have resource limitations that are exceeded when handling a large amount of data.
                            Rate Limiting: Some APIs impose rate limits to prevent abuse. If you exceed the allowed rate, you might encounter rate-limiting errors.""");
                    break;
                }
                if (!prefix.equals("{\"Products")) {
                    System.out.println(responseBody);
                    break;
                }

                insertNewLines(filePath, indexedResponseBody);
                productIndex++;

                // Process the current page of results
                System.out.println("API Response (Offset: " + offset + "):");
//                System.out.println(responseBody);

                // Check if there are more results
                if (!hasNextPage(indexedResponseBody, limit)) {
                    break; // Exit the loop if there are no more pages
                }

                // Increment offset for the next page
                offset += limit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int arrayCount(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray products = null;
        Object obj = parser.parse(new FileReader(filePath));
        org.json.simple.JSONObject jsonObject = (JSONObject) obj;

        products = (JSONArray) jsonObject.get("Products");
        Iterator iteratorProducts = products.iterator();
        int arrayCount = products.size();

        return arrayCount;
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

    private static boolean hasNextPage(String responseBody, int limit) {
        // Implement logic to check if there are more pages based on the structure of the API response
        // You need to analyze the API response format to determine if there are more results.
        // For example, you may check if the number of returned items is less than the specified limit.

        // Placeholder logic (adjust based on the actual API response structure):
        return responseBody.length() >= limit;
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