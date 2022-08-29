package com.example.order_adapter.factory;

import com.example.order_adapter.Facade.resourse.PizzaOrderResponseBody;
import com.example.order_adapter.common.OrderStatus;
import com.example.order_adapter.constant.TestConstant;

import java.util.ArrayList;
import java.util.List;

public class PizzaStoreFactory {
    public static List<PizzaOrderResponseBody> createPizzaOrderResponseList(){
        List<PizzaOrderResponseBody> pizzaOrderResponseBodyList = new ArrayList<>();
        pizzaOrderResponseBodyList.add(createPizzaOrderResponseBody());
        pizzaOrderResponseBodyList.add(createPizzaOrderResponseBody());
        return pizzaOrderResponseBodyList;
    }

    public static PizzaOrderResponseBody createPizzaOrderResponseBody(){
        PizzaOrderResponseBody pizzaOrderResponseBody = new PizzaOrderResponseBody();
        pizzaOrderResponseBody.setOrderStatus(OrderStatus.ACTIVE);
        pizzaOrderResponseBody.setSize(TestConstant.SIZE);
        pizzaOrderResponseBody.setFlavor(TestConstant.FLAVOR);
        pizzaOrderResponseBody.setCrust(TestConstant.CRUST);
        pizzaOrderResponseBody.setTableNo(1);
        return  pizzaOrderResponseBody;
    }
}
