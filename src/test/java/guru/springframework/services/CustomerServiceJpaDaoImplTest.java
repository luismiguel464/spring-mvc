package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testListMethod() {

        List<Customer> customers = (List<Customer>) customerService.listAll();

        assert customers.size() >= 1;
    }

    @Test
    public void testGetById() {

        Integer id = 6; //Note: hibernate is creating the IDs of the products, but when creating the IDs of
                        // customers, it is not starting from 1, but from 6 "continuing the sequence"

        Customer customer = customerService.getById(id);

        assert customer.getId() == id;

    }

}
