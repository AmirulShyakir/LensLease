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

/**
 *
 * @author Amirul
 */
@Entity
public class PortfolioPost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioPostId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private List<String> imagesUrl;
    private Boolean isDisplayed;
    
    @ManyToOne
    private User user;

    public PortfolioPost() {
        isDisplayed = true;
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
     * @return the thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @param thumbnailUrl the thumbnailUrl to set
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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


    public Long getPortfolioPostId() {
        return portfolioPostId;
    }

    public void setPortfolioPostId(Long portfolioPostId) {
        this.portfolioPostId = portfolioPostId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portfolioPostId != null ? portfolioPostId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the portfolioPostId fields are not set
        if (!(object instanceof PortfolioPost)) {
            return false;
        }
        PortfolioPost other = (PortfolioPost) object;
        if ((this.portfolioPostId == null && other.portfolioPostId != null) || (this.portfolioPostId != null && !this.portfolioPostId.equals(other.portfolioPostId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PortfolioPost[ id=" + portfolioPostId + " ]";
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
    
    public Boolean isDisplayed() {
        return isDisplayed;
    }

    public void setIsDisplayed(Boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }
    
}
