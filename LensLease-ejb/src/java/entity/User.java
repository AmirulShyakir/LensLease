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
import javax.persistence.OneToMany;

/**
 *
 * @author Amirul
 */
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String contactNumber;
    private String name;
    private String password;
    private String photoUrl;
    private boolean isBanned;
    
    //portfolio
    @ManyToMany
    private List<PortfolioSkill> portfolioSkills;
    @ManyToMany
    private List<PortfolioClient> portfolioClients;
    @OneToMany(mappedBy = "user")
    private List<PortfolioPost> portfolioPosts;
    
    //service & bookings
    @OneToMany(mappedBy = "provider")
    private List<Service> services;
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "poster")
    private List<ForumTopic> forumTopics;
    @OneToMany(mappedBy = "replier")
    private List<ForumReply> forumReplys;

    public User() {
        this.setIsBanned(false);
    }

    public User(String username, String email, String contactNumber, String name, String password) {
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.name = name;
        this.password = password;
        this.setIsBanned(false);
    }
    
    
    /**
     * @return the portfolioSkills
     */
    public List<PortfolioSkill> getPortfolioSkills() {
        return portfolioSkills;
    }

    /**
     * @param portfolioSkills the portfolioSkills to set
     */
    public void setPortfolioSkills(List<PortfolioSkill> portfolioSkills) {
        this.portfolioSkills = portfolioSkills;
    }

    /**
     * @return the portfolioClients
     */
    public List<PortfolioClient> getPortfolioClients() {
        return portfolioClients;
    }

    /**
     * @param portfolioClients the portfolioClients to set
     */
    public void setPortfolioClients(List<PortfolioClient> portfolioClients) {
        this.portfolioClients = portfolioClients;
    }

    /**
     * @return the portfolioPosts
     */
    public List<PortfolioPost> getPortfolioPosts() {
        return portfolioPosts;
    }

    /**
     * @param portfolioPosts the portfolioPosts to set
     */
    public void setPortfolioPosts(List<PortfolioPost> portfolioPosts) {
        this.portfolioPosts = portfolioPosts;
    }

    /**
     * @return the services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List<Service> services) {
        this.services = services;
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
     * @return the photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @param photoUrl the photoUrl to set
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

   

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + userId + " ]";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
    
}
