package ui;

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

                if (line.length() == 1) {
                    switch (line.charAt(0)) {
                        case '1':
                            displayAllCustomers();
                            break;
                        case '2':
                            displayAllRooms();
                            break;
                        case '3':
                            displayAllReservations();
                            break;
                        case '4':
                            addRoom();
                            break;
                        case '5':
                            MainMenu.printMainMenu();
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            break;
                    }
                } else {
                    System.out.println("Error: Invalid action\n");
                }
            } while (line.charAt(0) != '5' || line.length() != 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Empty input received. Exiting program...");
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

    private static void addRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter price per night:");
        double roomPrice = enterRoomPrice(scanner);

        System.out.println("Enter room type: 1 for single bed, 2 for double bed:");
        RoomType roomType = enterRoomType(scanner);

        Room room = new Room(roomNumber, roomPrice, roomType);

        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Room added successfully!");

        System.out.println("Would like to add another room? Y/N");
        addAnotherRoom();
    }

    private static double enterRoomPrice(Scanner scanner) {
        try {
            return scanner.nextDouble();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid room price! Please, enter a valid double number:" +
                    "Decimal should be separated by point (.)");
            return enterRoomPrice(scanner);
        }
    }

    private static RoomType enterRoomType(Scanner scanner) {
        try {
            return RoomType.valueOfLabel(scanner.nextLine());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid room type! Please, choose 1 for single bed or 2 for double bed:");
            return enterRoomType(scanner);
        }
    }

    private static void addAnotherRoom() {
        Scanner scanner = new Scanner(System.in);

        try {
            String anotherRoom;

            anotherRoom = scanner.nextLine();

            while ((anotherRoom.charAt(0) != 'Y' && anotherRoom.charAt(0) != 'N')
                    || anotherRoom.length() != 1) {
                System.out.println("Please enter Y (Yes) or N (No)");
                anotherRoom = scanner.nextLine();
            }

            if (anotherRoom.charAt(0) == 'Y') {
                addRoom();
            } else if (anotherRoom.charAt(0) == 'N') {
                printMenu();
            } else {
                addAnotherRoom();
            }
        } catch (StringIndexOutOfBoundsException ex) {
            addAnotherRoom();
        }
    }

    private static void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            adminResource.getAllRooms().forEach(System.out::println);
        }
    }

    private static void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            adminResource.getAllCustomers().forEach(System.out::println);
        }
    }

    private static void displayAllReservations() {
//        adminResource.displayAllReservations();
    }

}
