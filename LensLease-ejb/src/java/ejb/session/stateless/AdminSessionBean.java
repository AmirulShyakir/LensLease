/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.BanRequest;
import entity.Service;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.ReportNotMadeException;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

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
    @EJB
    UserSessionBeanLocal userSessionBeanLocal;
    
    @EJB
    ServiceSessionBeanLocal serviceSessionBeanLocal;
    
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

    @Override
    public List<BanRequest> getAllBanRequests() {
        Query query = em.createQuery("SELECT b FROM BanRequest b WHERE b.isAttendedTo = false");
        return query.getResultList();
    }
   
    
    @Override
    public List<BanRequest> getPastBanRequests() {
        Query query = em.createQuery("SELECT b FROM BanRequest b WHERE b.isAttendedTo = true");
        return query.getResultList();
    }
    
    @Override
    public void createNewBanRequest(BanRequest banRequest) {
        em.persist(banRequest);
        em.flush();
    }
    @Override
    public BanRequest findBanRequestById(Long banRequestId)  {
        Query query = em.createQuery("SELECT a FROM BanRequest a WHERE a.banRequestId = :inBRId");
        query.setParameter("inBRId", banRequestId);
        query.setMaxResults(1);
            BanRequest banRequest = (BanRequest) query.getSingleResult();
            return banRequest;
    }
    
    //changes user/service ban to true, deletes ban request after
    @Override
    public void acceptBanRequest(Long banRequestId) throws UserNotFoundException, ServiceNotFoundException{
        BanRequest banRequest= findBanRequestById(banRequestId);
        Service service = banRequest.getServiceToBan();
        User user;
        if (service == null) {
            user = banRequest.getUserToBan();
            User userToBan = userSessionBeanLocal.findUserByUserId(user.getUserId());
            userToBan.setIsBanned(true);
        } else {
            Service serviceToBan = serviceSessionBeanLocal.findServiceByServiceId(service.getServiceId());
            serviceToBan.setIsBanned(true);
        }  
        banRequest.setIsAttendedTo(true);
        banRequest.setIsRejected(false);
    }
    
    @Override
    public void rejectBanRequest(Long banRequestId) throws UserNotFoundException, ServiceNotFoundException {
        BanRequest banRequest = findBanRequestById(banRequestId);

        banRequest.setIsAttendedTo(true);
        banRequest.setIsRejected(true);
    }
    
    @Override
    public void submitReportService(BanRequest banRequest, long serviceId, long complainantId) throws ReportNotMadeException {
        try {
            User complainant = userSessionBeanLocal.findUserByUserId(complainantId);
            Service service = serviceSessionBeanLocal.findServiceByServiceId(serviceId);
            createNewBanRequest(banRequest);
            banRequest.setService(service);
            service.getBanRequests().add(banRequest);
            banRequest.setComplainant(complainant);
        } catch (Exception e) {
            throw new ReportNotMadeException("Report not made as " + e.getMessage());
        }
       
        
    }
}
