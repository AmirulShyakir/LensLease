/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PortfolioClient;
import entity.PortfolioSkill;
import java.util.List;
import javax.ejb.Local;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface PortfolioSessionBeanLocal {
    
    //Description
    public String getDescription(Long portfolioId) throws UserNotFoundException;
    
    //Client
    public Long addClient(PortfolioClient client);
    public Long editClient(Long portfolioId, PortfolioClient client);
    public void removeClient(Long portfolioId);
    
    //Skills
    public List<PortfolioSkill> getSkills();
    public Long addSkill(PortfolioSkill skill);
    public Long removeSkill(Long portfolioId);
    
    //Gallery
    public List<String> getPhotos();
    public void addPhoto(String photoURL);
    
    //Portfolio
    public void editPortfolio();
}
