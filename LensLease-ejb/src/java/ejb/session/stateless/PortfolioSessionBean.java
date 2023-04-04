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
import util.exception.IncompleteFieldsException;
import util.exception.SkillAlreadyExistsException;
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

    @Override
    public Portfolio findPortfolioByUserId(Long userId) throws UserNotFoundException {
        try {
            User u = userSessionBean.findUserByUserId(userId);
            return u.getPortfolio();
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    @Override
    public String getDescription(Portfolio portfolio) {
        String desc = portfolio.getDescription();
        if (desc.isEmpty()) {
            desc = "Hi there, welcome to my portfolio!";
        }
        return desc;
    }
    
    @Override
    public Long createClient(PortfolioClient client) throws IncompleteFieldsException {
        if (client.getClientName() != null && client.getClientLink() != null) {
            em.persist(client);
            em.flush();
            return client.getPortfolioClientId();
        } else {
            throw new IncompleteFieldsException("Please complete all fields to create client");
        }
    }

    @Override
    public void removeClient(PortfolioClient client) {
        Portfolio portfolio = client.getPortfolio();
        portfolio.getPortfolioClients().remove(client);
        em.remove(client);
    }

    @Override
    public Long addSkill(Portfolio portfolio, String s) throws SkillAlreadyExistsException {
        Query query = em.createQuery("SELECT s FROM (SELECT p.portfolioSkills FROM Portfolio p WHERE p.portfolioId = :portfolioId) s"
                + "WHERE s.skillName = :newSkill");
        query.setParameter("portfolioId", portfolio.getPortfolioId());
        query.setParameter("newSkill", s.trim());
        
        if (query.getResultList().isEmpty()) {
            PortfolioSkill skill = new PortfolioSkill();
            skill.setSkillName(s.trim());
            skill.setPortfolio(portfolio);
            portfolio.getPortfolioSkills().add(skill);
            em.persist(skill);
            em.flush();
            return skill.getPortfolioSkillId();
        } else {
            throw new SkillAlreadyExistsException("Skill has been added already");
        }
    }

    @Override
    public void removeSkill(Portfolio portfolio, PortfolioSkill skill) {
        if (portfolio.getPortfolioSkills().contains(skill)) {
            portfolio.getPortfolioSkills().remove(skill);
            em.remove(skill);
        }
    }

    @Override
    public List<String> getPhotos(Portfolio portfolio) {
        return portfolio.getImagesUrl();
    }

    @Override
    public void addPhoto(Portfolio portfolio, String photoURL) {
        portfolio.getImagesUrl().add(photoURL);
    }

}
