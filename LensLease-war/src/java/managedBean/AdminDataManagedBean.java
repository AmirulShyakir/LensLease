/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import entity.BanRequest;
import entity.Service;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jonta
 */
@Named(value = "adminDataManagedBean")
@RequestScoped
public class AdminDataManagedBean {

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
    private List<BanRequest> listOfBanRequests;
    private BanRequest selectedBanRequest;
    
    @PostConstruct
    public void init() {
            listOfBanRequests = adminSessionBeanLocal.getAllBanRequests();
       
    }

    public void handleSearch() {
        init();
    }

//    public void loadSelectedBanRequest() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        try {
//            this.selectedService = serviceSessionBeanLocal.findServiceByServiceId(serviceId);
//            serviceName = this.selectedService.getServiceName();
//            serviceType = this.selectedService.getServiceType();
//            serviceCost = this.selectedService.getServiceCost();
//            servicePhotos = this.selectedService.getServicePhotos();
//            provider = this.selectedService.getProvider();
//            System.out.println("Going to Individual service page with selected service: " + serviceName);
//        } catch (Exception e) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
//        }
//    }
    
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
    
}
