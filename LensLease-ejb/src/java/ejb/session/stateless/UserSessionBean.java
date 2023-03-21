/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Stateless
public class UserSessionBean implements UserSessionBeanLocal {

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public void createNewUser(User user) {
        em.persist(user);
        em.flush();
    }
    
    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }
    
    @Override
    public User findUserByUserId(Long userId) throws UserNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.userid = :inUserId");
        query.setParameter("inUserId", userId);
        query.setMaxResults(1);
        try {
            User user = (User)query.getSingleResult();
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with id " + userId);
        }
    }
    
    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :inUsername");
        query.setParameter("inUsername", username);
        query.setMaxResults(1);
        try {
            User user = (User)query.getSingleResult();
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with username " + username);
        }
    }
    
    @Override
    public User userLogin(String username, String password) throws UserNotFoundException, InvalidLoginException {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No Admin found in database");
        } else {
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    if (u.getPassword().equals(password)) {
                        return u;
                    } else {
                        throw new InvalidLoginException("Wrong password");
                    }
                }
            }
            throw new InvalidLoginException("Invalid username");
        }
    }
}
