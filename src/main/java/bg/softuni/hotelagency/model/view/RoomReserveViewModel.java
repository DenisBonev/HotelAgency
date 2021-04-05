package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;

import java.math.BigDecimal;

public class RoomReserveViewModel {
    private Long id;
    private String name;
    private RoomTypeEnum type;
    private Integer singleBedsCount;
    private Integer twinBedsCount;
    private BigDecimal price;

    public RoomReserveViewModel() {
    }

    public Long getId() {
        return id;
    }

    public RoomReserveViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomReserveViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public RoomReserveViewModel setType(RoomTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getSingleBedsCount() {
        return singleBedsCount;
    }

    public RoomReserveViewModel setSingleBedsCount(Integer singleBedsCount) {
        this.singleBedsCount = singleBedsCount;
        return this;
    }

    public Integer getTwinBedsCount() {
        return twinBedsCount;
    }

    public RoomReserveViewModel setTwinBedsCount(Integer twinBedsCount) {
        this.twinBedsCount = twinBedsCount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RoomReserveViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
