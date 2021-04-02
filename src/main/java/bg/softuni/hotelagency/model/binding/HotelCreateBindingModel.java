package bg.softuni.hotelagency.model.binding;

import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class HotelCreateBindingModel {
    private String name;
    private String address;
    private String stars;
    private String email;
    private String description;
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
