package br.com.valtergodoy.testSpring.util;

import br.com.valtergodoy.testSpring.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerCreator {

    public static Customer createValidCustomer() {
        return Customer.builder()
                .id(1L)
                .name("Name Saved")
                .identity("123456789")
                .street("Street")
                .number("123")
                .complement("Complement")
                .neighbourhood("NeighBourHood")
                .city("City")
                .state("State")
                .country("Country")
                .zipcode("13458025")
                .build();
    }

    public static Customer createValidUpdateCustomer() {
        return Customer.builder()
                .id(1L)
                .name("Name Updated")
                .identity("123456789")
                .street("Street")
                .number("123")
                .complement("Complement")
                .neighbourhood("NeighBourHood")
                .city("City")
                .state("State")
                .country("Country")
                .zipcode("13458025")
                .build();
    }

    public static Customer createCustomerReached() {
        return Customer.builder()
                .id(1L)
                .name("Name Reached")
                .identity("123456789")
                .street("Street")
                .number("123")
                .complement("Complement")
                .neighbourhood("NeighBourHood")
                .city("City")
                .state("State")
                .country("Country")
                .zipcode("13458025")
                .build();
    }

    public static List<Customer> createCustomersReachedByName() {
        List<Customer> list = new ArrayList<Customer>();
        list.add(createCustomerReached());
        return list;
    }

}
