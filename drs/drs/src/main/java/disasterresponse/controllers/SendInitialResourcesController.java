package disasterresponse.controllers;

import disasterresponse.models.Disaster;
import disasterresponse.models.DisasterMessage;
import disasterresponse.services.DisasterService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableRow;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * The {@code SendInitialResourcesController} class manages the sending of
 * initial resources to disasters and handling disaster status updates. It
 * interacts with the disaster service to load, approve, reject, and update
 * disaster messages and disaster statuses.
 *
 * <p>
 * This controller enables the user to send resources to disasters in "Open"
 * status, close disasters that are "In Progress", and approve/reject
 * disaster-related requests.</p>
 *
 * <p>
 * It also displays disaster information in a table, allowing users to select a
 * disaster and perform various actions based on its status.</p>
 *
 * @see DisasterService
 * @see Disaster
 * @see DisasterMessage
 * @see TableView
 * @see Alert
 * @see Button
 * @see TextInputDialog
 *
 * @author 12236202
 */
public class SendInitialResourcesController {

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
    private TableColumn<Disaster, String> statusColumn;

    @FXML
    private Button sendResourcesButton;

    @FXML
    private Button closeDisasterButton;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private TableView<DisasterMessage> messageTable;

    private final DisasterService disasterService;

    /**
     * Constructor for {@code SendInitialResourcesController}. Initializes the
     * {@code DisasterService} instance for interacting with the disaster
     * management system.
     */
    public SendInitialResourcesController() {
        this.disasterService = new DisasterService();
    }

    /**
     * Initializes the controller and sets up the table columns to display
     * disaster details. Populates the disaster table with available disasters,
     * and defines behavior when a disaster or disaster message is selected.
     */
    @FXML
    public void initialize() {
        // Set up the table columns to show disaster information
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        severityColumn.setCellValueFactory(new PropertyValueFactory<>("severity"));
        timeSinceColumn.setCellValueFactory(new PropertyValueFactory<>("timeSinceReporting"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load disasters into the table
        loadDisasters();

        // Enable button when an "Open" status disaster is selected
        disasterTable.setRowFactory(tv -> {
            TableRow<Disaster> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Disaster selectedDisaster = row.getItem();

                    loadDisasterMessages(selectedDisaster.getId());
                    approveButton.setDisable(false);
                    rejectButton.setDisable(false);

                    // Enable or disable buttons based on disaster status
                    if ("Open".equals(selectedDisaster.getStatus())) {
                        sendResourcesButton.setDisable(false);
                    } else {
                        sendResourcesButton.setDisable(true);
                    }

                    if ("In Progress".equals(selectedDisaster.getStatus())) {
                        closeDisasterButton.setDisable(false);
                    } else {
                        closeDisasterButton.setDisable(true);
                    }

                    if ("Closed".equals(selectedDisaster.getStatus())) {
                        approveButton.setDisable(true);
                        rejectButton.setDisable(true);
                    }
                }
            });
            return row;
        });

