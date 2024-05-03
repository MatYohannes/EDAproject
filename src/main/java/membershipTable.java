import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a static method createCustomTable that takes an original table
 * and a database connection as input parameters and returns a custom table
 * based on the original one. The method retrieves the highest custom ID from the
 * membership table in the database, increments it, and then creates custom rows
 * by iterating through the original table.
 */
public class membershipTable {

    /**
     * Creates a custom membership table based on the original table.
     *
     * @param originalTable The original table from which to create the custom table.
     * @param connection    The database connection.
     * @return A list of Object arrays representing the custom table.
     * @throws SQLException If a SQL error occurs during the execution of the command.
     */
    public static List<Object[]> createCustomTable(List<Object[]> originalTable, Connection connection) throws SQLException {
        List<Object[]> customTable = new ArrayList<>();
        int customID = 0;

        // SQL command to get the highest custom ID from the membership table
        String command = "SELECT custom_id FROM edadb.membership ORDER BY custom_id DESC LIMIT 1;";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(command);

            // Retrieve the highest custom ID from the result set
            if (resultSet.next()) {
                int previousCount = resultSet.getInt("custom_id");
                if (previousCount != 0) {
                    customID = previousCount;
                }
            }
        }
        // incrementing to either set to 1 or to read the table length then add 1
        customID++;

        // Iterate through the original table and create custom rows
        for (Object[] row : originalTable) {
            Object[] customRow = new Object[4];
            customRow[0] = customID; // Custom ID
            customRow[1] = row[0]; // manID
            customRow[2] = row[1]; // manufacture
            customRow[3] = row[2]; // manProductNumber
            customTable.add(customRow);
            customID++;
        }
        return customTable;
    }
}