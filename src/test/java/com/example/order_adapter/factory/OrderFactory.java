package com.example.order_adapter.factory;

import com.example.order_adapter.constant.TestConstant;
import com.example.order_adapter.dto.OrderDto;
import com.example.order_adapter.model.Order;
import com.example.order_adapter.model.Pizza;
import com.example.order_adapter.model.PizzaCrust;
import com.example.order_adapter.model.PizzaSize;
import com.example.order_adapter.resources.OrderRequest;
import com.example.order_adapter.resources.OrderRequestPayload;
import com.example.order_adapter.resources.OrderResponsePayload;
import liquibase.pro.packaged.O;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderFactory {
    public static List<OrderDto> createSuccessOrderDtoList(){
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(createSuccessOrderDto());
        orderDtoList.add(createSuccessOrderDto());
        return orderDtoList;
    }

    public static OrderDto  createSuccessOrderDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setCrust(TestConstant.CRUST);
        orderDto.setSize(TestConstant.SIZE);
        orderDto.setFlavor(TestConstant.FLAVOR);
        return orderDto;
    }

    public static Iterable<Order> savedOrdersIterable(){
        Iterable<Order> iterable = Arrays.asList(createSuccessOrder(), createSuccessOrder());
        return iterable;
    }

    public static List<Order> savedOrdersList(){
        return Arrays.asList(createSuccessOrder(), createSuccessOrder());
    }

    public static Order  createSuccessOrder(){
        Order order = new Order();
        order.setCrust(new PizzaCrust(TestConstant.CRUST));
        order.setSize(new PizzaSize(TestConstant.SIZE));
        order.setName(new Pizza(TestConstant.FLAVOR));
        order.setId(1);
        return order;
    }

    public static OrderRequestPayload createOrderRequestPayload(){
        OrderRequestPayload orderRequestPayload = new OrderRequestPayload();
        orderRequestPayload.setOrderRequest(createOrderRequestList());
        return orderRequestPayload;
    }

    public static List<OrderRequest> createOrderRequestList(){
        List<OrderRequest> orderRequestList = new ArrayList<>();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFlavor(TestConstant.FLAVOR);
        orderRequest.setCrust(TestConstant.CRUST);
        orderRequest.setSize(TestConstant.SIZE);
        orderRequestList.add(orderRequest);
        return orderRequestList;
    }
}
