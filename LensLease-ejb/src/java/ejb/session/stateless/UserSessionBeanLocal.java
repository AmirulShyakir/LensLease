/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import java.util.List;
import javax.ejb.Local;
import util.exception.InvalidLoginException;
import util.exception.UserAlreadyExistsException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface UserSessionBeanLocal {

    public void createNewUser(User user);

    public List<User> getAllUsers();

    public User findUserByUserId(Long userId) throws UserNotFoundException;

    public User findUserByUsername(String username) throws UserNotFoundException;

    public User userLogin(String username, String password) throws UserNotFoundException, InvalidLoginException;
    
    public Long userSignup(User user) throws UserAlreadyExistsException;

    public void changePassword(Long userId, String currentPassword, String newPassword) throws InvalidLoginException;

    public void updateDetails(Long userId, String name, String username, String email, String contactNumber, String photoURL);
    
}
