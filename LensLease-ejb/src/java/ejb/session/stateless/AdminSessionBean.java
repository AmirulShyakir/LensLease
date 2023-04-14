/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.BanRequest;
import entity.Booking;
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
import util.exception.BanRequestAlreadyExistException;
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
            Admin admin = (Admin) query.getSingleResult();
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
            Admin admin = (Admin) query.getSingleResult();
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
    public BanRequest findBanRequestById(Long banRequestId) {
        Query query = em.createQuery("SELECT a FROM BanRequest a WHERE a.banRequestId = :inBRId");
        query.setParameter("inBRId", banRequestId);
        query.setMaxResults(1);
        BanRequest banRequest = (BanRequest) query.getSingleResult();
        return banRequest;
    }

    //changes user/service ban to true, deletes ban request after
    @Override
    public void acceptBanRequest(Long banRequestId) throws UserNotFoundException, ServiceNotFoundException {
        BanRequest banRequest = findBanRequestById(banRequestId);
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
    
    
     public void unbanUser(Long userId) throws UserNotFoundException {
        User user = userSessionBeanLocal.findUserByUserId(userId);
        user.setIsBanned(false);
        
    }
     
     public void unbanService(Long serviceId) throws ServiceNotFoundException {
        Service service = serviceSessionBeanLocal.findServiceByServiceId(serviceId);
        service.setIsBanned(false);
        
    }

    @Override
    public void submitReportService(BanRequest banRequest, long serviceId, long complainantId) throws ReportNotMadeException, BanRequestAlreadyExistException {
        try {
            User complainer = userSessionBeanLocal.findUserByUserId(complainantId);
            Service service = serviceSessionBeanLocal.findServiceByServiceId(serviceId);
            boolean alreadyFiledBanRequest = false;
            for (int x = 0; x < service.getBanRequests().size(); x++) {
                if (service.getBanRequests().get(x).getComplainer().getUserId() == complainer.getUserId()) {
                    alreadyFiledBanRequest = true;
                }
            }
            if (!alreadyFiledBanRequest) {
                createNewBanRequest(banRequest);
                banRequest.setServiceToBan(service);
                service.getBanRequests().add(banRequest);
                banRequest.setComplainer(complainer);
            } else {
                throw new BanRequestAlreadyExistException("Ban Request already filed!");
            }

        } catch (Exception e) {
            throw new ReportNotMadeException("Report not made as " + e.getMessage());
        }
    }

    @Override
    public void submitNewBookingBanRequestAsUser(BanRequest report, long bookingId) throws ServiceNotFoundException, BanRequestAlreadyExistException {
        Booking booking = em.find(Booking.class, bookingId);
        User complainer = booking.getCustomer();
        User userToBan = booking.getService().getProvider();
        boolean alreadyFiledBanRequest = false;
        for (int x = 0; x < booking.getBanRequests().size(); x++) {
            if (booking.getBanRequests().get(x).getComplainer().getUserId() == complainer.getUserId()) {
                alreadyFiledBanRequest = true;
            }
        }
        if (!booking.getService().isBanned() && !alreadyFiledBanRequest) {
            createNewBanRequest(report);
            report.setBooking(booking);
            report.setComplainer(complainer);
            report.setUserToBan(userToBan);
            booking.getBanRequests().add(report);
        } else {
            throw new BanRequestAlreadyExistException("Ban Request already filed!");
        }
    }
    
    @Override
    public void submitNewBookingBanRequestAsProvider(BanRequest report, long bookingId) throws ServiceNotFoundException, BanRequestAlreadyExistException {
        Booking booking = em.find(Booking.class, bookingId);
        User complainer = booking.getService().getProvider();
        User userToBan = booking.getCustomer();
        boolean alreadyFiledBanRequest = false;
        for (int x = 0; x < booking.getBanRequests().size(); x++) {
            if (booking.getBanRequests().get(x).getComplainer().getUserId() == complainer.getUserId()) {
                alreadyFiledBanRequest = true;
            }
        }
        if (!booking.getService().isBanned() && !alreadyFiledBanRequest) {
            createNewBanRequest(report);
            report.setBooking(booking);
            report.setComplainer(complainer);
            report.setUserToBan(userToBan);
            booking.getBanRequests().add(report);
        } else {
            throw new BanRequestAlreadyExistException("Ban Request already filed!");
        }
    }
}
