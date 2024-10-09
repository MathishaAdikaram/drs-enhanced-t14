package disasterresponse.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import disasterresponse.services.UserService;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code LoginController} class handles the user login functionality. It
 * validates user input and, if the credentials are valid, transitions to the
 * main dashboard. This class interacts with the {@code UserService} to perform
 * the user validation.
 *
 * <p>
 * The controller captures the username and password, verifies them, and
 * displays an error message if the login fails.
 * </p>
 *
 * <p>
 * If the login is successful, the controller loads the dashboard interface.
 * </p>
 *
 * @see UserService
 * @see FXMLLoader
 * @see Stage
 * @see Parent
 *
 * @author 12236202
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label validationLabel;

    private UserService userService;

    /**
     * Initializes the {@code LoginController}. Creates a new instance of
     * {@code UserService} for user validation.
     */
    public LoginController() {
        userService = new UserService();
    }

    /**
     * Handles the sign-in process. Validates the username and password input
     * fields, and calls the {@code UserService} to validate the credentials. If
     * the login is successful, it loads the dashboard. Otherwise, an error
     * message is displayed.
     */
    @FXML
    private void handleSignIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate user input
        if (username.isEmpty() || password.isEmpty()) {
            validationLabel.setText("Username or password cannot be empty.");
            validationLabel.setVisible(true);
        } else {
            // Call the user service to check credentials
            boolean isValid = userService.validateUser(username, password);

            if (isValid) {
                loadDashboard();
            } else {
                validationLabel.setText("Invalid username or password.");
                validationLabel.setVisible(true);
            }
        }
    }

    /**
     * Loads the dashboard screen if the login is successful. Transitions from
     * the login screen to the dashboard by replacing the current scene with the
     * dashboard view.
     */
    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/disasterresponse/views/dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Dashboard");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
