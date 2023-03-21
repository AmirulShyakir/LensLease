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
import javax.persistence.ManyToMany;

/**
 *
 * @author Amirul
 */
@Entity
public class PortfolioClient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioClientId;
    private String clientName;
    private String clientPhotoUrl;

    @ManyToMany(mappedBy = "portfolioClients")
    private List<User> users;

    public PortfolioClient() {
    }
    
    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return the clientPhotoUrl
     */
    public String getClientPhotoUrl() {
        return clientPhotoUrl;
    }

    /**
     * @param clientPhotoUrl the clientPhotoUrl to set
     */
    public void setClientPhotoUrl(String clientPhotoUrl) {
        this.clientPhotoUrl = clientPhotoUrl;
    }

    public Long getPortfolioClientId() {
        return portfolioClientId;
    }

    public void setPortfolioClientId(Long portfolioClientId) {
        this.portfolioClientId = portfolioClientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portfolioClientId != null ? portfolioClientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the portfolioClientId fields are not set
        if (!(object instanceof PortfolioClient)) {
            return false;
        }
        PortfolioClient other = (PortfolioClient) object;
        if ((this.portfolioClientId == null && other.portfolioClientId != null) || (this.portfolioClientId != null && !this.portfolioClientId.equals(other.portfolioClientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PortfolioClient[ id=" + portfolioClientId + " ]";
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
