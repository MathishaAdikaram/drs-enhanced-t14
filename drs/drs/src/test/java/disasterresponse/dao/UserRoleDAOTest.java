package au.pathum.disasterresponse.dao;

import au.pathum.disasterresponse.services.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleDAOTest {

    private UserRoleDAO userRoleDAO;

    @BeforeEach
    void setUp() {
        userRoleDAO = new UserRoleDAO();
    }

//    @Test
//    void testGetUserRoleById_ValidRoleId() throws SQLException {
//        // Arrange
//        int roleId = 1;
//        String expectedRoleName = "System Administrator";
//
//        Connection mockConnection = mock(Connection.class);
//        PreparedStatement mockStatement = mock(PreparedStatement.class);
//        ResultSet mockResultSet = mock(ResultSet.class);
//
//        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
//            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
//            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//            when(mockResultSet.next()).thenReturn(true);
//            when(mockResultSet.getString("role_name")).thenReturn(expectedRoleName);
//
//            // Act
//            String actualRoleName = userRoleDAO.getUserRoleById(roleId);
//
//            // Assert
//            assertEquals(expectedRoleName, actualRoleName);
//            verify(mockStatement).setInt(1, roleId);
//            verify(mockStatement).executeQuery();
//        }
//    }

//    @Test
//    void testGetUserRoleById_InvalidRoleId() throws SQLException {
//        // Arrange
//        int roleId = 999;  // Non-existent role ID
//        String expectedRoleName = null;
//
//        Connection mockConnection = mock(Connection.class);
//        PreparedStatement mockStatement = mock(PreparedStatement.class);
//        ResultSet mockResultSet = mock(ResultSet.class);
//
//        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
//            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
//            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//            when(mockResultSet.next()).thenReturn(false);  // Simulate no result for the role
//
//            // Act
//            String actualRoleName = userRoleDAO.getUserRoleById(roleId);
//
//            // Assert
//            assertNull(actualRoleName);
//            verify(mockStatement).setInt(1, roleId);
//            verify(mockStatement).executeQuery();
//        }
//    }

//    @Test
//    void testGetUserRoleById_SQLException() throws SQLException {
//        // Arrange
//        int roleId = 1;
//
//        Connection mockConnection = mock(Connection.class);
//        PreparedStatement mockStatement = mock(PreparedStatement.class);
//
//        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
//            mockedDatabaseUtil.when(DatabaseUtil::getConnection).thenReturn(mockConnection);
//            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
//            when(mockStatement.executeQuery()).thenThrow(new SQLException("Database error"));
//
//            // Act
//            String actualRoleName = userRoleDAO.getUserRoleById(roleId);
//
//            // Assert
//            assertNull(actualRoleName);
//            verify(mockStatement).setInt(1, roleId);
//            verify(mockStatement).executeQuery();
//        }
//    }
}
