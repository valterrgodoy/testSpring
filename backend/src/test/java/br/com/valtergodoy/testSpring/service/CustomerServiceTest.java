package br.com.valtergodoy.testSpring.service;

import br.com.valtergodoy.testSpring.exception.BadRequestException;
import br.com.valtergodoy.testSpring.model.Customer;
import br.com.valtergodoy.testSpring.repository.CustomerRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepositoryMock;

    @BeforeEach()
    void setUp() {
        List<Customer> customerList = new ArrayList<Customer>(List.of(CustomerCreator.createValidCustomer()));
        CustomerCreator customerCreator = new CustomerCreator();
        BDDMockito.when(customerRepositoryMock.findAll())
                .thenReturn(customerList);

        Customer customerReachedById = customerCreator.createCustomerReached();
        BDDMockito.when(customerRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(customerReachedById));

        List<Customer> customersReachedByName =  customerCreator.createCustomersReachedByName();
        BDDMockito.when(customerRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(customersReachedByName);

        BDDMockito.when(customerRepositoryMock.save(ArgumentMatchers.any(Customer.class)))
                .thenReturn(customerCreator.createValidCustomer());

        BDDMockito.doNothing().when(customerRepositoryMock).delete(ArgumentMatchers.any(Customer.class));
    }

    @Test
    @DisplayName("FindAll customers")
    void successListAll() throws Exception {
        String customerExpectedName = CustomerCreator.createValidCustomer().getName();
        List<Customer> list = customerService.listAll();
        Assertions.assertThat(list).isNotNull()
                .hasSize(1);
        Assertions.assertThat(list.get(0).getName()).isEqualTo(customerExpectedName);
    }

    @Test
    @DisplayName("Find customer by ID")
    void successFindById() {
        Customer customerExpected = CustomerCreator.createCustomerReached();
        Customer customer = customerService.findById(1L);
        Assertions.assertThat(customer).isNotNull()
                .isEqualTo(customerExpected);
    }

    @Test
    @DisplayName("Find customer by Name")
    void successFindByName() {
        List<Customer> customerListExpected = CustomerCreator.createCustomersReachedByName();
        List<Customer> customerList = customerService.findByName("name");
        Assertions.assertThat(customerList).isNotNull()
                .hasSize(1);
        Assertions.assertThat(customerList.get(0).getName()).isEqualTo(customerListExpected.get(0).getName());
    }

    @Test
    @DisplayName("Insert customer")
    void successInsert() {
        CustomerRequestBodyCreator customerRequestBodyCreator = new CustomerRequestBodyCreator();
        Customer customerExpected = CustomerCreator.createValidCustomer();
        Customer customer = customerService.insert(customerRequestBodyCreator.createValidCustomerRequestBody());
        Assertions.assertThat(customer).isNotNull()
                .isEqualTo(customerExpected);
    }

    @Test
    @DisplayName("Update customer")
    void successUpdate() {
        CustomerRequestBodyCreator customerRequestBodyCreator = new CustomerRequestBodyCreator();
        Customer customerExpected = CustomerCreator.createValidCustomer();
        Customer customer = customerService.update(customerRequestBodyCreator.createValidCustomerRequestBody());
        Assertions.assertThat(customer).isNotNull();
        Assertions.assertThat(customer.getName()).isEqualTo(customerExpected.getName());
    }

    @Test
    @DisplayName("Delete customer")
    void successDelete() {
        Assertions.assertThatCode(() -> customerService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Exception when customer not found")
    void failFindByIdNotFound() {
        BDDMockito.when(customerRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> customerService.findById(1L));
    }
}