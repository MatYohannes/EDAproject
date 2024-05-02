import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;

public class membershipTable {
    public static Object[][] createCustomTable(Object[][] originalTable, Connection connection) throws FileNotFoundException, ParseException, SQLException {
        int rows = originalTable.length;
        Object[][] customTable = new Object[rows][4];
        int customID = 0;


        String command = "SELECT custom_id FROM edadb.membership ORDER BY custom_id DESC LIMIT 1;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(command);

            if (resultSet.next()) {
                int previousCount = resultSet.getInt("custom_id");
                if (previousCount != 0) {
                    customID = previousCount;
                }
            }
        }
        // incrementing to either set to 1 or to read the table length then add 1
        customID++;

        for (int i = 0; i < rows; i++) {
            // Assuming the first column is "manID", second is "manProductNumber", and third is "productParName"
            customTable[i][0] = customID;  // Custom ID
            customTable[i][1] = originalTable[i][0]; // manID
            customTable[i][2] = originalTable[i][1]; // manufacture
            customTable[i][3] = originalTable[i][2]; // manProductNumber
            customID++;
        }
        return customTable;
    }
}
