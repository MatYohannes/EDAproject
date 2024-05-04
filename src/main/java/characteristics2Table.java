import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains a static method createCustomTable that takes an original table,
 * membership headers, original headers, and a database connection as input parameters
 * and returns a custom characteristics table based on the original tables. The method iterates
 * through the original table, retrieves the custom ID based on the manufacturer part ID
 * from the membership table, and creates custom rows for the characteristics table.
 */
public class characteristics2Table {

    /**
     * Creates a custom characteristics table based on original tables.
     * Column names : { custom_id, attribute_name, value }
     * @param originalTable     The original table from which to create the custom table.
     * @param membershipHeaders Headers of the membership table. SQL Column names.
     * @param originalHeaders   Headers of the original table.
     * @param connection        The JDBC database connection.
     * @return A list of Characteristics representing the custom table that matches the SQL table.
     * @throws SQLException If a SQL error occurs during the execution of the command.
     */
    public static List<Characteristics> createCustomTable(List<Object[]> originalTable,
                                                          List<String> membershipHeaders,
                                                          List<String> originalHeaders,
                                                          Connection connection) throws SQLException {
        List<Characteristics> finalTable = new ArrayList<>();

        // Index mapping for first table headers
        Map<String, Integer> firstTableIndexMap = new HashMap<>();
        for (int i = 0; i < membershipHeaders.size(); i++) {
            firstTableIndexMap.put(membershipHeaders.get(i), i);
        }

        // Index mapping for second table headers
        Map<String, Integer> secondTableIndexMap = new HashMap<>();
        for (int i = 0; i < originalHeaders.size(); i++) {
            secondTableIndexMap.put(originalHeaders.get(i), i);
        }

        // Query to retrieve custom IDs for all manufacturer part IDs from membership table
        Map<String, Integer> customIdMap = new HashMap<>();
        String command = "SELECT manufacturer_part_num, custom_id FROM edadb.membership WHERE manufacturer_part_num IN (";
        for (Object[] originalRow : originalTable) {
            String manufacturerPartID = (String) originalRow[2];
            command += "\"" + manufacturerPartID + "\",";
        }
        // Remove the last comma and close the SQL statement
        command = command.substring(0, command.length() - 1) + ")";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                String manufacturerPartID = resultSet.getString("manufacturer_part_num");
                int customID = resultSet.getInt("custom_id");
                customIdMap.put(manufacturerPartID, customID);
            }
        }

        // Generate the third table
        for (Object[] originalRow : originalTable) {
            for (int col = 9; col < originalRow.length; col++) {
                if (originalRow[col] != null && !originalRow[col].toString().isEmpty() &&
                        !originalRow[col].toString().equals("0") && !originalRow[col].toString().equals("-")) {

                    String value = originalRow[col].toString();
                    String attribute = originalHeaders.get(col);
                    String manufacturerPartID = (String) originalRow[2];
                    int customID = customIdMap.getOrDefault(manufacturerPartID, 0);
                    if (customID != 0) {
                        Characteristics character = new Characteristics();
                        character.setCustomId(customID);
                        character.setAttributes(attribute);
                        character.setValue(value);
                        finalTable.add(character);
                    }
                }
            }
        }
        return finalTable;
    }
}

