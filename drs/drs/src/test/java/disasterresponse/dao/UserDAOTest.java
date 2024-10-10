package au.pathum.disasterresponse.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import au.pathum.disasterresponse.models.User;
import au.pathum.disasterresponse.services.DatabaseUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOTest {

    private UserDAO userDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        userDAO = new UserDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

//        // Mock the DatabaseUtil to return the mocked connection
//        MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class);
//        mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testGetUserByUsername_Success() throws SQLException {
        // Mock the DatabaseUtil to return the mocked connection
        MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class);
        mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        // Mocking resultSet behavior
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("user_id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn("testuser");
        when(mockResultSet.getString("first_name")).thenReturn("Test");
        when(mockResultSet.getString("last_name")).thenReturn("User");
        when(mockResultSet.getString("password")).thenReturn("password123");
        when(mockResultSet.getInt("user_role")).thenReturn(1);
        when(mockResultSet.getInt("department_id")).thenReturn(1);
        when(mockResultSet.getInt("created_by")).thenReturn(1);

        // Call the method
        User result = userDAO.getUserByUsername("testuser");

        // Verify the result
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());

        // Verify that the prepared statement was executed
        verify(mockPreparedStatement, times(1)).setString(1, "testuser");
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

//    @Test
//    public void testGetUserByUsername_NotFound() throws SQLException {
//        // Mock the DatabaseUtil to return the mocked connection
//        MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class);
//        mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        
//        // Mocking resultSet behavior for no results
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(false);
//
//        // Call the method
//        User result = userDAO.getUserByUsername("nonexistentuser");
//
//        // Verify the result
//        assertNull(result);
//
//        // Verify that the prepared statement was executed
//        verify(mockPreparedStatement, times(1)).setString(1, "nonexistentuser");
//        verify(mockPreparedStatement, times(1)).executeQuery();
//    }
}
