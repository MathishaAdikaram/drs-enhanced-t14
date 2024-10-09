package disasterresponse.dao;

import disasterresponse.models.User;
import disasterresponse.services.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDAO} class provides data access methods for the {@code users}
 * table in the database. It includes operations to retrieve, save, and delete
 * user records, as well as to retrieve roles and departments.
 *
 * <p>
 * This class manages user-related database operations including querying for
 * specific users, retrieving all users with their roles and departments, and
 * checking for unique usernames. It also includes methods for retrieving roles
 * and departments, saving new users, and deleting users by username.</p>
 *
 * @see DatabaseUtil
 * @see User
 *
 * @author 12236202
 */
public class UserDAO {

    /**
     * Retrieves a user record from the database based on the provided username.
     *
     * @param username The username of the user to be retrieved.
     * @return The {@code User} object if a user with the given username exists,
     * otherwise {@code null}.
     */
    public User getUserByUsername(String username) {
        User user = null;

        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPassword(resultSet.getString("password"));
                user.setUserRole(resultSet.getInt("user_role"));
                user.setDepartmentId(resultSet.getInt("department_id"));
                user.setCreatedBy(resultSet.getInt("created_by"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Retrieves all user records along with their roles and departments.
     *
     * @return A {@code List} of {@code User} objects containing user details,
     * role names, and department names.
     */
    public List<User> getAllUsersWithRolesAndDepartments() {
        List<User> users = new ArrayList<>();

        String query = "SELECT u.user_id, u.username, u.first_name, u.last_name, ur.role_name, d.department_name "
                + "FROM users u "
                + "INNER JOIN user_roles ur ON u.user_role = ur.role_id "
                + "LEFT JOIN departments d ON u.department_id = d.department_id";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUserRoleName(resultSet.getString("role_name"));
                user.setDepartmentName(resultSet.getString("department_name"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Retrieves all user roles from the database.
     *
     * @return A {@code List} of role names.
     */
    public List<String> getUserRoles() {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT role_name FROM user_roles";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roles.add(rs.getString("role_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    /**
     * Retrieves all department names from the database.
     *
     * @return A {@code List} of department names.
     */
    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT department_name FROM departments";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departments.add(rs.getString("department_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    /**
     * Checks whether a username is unique in the database.
     *
     * @param username The username to check for uniqueness.
     * @return {@code true} if the username is unique; {@code false} otherwise.
     */
    public boolean isUsernameUnique(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Saves a new user record into the database.
     *
     * @param user The {@code User} object containing the details of the user to
     * be saved.
     */
    public void saveUser(User user) {
        String sql = "INSERT INTO users (username, first_name, last_name, password, user_role, department_id, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());

            // Set user role ID
            int roleId = getUserRoleId(user.getUserRoleName());
            stmt.setInt(5, roleId);

            // Set department ID if applicable
            if (user.getDepartmentName() != null) {
                int departmentId = getDepartmentId(user.getDepartmentName());
                stmt.setInt(6, departmentId);
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            stmt.setInt(7, user.getCreatedBy());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the user role ID based on the provided role name.
     *
     * @param roleName The name of the user role.
     * @return The ID of the user role, or -1 if not found.
     */
    private int getUserRoleId(String roleName) {
        String sql = "SELECT role_id FROM user_roles WHERE role_name = ?";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("role_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Default value if role not found
    }

    /**
     * Retrieves the department ID based on the provided department name.
     *
     * @param departmentName The name of the department.
     * @return The ID of the department, or -1 if not found.
     */
    private int getDepartmentId(String departmentName) {
        String sql = "SELECT department_id FROM departments WHERE department_name = ?";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, departmentName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("department_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Default value if department not found
    }

    /**
     * Deletes a user record from the database based on the provided username.
     *
     * @param username The username of the user to be deleted.
     */
    public void deleteUserByUsername(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
