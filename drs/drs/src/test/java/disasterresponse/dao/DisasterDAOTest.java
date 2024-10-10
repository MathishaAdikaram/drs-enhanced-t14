package au.pathum.disasterresponse.dao;

import au.pathum.disasterresponse.models.Disaster;
import au.pathum.disasterresponse.services.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

class DisasterDAOTest {

    private DisasterDAO disasterDAO;

    @BeforeEach
    void setUp() {
        disasterDAO = new DisasterDAO();
    }

    @Test
    void testInsertDisaster() throws SQLException {
        Disaster disaster = new Disaster();
        disaster.setType("Flood");
        disaster.setLocation("City A");
        disaster.setSeverity(5);
        disaster.setDescription("Severe flood");
        disaster.setStatus("Open");
        disaster.setReportedBy(1);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

            // Act
            disasterDAO.insertDisaster(disaster);

            // Assert
            verify(mockStatement, times(1)).setString(1, "Flood");
            verify(mockStatement, times(1)).setString(2, "City A");
            verify(mockStatement, times(1)).setInt(3, 5);
            verify(mockStatement, times(1)).setString(4, "Severe flood");
            verify(mockStatement, times(1)).setString(5, "Open");
            verify(mockStatement, times(1)).setInt(6, 1);
            verify(mockStatement, times(1)).executeUpdate();
        }
    }

    @Test
    void testGetAllDisasters() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);

            // Mock ResultSet
            when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Return one row
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("type")).thenReturn("Flood");
            when(mockResultSet.getString("location")).thenReturn("City A");
            when(mockResultSet.getInt("severity")).thenReturn(5);
            when(mockResultSet.getString("description")).thenReturn("Severe flood");
            when(mockResultSet.getString("status")).thenReturn("Open");
            when(mockResultSet.getInt("reported_by")).thenReturn(1);
            when(mockResultSet.getTimestamp("reported_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            // Act
            List<Disaster> disasters = disasterDAO.getAllDisasters();

            // Assert
            assertNotNull(disasters);
            assertEquals(1, disasters.size());
            Disaster disaster = disasters.get(0);
            assertEquals("Flood", disaster.getType());
            assertEquals("City A", disaster.getLocation());
            assertEquals(5, disaster.getSeverity());
            assertEquals("Severe flood", disaster.getDescription());
            assertEquals("Open", disaster.getStatus());
            assertEquals(1, disaster.getReportedBy());
        }
    }

    @Test
    void testUpdateDisasterStatus() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

            // Act
            disasterDAO.updateDisasterStatus(1, "Closed");

            // Assert
            verify(mockStatement, times(1)).setString(1, "Closed");
            verify(mockStatement, times(1)).setInt(2, 1);
            verify(mockStatement, times(1)).executeUpdate();
        }
    }
}
