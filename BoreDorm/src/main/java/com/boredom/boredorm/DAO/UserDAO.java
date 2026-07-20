package com.boredom.boredorm.DAO;

import com.boredom.boredorm.Models.TenantProfile;
import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.Models.Tenant; // Ensure this is imported
import java.util.List;

public interface UserDAO {
    boolean createUser(User user);
    User getUserById(int userId);
    boolean updateTenantStatusByUsername(String username, String status);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(int userId);
    TenantProfile getTenantProfileByUserId(int userId);
    List<TenantProfile> getAllTenantProfiles();
    boolean updateTenant(String oldUsername, String newUsername, String newStatus);

    // Add this method
    List<Tenant> getAllTenants();
}