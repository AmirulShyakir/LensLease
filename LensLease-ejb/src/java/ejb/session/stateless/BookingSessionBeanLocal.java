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
import util.exception.BookingNotSubmittedException;
import util.exception.ServiceNotFoundException;
import util.exception.UserIsBannedException;
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

    public List<Booking> getBookingsAsSupplier(User user);

    public List<Booking> searchBookings(String name);
    public List<Booking> getTodayServicesByUser(User user);

    public List<Booking> getPendingBookingRequestsAsProvider(User user);

    public List<Booking> getBookingsAsClient(User user);

    public List<Booking> getPendingBookingRequestsAsRequester(User user);

    public List<Booking> getTodayServicesByRequester(User user);

    public List<Booking> getToRateBookingsAsRequester(User user);

    public List<Booking> getConfirmedBookingsAsRequester(User user);

    public void submitBookingRequest(Booking booking, long serviceId, long userId) throws ServiceNotFoundException, UserNotFoundException, BookingNotFoundException, BookingNotSubmittedException, UserIsBannedException;

    public void setBookingAsCancelled(Booking booking);

    public void setBookingAsToRate(Booking booking);

    public void setBookingAsRejected(Booking booking);

    public void setBookingAsConfirmed(Booking booking);

    
}
