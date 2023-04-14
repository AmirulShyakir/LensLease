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
import java.util.TimeZone;
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
    private List<BanRequest> listOfPastBanRequests;
    private BanRequest selectedBanRequest;
       private TimeZone timeZone;
    
    @PostConstruct
    public void init() {
            listOfBanRequests = adminSessionBeanLocal.getAllBanRequests();
            setListOfPastBanRequests(adminSessionBeanLocal.getPastBanRequests());

       
    }
    
    public TimeZone getTimeZome() {
        timeZone = TimeZone.getDefault();
        return timeZone;
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
            banService = this.selectedBanRequest.getServiceToBan();
            banUser = this.selectedBanRequest.getUserToBan();
//            System.out.println("Going to Individual service page with selected service: " + serviceName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Ban Request"));
        }
    }
    
    public void acceptBanRequest() {
        try {
            adminSessionBeanLocal.acceptBanRequest(selectedBanRequest.getBanRequestId());
        } catch (UserNotFoundException | ServiceNotFoundException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ban Request Accepted", "Successfully attended to");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     public void unbanUser() {
        try {
            adminSessionBeanLocal.unbanUser(selectedBanRequest.getUserToBan().getUserId());
        } catch (UserNotFoundException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ban Request Accepted", "Successfully attended to");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
     public void unbanService() {
        try {
            adminSessionBeanLocal.unbanService(selectedBanRequest.getServiceToBan().getServiceId());
        } catch (ServiceNotFoundException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ban Request Accepted", "Successfully attended to");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void rejectBanRequest() {
        try {
            adminSessionBeanLocal.rejectBanRequest(selectedBanRequest.getBanRequestId());
        } catch (UserNotFoundException | ServiceNotFoundException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ban Request Accepted", "Successfully attended to");
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public List<BanRequest> getListOfPastBanRequests() {
        return listOfPastBanRequests;
    }

    public void setListOfPastBanRequests(List<BanRequest> listOfPastBanRequests) {
        this.listOfPastBanRequests = listOfPastBanRequests;
    }

    
}
