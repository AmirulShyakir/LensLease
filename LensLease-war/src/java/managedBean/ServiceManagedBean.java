/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Service;
import entity.ServiceTypeEnum;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author jonta
 */
@Named(value = "serviceManagedBean")
@ViewScoped
public class ServiceManagedBean implements Serializable {

    /**
     * Creates a new instance of ServiceManagedBean
     */
    public ServiceManagedBean() {
    }
    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    private String serviceName;
    private ServiceTypeEnum serviceType;
    private double serviceCost;
    private List<String> servicePhotos;
    private boolean isBanned;
    private User provider;
    
    private Service selectedService;
    private List<Service> listOfServices;
    private Long serviceId; 

    private String searchString;
    private String searchType = "";

    @PostConstruct
    public void init() {

        if (getSearchString() == null || getSearchString().equals("")) {
            setListOfServices(serviceSessionBeanLocal.getAllServices());
        } else {
            listOfServices = serviceSessionBeanLocal.searchServices(searchString);
        }
    }

    public void handleSearch() {
        init();
    }


    public void loadSelectedService() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.selectedService = serviceSessionBeanLocal.findServiceByServiceId(serviceId);
            serviceName = this.selectedService.getServiceName();
            serviceType = this.selectedService.getServiceType();
            serviceCost = this.selectedService.getServiceCost();
            servicePhotos = this.selectedService.getServicePhotos();
            provider = this.selectedService.getProvider();

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceTypeEnum getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeEnum serviceType) {
        this.serviceType = serviceType;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public List<String> getServicePhotos() {
        return servicePhotos;
    }

    public void setServicePhotos(List<String> servicePhotos) {
        this.servicePhotos = servicePhotos;
    }

    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public List<Service> getListOfServices() {
        return listOfServices;
    }

    public void setListOfServices(List<Service> listOfServices) {
        this.listOfServices = listOfServices;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    
    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }
}
