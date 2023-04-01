/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Booking;
import entity.BookingStatusEnum;
import entity.Review;
import entity.Service;
import entity.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            Booking booking = (Booking) query.getSingleResult();
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

    @Override
    public List<Booking> getBookingsAsSupplier(User user) {
        List<Booking> allBookingsAsSupplier = new ArrayList<>();
        List<Service> servicesProvided = user.getServices();
        for (Service s : servicesProvided) {
            allBookingsAsSupplier.addAll(s.getBookings());
        }
        return allBookingsAsSupplier;
    }

    @Override
    public List<Booking> getBookingsAsClient(User user) {
        return user.getBookings();
    }
    
    @Override
    public List<Booking> getPendingBookingRequestsAsRequester(User user){
        Query q = em.createQuery("SELECT b FROM Booking b WHERE b.customer = :inUser AND b.bookingStatus = :inStatus");
        q.setParameter("inUser", user);
        q.setParameter("inStatus", BookingStatusEnum.PENDING);
        List<Booking> pendingBookingRequests = q.getResultList();
        return pendingBookingRequests;
    }
    
    @Override
    public List<Booking> getToRateBookingsAsRequester(User user){
        Query q = em.createQuery("SELECT b FROM Booking b WHERE b.customer = :inUser AND b.bookingStatus = :inStatus");
        q.setParameter("inUser", user);
        q.setParameter("inStatus", BookingStatusEnum.TORATE);
        List<Booking> pendingBookingRequests = q.getResultList();
        return pendingBookingRequests;
    }
    
    @Override
    public List<Booking> getConfirmedBookingsAsRequester(User user){
        Query q = em.createQuery("SELECT b FROM Booking b WHERE b.customer = :inUser AND b.bookingStatus = :inStatus");
        q.setParameter("inUser", user);
        q.setParameter("inStatus", BookingStatusEnum.CONFIRMED);
        List<Booking> pendingBookingRequests = q.getResultList();
        return pendingBookingRequests;
    }

    @Override
    public List<Booking> getTodayServicesByUser(User user) {
        List<Booking> bookings = getBookingsAsSupplier(user);
        List<Booking> todayServiceBookings = new ArrayList<Booking>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");
        String today = simpleDateFormat.format(new Date());
        System.out.println("today " + today);
        for (Booking b : bookings) {
            System.out.println("booking " + simpleDateFormat.format(b.getDate()));
            if (simpleDateFormat.format(b.getDate()).equals(today)) {
                todayServiceBookings.add(b);
            }
        }
        return todayServiceBookings;
    }
    
    @Override
    public List<Booking> getTodayServicesByRequester(User user) {
        List<Booking> bookings = getBookingsAsClient(user);
        List<Booking> todayServiceBookings = new ArrayList<Booking>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");
        String today = simpleDateFormat.format(new Date());
        System.out.println("today " + today);
        for (Booking b : bookings) {
            System.out.println("booking " + simpleDateFormat.format(b.getDate()));
            if (simpleDateFormat.format(b.getDate()).equals(today)) {
                todayServiceBookings.add(b);
            }
        }
        return todayServiceBookings;
    }

    @Override
    public List<Booking> getPendingBookingRequestsAsProvider(User user) {
        Query q = em.createQuery("SELECT b FROM Booking b WHERE b.service.provider = :inUser AND b.bookingStatus = :inStatus");
        q.setParameter("inUser", user);
        q.setParameter("inStatus", BookingStatusEnum.PENDING);
        List<Booking> pendingBookingRequests = q.getResultList();
        return pendingBookingRequests;

    }

}
