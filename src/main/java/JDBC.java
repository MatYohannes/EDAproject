import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


public class JDBC {
    private static final String USERNAME = "edadb";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/EDAProject";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement preparedStatement = null;


    /**
     * constructor for the Java DataBase connector with nor parameters
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public JDBC() throws FileNotFoundException, ParseException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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


    public void insertMembership(Object[][] table) throws SQLException {
        String command = "INSERT INTO `membership` (`custom_id`, `category_id`, `manufacturer`," +
                " `manufacturer_part_num`) VALUES (?, ?, ?, ?);";
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            for (Object[] row: table) {
                addstmt.setObject(1, row[0]);
                addstmt.setObject(2, row[1]);
                addstmt.setObject(3, row[2]);
                addstmt.setObject(4, row[3]);
            }

            addstmt.executeUpdate();
            System.out.println("Insertion to Membership SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to Membership sql table.");
            err.printStackTrace();
        }
    }

    public void insertCharacteristics(Object[][] table) throws SQLException {
        String command = "INSERT INTO `characteristics` (`custom_id`, `attribute_name`, `value`) VALUES (?, ?, ?);";
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            for (Object[] row: table) {
                addstmt.setObject(1, row[0]);
                addstmt.setObject(2, row[1]);
                addstmt.setObject(3, row[2]);
            }

            addstmt.executeUpdate();
            System.out.println("Insertion to Characteristics SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to Characteristics sql table.");
            err.printStackTrace();
        }
    }

    public void insertCategories(Map<Long, Object[]> table) throws SQLException {
        String command = "INSERT INTO `edadb`.`categories` (`custom_id`, `category_name`, `parent_id`) VALUES (?, ?, ?);";
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            for (Map.Entry<Long, Object[]> entry : table.entrySet()) {
                Long customID = entry.getKey();

                try {
                    int intValue = Math.toIntExact(customID); // Convert long to int safely
                    // Use intValue
                } catch (ArithmeticException e) {
                    // Handle overflow
                    System.out.println("Overflow occurred when converting long to int.");
                }

                String categoryName = entry.getValue()[0].toString();
                int parentID = (int) entry.getValue()[1];

                addstmt.setObject(1, customID);
                addstmt.setObject(2, categoryName);
                addstmt.setObject(3, parentID);
                addstmt.addBatch(); // Add the current row to the batch
            }

            addstmt.executeUpdate();
            System.out.println("Insertion to Categories SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to Categories sql table.");
            err.printStackTrace();
        }
    }


    public void removeSQLSAFE() throws SQLException {
        String command = "SET SQL_SAFE_UPDATES=0;";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("SQL safety removed.");
    }

    public void clearMembership() throws SQLException {
        String command = "DELETE FROM `membership`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Membership SQL table cleared");
    }

    public void clearCharacteristics() throws SQLException {
        String command = "DELETE FROM `characteristics`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Characteristics SQL table cleared");
    }
    public void clearCategories() throws SQLException {
        String command = "DELETE FROM `categories`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Categories SQL table cleared");
    }



}