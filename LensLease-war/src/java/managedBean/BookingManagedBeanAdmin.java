/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.BookingSessionBeanLocal;
import entity.Booking;
import entity.BookingStatusEnum;
import entity.Service;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import util.exception.BookingNotFoundException;

/**
 *
 * @author jonta
 */
@Named(value = "bookingManagedBeanAdmin")
@ViewScoped
public class BookingManagedBeanAdmin implements Serializable {

    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;
    
    private Date bookingDate;
    private String startTime; //refers to start time for services, collection time for rentals
    private String preferredLocation;
    private String comments;
    private BookingStatusEnum bookingStatus;
    private Service service;
    private User customer;
    
    private Long bookingId;
    private List<Booking> listOfBookings;
    private Booking selectedBooking;
    private String searchString;
    
    /**
     * Creates a new instance of BookingManagedBean
     */
    public BookingManagedBeanAdmin() {
    }
    
    @PostConstruct
    public void init() {

        if (getSearchString() == null || getSearchString().equals("")) {
            setListOfBookings(bookingSessionBeanLocal.getAllBookings());
        } else {
            listOfBookings = bookingSessionBeanLocal.searchBookings(searchString);
        }

        
    }
    
    public void handleSearch() {
        init();
    }
    
    public void loadSelectedService() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.selectedBooking = bookingSessionBeanLocal.findBookingByBookingId(bookingId);
            bookingDate = this.selectedBooking.getDate();
            comments = this.selectedBooking.getComments();
            bookingStatus = this.selectedBooking.getBookingStatus();
            service = this.selectedBooking.getService();
            customer = this.selectedBooking.getCustomer();
            System.out.println("Going to Individual service page with selected service: " + bookingId);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
    }


    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BookingStatusEnum getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatusEnum bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<Booking> getListOfBookings() {
        return listOfBookings;
    }

    public void setListOfBookings(List<Booking> listOfBookings) {
        this.listOfBookings = listOfBookings;
    }

    public Booking getSelectedBooking() {
        return selectedBooking;
    }

    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
