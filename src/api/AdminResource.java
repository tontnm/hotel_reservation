package api;

import model.customer.Customer;
import model.room.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource reference = new AdminResource();

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {
    }

    public static AdminResource getInstance() {
        return reference;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
