/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.ForumSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.ForumReply;
import entity.ForumTopic;
import entity.ForumTopicTagEnum;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import util.exception.ForumTopicNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author tohdekai
 */
@Named(value = "forumManagedBean")
@ViewScoped
public class ForumManagedBean implements Serializable {

    public ForumManagedBean() {
    }
    

    @EJB
    private ForumSessionBeanLocal forumSessionBean;

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private Long forumTopicId;
    private String topicName;
    private String description;
    private User poster;
    private Set<ForumTopicTagEnum> tags;
    private Date dateCreated;
    private List<ForumReply> replies;

    private ForumTopic selectedForumTopic;
    private List<ForumTopic> listOfForumTopics;

    private String searchString;
    private String searchType = "";

    private ForumTopicTagEnum topicFilter = ForumTopicTagEnum.ALL;
    private String selectedTopics;

    private boolean topicTagIsAll = true;
    private boolean topicTagIsPhotography = false;
    private boolean topicTagIsVideography = false;
    private boolean topicTagIsEquipment = false;
    private boolean topicTagIsPhotoediting = false;
    private boolean topicTagIsVideoediting = false;
    private boolean topicTagIsTipsAndAdvice = false;
    
    private List<String> selectedTags = new ArrayList<String>();
    
    private String forumReplyMessage;
    
    @PostConstruct
    public void init() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(elContext, null, "authenticationManagedBean");

