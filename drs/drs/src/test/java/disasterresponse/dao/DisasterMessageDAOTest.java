package au.pathum.disasterresponse.dao;

import au.pathum.disasterresponse.models.DisasterMessage;
import au.pathum.disasterresponse.services.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisasterMessageDAOTest {

    private DisasterMessageDAO disasterMessageDAO;

    @BeforeEach
    void setUp() {
        disasterMessageDAO = new DisasterMessageDAO();
    }

    @Test
    void testInsertDisasterMessage() throws SQLException {
        DisasterMessage disasterMessage = new DisasterMessage();
        disasterMessage.setDisasterId(1);
        disasterMessage.setDepartmentId(2);
        disasterMessage.setMessage("Test message");
        disasterMessage.setMessagedBy(3);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

            // Act
            disasterMessageDAO.insertDisasterMessage(disasterMessage);

            // Assert
            verify(mockStatement, times(1)).setInt(1, 1);
            verify(mockStatement, times(1)).setInt(2, 2);
            verify(mockStatement, times(1)).setString(3, "Test message");
            verify(mockStatement, times(1)).setInt(4, 3);
            verify(mockStatement, times(1)).executeUpdate();
        }
    }

    @Test
    void testGetDisasterMessages() throws SQLException {
        int disasterId = 1;

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true).thenReturn(false);

            // Set up the mock result set
            when(mockResultSet.getTimestamp("messageTime")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getString("department_name")).thenReturn("Fire Department");
            when(mockResultSet.getString("role_name")).thenReturn("Responder");
            when(mockResultSet.getString("first_name")).thenReturn("John");
            when(mockResultSet.getString("last_name")).thenReturn("Doe");
            when(mockResultSet.getString("message")).thenReturn("Test disaster message");

            // Act
            List<DisasterMessage> messages = disasterMessageDAO.getDisasterMessages(disasterId);

            // Assert
            assertNotNull(messages);
            assertEquals(1, messages.size());
            DisasterMessage message = messages.get(0);
            assertEquals("Fire Department", message.getDepartmentName());
            assertEquals("Responder", message.getRoleName());
            assertEquals("John Doe", message.getMessagedByFullName());
            assertEquals("Test disaster message", message.getMessage());
        }
    }

    @Test
    void testGetMessagesByPrefix() throws SQLException {
        int disasterId = 1;
        String prefix = "[REQUEST]";

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true).thenReturn(false);

            // Set up the mock result set
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getTimestamp("messageTime")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getString("departmentId")).thenReturn("Fire Department");
            when(mockResultSet.getString("message")).thenReturn("[REQUEST] Send resources");

            // Act
            List<DisasterMessage> messages = disasterMessageDAO.getMessagesByPrefix(disasterId, prefix);

            // Assert
            assertNotNull(messages);
            assertEquals(1, messages.size());
            DisasterMessage message = messages.get(0);
            assertEquals("[REQUEST] Send resources", message.getMessage());
        }
    }

    @Test
    void testUpdateDisasterMessage() throws SQLException {
        DisasterMessage disasterMessage = new DisasterMessage();
        disasterMessage.setId(1);
        disasterMessage.setApprovalStatus(1);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

            // Act
            disasterMessageDAO.updateDisasterMessage(disasterMessage);

            // Assert
            verify(mockStatement, times(1)).setInt(1, 1); // approvalStatus
            verify(mockStatement, times(1)).setInt(2, 1); // id
            verify(mockStatement, times(1)).executeUpdate();
        }
    }
}
