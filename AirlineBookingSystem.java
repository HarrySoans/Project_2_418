import java.sql.*;

public class AirlineBookingSystem {

    // Function to create connection to MySQL database
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://your_host/falcon_airline",
                "harry",
                "12345"
        );
    }

    // Function to create tables for the airline booking system
    public static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // SQL queries to create tables
        String createTableQuery = "CREATE TABLE IF NOT EXISTS bookings ("
                + "booking_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "passenger_name VARCHAR(255),"
                + "flight_number VARCHAR(50),"
                + "departure_date DATE,"
                + "seat_number VARCHAR(10)"
                + ")";
        statement.execute(createTableQuery);
        statement.close();
    }

    // Function to insert a booking into the database
    public static void insertBooking(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // SQL query to insert a booking
        System.out.print("Enter passenger name: ");
        String passengerName = System.console().readLine();
        System.out.print("Enter flight number: ");
        String flightNumber = System.console().readLine();
        System.out.print("Enter departure date (YYYY-MM-DD): ");
        String departureDate = System.console().readLine();
        System.out.print("Enter seat number: ");
        String seatNumber = System.console().readLine();

        String insertQuery = "INSERT INTO bookings (passenger_name, flight_number, departure_date, seat_number) "
                + "VALUES ('" + passengerName + "', '" + flightNumber + "', '" + departureDate + "', '" + seatNumber + "')";
        statement.execute(insertQuery);
        System.out.println("Booking successfully added!");
        statement.close();
    }

    // Function to view bookings
    public static void viewBooking(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // SQL query to view bookings
        String viewQuery = "SELECT * FROM bookings";
        ResultSet resultSet = statement.executeQuery(viewQuery);
        while (resultSet.next()) {
            System.out.println("Booking ID: " + resultSet.getInt("booking_id"));
            System.out.println("Passenger Name: " + resultSet.getString("passenger_name"));
            System.out.println("Flight Number: " + resultSet.getString("flight_number"));
            System.out.println("Departure Date: " + resultSet.getString("departure_date"));
            System.out.println("Seat Number: " + resultSet.getString("seat_number"));
            System.out.println();
        }
        resultSet.close();
        statement.close();
    }

    // Function to delete a booking
    public static void deleteBooking(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // SQL query to delete a booking
        System.out.print("Enter booking ID to cancel: ");
        String bookingID = System.console().readLine();
        String deleteQuery = "DELETE FROM bookings WHERE booking_id = " + bookingID;
        statement.execute(deleteQuery);
        System.out.println("Booking successfully canceled!");
        statement.close();
    }

    // Main function
    public static void main(String[] args) {
        try {
            Connection connection = createConnection();
            createTable(connection);
            while (true) {
                System.out.println("1. Book\n2. View Reservation\n3. Cancel Reservation\n4. Exit");
                System.out.print("Enter your choice: ");
                String choice = System.console().readLine();
                if (choice.equals("1")) {
                    insertBooking(connection);
                } else if (choice.equals("2")) {
                    viewBooking(connection);
                } else if (choice.equals("3")) {
                    deleteBooking(connection);
                } else if (choice.equals("4")) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
