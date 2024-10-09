package disasterresponse.models;

/**
 * The {@code User} class represents a user within the system. It includes
 * details about the user's identity, role, department, and creation metadata.
 *
 * <p>
 * This class serves as a model for managing user information in the
 * application.</p>
 *
 * @see disasterresponse.dao.UserDAO
 * @see disasterresponse.services.UserService
 *
 * @author 12236202
 */
public class User {

    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private int userRole;
    private String userRoleName;
    private int departmentId;
    private String departmentName;
    private int createdBy;

    // Getters and Setters
    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param userId The unique identifier of the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role ID of the user.
     *
     * @return The role ID of the user.
     */
    public int getUserRole() {
        return userRole;
    }

    /**
     * Sets the role ID of the user.
     *
     * @param userRole The role ID of the user.
     */
    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets the name of the user's role.
     *
     * @return The name of the user's role.
     */
    public String getUserRoleName() {
        return userRoleName;
    }

    /**
     * Sets the name of the user's role.
     *
     * @param userRoleName The name of the user's role.
     */
    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    /**
     * Gets the department ID associated with the user.
     *
     * @return The department ID associated with the user.
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department ID associated with the user.
     *
     * @param departmentId The department ID associated with the user.
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the name of the department associated with the user.
     *
     * @return The name of the department associated with the user.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the name of the department associated with the user.
     *
     * @param departmentName The name of the department associated with the
     * user.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Gets the ID of the user who created this user record.
     *
     * @return The ID of the user who created this user record.
     */
    public int getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the ID of the user who created this user record.
     *
     * @param createdBy The ID of the user who created this user record.
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
