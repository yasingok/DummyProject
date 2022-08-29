package com.example.order_adapter.controller;

import com.example.order_adapter.common.constant.AdapterConstant;
import com.example.order_adapter.controller.converter.OrderConverter;
import com.example.order_adapter.exceptions.CrustNotSupportedException;
import com.example.order_adapter.exceptions.FlavorNotSupportedException;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.ServiceException;
import com.example.order_adapter.exceptions.SizeNotSupportedException;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.resources.OrderRequestPayload;
import com.example.order_adapter.resources.OrderResponse;
import com.example.order_adapter.resources.OrderResponsePayload;
import com.example.order_adapter.service.Order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(AdapterConstant.ORDER_CONTROLLER_PATH)
@RequiredArgsConstructor
@Api(value = "Order Api documentation")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping(path = "/create/{userIdentity}")
    @ApiOperation(value = "Create new order")
    public ResponseEntity<OrderResponsePayload> createOrder(
            @Valid @NotNull @NotEmpty @RequestBody OrderRequestPayload orderRequestPayload, @NotBlank @PathVariable("userIdentity") String userIdentity) throws OrderException, UserNotFoundException, FlavorNotSupportedException, ServiceException, SizeNotSupportedException, JsonProcessingException, CrustNotSupportedException {
        String identityNumber = userIdentity;
        OrderResponsePayload orderResponsePayload = OrderConverter.fromPizzaResponseBodyList(orderService.createOrder(identityNumber, OrderConverter.fromOrderRequestPayload(orderRequestPayload)));
        return ResponseEntity.ok(orderResponsePayload);
    }

    @DeleteMapping(path = "/delete/{userIdentity}/{orderId}")
    @ApiOperation(value = "Cancel order")
    public ResponseEntity<Void> deleteOrder(
            @NotBlank @PathVariable("userIdentity") String userIdentity,
            @NotBlank @PathVariable("orderId") String orderId) throws OrderException, UserNotFoundException, ServiceException, JsonProcessingException {
        orderService.deleteOrder(userIdentity, getOrderId(orderId));

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/receive/{userIdentity}/{orderId}")
    @ApiOperation(value = "Receive any active order for user")
    public ResponseEntity<OrderResponse> getAnyOrderForUser(
            @NotBlank @PathVariable("userIdentity") String userIdentity,
            @NotBlank @PathVariable("orderId") String orderId) throws OrderException, UserNotFoundException, ServiceException {
        Integer.parseInt(orderId);
        String identityNumber = userIdentity;
        OrderResponse orderResponse = OrderConverter.fromOrder(orderService.getOrderForUser(userIdentity, getOrderId(orderId)));
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping(path = "/receive/{userIdentity}")
    @ApiOperation(value = "Receive all active order for user")
    public ResponseEntity<OrderResponsePayload> getAllOrderForUser(
            @NotBlank @PathVariable("userIdentity") String userIdentity,
            @PageableDefault(value = 50) final Pageable pageable) throws UserNotFoundException {
        String identityNumber = userIdentity;
        Page<OrderResponse> orderResponse = orderService.getAllOrdersForUser(identityNumber, pageable)
                .map(OrderConverter::fromOrder);
        OrderResponsePayload orderResponsePayload = new OrderResponsePayload();
        orderResponsePayload.setOrderResponseList(orderResponse.getContent());
        return ResponseEntity.ok(orderResponsePayload);
    }

    @GetMapping(path = "/receive")
    @ApiOperation(value = "Receive all order")
    public ResponseEntity<OrderResponsePayload> getAllOrders(
            @PageableDefault(value = 100) final Pageable pageable) {
        Page<OrderResponse> orderResponse = orderService.getAllOrders(pageable)
                .map(OrderConverter::fromOrder);
        OrderResponsePayload orderResponsePayload = new OrderResponsePayload();
        orderResponsePayload.setOrderResponseList(orderResponse.getContent());
        return ResponseEntity.ok(orderResponsePayload);
    }

    private Integer getOrderId(String orderId) throws ServiceException {
        int id ;
        try {
             id = Integer.parseInt(orderId);
        }catch (Exception e){
            throw  new ServiceException("OrderId should be number");
        }
        return id;
    }

}