        // Set behavior for selecting a disaster message
        messageTable.setRowFactory(tv -> {
            TableRow<DisasterMessage> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    approveButton.setDisable(false);
                    rejectButton.setDisable(false);
                }
            });
            return row;
        });
    }

    /**
     * Loads disasters from the service, sorts them by status (Open, In
     * Progress, Closed) and then by priority, and populates the disaster table.
     */
    private void loadDisasters() {
        List<Disaster> disasters = disasterService.getAllDisasters();

        // Sort by status: Open first, In Progress next, and Closed last
        disasters.sort(Comparator.comparing(Disaster::getStatus)
                .thenComparing(Comparator.comparing(Disaster::getPriority).reversed()));

        ObservableList<Disaster> disasterData = FXCollections.observableArrayList(disasters);
        disasterTable.setItems(disasterData);
    }

    /**
     * Handles sending initial resources to a disaster. Updates the disaster's
     * status to "In Progress" and records a message provided by the user.
     */
    @FXML
    private void handleSendResources() {
        Disaster selectedDisaster = disasterTable.getSelectionModel().getSelectedItem();
        if (selectedDisaster != null) {

            // Show dialog to enter message before sending resources
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Send Resources");
            dialog.setHeaderText("Enter a message before sending resources.");
            dialog.setContentText("Message:");

            Optional<String> result = dialog.showAndWait();

            // Update disaster status to "In Progress" with the message
            result.ifPresent(message -> {
                selectedDisaster.setStatus("In Progress");
                disasterService.updateDisasterStatus(selectedDisaster.getId(), "In Progress", message);

                sendResourcesButton.setDisable(true);
                approveButton.setDisable(true);
                rejectButton.setDisable(true);
                loadDisasters();// Refresh the disaster table
            });
        }
    }

    /**
     * Handles closing a disaster that is in "In Progress" status. Updates the
     * disaster's status to "Closed" and records a closing message provided by
     * the user.
     */
    @FXML
    private void handleCloseDisaster() {
        Disaster selectedDisaster = disasterTable.getSelectionModel().getSelectedItem();
        if (selectedDisaster != null && "In Progress".equals(selectedDisaster.getStatus())) {

            // Show dialog to enter closing message
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Close Disaster");
            dialog.setHeaderText("Enter a closing message before marking the disaster as closed.");
            dialog.setContentText("Closing Message:");

            Optional<String> result = dialog.showAndWait();

            // Update disaster status to "Closed" with the closing message
            result.ifPresent(message -> {
                // Proceed with marking the disaster as "Closed" and adding the message
                selectedDisaster.setStatus("Closed");
                disasterService.updateDisasterStatus(selectedDisaster.getId(), "Closed", message);

                closeDisasterButton.setDisable(true);
                approveButton.setDisable(true);
                rejectButton.setDisable(true);
                loadDisasters();  // Refresh the table
            });
        }
    }

    /**
     * Reloads the list of disasters and refreshes the disaster table.
     */
    @FXML
    private void handleRefresh() {
        loadDisasters();
    }

    /**
     * Loads disaster messages for the selected disaster based on its ID.
     *
     * @param disasterId the ID of the selected disaster
     */
    private void loadDisasterMessages(int disasterId) {
        List<DisasterMessage> requestMessages = disasterService.getRequestMessagesByDisasterId(disasterId, "[REQUEST]");
        messageTable.getItems().setAll(requestMessages);
    }

    /**
     * Approves the selected disaster message. The message is updated with an
     * "[APPROVE]" prefix, and the disaster message approval status is set to 1
     * (approved).
     */
    @FXML
    private void handleApprove() {
        DisasterMessage selectedMessage = messageTable.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            selectedMessage.setDisasterId(disasterTable.getSelectionModel().getSelectedItem().getId());
            // Show confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Approve Request");
            alert.setHeaderText("Are you sure you want to approve this request?");
            Optional<ButtonType> result = alert.showAndWait();

            // If confirmed, approve the request
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Show an alert with the approval message
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setTitle("Approval");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Request approved successfully.");
                infoAlert.showAndWait();

                // Prefix message with [APPROVE] and update approval status to 1
                disasterService.updateDisasterMessageStatus(selectedMessage, "[APPROVE]", 1);
                messageTable.getItems().clear();
            }
        }
    }

    /**
     * Rejects the selected disaster message. The message is updated with a
     * "[REJECT]" prefix, and the disaster message approval status is set to 0
     * (rejected).
     */
    @FXML
    private void handleReject() {
        DisasterMessage selectedMessage = messageTable.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            selectedMessage.setDisasterId(disasterTable.getSelectionModel().getSelectedItem().getId());
            // Show confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Reject Request");
            alert.setHeaderText("Are you sure you want to reject this request?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Show an alert with the rejection message
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setTitle("Rejection");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Request rejected successfully.");
                infoAlert.showAndWait();

                // Prefix message with [REJECT] and update approval status to 0
                disasterService.updateDisasterMessageStatus(selectedMessage, "[REJECT]", 0);
                messageTable.getItems().clear();
            }
        }
    }
}
