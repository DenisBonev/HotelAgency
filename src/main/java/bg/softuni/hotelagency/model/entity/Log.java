package bg.softuni.hotelagency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    @Column(nullable = false)
    private String action;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @ManyToOne
    private User user;
    private String exception;

    public Log() {
    }

    public String getAction() {
        return action;
    }

    public Log setAction(String action) {
        this.action = action;
        return this;
    }


    public String getException() {
        return exception;
    }

    public Log setException(String exception) {
        this.exception = exception;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Log setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Log setUser(User user) {
        this.user = user;
        return this;
    }
}
