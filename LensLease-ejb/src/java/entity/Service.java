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
    private boolean isDelisted;
    
    @ManyToOne
    private User provider;
    @OneToMany(mappedBy = "service")
    private List<BanRequest> banRequests;
    @OneToMany(mappedBy = "service")
    private List<Booking> bookings;
    @OneToOne
    private Schedule schedule;

    public Service() {
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

    public boolean isDelisted() {
        return isDelisted;
    }

    public void setIsDelisted(boolean isDelisted) {
        this.isDelisted = isDelisted;
    }
    
    
}
