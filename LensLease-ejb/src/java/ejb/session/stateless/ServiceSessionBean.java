/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Schedule;
import entity.Service;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ServiceNotFoundException;

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
    public Service findServiceByServiceId(Long serviceId) throws ServiceNotFoundException {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.serviceId = :inServiceId");
        query.setParameter("inServiceId", serviceId);
        query.setMaxResults(1);
        try {
            Service service = (Service)query.getSingleResult();
            return service;
        } catch (Exception e) {
            throw new ServiceNotFoundException("Member not found with id " + serviceId);
        }
    }
}
