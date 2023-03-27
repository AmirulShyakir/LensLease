package managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.InvalidLoginException;
import util.exception.UserNotFoundException;

@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private String username = null;
    private String password = null;
    private long userId = -1;

    public AuthenticationManagedBean() {
    }

    public String login() {
        //by right supposed to use a session bean to
        //do validation here
        //...
        //simulate username/password
        
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
   
}
