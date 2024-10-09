package disasterresponse.controllers;

import disasterresponse.services.DisasterService;
import disasterresponse.services.SessionDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The {@code ReportDisasterController} class is responsible for handling the
 * reporting of new disasters. It allows the user to input details about a
 * disaster such as type, severity, location, and description. The report is
 * then sent to the system using the {@code DisasterService}.
 *
 * <p>
 * This controller also handles the population of the disaster type and severity
 * fields, as well as the submission and clearing of the form.
 * </p>
 *
 * @see DisasterService
 * @see SessionDetails
 * @see Alert
 * @see ComboBox
 * @see TextField
 * @see TextArea
 *
 * @author 12236202
 */
public class ReportDisasterController {

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<Integer> severityComboBox;

    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionField;

    private final DisasterService disasterService;

    /**
     * Constructor for {@code ReportDisasterController}. Initializes the
     * {@code DisasterService} instance for reporting disasters.
     */
    public ReportDisasterController() {
        this.disasterService = new DisasterService();
    }

    /**
     * Initializes the controller and populates the disaster type and severity
     * drop-downs with predefined values. This method is called automatically
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Populate disaster type dropdown
        typeComboBox.getItems().addAll("Flood", "Earthquake", "Fire", "Tornado");

        // Populate severity dropdown
        severityComboBox.getItems().addAll(1, 2, 3, 4, 5);
    }

    /**
     * Handles the submission of the disaster report when the user clicks the
     * "Report" button. It collects the form data, retrieves the logged-in
     * user's ID from {@code SessionDetails}, and submits the disaster report
     * via the {@code DisasterService}. An alert is shown to confirm that the
     * report has been added successfully.
     */
    @FXML
    private void handleReport() {
        String type = typeComboBox.getValue();
        String location = locationField.getText();
        int severity = severityComboBox.getValue();
        String description = descriptionField.getText();

        // Get the logged-in user's ID from SessionDetails
        int reportedBy = SessionDetails.getInstance().getLoggedInUser().getUserId();

        // Report the disaster
        disasterService.reportDisaster(type, location, severity, description, reportedBy);

        // Show success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Report Added Successfully!");
        alert.showAndWait();

        // Clear the form
        clearForm();
    }

    /**
     * Clears all fields in the form after a successful disaster report
     * submission. Resets the disaster type, severity, location, and description
     * fields.
     */
    private void clearForm() {
        typeComboBox.getSelectionModel().clearSelection();
        severityComboBox.getSelectionModel().clearSelection();
        locationField.clear();
        descriptionField.clear();
    }
}
