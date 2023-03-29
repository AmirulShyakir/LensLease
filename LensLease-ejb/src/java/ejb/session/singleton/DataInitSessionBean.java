/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ForumSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.Service;
import entity.ServiceTypeEnum;
import entity.User;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Amirul
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private AdminSessionBeanLocal adminSessionBean;
    
    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PostConstruct 
    public void postConstruct() {
        if (em.find(Admin.class, 1l) == null) {
            adminSessionBean.createNewAdmin(new Admin("Admin", "admin@gmail.com", "password"));
        }
        if (em.find(User.class, 1l) == null) {
            userSessionBean.createNewUser(new User("Amirul", "amirul@gmail.com", "98553644", "Amirul", "password"));
            userSessionBean.createNewUser(new User("Adriel", "adriel@gmail.com", "91111111", "Adriel", "password"));
            userSessionBean.createNewUser(new User("Jonathan", "jonathan@gmail.com", "91111112", "Jonathan",  "password"));
            userSessionBean.createNewUser(new User("Jun Wei", "junwei@gmail.com","82226727", "Jun Wei",  "password"));
            userSessionBean.createNewUser(new User("Leeann", "leeann@gmail.com", "83685686", "Leeann", "password"));
        }
        if (em.find(Service.class, 1l) == null) {
            serviceSessionBean.createNewService(new Service("Service Name 1", 100, ServiceTypeEnum.EQUIPMENT_RENTAL, "Description 1"));
            serviceSessionBean.createNewService(new Service("Photography", 200, ServiceTypeEnum.PHOTOGRAPHY, "Description 1"));
            serviceSessionBean.createNewService(new Service("Photo Editing", 300, ServiceTypeEnum.PHOTO_EDITING, "Description 1"));
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
