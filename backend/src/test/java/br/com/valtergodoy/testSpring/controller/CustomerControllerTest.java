package br.com.valtergodoy.testSpring.controller;

import br.com.valtergodoy.testSpring.controller.dto.CustomerRequestBody;
import br.com.valtergodoy.testSpring.model.Customer;
import br.com.valtergodoy.testSpring.service.CustomerService;
import br.com.valtergodoy.testSpring.util.CustomerCreator;
import br.com.valtergodoy.testSpring.util.CustomerRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerServiceMock;

    @BeforeEach()
    void setUp() {
        List<Customer> customerList = new ArrayList<Customer>(List.of(CustomerCreator.createValidCustomer()));
        CustomerCreator customerCreator = new CustomerCreator();
        BDDMockito.when(customerServiceMock.listAll())
                .thenReturn(customerList);

        Customer customerReachedById = customerCreator.createCustomerReached();
        BDDMockito.when(customerServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(customerReachedById);

        List<Customer> customersReachedByName =  customerCreator.createCustomersReachedByName();
        BDDMockito.when(customerServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(customersReachedByName);

        BDDMockito.when(customerServiceMock.insert(ArgumentMatchers.any(CustomerRequestBody.class)))
                .thenReturn(customerCreator.createValidCustomer());

        Customer customerUpdated = customerCreator.createValidUpdateCustomer();

        BDDMockito.when(customerServiceMock.update(ArgumentMatchers.any()))
                .thenReturn(customerUpdated);

        BDDMockito.doNothing().when(customerServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List all customers")
    void successListAll() throws Exception {
        String customerExpectedName = CustomerCreator.createValidCustomer().getName();
        List<Customer> list = customerController.listAll().getBody();
        Assertions.assertThat(list).isNotNull()
                .hasSize(1);
        Assertions.assertThat(list.get(0).getName()).isEqualTo(customerExpectedName);
    }

    @Test
    @DisplayName("Find customer by ID")
    void successFindById() {
        Customer customerExpected = CustomerCreator.createCustomerReached();
        Customer customer = customerController.findById(1L).getBody();
        Assertions.assertThat(customer).isNotNull()
        .isEqualTo(customerExpected);
    }

    @Test
    @DisplayName("Find customer by Name")
    void successFindByName() {
        List<Customer> customerListExpected = CustomerCreator.createCustomersReachedByName();
        List<Customer> customerList = customerController.findByName("name").getBody();
        Assertions.assertThat(customerList).isNotNull()
                .hasSize(1);
        Assertions.assertThat(customerList.get(0).getName()).isEqualTo(customerListExpected.get(0).getName());
    }

    @Test
    @DisplayName("Insert customer")
    void successInsert() {
        CustomerRequestBodyCreator customerRequestBodyCreator = new CustomerRequestBodyCreator();
        Customer customerExpected = CustomerCreator.createValidCustomer();
        Customer customer = customerController.insert(customerRequestBodyCreator.createValidCustomerRequestBody()).getBody();
        Assertions.assertThat(customer).isNotNull()
                .isEqualTo(customerExpected);
    }

    @Test
    @DisplayName("Update customer")
    void successUpdate() {
        CustomerRequestBodyCreator customerRequestBodyCreator = new CustomerRequestBodyCreator();
        Customer customerExpected = CustomerCreator.createValidUpdateCustomer();
        Customer customer = customerController.update(customerRequestBodyCreator.createValidUpdateCustomerRequestBody()).getBody();
        Assertions.assertThat(customer).isNotNull();
        Assertions.assertThat(customer.getName()).isEqualTo(customerExpected.getName());
    }

    @Test
    @DisplayName("Delete customer")
    void successDelete() {
        Assertions.assertThatCode(() -> customerController.delete(1L)).doesNotThrowAnyException();

        ResponseEntity<Void> entity = customerController.delete(1L);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}