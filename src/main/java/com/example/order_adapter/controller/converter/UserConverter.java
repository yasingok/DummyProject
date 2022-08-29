package com.example.order_adapter.controller.converter;

import com.example.order_adapter.dto.UserDto;
import com.example.order_adapter.model.Address;
import com.example.order_adapter.model.User;
import com.example.order_adapter.resources.AddressResource;
import com.example.order_adapter.resources.UserResource;

import java.util.Objects;

public class UserConverter {

    public static UserResource fromUser(User user){
        if (Objects.isNull(user))
            return null;
        UserResource userResource =  new UserResource();
        AddressResource addressResource = new AddressResource();

        userResource.setFirstName(user.getFirstName());
        userResource.setLastName(user.getLastName());
        userResource.setEmail(user.getEmail());
        userResource.setPhoneNumber(user.getPhoneNumber());
        userResource.setIdentityNumber(user.getIdentityNumber());

        Address address = user.getAddress();
        if (Objects.nonNull(address)){
            addressResource.setAddress1(address.getAddress1());
            addressResource.setAddress2(address.getAddress2());
            addressResource.setCity(address.getCity());
            addressResource.setState(address.getState());
            addressResource.setCountry(address.getCountry());
            addressResource.setPostalCode(address.getPostalCode());
        }
        userResource.setAddressResource(addressResource);
        return userResource;
    }

    public static UserDto fromUserResource(UserResource userResource){
        UserDto userDto = new UserDto();
        userDto.setFirstName(userResource.getFirstName());
        userDto.setLastName(userResource.getLastName());
        userDto.setEmail(userResource.getEmail());
        userDto.setPhoneNumber(userResource.getPhoneNumber());
        userDto.setIdentityNumber(userResource.getIdentityNumber());

        AddressResource addressResource = userResource.getAddressResource();
        userDto.setAddress1(addressResource.getAddress1());
        userDto.setAddress2(addressResource.getAddress2());
        userDto.setCity(addressResource.getCity());
        userDto.setState(addressResource.getState());
        userDto.setCountry(addressResource.getCountry());

        return userDto;
    }
}
