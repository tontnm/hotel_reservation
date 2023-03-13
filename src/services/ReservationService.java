package services;

import models.customer.Customer;
import models.room.IRoom;
import models.reservation.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private Map<String, Collection<Reservation>> reservations = new HashMap<>();
    private Map<String, IRoom> rooms = new HashMap<>();
    private static ReservationService reference = new ReservationService();

    public static ReservationService getInstance() {
        return reference;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<Reservation> reservationList = reservations.get(customer.getEmail());

        if (reservationList == null) {
            reservationList = new LinkedList<>();
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);

        reservations.put(customer.getEmail(), reservationList);

        return reservation;
    }

    public Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();

        for (Collection<Reservation> reservation : reservations.values()) {
            allReservations.addAll(reservation);
        }

        return allReservations;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> availableRooms = new LinkedList<>(rooms.values());

        for (Reservation reservation : allReservations) {
            if (reservationOverlaps(reservation, checkInDate, checkOutDate)) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

//    public Collection<IRoom> findRooms(Date inputCheckInDate, Date inputCheckOutDate) {
//        Collection<Reservation> allReservations = getAllReservations();
//
//        Collection<IRoom> availableRooms = new LinkedList<>();
//
//        for (Reservation reservation : allReservations) {
//            if (reservationOverlaps(reservation , inputCheckInDate, inputCheckOutDate)) {
//                availableRooms.add(reservation.getRoom());
//            }
//        }
//
//        return availableRooms;
//    }

    private boolean reservationOverlaps(Reservation reservation, Date checkInDate, Date checkOutDate) {
        Date reservationCheckInDate = reservation.getCheckInDate();
        Date reservationCheckOutDate = reservation.getCheckOutDate();

        if (checkOutDate.compareTo(reservationCheckInDate) <= 0) {
            return false;
        }

        if (checkInDate.compareTo(reservationCheckOutDate) >= 0) {
            return false;
        }

        return true;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservation yet.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}
