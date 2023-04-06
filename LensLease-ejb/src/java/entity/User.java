/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    
    //service & bookings
    @OneToMany(mappedBy = "provider")
    private List<Service> services;
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    @OneToOne
    private Portfolio portfolio;

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
    
    public double getRating() {
        double numRatings = 0;
        double totalRatingScore = 0;
        
        if (services.isEmpty()) {
            return -1;
        } else {
            for (Service s : services) {
                if (!s.getBookings().isEmpty()) {
                    List<Booking> bookings = s.getBookings();
                    for (Booking b : bookings) {
                        if (b.getReview() != null) {
                            numRatings++;
                            totalRatingScore += b.getReview().getStarRating();
                        }
                    }
                }
            }
        }
        if (numRatings == 0) {
            return -1;
        } else {
             return totalRatingScore / numRatings;
        }
    }
    
    public int getStarRating() {
        return (int) Math.round(getRating());
    }
    
    public String getFormattedRating() {
        DecimalFormat df = new DecimalFormat("#.0");
        double rating = getRating();
        if (rating == -1) {
            return "No Ratings Yet";
        } 
        String formatted = df.format(rating);
        return formatted;
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

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
    
}
