package bg.softuni.hotelagency.model.view;

import java.time.LocalDate;

public class UserEditViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public UserEditViewModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditViewModel setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEditViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

}
