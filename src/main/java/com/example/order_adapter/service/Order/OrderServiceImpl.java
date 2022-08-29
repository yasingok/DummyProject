package com.example.order_adapter.service.Order;

import com.example.order_adapter.Facade.PizzaStore;
import com.example.order_adapter.Facade.facadeutil.PizzaStoreConverter;
import com.example.order_adapter.Facade.resourse.PizzaOrderResponseBody;
import com.example.order_adapter.common.OrderStatus;
import com.example.order_adapter.dto.OrderDto;
import com.example.order_adapter.exceptions.CrustNotSupportedException;
import com.example.order_adapter.exceptions.FlavorNotSupportedException;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.ServiceException;
import com.example.order_adapter.exceptions.SizeNotSupportedException;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.model.Order;
import com.example.order_adapter.model.User;
import com.example.order_adapter.repository.OrderRepository;
import com.example.order_adapter.repository.PizzaCrustRepository;
import com.example.order_adapter.repository.PizzaRepository;
import com.example.order_adapter.repository.PizzaSizeRepository;
import com.example.order_adapter.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String USER_NOT_FOUND = "User can not be found. UserIdentity:{}";
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PizzaSizeRepository pizzaSizeRepository;
    private final PizzaCrustRepository pizzaCrustRepository;
    private final PizzaRepository pizzaRepository;
    private final PizzaStore pizzaStore;
    private static int value = 0;

    @Override
    @Transactional()
    public List<PizzaOrderResponseBody> createOrder(String identityNumber,
                             List<OrderDto> orderDtoList) throws UserNotFoundException, FlavorNotSupportedException, SizeNotSupportedException, CrustNotSupportedException, OrderException, ServiceException, JsonProcessingException {
        User user = isValidUser(identityNumber);
        isValidOrder(orderDtoList);
        List<Order> orderList = convertDtoToModel(orderDtoList, user);
        Iterable<Order> savedOrders = orderRepository.saveAll(orderList);

        List<PizzaOrderResponseBody> pizzaOrderResponseBodyList = pizzaStore.createOrder(PizzaStoreConverter.fromOrder(savedOrders), identityNumber);

        orderRepository.saveAll(updateOrderStatus(pizzaOrderResponseBodyList, savedOrders));

        logger.trace("Order has been created. with: {}", orderList);
        return pizzaOrderResponseBodyList;
    }

    private  List<Order> updateOrderStatus(List<PizzaOrderResponseBody> pizzaOrderResponseBodyList, Iterable<Order> savedOrders){
        pizzaOrderResponseBodyList.forEach(pizzaOrderResponseBody -> {
            for (Order order : savedOrders){
                if (order.getId().equals(pizzaOrderResponseBody.getTableNo()) ){
                    if (pizzaOrderResponseBody.getOrderStatus().equals(OrderStatus.FAILED)) {
                        order.setStatus(pizzaOrderResponseBody.getOrderStatus());
                    }
                    else{
                        order.setOrderId(pizzaOrderResponseBody.getOrderId());
                    }
                }
            }
        });
        List<Order> result = new ArrayList<>();
        savedOrders.forEach(result::add);
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteOrder(String identityNumber, int orderId) throws UserNotFoundException, OrderException, ServiceException, JsonProcessingException {
        isValidUser(identityNumber);
        checkOrderBelongsUser(identityNumber, orderId);
        orderRepository.softDeleteByOrderId(OrderStatus.CANCELLED.name(), orderId);
        pizzaStore.deleteOrder(orderId+"", identityNumber);
    }

    @Override
    @Transactional
    public Page<Order> getAllOrdersForUser(String identityNumber, Pageable pageable) throws UserNotFoundException {
        isValidUser(identityNumber);
        return orderRepository.findByIdentityNumberAndStatus(identityNumber, OrderStatus.ACTIVE.name(), pageable);
    }

    @Override
    @Transactional
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderForUser(String identityNumber, int orderId) throws UserNotFoundException, OrderException {
        isValidUser(identityNumber);
        checkOrderBelongsUser(identityNumber, orderId);

        Optional<Order> optionalOrder = orderRepository.findByIdentityNumberAndOrderIdAndStatus(identityNumber, OrderStatus.ACTIVE.name(), orderId);

        if (!optionalOrder.isPresent()){
            logger.debug("Order can not found. orderId:{}, identityNumber:{}", orderId, identityNumber);
            throw new OrderException(String.format("Order can not found. orderId:%s, identityNumber:%s", orderId, identityNumber));
        }
        return optionalOrder.get();
    }

    private void isValidOrder(
            List<OrderDto> orderDtoList) throws FlavorNotSupportedException, CrustNotSupportedException, SizeNotSupportedException {
        for (OrderDto orderDto:orderDtoList){
            if (!pizzaRepository.existsByName(orderDto.getFlavor())) {
                logger.debug("Pizza:{} is not supported.", orderDto.getFlavor());
                throw new FlavorNotSupportedException(String.format("Pizza:%s is not supported.", orderDto.getFlavor()));
            }
            if (!pizzaCrustRepository.existsByCrust(orderDto.getCrust())) {
                logger.debug("Crust:{} is not supported.", orderDto.getCrust());
                throw new CrustNotSupportedException(String.format("Crust:%s is not supported.", orderDto.getCrust()));
            }
            if (!pizzaSizeRepository.existsBySize(orderDto.getSize())) {
                logger.debug("Size:{} is not supported.", orderDto.getSize());
                throw new SizeNotSupportedException(String.format("Size:%s is not supported.", orderDto.getSize()));
            }

        }
    }

    private User isValidUser(String identityNumber) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByIdentityNumber(identityNumber);
        if (!userOptional.isPresent()) {
            logger.debug(USER_NOT_FOUND, identityNumber);
            throw new UserNotFoundException(String.format("User can not be found. UserIdentity:%s ", identityNumber));
        }
        return userOptional.get();
    }

    private List<Order> convertDtoToModel(List<OrderDto> orderDtoList, User user) {
        List<Order> orderList = new ArrayList<>();
        for (OrderDto orderDto : orderDtoList){
            Order order = new Order();
            order.setName(pizzaRepository.findByName(orderDto.getFlavor()).get());
            order.setSize(pizzaSizeRepository.findBySize(orderDto.getSize()).get());
            order.setCrust(pizzaCrustRepository.findByCrust(orderDto.getCrust()).get());
            order.setUser(user);
            order.setStatus(OrderStatus.ACTIVE);
            orderList.add(order);
        }
        return orderList;
    }

    private void checkOrderBelongsUser(String identityNumber, int orderId) throws OrderException {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);

        if (!optionalOrder.isPresent()){
            logger.debug("Order can not found. orderId:{}, identityNumber:{}", orderId, identityNumber);
            throw new OrderException(String.format("Order can not found. orderId:%s, identityNumber:%s", orderId, identityNumber));
        }
        Order order = optionalOrder.get();
        if (!order.getUser().getIdentityNumber().equals(identityNumber)){
            logger.debug("Order do not belongs to this user. orderId:{}, identityNumber:{}", orderId, identityNumber);
            throw new OrderException(String.format("Order do not belongs to this user. orderId:%s, identityNumber:%s", orderId, identityNumber));
        }
    }

}
