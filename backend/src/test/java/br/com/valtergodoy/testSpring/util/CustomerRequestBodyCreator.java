package br.com.valtergodoy.testSpring.util;

import br.com.valtergodoy.testSpring.controller.dto.CustomerRequestBody;
import br.com.valtergodoy.testSpring.repository.CustomerRepository;
import br.com.valtergodoy.testSpring.service.CustomerService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRequestBodyCreator
{
    @Autowired
    private CustomerRepository customerRepository;
    private CustomerService customerService = new CustomerService(customerRepository);

    public CustomerRequestBody createValidCustomerRequestBody() {
         CustomerRequestBody customerRequestBody = customerService.CustomerToCustomerResquestBody(
                new CustomerCreator().createValidCustomer());
        return customerRequestBody;
    }

    public CustomerRequestBody createValidUpdateCustomerRequestBody() {
        CustomerRequestBody customerRequestBody = customerService.CustomerToCustomerResquestBody(
                new CustomerCreator().createValidUpdateCustomer());
        return customerRequestBody;
    }

}
