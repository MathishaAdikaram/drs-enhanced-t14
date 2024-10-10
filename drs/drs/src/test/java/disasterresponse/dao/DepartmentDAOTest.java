package au.pathum.disasterresponse.dao;

import au.pathum.disasterresponse.services.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentDAOTest {

    private DepartmentDAO departmentDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize the DAO
        departmentDAO = new DepartmentDAO();

        // Mock the database-related objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Setup mock behavior for the connection and statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void testGetDepartmentById_Found() throws SQLException {
        // Mock the DatabaseUtil to return the mocked connection
        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);

            // Mock result set behavior: returning a department name
            when(mockResultSet.next()).thenReturn(true);  // Simulate that there is a result
            when(mockResultSet.getString("department_name")).thenReturn("IT Department");

            // Act
            String departmentName = departmentDAO.getDepartmentById(1);

            // Assert
            assertNotNull(departmentName);
            assertEquals("IT Department", departmentName);

            // Verify interactions
            verify(mockPreparedStatement, times(1)).setInt(1, 1);
            verify(mockPreparedStatement, times(1)).executeQuery();
        }
    }

    @Test
    void testGetDepartmentById_NotFound() throws SQLException {
        // Mock the DatabaseUtil to return the mocked connection
        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);

            // Mock result set behavior: no department found
            when(mockResultSet.next()).thenReturn(false);  // Simulate no results

            // Act
            String departmentName = departmentDAO.getDepartmentById(1);

            // Assert
            assertNull(departmentName);  // Should return null since no result was found

            // Verify interactions
            verify(mockPreparedStatement, times(1)).setInt(1, 1);
            verify(mockPreparedStatement, times(1)).executeQuery();
        }
    }

    @Test
    void testGetDepartmentById_SQLException() throws SQLException {
        // Mock the DatabaseUtil to return the mocked connection
        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);

            // Mock the prepared statement to throw an exception
            when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

            // Act
            String departmentName = departmentDAO.getDepartmentById(1);

            // Assert
            assertNull(departmentName);  // Should return null due to exception

            // Verify that the prepared statement was used correctly
            verify(mockPreparedStatement, times(1)).setInt(1, 1);
        }
    }
}
