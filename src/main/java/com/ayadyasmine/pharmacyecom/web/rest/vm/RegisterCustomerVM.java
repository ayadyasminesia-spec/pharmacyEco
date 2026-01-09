package com.ayadyasmine.pharmacyecom.web.rest.vm;

import com.ayadyasmine.pharmacyecom.service.dto.AdminUserDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * View Model for customer registration, extending AdminUserDTO with phone number.
 */
public class RegisterCustomerVM extends AdminUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    @Size(min = 10, max = 20)
    private String phoneNumber;

    public RegisterCustomerVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegisterCustomerVM{" + super.toString() + ", phoneNumber='" + phoneNumber + "'}";
    }
}