import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class characteristics2Table {
    public static List<Characteristics> createCustomTable(Object[][] originalTable,
                                               List<String> membershipHeaders, List<String> originalHeaders, Connection connection) throws SQLException {
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

        // Generate the third table
        String value;
        String attribute;

        for (int row = 0; row < originalTable.length; row++) {
            for (int col = 9; col < originalTable[row].length; col++) {

                if (originalTable[row][col] != null && !originalTable[row][col].toString().equals("") &&
                !originalTable[row][col].toString().equals("0") && !originalTable[row][col].toString().equals("-")) {

                    value = originalTable[row][col].toString();
                    attribute = originalHeaders.get(col);
                    String manufacturerPartID = originalTable[row][2].toString();

                    int customID = 0;

                    String command = "SELECT custom_id FROM edadb.membership WHERE manufacturer_part_num=" + "\"" + manufacturerPartID +"\"";
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery(command);
                        if (resultSet.next()) {
                            int idNumber = resultSet.getInt("custom_id");
                            if (idNumber != 0) {
                                customID = idNumber;
                                Characteristics character = new Characteristics();
                                character.setCustomId(customID);
                                character.setAttributes(attribute);
                                character.setValue(value);
                                finalTable.add(character);
                            }
                        }
                    }
                }
            }
        }
        return finalTable;
    }
}

