package com.example.order_adapter.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderRequest extends CommonBase{
    @NotNull
    @NotEmpty
    private String crust;
    @NotNull
    @NotEmpty
    private String flavor;
    @NotNull
    @NotEmpty
    private String size;
}
