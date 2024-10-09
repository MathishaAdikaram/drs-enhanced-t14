package disasterresponse.services;

import disasterresponse.dao.DisasterDAO;
import disasterresponse.dao.DisasterMessageDAO;
import disasterresponse.models.Disaster;
import disasterresponse.models.DisasterMessage;
import java.util.List;

/**
 * The {@code DisasterService} class provides methods for managing disaster
 * reports and messages. It interacts with {@code DisasterDAO} and
 * {@code DisasterMessageDAO} to perform CRUD operations related to disasters
 * and disaster messages.
 *
 * <p>
 * This service layer helps in separating business logic from data access
 * logic.</p>
 *
 * @see disasterresponse.dao.DisasterDAO
 * @see disasterresponse.dao.DisasterMessageDAO
 * @see disasterresponse.models.Disaster
 * @see disasterresponse.models.DisasterMessage
 *
 * @author 12236202
 */
public class DisasterService {

    private final DisasterDAO disasterDAO;
    private final DisasterMessageDAO disasterMessageDAO;

    /**
     * Constructs a new {@code DisasterService} instance. Initializes the
     * {@code DisasterDAO} and {@code DisasterMessageDAO} objects.
     */
    public DisasterService() {
        this.disasterDAO = new DisasterDAO();
        this.disasterMessageDAO = new DisasterMessageDAO();
    }

    /**
     * Reports a new disaster by creating a {@code Disaster} object and
     * inserting it into the database using {@code DisasterDAO}.
     *
     * @param type The type of the disaster.
     * @param location The location where the disaster occurred.
     * @param severity The severity of the disaster.
     * @param description A description of the disaster.
     * @param reportedBy The user ID of the person reporting the disaster.
     */
    public void reportDisaster(String type, String location, int severity, String description, int reportedBy) {
        Disaster disaster = new Disaster();
        disaster.setType(type);
        disaster.setLocation(location);
        disaster.setSeverity(severity);
        disaster.setDescription(description);
        disaster.setStatus("Open");
        disaster.setReportedBy(reportedBy);

        disasterDAO.insertDisaster(disaster);
    }

    /**
     * Retrieves a list of all disasters from the database.
     *
     * @return A list of {@code Disaster} objects.
     */
    public List<Disaster> getAllDisasters() {
        return disasterDAO.getAllDisasters();
    }

    /**
     * Updates the status of a disaster and inserts a new message related to
     * that status update.
     *
     * @param id The ID of the disaster.
     * @param status The new status of the disaster.
     * @param message The message associated with the status update.
     */
    public void updateDisasterStatus(int id, String status, String message) {
        disasterDAO.updateDisasterStatus(id, status);

        DisasterMessage disasterMessage = new DisasterMessage();
        disasterMessage.setDisasterId(id);
        disasterMessage.setDepartmentId(SessionDetails.getInstance().getLoggedInUser().getDepartmentId());
        disasterMessage.setMessage(message);
        disasterMessage.setMessagedBy(SessionDetails.getInstance().getLoggedInUser().getUserId());

        disasterMessageDAO.insertDisasterMessage(disasterMessage);
    }

    /**
     * Adds a new message related to a disaster.
     *
     * @param id The ID of the disaster.
     * @param message The message to be added.
     */
    public void addMessage(int id, String message) {
        DisasterMessage disasterMessage = new DisasterMessage();
        disasterMessage.setDisasterId(id);
        disasterMessage.setDepartmentId(SessionDetails.getInstance().getLoggedInUser().getDepartmentId());
        disasterMessage.setMessage(message);
        disasterMessage.setMessagedBy(SessionDetails.getInstance().getLoggedInUser().getUserId());

        disasterMessageDAO.insertDisasterMessage(disasterMessage);
    }

    /**
     * Retrieves a list of disasters filtered by status.
     *
     * @param status The status to filter by.
     * @return A list of {@code Disaster} objects.
     */
    public List<Disaster> getDisastersByStatus(String status) {
        return disasterDAO.getDisastersByStatus(status);
    }

    /**
     * Retrieves a list of messages related to a specific disaster.
     *
     * @param disasterId The ID of the disaster.
     * @return A list of {@code DisasterMessage} objects.
     */
    public List<DisasterMessage> getDisasterMessages(int disasterId) {
        return disasterMessageDAO.getDisasterMessages(disasterId);
    }

    /**
     * Retrieves a list of messages related to a disaster filtered by a prefix.
     *
     * @param disasterId The ID of the disaster.
     * @param prefix The prefix to filter messages by.
     * @return A list of {@code DisasterMessage} objects.
     */
    public List<DisasterMessage> getRequestMessagesByDisasterId(int disasterId, String prefix) {
        return disasterMessageDAO.getMessagesByPrefix(disasterId, prefix);
    }

    /**
     * Updates the approval status of a disaster message and adds a new message
     * with a prefix indicating the approval.
     *
     * @param disasterMessage The {@code DisasterMessage} object to be updated.
     * @param prefix The prefix to be added to the new message.
     * @param approvalStatus The new approval status.
     */
    public void updateDisasterMessageStatus(DisasterMessage disasterMessage, String prefix, int approvalStatus) {
        disasterMessage.setApprovalStatus(approvalStatus);

        disasterMessageDAO.updateDisasterMessage(disasterMessage);

        DisasterMessage disasterMessage2 = new DisasterMessage();
        disasterMessage2.setDisasterId(disasterMessage.getDisasterId());
        disasterMessage2.setDepartmentId(SessionDetails.getInstance().getLoggedInUser().getDepartmentId());
        disasterMessage2.setMessage(prefix + " " + disasterMessage.getMessage());
        disasterMessage2.setMessagedBy(SessionDetails.getInstance().getLoggedInUser().getUserId());

        disasterMessageDAO.insertDisasterMessage(disasterMessage2);
    }
}
