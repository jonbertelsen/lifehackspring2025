package app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Team11Mapper {

    public static double getTariffRate(String country, String category) {
        String sql = "SELECT tariff_rate_in_percent FROM team11_tariff_rate WHERE TRIM(LOWER(country_name)) = TRIM(LOWER(?)) AND TRIM(LOWER(category_name)) = TRIM(LOWER(?))";

        try (Connection connection = ConnectionPool.getInstance(
                "postgres", // Username
                "postgres", // Password
                "jdbc:postgresql://localhost:5432/lifehackspring2025", // PostgreSQL URL
                "lifehackspring2025" // DB name
        ).getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // LOGGING: Tjekker hvilke værdier der bliver sendt til SQL'en
            System.out.println("🛢️ Executing tariff query with:");
            System.out.println("🛢️ Country = " + country);
            System.out.println("🛢️ Category = " + category);

            statement.setString(1, country);
            statement.setString(2, category);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // LOGGING: Hvis der bliver fundet en værdi i databasen
                double tariffRate = rs.getDouble("tariff_rate_in_percent");
                System.out.println("✅ Tariff rate found: " + tariffRate);
                return tariffRate;
            } else {
                // LOGGING: Hvis der ikke bliver fundet noget i databasen
                System.out.println("❌ No tariff rate found for country: " + country + ", category: " + category);
            }

        } catch (Exception e) {
            // LOGGING: Hvis der opstår en fejl under forespørgslen
            System.err.println("❌ Error in getTariffRate: " + e.getMessage());
            e.printStackTrace();
        }

        // LOGGING: Hvis der returneres 0 som fallback
        System.out.println("🔎 Returning default tariff rate: 0.0");
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

            // LOGGING: Tjekker hvilke værdier der bliver sendt til SQL'en
            System.out.println("💰 Executing VAT query with:");
            System.out.println("💰 Country = " + country);

            statement.setString(1, country);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // LOGGING: Hvis der bliver fundet en værdi i databasen
                double vatRate = rs.getDouble("country_moms_in_percent");
                System.out.println("✅ VAT rate found: " + vatRate);
                return vatRate;
            } else {
                // LOGGING: Hvis der ikke bliver fundet noget i databasen
                System.out.println("❌ No VAT rate found for country: " + country);
            }

        } catch (Exception e) {
            // LOGGING: Hvis der opstår en fejl under forespørgslen
            System.err.println("❌ Error in getVatRate: " + e.getMessage());
            e.printStackTrace();
        }

        // LOGGING: Hvis der returneres 0 som fallback
        System.out.println("🔎 Returning default VAT rate: 0.0");
        return 0.0; // Returner 0 hvis ingen data findes
    }
}
