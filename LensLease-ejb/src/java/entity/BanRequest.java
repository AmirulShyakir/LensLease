/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Amirul
 */
@Entity
public class BanRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long banRequestId;
    private String description;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date requestDate;
    private boolean isAttendedTo;
    private boolean isRejected;
    @ManyToOne
    private User complainer;
    
    @ManyToOne
    private Service serviceToBan;
    @ManyToOne
    private User userToBan;
    @ManyToOne
    private Booking booking;

    public BanRequest() {
    }

    public BanRequest(String description, Date requestDate, Boolean isAttendedTo, User complainer, Service service, User user,  Booking booking) {
        this.description = description;
        this.requestDate = requestDate;
        this.isAttendedTo = isAttendedTo;
        this.complainer = complainer;
        this.serviceToBan = service;
        this.userToBan = user;
        this.booking = booking;
    }
    
    public Long getBanRequestId() {
        return banRequestId;
    }

    public void setBanRequestId(Long banRequestId) {
        this.banRequestId = banRequestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banRequestId != null ? banRequestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the banRequestId fields are not set
        if (!(object instanceof BanRequest)) {
            return false;
        }
        BanRequest other = (BanRequest) object;
        if ((this.banRequestId == null && other.banRequestId != null) || (this.banRequestId != null && !this.banRequestId.equals(other.banRequestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BanRequest[ id=" + banRequestId + " ]";
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isIsAttendedTo() {
        return isAttendedTo;
    }

    public void setIsAttendedTo(boolean isAttendedTo) {
        this.isAttendedTo = isAttendedTo;
    }

    public User getComplainer() {
        return complainer;
    }

    public void setComplainer(User complainer) {
        this.complainer = complainer;
    }

    /**
     * @return the service
     */
    public Service getServiceToBan() {
        return serviceToBan;
    }

    /**
     * @param serviceToBan the service to set
     */
    public void setServiceToBan(Service serviceToBan) {
        this.serviceToBan = serviceToBan;
    }

    public User getUserToBan() {
        return userToBan;
    }

    public void setUserToBan(User userToBan) {
        this.userToBan = userToBan;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public boolean isIsRejected() {
        return isRejected;
    }

    public void setIsRejected(boolean isRejected) {
        this.isRejected = isRejected;
    }
}
