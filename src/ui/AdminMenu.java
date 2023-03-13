package ui;

import models.reservation.Reservation;
import resources.AdminResource;
import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;
import models.room.enums.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {

    private static AdminResource adminResource = AdminResource.getInstance();

    public static void adminMenu() {
        String line = "";

        Scanner scanner = new Scanner(System.in);

        printMenu();

        try {
            do {
                line = scanner.nextLine();

                switch (line.charAt(0)) {
                    case '1':
                        showAllCustomers();
                        break;
                    case '2':
                        showAllRooms();
                        break;
                    case '3':
                        showAllReservations();
                        break;
                    case '4':
                        addRoom();
                        break;
                    case '5':
                        MainMenu.printMainMenu();
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

    private static void printMenu() {
        System.out.print("\nAdmin Menu\n" +
                "-------------------------------------------\n" +
                "1. See All Customers\n" +
                "2. See All Rooms\n" +
                "3. See All Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }

    private static void showAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customer found");
        } else {
            adminResource.getAllCustomers().forEach(System.out::println);
        }
    }

    private static void showAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found");
        } else {
            adminResource.getAllRooms().forEach(System.out::println);
        }
    }

    private static void showAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter room type: SINGLE or DOUBLE");
        String roomType = scanner.nextLine();

        System.out.println("Price per night:");
        Double price = scanner.nextDouble();


        try {
            RoomType type = RoomType.valueOf(roomType);
            Room room = new Room(roomNumber, price, type);
            adminResource.addRoom(room);
            System.out.println("Room added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid room type: " + roomType);
        }


    }

}
