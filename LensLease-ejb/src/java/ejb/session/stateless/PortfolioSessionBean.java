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
        System.out.println(p.getDescription());
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
    public void removeClient(PortfolioClient client) {
        Portfolio portfolio = client.getPortfolio();
        portfolio.getPortfolioClients().remove(client);
        em.remove(client);
    }

    @Override
    public Portfolio updateSkills(Portfolio portfolio, List<String> skillString) {
        Portfolio p = em.find(Portfolio.class, portfolio.getPortfolioId());

        if (skillString.isEmpty()) {
            p.setPortfolioSkills(new ArrayList<>());
        }

        //Remove duplicates
        List<PortfolioSkill> current = p.getPortfolioSkills();

        if (current.isEmpty()) {
            for (String s : skillString) {
                PortfolioSkill skill = new PortfolioSkill(s);
                em.persist(skill);
                skill.setPortfolio(p);
                p.getPortfolioSkills().add(skill);
            }
            return p;
        }

        for (String s : skillString) {
            for (PortfolioSkill k : current) {
                if (!k.getSkillName().equals(s)) {
                    PortfolioSkill skill = new PortfolioSkill(s);
                    em.persist(skill);
                    skill.setPortfolio(p);
                    p.getPortfolioSkills().add(skill);
                }
            }
        }

        em.flush();
        return p;
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
