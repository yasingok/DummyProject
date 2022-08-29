package com.example.order_adapter.Facade.resourse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PizzaPostRequestBody {
    @JsonProperty("Crust")
    private String crust;
    @JsonProperty("Flavor")
    private String flavor;
    @JsonProperty("Size")
    private String size;
    @JsonProperty("Table_No")
    private Integer tableNo;
}
