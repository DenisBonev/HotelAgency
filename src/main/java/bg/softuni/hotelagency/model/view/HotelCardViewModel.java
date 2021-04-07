package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.enums.StarEnum;

public class HotelCardViewModel {
    private Long id;
    private String name;
    private String address;
    private StarEnum stars;
    private String mainPictureUrl;

    public HotelCardViewModel() {
    }

    public Long getId() {
        return id;
    }

    public HotelCardViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HotelCardViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HotelCardViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public StarEnum getStars() {
        return stars;
    }

    public HotelCardViewModel setStars(StarEnum stars) {
        this.stars = stars;
        return this;
    }

    public String getMainPictureUrl() {
        return mainPictureUrl;
    }

    public HotelCardViewModel setMainPictureUrl(String mainPictureUrl) {
        this.mainPictureUrl = mainPictureUrl;
        return this;
    }
}
