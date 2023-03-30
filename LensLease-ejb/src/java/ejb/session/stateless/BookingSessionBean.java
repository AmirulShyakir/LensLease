/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Booking;
import entity.Review;
import entity.Service;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BookingNotFoundException;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Stateless
public class BookingSessionBean implements BookingSessionBeanLocal {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createNewBooking(Booking booking) {
        em.persist(booking);
        em.flush();
    }
    
    @Override
    public void createNewReview(Review review) {
        em.persist(review);
        em.flush();
    }
    
    @Override
    public List<Booking> getAllBookings() {
        Query query = em.createQuery("SELECT b FROM Booking b");
        return query.getResultList();
    }
    
    @Override
    public Booking findBookingByBookingId(Long bookingId) throws BookingNotFoundException {
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.bookingId = :inBookingId");
        query.setParameter("inBookingId", bookingId);
        query.setMaxResults(1);
        try {
            Booking booking = (Booking)query.getSingleResult();
            return booking;
        } catch (Exception e) {
            throw new BookingNotFoundException("Booking not found with id " + bookingId);
        }
    }
    
    @Override
    public void submitBookingRequest(long bookingId, long serviceId, long userId) throws ServiceNotFoundException, UserNotFoundException, BookingNotFoundException {
        Booking booking = findBookingByBookingId(bookingId);
        Service service = serviceSessionBean.findServiceByServiceId(serviceId);
        User user = userSessionBean.findUserByUserId(userId);

        booking.setService(service);
        service.getBookings().add(booking);

        booking.setCustomer(user);
        user.getBookings().add(booking);
    }
}
