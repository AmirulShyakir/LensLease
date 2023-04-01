/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Booking;
import entity.Review;
import entity.Service;
import entity.User;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookingNotFoundException;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface BookingSessionBeanLocal {

    public void createNewBooking(Booking booking);

    public void createNewReview(Review review);

    public List<Booking> getAllBookings();

    public Booking findBookingByBookingId(Long bookingId) throws BookingNotFoundException;

    public void submitBookingRequest(long bookingId, long serviceId, long userId) throws ServiceNotFoundException, UserNotFoundException, BookingNotFoundException;

    public List<Booking> getBookingsAsSupplier(User user);

    public List<Booking> getTodayServicesByUser(User user);

    public List<Booking> getPendingBookingRequestsAsProvider(User user);
    
}
