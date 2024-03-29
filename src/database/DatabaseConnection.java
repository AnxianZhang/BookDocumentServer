package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static Connection connexion;

    static {
        connexion = null;
    }

    private DatabaseConnection() {
        throw new IllegalStateException("DatabaseConnection is an utility class");
    }

    public static void makeConnexion() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";

            connexion = DriverManager.getConnection(url, "system", "xingxing");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("ERROR IN DatabaseConnection WITH: " + e.getMessage());
        }
    }

    public static Connection getConnexion() {
        return connexion;
    }

    public static void close() throws SQLException {
        connexion.close();
        connexion = null;
    }
}