/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.UserNotFoundException;

/**
 * REST Web Service
 *
 * @author jonta
 */
@Path("users")
public class UsersResource {
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;
    
    public UsersResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllCustomers() {
        return userSessionBeanLocal.getAllUsers();
    } //end getAllUsers
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long uId) {
        try {
            User u = userSessionBeanLocal.findUserByUserId(uId);
            return Response.status(200).entity(
                    u
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (UserNotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getUser
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createCustomer(User u) {    
        userSessionBeanLocal.createNewUser(u);
        return u;
    } //end createUser

    
}
