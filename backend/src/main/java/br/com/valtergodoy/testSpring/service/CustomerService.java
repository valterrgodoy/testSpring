package br.com.valtergodoy.testSpring.service;

import br.com.valtergodoy.testSpring.controller.dto.CustomerRequestBody;
import br.com.valtergodoy.testSpring.exception.BadRequestException;
import br.com.valtergodoy.testSpring.model.Customer;
import br.com.valtergodoy.testSpring.repository.CustomerRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer findById(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Customer ID: " + id + " not found."));
    }

    @Transactional(rollbackFor = Exception.class)
    public Customer insert(CustomerRequestBody customerRequestBody) {
        return customerRepository.save(this.CustomerResquestBodyToCustomer(customerRequestBody));
    }

    public Customer CustomerResquestBodyToCustomer(CustomerRequestBody customerRequestBody) {
        Customer customer = new Customer();
        try {
            customer.setId(customerRequestBody.getId());
            customer.setName(customerRequestBody.getName());
            customer.setIdentity(customerRequestBody.getIdentity());
            customer.setCity(customerRequestBody.getCity());
            customer.setComplement(customerRequestBody.getComplement());
            customer.setCountry(customerRequestBody.getCountry());
            customer.setNeighbourhood(customerRequestBody.getNeighbourhood());
            customer.setNumber(customerRequestBody.getNumber());
            customer.setState(customerRequestBody.getState());
            customer.setStreet(customerRequestBody.getStreet());
            customer.setZipcode(customerRequestBody.getZipcode());

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return customer;
    }

    public CustomerRequestBody CustomerToCustomerResquestBody(Customer customer) {
        CustomerRequestBody customerRequestBody = new CustomerRequestBody();
        try {
            customerRequestBody.setId(customer.getId());
            customerRequestBody.setName(customer.getName());
            customerRequestBody.setIdentity(customer.getIdentity());
            customerRequestBody.setCity(customer.getCity());
            customerRequestBody.setComplement(customer.getComplement());
            customerRequestBody.setCountry(customer.getCountry());
            customerRequestBody.setNeighbourhood(customer.getNeighbourhood());
            customerRequestBody.setNumber(customer.getNumber());
            customerRequestBody.setState(customer.getState());
            customerRequestBody.setStreet(customer.getStreet());
            customerRequestBody.setZipcode(customer.getZipcode());

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return customerRequestBody;
    }

    public Customer update(CustomerRequestBody customerRequestBody) {
        Customer customerSaved = this.findById(customerRequestBody.getId());
        Customer customer = this.CustomerResquestBodyToCustomer(customerRequestBody);
        customer.setId(customerSaved.getId());
        return customerRepository.save(customer);
    }

    public void delete(long id) {
        Customer customerSaved = this.findById(id);
        customerRepository.delete(customerSaved);
    }

}
