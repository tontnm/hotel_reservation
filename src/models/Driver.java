package models;

import models.customer.Customer;

public class Driver {
    public static void main(String[] args) {
        Customer customer = new Customer("Tony", "Jackson", "tonyj");
        System.out.println(customer);
    }
}
