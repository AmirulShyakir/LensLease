/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.BanRequest;
import entity.Booking;
import entity.Review;
import entity.Service;
import entity.ServiceTypeEnum;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
 * @author jonta
 */
@Named(value = "serviceManagedBean")
@ViewScoped
public class ServiceManagedBean implements Serializable {

    @EJB
    private AdminSessionBeanLocal adminSessionBean;

    @EJB
    private ReviewSessionBeanLocal reviewSessionBean;

    /**
     * Creates a new instance of ServiceManagedBean
     */
    public ServiceManagedBean() {
    }
    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBean;
    

    private User user;
    private String serviceName;
    private ServiceTypeEnum serviceType;
    private double serviceCost;
    private List<String> servicePhotos;
    private boolean isBanned;
    private User provider;
    
    private Service selectedService;
    private List<Review> reviewsForSelectedService;
    private List<Service> listOfServices;
    private List<Service> servicesProvided;
    private List<Service> listOfEquipmentRental;
    private List<Service> listOfEditingServices;
    private List<Service> listOfPhotographyServices;
   
    private String reportDescription;
    private Long serviceId; 

    private String searchString;
    private String searchType = "";

    @PostConstruct
    public void init() {
         listOfServices = serviceSessionBeanLocal.getAllServices();
        if (getSearchString() == null || getSearchString().equals("")) {
            listOfServices = serviceSessionBeanLocal.getAllServices();
            setListOfEquipmentRental(serviceSessionBeanLocal.getServicesByType(ServiceTypeEnum.EQUIPMENT_RENTAL));
            setListOfEditingServices(serviceSessionBeanLocal.getServicesByType(ServiceTypeEnum.PHOTO_EDITING));
            listOfEditingServices.addAll(serviceSessionBeanLocal.getServicesByType(ServiceTypeEnum.VIDEO_EDITING));
            setListOfPhotographyServices(serviceSessionBeanLocal.getServicesByType(ServiceTypeEnum.PHOTOGRAPHY));
            listOfPhotographyServices.addAll(serviceSessionBeanLocal.getServicesByType(ServiceTypeEnum.VIDEOGRAPHY));
        } else {
            listOfServices = serviceSessionBeanLocal.searchServices(searchString);
            listOfEditingServices = serviceSessionBeanLocal.searchServicesWithType(searchString,ServiceTypeEnum.PHOTO_EDITING);
            listOfEditingServices.addAll(serviceSessionBeanLocal.searchServicesWithType(searchString,ServiceTypeEnum.VIDEO_EDITING));
            listOfPhotographyServices = serviceSessionBeanLocal.searchServicesWithType(searchString, ServiceTypeEnum.PHOTOGRAPHY);
            listOfPhotographyServices.addAll(serviceSessionBeanLocal.searchServicesWithType(searchString, ServiceTypeEnum.VIDEOGRAPHY));
            listOfEquipmentRental = serviceSessionBeanLocal.searchServicesWithType(searchString,ServiceTypeEnum.EQUIPMENT_RENTAL);
          
        }
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "authenticationManagedBean");
            
            long userId = authenticationManagedBean.getUserId();
            setUser(userSessionBean.findUserByUserId(userId));
        } catch (Exception ex) {
            Logger.getLogger(CheckoutManagedbean.class.getName()).log(Level.SEVERE, null, ex);
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
            List<Booking> bookings = selectedService.getBookings();
            List<Review> reviews = new ArrayList<Review>();
            for (Booking b : bookings) {
                if (b.getReview() != null) {
                    reviews.add(b.getReview());
                }
            }
            setReviewsForSelectedService(reviews);
            System.out.println("Going to Individual service page with selected service: " + serviceName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
    }
    
    public void loadServicesProvided(){
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            this.servicesProvided = serviceSessionBeanLocal.getServicesByUser(user.getUserId());
        }catch(Exception e){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
        
    }
    
    public String submitReportService() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            BanRequest banRequest = new BanRequest();
            banRequest.setRequestDate(new Date());
            banRequest.setDescription(reportDescription);
            adminSessionBean.submitReportService(banRequest,serviceId, user.getUserId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "Successfully reported service "));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "landingPage.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ex.getMessage()));
            return "reportService.xhtml";
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Service> getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(List<Service> servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    /**
     * @return the reviewsForSelectedService
     */
    public List<Review> getReviewsForSelectedService() {
        return reviewsForSelectedService;
    }

    /**
     * @param reviewsForSelectedService the reviewsForSelectedService to set
     */
    public void setReviewsForSelectedService(List<Review> reviewsForSelectedService) {
        this.reviewsForSelectedService = reviewsForSelectedService;
    }

    public List<Service> getListOfEquipmentRental() {
        return listOfEquipmentRental;
    }

    public void setListOfEquipmentRental(List<Service> listOfEquipmentRental) {
        this.listOfEquipmentRental = listOfEquipmentRental;
    }

    public List<Service> getListOfEditingServices() {
        return listOfEditingServices;
    }

    public void setListOfEditingServices(List<Service> listOfEditingServices) {
        this.listOfEditingServices = listOfEditingServices;
    }

    public List<Service> getListOfPhotographyServices() {
        return listOfPhotographyServices;
    }

    public void setListOfPhotographyServices(List<Service> listOfPhotographyServices) {
        this.listOfPhotographyServices = listOfPhotographyServices;
    }

    /**
     * @return the reportDescription
     */
    public String getReportDescription() {
        return reportDescription;
    }

    /**
     * @param reportDescription the reportDescription to set
     */
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }
}
