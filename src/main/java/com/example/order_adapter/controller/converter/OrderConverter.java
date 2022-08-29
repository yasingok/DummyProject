package com.example.order_adapter.controller.converter;

import com.example.order_adapter.Facade.resourse.PizzaOrderResponseBody;
import com.example.order_adapter.common.OrderStatus;
import com.example.order_adapter.dto.OrderDto;
import com.example.order_adapter.model.Order;
import com.example.order_adapter.resources.OrderRequest;
import com.example.order_adapter.resources.OrderRequestPayload;
import com.example.order_adapter.resources.OrderResponse;
import com.example.order_adapter.resources.OrderResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderConverter {
    public static List<OrderDto> fromOrderRequestPayload(OrderRequestPayload orderRequestPayload) {
        List<OrderRequest> orderRequestList = orderRequestPayload.getOrderRequest();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequestList) {
            OrderDto orderDto = new OrderDto();
            orderDto.setFlavor(orderRequest.getFlavor());
            orderDto.setSize(orderRequest.getSize());
            orderDto.setCrust(orderRequest.getCrust());
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    public static OrderResponse fromOrder(Order order) {
        if (Objects.isNull(order))
            return null;
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setFlavor(order.getName().getName());
        orderResponse.setCrust(order.getCrust().getCrust());
        orderResponse.setSize(order.getSize().getSize());
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setTableNo(order.getId().toString());
        orderResponse.setOrderStatus(order.getStatus());
        return orderResponse;
    }

    public static OrderResponsePayload fromPizzaResponseBodyList(
            List<PizzaOrderResponseBody> pizzaOrderResponseBodyList) {
        OrderResponsePayload orderResponsePayload = new OrderResponsePayload();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (PizzaOrderResponseBody pizzaOrderResponseBody : pizzaOrderResponseBodyList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setFlavor(pizzaOrderResponseBody.getFlavor());
            orderResponse.setCrust(pizzaOrderResponseBody.getCrust());
            orderResponse.setSize(pizzaOrderResponseBody.getSize());
            orderResponse.setOrderId(pizzaOrderResponseBody.getOrderId());
            orderResponse.setTableNo(pizzaOrderResponseBody.getTableNo() + "");
            orderResponse.setOrderStatus(pizzaOrderResponseBody.getOrderStatus());
            if (pizzaOrderResponseBody.getOrderStatus().equals(OrderStatus.FAILED))
                orderResponse.setFailureReason(pizzaOrderResponseBody.getFailureReason());
            orderResponseList.add(orderResponse);
        }
        orderResponsePayload.setOrderResponseList(orderResponseList);

        return orderResponsePayload;
    }
}
