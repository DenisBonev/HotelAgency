package bg.softuni.hotelagency.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class HotelEditBindingModel {
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

    public HotelEditBindingModel() {
    }

    public String getDescription() {
        return description;
    }

    public HotelEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public HotelEditBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HotelEditBindingModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getStars() {
        return stars;
    }

    public HotelEditBindingModel setStars(String stars) {
        this.stars = stars;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public HotelEditBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public HotelEditBindingModel setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }
}
