package com.example.order_adapter.Facade.resourse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PizzaGetResponseBody {

    List<PizzaOrderResponseBody> pizzaOrderResponseBodyList;
}
