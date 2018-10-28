package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {

    @Mock //Mockito object
    private CustomerService customerService;

    @InjectMocks //Setups controller and injects mock object into it
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); //Initialize controller and mocks

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listOfCustomerTest() throws Exception {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customers);

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void testGetCustomer() throws Exception {

        Integer id = 1;

        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/customer"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));

    }

    @Test
    public void testNewCustomer() throws Exception {

        //Should not call service
        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testEdit() throws Exception {

        Integer id = 1;

        //Tell stub to return a list of products
        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        Integer id = 1;
        String address = "Test address";
        String city = "Test city";
        String email = "Test email";

        Customer returnCustomer = new Customer();
        returnCustomer.setId(id);
        returnCustomer.setAddressLineOne(address);
        returnCustomer.setCity(city);
        returnCustomer.setEmail(email);
        returnCustomer.setFirstName("First Name");
        returnCustomer.setLastName("Last Name");
        returnCustomer.setPhoneNumber("Test Phone Number");

        //Tell stub to return a list of products
        when(customerService.saveOrUpdate(any(Customer.class))).thenReturn(returnCustomer);

        mockMvc.perform(post("/customer")
                .param("id", "1")
                .param("addressLineOne", address)
                .param("city", city)
                .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/1"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("addressLineOne", is(address))))
                .andExpect(model().attribute("customer", hasProperty("city", is(city))))
                .andExpect(model().attribute("customer", hasProperty("email", is(email))));

        //Verify properties of bound object
        ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(boundCustomer.capture());

        assertEquals(id, boundCustomer.getValue().getId());
        assertEquals(address, boundCustomer.getValue().getAddressLineOne());
        assertEquals(email, boundCustomer.getValue().getEmail());
        assertEquals(city, boundCustomer.getValue().getCity());

    }

    @Test
    public void testDelete() throws Exception {

        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        verify(customerService, times(1)).delete(id);
    }

}
