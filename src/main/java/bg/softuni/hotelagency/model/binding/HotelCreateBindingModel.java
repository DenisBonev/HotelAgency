package bg.softuni.hotelagency.model.binding;

import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

public class HotelCreateBindingModel {
    @NotBlank(message = "Field cannot be blank")
    @Size(min = 2,max = 20,message = "Length must be between 2 and 20 characters")
    private String name;
    @Size(min = 5,max = 50,message = "Length must be between 5 and 50 characters")
    private String address;
    @NotNull(message = "Field cannot be blank")
    private String stars;
    @Email
    @NotBlank(message = "Field cannot be blank")
    private String email;
    @NotBlank(message = "Field cannot be blank")
    @Size(min = 15,max = 500,message = "Length must be between 15 and 500 characters")
    private String description;
    @NotNull(message = "Field cannot be blank")
    private List<MultipartFile> pictures;

    public HotelCreateBindingModel() {
    }

    public String getDescription() {
        return description;
    }

    public HotelCreateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public HotelCreateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HotelCreateBindingModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getStars() {
        return stars;
    }

    public HotelCreateBindingModel setStars(String stars) {
        this.stars = stars;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public HotelCreateBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public HotelCreateBindingModel setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }
}
