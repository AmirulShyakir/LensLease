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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.UserNotFoundException;

/**
 *
 * @author User
 */
@Named(value = "userListManagedBean")
@ViewScoped
public class userListManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;
    
    private User user;
    private String username;
    private String email;
    private String contactNumber;
    private String name;
    private String password;
    private String photoUrl;
    private boolean isBanned;
    
    private User selectedUser;
    private Long userId;
    
    private List<User> listOfUsers;
    private List<User> usersProvided;
    
    private String searchString;
    private String searchType = "";
    
    public userListManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        if (getSearchString() == null || getSearchString().equals("")) {
            setListOfUsers(userSessionBeanLocal.getAllUsers());
        } else {
            setListOfUsers((List<User>) userSessionBeanLocal.findUserByUsername(getSearchString()));
            getListOfUsers().addAll(userSessionBeanLocal.findUserByUsername(getSearchString()));
        }
        try{
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                    .getELResolver().getValue(elContext, null, "authenticationManagedBean");
            
            long userId = authenticationManagedBean.getUserId();
            setUser(userSessionBeanLocal.findUserByUserId(userId));
        } catch (Exception ex) {
            Logger.getLogger(CheckoutManagedbean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleSearch() {
        init();
    }
     // Search by UserID
     public void loadSelectedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.setSelectedUser(userSessionBeanLocal.findUserByUserId(getUserId()));
            setUsername(this.getSelectedUser().getUsername());
            setEmail(this.getSelectedUser().getEmail());
            setContactNumber(this.getSelectedUser().getContactNumber());
            setName(this.getSelectedUser().getName());
            setPassword(this.getSelectedUser().getPassword());
            setPhotoUrl(this.getSelectedUser().getPhotoUrl());
          
            System.out.println("Viewing users with selected username: " + getUsername());
        } catch (UserNotFoundException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load user"));
        }
    }
       public void loadUsersProvided(){
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            this.usersProvided = userSessionBeanLocal.getAllUsers();
        }catch(Exception e){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load users"));
        } 
       }



    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
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

    /**
     * @return the isBanned
     */
    public boolean isIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the selectedUser
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser the selectedUser to set
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the listOfUsers
     */
    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    /**
     * @param listOfUsers the listOfUsers to set
     */
    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    /**
     * @return the usersProvided
     */
    public List<User> getUsersProvided() {
        return usersProvided;
    }

    /**
     * @param usersProvided the usersProvided to set
     */
    public void setUsersProvided(List<User> usersProvided) {
        this.usersProvided = usersProvided;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the searchType
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
       
}
 
