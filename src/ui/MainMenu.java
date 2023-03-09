package ui;

import resources.HotelResource;
import models.reservation.Reservation;
import models.room.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final String DATE_FORMAT = "MM/dd/YYYY";

    private static HotelResource hotelResource = HotelResource.getInstance();

    public static void mainMenu() {
        String line = "";
        Scanner scanner = new Scanner(System.in);

        printMainMenu();

//        try {
//            do {
//                line = scanner.nextLine();
//
//                if (line.length() == 1) {
//                    switch (line.charAt(0)) {
//                        case '1':
//                            findAndReserveRoom();
//                            break;
//                        case '2':
//                            seeMyReservation();
//                            break;
//                        case '3':
//                            createAccount();
//                            break;
//                        case '4':
//                            AdminMenu.adminMenu();
//                            break;
//                        case '5':
//                            System.out.println("Exit");
//                            break;
//                        default:
//                            System.out.println("Unknown action\n");
//                            break;
//                    }
//                } else {
//                    System.out.println("Error: Invalid action\n");
//                }
//            } while (line.charAt(0) != '5');
//        } catch (StringIndexOutOfBoundsException ex) {
//            System.out.println("Empty input received. Exiting program...");
//        }

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
                    System.out.println("AdminMenu");
                    break;
                case '5':
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Unknown action");
                    break;
            }
        } while (line.charAt(0) != '5');

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

            } else {

            }
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
