package com.boredom.boredorm.DAO;

import com.boredom.boredorm.Models.User;
import java.util.List;

public interface UserDAO {

    boolean createUser(User user);

    User getUserById(int userId);
    User getUserByUsername(String username);
    List<User> getAllUsers();


    boolean updateUser(User user);


    boolean deleteUser(int userId);
}