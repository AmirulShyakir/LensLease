/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.ForumSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.ForumReply;
import entity.ForumTopic;
import entity.ForumTopicTagEnum;
import entity.Service;
import entity.ServiceTypeEnum;
import entity.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.ForumTopicNotFoundException;
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
    private ForumSessionBeanLocal forumSessionBean;

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
            userSessionBean.createNewUser(new User("Jonathan", "jonathan@gmail.com", "91111112", "Jonathan", "password"));
            userSessionBean.createNewUser(new User("Jun Wei", "junwei@gmail.com", "82226727", "Jun Wei", "password"));
            userSessionBean.createNewUser(new User("Leeann", "leeann@gmail.com", "83685686", "Leeann", "password"));
        }

        if (em.find(Service.class, 1l) == null) {
            try {
                ArrayList<String> photos = new ArrayList();
                photos.add("/studio-image.jpg");

                Service equipmentRental = new Service("Camera Rental", ServiceTypeEnum.EQUIPMENT_RENTAL, 100.00, photos, false, userSessionBean.findUserByUserId(new Long(1)));
                equipmentRental.setEarliestCollectionTime("9am");
                equipmentRental.setLatestReturnTime("10pm");
                equipmentRental.setServiceDescription("Description Camera Rental Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service photography = new Service("Wedding Photoshoot", ServiceTypeEnum.PHOTOGRAPHY, 1000.00, photos, false, userSessionBean.findUserByUserId(new Long(2)));
                photography.setPackageDurationHours("6 hours");
                photography.setServiceDescription("Description Wedding Photoshoot Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service photoEditing = new Service("Photoshop Services", ServiceTypeEnum.PHOTO_EDITING, 50.00, photos, false, userSessionBean.findUserByUserId(new Long(3)));
                photoEditing.setServiceDescription("Description Photoshop Services Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                serviceSessionBeanLocal.createNewService(equipmentRental);
                serviceSessionBeanLocal.createNewService(photography);
                serviceSessionBeanLocal.createNewService(photoEditing);
            } catch (UserNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Calendar calendar = Calendar.getInstance();
        Date thisInstance = calendar.getTime();
        if (em.find(ForumTopic.class, 1l) == null) {
            ArrayList<ForumTopicTagEnum> topics = new ArrayList<ForumTopicTagEnum>();

            topics.add(ForumTopicTagEnum.EQUIPMENT);
            topics.add(ForumTopicTagEnum.PHOTOGRAPHY);
            topics.add(ForumTopicTagEnum.TIPSANDADVICE);

            ArrayList<ForumTopicTagEnum> topics1 = new ArrayList<ForumTopicTagEnum>();
            topics1.add(ForumTopicTagEnum.VIDEOGRAPHY);
            topics1.add(ForumTopicTagEnum.VIDEOEDITING);
            topics1.add(ForumTopicTagEnum.TIPSANDADVICE);

            try {
                ForumTopic forumTopic1 = new ForumTopic("Test Topic", "Bing Bong Bing Bong Bing Bong ", userSessionBean.findUserByUserId(new Long(1)), topics, thisInstance);

                ForumTopic forumTopic2 = new ForumTopic("Test Topic 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, ", userSessionBean.findUserByUserId(new Long(2)),
                        topics1, thisInstance);
                forumSessionBean.createNewForumTopic(forumTopic1);
                forumSessionBean.createNewForumTopic(forumTopic2);
            } catch (UserNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (em.find(ForumReply.class, 1l) == null) {
            try {
                ForumReply forumReply1 = new ForumReply("Reply1 Reply1 Reply1", thisInstance, userSessionBean.findUserByUserId(new Long(1)), forumSessionBean.findForumTopicById(new Long(1)));
                ForumReply forumReply2 = new ForumReply("Reply2 Reply2 Reply2", thisInstance, userSessionBean.findUserByUserId(new Long(2)), forumSessionBean.findForumTopicById(new Long(1)));
                forumSessionBean.createNewForumReply(forumReply1);
                forumSessionBean.createNewForumReply(forumReply2);
            } catch (UserNotFoundException | ForumTopicNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("****");
        List<ForumTopic> forumTopics = forumSessionBean.getAllForumTopics();
        for (ForumTopic f:forumTopics) {
            List<ForumTopicTagEnum> tags = f.getTags();
            System.out.println(f.getTopicName());
            for (ForumTopicTagEnum tag:tags) {
                System.out.println(tag);
            }
        }
        System.out.println(forumSessionBean.searchForumTopicsByTags(ForumTopicTagEnum.EQUIPMENT));
        System.out.println(forumSessionBean.searchForumTopicsByTags(ForumTopicTagEnum.ALL));
        System.out.println(forumSessionBean.searchForumTopicsByTags(ForumTopicTagEnum.TIPSANDADVICE));
        System.out.println("****");

    }

    public void persist(Object object) {
        em.persist(object);
    }
}
