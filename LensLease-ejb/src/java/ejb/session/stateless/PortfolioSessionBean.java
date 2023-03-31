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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Stateless
public class PortfolioSessionBean implements PortfolioSessionBeanLocal {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
    @Override
    public void createPortfolio(User user){
        PortfolioClient portfolioClient = new PortfolioClient();
        em.persist(portfolioClient);
        portfolioClient.getUsers().add(user);
        PortfolioSkill portfolioSkill = new PortfolioSkill();
        em.persist(portfolioSkill);
        portfolioSkill.getUsers().add(user);
        PortfolioPost portfolioPost = new PortfolioPost();
        em.persist(portfolioPost);
        portfolioPost.setUser(user);
        
        em.flush();
    }
    
    @Override
    public List<PortfolioClient> findPortfolioClientsByUserId(Long userId) throws UserNotFoundException{
        User user = userSessionBean.findUserByUserId(userId);
        return user.getPortfolioClients();
    }
    
    @Override
    public List<PortfolioSkill> findPortfolioSkillsByUserId(Long userId) throws UserNotFoundException{
        User user = userSessionBean.findUserByUserId(userId);
        return user.getPortfolioSkills();
    }
    
    @Override
    public List<PortfolioPost> findPortfolioPostsByUserId(Long userId) throws UserNotFoundException{
        User user = userSessionBean.findUserByUserId(userId);
        return user.getPortfolioPosts();
    }
    
    @Override
    public void uploadPortfolioPost(PortfolioPost portfolioPost, User user){
        em.persist(portfolioPost);
        user.getPortfolioPosts().add(portfolioPost);
        portfolioPost.setUser(user);
        em.flush();
    }
    
    @Override
    public void hidePortfolioPost(Long postId){
        PortfolioPost post = em.find(PortfolioPost.class, postId);
        post.setIsDisplayed(false);
    }
    
    @Override
    public void addPortfolioClient(PortfolioClient portfolioClient, User user){
        em.persist(portfolioClient);
        user.getPortfolioClients().add(portfolioClient);
        portfolioClient.getUsers().set(0, user);
        em.flush();
    }
    
    @Override
    public void hidePortfolioClient(Long portfolioClientId){
        PortfolioClient portfolioClient = em.find(PortfolioClient.class, portfolioClientId);
        portfolioClient.setIsDisplayed(false);
    }
    
    @Override
    public void addPortfolioSkill(PortfolioSkill portfolioSkill, User user){
        em.persist(portfolioSkill);
        user.getPortfolioSkills().add(portfolioSkill);
        portfolioSkill.getUsers().set(0, user);
        em.flush();
    }
    
    @Override
    public void hidePortfolioSkill(Long portfolioSkillId){
        PortfolioSkill skill = em.find(PortfolioSkill.class, portfolioSkillId);
        skill.setIsDisplayed(false);
    }
}