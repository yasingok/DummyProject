package com.example.order_adapter.service.Order;

import com.example.order_adapter.Facade.resourse.PizzaOrderResponseBody;
import com.example.order_adapter.dto.OrderDto;
import com.example.order_adapter.exceptions.CrustNotSupportedException;
import com.example.order_adapter.exceptions.FlavorNotSupportedException;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.ServiceException;
import com.example.order_adapter.exceptions.SizeNotSupportedException;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderService {

    List<PizzaOrderResponseBody> createOrder(String identityNumber,
                                             List<OrderDto> orderDtoList) throws UserNotFoundException, FlavorNotSupportedException, SizeNotSupportedException, CrustNotSupportedException, OrderException, ServiceException, JsonProcessingException;

    void deleteOrder(String identityNumber, int orderId) throws UserNotFoundException, OrderException, ServiceException, JsonProcessingException;

    @Transactional
    Page<Order> getAllOrdersForUser(String identityNumber, Pageable pageable) throws UserNotFoundException;

    @Transactional
    Page<Order> getAllOrders(Pageable pageable);

    Order getOrderForUser(String identityNumber, int orderId) throws UserNotFoundException, OrderException;
}
