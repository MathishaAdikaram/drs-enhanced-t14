package disasterresponse.controllers;

import disasterresponse.models.User;
import disasterresponse.services.SessionDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * The {@code DashboardController} class is responsible for managing the main
 * dashboard of the application. It loads the content of various tabs based on
 * the user's role and handles user interaction, such as signing out.
 *
 * <p>
 * The controller determines the visibility and accessibility of different tabs
 * (e.g., users management, reporting disasters) based on the role of the
 * logged-in user.
 * </p>
 *
 * @author 12236202
 */
public class DashboardController {

    @FXML
    private TabPane dashboardTabPane;

    @FXML
    private Label userInfoLabel;

    @FXML
    private Tab usersTab;

    @FXML
    private Tab reportDisasterTab;

    @FXML
    private Tab viewAllDisastersTab;

    @FXML
    private Tab sendInitialResourcesTab;

    @FXML
    private Tab currentDisasterStatusTab;

    @FXML
    private Button signOutButton;

    /**
     * Initializes the dashboard controller. Loads the content of various tabs
     * and sets their visibility based on the logged-in user's role. The method
     * also sets the user's information at the top of the dashboard.
     */
    @FXML
    public void initialize() {
        userInfoLabel.setText(SessionDetails.getInstance().getTopMessage());

        // Load content for each tab
        loadTabContent(usersTab, "/disasterresponse/views/users.fxml");
        loadTabContent(reportDisasterTab, "/disasterresponse/views/reportDisaster.fxml");
        loadTabContent(viewAllDisastersTab, "/disasterresponse/views/viewAllDisasters.fxml");
        loadTabContent(sendInitialResourcesTab, "/disasterresponse/views/sendInitialResources.fxml");
        loadTabContent(currentDisasterStatusTab, "/disasterresponse/views/currentDisasterStatus.fxml");

        // Get the logged-in user details
        User loggedInUser = SessionDetails.getInstance().getLoggedInUser();
        String userRole = loggedInUser.getUserRoleName();

        // Disable all tabs initially
        usersTab.setDisable(true);
        reportDisasterTab.setDisable(true);
        viewAllDisastersTab.setDisable(true);
        sendInitialResourcesTab.setDisable(true);
        currentDisasterStatusTab.setDisable(true);

        // Show or hide tabs based on the user's role
        switch (userRole) {
            case "System Administrator" -> {
                usersTab.setDisable(false);
                dashboardTabPane.getTabs().removeAll(reportDisasterTab, viewAllDisastersTab, sendInitialResourcesTab, currentDisasterStatusTab);
            }
            case "Public User" -> {
                reportDisasterTab.setDisable(false);
                viewAllDisastersTab.setDisable(false);
                dashboardTabPane.getTabs().removeAll(usersTab, sendInitialResourcesTab, currentDisasterStatusTab);
            }
            case "Department Administrator" -> {
                sendInitialResourcesTab.setDisable(false);
                currentDisasterStatusTab.setDisable(false);
                dashboardTabPane.getTabs().removeAll(usersTab, reportDisasterTab, viewAllDisastersTab);
            }
            case "Responder" -> {
                currentDisasterStatusTab.setDisable(false);
                dashboardTabPane.getTabs().removeAll(usersTab, reportDisasterTab, viewAllDisastersTab, sendInitialResourcesTab);
            }
        }
    }

    /**
     * Handles the sign-out process. Displays a confirmation dialog and, if
     * confirmed, clears the session and navigates back to the login screen.
     */
    @FXML
    private void handleSignOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Sign Out");
        alert.setContentText("Are you sure you want to sign out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Clear session details
            SessionDetails.getInstance().clearSession();
            try {
                // Load the login screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/disasterresponse/views/login.fxml"));
                Parent loginRoot = loader.load();

                // Get the current stage and set the login scene
                Stage stage = (Stage) signOutButton.getScene().getWindow();
                Scene loginScene = new Scene(loginRoot);
                stage.setScene(loginScene);
                stage.setTitle("Login");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the content for the specified tab from the given FXML file.
     *
     * @param tab The tab to load the content into.
     * @param fxmlPath The path to the FXML file to be loaded.
     */
    private void loadTabContent(Tab tab, String fxmlPath) {
        try {
            AnchorPane tabContent = FXMLLoader.load(getClass().getResource(fxmlPath));
            tab.setContent(tabContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
