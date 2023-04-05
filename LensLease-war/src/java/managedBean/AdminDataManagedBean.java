/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.BanRequest;
import entity.Service;
import entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author jonta
 */
@Named(value = "adminDataManagedBean")
@ViewScoped
public class AdminDataManagedBean implements Serializable{

    /**
     * Creates a new instance of AdminDataManagedBean
     */
    public AdminDataManagedBean() {
    }
    
    @EJB
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private Long banRequestId;
    private String banDescription;
    private Date banRequestDate;
    private Service banService;
    private User banUser;
    private List<BanRequest> listOfBanRequests;
    private BanRequest selectedBanRequest;
    
    @PostConstruct
    public void init() {
            listOfBanRequests = adminSessionBeanLocal.getAllBanRequests();
       
    }

    public void handleSearch() {
        init();
    }

    public void loadSelectedBanRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.selectedBanRequest = adminSessionBeanLocal.findBanRequestById(banRequestId);
            banDescription = this.selectedBanRequest.getDescription();
            banRequestDate = this.selectedBanRequest.getRequestDate();
            banService = this.selectedBanRequest.getService();
//            provider = this.selectedBanRequest.getProvider();
//            System.out.println("Going to Individual service page with selected service: " + serviceName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Ban Request"));
        }
    }
    
    public void acceptBanRequest() {
        try {
            adminSessionBeanLocal.acceptBanRequest(selectedBanRequest.getBanRequestId());
        } catch (UserNotFoundException | ServiceNotFoundException ex) {
            Logger.getLogger(AdminDataManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Long getBanRequestId() {
        return banRequestId;
    }

    public void setBanRequestId(Long banRequestId) {
        this.banRequestId = banRequestId;
    }

    public String getBanDescription() {
        return banDescription;
    }

    public void setBanDescription(String banDescription) {
        this.banDescription = banDescription;
    }

    public Date getBanRequestDate() {
        return banRequestDate;
    }

    public void setBanRequestDate(Date banRequestDate) {
        this.banRequestDate = banRequestDate;
    }

    public Service getBanService() {
        return banService;
    }

    public void setBanService(Service banService) {
        this.banService = banService;
    }

    public List<BanRequest> getListOfBanRequests() {
        return listOfBanRequests;
    }

    public void setListOfBanRequests(List<BanRequest> listOfBanRequests) {
        this.listOfBanRequests = listOfBanRequests;
    }

    public BanRequest getSelectedBanRequest() {
        return selectedBanRequest;
    }

    public void setSelectedBanRequest(BanRequest selectedBanRequest) {
        this.selectedBanRequest = selectedBanRequest;
        System.out.println(selectedBanRequest.getBanRequestId());
    }

    public User getBanUser() {
        return banUser;
    }

    public void setBanUser(User banUser) {
        this.banUser = banUser;
    }
    
}