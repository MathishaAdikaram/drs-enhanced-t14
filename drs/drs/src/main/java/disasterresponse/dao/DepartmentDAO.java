package disasterresponse.dao;

import disasterresponse.services.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code DepartmentDAO} class provides data access methods for the
 * {@code departments} table in the database. It includes operations to retrieve
 * department information based on department ID.
 *
 * <p>
 * This class is used to interact with the database to obtain details about
 * departments, such as fetching the name of a department using its ID.</p>
 *
 * <p>
 * The class relies on {@code DatabaseUtil} for obtaining database connections
 * and handles SQL exceptions that may occur during database operations.</p>
 *
 * @see DatabaseUtil
 *
 * @author 12236202
 */
public class DepartmentDAO {

    /**
     * Retrieves the department name for a given department ID from the
     * database.
     *
     * @param departmentId The ID of the department whose name is to be
     * retrieved.
     * @return The name of the department, or {@code null} if no department with
     * the given ID exists.
     */
    public String getDepartmentById(int departmentId) {
        String sql = "SELECT department_name FROM departments WHERE department_id = ?";
        String departmentName = null;

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                departmentName = rs.getString("department_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentName;
    }
}
