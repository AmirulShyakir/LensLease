/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Portfolio;
import entity.PortfolioClient;
import entity.PortfolioSkill;
import entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public Portfolio findPortfolioByUserId(Long userId) throws UserNotFoundException {
        try {
            User u = userSessionBean.findUserByUserId(userId);
            Query query = em.createQuery("SELECT p FROM Portfolio p WHERE p.user.userId = :userId");
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            return (Portfolio) query.getSingleResult();
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    @Override
    public String getDescription(Long userId) throws UserNotFoundException {
        try {
            Portfolio p = findPortfolioByUserId(userId);
            return p.getDescription();
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    @Override
    public Long addClient(PortfolioClient client) {
        
    }

    @Override
    public Long editClient(Long portfolioId, PortfolioClient client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeClient(Long portfolioId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PortfolioSkill> getSkills() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long addSkill(PortfolioSkill skill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long removeSkill(Long portfolioId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPhotos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPhoto(String photoURL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editPortfolio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
