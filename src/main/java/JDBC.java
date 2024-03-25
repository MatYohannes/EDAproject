import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;



public class JDBC {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "MAAD_password";
    private static final String URL = "jdbc:mysql://localhost:3306/maad";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement preparedStatement = null;

    // Create link between DatabaseH HashMap and JDBC HashMap
    LinkedHashMap<Room, Account> jdbcHash;
    ArrayList<Account> jdbcAcList;
    ArrayList<Reservation> jdbcResList;

    // link hashmaps

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

    /*public boolean execute(String sql) throws SQLException {
        if (connection == null) {
            throw new SQLException("Connection null!");
        }
            statement = connection.createStatement();
            boolean res = statement.execute(sql);
            statement.close();
            return res;
    }
    public int executeUpdate(String sql) throws  SQLException {
        if (connection == null) {
            throw new SQLException("Connection null!");
        }
        //Statement statement = connection.createStatement();
        preparedStatement = connection.prepareStatement(sql);
        int res = statement.executeUpdate(sql);
        statement.close();
        return res;
    }
    public ResultSet executeQuery(String sql) throws SQLException {
        if (connection == null) {
            throw new SQLException("Connection null!");
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        statement.close();
        return resultSet;
    }
*/

    /**
     * transfer data for DatabaseH reservation ArrayList to SQL table
     * @param reservation Reservation Class
     * @throws SQLException
     */
    public void insert(Reservation reservation) throws SQLException {
        String command = "INSERT INTO `reservations` (`ReservationID`, `RoomNumber`, `NumberOfGuest`," +
                " `RoomPrice`, `StayLength`, `ReservationDate`, `ReservationMade`, `AccountID`, `Cancelled`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement addstmt = connection.prepareStatement(command)) {
            addstmt.setObject(1, reservation.getReservationID());
            addstmt.setObject(2, reservation.getRoomNumber());
            addstmt.setObject(3, reservation.getOccupancy());
            addstmt.setObject(4, reservation.getCost());
            addstmt.setObject(5, reservation.getDurationOfStay());
            addstmt.setObject(6, reservation.getArrivalDate());
            addstmt.setObject(7, reservation.getDateMade());
            addstmt.setObject(8, reservation.getAccountID());
            addstmt.setObject(9, reservation.isCancelled());
            addstmt.executeUpdate();
            System.out.println("Insertion to Reservation SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to reservations sql table.");
            err.printStackTrace();
        }
    }
    /**
     * transfer data for DatabaseH account ArrayList to SQL table
     * @param acc Account Class
     * @throws SQLException
     */
    public void insert(Account acc) throws SQLException {
        String command = "INSERT INTO `accounts` (`AccountID`, `UserName`, `Password`, `CustomerInfo`)" +
                "VALUES (?,?,?,?);";
        try(PreparedStatement addstmt = connection.prepareStatement(command)) {
            addstmt.setObject(1, acc.getAccountID());
            addstmt.setObject(2, acc.getUserName());
            addstmt.setObject(3, acc.getAccountPassword());
            addstmt.setObject(4, acc.getCustomerData());
            addstmt.executeUpdate();
            System.out.println("Insertion to Account SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occurred when inserting to accounts sql table.");
            err.printStackTrace();
        }
    }
    /**
     * transfer data for DatabaseH LinkedHashMap to SQL table
     * @param room Room Class
     * @throws SQLException
     */
    public void insert(Room room) throws SQLException {
        String command = "INSERT INTO `rooms` (`RoomNumber`, `Vacant`, `Occupancy`, `RoomPrice`," +
                " `RoomType`, `Amenities`, `AccountID`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try(PreparedStatement addstmt = connection.prepareStatement(command)) {
            addstmt.setObject(1, room.getRoomNumber());
            addstmt.setObject(2, room.isCancelled());
            addstmt.setObject(3, room.getOccupancy());
            addstmt.setObject(4, room.getRoomPrice());
            addstmt.setObject(5, room.getRoomType());
            addstmt.setObject(6, room.getAmenities());
            addstmt.setObject(7, room.getAccountID());
            addstmt.executeUpdate();
            System.out.println("Insertion to Room SQL table.");
        } catch (Exception err) {
            System.out.println("An error has occured when inserting to rooms sql table.");
            err.printStackTrace();
        }
    }
    /**
     * pulls data from sql server and uploads to DatabaseH LinkedHashMap,
     * DatabaseH accountList, and DatabaseH reservation list
     */
    public void pullSQLToHash() {
        try {
            // Pulling Rooms data to HashMap
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `rooms`");
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("RoomNumber");
                boolean availability = resultSet.getBoolean("Vacant");
                int occupancy = resultSet.getInt("Occupancy");
                double roomPrice = resultSet.getDouble("RoomPrice");
                String roomType = resultSet.getString("roomType");
                String amenities = resultSet.getString("Amenities");
                int accountID = resultSet.getInt("AccountID");

                Room room = new Room(roomNumber, availability, occupancy, roomPrice, roomType, amenities, accountID);
                jdbcHash.put(room, null);
            }

            resultSet = statement.executeQuery("SELECT * FROM `accounts`");
            while (resultSet.next()) {
                int accountID = resultSet.getInt("AccountID");
                String userName = resultSet.getString("UserName");
                String accountPassword = resultSet.getString("Password");
                String customerData = resultSet.getString("CustomerInfo");

                Account account = new Account(accountID, userName, accountPassword,
                        customerData);
                jdbcAcList.add(account);
            }

            resultSet = statement.executeQuery("SELECT * FROM `reservations`");
            while (resultSet.next()) {
                int reservationID = resultSet.getInt("ReservationID");
                int roomNumber = resultSet.getInt("RoomNumber");
                int occupancy = resultSet.getInt("NumberOfGuest");
                double cost = resultSet.getDouble("RoomPrice");
                int durationOfStay = resultSet.getInt("StayLength");
                Date arrivalDate = resultSet.getDate("ReservationDate");
                Date dateMade = resultSet.getDate("ReservationMade");
                int accountID = resultSet.getInt("AccountID");
                boolean cancelled = resultSet.getBoolean("Cancelled");

                Reservation reservation = new Reservation(reservationID, roomNumber,
                        occupancy, cost, durationOfStay, arrivalDate, dateMade,
                        accountID, cancelled);
                jdbcResList.add(reservation);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void removeSQLSAFE() throws SQLException {
        String command = "SET SQL_SAFE_UPDATES=0;";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("SQL safety removed.");
    }

    public void clearRoomSQLTable() throws SQLException {
        String command = "DELETE FROM `rooms`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Rooms SQL table cleared");
    }

    public void clearAccountSQLTable() throws SQLException {
        String command = "DELETE FROM `accounts`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Accounts SQL table cleared");
    }
    public void clearReservationSQLTable() throws SQLException {
        String command = "DELETE FROM `reservations`";
        PreparedStatement addstmt = connection.prepareStatement(command);
        addstmt.executeUpdate();
        System.out.println("Rooms SQL table cleared");
    }



}