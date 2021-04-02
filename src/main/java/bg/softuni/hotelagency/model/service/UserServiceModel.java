package bg.softuni.hotelagency.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class UserServiceModel {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String phoneNumber;
    private String password;
    private MultipartFile profilePicture;
    private boolean hotelOwner;

    public UserServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserServiceModel setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserServiceModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public UserServiceModel setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public boolean isHotelOwner() {
        return hotelOwner;
    }

    public UserServiceModel setHotelOwner(boolean hotelOwner) {
        this.hotelOwner = hotelOwner;
        return this;
    }
}
