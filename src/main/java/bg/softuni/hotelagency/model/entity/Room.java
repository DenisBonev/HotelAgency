package bg.softuni.hotelagency.model.entity;

import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "rooms")
@Entity
public class Room extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private RoomTypeEnum type;
    @Column(nullable = false)
    private Integer count;
    @Column(nullable = false)
    private Integer singleBedsCount;
    @Column(nullable = false)
    private Integer twinBedsCount;
    @Column(nullable = false)
    private BigDecimal price;
    private String name;
    @ManyToOne
    private Hotel hotel;

    public Room() {
    }

    public String getName() {
        return name;
    }

    public Room setName(String name) {
        this.name = name;
        return this;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public Room setType(RoomTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public Room setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getSingleBedsCount() {
        return singleBedsCount;
    }

    public Room setSingleBedsCount(Integer singleBedsCount) {
        this.singleBedsCount = singleBedsCount;
        return this;
    }

    public Integer getTwinBedsCount() {
        return twinBedsCount;
    }

    public Room setTwinBedsCount(Integer twinBedsCount) {
        this.twinBedsCount = twinBedsCount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Room setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }
}
