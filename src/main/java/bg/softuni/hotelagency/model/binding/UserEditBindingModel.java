package bg.softuni.hotelagency.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

public class UserEditBindingModel {
    @NotEmpty(message = "Field must be filled")
    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters")
    private String firstName;
    @NotEmpty(message = "Field must be filled")
    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters")
    private String lastName;
    @NotEmpty(message = "Field must be filled")
    @Email
    private String email;
    @NotEmpty(message = "Field must be filled")
    @Pattern(regexp = "\\+*[0-9]{10,12}")
    private String phoneNumber;
    private MultipartFile profilePicture;


    public UserEditBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public String getEmail() {
        return email;
    }

    public UserEditBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEditBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public UserEditBindingModel setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

}
