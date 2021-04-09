package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.User;

import java.time.LocalDateTime;

public class ExceptionLogViewModel {
    private String action;
    private LocalDateTime dateTime;
    private User user;
    private String exception;

    public ExceptionLogViewModel() {
    }

    public String getAction() {
        return action;
    }

    public ExceptionLogViewModel setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ExceptionLogViewModel setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ExceptionLogViewModel setUser(User user) {
        this.user = user;
        return this;
    }

    public String getException() {
        return exception;
    }

    public ExceptionLogViewModel setException(String exception) {
        this.exception = exception;
        return this;
    }
}
