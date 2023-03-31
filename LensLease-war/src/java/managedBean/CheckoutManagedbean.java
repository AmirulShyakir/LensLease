/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Booking;
import entity.BookingStatusEnum;
import entity.Service;
import entity.User;
import java.awt.event.ActionEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.annotation.ManagedProperty;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Named(value = "checkoutManagedBean")
@ViewScoped
public class CheckoutManagedbean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;
    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    private long serviceId;
    private long confirmServiceId;
    private Service service;
    private User user;

    //fields for submitting booking request
    private Date selectedDate;
    private String selectedTime;
    private String preferredLocation;
    private String projectSpecifications;
    private String comments;
    private boolean agreedToTermsAndConditions;

    /**
     * Creates a new instance of CheckoutManagedbean
     */
    public CheckoutManagedbean() {
    }

    @PostConstruct
    public void init() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "authenticationManagedBean");
            
            long userId = authenticationManagedBean.getUserId();
            setUser(userSessionBean.findUserByUserId(userId));           
            System.out.println(getUser().getUsername());
            
            //ServiceManagedBean serviceManagedBean = (ServiceManagedBean) FacesContext.getCurrentInstance().getApplication()
            //       .getELResolver().getValue(elContext, null, "serviceManagedBean");
            //long serviceId = serviceManagedBean.getServiceId();
            //service = serviceSessionBean.findServiceByServiceId(serviceId);
            //service = serviceSessionBean.getAllServices().get(1); //THIS IS FOR TESTING
            //System.out.println("Going to Checkout Page with Selected service: " + service.getServiceName());
           
        } catch (Exception ex) {
            Logger.getLogger(CheckoutManagedbean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String bookNow() {
        return "/secret/checkout.xhtml?faces-redirect=true";
    }
    

    public String bookNow(ActionEvent evt) {
        return "checkout.xhtml?faces-redirect=true";
    }
    
    public String createBookingRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Booking booking = new Booking();
            booking.setDate(selectedDate);
            booking.setStartTime(selectedTime);
            booking.setComments(comments);
            booking.setPreferredLocation(preferredLocation);
            booking.setBookingStatus(BookingStatusEnum.PENDING);
            booking.setProjectSpecifications(projectSpecifications);
            bookingSessionBean.createNewBooking(booking);
            bookingSessionBean.submitBookingRequest(booking.getBookingId(), serviceId, getUser().getUserId());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "Successfully submitted booking request "));
            return "landingPage.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ex.getMessage()));
            return "checkout.xhtml";
        }
    }

    public String getServiceCost() {
        double amount = service.getServiceCost();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String currencyString = formatter.format(amount);
        return currencyString;
    }

    public void handleDateSelect(SelectEvent<LocalDate> event) {
        LocalDate date = event.getObject();
        FacesContext context = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected date:  ", format.format(date)));
        //Add facesmessage
    }

    public void loadSelectedService() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.service = serviceSessionBean.findServiceByServiceId(serviceId);
//            System.out.println("Going to Individual service page with selected service: " + serviceName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
    }
    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @return the selectedDate
     */
    public Date getSelectedDate() {
        return selectedDate;
    }

    /**
     * @param selectedDate the selectedDate to set
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * @return the preferredLocation
     */
    public String getPreferredLocation() {
        return preferredLocation;
    }

    /**
     * @param preferredLocation the preferredLocation to set
     */
    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the selectedTime
     */
    public String getSelectedTime() {
        return selectedTime;
    }

    /**
     * @param selectedTime the selectedTime to set
     */
    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    /**
     * @return the agreedToTermsAndConditions
     */
    public boolean isAgreedToTermsAndConditions() {
        return agreedToTermsAndConditions;
    }

    /**
     * @param agreedToTermsAndConditions the agreedToTermsAndConditions to set
     */
    public void setAgreedToTermsAndConditions(boolean agreedToTermsAndConditions) {
        this.agreedToTermsAndConditions = agreedToTermsAndConditions;
    }

    /**
     * @return the serviceId
     */
    public long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the confirmServiceId
     */
    public long getConfirmServiceId() {
        return confirmServiceId;
    }

    /**
     * @param confirmServiceId the confirmServiceId to set
     */
    public void setConfirmServiceId(long confirmServiceId) {
        this.confirmServiceId = confirmServiceId;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the projectSpecifications
     */
    public String getProjectSpecifications() {
        return projectSpecifications;
    }

    /**
     * @param projectSpecifications the projectSpecifications to set
     */
    public void setProjectSpecifications(String projectSpecifications) {
        this.projectSpecifications = projectSpecifications;
    }

}
