/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumReply;
import entity.ForumTopic;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ForumTopicNotFoundException;

/**
 *
 * @author Amirul
 */
@Stateless
public class ForumSessionBean implements ForumSessionBeanLocal {

    @PersistenceContext(unitName = "LensLease-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createNewForumTopic(ForumTopic forumTopic) {
        em.persist(forumTopic);
        em.flush();
    }
    
    @Override
    public ForumTopic findForumTopicById(Long forumTopicId) throws ForumTopicNotFoundException {
        Query query = em.createQuery("SELECT f FROM ForumTopic f WHERE f.forumTopicId = :inForumTopicId");
        query.setParameter("inForumTopicId", forumTopicId);
        query.setMaxResults(1);
        try {
            ForumTopic booking = (ForumTopic)query.getSingleResult();
            return booking;
        } catch (Exception e) {
            throw new ForumTopicNotFoundException("Booking not found with id " + forumTopicId);
        }
    }

    @Override
    public void createNewForumReply(ForumReply forumReply) {
        em.persist(forumReply);
        em.flush();
    }
    
    
    @Override
    public List<ForumTopic> getAllForumTopics() {
        Query query = em.createQuery("SELECT f FROM ForumTopic f");
        return query.getResultList();
    }
    
    public List<ForumTopic> searchForumTopics(String name) {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT f FROM ForumTopic f WHERE "
                    + "LOWER(f.topicName) LIKE :name");
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT f FROM ForumTopic f");
        }

        return q.getResultList();
    }
}
