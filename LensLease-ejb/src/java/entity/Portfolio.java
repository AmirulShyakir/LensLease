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
import javax.persistence.OneToOne;

/**
 *
 * @author Amirul
 */
@Entity
public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;
    private String title;
    private String description;
    private List<String> imagesUrl;
       
    @OneToOne(mappedBy = "portfolio")
    private User user;
    @ManyToMany
    private List<PortfolioClient> clients;
    @ManyToMany
    private List<PortfolioSkill> skills;

    public Portfolio() {
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the imagesUrl
     */
    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    /**
     * @param imagesUrl the imagesUrl to set
     */
    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }


    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portfolioId != null ? portfolioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the portfolioId fields are not set
        if (!(object instanceof Portfolio)) {
            return false;
        }
        Portfolio other = (Portfolio) object;
        if ((this.portfolioId == null && other.portfolioId != null) || (this.portfolioId != null && !this.portfolioId.equals(other.portfolioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PortfolioPost[ id=" + portfolioId + " ]";
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public List<PortfolioClient> getClients() {
        return clients;
    }

    public void setClients(List<PortfolioClient> clients) {
        this.clients = clients;
    }

    public List<PortfolioSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<PortfolioSkill> skills) {
        this.skills = skills;
    }
    
}
