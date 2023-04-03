/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Amirul
 */
@Entity
public class ForumTopic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumTopicId;
    private String topicName;
    private String description;
    @ManyToOne
    private User poster;
    private List<ForumTopicTagEnum> tags;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreated;
    
    
    @OneToMany(mappedBy = "forumTopic")
    private List<ForumReply> replies;

    public ForumTopic() {
    }

    public ForumTopic(String topicName, String description, User poster, List<ForumTopicTagEnum> tags, Date dateCreated) {
        this.topicName = topicName;
        this.description = description;
        this.poster = poster;
        this.tags = tags;
        this.dateCreated = dateCreated;
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

    public Long getForumTopicId() {
        return forumTopicId;
    }

    public void setForumTopicId(Long forumTopicId) {
        this.forumTopicId = forumTopicId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forumTopicId != null ? forumTopicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the forumTopicId fields are not set
        if (!(object instanceof ForumTopic)) {
            return false;
        }
        ForumTopic other = (ForumTopic) object;
        if ((this.forumTopicId == null && other.forumTopicId != null) || (this.forumTopicId != null && !this.forumTopicId.equals(other.forumTopicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumTopic[ id=" + forumTopicId + " ]";
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
    
}
