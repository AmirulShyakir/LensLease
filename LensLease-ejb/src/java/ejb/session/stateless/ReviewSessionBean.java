/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Booking;
import entity.Review;
import entity.Service;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BookingNotFoundException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author Amirul
 */
@Stateless
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public void createNewReview(Review review) {
        em.persist(review);
        em.flush();
    }
    
    @Override
    public Review findReviewByReviewId(Long reviewId) throws ReviewNotFoundException {
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.reviewId = :inReviewId");
        query.setParameter("inReviewId", reviewId);
        query.setMaxResults(1);
        try {
            Review review = (Review) query.getSingleResult();
            return review;
        } catch (Exception e) {
            throw new ReviewNotFoundException("Review not found with id " + reviewId);
        }
    }
    
    public void createReview(long reviewId, long bookingId) throws BookingNotFoundException, ReviewNotFoundException {
        Booking booking = bookingSessionBean.findBookingByBookingId(bookingId);
        Review review = findReviewByReviewId(reviewId);
        booking.setReview(review);
        review.setBooking(booking);
    }
}