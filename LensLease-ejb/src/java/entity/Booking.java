/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class Booking implements Serializable {
    /**
     * @param bookingStatus the bookingStatus to set
     */
    public void setBookingStatus(BookingStatusEnum bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private String startTime; //refers to start time for services, collection time for rentals
    private String preferredLocation;
    private String projectSpecifications;
    private String comments;
    private BookingStatusEnum bookingStatus;
    
    @ManyToOne
    private Service service;
    @ManyToOne
    private User customer;
   
    @OneToOne
    private Review review;

    public Booking() {
    }
    
    public Booking(Date date, String startTime, String preferredLocation, String projectSpecifications, BookingStatusEnum bookingStatus) {
        this.date = date;
        this.startTime = startTime;
        this.preferredLocation = preferredLocation;
        this.bookingStatus = bookingStatus;
    }
    
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long id) {
        this.bookingId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Booking[ id=" + bookingId + " ]";
    }

    /**
     * @return the startDateTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startDateTime the startDateTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @return the customer
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    /**
     * @return the review
     */
    public Review getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(Review review) {
        this.review = review;
    }
    
    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the bookingStatus
     */
    public BookingStatusEnum getBookingStatus() {
        return bookingStatus;
    }

    /**
     * @return the preferredLocation
     */
    public String getPreferredLocation() {
        return preferredLocation;
    }

    /**
     * @param preferredLocation the preferredLocation to set
     */
    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the projectSpecifications
     */
    public String getProjectSpecifications() {
        return projectSpecifications;
    }

    /**
     * @param projectSpecifications the projectSpecifications to set
     */
    public void setProjectSpecifications(String projectSpecifications) {
        this.projectSpecifications = projectSpecifications;
    }
    
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
