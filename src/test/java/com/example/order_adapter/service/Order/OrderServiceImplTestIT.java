package com.example.order_adapter.service.Order;

import com.example.order_adapter.Facade.PizzaStore;
import com.example.order_adapter.config.initializer.RedisInitializer;
import com.example.order_adapter.constant.TestConstant;
import com.example.order_adapter.exceptions.CrustNotSupportedException;
import com.example.order_adapter.exceptions.FlavorNotSupportedException;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.ServiceException;
import com.example.order_adapter.exceptions.SizeNotSupportedException;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.factory.OrderFactory;
import com.example.order_adapter.model.User;
import com.example.order_adapter.repository.OrderRepository;
import com.example.order_adapter.repository.PizzaCrustRepository;
import com.example.order_adapter.repository.PizzaRepository;
import com.example.order_adapter.repository.PizzaSizeRepository;
import com.example.order_adapter.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ContextConfiguration(initializers = { RedisInitializer.class})
class OrderServiceImplTestIT {

    private final OrderServiceImpl orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PizzaSizeRepository pizzaSizeRepository;
    private final PizzaCrustRepository pizzaCrustRepository;
    private final PizzaRepository pizzaRepository;
    private final PizzaStore pizzaStore;

    @Autowired
    OrderServiceImplTestIT(OrderServiceImpl orderService, OrderRepository orderRepository, UserRepository userRepository,
                           PizzaSizeRepository pizzaSizeRepository, PizzaCrustRepository pizzaCrustRepository,
                           PizzaRepository pizzaRepository, PizzaStore pizzaStore) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.pizzaSizeRepository = pizzaSizeRepository;
        this.pizzaCrustRepository = pizzaCrustRepository;
        this.pizzaRepository = pizzaRepository;
        this.pizzaStore = pizzaStore;
    }

    @BeforeEach
    void beforeEach() throws Exception {
        createOrder();
    }

    @Transactional
    void createOrder() throws Exception {
        userRepository.save(createUser());

    }

    @Test
    void givenSuccessful_whenGetProjectConfig_thenStatusIsNoContent() throws UserNotFoundException, FlavorNotSupportedException, ServiceException, SizeNotSupportedException, JsonProcessingException, OrderException, CrustNotSupportedException {
        orderService.createOrder(TestConstant.IDENTITY_NUMBER, OrderFactory.createSuccessOrderDtoList());
    }

    private User createUser(){
        User user = new User();
        user.setIdentityNumber(TestConstant.IDENTITY_NUMBER);
        user.setFirstName(TestConstant.FIRST_NAME);
        user.setLastName(TestConstant.LAST_NAME);
        user.setPhoneNumber(TestConstant.PHONE_NUMBER);
        user.setEmail(TestConstant.EMAIL);
        return user;
    }

}