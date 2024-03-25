
public class membershipTable {
    public static Object[][] createCustomTable(Object[][] originalTable) {
        int rows = originalTable.length;
        Object[][] customTable = new Object[rows][4];

        int customID = 1;

        for (int i = 0; i < rows; i++) {
            // Assuming the first column is "manID", second is "manProductNumber", and third is "productParName"
            customTable[i][0] = customID;  // Custom ID
            customTable[i][1] = originalTable[i][0]; // manID
            customTable[i][2] = originalTable[i][2]; // manProductNumber
            customTable[i][3] = originalTable[i][8]; // productParName
            customID++;
        }

        return customTable;
    }


}
