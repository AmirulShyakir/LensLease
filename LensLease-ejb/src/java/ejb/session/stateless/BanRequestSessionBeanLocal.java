/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import java.util.List;
import javax.ejb.Local;
import util.exception.BanRequestAlreadyExistException;
import util.exception.ServiceNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author junwe
 */
@Local
public interface BanRequestSessionBeanLocal {

    public void createNewBanRequest(BanRequest report);

    public void submitNewBanRequest(BanRequest report, long serviceId) throws ServiceNotFoundException, BanRequestAlreadyExistException;

    public BanRequest findBanRequestByBanRequestId(Long banRequestId) throws BanRequestAlreadyExistException;

    public List<BanRequest> getBanRequestsByServiceId(long serviceId) throws UserNotFoundException, ServiceNotFoundException;
    
}
