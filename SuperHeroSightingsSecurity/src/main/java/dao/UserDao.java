/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.User;

public interface UserDao {

    public User addUser(User newUser);

    public void deleteUser(long userId);

    public List<User> getAllUsers();

    public void editUser(User editUser);

    public User getUserById(long contactId);

    public void changeUserStatus(long userId);
}
