package disasterresponse.dao;

import disasterresponse.services.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code UserRoleDAO} class provides data access methods for the
 * {@code user_roles} table in the database. It includes operations to retrieve
 * user roles based on their ID.
 *
 * <p>
 * This class manages user role-related database operations, specifically
 * querying for a user role by its ID.</p>
 *
 * @see DatabaseUtil
 *
 * @author 12236202
 */
public class UserRoleDAO {

    /**
     * Retrieves the role name based on the provided role ID.
     *
     * @param roleId The ID of the user role.
     * @return The name of the user role if it exists; {@code null} otherwise.
     */
    public String getUserRoleById(int roleId) {
        String sql = "SELECT role_name FROM user_roles WHERE role_id = ?";
        String roleName = null;

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                roleName = rs.getString("role_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleName;
    }
}
