/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Schedule;
import entity.Service;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Stateless
public class ServiceSessionBean implements ServiceSessionBeanLocal {

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createNewService(Service service) {
        em.persist(service);
        em.flush();
    }

    @Override
    public void editService(Service service) throws ServiceNotFoundException {
        Service oldService = findServiceByServiceId(service.getServiceId());
        
        oldService.setServiceName(service.getServiceName());
        oldService.setServiceType(service.getServiceType());
        oldService.setServiceCost(service.getServiceCost());
        oldService.setServicePhotos(service.getServicePhotos());
        oldService.setIsBanned(service.isIsBanned());
        oldService.setBookings(service.getBookings());
        oldService.setSchedule(service.getSchedule());
    }

    @Override
    public void deleteService(Long serviceId) throws ServiceNotFoundException { //this is incomplete
        Service service = findServiceByServiceId(serviceId);
        //need to add all the disassociation of entities
        em.remove(service);

    }

    @Override
    public void createNewSchedule(Schedule schedule) {
        em.persist(schedule);
        em.flush();
    }

    @Override
    public void createNewBanRequest(BanRequest ban) {
        em.persist(ban);
        em.flush();
    }

    @Override
    public List<Service> getAllServices() {
        Query query = em.createQuery("SELECT s FROM Service s");
        return query.getResultList();
    }

    @Override
    public List<Service> getServicesByUser(Long userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);
        if (user != null) {
            return user.getServices();
        } else {
            throw new UserNotFoundException("No such user!");
        }
    }

    @Override
    public Service findServiceByServiceId(Long serviceId) throws ServiceNotFoundException {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.serviceId = :inServiceId");
        query.setParameter("inServiceId", serviceId);
        query.setMaxResults(1);
        try {
            Service service = (Service) query.getSingleResult();
            return service;
        } catch (Exception e) {
            throw new ServiceNotFoundException("Member not found with id " + serviceId);
        }
    }
}
