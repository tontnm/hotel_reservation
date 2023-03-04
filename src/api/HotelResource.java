package api;

import model.customer.Customer;
import model.room.IRoom;
import model.reservation.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HotelResource {

    private static HotelResource reference = new HotelResource();
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {
    }

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

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reverseARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);

        if (customer == null) {
            return Collections.emptyList();
        }

        return reservationService.getCustomerReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findAlternativeRooms(Date checkIn, Date checkOut) {
        return reservationService.findAlternativeRoom(checkIn, checkOut);
    }

    public Date addDefaultPlusDays(Date date) {
        return reservationService.addDefaultPlusDays(date);
    }
}
