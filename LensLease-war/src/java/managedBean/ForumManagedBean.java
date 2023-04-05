/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.ForumSessionBeanLocal;
import entity.ForumTopic;
import entity.ForumTopicTagEnum;
import entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tohdekai
 */
@Named(value = "forumManagedBean")
@ViewScoped
public class ForumManagedBean implements Serializable{
    
    public ForumManagedBean() {
    }

    @EJB
    private ForumSessionBeanLocal forumSessionBean;
    
    private Long forumTopicId;
    private String topicName;
    private String description;
    private User poster;
    private List<ForumTopicTagEnum> tags;
    private Date dateCreated;
    
    private ForumTopic selectedForumTopic;
    private List<ForumTopic> listOfForumTopics;

    private String searchString;
    private String searchType = "";

    @PostConstruct
    public void init() {

        if (getSearchString() == null || getSearchString().equals("")) {
            setListOfForumTopics(forumSessionBean.getAllForumTopics());
        } else {
            listOfForumTopics = forumSessionBean.searchForumTopics(searchString);
        }
    }

    public void handleSearch() {
        init();
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
            System.out.println("Going to Forum topic page with selected topic: " + topicName);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load Service"));
        }
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
    public List<ForumTopicTagEnum> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<ForumTopicTagEnum> tags) {
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
    
}
