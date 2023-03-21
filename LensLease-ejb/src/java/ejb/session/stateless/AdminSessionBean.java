/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginException;

/**
 *
 * @author Amirul
 */
@Stateless
public class AdminSessionBean implements AdminSessionBeanLocal {

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public void createNewAdmin(Admin admin) {
        em.persist(admin);
        em.flush();
    }
    
    @Override
    public List<Admin> getAllAdmins() {
        Query query = em.createQuery("SELECT a FROM Admin a");
        return query.getResultList();
    }
    
    @Override
    public Admin findAdminByAdminId(Long adminId) throws AdminNotFoundException {
        Query query = em.createQuery("SELECT a FROM Admin a WHERE a.adminId = :inAdminId");
        query.setParameter("inAdminId", adminId);
        query.setMaxResults(1);
        try {
            Admin admin = (Admin)query.getSingleResult();
            return admin;
        } catch (Exception e) {
            throw new AdminNotFoundException("Admin not found with id " + adminId);
        }
    }
    
    @Override
    public Admin findAdminByUsername(String username) throws AdminNotFoundException {
        Query query = em.createQuery("SELECT a FROM Admin a WHERE a.username = :inUsername");
        query.setParameter("inUsername", username);
        query.setMaxResults(1);
        try {
            Admin admin = (Admin)query.getSingleResult();
            return admin;
        } catch (Exception e) {
            throw new AdminNotFoundException("Admin not found with username " + username);
        }
    }
    
    @Override
    public Admin adminLogin(String username, String password) throws AdminNotFoundException, InvalidLoginException {
        List<Admin> admins = getAllAdmins();
        if (admins.isEmpty()) {
            throw new AdminNotFoundException("No Admin found in database");
        } else {
            for (Admin a : admins) {
                if (a.getUsername().equals(username)) {
                    if (a.getPassword().equals(password)) {
                        return a;
                    } else {
                        throw new InvalidLoginException("Wrong password");
                    }
                }
            }
            throw new InvalidLoginException("Invalid username");
        }
    }
}
