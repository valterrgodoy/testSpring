package br.com.valtergodoy.testSpring.integration;

import br.com.valtergodoy.testSpring.controller.dto.CustomerRequestBody;
import br.com.valtergodoy.testSpring.model.Customer;
import br.com.valtergodoy.testSpring.repository.CustomerRepository;
import br.com.valtergodoy.testSpring.util.CustomerCreator;
import br.com.valtergodoy.testSpring.util.CustomerRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerControleIntegration {
    @Autowired
    @Qualifier(value = "testRestTemplateWithSimpleUser")
    private TestRestTemplate testRestTemplateWithSimpleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateWithAdminUser")
    private TestRestTemplate testRestTemplateWithAdminUser;
    @Autowired
    private CustomerRepository customerRepository;

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateWithSimpleUser")
        public TestRestTemplate testRestTemplateWithSimpleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("valter", "!@#1234Abc");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateWithAdminUser")
        public TestRestTemplate testRestTemplateWithAdminUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("administrator", "bcA4321#@!");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("List all customers")
    void successListAll() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());

        List<Customer> list = testRestTemplateWithSimpleUser.exchange("/customer/", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Customer>>() {
                }).getBody();
        Assertions.assertThat(list)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Find customer by ID")
    void successFindById() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());
        Customer customerReached = testRestTemplateWithSimpleUser.exchange("/customer/" + customerSaved.getId().toString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<Customer>() {
                }).getBody();
        Assertions.assertThat(customerReached).isNotNull()
                .isEqualTo(customerSaved);
    }

    @Test
    @DisplayName("Find customer by Name")
    void successFindByName() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());
        String url = String.format("/customer/find?name=%s", customerSaved.getName());
        List<Customer> list = testRestTemplateWithSimpleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Customer>>() {
                }).getBody();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.get(0).getName()).isEqualTo(customerSaved.getName());
    }

    @Test
    @DisplayName("Throws when customer not found by Name")
    void failFindByNameNotFound() {
        String url = String.format("/customer/find?name=%s", "Not Found");
        List<Customer> list = testRestTemplateWithSimpleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Customer>>() {
                }).getBody();
        Assertions.assertThat(list).isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Insert customer without permission")
    void failInsert() {
        CustomerRequestBodyCreator customerRequestBodyCreator = new CustomerRequestBodyCreator();
        CustomerRequestBody customerRequestBody = customerRequestBodyCreator.createValidCustomerRequestBody();
        ResponseEntity<Customer> customerResponseEntity = testRestTemplateWithSimpleUser.postForEntity("/customer", customerRequestBody, Customer.class);
        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Insert customer")
    void successInsert() {
        CustomerRequestBodyCreator customerRequestBodyCreator = new CustomerRequestBodyCreator();
        CustomerRequestBody customerRequestBody = customerRequestBodyCreator.createValidCustomerRequestBody();
        ResponseEntity<Customer> customerResponseEntity = testRestTemplateWithAdminUser.postForEntity("/customer", customerRequestBody, Customer.class);
        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(customerResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(customerResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Update customer without permission")
    void failUpdate() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());
        customerSaved.setName("UPDATED");
        ResponseEntity<Customer> customerResponseEntity = testRestTemplateWithSimpleUser.exchange("/customer", HttpMethod.PUT, new HttpEntity<>(customerSaved),
                Customer.class);
        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Update customer")
    void successUpdate() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());
        customerSaved.setName("UPDATED");
        ResponseEntity<Customer> customerResponseEntity = testRestTemplateWithAdminUser.exchange("/customer", HttpMethod.PUT, new HttpEntity<>(customerSaved),
                Customer.class);
        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(customerResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(customerResponseEntity.getBody().getName()).isEqualTo(customerSaved.getName());
    }

    @Test
    @DisplayName("Delete customer without permission")
    void failDelete() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());
        ResponseEntity<Void> customerResponseEntity = testRestTemplateWithSimpleUser.exchange("/customer/{id}", HttpMethod.DELETE, null,
                Void.class, customerSaved.getId());

        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Delete customer")
    void successDelete() {
        Customer customerSaved = customerRepository.save(CustomerCreator.createValidCustomer());
        ResponseEntity<Void> customerResponseEntity = testRestTemplateWithAdminUser.exchange("/customer/{id}", HttpMethod.DELETE, null,
                Void.class, customerSaved.getId());

        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
