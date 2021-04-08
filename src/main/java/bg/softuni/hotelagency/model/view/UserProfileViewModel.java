package bg.softuni.hotelagency.model.view;


import java.util.List;

public class UserProfileViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private List<String> roles;

    public UserProfileViewModel() {
    }


    public List<String> getRoles() {
        return roles;
    }

    public UserProfileViewModel setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileViewModel setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserProfileViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserProfileViewModel setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
