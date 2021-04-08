package bg.softuni.hotelagency.model.service;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.User;

import java.time.LocalDate;

public class CommentServiceModel {
    private String content;
    private User user;
    private Hotel hotel;
    private LocalDate postedOn;

    public CommentServiceModel() {
    }

    public String getContent() {
        return content;
    }

    public CommentServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public CommentServiceModel setUser(User user) {
        this.user = user;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public CommentServiceModel setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public LocalDate getPostedOn() {
        return postedOn;
    }

    public CommentServiceModel setPostedOn(LocalDate postedOn) {
        this.postedOn = postedOn;
        return this;
    }
}
