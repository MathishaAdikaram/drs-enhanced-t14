package disasterresponse.services;

import disasterresponse.dao.DepartmentDAO;
import disasterresponse.dao.UserDAO;
import disasterresponse.dao.UserRoleDAO;
import disasterresponse.models.User;
import java.util.List;

/**
 * The {@code UserService} class handles operations related to user management.
 * It interacts with the Data Access Objects (DAOs) for users, roles, and
 * departments to perform various user-related tasks such as validation,
 * retrieval, and persistence.
 *
 * <p>
 * It provides methods for validating users, fetching user roles and
 * departments, saving and deleting users, and ensuring username uniqueness.</p>
 *
 * @see disasterresponse.dao.UserDAO
 * @see disasterresponse.dao.UserRoleDAO
 * @see disasterresponse.dao.DepartmentDAO
 * @see disasterresponse.models.User
 *
 * @autor 12236202
 */
public class UserService {

    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final DepartmentDAO departmentDAO;

    /**
     * Constructs a {@code UserService} instance and initializes the necessary
     * DAOs.
     */
    public UserService() {
        userDAO = new UserDAO();
        userRoleDAO = new UserRoleDAO();
        departmentDAO = new DepartmentDAO();
    }

    /**
     * Validates the user credentials and sets session details if valid.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return {@code true} if the user is validated successfully, {@code false}
     * otherwise.
     */
    public boolean validateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            String userRoleName = userRoleDAO.getUserRoleById(user.getUserRole());
            user.setUserRoleName(userRoleName);

            String topMessage = user.getFirstName() + " " + user.getLastName() + " (" + user.getUsername() + ") [" + user.getUserRoleName() + "]";

            if (user.getDepartmentId() != 0) {
                String departmentName = departmentDAO.getDepartmentById(user.getDepartmentId());
                user.setDepartmentName(departmentName);
                topMessage += " [Department: " + user.getDepartmentName() + "]";
            }

            SessionDetails.getInstance().setLoggedInUser(user);
            SessionDetails.getInstance().setTopMessage(topMessage);
            return true;
        }

        return false;
    }

    /**
     * Retrieves a list of all users along with their roles and departments.
     *
     * @return A list of {@code User} objects with roles and departments.
     */
    public List<User> getAllUsersWithRolesAndDepartments() {
        return userDAO.getAllUsersWithRolesAndDepartments();
    }

    /**
     * Retrieves a list of all user roles.
     *
     * @return A list of user role names.
     */
    public List<String> getUserRoles() {
        return userDAO.getUserRoles();
    }

    /**
     * Retrieves a list of all departments.
     *
     * @return A list of department names.
     */
    public List<String> getDepartments() {
        return userDAO.getDepartments();
    }

    /**
     * Checks if a given username is unique.
     *
     * @param username The username to check.
     * @return {@code true} if the username is unique, {@code false} otherwise.
     */
    public boolean isUsernameUnique(String username) {
        return userDAO.isUsernameUnique(username);
    }

    /**
     * Saves a new user to the database.
     *
     * @param username The username of the new user.
     * @param firstName The first name of the new user.
     * @param lastName The last name of the new user.
     * @param password The password of the new user.
     * @param userRole The role name of the new user.
     * @param department The department name of the new user.
     */
    public void saveUser(String username, String firstName, String lastName, String password, String userRole, String department) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setUserRoleName(userRole);
        newUser.setDepartmentName(department);
        newUser.setCreatedBy(SessionDetails.getInstance().getLoggedInUser().getUserId());

        userDAO.saveUser(newUser);
    }

    /**
     * Deletes a user by their username.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUserByUsername(String username) {
        userDAO.deleteUserByUsername(username);
    }
}
