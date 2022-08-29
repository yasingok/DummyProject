package com.example.order_adapter.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserResource extends CommonBase{
    @JsonProperty(value = "firstName",required = true)
    @NotEmpty
    @NotNull
    private String firstName;
    @JsonProperty(value = "lastName",required = true)
    @NotEmpty
    @NotNull
    private String lastName;
    @JsonProperty(value = "email",required = true)
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;
    @JsonProperty(value = "identity",required = true)
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{10}|[A-z][0-9]{8}$")
    private String identityNumber;
    @JsonProperty(value = "phone",required = true)
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
    private String phoneNumber;
    @JsonProperty(value = "Address",required = true)
    @NotNull
    @Valid
    private AddressResource addressResource;
}
