package com.boredom.boredorm.DAO;

import com.boredom.boredorm.DBConnection;
import com.boredom.boredorm.Models.TenantProfile;
import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.Models.Tenant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public List<Tenant> getAllTenants() {
        List<Tenant> tenants = new ArrayList<>();
        String query = "SELECT t.room_id, u.username AS full_name, t.status " +
                "FROM users u " +
                "LEFT JOIN tenants t ON u.userID = t.tenant_id " +
                "WHERE u.role = 'tenant'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String room = rs.getString("room_id");
                String status = rs.getString("status");
                tenants.add(new Tenant(
                        (room == null || room.isEmpty()) ? "N/A" : room,
                        rs.getString("full_name"),
                        (status == null || status.isEmpty()) ? "Pending" : status
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return tenants;
    }

    @Override
    public boolean updateTenantStatusByUsername(String username, String status) {
        String findIdQuery = "SELECT userID FROM users WHERE username = ? AND role = 'tenant'";
        String upsertQuery = "INSERT INTO tenants (tenant_id, status, first_name, last_name) VALUES (?, ?, '', '') " +
                "ON DUPLICATE KEY UPDATE status = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(findIdQuery)) {
            findStmt.setString(1, username);
            try (ResultSet rs = findStmt.executeQuery()) {
                if (rs.next()) {
                    int tenantId = rs.getInt("userID");
                    try (PreparedStatement upsertStmt = conn.prepareStatement(upsertQuery)) {
                        upsertStmt.setInt(1, tenantId);
                        upsertStmt.setString(2, status);
                        upsertStmt.setString(3, status);
                        return upsertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean createUser(User user) {
        // Updated to include roomNumber and leaseStatus
        String query = "INSERT INTO users (username, password, role, roomNumber, leaseStatus) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, (user.getRoomNumber() == null) ? "N/A" : user.getRoomNumber());
            stmt.setString(5, (user.getLeaseStatus() == null) ? "Pending" : user.getLeaseStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("userID"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("roomNumber"),
                            rs.getString("leaseStatus")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("roomNumber"),
                        rs.getString("leaseStatus")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return users;
    }

    @Override
    public boolean updateTenant(String oldUsername, String newUsername, String newStatus) {
        return updateTenantStatusByUsername(oldUsername, newStatus);
    }

    @Override public User getUserById(int userId) { return null; }
    @Override public boolean updateUser(User user) { return false; }
    @Override public boolean deleteUser(int userId) { return false; }
    @Override public TenantProfile getTenantProfileByUserId(int userId) { return null; }
    @Override public List<TenantProfile> getAllTenantProfiles() { return new ArrayList<>(); }
}