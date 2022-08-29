package com.example.order_adapter.dto;

import com.example.order_adapter.model.Address;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;

    private String lastName;

    private String email;

    private String identityNumber;

    private String phoneNumber;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
