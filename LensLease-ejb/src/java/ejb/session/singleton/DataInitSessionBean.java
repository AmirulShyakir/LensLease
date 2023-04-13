/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.ForumSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.ForumReply;
import entity.ForumTopic;
import entity.ForumTopicTagEnum;
import entity.BanRequest;
import entity.Booking;
import entity.BookingStatusEnum;
import entity.Review;
import entity.Service;
import entity.ServiceTypeEnum;
import entity.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import util.exception.BookingNotFoundException;
import util.exception.ServiceNotFoundException;
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
    private ReviewSessionBeanLocal reviewSessionBean;

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

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
            User amirul = new User("Amirul", "amirul@gmail.com", "98553644", "Amirul", "password");
            amirul.setPhotoUrl("https://i.pravatar.cc/300?img=56");
            User adriel = new User("Adriel", "adriel@gmail.com", "91111111", "Adriel", "password");
            adriel.setPhotoUrl("https://i.pravatar.cc/300?img=6");
            User jonathan = new User("Jonathan", "jonathan@gmail.com", "91111112", "Jonathan", "password");
            jonathan.setPhotoUrl("https://i.pravatar.cc/300?img=14");
            User junwei = new User("Jun Wei", "junwei@gmail.com", "82226727", "Jun Wei", "password");
            junwei.setPhotoUrl("https://i.pravatar.cc/300?img=12");
            User leeann = new User("Leeann", "leeann@gmail.com", "83685686", "Leeann", "password");
            leeann.setPhotoUrl("https://i.pravatar.cc/300?img=31");
                    
            userSessionBean.createNewUser(amirul);
            userSessionBean.createNewUser(adriel);
            userSessionBean.createNewUser(jonathan);
            userSessionBean.createNewUser(junwei);
            userSessionBean.createNewUser(leeann);
        }

        if (em.find(Service.class, 1l) == null) {
            try {
                ArrayList<String> photos = new ArrayList();
                photos.add("/resources/images/studio-image.jpg");
                photos.add("/resources/images/photography-image.jpg");
                ArrayList<String> photos2 = new ArrayList();
                photos2.add("/resources/images/photography-image.jpg");
                photos2.add("/resources/images/studio-image.jpg");
                ArrayList<String> photos3 = new ArrayList();
                photos3.add("/resources/images/editing-image.jpg");
                photos3.add("/resources/images/studio-image.jpg");
                ArrayList<String> photos4 = new ArrayList();
                photos4.add("/resources/images/editing2-image.png");
                photos4.add("/resources/images/studio-image.jpg");
                ArrayList<String> photos5 = new ArrayList();
                photos5.add("/resources/images/vertical-photography-image.jpg");
                photos5.add("/resources/images/studio-image.jpg");
                //Rentals
                Service equipmentRental = new Service("Camera Rental", ServiceTypeEnum.EQUIPMENT_RENTAL, 100.00, photos2, false, userSessionBean.findUserByUserId(new Long(1)));
                equipmentRental.setEarliestCollectionTime("9am");
                equipmentRental.setLatestReturnTime("10pm");
                equipmentRental.setPackageDurationHours("Full Day Rental");
                equipmentRental.setServiceDescription("Description Camera Rental Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service equipmentRental2 = new Service("Camera Lens Rental", ServiceTypeEnum.EQUIPMENT_RENTAL, 120.00, photos, false, userSessionBean.findUserByUserId(new Long(2)));
                equipmentRental2.setEarliestCollectionTime("10am");
                equipmentRental2.setLatestReturnTime("11pm");
                equipmentRental2.setPackageDurationHours("Full Day Rental");
                equipmentRental2.setServiceDescription("Description Camera Lens Rental Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service equipmentRental3 = new Service("SD Card Rental", ServiceTypeEnum.EQUIPMENT_RENTAL, 10.00, photos, false, userSessionBean.findUserByUserId(new Long(3)));
                equipmentRental3.setEarliestCollectionTime("10am");
                equipmentRental3.setLatestReturnTime("11pm");
                equipmentRental3.setPackageDurationHours("Full Day Rental");
                equipmentRental3.setServiceDescription("Description SD Card Rental Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service equipmentRental4 = new Service("Lighting Equipment Rental", ServiceTypeEnum.EQUIPMENT_RENTAL, 30.00, photos5, false, userSessionBean.findUserByUserId(new Long(4)));
                equipmentRental4.setEarliestCollectionTime("7am");
                equipmentRental4.setLatestReturnTime("11pm");
                equipmentRental4.setPackageDurationHours("Full Day Rental");
                equipmentRental4.setServiceDescription("Description Lighting Equipment Rental Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                serviceSessionBeanLocal.createNewService(equipmentRental);
                serviceSessionBeanLocal.createNewService(equipmentRental2);
                serviceSessionBeanLocal.createNewService(equipmentRental3);
                serviceSessionBeanLocal.createNewService(equipmentRental4);

                //services package
                Service photography = new Service("Wedding Photoshoot", ServiceTypeEnum.PHOTOGRAPHY, 1000.00, photos2, false, userSessionBean.findUserByUserId(new Long(1)));
                photography.setPackageDurationHours("6 hours");
                photography.setServiceDescription("Description Wedding Photoshoot Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service photography2 = new Service("Graduation Photoshoot", ServiceTypeEnum.PHOTOGRAPHY, 500.00, photos5, false, userSessionBean.findUserByUserId(new Long(2)));
                photography2.setPackageDurationHours("3 hours");
                photography2.setServiceDescription("Description Graduation Photoshoot Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service videography = new Service("Music Video Shoot", ServiceTypeEnum.VIDEOGRAPHY, 2000.00, photos2, false, userSessionBean.findUserByUserId(new Long(3)));
                videography.setPackageDurationHours("8 hours");
                videography.setServiceDescription("Description Music Video Shoot Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service videography2 = new Service("Product Videography", ServiceTypeEnum.VIDEOGRAPHY, 300.00, photos5, false, userSessionBean.findUserByUserId(new Long(5)));
                videography2.setPackageDurationHours("2 hours");
                videography2.setServiceDescription("Description Product Videography Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                serviceSessionBeanLocal.createNewService(photography);
                serviceSessionBeanLocal.createNewService(photography2);
                serviceSessionBeanLocal.createNewService(videography);
                serviceSessionBeanLocal.createNewService(videography2);

                //editing
                Service photoEditing = new Service("Photoshop Services", ServiceTypeEnum.PHOTO_EDITING, 20.00, photos3, false, userSessionBean.findUserByUserId(new Long(3)));
                photoEditing.setPackageDurationHours("1 week");
                photoEditing.setServiceDescription("Description Photoshop Services Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service photoEditing1 = new Service("Photo ID Touchup", ServiceTypeEnum.PHOTO_EDITING, 5.00, photos4, false, userSessionBean.findUserByUserId(new Long(3)));
                photoEditing1.setPackageDurationHours("2 days");
                photoEditing1.setServiceDescription("Description Photo ID Touchup Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service videoEditing = new Service("Davinci Resolve", ServiceTypeEnum.VIDEO_EDITING, 200.00, photos3, false, userSessionBean.findUserByUserId(new Long(5)));
                videoEditing.setPackageDurationHours("2 weeks");
                videoEditing.setServiceDescription("Description Davinci Resolve Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service videoEditing2 = new Service("Premier Pro", ServiceTypeEnum.VIDEO_EDITING, 200.00, photos4, false, userSessionBean.findUserByUserId(new Long(5)));
                videoEditing2.setPackageDurationHours("2 weeks");
                videoEditing2.setServiceDescription("Description Photoshop Services Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                Service videoEditing3 = new Service("Final Cut Pro X", ServiceTypeEnum.VIDEO_EDITING, 200.00, photos, false, userSessionBean.findUserByUserId(new Long(5)));
                videoEditing3.setPackageDurationHours("2 weeks");
                videoEditing3.setServiceDescription("Description Final Cut Pro X Lorem Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");

                serviceSessionBeanLocal.createNewService(photoEditing);
                serviceSessionBeanLocal.createNewService(photoEditing1);
                serviceSessionBeanLocal.createNewService(videoEditing);
                serviceSessionBeanLocal.createNewService(videoEditing2);
                serviceSessionBeanLocal.createNewService(videoEditing3);
            } catch (UserNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        if(em.find(Booking.class, 1l) == null) {
            try {
                Booking booking1 = new Booking();
                booking1.setDate(new Date());
                booking1.setStartTime("9am");
                booking1.setProjectSpecifications("I need a video done for my sons birthday");
                booking1.setComments("Would be nice if you could whatsapp me first");
                booking1.setBookingStatus(BookingStatusEnum.PENDING);
                booking1.setService(serviceSessionBeanLocal.findServiceByServiceId(new Long(11)));
                booking1.setCustomer(userSessionBean.findUserByUserId(new Long(2)));
                
                Booking booking2 = new Booking();
                booking2.setDate(new Date());
                booking2.setStartTime("10am");
                booking2.setProjectSpecifications("I need this done urgently");
                booking2.setComments("Please make it quick");
                booking2.setBookingStatus(BookingStatusEnum.PENDING);
                booking2.setService(serviceSessionBeanLocal.findServiceByServiceId(new Long(12)));
                booking2.setCustomer(userSessionBean.findUserByUserId(new Long(3)));
                
                Booking booking3 = new Booking();
                booking3.setDate(new Date());
                booking3.setStartTime("5pm");
                booking3.setProjectSpecifications("make the video less serious, its meant to be funny");
                booking3.setComments("You can use whatever software you want");
                booking3.setBookingStatus(BookingStatusEnum.PENDING);
                booking3.setService(serviceSessionBeanLocal.findServiceByServiceId(new Long(13)));
                booking3.setCustomer(userSessionBean.findUserByUserId(new Long(4)));
                
                Booking booking4 = new Booking();
                booking4.setDate(new Date());
                booking4.setStartTime("11pm");
                booking4.setPreferredLocation("Jurong East");
                booking4.setComments("Let me know if it comes with batteries");
                booking4.setBookingStatus(BookingStatusEnum.PENDING);
                booking4.setService(serviceSessionBeanLocal.findServiceByServiceId(new Long(4)));
                booking4.setCustomer(userSessionBean.findUserByUserId(new Long(5)));
                
                Booking booking5 = new Booking();
                booking5.setDate(new Date());
                booking5.setStartTime("3pm");
                booking5.setProjectSpecifications("Okay i will message you the storyboard i came up with");
                booking5.setComments("i need your email address so i can share the google drive");
                booking5.setBookingStatus(BookingStatusEnum.PENDING);
                booking5.setService(serviceSessionBeanLocal.findServiceByServiceId(new Long(11)));
                booking5.setCustomer(userSessionBean.findUserByUserId(new Long(3)));
                
                Booking booking6 = new Booking();
                booking6.setDate(new Date());
                booking6.setStartTime("12pm");
                booking6.setProjectSpecifications("Help me edit a video for Eusoff Hall please");
                booking6.setComments("Comment");
                booking6.setBookingStatus(BookingStatusEnum.TORATE);
                booking6.setService(serviceSessionBeanLocal.findServiceByServiceId(new Long(11)));
                booking6.setCustomer(userSessionBean.findUserByUserId(new Long(1)));
                
                bookingSessionBean.createNewBooking(booking1);
                bookingSessionBean.createNewBooking(booking2);
                bookingSessionBean.createNewBooking(booking3);
                bookingSessionBean.createNewBooking(booking4);
                bookingSessionBean.createNewBooking(booking5);
                bookingSessionBean.createNewBooking(booking6);
            } catch (ServiceNotFoundException | UserNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(em.find(Review.class, 1l) == null) {
            try {
                Booking booking1 = bookingSessionBean.findBookingByBookingId(new Long(1));
                Review review1 = new Review();
                review1.setStarRating(5);
                review1.setDescription("The service provider is nice");
                review1.setReviewDate(new Date());
                reviewSessionBean.createNewReview(review1);
                booking1.setReview(review1);
                review1.setBooking(booking1);
                booking1.setBookingStatus(BookingStatusEnum.COMPLETED);
                
                Booking booking2 = bookingSessionBean.findBookingByBookingId(new Long(2));
                Review review2 = new Review();
                review2.setStarRating(5);
                review2.setDescription("Great service. Highly Recommend");
                review2.setReviewDate(new Date());
                reviewSessionBean.createNewReview(review2);
                booking2.setReview(review2);
                booking2.setBookingStatus(BookingStatusEnum.COMPLETED);
                review2.setBooking(booking2);
                
                Booking booking3 = bookingSessionBean.findBookingByBookingId(new Long(3));
                Review review3 = new Review();
                review3.setStarRating(4);
                review3.setDescription("Timeline overshot but otherwise good");
                review3.setReviewDate(new Date());
                reviewSessionBean.createNewReview(review3);
                booking3.setReview(review3);
                booking3.setBookingStatus(BookingStatusEnum.COMPLETED);
                review3.setBooking(booking3);
                
                Booking booking4 = bookingSessionBean.findBookingByBookingId(new Long(4));
                Review review4 = new Review();
                review4.setStarRating(5);
                review4.setDescription("It was nice working with you. Thank you");
                review4.setReviewDate(new Date());
                reviewSessionBean.createNewReview(review4);
                booking4.setBookingStatus(BookingStatusEnum.COMPLETED);
                review4.setBooking(booking3);
                
                Booking booking5 = bookingSessionBean.findBookingByBookingId(new Long(5));
                Review review5 = new Review();
                review5.setStarRating(5);
                review5.setDescription("Great work! Will patronise again");
                review5.setReviewDate(new Date());
                reviewSessionBean.createNewReview(review5);
                booking5.setReview(review5);
                booking5.setBookingStatus(BookingStatusEnum.COMPLETED);
                review5.setBooking(booking5);
                
            } catch (BookingNotFoundException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if (em.find(BanRequest.class, 1l) == null) {
         
            try {
                ArrayList<String> photos = new ArrayList();
                photos.add("/studio-image.jpg");
                adminSessionBean.createNewBanRequest(new BanRequest("This service is horrigible!", new Date(),false,userSessionBean.findUserByUserId(new Long(2)), serviceSessionBeanLocal.findServiceByServiceId(new Long(1)), null,null));
                adminSessionBean.createNewBanRequest(new BanRequest("This user is horrigible!", new Date(),false,userSessionBean.findUserByUserId(new Long(2)), null, userSessionBean.findUserByUserId(new Long(1)),null));
                adminSessionBean.createNewBanRequest(new BanRequest("This booking is horrigible!", new Date(), false,userSessionBean.findUserByUserId(new Long(2)),serviceSessionBeanLocal.findServiceByServiceId(new Long(2)), null, bookingSessionBean.findBookingByBookingId(new Long(1))));
            } catch (Exception ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
                    }
        Calendar calendar = Calendar.getInstance();
        Date thisInstance = calendar.getTime();
        if (em.find(ForumTopic.class, 1l) == null) {
            Set<ForumTopicTagEnum> topics = new HashSet<ForumTopicTagEnum>();

            topics.add(ForumTopicTagEnum.EQUIPMENT);
            topics.add(ForumTopicTagEnum.PHOTOGRAPHY);
            topics.add(ForumTopicTagEnum.TIPSANDADVICE);

            Set<ForumTopicTagEnum> topics1 = new HashSet<ForumTopicTagEnum>();
            topics1.add(ForumTopicTagEnum.VIDEOGRAPHY);
            topics1.add(ForumTopicTagEnum.VIDEOEDITING);
            topics1.add(ForumTopicTagEnum.TIPSANDADVICE);
            
            Set<ForumTopicTagEnum> topics2 = new HashSet<ForumTopicTagEnum>();
            topics2.add(ForumTopicTagEnum.VIDEOGRAPHY);
            topics2.add(ForumTopicTagEnum.PHOTOEDITING);
            
            Set<ForumTopicTagEnum> topics3 = new HashSet<ForumTopicTagEnum>();
            topics3.add(ForumTopicTagEnum.VIDEOGRAPHY);
            topics3.add(ForumTopicTagEnum.PHOTOEDITING);
            topics3.add(ForumTopicTagEnum.PHOTOGRAPHY);

            try {
                ForumTopic forumTopic1 = new ForumTopic("Test Topic", "Bing Bong Bing Bong Bing Bong ", userSessionBean.findUserByUserId(new Long(1)), topics, thisInstance);

                ForumTopic forumTopic2 = new ForumTopic("Test Topic 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, ", userSessionBean.findUserByUserId(new Long(2)),
                        topics1, thisInstance);
                
                ForumTopic forumTopic3 = new ForumTopic("Test Topic 3", "Bing Bong Bing Bong Bing Bong ", userSessionBean.findUserByUserId(new Long(2)), topics2, thisInstance);
                
                ForumTopic forumTopic4 = new ForumTopic("Test Topic 4", "Bing Bong Bing Bong Bing Bong ", userSessionBean.findUserByUserId(new Long(3)), topics3, thisInstance);

                forumSessionBean.createNewForumTopic(forumTopic1);
                forumSessionBean.createNewForumTopic(forumTopic2);
                forumSessionBean.createNewForumTopic(forumTopic3);
                forumSessionBean.createNewForumTopic(forumTopic4);
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

   


    }

    public void persist(Object object) {
        em.persist(object);
    }
}
