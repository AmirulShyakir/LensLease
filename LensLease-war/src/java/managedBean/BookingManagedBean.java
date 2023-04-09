/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.BanRequest;
import entity.Booking;
import entity.Review;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.BanRequestAlreadyExistException;
import util.exception.BookingNotFoundException;
import util.exception.ReviewAlreadyExistException;
import util.exception.ServiceNotFoundException;

/**
 *
 * @author junwe
 */
@Named(value = "bookingManagedBean")
@ViewScoped
public class BookingManagedBean implements Serializable {

    @EJB
    private AdminSessionBeanLocal adminSessionBean;

    @EJB
    private ReviewSessionBeanLocal reviewSessionBean;
    @EJB
    private BookingSessionBeanLocal bookingSessionBean;
    @EJB
    private UserSessionBeanLocal userSessionBean;
    
    
    private User user;
    private List<Booking> todayServicesProvided;
    private List<Booking> pendingBookingsAsProvider;
    private List<Booking> allBookingsAsProvider;
    
    private List<Booking> allBookingsAsRequester;
    private List<Booking> pendingBookingsAsRequester;
    private List<Booking> todayServices;
    private List<Booking> bookingsToBeRated;
    private List<Booking> upcomingBookings;
    
    //for editing and rating
    private long selectedBookingId;
    private Booking selectedBooking;
    private int starRating;
    private String reviewDescription;
    private String reportDescription;

