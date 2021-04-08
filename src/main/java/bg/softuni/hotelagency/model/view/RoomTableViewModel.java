package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;

import java.math.BigDecimal;

public class RoomTableViewModel {
    private Long id;
    private RoomTypeEnum type;
    private String name;
    private BigDecimal price;
    private Integer singleBedsCount;
    private Integer twinBedsCount;
    private Integer count;

    public RoomTableViewModel() {
    }

    public Long getId() {
        return id;
    }

    public RoomTableViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public RoomTableViewModel setType(RoomTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomTableViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RoomTableViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getSingleBedsCount() {
        return singleBedsCount;
    }

    public RoomTableViewModel setSingleBedsCount(Integer singleBedsCount) {
        this.singleBedsCount = singleBedsCount;
        return this;
    }

    public Integer getTwinBedsCount() {
        return twinBedsCount;
    }

    public RoomTableViewModel setTwinBedsCount(Integer twinBedsCount) {
        this.twinBedsCount = twinBedsCount;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public RoomTableViewModel setCount(Integer count) {
        this.count = count;
        return this;
    }
}
