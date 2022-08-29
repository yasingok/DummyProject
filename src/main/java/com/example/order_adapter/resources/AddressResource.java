package com.example.order_adapter.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class AddressResource extends CommonBase{
    @NotEmpty
    @NotNull
    private String address1;
    @NotEmpty
    @NotNull
    private String address2;
    @NotEmpty
    @NotNull
    private String city;
    @NotEmpty
    @NotNull
    private String state;
    @NotEmpty
    @NotNull
    private String country;
    @NotEmpty
    @NotNull
    private String postalCode;
}
