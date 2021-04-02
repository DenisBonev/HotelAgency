package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.enums.StarEnum;

import java.util.List;

public class HotelDetailsViewModel {
    private String name;
    private String address;
    private StarEnum stars;
    private String email;
    private String description;
    private String mainPictureUrl;
    private List<String> pictureUrls;

    public HotelDetailsViewModel() {
    }

    public String getName() {
        return name;
    }

    public HotelDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HotelDetailsViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public StarEnum getStars() {
        return stars;
    }

    public HotelDetailsViewModel setStars(StarEnum stars) {
        this.stars = stars;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public HotelDetailsViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HotelDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getMainPictureUrl() {
        return mainPictureUrl;
    }

    public HotelDetailsViewModel setMainPictureUrl(String mainPictureUrl) {
        this.mainPictureUrl = mainPictureUrl;
        return this;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public HotelDetailsViewModel setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
        return this;
    }
}
