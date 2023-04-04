/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.session.stateless.ForumSessionBeanLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author tohdekai
 */
@Named(value = "forumManagedBean")
@Dependent
public class ForumManagedBean {

    @EJB
    private ForumSessionBeanLocal forumSessionBean;

    /**
     * Creates a new instance of ForumManagedBean
     */
    public ForumManagedBean() {
    }
    
}
