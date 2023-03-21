/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BanRequest;
import entity.Schedule;
import entity.Service;
import java.util.List;
import javax.ejb.Local;
import util.exception.ServiceNotFoundException;

/**
 *
 * @author Amirul
 */
@Local
public interface ServiceSessionBeanLocal {

    public void createNewService(Service service);

    public List<Service> getAllServices();

    public Service findServiceByServiceId(Long serviceId) throws ServiceNotFoundException;

    public void createNewSchedule(Schedule schedule);

    public void createNewBanRequest(BanRequest ban);
    
}
