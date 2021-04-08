package bg.softuni.hotelagency.model.view;


import java.util.List;

public class UserRoleViewModel {
    private Long id;
    private String email;
    private List<String> roles;

    public UserRoleViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserRoleViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRoleViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public UserRoleViewModel setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
