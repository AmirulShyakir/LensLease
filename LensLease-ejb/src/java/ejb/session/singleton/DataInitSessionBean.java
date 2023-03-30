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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.UserNotFoundException;

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
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

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
             try {
                 ArrayList<String> photos = new ArrayList();
                 photos.add("/studio-image.jpg");
                 
                 Service equipmentRental = new Service("Camera Rental", ServiceTypeEnum.EQUIPMENT_RENTAL, 100.00 , photos, false,userSessionBean.findUserByUserId(new Long(1)));
                 equipmentRental.setEarliestCollectionTime("9am");
                 equipmentRental.setLatestReturnTime("10pm");
                 equipmentRental.setDescription("Description Camera Rental Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ")
                 
                 Service photography = new Service("Wedding Photoshoot", ServiceTypeEnum.PHOTOGRAPHY, 1000.00, photos, false,userSessionBean.findUserByUserId(new Long(2)));
                 photography.setPackageDurationHours("6 hours");
                 photography.setDescription("Description Wedding Photoshoot Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ")

                 Service photoEditing = new Service("Photoshop Services", ServiceTypeEnum.PHOTO_EDITING, 50.00, photos, false, userSessionBean.findUserByUserId(new Long(3)))
                 photoEditing.setDescription("Description Photoshop Services Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ")
                 
                 serviceSessionBeanLocal.createNewService(equipmentRental);
                 serviceSessionBeanLocal.createNewService(photography);    
                 serviceSessionBeanLocal.createNewService(photoEditing);
             } catch (UserNotFoundException ex) {
                 Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
