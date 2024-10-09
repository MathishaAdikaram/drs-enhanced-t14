package disasterresponse.dao;

import disasterresponse.models.Disaster;
import disasterresponse.services.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DisasterDAO} class provides data access methods for the
 * {@code disasters} table in the database. It includes operations to insert,
 * retrieve, and update disaster information.
 *
 * <p>
 * This class interacts with the database to manage disaster records, including
 * inserting new disasters, fetching all or filtered disaster records, and
 * updating the status of disasters.</p>
 *
 * @see DatabaseUtil
 * @see Disaster
 *
 * @author 12236202
 */
public class DisasterDAO {

    /**
     * Inserts a new disaster record into the database.
     *
     * @param disaster The {@code Disaster} object containing the details of the
     * disaster to be inserted.
     */
    public void insertDisaster(Disaster disaster) {
        String sql = "INSERT INTO disasters (type, location, severity, description, status, reported_by) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, disaster.getType());
            stmt.setString(2, disaster.getLocation());
            stmt.setInt(3, disaster.getSeverity());
            stmt.setString(4, disaster.getDescription());
            stmt.setString(5, disaster.getStatus());
            stmt.setInt(6, disaster.getReportedBy());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all disaster records from the database.
     *
     * @return A {@code List} of {@code Disaster} objects representing all
     * disasters in the database.
     */
    public List<Disaster> getAllDisasters() {
        List<Disaster> disasters = new ArrayList<>();
        String sql = "SELECT * FROM disasters";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Disaster disaster = new Disaster();
                disaster.setId(rs.getInt("id"));
                disaster.setType(rs.getString("type"));
                disaster.setLocation(rs.getString("location"));
                disaster.setSeverity(rs.getInt("severity"));
                disaster.setDescription(rs.getString("description"));
                disaster.setStatus(rs.getString("status"));
                disaster.setReportedBy(rs.getInt("reported_by"));
                disaster.setReportedAt(rs.getTimestamp("reported_at").toLocalDateTime());

                // Calculate time since reporting
                disaster.setTimeSinceReporting(calculateTimeSince(rs.getTimestamp("reported_at").toLocalDateTime()));

                // Priority calculation based on type, severity, and time since reporting
                disaster.setPriority(calculatePriority(disaster));

                disasters.add(disaster);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disasters;
    }

    /**
     * Updates the status of a disaster record in the database.
     *
     * @param id The ID of the disaster whose status is to be updated.
     * @param status The new status of the disaster.
     */
    public void updateDisasterStatus(int id, String status) {
        String sql = "UPDATE disasters SET status = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves disaster records from the database filtered by status.
     *
     * @param status The status of the disasters to retrieve. Use "ALL" to
     * retrieve all disasters.
     * @return A {@code List} of {@code Disaster} objects matching the specified
     * status.
     */
    public List<Disaster> getDisastersByStatus(String status) {
        List<Disaster> disasters = new ArrayList<>();
        String sql = "SELECT * FROM disasters WHERE status = ?";
        if ("ALL".equals(status)) {
            sql = "SELECT * FROM disasters";
        }

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (!"ALL".equals(status)) {
                stmt.setString(1, status);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Disaster disaster = new Disaster();
                disaster.setId(rs.getInt("id"));
                disaster.setType(rs.getString("type"));
                disaster.setLocation(rs.getString("location"));
                disaster.setSeverity(rs.getInt("severity"));
                disaster.setStatus(rs.getString("status"));
                disaster.setReportedBy(rs.getInt("reported_by"));
                disaster.setReportedAt(rs.getTimestamp("reported_at").toLocalDateTime());

                // Calculate time since reporting
                disaster.setTimeSinceReporting(calculateTimeSince(rs.getTimestamp("reported_at").toLocalDateTime()));

                // Calculate priority (this logic can be refined based on your needs)
                disaster.setPriority(calculatePriority(disaster));

                disasters.add(disaster);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disasters;
    }

    /**
     * Calculates the time since the disaster was reported.
     *
     * @param reportedAt The {@code LocalDateTime} when the disaster was
     * reported.
     * @return A {@code String} representing the time since reporting in hours.
     */
    private String calculateTimeSince(LocalDateTime reportedAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(reportedAt, now);
        long hours = duration.toHours();
        return hours + " hours ago";
    }

    /**
     * Calculates the priority of the disaster based on its severity and time
     * since reporting.
     *
     * @param disaster The {@code Disaster} object for which the priority is
     * calculated.
     * @return An {@code int} representing the calculated priority of the
     * disaster.
     */
    private int calculatePriority(Disaster disaster) {
        int severityWeight = disaster.getSeverity();
        long timeSinceHours = Duration.between(disaster.getReportedAt(), LocalDateTime.now()).toHours();
        return severityWeight * 100 - (int) timeSinceHours;
    }
}
