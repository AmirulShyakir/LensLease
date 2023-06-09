/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Booking;
import entity.Schedule;
import entity.Service;
import entity.ServiceTypeEnum;
import entity.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createNewServiceProvided(Long userId, String name, int serviceType, double cost, String description, String collectionTime, String returnTime, String packageDuration, String imageURL) {
        User user = em.find(User.class, userId);
        Service service = new Service();
        service.setServiceName(name);
        service.getServicePhotos().set(0, imageURL);
        service.setServiceType(ServiceTypeEnum.values()[serviceType]);
        service.setPackageDurationHours(packageDuration);
        if (service.getServiceType() == ServiceTypeEnum.EQUIPMENT_RENTAL) {
            service.setIsRental(true);
            service.setPackageDurationHours("Full Day Rental");
        }
        service.setServiceCost(cost);
        service.setServiceDescription(description);
        service.setEarliestCollectionTime(collectionTime);
        service.setLatestReturnTime(returnTime);
        user.getServices().add(service);
        service.setProvider(user);
        em.persist(service);
        em.flush();
    }

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
        oldService.setPackageDurationHours(service.getPackageDurationHours());
        oldService.setEarliestCollectionTime(service.getEarliestCollectionTime());
        oldService.setLatestReturnTime(service.getLatestReturnTime());
        oldService.setServiceDescription(service.getServiceDescription());
        oldService.setServiceCost(service.getServiceCost());
        oldService.setServicePhotos(service.getServicePhotos());
        oldService.setIsBanned(service.isBanned());
        oldService.setIsDelisted(service.isDelisted());
        oldService.setBookings(service.getBookings());
        oldService.setSchedule(service.getSchedule());
    }

    @Override
    public void delistService(Long serviceId) throws ServiceNotFoundException {
        Service service = findServiceByServiceId(serviceId);
        service.setIsDelisted(true);
    }

    @Override
    public void relistService(Long serviceId) throws ServiceNotFoundException {
        Service service = findServiceByServiceId(serviceId);
        service.setIsDelisted(false);
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
    public List<Service> getServicesByType(ServiceTypeEnum serviceType) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.serviceType = :serviceType AND s.isBanned = false ");
        query.setParameter("serviceType", serviceType);
//        query.setParameter("bool", false);

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
    public List<Service> getActiveServicesByUser(Long userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);
        List<Service> active = new ArrayList<>();
        if (user != null) {
            List<Service> all = user.getServices();
            for (Service s : all) {
                if (!s.isDelisted()) {
                    active.add(s);
                }
            }
            return active;
        } else {
            throw new UserNotFoundException("No such user!");
        }
    }

    @Override
    public List<Service> getDelistedServicesByUser(Long userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);
        List<Service> active = new ArrayList<>();
        if (user != null) {
            List<Service> all = user.getServices();
            for (Service s : all) {
                if (s.isDelisted()) {
                    active.add(s);
                }
            }
            return active;
        } else {
            throw new UserNotFoundException("No such user!");
        }
    }

    @Override
    public List<Service> filterActiveServices(List<Service> services) {
        List<Service> active = new ArrayList<>();
        for (Service s : services) {
            if (!s.isDelisted()) {
                active.add(s);
            }
        }
        return active;
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
            throw new ServiceNotFoundException("Service not found with id " + serviceId);
        }
    }

    @Override
    public List<Service> searchServices(String name) {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT s FROM Service s WHERE "
                    + "LOWER(s.serviceName) LIKE :name");
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT s FROM Service s");
        }

        return q.getResultList();
    }

    @Override
    public List<Service> searchServicesWithType(String name, ServiceTypeEnum type) {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT s FROM Service s WHERE s.serviceType =:type AND s.isBanned = false AND "
                    + "LOWER(s.serviceName) LIKE :name");
            q.setParameter("type", type);
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT s FROM Service s WHERE service.serviceType =:type");
            q.setParameter("type", type);
        }

        return q.getResultList();
    }
}
