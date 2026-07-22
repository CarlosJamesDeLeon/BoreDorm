package com.boredom.boredorm.DAO;

import com.boredom.boredorm.DBConnection;
import com.boredom.boredorm.Models.MaintenanceRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of MaintenanceRequestDAO.
 * Handles all SQL operations against the maintenance_requests table.
 */
public class MaintenanceRequestDAOImpl implements MaintenanceRequestDAO {

    @Override
    public boolean submitRequest(MaintenanceRequest request) {
        String query = "INSERT INTO maintenance_requests " +
                "(tenant_user_id, tenant_username, room_number, issue_type, description, status, date_filed, assigned_staff) " +
                "VALUES (?, ?, ?, ?, ?, 'Pending', NOW(), 'Unassigned')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, request.getTenantUserId());
            stmt.setString(2, request.getTenantUsername());
            stmt.setString(3, request.getRoomNumber());
            stmt.setString(4, request.getIssueType());
            stmt.setString(5, request.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<MaintenanceRequest> getRequestsByUserId(int tenantUserId) {
        List<MaintenanceRequest> list = new ArrayList<>();
        String query = "SELECT * FROM maintenance_requests WHERE tenant_user_id = ? ORDER BY date_filed DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tenantUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<MaintenanceRequest> getAllRequests() {
        List<MaintenanceRequest> list = new ArrayList<>();
        String query = "SELECT * FROM maintenance_requests ORDER BY date_filed DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateRequestStatus(int requestId, String newStatus, String assignedStaff) {
        String query = "UPDATE maintenance_requests SET status = ?, assigned_staff = ? WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, assignedStaff);
            stmt.setInt(3, requestId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int countPendingRequests() {
        String query = "SELECT COUNT(*) FROM maintenance_requests WHERE status = 'Pending'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private MaintenanceRequest mapRow(ResultSet rs) throws SQLException {
        return new MaintenanceRequest(
                rs.getInt("request_id"),
                rs.getInt("tenant_user_id"),
                rs.getString("tenant_username"),
                rs.getString("room_number"),
                rs.getString("issue_type"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getString("date_filed") != null ? rs.getString("date_filed") : "",
                rs.getString("assigned_staff")
        );
    }
}
