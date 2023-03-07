package services;

import models.customer.Customer;
import models.room.IRoom;
import models.reservation.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private static ReservationService reference = new ReservationService();

    private Map<String, IRoom> rooms = new HashMap<>();

    private Map<String, Collection<Reservation>> reservations = new HashMap<>();

    public static ReservationService getInstance() {
        return reference;
    }

    public ReservationService() {
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reverseARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> customerReservationList = getCustomerReservation(customer);

        if (customerReservationList == null) {
            customerReservationList = new LinkedList<>();
        }

        customerReservationList.add(reservation);

        reservations.put(customer.getEmail(), customerReservationList);

        return reservation;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom> findAlternativeRoom(Date checkInDate, Date checkOutDate) {
        return findAvailableRooms(addDefaultPlusDays(checkInDate), addDefaultPlusDays(checkOutDate));
    }

    public Date addDefaultPlusDays(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);

        return calendar.getTime();
    }

    private Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> reservationList = getReservationList();

        Collection<IRoom> notAvailableRoomList = new LinkedList<>();

        for (Reservation reservation : reservationList) {
            if (reservationOverlap(reservation, checkInDate, checkOutDate)) {
                notAvailableRoomList.add(reservation.getRoom());
            }
        }

        return rooms.values().stream()
                .filter(room -> notAvailableRoomList.stream()
                        .noneMatch(notAvailableRoom -> notAvailableRoom.equals(room)))
                .collect(Collectors.toList());
    }

    private boolean reservationOverlap(Reservation reservation, Date checkInDate, Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate());
    }

    private Collection<Reservation> getReservationList() {
        Collection<Reservation> reservationList = new LinkedList<>();

        for (Collection<Reservation> reservations : reservations.values()) {
            reservationList.addAll(reservations);
        }

        return reservationList;
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getReservationList();

        if (reservations.isEmpty()) {
            System.out.println("No reservation found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }
}
