package managedBean;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.UserNotFoundException;

@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

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

    public AuthenticationManagedBean() {
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            User u = userSessionBean.userLogin(getUsername(), getPassword());
            setUserId(u.getUserId());
            return "/secret/index.xhtml?faces-redirect=true";
        } catch (UserNotFoundException | InvalidLoginException ex) {
            setUsername(null);
            setPassword(null);
            setUserId(-1);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "" ));
            return "/login.xhtml";    
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
            Admin a = adminSessionBean.adminLogin(getUsername(), getPassword());
            setUserId(a.getAdminId());
            return "/secret/index.xhtml?faces-redirect=true";
        } catch (AdminNotFoundException | InvalidLoginException ex) {
            setAdminUsername(null);
            setAdminPassword(null);
            setAdminId(-1);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "" ));
            return "/login.xhtml";    
        }
    }

    public String adminLogout() {
        setAdminUsername(null);
        setAdminPassword(null);
        setAdminId(-1);
        return "/login.xhtml?faces-redirect=true";
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
   
}
