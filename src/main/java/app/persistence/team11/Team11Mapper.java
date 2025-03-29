package app.persistence.team11;

import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Team11Mapper {

    public static double getTariffRate(String country, String category) {
        String sql = "SELECT tariff_rate_in_percent FROM team11_tariff_rate WHERE TRIM(LOWER(country_name)) = TRIM(LOWER(?)) AND TRIM(LOWER(category_name)) = TRIM(LOWER(?))";

        try (Connection connection = ConnectionPool.getInstance(
                "postgres", // Username
                "postgres", // Password
                "jdbc:postgresql://localhost:5432/%s?currentSchema=public", // PostgreSQL URL
                "lifehackspring2025" // DB name
        ).getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, country);
            statement.setString(2, category);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                double tariffRate = rs.getDouble("tariff_rate_in_percent");
                System.out.println("Tariff rate found: " + tariffRate);
                return tariffRate;
            } else {

                System.out.println(" No tariff rate found for country: " + country + ", category: " + category);
            }

        } catch (Exception e) {

            System.err.println(" Error in getTariffRate: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0; // Returner 0 hvis ingen data findes
    }

    public static double getVatRate(String country) {
        String sql = "SELECT country_moms_in_percent FROM team11_country WHERE TRIM(LOWER(country_name)) = TRIM(LOWER(?))";


        try (Connection connection = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/lifehackspring2025",
                "lifehackspring2025"
        ).getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, country);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                double vatRate = rs.getDouble("country_moms_in_percent");
                return vatRate;
            } else {
                System.out.println(" No VAT rate found for country: " + country);
            }

        } catch (Exception e) {
            System.err.println(" Error in getVatRate: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Returning default VAT rate: 0.0");
        return 0.0; // Returner 0 hvis ingen data findes
    }
}
