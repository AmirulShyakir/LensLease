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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ImageDuplicateException;
import util.exception.IncompleteFieldsException;
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
    public void updateDescription(Portfolio portfolio, String s) {
        Portfolio p = em.find(Portfolio.class, portfolio.getPortfolioId());
        p.setDescription(s);
    }

    @Override
    public Portfolio createClient(Portfolio portfolio, PortfolioClient client) throws IncompleteFieldsException {
        Portfolio p = em.find(Portfolio.class, portfolio.getPortfolioId());
        if (client.getClientName() != null && client.getClientLink() != null) {
            em.persist(client);
            client.setPortfolio(p);
            p.getPortfolioClients().add(client);
            em.flush();
            return p;
        } else {
            throw new IncompleteFieldsException("Please complete all fields to create client");
        }
    }

    @Override
    public Portfolio updateClient(Portfolio portfolio, PortfolioClient client) throws IncompleteFieldsException {
        Portfolio p = em.find(Portfolio.class, portfolio.getPortfolioId());
        PortfolioClient c = em.find(PortfolioClient.class, client.getPortfolioClientId());
        if (client.getClientName() != null && client.getClientLink() != null) {
            c.setClientName(client.getClientName());
            c.setClientLink(client.getClientLink());
            em.flush();
            return p;
        } else {
            throw new IncompleteFieldsException("Please complete all fields to update client");
        }
    }

    @Override
    public Portfolio removeClient(PortfolioClient client) {
        PortfolioClient c = em.find(PortfolioClient.class, client.getPortfolioClientId());
        Portfolio portfolio = c.getPortfolio();
        portfolio.getPortfolioClients().remove(c);
        c.setPortfolio(null);
        em.remove(c);
        return portfolio;
    }

    @Override
    public Portfolio updateSkills(Portfolio portfolio, List<String> input) {
        Portfolio p = em.find(Portfolio.class, portfolio.getPortfolioId());

        if (input.isEmpty()) {
            p.setPortfolioSkills(new ArrayList<>());
            return p;
        }
        int originalSize = p.getPortfolioSkills().size();
        for (int x = 0; x < originalSize; x++) {
            PortfolioSkill skill = em.find(PortfolioSkill.class, p.getPortfolioSkills().get(originalSize - 1 - x).getPortfolioSkillId());
            p.getPortfolioSkills().remove(skill);
            skill.setPortfolio(null);
            em.remove(skill);
            em.flush();
        }

        if (p.getPortfolioSkills().isEmpty()) {
            for (String s : input) {
                PortfolioSkill skill = new PortfolioSkill(s);
                em.persist(skill);
                skill.setPortfolio(p);
                p.getPortfolioSkills().add(skill);
            }
            return p;
        }

        em.flush();
        return p;
    }

    @Override
    public Portfolio addPhoto(Portfolio portfolio, String photoURL) throws ImageDuplicateException {
        Portfolio p = em.find(Portfolio.class, portfolio.getPortfolioId());
        
        if (!p.getImagesUrl().contains(photoURL)) {
            p.getImagesUrl().add(photoURL);
        } else {
            throw new ImageDuplicateException("Duplicate image " + photoURL + " not uploaded");
        }
        return p;
    }

}
