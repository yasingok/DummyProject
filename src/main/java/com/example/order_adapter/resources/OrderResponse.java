package com.example.order_adapter.resources;

import com.example.order_adapter.common.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderResponse extends CommonBase{
    @JsonProperty("Crust")
    private String crust;
    @JsonProperty("Flavor")
    private String flavor;
    @JsonProperty("Size")
    private String size;
    @JsonProperty("Table_No")
    private String tableNo;
    @JsonProperty("Order_ID")
    private Integer orderId;
    @JsonProperty("Order_Status")
    private OrderStatus orderStatus;
    @JsonProperty("Failure_Reason")
    private String failureReason;
}
