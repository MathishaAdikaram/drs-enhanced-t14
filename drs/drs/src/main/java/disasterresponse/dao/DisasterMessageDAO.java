package disasterresponse.dao;

import disasterresponse.models.DisasterMessage;
import disasterresponse.services.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DisasterMessageDAO} class provides data access methods for the
 * {@code disaster_messages} table in the database. It includes operations to
 * insert, retrieve, and update disaster messages.
 *
 * <p>
 * This class interacts with the database to manage disaster messages, including
 * inserting new messages, fetching messages related to specific disasters, and
 * updating the approval status of messages.</p>
 *
 * @see DatabaseUtil
 * @see DisasterMessage
 *
 * @author 12236202
 */
public class DisasterMessageDAO {

    /**
     * Inserts a new disaster message record into the database.
     *
     * @param disasterMessage The {@code DisasterMessage} object containing the
     * details of the message to be inserted.
     */
    public void insertDisasterMessage(DisasterMessage disasterMessage) {
        String sql = "INSERT INTO disaster_messages (disasterId, departmentId, message, messageTime, messagedBy) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, disasterMessage.getDisasterId());
            stmt.setInt(2, disasterMessage.getDepartmentId());
            stmt.setString(3, disasterMessage.getMessage());
            stmt.setInt(4, disasterMessage.getMessagedBy());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all disaster messages for a given disaster from the database.
     *
     * @param disasterId The ID of the disaster whose messages are to be
     * retrieved.
     * @return A {@code List} of {@code DisasterMessage} objects associated with
     * the specified disaster.
     */
    public List<DisasterMessage> getDisasterMessages(int disasterId) {
        List<DisasterMessage> messages = new ArrayList<>();
        String sql = "SELECT dm.messageTime, d.department_name, ur.role_name, u.first_name, u.last_name, dm.message "
                + "FROM disaster_messages dm "
                + "JOIN departments d ON dm.departmentId = d.department_id "
                + "JOIN users u ON dm.messagedBy = u.user_id "
                + "JOIN user_roles ur ON u.user_role = ur.role_id "
                + "WHERE dm.disasterId = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, disasterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DisasterMessage message = new DisasterMessage();
                message.setMessageTime(rs.getTimestamp("messageTime").toLocalDateTime());
                message.setDepartmentName(rs.getString("department_name"));
                message.setRoleName(rs.getString("role_name"));
                message.setMessagedByFullName(rs.getString("first_name") + " " + rs.getString("last_name"));
                message.setMessage(rs.getString("message"));

                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    /**
     * Retrieves disaster messages that match a specific prefix for a given
     * disaster.
     *
     * @param disasterId The ID of the disaster whose messages are to be
     * retrieved.
     * @param prefix The prefix to filter the messages by.
     * @return A {@code List} of {@code DisasterMessage} objects matching the
     * specified prefix.
     */
    public List<DisasterMessage> getMessagesByPrefix(int disasterId, String prefix) {
        List<DisasterMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM disaster_messages WHERE disasterId = ? AND approval_status IS NULL AND message LIKE ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, disasterId);
            stmt.setString(2, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DisasterMessage message = new DisasterMessage();
                message.setId(rs.getInt("id"));
                message.setMessageTime(rs.getTimestamp("messageTime").toLocalDateTime());
                message.setDepartmentName(rs.getString("departmentId"));
                message.setMessage(rs.getString("message"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    /**
     * Updates the approval status of a disaster message in the database.
     *
     * @param disasterMessage The {@code DisasterMessage} object containing the
     * ID and new approval status to update.
     */
    public void updateDisasterMessage(DisasterMessage disasterMessage) {
        String sql = "UPDATE disaster_messages SET approval_status = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, disasterMessage.getApprovalStatus());
            stmt.setInt(2, disasterMessage.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
