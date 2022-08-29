package com.example.order_adapter.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequestPayload extends CommonBase{

    @JsonProperty(value = "createUser",required = true)
    @NotNull
    @Valid
    private UserResource userResource;
}
