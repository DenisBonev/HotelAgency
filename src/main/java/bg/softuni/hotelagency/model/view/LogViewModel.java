package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.User;

import java.time.LocalDateTime;

public class LogViewModel {
    private String action;
    private User user;
    private LocalDateTime localDateTime;

    public LogViewModel() {
    }

    public String getAction() {
        return action;
    }

    public LogViewModel setAction(String action) {
        this.action = action;
        return this;
    }

    public User getUser() {
        return user;
    }

    public LogViewModel setUser(User user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LogViewModel setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }
}
