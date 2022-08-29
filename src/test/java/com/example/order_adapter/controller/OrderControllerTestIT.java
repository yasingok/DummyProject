package com.example.order_adapter.controller;

import com.example.order_adapter.factory.OrderFactory;
import com.example.order_adapter.service.Order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenValidInput_thenReturn200() throws Exception {

        String urı = "/order_adapter/order/create/userIdentity";
        mockMvc.perform(post(urı).contentType(MediaType.APPLICATION_JSON).content(toJson(OrderFactory.createOrderRequestPayload())));
    }

    public static String toJson(final Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

}