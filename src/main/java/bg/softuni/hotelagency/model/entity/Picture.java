package bg.softuni.hotelagency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "pictures")
@Entity
public class Picture extends BaseEntity {

    @Column(nullable = false)
    private String url;
    @ManyToOne
    private Hotel hotel;

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public Picture setUrl(String url) {
        this.url = url;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Picture setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }
}
