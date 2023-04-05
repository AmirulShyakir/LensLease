/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Service;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BanRequestAlreadyExistException;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author junwe
 */
@Stateless
public class BanRequestSessionBean implements BanRequestSessionBeanLocal {

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public void createNewBanRequest(BanRequest report) {
        em.persist(report);
        em.flush();
    }

    @Override
    public void submitNewBanRequest(BanRequest report, long serviceId) throws ServiceNotFoundException, BanRequestAlreadyExistException{
        Service service = serviceSessionBean.findServiceByServiceId(serviceId);
        if (!service.isBanned()) {
            createNewBanRequest(report);
            report.setService(service);
            service.getBanRequests().add(report);
        } else {
            throw new BanRequestAlreadyExistException("Ban Request already exist for this booking");
        }

    }

    @Override
    public BanRequest findBanRequestByBanRequestId(Long banRequestId) throws BanRequestAlreadyExistException {
        Query query = em.createQuery("SELECT b FROM BanRequest b WHERE b.banRequestId = :inBanRequestId");
        query.setParameter("inBanRequestId", banRequestId);
        query.setMaxResults(1);
        try {
            BanRequest report = (BanRequest) query.getSingleResult();
            return report;
        } catch (Exception e) {
            throw new BanRequestAlreadyExistException("Ban Request not found with id " + banRequestId);
        }
    }

    @Override
    public List<BanRequest> getBanRequestsByServiceId(long serviceId) throws UserNotFoundException, ServiceNotFoundException {
        Service s = serviceSessionBean.findServiceByServiceId(serviceId);
        return s.getBanRequests();
    }
}
