package disasterresponse.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import disasterresponse.models.User;
import disasterresponse.services.UserService;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * The {@code UsersController} class handles user management within the
 * application. It allows for the creation, editing, deletion, and display of
 * users.
 *
 * <p>
 * This controller interacts with the {@code UserService} to perform operations
 * related to user management, including user creation and deletion. It also
 * provides functionality for filtering users based on roles and
 * departments.</p>
 *
 * <p>
 * The user interface includes a table view for displaying users, as well as
 * forms for creating and editing user details.</p>
 *
 * @see User
 * @see UserService
 * @see TableView
 * @see Alert
 * @see ComboBox
 * @see TextField
 *
 * @author 12236202
 */
public class UsersController {

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> userRoleColumn;

    @FXML
    private TableColumn<User, String> departmentColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<String> userRoleComboBox;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label usernameValidation;

    @FXML
    private VBox createForm;

    private final UserService userService;

    /**
     * Constructor for {@code UsersController}. Initializes the
     * {@code UserService} instance for managing user data.
     */
    public UsersController() {
        this.userService = new UserService();
    }

    /**
     * Initializes the controller. Configures table columns, sets up listeners
     * for table selection, populates dropdowns with roles and departments, and
     * defines behavior based on user selection.
     */
    @FXML
    public void initialize() {
        // Disable buttons by default
        deleteButton.setDisable(true);

        // Add listener to table selection model
        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Enable buttons if a row is selected
                deleteButton.setDisable(false);
            } else {
                // Disable buttons if no row is selected
                deleteButton.setDisable(true);
            }
        });

        // Set up table columns to display user information
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("userRoleName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

        // Load users into the table
        loadUsers();

        // Populate user roles in the combo box
        List<String> roles = userService.getUserRoles();
        ObservableList<String> roleOptions = FXCollections.observableArrayList(roles);
        userRoleComboBox.setItems(roleOptions);

        // Add listener to userRoleComboBox for role-dependent behavior
        userRoleComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Enable department dropdown if role is "Department Administrator" or "Responder"
            if (newValue.equals("Department Administrator") || newValue.equals("Responder")) {
                departmentComboBox.setDisable(false);
                populateDepartments();
            } else {
                departmentComboBox.setDisable(true);
                departmentComboBox.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * Handles the creation of a new user. Shows the user creation form.
     */
    @FXML
    private void handleCreate() {
        createForm.setVisible(true);
    }

    /**
     * Handles the deletion of a selected user. Shows a confirmation dialog
     * before deleting the user.
     */
    @FXML
    private void handleDelete() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Show confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Delete User");
            confirmationAlert.setContentText("Are you sure you want to delete user: " + selectedUser.getUsername() + "?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete user and reload user list
                userService.deleteUserByUsername(selectedUser.getUsername());
                loadUsers();
                deleteButton.setDisable(true);
            }
        }
    }

    /**
     * Saves a new user based on the input fields and selected options.
     * Validates the input and shows alerts if there are errors or confirms
     * success.
     */
    @FXML
    private void handleSaveUser() {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String selectedRole = userRoleComboBox.getSelectionModel().getSelectedItem();
        String selectedDepartment = departmentComboBox.getSelectionModel().getSelectedItem();

        // Validate required fields
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || selectedRole == null || password.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Check for unique username
        if (!userService.isUsernameUnique(username)) {
            usernameValidation.setVisible(true);
            return;
        } else {
            usernameValidation.setVisible(false);
        }

        // If user role is 3 or 4, validate department selection
        if ((selectedRole.equals("Department Administrator") || selectedRole.equals("Responder")) && selectedDepartment == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a department for this role.");
            alert.showAndWait();
            return;
        }

        // Save the new user
        userService.saveUser(username, firstName, lastName, password, selectedRole, selectedDepartment);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("User saved successfully.");
        alert.showAndWait();

        createForm.setVisible(false);

        loadUsers();
    }

    /**
     * Loads all users and populates the table with user data.
     */
    private void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(userService.getAllUsersWithRolesAndDepartments());
        usersTable.setItems(users);
    }

    /**
     * Populates the department combo box with available departments.
     */
    private void populateDepartments() {
        List<String> departments = userService.getDepartments();
        ObservableList<String> departmentOptions = FXCollections.observableArrayList(departments);
        departmentComboBox.setItems(departmentOptions);
    }
}