    /**
     * Creates a new instance of BookingManagedBean
     */
    public BookingManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "authenticationManagedBean");
            
            long userId = authenticationManagedBean.getUserId();
            setUser(userSessionBean.findUserByUserId(userId));
        } catch (Exception ex) {
            Logger.getLogger(CheckoutManagedbean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createReview() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Review review = new Review();
            review.setDescription(reviewDescription);
            review.setStarRating(starRating);
            review.setReviewDate(new Date());
            reviewSessionBean.submitNewReview(review,selectedBooking.getBookingId());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Review submmited successfully"));
        } catch (BookingNotFoundException | ReviewAlreadyExistException e) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to submit review" + e.getMessage()));
        }
    }
    public String createBookingBanRequestAsUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            BanRequest report = new BanRequest();
            report.setRequestDate(new Date());
            report.setDescription(reportDescription);
            adminSessionBean.submitNewBookingBanRequestAsUser(report, selectedBookingId);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Report submmited successfully"));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "myBookings.xhtml?faces-redirect=true";
        } catch (ServiceNotFoundException | BanRequestAlreadyExistException e) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to submit report" + e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "myBookings.xhtml?faces-redirect=true";
        }
    }
    
    public String createBookingBanRequestAsProvider() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            BanRequest report = new BanRequest();
            report.setRequestDate(new Date());
            report.setDescription(reportDescription);
            adminSessionBean.submitNewBookingBanRequestAsProvider(report, selectedBookingId);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Report submmited successfully"));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "myServices.xhtml?faces-redirect=true";
        } catch (ServiceNotFoundException | BanRequestAlreadyExistException e) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to submit report" + e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "myServices.xhtml?faces-redirect=true";
        }
    }

    public void loadTodayBookingsForServices() {
        this.todayServicesProvided = bookingSessionBean.getTodayServicesByUser(this.user);
    }
    public void loadTodayBookingsAsRequester() {
        this.todayServices = bookingSessionBean.getTodayServicesByRequester(this.user);
    }

    public void loadPendingBookingsAsProvider() {
        this.pendingBookingsAsProvider = bookingSessionBean.getPendingBookingRequestsAsProvider(user);
    }
    public void loadPendingBookingsAsRequester() {
        this.pendingBookingsAsRequester = bookingSessionBean.getPendingBookingRequestsAsRequester(user);
    }
    
    public void loadAllBookingsAsProvider(){
        this.allBookingsAsProvider = bookingSessionBean.getBookingsAsSupplier(user);
    }
    
    public void loadAllBookingsAsRequester(){
        this.allBookingsAsRequester = bookingSessionBean.getBookingsAsClient(user);
    }
    
    public void loadBookingsToBeRated(){
        this.bookingsToBeRated = bookingSessionBean.getToRateBookingsAsRequester(user);
    }
    
    public void loadUpcomingBookings(){
        this.upcomingBookings = bookingSessionBean.getConfirmedBookingsAsRequester(user);
    }

    public String setBookingAsCancelled(Booking booking){
        bookingSessionBean.setBookingAsCancelled(booking);
        System.out.println("Booking has been cancelled " + booking.getBookingStatus());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmed", "You have cancelled booking");
        FacesContext.getCurrentInstance().addMessage(null, message);    
        return "myBookings.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public String setBookingAsToRate(Booking booking){
        bookingSessionBean.setBookingAsToRate(booking);
        System.out.println("Booking has been completed " + booking.getBookingStatus());
        return "myServices.xhtml?faces-redirect=true&includeViewParams=true";
    }
    public String setBookingAsRejected(Booking booking){
        bookingSessionBean.setBookingAsRejected(booking);
        System.out.println("Booking has been rejected " + booking.getBookingStatus());
        return "myServices.xhtml?faces-redirect=true&includeViewParams=true";
    }
    public String setBookingAsConfirmed(Booking booking){
        bookingSessionBean.setBookingAsConfirmed(booking);
        System.out.println("Booking has been confirmed " + booking.getBookingStatus());
        return "myServices.xhtml?faces-redirect=true&includeViewParams=true";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Booking> getTodayServicesProvided() {
        return todayServicesProvided;
    }

    public void setTodayServicesProvided(List<Booking> todayServicesProvided) {
        this.todayServicesProvided = todayServicesProvided;
    }

    public List<Booking> getPendingBookingsAsProvider() {
        return pendingBookingsAsProvider;
    }

    public void setPendingBookingsAsProvider(List<Booking> pendingBookingsAsProvider) {
        this.pendingBookingsAsProvider = pendingBookingsAsProvider;
    }

    public List<Booking> getAllBookingsAsProvider() {
        return allBookingsAsProvider;
    }

    public void setAllBookingsAsProvider(List<Booking> allBookingsAsProvider) {
        this.allBookingsAsProvider = allBookingsAsProvider;
    }

    public List<Booking> getAllBookingsAsRequester() {
        return allBookingsAsRequester;
    }

    public void setAllBookingsAsRequester(List<Booking> allBookingsAsRequester) {
        this.allBookingsAsRequester = allBookingsAsRequester;
    }

    /**
     * @return the pendingBookingsAsRequester
     */
    public List<Booking> getPendingBookingsAsRequester() {
        return pendingBookingsAsRequester;
    }

    public void setPendingBookingsAsRequester(List<Booking> pendingBookingsAsRequester) {
        this.pendingBookingsAsRequester = pendingBookingsAsRequester;
    }

    public List<Booking> getTodayServices() {
        return todayServices;
    }

    public void setTodayServices(List<Booking> todayServices) {
        this.todayServices = todayServices;
    }

    public List<Booking> getBookingsToBeRated() {
        return bookingsToBeRated;
    }

    public void setBookingsToBeRated(List<Booking> bookingsToBeRated) {
        this.bookingsToBeRated = bookingsToBeRated;
    }

    public List<Booking> getUpcomingBookings() {
        return upcomingBookings;
    }

    public void setUpcomingBookings(List<Booking> upcomingBookings) {
        this.upcomingBookings = upcomingBookings;
    }

    /**
     * @return the selectedBooking
     */
    public Booking getSelectedBooking() {
        return selectedBooking;
    }

    /**
     * @param selectedBooking the selectedBooking to set
     */
    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }
    
    public void loadSelectedBooking() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.selectedBooking = bookingSessionBean.findBookingByBookingId(selectedBookingId);
//            System.out.println("Going to Individual service page with selected service: " + serviceName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
    }

    /**
     * @return the starRating
     */
    public int getStarRating() {
        return starRating;
    }

    /**
     * @param starRating the starRating to set
     */
    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    /**
     * @return the reviewDescription
     */
    public String getReviewDescription() {
        return reviewDescription;
    }

    /**
     * @param reviewDescription the reviewDescription to set
     */
    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    /**
     * @return the selectedBookingId
     */
    public long getSelectedBookingId() {
        return selectedBookingId;
    }

    /**
     * @param selectedBookingId the selectedBookingId to set
     */
    public void setSelectedBookingId(long selectedBookingId) {
        this.selectedBookingId = selectedBookingId;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

}
