package bg.softuni.hotelagency.model.entity;


import bg.softuni.hotelagency.model.entity.enums.StarEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Enumerated(EnumType.ORDINAL)
    private StarEnum stars;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String videoUrl;
    @ManyToOne
    private User owner;


    public Hotel() {
    }

    public String getName() {
        return name;
    }

    public Hotel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Hotel setAddress(String address) {
        this.address = address;
        return this;
    }

    public StarEnum getStars() {
        return stars;
    }

    public Hotel setStars(StarEnum stars) {
        this.stars = stars;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Hotel setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public Hotel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public User getOwner() {
        return owner;
    }

    public Hotel setOwner(User owner) {
        this.owner = owner;
        return this;
    }
}
