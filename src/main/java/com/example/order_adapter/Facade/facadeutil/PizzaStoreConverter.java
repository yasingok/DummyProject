package com.example.order_adapter.Facade.facadeutil;

import com.example.order_adapter.Facade.resourse.PizzaPostRequestBody;
import com.example.order_adapter.Facade.resourse.PizzaOrderResponseBody;
import com.example.order_adapter.common.OrderStatus;
import com.example.order_adapter.model.Order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PizzaStoreConverter {

    public static List<PizzaPostRequestBody> fromOrder (Iterable<Order> orderIterable){
        List<PizzaPostRequestBody> pizzaPostRequestBodyList = new ArrayList<>();

        for (Order order : orderIterable) {
            PizzaPostRequestBody pizzaPostRequestBody = new PizzaPostRequestBody();
            pizzaPostRequestBody.setCrust(order.getCrust().getCrust());
            pizzaPostRequestBody.setSize(order.getSize().getSize());
            pizzaPostRequestBody.setFlavor(order.getName().getName());
            pizzaPostRequestBody.setTableNo(order.getId());
            pizzaPostRequestBodyList.add(pizzaPostRequestBody);
        }
        return pizzaPostRequestBodyList;
    }

    public static Integer getOrderId(PizzaOrderResponseBody pizzaOrderResponseBody){
        return pizzaOrderResponseBody.getOrderId();
    }

    public static PizzaOrderResponseBody pizzaOrderResponseBody(PizzaPostRequestBody pizzaPostRequestBody, OrderStatus orderStatus, String failureReason){
        PizzaOrderResponseBody pizzaOrderResponseBody = new PizzaOrderResponseBody();
        pizzaOrderResponseBody.setFlavor(pizzaPostRequestBody.getFlavor());
        pizzaOrderResponseBody.setSize(pizzaPostRequestBody.getSize());
        pizzaOrderResponseBody.setCrust(pizzaPostRequestBody.getCrust());
        pizzaOrderResponseBody.setTableNo(pizzaPostRequestBody.getTableNo());
        pizzaOrderResponseBody.setOrderStatus(orderStatus);
        pizzaOrderResponseBody.setFailureReason(failureReason);
        return pizzaOrderResponseBody;
    }
}
