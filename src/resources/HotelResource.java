package resources;

import models.customer.Customer;
import models.room.IRoom;
import models.reservation.Reservation;
import services.CustomerService;
import services.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HotelResource {

    private static HotelResource reference = new HotelResource();
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    public static HotelResource getInstance() {
        return reference;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String email, IRoom room, Date inputCheckInDate, Date inputCheckOutDate) {
        return reservationService.reserveARoom(getCustomer(email), room, inputCheckInDate, inputCheckOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String email) {

        Customer customer = getCustomer(email);

        if (customer == null) {
            return Collections.emptyList();
        }

        return reservationService.getCustomerReservation(getCustomer(email));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
