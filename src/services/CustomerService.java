package services;

import models.customer.Customer;
import models.reservation.Reservation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private Map<String, Customer> customers = new HashMap<>();

    private static CustomerService reference = new CustomerService();

    public static CustomerService getInstance() {
        return reference;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

}
