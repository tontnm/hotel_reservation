package ui;

import models.customer.Customer;
import resources.HotelResource;
import models.reservation.Reservation;
import models.room.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final String DATE_FORMAT = "MM/dd/YYYY";

    private static HotelResource hotelResource = HotelResource.getInstance();

    public static void mainMenu() {
        String line = "";
        Scanner scanner = new Scanner(System.in);

        printMainMenu();

        try {
            do {
                line = scanner.nextLine();

                switch (line.charAt(0)) {
                    case '1':
                        findAndReserveRoom();
                        break;
                    case '2':
                        seeMyReservations();
                        break;
                    case '3':
                        createAccount();
                        break;
                    case '4':
                        AdminMenu.adminMenu();
                        break;
                    case '5':
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Unknown action");
                        break;
                }
            } while (line.charAt(0) != '5');
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Exit");
        }
    }

    public static void printMainMenu() {
        System.out.print("\nWelcome to the Hotel Reservation Application\n" +
                "--------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an Account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter check in date:");
        Date inputCheckInDate = getInputDate(scanner);

        System.out.println("Please enter check out date:");
        Date inputCheckOutDate = getInputDate(scanner);

        if (inputCheckOutDate != null && inputCheckInDate != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(inputCheckInDate, inputCheckOutDate);



            if (availableRooms.isEmpty()) {
                reserveARoom(scanner, availableRooms, inputCheckInDate, inputCheckOutDate);
            } else {
                reserveARoom(scanner, availableRooms, inputCheckInDate, inputCheckOutDate);
            }
        }
    }

    private static void reserveARoom(Scanner scanner, Collection<IRoom> rooms, Date checkIn, Date checkOut) {
//        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to book room? y/n");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {

            System.out.println("Do you have account with us? y/n");
            String account = scanner.nextLine();

            if (account.equalsIgnoreCase("y")) {

                System.out.println("Please enter your email:");
                String email = scanner.nextLine();

                Customer customer = hotelResource.getCustomer(email);

                if (customer == null) {
                    System.out.println("Account not exist. Please type 3 to create an account with us before continue booking.");
                    printMainMenu();
                } else {
                    System.out.println("Please enter room number");
                    String roomNumber = scanner.nextLine();

                    if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
                        IRoom room = hotelResource.getRoom(roomNumber);
                        Reservation reservation = hotelResource.bookARoom(email, room, checkIn, checkOut);
                        System.out.println("Reserve a room successfully.\n" + reservation);
                    } else {
                        System.out.println("Room not available.");
                    }

                }

            } else if (account.equalsIgnoreCase("n")) {
                System.out.println("Please select 3 to create account before you book a room");
                printMainMenu();
            } else {
                System.out.println("You can only type y or n");
                printMainMenu();
            }

        } else if (answer.equalsIgnoreCase("n")) {
            printMainMenu();
        } else {
            System.out.println("You can only type y or n");
            printMainMenu();
        }
    }

    private static Date getInputDate(Scanner scanner) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error parsing date. Please follow instruction to enter again.");
            findAndReserveRoom();
        }

        return null;
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email example: name@domain.com");
        String email = scanner.nextLine();

        Collection<Reservation> myReservation = hotelResource.getCustomersReservations(email);

        if (myReservation.isEmpty()) {
            System.out.println("No Reservation Found.");
        } else {
            myReservation.forEach(reservation -> System.out.println(reservation));
        }
    }

    private static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter firstName:");
        String firstName = scanner.nextLine();

        System.out.println("Enter lastName:");
        String lastName = scanner.nextLine();

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created successfully");
    }

}
