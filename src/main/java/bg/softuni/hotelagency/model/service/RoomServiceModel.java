package bg.softuni.hotelagency.model.service;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;

import java.math.BigDecimal;

public class RoomServiceModel {
    private RoomTypeEnum type;
    private Integer count;
    private Integer singleBedsCount;
    private Integer twinBedsCount;
    private BigDecimal price;
    private String name;
    private Hotel hotel;

    public RoomServiceModel() {
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public RoomServiceModel setType(RoomTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public RoomServiceModel setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getSingleBedsCount() {
        return singleBedsCount;
    }

    public RoomServiceModel setSingleBedsCount(Integer singleBedsCount) {
        this.singleBedsCount = singleBedsCount;
        return this;
    }

    public Integer getTwinBedsCount() {
        return twinBedsCount;
    }

    public RoomServiceModel setTwinBedsCount(Integer twinBedsCount) {
        this.twinBedsCount = twinBedsCount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RoomServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public RoomServiceModel setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }
}
