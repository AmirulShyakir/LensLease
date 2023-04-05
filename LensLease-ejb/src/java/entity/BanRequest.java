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
    
    @ManyToOne
    private Service service;
    @ManyToOne
    private User user;

    public BanRequest() {
    }

    public BanRequest(String description, Date requestDate, Service service, User user) {
        this.description = description;
        this.requestDate = requestDate;
        this.service = service;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
