/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Booking;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author junwe
 */
@Named(value = "bookingManagedBean")
@ViewScoped
public class BookingManagedBean implements Serializable {

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;
    @EJB
    private UserSessionBeanLocal userSessionBean;
    
    private User user;
    private List<Booking> todayServicesProvided;
    private List<Booking> pendingBookingsAsProvider;
    private List<Booking> allBookingsAsProvider;

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


    public void loadTodayBookingsForServices() {
        this.todayServicesProvided = bookingSessionBean.getTodayServicesByUser(this.user);
        System.out.println("Retrieved todays services as provider");
    }

    public void loadPendingBookingsAsProvider() {
        this.pendingBookingsAsProvider = bookingSessionBean.getPendingBookingRequestsAsProvider(user);
        System.out.println("Retrieved pending bookings as provider");
    }
    
    public void loadAllBookingsAsProvider(){
        this.allBookingsAsProvider = bookingSessionBean.getBookingsAsSupplier(user);
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
    
}
