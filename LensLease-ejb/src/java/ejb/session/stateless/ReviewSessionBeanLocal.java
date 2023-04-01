/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookingNotFoundException;
import util.exception.ReviewNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface ReviewSessionBeanLocal {

    public void createNewReview(Review review);

    public Review findReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void createReview(long reviewId, long bookingId) throws BookingNotFoundException, ReviewNotFoundException;

    public List<Review> getReviewsByUserId(long userId) throws UserNotFoundException;
    
}
