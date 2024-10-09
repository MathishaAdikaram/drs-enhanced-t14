package disasterresponse.models;

import java.time.LocalDateTime;

/**
 * The {@code DisasterMessage} class represents a message related to a disaster
 * event. It contains details about the message, including the sender, the
 * department involved, the time the message was sent, and its approval status.
 *
 * <p>
 * This class serves as a model for managing and displaying disaster-related
 * messages within the application.</p>
 *
 * @see disasterresponse.dao.DisasterMessageDAO
 * @see disasterresponse.services.DisasterMessageService
 *
 * @author 12236202
 */
public class DisasterMessage {

    private int id;
    private int disasterId;
    private int departmentId;
    private String message;
    private int messagedBy;
    private String departmentName;
    private String messagedByFullName;
    private LocalDateTime messageTime;
    private String roleName;
    private int approvalStatus;

    // Getters and Setters
    /**
     * Gets the unique identifier of the disaster message.
     *
     * @return The unique identifier of the disaster message.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the disaster message.
     *
     * @param id The unique identifier of the disaster message.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier of the associated disaster.
     *
     * @return The unique identifier of the associated disaster.
     */
    public int getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the unique identifier of the associated disaster.
     *
     * @param disasterId The unique identifier of the associated disaster.
     */
    public void setDisasterId(int disasterId) {
        this.disasterId = disasterId;
    }

    /**
     * Gets the unique identifier of the department that sent the message.
     *
     * @return The unique identifier of the department.
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the unique identifier of the department that sent the message.
     *
     * @param departmentId The unique identifier of the department.
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the content of the message.
     *
     * @return The content of the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the message.
     *
     * @param message The content of the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the unique identifier of the user who sent the message.
     *
     * @return The unique identifier of the user who sent the message.
     */
    public int getMessagedBy() {
        return messagedBy;
    }

    /**
     * Sets the unique identifier of the user who sent the message.
     *
     * @param messagedBy The unique identifier of the user who sent the message.
     */
    public void setMessagedBy(int messagedBy) {
        this.messagedBy = messagedBy;
    }

    /**
     * Gets the name of the department that sent the message.
     *
     * @return The name of the department that sent the message.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the name of the department that sent the message.
     *
     * @param departmentName The name of the department that sent the message.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Gets the full name of the user who sent the message.
     *
     * @return The full name of the user who sent the message.
     */
    public String getMessagedByFullName() {
        return messagedByFullName;
    }

    /**
     * Sets the full name of the user who sent the message.
     *
     * @param messagedByFullName The full name of the user who sent the message.
     */
    public void setMessagedByFullName(String messagedByFullName) {
        this.messagedByFullName = messagedByFullName;
    }

    /**
     * Gets the date and time when the message was sent.
     *
     * @return The date and time when the message was sent.
     */
    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    /**
     * Sets the date and time when the message was sent.
     *
     * @param messageTime The date and time when the message was sent.
     */
    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * Gets the name of the role of the user who sent the message.
     *
     * @return The name of the role of the user who sent the message.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the name of the role of the user who sent the message.
     *
     * @param roleName The name of the role of the user who sent the message.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the approval status of the message.
     *
     * @return The approval status of the message.
     */
    public int getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * Sets the approval status of the message.
     *
     * @param approvalStatus The approval status of the message.
     */
    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
