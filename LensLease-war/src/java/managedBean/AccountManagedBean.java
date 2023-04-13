/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.InvalidLoginException;

/**
 *
 * @author junwe
 */
@Named(value = "accountManagedBean")
@ViewScoped
public class AccountManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private User user;
    private String currentPassword;
    private String newPassword;
    private String photoUrl;

    /**
     * Creates a new instance of AccountManagedBean
     */
    public AccountManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "authenticationManagedBean");
            
            long userId = authenticationManagedBean.getUserId();
            setUser(userSessionBean.findUserByUserId(userId));
        } catch (Exception ex) {
            Logger.getLogger(PortfolioManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveImage(){
        this.user.setPhotoUrl(photoUrl);
    }
    
    public void changePassword(){
        try{
            userSessionBean.changePassword(user.getUserId(), currentPassword, newPassword);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Password Changed", "Password has been changed successfully!"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong current password", ex.getMessage()));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    public void saveDetails(){
        userSessionBean.updateDetails(user.getUserId(), user.getName() ,user.getUsername(), user.getEmail(), user.getContactNumber(), user.getPhotoUrl());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Details Changed", "Your profile details have been updated successfully!"));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
}
