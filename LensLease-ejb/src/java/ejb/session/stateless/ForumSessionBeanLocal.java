/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumReply;
import entity.ForumTopic;
import entity.ForumTopicTagEnum;
import java.util.List;
import javax.ejb.Local;
import util.exception.ForumTopicNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface ForumSessionBeanLocal {

    public void createNewForumTopic(ForumTopic forumTopic);

    public ForumTopic findForumTopicById(Long forumTopicId) throws ForumTopicNotFoundException;

    public void createNewForumReply(ForumReply forumReply);

    public List<ForumTopic> getAllForumTopics();

    public List<ForumTopic> searchForumTopicsByTags(ForumTopicTagEnum selectedTags);

    public List<ForumTopic> searchForumTopicsByName(String name);

    public List<ForumTopic> searchForumTopicsByNameAndTags(String name, ForumTopicTagEnum selectedTag);
    
}
