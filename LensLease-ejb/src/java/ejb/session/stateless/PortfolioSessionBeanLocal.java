/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Portfolio;
import entity.PortfolioClient;
import entity.PortfolioSkill;
import java.util.List;
import javax.ejb.Local;
import util.exception.IncompleteFieldsException;
import util.exception.SkillAlreadyExistsException;
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
    public Long createClient(PortfolioClient client) throws IncompleteFieldsException;
    public void removeClient(PortfolioClient client);
    
    //Skills
    public Long addSkill(Portfolio portfolio, String s) throws SkillAlreadyExistsException;
    public void removeSkill(Portfolio portfolio, PortfolioSkill skill);
    
    //Gallery
    public List<String> getPhotos(Portfolio portfolio);
    public void addPhoto(Portfolio portfolio, String photoURL);
    
}