        long userId = authenticationManagedBean.getUserId();
        try {
            setPoster(userSessionBean.findUserByUserId(userId));
        } catch (UserNotFoundException ex) {
            Logger.getLogger(ForumManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ((getSearchString() == null || getSearchString().equals("")) && getTopicFilter() == ForumTopicTagEnum.ALL) {
            setListOfForumTopics(forumSessionBean.getAllForumTopics());
        } else if ((getSearchString() == null || getSearchString().equals("")) && getTopicFilter() != ForumTopicTagEnum.ALL) {
            listOfForumTopics = forumSessionBean.searchForumTopicsByTags(topicFilter);
        } else if (getSearchString() != null && getTopicFilter() == ForumTopicTagEnum.ALL) {
            listOfForumTopics = forumSessionBean.searchForumTopicsByName(searchString);
        } else {
            listOfForumTopics = forumSessionBean.searchForumTopicsByNameAndTags(searchString, topicFilter);
        }
    }

    public void handleSearch() {
        init();
    }
    
    public ForumTopicTagEnum[] getAllAvailableTags() {
        System.out.println(ForumTopicTagEnum.values());
        return ForumTopicTagEnum.values();
    }

    public void getAllForumTopics() {
        listOfForumTopics = forumSessionBean.getAllForumTopics();
    }

    public void loadSelectedService() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.selectedForumTopic = forumSessionBean.findForumTopicById(forumTopicId);
            topicName = this.selectedForumTopic.getTopicName();
            description = this.selectedForumTopic.getDescription();
            poster = this.selectedForumTopic.getPoster();
            tags = this.selectedForumTopic.getTags();
            dateCreated = this.selectedForumTopic.getDateCreated();
            replies = this.selectedForumTopic.getReplies();
            System.out.println("Going to Forum topic page with selected topic: " + topicName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
        /**
         * @return the topicTagIsAll
         */
    }

    public void openNew() {
        this.selectedForumTopic = new ForumTopic();
    }
    
    public void saveReply() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        AuthenticationManagedBean authenticationManagedBean = (AuthenticationManagedBean) FacesContext.getCurrentInstance().getApplication()
                .getELResolver().getValue(elContext, null, "authenticationManagedBean");
        long userId = authenticationManagedBean.getUserId();
        ForumReply forumReply = new ForumReply();
        forumReply.setForumTopic(selectedForumTopic);
        try {
            forumReply.setReplier(userSessionBean.findUserByUserId(userId));
        } catch (UserNotFoundException ex) {
            Logger.getLogger(ForumManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        forumReply.setReplyTime(new Date());     
        forumReply.setMessage(this.getForumReplyMessage());
        forumSessionBean.createNewForumReply(forumReply);
        forumSessionBean.linkForumWithReply(this.selectedForumTopic.getForumTopicId(), forumReply.getForumReplyId());
        
        for (ForumReply f:this.replies) {
            System.out.println(f.getMessage());
        }
        System.out.println("***");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reply Added"));
        PrimeFaces.current().executeScript("PF('manageReplyDialog').hide()");
    }

    public void saveTopic() {
        System.out.println(selectedForumTopic.getTopicName());
        selectedForumTopic.setPoster(poster);
        selectedForumTopic.setDateCreated(new Date());
        selectedForumTopic.setTagsFromStringToEnums();
        forumSessionBean.createNewForumTopic(selectedForumTopic);
        this.listOfForumTopics.add(selectedForumTopic);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("New Forum Topic Added"));
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
    }

    public boolean isTopicTagIsAll() {
        return topicTagIsAll;
    }

    /**
     * @param topicTagIsAll the topicTagIsAll to set
     */
    public void setTopicTagIsAll(boolean topicTagIsAll) {
        this.topicTagIsAll = topicTagIsAll;
    }

    /**
     * @return the topicTagIsPhotography
     */
    public boolean isTopicTagIsPhotography() {
        return topicTagIsPhotography;
    }

    /**
     * @param topicTagIsPhotography the topicTagIsPhotography to set
     */
    public void setTopicTagIsPhotography(boolean topicTagIsPhotography) {
        this.topicTagIsPhotography = topicTagIsPhotography;
    }

    /**
     * @return the topicTagIsVideography
     */
    public boolean isTopicTagIsVideography() {
        return topicTagIsVideography;
    }

    /**
     * @param topicTagIsVideography the topicTagIsVideography to set
     */
    public void setTopicTagIsVideography(boolean topicTagIsVideography) {
        this.topicTagIsVideography = topicTagIsVideography;
    }

    /**
     * @return the topicTagIsEquipment
     */
    public boolean isTopicTagIsEquipment() {
        return topicTagIsEquipment;
    }

    /**
     * @param topicTagIsEquipment the topicTagIsEquipment to set
     */
    public void setTopicTagIsEquipment(boolean topicTagIsEquipment) {
        this.topicTagIsEquipment = topicTagIsEquipment;
    }

    /**
     * @return the topicTagIsPhotoediting
     */
    public boolean isTopicTagIsPhotoediting() {
        return topicTagIsPhotoediting;
    }

    /**
     * @param topicTagIsPhotoediting the topicTagIsPhotoediting to set
     */
    public void setTopicTagIsPhotoediting(boolean topicTagIsPhotoediting) {
        this.topicTagIsPhotoediting = topicTagIsPhotoediting;
    }

    /**
     * @return the topicTagIsVideoediting
     */
    public boolean isTopicTagIsVideoediting() {
        return topicTagIsVideoediting;
    }

    /**
     * @param topicTagIsVideoediting the topicTagIsVideoediting to set
     */
    public void setTopicTagIsVideoediting(boolean topicTagIsVideoediting) {
        this.topicTagIsVideoediting = topicTagIsVideoediting;
    }

    /**
     * @return the topicTagIsTipsAndAdvice
     */
    public boolean isTopicTagIsTipsAndAdvice() {
        return topicTagIsTipsAndAdvice;
    }

    /**
     * @param topicTagIsTipsAndAdvice the topicTagIsTipsAndAdvice to set
     */
    public void setTopicTagIsTipsAndAdvice(boolean topicTagIsTipsAndAdvice) {
        this.topicTagIsTipsAndAdvice = topicTagIsTipsAndAdvice;
    }

    /**
     * @return the forumSessionBean
     */
    public ForumSessionBeanLocal getForumSessionBean() {
        return forumSessionBean;
    }

    /**
     * @param forumSessionBean the forumSessionBean to set
     */
    public void setForumSessionBean(ForumSessionBeanLocal forumSessionBean) {
        this.forumSessionBean = forumSessionBean;
    }

    /**
     * @return the forumTopicId
     */
    public Long getForumTopicId() {
        return forumTopicId;
    }

    /**
     * @param forumTopicId the forumTopicId to set
     */
    public void setForumTopicId(Long forumTopicId) {
        this.forumTopicId = forumTopicId;
    }

    /**
     * @return the topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName the topicName to set
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
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
     * @return the poster
     */
    public User getPoster() {
        return poster;
    }

    /**
     * @param poster the poster to set
     */
    public void setPoster(User poster) {
        this.poster = poster;
    }

    /**
     * @return the tags
     */
    public Set<ForumTopicTagEnum> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(Set<ForumTopicTagEnum> tags) {
        this.tags = tags;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the selectedForumTopic
     */
    public ForumTopic getSelectedForumTopic() {
        return selectedForumTopic;
    }

    /**
     * @param selectedForumTopic the selectedForumTopic to set
     */
    public void setSelectedForumTopic(ForumTopic selectedForumTopic) {
        this.selectedForumTopic = selectedForumTopic;
    }

    /**
     * @return the listOfForumTopics
     */
    public List<ForumTopic> getListOfForumTopics() {
        return listOfForumTopics;
    }

    /**
     * @param listOfForumTopics the listOfForumTopics to set
     */
    public void setListOfForumTopics(List<ForumTopic> listOfForumTopics) {
        this.listOfForumTopics = listOfForumTopics;
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

    /**
     * @return the replies
     */
    public List<ForumReply> getReplies() {
        return replies;
    }

    /**
     * @param replies the replies to set
     */
    public void setReplies(List<ForumReply> replies) {
        this.replies = replies;
    }

    /**
     * @return the topicFilter
     */
    public ForumTopicTagEnum getTopicFilter() {
        return topicFilter;
    }

    private void resetFilters() {
        setTopicTagIsAll(false);
        setTopicTagIsPhotography(false);
        setTopicTagIsVideography(false);
        setTopicTagIsEquipment(false);
        setTopicTagIsPhotoediting(false);
        setTopicTagIsVideoediting(false);
        setTopicTagIsTipsAndAdvice(false);
    }

    /**
     * @param topicFilter the topicFilter to set
     */
    public void setTopicFilter(String topicFilter) {
        System.out.println(topicFilter);
        resetFilters();
        if (topicFilter.equals("PHOTOGRAPHY")) {
            this.topicFilter = ForumTopicTagEnum.PHOTOGRAPHY;
            setTopicTagIsPhotography(true);
        } else if (topicFilter.equals("VIDEOGRAPHY")) {
            this.topicFilter = ForumTopicTagEnum.VIDEOGRAPHY;
            setTopicTagIsVideography(true);
        } else if (topicFilter.equals("EQUIPMENT")) {
            this.topicFilter = ForumTopicTagEnum.EQUIPMENT;
            setTopicTagIsEquipment(true);
        } else if (topicFilter.equals("PHOTOEDITING")) {
            this.topicFilter = ForumTopicTagEnum.PHOTOEDITING;
            setTopicTagIsPhotoediting(true);
        } else if (topicFilter.equals("VIDEOEDITING")) {
            this.topicFilter = ForumTopicTagEnum.VIDEOEDITING;
            setTopicTagIsVideoediting(true);
        } else if (topicFilter.equals("TIPSANDADVICE")) {
            this.topicFilter = ForumTopicTagEnum.TIPSANDADVICE;
            setTopicTagIsTipsAndAdvice(true);
        } else {
            this.topicFilter = ForumTopicTagEnum.ALL;
            this.setTopicTagIsAll(true);
        }
        
        System.out.println(this.topicFilter);
        this.listOfForumTopics = forumSessionBean.searchForumTopicsByTags(this.topicFilter);
    }

    /**
     * @return the selectedTopics
     */
    public String getSelectedTopics() {
        return selectedTopics;
    }

    /**
     * @param selectedTopics the selectedTopics to set
     */
    public void setSelectedTopics(String selectedTopics) {
        this.selectedTopics = selectedTopics;
    }
    
    public void resetSelectedTags() {
        this.setSelectedTags(new ArrayList<String>());
    }

    /**
     * @return the selectedTags
     */
    public List<String> getSelectedTags() {
        return selectedTags;
    }

    /**
     * @param selectedTags the selectedTags to set
     */
    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    /**
     * @return the forumReplyMessage
     */
    public String getForumReplyMessage() {
        return forumReplyMessage;
    }

    /**
     * @param forumReplyMessage the forumReplyMessage to set
     */
    public void setForumReplyMessage(String forumReplyMessage) {
        this.forumReplyMessage = forumReplyMessage;
    }


}
