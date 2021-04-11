package bg.softuni.hotelagency.model.entity;

import bg.softuni.hotelagency.model.entity.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public UserRole() {
    }

    public RoleEnum getName() {
        return name;
    }

    public UserRole setName(RoleEnum name) {
        this.name = name;
        return this;
    }
}
