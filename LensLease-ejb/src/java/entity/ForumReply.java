/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Amirul
 */
@Entity
public class ForumReply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumReplyId;
    private String message;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date replyTime;
    @ManyToOne
    private User replier;
    
    @ManyToOne
    private ForumTopic forumTopic;

    public ForumReply(String message, Date replyTime, User user, ForumTopic forumTopic) {
        this.message = message;
        this.replyTime = replyTime;
        this.replier = user;
        this.forumTopic = forumTopic;
    }

    public ForumReply() {
    }

    public Long getForumReplyId() {
        return forumReplyId;
    }

    public void setForumReplyId(Long forumReplyId) {
        this.forumReplyId = forumReplyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forumReplyId != null ? forumReplyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the forumReplyId fields are not set
        if (!(object instanceof ForumReply)) {
            return false;
        }
        ForumReply other = (ForumReply) object;
        if ((this.forumReplyId == null && other.forumReplyId != null) || (this.forumReplyId != null && !this.forumReplyId.equals(other.forumReplyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumReply[ id=" + forumReplyId + " ]";
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the replyTime
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * @param replyTime the replyTime to set
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * @return the forumTopic
     */
    public ForumTopic getForumTopic() {
        return forumTopic;
    }

    /**
     * @param forumTopic the forumTopic to set
     */
    public void setForumTopic(ForumTopic forumTopic) {
        this.forumTopic = forumTopic;
    }

    /**
     * @return the replier
     */
    public User getReplier() {
        return replier;
    }

    /**
     * @param replier the replier to set
     */
    public void setReplier(User replier) {
        this.replier = replier;
    }
    
}
