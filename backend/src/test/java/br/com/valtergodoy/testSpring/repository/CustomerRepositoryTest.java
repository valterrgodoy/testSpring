package br.com.valtergodoy.testSpring.repository;

import br.com.valtergodoy.testSpring.model.Customer;
import br.com.valtergodoy.testSpring.util.CustomerCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Log4j2
class CustomerRepositoryTest {
   /* @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Save customer")
    void successSave(){
        Customer customerToSave = CustomerCreator.createValidCustomer();
        Customer customerSaved = this.customerRepository.save(customerToSave);

        Assertions.assertThat(customerSaved).isNotNull();

        Assertions.assertThat(customerSaved.getId()).isNotNull();

        Assertions.assertThat(customerSaved.getName()).isEqualTo(customerToSave.getName());
    }

    @Test
    @DisplayName("Update customer")
    void successUpdate(){
        Customer customerToSave = CustomerCreator.createValidCustomer();
        Customer customerSaved = this.customerRepository.save(customerToSave);
        customerSaved.setName("Updated");
        Customer customerUpdated = this.customerRepository.save(customerSaved);

        Assertions.assertThat(customerUpdated).isNotNull();

        Assertions.assertThat(customerUpdated.getId()).isNotNull();

        Assertions.assertThat(customerUpdated.getName()).isEqualTo(customerToSave.getName());
    }

    @Test
    @DisplayName("Delete customer")
    void successDelete(){
        Customer customerToSave = CustomerCreator.createValidCustomer();
        Customer customerSaved = this.customerRepository.save(customerToSave);
        this.customerRepository.delete(customerSaved);
        Optional<Customer> customerOptional = this.customerRepository.findById(customerSaved.getId());
        Assertions.assertThat(customerOptional).isEmpty();
    }

    @Test
    @DisplayName("Find customer by name")
    void successFindByName(){
        Customer customerToSave = CustomerCreator.createValidCustomer();
        Customer customerSaved = this.customerRepository.save(customerToSave);
        String nameToFind = customerSaved.getName();
        List<Customer> list = this.customerRepository.findByName(nameToFind);
        Assertions.assertThat(list)
                .isNotEmpty()
                .contains(customerSaved);
    }

    @Test
    @DisplayName("Find customer by name not exists")
    void successFindByNameNotExists(){
        List<Customer> list = this.customerRepository.findByName("Not Exists");
        Assertions.assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("Insert throws when name is empty")
    void failInsertEmptyName(){
        Customer customer = new Customer();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.customerRepository.save(customer));
    }*/
}