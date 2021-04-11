package bg.softuni.hotelagency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDate postedOn;
    @ManyToOne
    private User user;
    @ManyToOne
    private Hotel hotel;

    public Comment() {
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Comment setUser(User user) {
        this.user = user;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Comment setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public LocalDate getPostedOn() {
        return postedOn;
    }

    public Comment setPostedOn(LocalDate postedOn) {
        this.postedOn = postedOn;
        return this;
    }
}
