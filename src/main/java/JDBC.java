import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * This code provides a JDBC (Java Database Connectivity) class for interacting with a MySQL database.
 * It includes methods for establishing and closing a database connection, inserting data into various tables,
 * removing SQL safety settings, and clearing data from tables.
 */

public class JDBC {
    private static final String USERNAME = "edadb";
    private static final String PASSWORD = "3d@db"; // // Password for database access
    private static final String IPADDRESS = "130.166.160.20"; // IP address of the database server
    private static final int PORT = 3306; // Port number for database connection
    private static final String DATABASES = "edadb"; // Name of the database
    private static final String URL = "jdbc:mysql://" + IPADDRESS + ":" + PORT + "/" + DATABASES; // JDBC URL
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // JDBC driver class name

    private static Connection connection = null;

    /**
     * Gets the database connection object.
     * @return Connection object.
     */
    public Connection Connection() {
        return connection;
    }

    /**
     * Constructor for JDBC class. Initializes the database connection.
     * @throws FileNotFoundException If the driver class is not found.
     * @throws ParseException If an error occurs during parsing.
     */
    public JDBC() throws FileNotFoundException, ParseException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establish database connection

            // Check if connection is established
            if (connection == null) {
                System.out.println("Database Connection is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Database Connection Initialized.");
    }

    /**
     * closes Java DataBase connection
     */
    public void closeConnection() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
            connection = null;
            System.out.println("Connection closed.");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts data into the 'membership' table.
     * Column names : { custom_id, category_id, manufacturer, manufacturer_part_num }
     * @param table A list of Object arrays representing rows of data to be inserted. Each row of data
     *              contains details on individual items with all their data.
     * @throws SQLException If an SQL error occurs during the insertion process.
     */
    public void insertMembership(List<Object[]> table) throws SQLException {
        String command = "INSERT INTO `edadb`.`membership` (`custom_id`, `category_id`, `manufacturer`," +
                " `manufacturer_part_num`) VALUES (?, ?, ?, ?);";
        System.out.println("Inserting into Membership SQL Table.");
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            for (Object[] row: table) {
                addstmt.setObject(1, row[0]);
                addstmt.setObject(2, row[1]);
                addstmt.setObject(3, row[2]);
                addstmt.setObject(4, row[3]);
                addstmt.executeUpdate();
            }
            System.out.println("Insertion to Membership SQL table complete.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to Membership sql table.");
            err.printStackTrace();
        }
    }

    /**
     * Inserts data into the 'characteristics' table.
     * Column names : { custom_id, attribute_name, value }
     * @param table A list of Characteristics objects representing rows of data to be inserted.
     * @throws SQLException If an SQL error occurs during the insertion process.
     */
    public void insertCharacteristics(List<Characteristics> table) throws SQLException {
        String command = "INSERT INTO `edadb`.`characteristics` (`custom_id`, `attribute_name`, `value`) VALUES (?, ?, ?);";
        System.out.println("Inserting into Characteristics SQL Table.");
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            for (Characteristics row: table) {
                try {
                    addstmt.setObject(1, row.getCustomId());
                    addstmt.setObject(2, row.getAttributes());
                    addstmt.setObject(3, row.getValue());
                    addstmt.executeUpdate();

                } catch (SQLIntegrityConstraintViolationException e) {
                    // Ignore duplicate entry and continue to the next row
                    System.out.println("Duplicate entry. Ignoring row and continuing to next row.");
                }

            }
            System.out.println("Insertion to Characteristics SQL table complete.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to Characteristics sql table.");
            err.printStackTrace();
        }
    }

    /**
     * Inserts data into the 'categories' table.
     * Column names : { category_id, category_name, parent_id }
     * @param table A Map representing rows of data to be inserted, with category IDs as keys and Object arrays as values.
     * @throws SQLException If an SQL error occurs during the insertion process.
     */
    public void insertCategories(Map<Long, Object[]> table) throws SQLException {

        String command = "INSERT INTO `edadb`.`categories` (`category_id`,`category_name`, `parent_id`) VALUES (?, ?, ?);";
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            for (Map.Entry<Long, Object[]> entry : table.entrySet()) {
                Long categoryID = entry.getKey();

                String categoryName = entry.getValue()[0].toString();
                Long parentID = (Long) entry.getValue()[1];

                addstmt.setObject(1, categoryID);
                addstmt.setObject(2, categoryName);
                addstmt.setObject(3, parentID);
                addstmt.addBatch(); // Add the current row to the batch
            }
            addstmt.executeBatch();
            System.out.println("Insertion to Categories SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to Categories sql table.");
            err.printStackTrace();
        }
    }

    /**
     * Removes SQL safety settings.
     * @throws SQLException If an SQL error occurs during the execution of the command.
     */
    public void removeSQLSAFE() throws SQLException {
        String command = "SET SQL_SAFE_UPDATES=0;";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("SQL safety removed.");
    }

    /**
     * Clears data from the 'membership' table.
     * @throws SQLException If an SQL error occurs during the execution of the command.
     */
    public void clearMembership() throws SQLException {
        String command = "DELETE FROM `membership`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Membership SQL table cleared");
    }

    /**
     * Clears data from the 'characteristics' table.
     * @throws SQLException If an SQL error occurs during the execution of the command.
     */
    public void clearCharacteristics() throws SQLException {
        String command = "DELETE FROM `characteristics`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Characteristics SQL table cleared");
    }

    /**
     * Clears data from the 'categories' table.
     * @throws SQLException If an SQL error occurs during the execution of the command.
     */
    public void clearCategories() throws SQLException {
        String command = "DELETE FROM `categories`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Categories SQL table cleared");
    }



}