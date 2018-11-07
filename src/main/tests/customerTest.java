import com.rest.resources.CustomerResource;
import model.ConnectionPool;
import model.Customer;
import org.apache.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import org.apache.log4j.Logger;
import service.CustomerService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class customerTest {

    private static final Logger log = LogManager.getLogger(customerTest.class);

    @Test
    public void testConnectionSucceed(){
        try{
            Connection connection = ConnectionPool.getConnection();
            assertNotNull(connection);
        }catch (SQLException e){
            log.error("Error caused by :", e);
        }

    }

    @Test
    public void testGetAllCustomerS(){
        try {
            CustomerService customerService = new CustomerService();
            List<Customer> customers = customerService.getAll();
            assertNotNull(customers);
            assertFalse(customers.isEmpty());
        }catch (SQLException e){
            log.error("Error caused by " , e);
        }
    }

    @Test
    public void testGetById(){
        try {
            CustomerService customerService = new CustomerService();
            Customer customer = customerService.get(1);
            assertNotNull(customer);
        }catch (Exception e){
            log.error("Error caused by " , e);
        }
    }

    @Test
    public void testGetByName(){
        try {
            CustomerService customerService = new CustomerService();
            Customer customer = customerService.getByName("Axl Rose");
            assertNotNull(customer);
        }catch (Exception e){
            log.error("Error caused by " , e);
        }
    }

    @Test
    public void testUpdate(){
        try {
            CustomerService customerService = new CustomerService();
            Customer customer = customerService.get(1);
            assertNotNull(customer);
            customer.setEmail("modified@track.com");
            customer.setStatus(false);

            Customer updatedCustomer = customerService.edit(customer);
            assertNotNull(updatedCustomer);
        }catch (Exception e){
            log.error("Error caused by " , e);
        }
    }

    @Test
    public void testDelete(){
        try {
            CustomerService customerService = new CustomerService();
            assertTrue(customerService.delete(1));
        }catch (Exception e){
            log.error("Error caused by " , e);
        }
    }

    @Test
    public void testAdd(){
        try {
            CustomerService customerService = new CustomerService();
            Customer customer = new Customer();
            customer.setName("Axl Rose");
            customer.setAge(36);
            customer.setStatus(true);
            customer = customerService.add(customer);
            assertNotNull(customer);
        }catch (Exception e){
            log.error("Error caused by " , e);
        }
    }
}
