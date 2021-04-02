package bg.softuni.hotelagency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends BaseEntity {
    @Column(nullable = false)
    private String content;
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
}
