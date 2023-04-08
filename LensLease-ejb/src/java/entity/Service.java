/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Amirul
 */
@Entity
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    private String serviceName;
    private ServiceTypeEnum serviceType;
    private double serviceCost;
    private List<String> servicePhotos;
    private boolean isBanned;
    private String serviceDescription;
    
    //to display different stuff on checkout page
    private boolean isRental;
    private boolean isEditing;
    
    //for rental
    private String earliestCollectionTime;
    private String latestReturnTime;
    //for videography, photography services and editing (rendered as estimated completion window
    private String packageDurationHours;

    private boolean isDelisted;

    
    @ManyToOne
    private User provider;
    @OneToMany(mappedBy = "serviceToBan")
    private List<BanRequest> banRequests;
    @OneToMany(mappedBy = "service")
    private List<Booking> bookings;
    @OneToOne
    private Schedule schedule;

    public Service() {
    }

    public Service(String serviceName, ServiceTypeEnum serviceType, double serviceCost, List<String> servicePhotos, boolean isBanned, User provider) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.serviceCost = serviceCost;
        this.servicePhotos = servicePhotos;
        this.isBanned = isBanned;
        this.provider = provider;
        
        if (serviceType == serviceType.EQUIPMENT_RENTAL) {
            this.isRental = true;
            this.packageDurationHours = "Full Day Rental";
        } else {
            this.isRental = false;
            if (serviceType == serviceType.PHOTO_EDITING ||serviceType == serviceType.VIDEO_EDITING) {
                this.isEditing = true;
            }
        }
    }
    
    
    public Service(String serviceName, double serviceCost, ServiceTypeEnum serviceType, String serviceDescription) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
        this.serviceType = serviceType;
        this.isBanned = false;
        this.serviceDescription = serviceDescription;
        
        if (serviceType == serviceType.EQUIPMENT_RENTAL) {
            this.isRental = true;
            this.packageDurationHours = "Full Day Rental";
        } else {
            this.isRental = false;
            if (serviceType == serviceType.PHOTO_EDITING ||serviceType == serviceType.VIDEO_EDITING) {
                this.isEditing = true;
            }
        }
    }
    
    /**
     * @return the provider
     */
    public User getProvider() {
        return provider;
    }

    /**
     * @param provider the provider to set
     */
    public void setProvider(User provider) {
        this.provider = provider;
    }

    /**
     * @return the banRequests
     */
    public List<BanRequest> getBanRequests() {
        return banRequests;
    }

    /**
     * @param banRequests the banRequests to set
     */
    public void setBanRequests(List<BanRequest> banRequests) {
        this.banRequests = banRequests;
    }

    /**
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * @param bookings the bookings to set
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * @return the schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * @param schedule the schedule to set
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the serviceId fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Service[ id=" + serviceId + " ]";
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the serviceType
     */
    public ServiceTypeEnum getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(ServiceTypeEnum serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return the serviceCost
     */
    public double getServiceCost() {
        return serviceCost;
    }

    /**
     * @param serviceCost the serviceCost to set
     */
    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    /**
     * @return the servicePhotos
     */
    public List<String> getServicePhotos() {
        return servicePhotos;
    }

    /**
     * @param servicePhotos the servicePhotos to set
     */
    public void setServicePhotos(List<String> servicePhotos) {
        this.servicePhotos = servicePhotos;
    }

    /**
     * @return the isBanned
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the serviceDescription
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     * @param serviceDescription the serviceDescription to set
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    /**
     * @return the earliestCollectionTime
     */
    public String getEarliestCollectionTime() {
        return earliestCollectionTime;
    }

    /**
     * @param earliestCollectionTime the earliestCollectionTime to set
     */
    public void setEarliestCollectionTime(String earliestCollectionTime) {
        this.earliestCollectionTime = earliestCollectionTime;
    }

    /**
     * @return the latestReturnTime
     */
    public String getLatestReturnTime() {
        return latestReturnTime;
    }

    /**
     * @param latestReturnTime the latestReturnTime to set
     */
    public void setLatestReturnTime(String latestReturnTime) {
        this.latestReturnTime = latestReturnTime;
    }

    /**
     * @return the packageDurationHours
     */
    public String getPackageDurationHours() {
        return packageDurationHours;
    }

    /**
     * @param packageDurationHours the packageDurationHours to set
     */
    public void setPackageDurationHours(String packageDurationHours) {
        this.packageDurationHours = packageDurationHours;
    }

    /**
     * @return the isRental
     */
    public boolean isRental() {
        return isRental;
    }

    /**
     * @param isRental the isRental to set
     */
    public void setIsRental(boolean isRental) {
        this.isRental = isRental;
    }

    public boolean isDelisted() {
        return isDelisted;
    }

    public void setIsDelisted(boolean isDelisted) {
        this.isDelisted = isDelisted;
    }

    /**
     * @return the isEditing
     */
    public boolean isEditing() {
        return isEditing;
    }

    /**
     * @param isEditing the isEditing to set
     */
    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }
    
}
