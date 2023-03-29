/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PortfolioClient;
import entity.PortfolioPost;
import entity.PortfolioSkill;
import entity.User;
import java.util.List;
import javax.ejb.Local;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface PortfolioSessionBeanLocal {

    public void createPortfolio(User user);

    public List<PortfolioClient> findPortfolioClientsByUserId(Long userId) throws UserNotFoundException;

    public List<PortfolioSkill> findPortfolioSkillsByUserId(Long userId) throws UserNotFoundException;

    public List<PortfolioPost> findPortfolioPostsByUserId(Long userId) throws UserNotFoundException;

    public void uploadPortfolioPost(PortfolioPost portfolioPost, User user);

    public void hidePortfolioPost(Long postId);

    public void addPortfolioClient(PortfolioClient portfolioClient, User user);

    public void hidePortfolioClient(Long portfolioClientId);

    public void addPortfolioSkill(PortfolioSkill portfolioSkill, User user);

    public void hidePortfolioSkill(Long portfolioSkillId);
    
}
