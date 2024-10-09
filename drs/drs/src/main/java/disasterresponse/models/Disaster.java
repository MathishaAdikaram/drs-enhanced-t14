package disasterresponse.models;

import java.time.LocalDateTime;

/**
 * The {@code Disaster} class represents a disaster event in the system. It
 * includes information about the disaster such as its type, location, severity,
 * description, status, and related metadata.
 *
 * <p>
 * This class serves as a model for storing and managing disaster-related data
 * throughout the application.</p>
 *
 * @see disasterresponse.dao.DisasterDAO
 * @see disasterresponse.services.DisasterService
 *
 * @author 12236202
 */
public class Disaster {

    private int id;
    private String type;
    private String location;
    private int severity;
    private String description;
    private String status;
    private int reportedBy;
    private LocalDateTime reportedAt;
    private int priority;
    private String timeSinceReporting;

    /**
     * Gets the ID of the disaster.
     *
     * @return The ID of the disaster.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the disaster.
     *
     * @param id The ID of the disaster.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of the disaster.
     *
     * @return The type of the disaster.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the disaster.
     *
     * @param type The type of the disaster.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the location of the disaster.
     *
     * @return The location of the disaster.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the disaster.
     *
     * @param location The location of the disaster.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the severity of the disaster.
     *
     * @return The severity of the disaster.
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Sets the severity of the disaster.
     *
     * @param severity The severity of the disaster.
     */
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    /**
     * Gets the description of the disaster.
     *
     * @return The description of the disaster.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the disaster.
     *
     * @param description The description of the disaster.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the status of the disaster.
     *
     * @return The status of the disaster.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the disaster.
     *
     * @param status The status of the disaster.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the ID of the user who reported the disaster.
     *
     * @return The ID of the user who reported the disaster.
     */
    public int getReportedBy() {
        return reportedBy;
    }

    /**
     * Sets the ID of the user who reported the disaster.
     *
     * @param reportedBy The ID of the user who reported the disaster.
     */
    public void setReportedBy(int reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * Gets the date and time when the disaster was reported.
     *
     * @return The date and time when the disaster was reported.
     */
    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    /**
     * Sets the date and time when the disaster was reported.
     *
     * @param reportedAt The date and time when the disaster was reported.
     */
    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    /**
     * Gets the priority of the disaster.
     *
     * @return The priority of the disaster.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the disaster.
     *
     * @param priority The priority of the disaster.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Gets the time elapsed since the disaster was reported.
     *
     * @return The time elapsed since the disaster was reported.
     */
    public String getTimeSinceReporting() {
        return timeSinceReporting;
    }

    /**
     * Sets the time elapsed since the disaster was reported.
     *
     * @param timeSinceReporting The time elapsed since the disaster was
     * reported.
     */
    public void setTimeSinceReporting(String timeSinceReporting) {
        this.timeSinceReporting = timeSinceReporting;
    }
}
