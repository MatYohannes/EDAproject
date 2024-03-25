import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class characteristicsTable {
    public static Object[][] createCustomTable(Object[][] membershipTable, Object[][] originalTable,
                                               List<String> membershipHeaders, List<String> originalHeaders) {
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
        String value= null;
        String attribute = null;
        String customID = null;

        for (int row = 0; row < originalTable.length; row++) {
            for (int col = 9; col < originalTable[row].length; col++) {
                if (originalTable[row][col] != null && !originalTable[row][col].toString().equals("") &&
                !originalTable[row][col].toString().equals("0") && !originalTable[row][col].toString().equals("-")) {

                    value = originalTable[row][col].toString();
                    attribute = originalHeaders.get(col);
                    String manufacturerPartID = originalTable[row][2].toString();

                    if (manufacturerPartID.equals(membershipTable[row][2])) {
                        customID = membershipTable[row][0].toString();
                    }
                    Object[] thirdTableRow = {customID, attribute, value};
                    thirdTableList.add(thirdTableRow);
                }
            }
        }
        // Convert the list to a 2D array
        Object[][] thirdTable = new Object[thirdTableList.size()][];
        thirdTableList.toArray(thirdTable);

        return thirdTable;
    }
}

