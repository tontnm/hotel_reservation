package models.room;

import models.room.enums.RoomType;

public interface IRoom {
    public String getRoomNumber();

    public Double getPrice();

    public RoomType getRoomType();

    public boolean isFree();
}
