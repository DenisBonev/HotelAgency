package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.enums.StarEnum;

import java.util.List;

public class HotelEditViewModel {
    private String name;
    private StarEnum stars;
    private String address;
    private String email;
    private String description;
    private List<String> imageUrls;

    public HotelEditViewModel() {
    }

    public String getName() {
        return name;
    }

    public HotelEditViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public StarEnum getStars() {
        return stars;
    }

    public HotelEditViewModel setStars(StarEnum stars) {
        this.stars = stars;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HotelEditViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public HotelEditViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HotelEditViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public HotelEditViewModel setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }
}
