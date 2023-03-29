/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Booking;
import entity.BookingStatusEnum;
import entity.Service;
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

/**
 *
 * @author Amirul
 */
@Named(value = "checkoutManagedBean")
@ViewScoped
public class CheckoutManagedbean implements Serializable {

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;
    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    private Service service;
    
    //fields for submitting booking request
    private Date selectedDate;
    private String selectedTime;
    private String preferredLocation;
    private String comments;
    private boolean agreedToTermsAndConditions;
    
    /**
     * Creates a new instance of CheckoutManagedbean
     */
    public CheckoutManagedbean() {
    }
    
    //INIT FOR TESTING
    @PostConstruct
    public void init() {
        service = serviceSessionBean.getAllServices().get(1);
        System.out.println(service.getServiceName());
    }
    
    public String createBookingRequest(ActionEvent evt) {
        FacesContext context = FacesContext.getCurrentInstance();

        Booking booking = new Booking();
        booking.setDate(selectedDate);
        booking.setStartDateTime(selectedTime);
        booking.setComments(getComments());
        booking.setPreferredLocation(preferredLocation);
        booking.setBookingStatus(BookingStatusEnum.PENDING);
        bookingSessionBean.createNewBooking(booking);
        bookingSessionBean.submitBookingRequest(booking, service);
        context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "Successfully submitted booking request " ));
        return "index.xhtml?faces-redirect=true";
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
    
}
