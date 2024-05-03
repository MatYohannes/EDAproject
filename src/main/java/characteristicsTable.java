import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class characteristicsTable {
    private int customId;
    private String attributes;
    private String value;

    public characteristicsTable(int customId, String attributes, String value) {
        this.customId = customId;
        this.attributes = attributes;
        this.value = value;
    }

    // Getters and setters
    public int getCustomId() { return customId;}
    public void setCustomId(int customId) {this.customId = customId;}
    public String getAttributes() {return attributes;}
    public void setAttributes(String attributes) {this.attributes = attributes;}
    public String getValue() {return value;}
    public void setValue(String value) {this.value = value;}


    public static Object[][] createCustomTable(Object[][] originalTable,
                                               List<String> membershipHeaders, List<String> originalHeaders, Connection connection) throws SQLException {
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
        List<Object[]> thirdTableList = new ArrayList<>();
        String value;
        String attribute;

        for (int row = 0; row < originalTable.length; row++) {
            for (int col = 9; col < originalTable[row].length; col++) {
                if (originalTable[row][col] != null && !originalTable[row][col].toString().equals("") &&
                !originalTable[row][col].toString().equals("0") && !originalTable[row][col].toString().equals("-")) {

                    value = originalTable[row][col].toString();
                    attribute = originalHeaders.get(col);
                    String manufacturerPartID = originalTable[row][2].toString();

                    String customID = null;


                    String command = "SELECT custom_id FROM edadb.membership WHERE manufacturer_part_num=" + "\"" + manufacturerPartID +"\"";
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery(command);
                        if (resultSet.next()) {
                            int idNumber = resultSet.getInt("custom_id");
                            if (idNumber != 0) {
                                customID = Integer.toString(idNumber);
                            }
                        }

                        //
//                    if (manufacturerPartID.equals(membershipTable[row][2])) {
//                        customID = membershipTable[row][0].toString();
//                    }

                        Object[] thirdTableRow = {customID, attribute, value};
                        thirdTableList.add(thirdTableRow);
                    }
                }
            }
        }
        // Convert the list to a 2D array
        Object[][] thirdTable = new Object[thirdTableList.size()][];
        thirdTableList.toArray(thirdTable);

        return thirdTable;
    }
}

