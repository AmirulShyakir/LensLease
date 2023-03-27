/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Service;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author Amirul
 */
@Named(value = "checkoutManagedbean")
@SessionScoped
public class CheckoutManagedbean implements Serializable {

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;
    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    private Service service;
    /**
     * Creates a new instance of CheckoutManagedbean
     */
    public CheckoutManagedbean() {
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
    
}
