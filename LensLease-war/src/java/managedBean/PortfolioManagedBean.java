/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.PortfolioSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Portfolio;
import entity.PortfolioClient;
import entity.PortfolioSkill;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author leeannong
 */
@Named(value = "portfolioManagedBean")
@ViewScoped
public class PortfolioManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private PortfolioSessionBeanLocal portfolioSessionBean;
    
    private User user;
    private Portfolio portfolio;
    private String description;
    private PortfolioSkill skill;
    private List<PortfolioSkill> allSkills;
    private PortfolioClient client;
    private List<PortfolioClient> allClients;
    private List<String> images;

    /**
     * Creates a new instance of PortfolioManagedBean
     */
    public PortfolioManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "authenticationManagedBean");
            
            long userId = authenticationManagedBean.getUserId();
            setUser(userSessionBean.findUserByUserId(userId));
        } catch (Exception ex) {
            Logger.getLogger(PortfolioManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadPortfolioInformation() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            setPortfolio(portfolioSessionBean.findPortfolioByUserId(user.getUserId()));
            setDescription(portfolioSessionBean.getDescription(portfolio));
            setAllClients(portfolio.getPortfolioClients());
            setAllSkills(portfolio.getPortfolioSkills());
            setImages(portfolio.getImagesUrl());
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error error", "This"));
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public PortfolioSkill getSkill() {
        return skill;
    }

    public void setSkill(PortfolioSkill skill) {
        this.skill = skill;
    }

    public List<PortfolioSkill> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(List<PortfolioSkill> allSkills) {
        this.allSkills = allSkills;
    }

    public PortfolioClient getClient() {
        return client;
    }

    public void setClient(PortfolioClient client) {
        this.client = client;
    }

    public List<PortfolioClient> getAllClients() {
        return allClients;
    }

    public void setAllClients(List<PortfolioClient> allClients) {
        this.allClients = allClients;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    
    
}
