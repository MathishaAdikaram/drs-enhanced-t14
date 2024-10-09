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
import javafx.scene.control.TableRow;

/**
 * The {@code ViewAllDisastersController} class manages the display of disasters
 * and their updates in the application. It provides functionality to view all
 * in-progress disasters and their associated messages.
 *
 * <p>
 * This controller allows users to see detailed information about disasters and
 * their updates. It provides a table view for disasters and another for
 * disaster messages. Selecting a disaster in the disaster table will display
 * its associated messages in the update table.</p>
 *
 * <p>
 * Access to disaster data is handled through the {@code DisasterService} and
 * user details are retrieved via {@code SessionDetails}.</p>
 *
 * @see Disaster
 * @see DisasterMessage
 * @see DisasterService
 * @see SessionDetails
 * @see TableView
 * @see TableColumn
 *
 * @author 12236202
 */
public class ViewAllDisastersController {

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

    private final DisasterService disasterService;

    /**
     * Constructor for {@code ViewAllDisastersController}. Initializes the
     * {@code DisasterService} instance for interacting with disaster data.
     */
    public ViewAllDisastersController() {
        this.disasterService = new DisasterService();
    }

    /**
     * Initializes the controller by setting up table columns, configuring row
     * click events, and loading in-progress disasters into the disaster table.
     */
    @FXML
    public void initialize() {
        // Get the logged-in user's role
        User loggedInUser = SessionDetails.getInstance().getLoggedInUser();
        String userRole = loggedInUser.getUserRoleName();

        // Configure disaster table columns
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        severityColumn.setCellValueFactory(new PropertyValueFactory<>("severity"));
        timeSinceColumn.setCellValueFactory(new PropertyValueFactory<>("timeSinceReporting"));

        // Configure disaster message table columns
        timeOfUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("messageTime"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("messagedByFullName"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        // Load in-progress disasters
        loadInProgressDisasters();

        // Set row factory for disaster table to handle row clicks
        disasterTable.setRowFactory(tv -> {
            TableRow<Disaster> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Disaster selectedDisaster = row.getItem();
                    loadDisasterUpdates(selectedDisaster.getId());
                }
            });
            return row;
        });
    }

    /**
     * Loads all in-progress disasters and populates the disaster table.
     */
    private void loadInProgressDisasters() {
        
        updateTable.getItems().clear();
        List<Disaster> inProgressDisasters = disasterService.getDisastersByStatus("ALL");
        ObservableList<Disaster> disasterData = FXCollections.observableArrayList(inProgressDisasters);
        disasterTable.setItems(disasterData);
    }

    /**
     * Loads the updates for a specific disaster and populates the update table.
     *
     * @param disasterId The ID of the disaster for which updates are to be
     * loaded.
     */
    private void loadDisasterUpdates(int disasterId) {
        
        List<DisasterMessage> disasterMessages = disasterService.getDisasterMessages(disasterId);
        ObservableList<DisasterMessage> updateData = FXCollections.observableArrayList(disasterMessages);
        updateTable.setItems(updateData);
    }

    /**
     * Refreshes the disaster table by reloading all in-progress disasters.
     */
    @FXML
    private void handleRefresh() {
        loadInProgressDisasters();
    }
}
