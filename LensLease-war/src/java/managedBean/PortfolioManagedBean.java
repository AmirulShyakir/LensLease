/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.PortfolioSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Portfolio;
import entity.PortfolioClient;
import entity.PortfolioSkill;
import entity.Review;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import util.exception.IncompleteFieldsException;

/**
 *
 * @author leeannong
 */
@Named(value = "portfolioManagedBean")
@ViewScoped
public class PortfolioManagedBean implements Serializable {

    @EJB
    private ReviewSessionBeanLocal reviewSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private PortfolioSessionBeanLocal portfolioSessionBean;

    private User user;
    private Portfolio portfolio;
    private String description;
    private List<String> inputSkills;
    private PortfolioClient client;
    private List<PortfolioClient> allClients;
    private List<String> images;
    private UploadedFiles newImages;
    
    private Long providerId;
    private User provider;
    private Portfolio providerPortfolio;
    private List<Review> reviews;

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
            convertSkillsToString(portfolio.getPortfolioSkills());
            setImages(portfolio.getImagesUrl());
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", ex.getMessage()));
        }
    }

    public void updateDescription() {
        portfolioSessionBean.updateDescription(portfolio, description);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully updated about me", ""));
    }

    public void newClient() {
        this.client = new PortfolioClient();
    }

    public void saveClient() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (client.getPortfolioClientId() == null) {
                this.portfolio = portfolioSessionBean.createClient(portfolio, client);
                setAllClients(portfolio.getPortfolioClients());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Client successfully added", ""));
            } else {
                this.portfolio = portfolioSessionBean.updateClient(portfolio, client);
                setAllClients(portfolio.getPortfolioClients());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Client successfully updated", ""));
            }

        } catch (IncompleteFieldsException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", ex.getMessage()));
        }
        PrimeFaces.current().executeScript("PF('clientDialog').hide()");
        PrimeFaces.current().ajax().update("form:clients");
    }

    public String deleteClient() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.portfolio = portfolioSessionBean.removeClient(client);
        setAllClients(portfolio.getPortfolioClients());
        context.addMessage(null, new FacesMessage("Client successfully deleted"));

        PrimeFaces.current().executeScript("PF('deleteDialog').hide()");
        PrimeFaces.current().ajax().update("form:clients");
        return "myPortfolio.xhtml?faces-redirect=true";
    }

    public void updateSkills() {
        if (inputSkills == null) {
            inputSkills = new ArrayList<>();
        }
        this.portfolio = portfolioSessionBean.updateSkills(portfolio, inputSkills);
        convertSkillsToString(portfolio.getPortfolioSkills());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Skill successfully updated", ""));
    }

    public void uploadImages() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (newImages != null) {
            for (UploadedFile f : newImages.getFiles()) {
                try {
                    String s = copyFile(f.getFileName(), f.getInputStream());
                    getImages().add(s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                try {
//                    portfolio = portfolioSessionBean.addPhoto(portfolio, i);
//                    setImages(portfolio.getImagesUrl());
//                } catch (ImageDuplicateException ex) {
//                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error " + ex.getMessage(), ""));
//                }
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Images have been uploaded", ""));
            PrimeFaces.current().ajax().update("form:gallery");
        }
    }

    public String copyFile(String fileName, InputStream in) {
        String destination = "/galleryUploads/";
        try {
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            return destination + fileName;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "empty";
    }
    
    public void loadProviderDetails() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            setPortfolio(portfolioSessionBean.findPortfolioByUserId(providerId));
            setUser(userSessionBean.findUserByUserId(providerId));
            setReviews(reviewSessionBean.getReviewsByUserId(providerId));
            setDescription(portfolio.getDescription());
            setAllClients(portfolio.getPortfolioClients());
            convertSkillsToString(portfolio.getPortfolioSkills());
            setImages(portfolio.getImagesUrl());
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", ex.getMessage()));
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

    public void setInputSkills(List<String> inputSkills) {
        this.inputSkills = inputSkills;
    }

    public void convertSkillsToString(List<PortfolioSkill> skills) {
        List<String> skillString = new ArrayList<>();
        for (PortfolioSkill s : skills) {
            skillString.add(s.getSkillName());
        }
        setInputSkills(skillString);
    }

    public UploadedFiles getNewImages() {
        return newImages;
    }

    public void setNewImages(UploadedFiles newImages) {
        this.newImages = newImages;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public Portfolio getProviderPortfolio() {
        return providerPortfolio;
    }

    public void setProviderPortfolio(Portfolio providerPortfolio) {
        this.providerPortfolio = providerPortfolio;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
