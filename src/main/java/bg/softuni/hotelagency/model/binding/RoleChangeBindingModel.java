package bg.softuni.hotelagency.model.binding;

import bg.softuni.hotelagency.model.entity.enums.RoleEnum;

public class RoleChangeBindingModel {
    private RoleEnum admin;
    private RoleEnum user;
    private RoleEnum hotelOwner;

    public RoleChangeBindingModel() {
    }


    public RoleEnum getAdmin() {
        return admin;
    }

    public RoleChangeBindingModel setAdmin(RoleEnum admin) {
        this.admin = admin;
        return this;
    }

    public RoleEnum getUser() {
        return user;
    }

    public RoleChangeBindingModel setUser(RoleEnum user) {
        this.user = user;
        return this;
    }

    public RoleEnum getHotelOwner() {
        return hotelOwner;
    }

    public RoleChangeBindingModel setHotelOwner(RoleEnum hotelOwner) {
        this.hotelOwner = hotelOwner;
        return this;
    }
}
