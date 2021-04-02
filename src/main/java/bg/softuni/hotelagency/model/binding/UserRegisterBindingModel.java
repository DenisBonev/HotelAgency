package bg.softuni.hotelagency.model.binding;

import bg.softuni.hotelagency.model.validation.FieldsMatch;
import bg.softuni.hotelagency.model.validation.IsAdult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;

@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords should match")
public class UserRegisterBindingModel {
    @NotEmpty(message = "Field must be filled")
    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters")
    private String firstName;
    @NotEmpty(message = "Field must be filled")
    @Size(min = 2, max = 20, message = "Length must be between 2 and 20 characters")
    private String lastName;
    @NotEmpty(message = "Field must be filled")
    @Email
    //TODO:regex pattern(one more in hotel)
    private String email;
    @Past
    //TODO:18 years old annotation
    @IsAdult
    @NotNull(message = "Date must be chosen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotEmpty(message = "Field must be filled")
    @Pattern(regexp = "\\+*[0-9]{10,12}")
    private String phoneNumber;
    @NotEmpty(message = "Field must be filled")
    @Size(min = 5, max = 20, message = "Length must be between 2 and 20 characters")
    private String password;
    @NotEmpty(message = "Field must be filled")
    @Size(min = 5, max = 20, message = "Length must be between 2 and 20 characters")
    private String confirmPassword;
    private MultipartFile profilePicture;
    private boolean hotelOwner;

    public UserRegisterBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegisterBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public UserRegisterBindingModel setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserRegisterBindingModel setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public boolean isHotelOwner() {
        return hotelOwner;
    }

    public UserRegisterBindingModel setHotelOwner(boolean hotelOwner) {
        this.hotelOwner = hotelOwner;
        return this;
    }
}
