/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.BanRequest;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdminNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface AdminSessionBeanLocal {

    public void createNewAdmin(Admin admin);

    public List<Admin> getAllAdmins();

    public Admin findAdminByAdminId(Long adminId) throws AdminNotFoundException;

    public Admin findAdminByUsername(String username) throws AdminNotFoundException;

    public Admin adminLogin(String username, String password) throws AdminNotFoundException, InvalidLoginException;

    public List<BanRequest> getAllBanRequests();

    public void createNewBanRequest(BanRequest banRequest);

    public BanRequest findBanRequestById(Long banRequestId);

    public void acceptBanRequest(Long banRequestId) throws UserNotFoundException, ServiceNotFoundException;

    

    public void rejectBanRequest(Long banRequestId) throws UserNotFoundException, ServiceNotFoundException;

    public List<BanRequest> getPastBanRequests();
    
}
