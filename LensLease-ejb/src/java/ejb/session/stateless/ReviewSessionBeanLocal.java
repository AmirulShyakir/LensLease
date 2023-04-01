/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface ReviewSessionBeanLocal {

    public void createNewReview(Review review);

    public Review findReviewByReviewId(Long reviewId) throws ReviewNotFoundException;
    
}
