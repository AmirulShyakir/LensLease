/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Portfolio;
import entity.PortfolioClient;
import java.util.List;
import javax.ejb.Local;
import util.exception.ImageDuplicateException;
import util.exception.IncompleteFieldsException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface PortfolioSessionBeanLocal {
   
    public Portfolio findPortfolioByUserId(Long userId) throws UserNotFoundException;
    
    //Description
    public void updateDescription(Portfolio portfolio, String s);
    
    //Client
    public Portfolio createClient(Portfolio portfolio, PortfolioClient client) throws IncompleteFieldsException;
    public Portfolio updateClient(Portfolio portfolio, PortfolioClient client) throws IncompleteFieldsException;
    public Portfolio removeClient(PortfolioClient client);
    
    //Skills
    public Portfolio updateSkills(Portfolio portfolio, List<String> skills);
    
    //Gallery
//    public Portfolio addPhoto(Portfolio portfolio, String photoURL) throws ImageDuplicateException;
    
}
