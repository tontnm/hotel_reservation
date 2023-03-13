package models.room;

import models.room.enums.RoomType;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public boolean isFree() {
        return price != null && this.price.equals(0.0);
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                '}';
    }


}
