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
import util.exception.BanRequestAlreadyExistException;
import util.exception.InvalidLoginException;
import util.exception.ReportNotMadeException;
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

    public void unbanUser(Long userId) throws UserNotFoundException;
            
    public void unbanService(Long serviceId) throws ServiceNotFoundException;
            
    public void rejectBanRequest(Long banRequestId) throws UserNotFoundException, ServiceNotFoundException;

    public List<BanRequest> getPastBanRequests();
    public void submitReportService(BanRequest banRequest, long serviceId, long complainantId) throws ReportNotMadeException, BanRequestAlreadyExistException ;

    public void submitNewBookingBanRequestAsUser(BanRequest report, long bookingId) throws ServiceNotFoundException, BanRequestAlreadyExistException;

    public void submitNewBookingBanRequestAsProvider(BanRequest report, long bookingId) throws ServiceNotFoundException, BanRequestAlreadyExistException;
    
}
