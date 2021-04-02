package bg.softuni.hotelagency.model.binding;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class RoomAddBindingModel {
    @NotNull(message = "Field cannot be blank")
    private RoomTypeEnum type;
    @Min(1)
    @Max(200)
    @NotNull(message = "Field cannot be blank")
    private Integer count;
    @Min(0)
    @Max(20)
    @NotNull(message = "Field cannot be blank")
    private Integer singleBedsCount;
    @Min(0)
    @Max(20)
    @NotNull(message = "Field cannot be blank")
    private Integer twinBedsCount;
    @Min(5)
    @Max(1000)
    @NotNull(message = "Field cannot be blank")
    private BigDecimal price;
    @Size(min = 3, max = 20,message = "Length must be between 3 and 20 characters")
    @NotBlank(message = "Field cannot be blank")
    private String name;

    public RoomAddBindingModel() {
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public RoomAddBindingModel setType(RoomTypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public RoomAddBindingModel setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getSingleBedsCount() {
        return singleBedsCount;
    }

    public RoomAddBindingModel setSingleBedsCount(Integer singleBedsCount) {
        this.singleBedsCount = singleBedsCount;
        return this;
    }

    public Integer getTwinBedsCount() {
        return twinBedsCount;
    }

    public RoomAddBindingModel setTwinBedsCount(Integer twinBedsCount) {
        this.twinBedsCount = twinBedsCount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RoomAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }
}
