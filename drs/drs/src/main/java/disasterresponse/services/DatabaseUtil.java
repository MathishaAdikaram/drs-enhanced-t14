package disasterresponse.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DatabaseUtil} class provides utility methods for connecting to the
 * database. It includes the database URL, username, and password required to
 * establish a connection.
 *
 * <p>
 * This class is used throughout the application to manage database
 * connections.</p>
 *
 * @see disasterresponse.dao.UserDAO
 * @see disasterresponse.dao.DisasterDAO
 * @see disasterresponse.dao.DisasterMessageDAO
 *
 * @author 12236202
 */
public class DatabaseUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/disaster_response";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    /**
     * Obtains a connection to the database using the provided URL, username,
     * and password.
     *
     * <p>
     * This method establishes a connection to the MySQL database and returns
     * the {@code Connection} object.</p>
     *
     * @return A {@code Connection} object for interacting with the database.
     * @throws SQLException If a database access error occurs or the URL is
     * null.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
