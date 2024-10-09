package disasterresponse.controllers;

import disasterresponse.models.Disaster;
import disasterresponse.models.DisasterMessage;
import disasterresponse.models.User;
import disasterresponse.services.DisasterService;
import disasterresponse.services.SessionDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextInputDialog;

/**
 * Controller class for managing the current disaster status view. It handles
 * the logic for displaying ongoing disasters, disaster updates, and managing
 * resource requests.
 *
 * This class interacts with the {@link DisasterService} to retrieve
 * disaster-related data and updates displayed in the JavaFX TableViews.
 *
 *
 * @see DisasterService
 * @see SessionDetails
 * @see Disaster
 * @see DisasterMessage
 *
 * @author 12236202
 */
public class CurrentDisasterStatusController {

    @FXML
    private TableView<Disaster> disasterTable;

    @FXML
    private TableColumn<Disaster, Integer> priorityColumn;

    @FXML
    private TableColumn<Disaster, String> typeColumn;

    @FXML
    private TableColumn<Disaster, String> locationColumn;

    @FXML
    private TableColumn<Disaster, Integer> severityColumn;

    @FXML
    private TableColumn<Disaster, String> timeSinceColumn;

    @FXML
    private TableView<DisasterMessage> updateTable;

    @FXML
    private TableColumn<DisasterMessage, String> timeOfUpdateColumn;

    @FXML
    private TableColumn<DisasterMessage, String> departmentColumn;

    @FXML
    private TableColumn<DisasterMessage, String> roleColumn;

    @FXML
    private TableColumn<DisasterMessage, String> nameColumn;

    @FXML
    private TableColumn<DisasterMessage, String> messageColumn;

    @FXML
    private Button requestResourceButton;

    @FXML
    private Button addUpdateButton;

    private final DisasterService disasterService;

    /**
     * Constructor for {@code CurrentDisasterStatusController}. Initializes the
     * {@link DisasterService} for accessing disaster-related data.
     */
    public CurrentDisasterStatusController() {
        this.disasterService = new DisasterService();
    }

    /**
     * Initializes the controller. This method is automatically called after the
     * FXML file has been loaded. It sets up the TableView columns, checks the
     * user role for specific button visibility, and loads the ongoing disaster
     * data.
     */
    @FXML
    public void initialize() {
        User loggedInUser = SessionDetails.getInstance().getLoggedInUser();
        String userRole = loggedInUser.getUserRoleName();

        // Hides the request resource button if the user is not a "Responder"
        if (!"Responder".equals(userRole)) {
            requestResourceButton.setVisible(false);
        }

        // Initialize the disaster table columns
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        severityColumn.setCellValueFactory(new PropertyValueFactory<>("severity"));
        timeSinceColumn.setCellValueFactory(new PropertyValueFactory<>("timeSinceReporting"));

        // Initialize the update table columns
        timeOfUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("messageTime"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("messagedByFullName"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        // Load ongoing disasters
        loadInProgressDisasters();

        // Row click event to load disaster updates
        disasterTable.setRowFactory(tv -> {
            TableRow<Disaster> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Disaster selectedDisaster = row.getItem();
                    loadDisasterUpdates(selectedDisaster.getId());
                    addUpdateButton.setDisable(false);
                    requestResourceButton.setDisable(false);
                }
            });
            return row;
        });
    }

    /**
     * Loads the ongoing disasters from the service and populates the disaster
     * table with the data.
     */
    private void loadInProgressDisasters() {
        updateTable.getItems().clear();
        addUpdateButton.setDisable(true);
        requestResourceButton.setDisable(true);

        List<Disaster> inProgressDisasters = disasterService.getDisastersByStatus("In Progress");
        ObservableList<Disaster> disasterData = FXCollections.observableArrayList(inProgressDisasters);
        disasterTable.setItems(disasterData);
    }

    /**
     * Loads the updates for the selected disaster and populates the update
     * table with the data.
     *
     * @param disasterId The ID of the selected disaster.
     */
    private void loadDisasterUpdates(int disasterId) {
        List<DisasterMessage> disasterMessages = disasterService.getDisasterMessages(disasterId);
        ObservableList<DisasterMessage> updateData = FXCollections.observableArrayList(disasterMessages);
        updateTable.setItems(updateData);
    }

    /**
     * Handles the refresh button action. Reloads the ongoing disasters.
     */
    @FXML
    private void handleRefresh() {
        loadInProgressDisasters();
    }

    /**
     * Handles the "Add Update" button action. Allows the user to add an update
     * for the selected disaster via a text input dialog.
     */
    @FXML
    private void handleAddUpdate() {
        Disaster selectedDisaster = disasterTable.getSelectionModel().getSelectedItem();
        if (selectedDisaster != null) {
            // Show a text input dialog to enter the update
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Update");
            dialog.setHeaderText("Enter an update for the selected disaster:");
            dialog.setContentText("Update:");

            // Show the dialog and wait for the result
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(updateMessage -> {
                disasterService.addMessage(selectedDisaster.getId(), updateMessage);
                loadDisasterUpdates(selectedDisaster.getId());
                addUpdateButton.setDisable(true);
                requestResourceButton.setDisable(true);
            });
        }
    }

    /**
     * Handles the "Request Resource" button action. Allows the user to request
     * a resource for the selected disaster via a text input dialog.
     */
    @FXML
    private void handleRequestResource() {
        Disaster selectedDisaster = disasterTable.getSelectionModel().getSelectedItem();
        if (selectedDisaster != null) {
            // Show a text input dialog to enter the resource request
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Request Resource");
            dialog.setHeaderText("Enter a resource request for the selected disaster:");
            dialog.setContentText("Resource Request:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(requestMessage -> {
                String prefixedMessage = "[REQUEST] " + requestMessage;

                disasterService.addMessage(selectedDisaster.getId(), prefixedMessage);
                loadDisasterUpdates(selectedDisaster.getId());
                addUpdateButton.setDisable(true);
                requestResourceButton.setDisable(true);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Request Resource");
                alert.setHeaderText("Resource request has been saved.");
                alert.setContentText("Message: " + prefixedMessage);
                alert.showAndWait();

                // Here you can handle the request resource logic (e.g., calling service/DAO)
                System.out.println("Resource requested: " + prefixedMessage);
            });
        }
    }
}
