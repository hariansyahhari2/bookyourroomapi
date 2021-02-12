package com.hariansyah.bookyourrooms.api.models.entitysearch;

import com.hariansyah.bookyourrooms.api.models.pagination.PageSearch;

public class RoomSearch extends PageSearch {

    private Integer id;

    private String roomType;

    private Double price;

    private Integer numberOfRoom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(Integer numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }
}
