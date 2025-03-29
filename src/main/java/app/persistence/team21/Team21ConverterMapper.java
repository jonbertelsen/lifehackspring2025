package app.persistence.team21;

import app.entities.team21.Team21Conversion;
import app.exceptions.DatabaseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import app.persistence.ConnectionPool;
import io.javalin.http.Context;

import java.sql.*;

public class Team21ConverterMapper {


    // Retrieves all conversion records from the database.
    public static List<Team21Conversion> getData(ConnectionPool connectionPool) throws DatabaseException {
        List<Team21Conversion> conversions = new ArrayList<>(); // List to hold conversion records.
        String sql = "SELECT * FROM team_21_conversions"; // SQL query to fetch all records.

        try (
                Connection connection = connectionPool.getConnection(); // Acquire a database connection.
                PreparedStatement ps = connection.prepareStatement(sql) // Prepare the SQL statement.
        ) {
            ResultSet rs = ps.executeQuery(); // Execute the query and fetch results.
            while (rs.next()) { // Iterate over each row in the result set.
                int id = rs.getInt("id"); // Extract the 'id' field from the row.
                String unit = rs.getString("units"); // Extract the 'units' field.
                double inputValue = rs.getDouble("input_value"); // Extract the 'input_value' field.
                String date = rs.getString("date");  // Extract the 'date' field.

                // Create a Team21Conversion object and add it to the list.
                conversions.add(new Team21Conversion(id, unit, inputValue, date));
            }
            //Catch exceptions
        } catch (SQLException e) {
            throw new DatabaseException("Error", e.getMessage());
        }
        return conversions;  // Return the list of conversion records
    }



    // Inserts a new conversion record into the database.
    public static void insertData(double input, String units, ConnectionPool connectionPool, Context ctx) throws DatabaseException, SQLException {
        // SQL query to insert a new record into the team_21_conversions table.
        String sql = "INSERT INTO team_21_conversions (units, input_value, date)" +
                "VALUES (?, ?, ?)";

        // Get the current date and format it as a String with format 'dd/MM/yyyy'.
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);


        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, units); // Set the 'units' parameter.
            ps.setDouble(2, input); // Set the 'input_value' parameter.
            ps.setString(3, formattedDate); // Set the 'date' parameter.

            int rowsAffected = ps.executeUpdate(); // Execute the insert operation.
            if (rowsAffected != 1) { // Check if exactly one row was affected.
                // If not, throw a DatabaseException to indicate an error.
                throw new DatabaseException("Error in updating of conversion");
            }
            //Catch exceptions
        } catch (SQLException e) {
            throw new DatabaseException("Error in updating of conversion");
        }
    }
}
