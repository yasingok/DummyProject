package com.example.order_adapter.Facade.resourse;

import com.example.order_adapter.common.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PizzaOrderResponseBody {
    @JsonProperty("Crust")
    private String crust;
    @JsonProperty("Flavor")
    private String flavor;
    @JsonProperty("Size")
    private String size;
    @JsonProperty("Table_No")
    private Integer tableNo;
    @JsonProperty("Order_ID")
    private Integer orderId;
    @JsonProperty("Timestamp")
    private String timeStamp;
    @JsonProperty("orderStatus")
    private OrderStatus orderStatus;
    private String failureReason;
}
