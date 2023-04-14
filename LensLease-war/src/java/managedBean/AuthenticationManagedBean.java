package managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.Review;
import entity.User;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.UserAlreadyExistsException;
import util.exception.UserNotFoundException;

@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private ReviewSessionBeanLocal reviewSessionBean;

    @EJB
    private AdminSessionBeanLocal adminSessionBean;
    private String adminUsername = null;
    private String adminPassword = null;
    private long adminId = -1;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private String username = null;
    private String password = null;
    private long userId = -1;
    private String name;
    private String email;
    private String contact;
    private List<Review> reviews;
    private User user;

    public AuthenticationManagedBean() {
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            User u = userSessionBean.userLogin(getUsername(), getPassword());
            setUser(u);
            setUserId(u.getUserId());
            return "/secret/landingPage.xhtml?faces-redirect=true";
        } catch (UserNotFoundException | InvalidLoginException ex) {
            setUsername(null);
            setPassword(null);
            setUserId(-1);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            return "/login.xhtml";
        }
    }

    public String signup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            User u = new User();
            u.setName(name);
            u.setEmail(email);
            u.setContactNumber(contact);
            u.setUsername(username);
            u.setPassword(password);
            u.setPhotoUrl("https://t4.ftcdn.net/jpg/02/15/84/43/240_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg");
            Long id = userSessionBean.userSignup(u);
            setUserId(id);
            setUser(u);
            //System.out.println("signup");
            return "/secret/landingPage.xhtml?faces-redirect=true";
        } catch (UserAlreadyExistsException ex) {
            setUsername(null);
            setPassword(null);
            setUserId(-1);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "/signup.xhtml";
        }
    }

    public String logout() {
        username = null;
        password = null;
        userId = -1;
        return "/login.xhtml?faces-redirect=true";
    }

    public String adminLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Admin a = adminSessionBean.adminLogin(getAdminUsername(), getAdminPassword());
            setAdminId(a.getAdminId());
            return "/admin/homePage.xhtml?faces-redirect=true";
        } catch (AdminNotFoundException | InvalidLoginException ex) {
            setAdminUsername(null);
            setAdminPassword(null);
            setAdminId(-1);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "" ));
            return "/adminLogin.xhtml";    
        }
    }

    public String adminLogout() {
        setAdminUsername(null);
        setAdminPassword(null);
        setAdminId(-1);
        return "/adminLogin.xhtml?faces-redirect=true";
    }

    public void loadReviewsForUser() {
        try {
            this.setReviews(reviewSessionBean.getReviewsByUserId(this.userId));
        } catch (UserNotFoundException ex) {
            Logger.getLogger(AuthenticationManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //getter and setter for the attributes
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the adminUsername
     */
    public String getAdminUsername() {
        return adminUsername;
    }

    /**
     * @param adminUsername the adminUsername to set
     */
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    /**
     * @return the adminPassword
     */
    public String getAdminPassword() {
        return adminPassword;
    }

    /**
     * @param adminPassword the adminPassword to set
     */
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    /**
     * @return the adminId
     */
    public long getAdminId() {
        return adminId;
    }

    /**
     * @param adminId the adminId to set
     */
    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
