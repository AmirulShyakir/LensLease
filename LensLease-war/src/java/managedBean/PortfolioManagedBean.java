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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import util.exception.IncompleteFieldsException;

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
    private List<String> inputSkills;
    private List<PortfolioSkill> allSkills;
    private PortfolioClient client;
    private List<PortfolioClient> allClients;
    private List<String> images;
    private int serialNumber = 0;
    
    private String clientName;
    private String clientLink;

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
            setDescription(portfolio.getDescription());
            setAllClients(portfolio.getPortfolioClients());
            setInputSkills(portfolio.getPortfolioSkills());
            setImages(portfolio.getImagesUrl());
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
    }
    
    public void updateDescription() {
        portfolioSessionBean.updateDescription(portfolio, description);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful Update", "for user biography"));
    }
    
    public void generateSerial() {
        serialNumber++;
    }
    
    public void saveClient() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (client.getPortfolioClientId() == null) {
                this.portfolio = portfolioSessionBean.createClient(portfolio, client);
                setAllClients(portfolio.getPortfolioClients());
                context.addMessage(null, new FacesMessage("Client successfully added"));
            } else {
                context.addMessage(null, new FacesMessage("Client successfully updated"));
            }
            
        } catch (IncompleteFieldsException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
    }
    
    public void updateSkills() {
        trimSkillName();
        portfolioSessionBean.updateSkills(portfolio, inputSkills);
        System.out.println(portfolio.getPortfolioSkills());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful Update", "for skills"));
    }
    
    public void trimSkillName() {
        List<String> trimmed = new ArrayList<>();
        for (String s : inputSkills) {
            trimmed.add(s.trim());
        }
        inputSkills = trimmed;
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

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLink() {
        return clientLink;
    }

    public void setClientLink(String clientLink) {
        this.clientLink = clientLink;
    }

    public List<String> getInputSkills() {
        return inputSkills;
    }

    public void setInputSkills(List<PortfolioSkill> skills) {
        List<String> skillString = new ArrayList<>();
        for (PortfolioSkill s : skills) {
            skillString.add(s.getSkillName());
        }
        this.inputSkills = skillString;
    }

    public List<PortfolioSkill> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(List<PortfolioSkill> allSkills) {
        this.allSkills = allSkills;
    }
    
    
}
