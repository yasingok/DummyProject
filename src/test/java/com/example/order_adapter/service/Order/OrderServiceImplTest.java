package com.example.order_adapter.service.Order;

import com.example.order_adapter.Facade.PizzaStore;
import com.example.order_adapter.Facade.facadeutil.PizzaStoreConverter;
import com.example.order_adapter.constant.TestConstant;
import com.example.order_adapter.dto.OrderDto;
import com.example.order_adapter.exceptions.CrustNotSupportedException;
import com.example.order_adapter.exceptions.FlavorNotSupportedException;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.ServiceException;
import com.example.order_adapter.exceptions.SizeNotSupportedException;
import com.example.order_adapter.exceptions.UserNotFoundException;
import com.example.order_adapter.factory.OrderFactory;
import com.example.order_adapter.factory.PizzaStoreFactory;
import com.example.order_adapter.factory.UserFactory;
import com.example.order_adapter.model.Pizza;
import com.example.order_adapter.model.PizzaCrust;
import com.example.order_adapter.model.PizzaSize;
import com.example.order_adapter.model.User;
import com.example.order_adapter.repository.OrderRepository;
import com.example.order_adapter.repository.PizzaCrustRepository;
import com.example.order_adapter.repository.PizzaRepository;
import com.example.order_adapter.repository.PizzaSizeRepository;
import com.example.order_adapter.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PizzaSizeRepository pizzaSizeRepository;
    @Mock
    PizzaCrustRepository pizzaCrustRepository;
    @Mock
    PizzaRepository pizzaRepository;
    @Mock
    PizzaStore pizzaStore;

    User user;
    Optional<User> optionalUser;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        user = UserFactory.createUser();
        optionalUser = Optional.ofNullable(user);
    }

    @Test
    void GivenIdentityNumberAndOrderDtoListWhenCreateOrderThenSuccess() throws UserNotFoundException, FlavorNotSupportedException, ServiceException, SizeNotSupportedException, JsonProcessingException, OrderException, CrustNotSupportedException {
        //Given
        Mockito.doReturn(true).when(pizzaRepository).existsByName(Mockito.anyString());
        Mockito.doReturn(true).when(pizzaSizeRepository).existsBySize(Mockito.anyString());
        Mockito.doReturn(true).when(pizzaCrustRepository).existsByCrust(Mockito.anyString());
        Mockito.doReturn(Optional.of(new Pizza(TestConstant.FLAVOR))).when(pizzaRepository)
                .findByName(Mockito.anyString());
        Mockito.doReturn(Optional.of(new PizzaSize(TestConstant.SIZE))).when(pizzaSizeRepository)
                .findBySize(Mockito.anyString());
        Mockito.doReturn(Optional.of(new PizzaCrust(TestConstant.CRUST))).when(pizzaCrustRepository)
                .findByCrust(Mockito.anyString());
        Mockito.doReturn(optionalUser).when(userRepository).findByIdentityNumber(TestConstant.IDENTITY_NUMBER);
        Mockito.doReturn(OrderFactory.savedOrdersIterable()).when(orderRepository).saveAll(Mockito.anyCollection());
        Mockito.doReturn(PizzaStoreFactory.createPizzaOrderResponseList()).when(pizzaStore)
                .createOrder(PizzaStoreConverter.fromOrder(OrderFactory.savedOrdersIterable()),
                        TestConstant.IDENTITY_NUMBER);
        //When
        orderService.createOrder(TestConstant.IDENTITY_NUMBER, OrderFactory.createSuccessOrderDtoList());
        //Then
        Mockito.verify(orderRepository, Mockito.times(2)).saveAll(Mockito.anyCollection());
        Mockito.verify(pizzaCrustRepository, Mockito.times(2)).existsByCrust(Mockito.anyString());
        Mockito.verify(pizzaSizeRepository, Mockito.times(2)).existsBySize(Mockito.anyString());
        Mockito.verify(pizzaRepository, Mockito.times(2)).existsByName(Mockito.anyString());
    }

    @Test
    void GivenIdentityNumberAndOrderDtoListIncludeUnSupportedFlavorWhenCreateOrderThenThrowFlavorNotSupportedException() {
        //Given
        Mockito.doReturn(optionalUser).when(userRepository).findByIdentityNumber(TestConstant.IDENTITY_NUMBER);
        Mockito.doReturn(false).when(pizzaRepository).existsByName(Mockito.anyString());
        OrderDto orderDto = new OrderDto();
        //When
        Executable executable = () -> orderService.createOrder(TestConstant.IDENTITY_NUMBER,
                OrderFactory.createSuccessOrderDtoList());
        //Then
        Assertions.assertThrows(FlavorNotSupportedException.class, executable);
    }

    @Test
    void GivenIdentityNumberAndOrderDtoListIncludeUnSupportedCrustWhenCreateOrderThenThrowCrustNotSupportedException() {
        //Given
        Mockito.doReturn(optionalUser).when(userRepository).findByIdentityNumber(TestConstant.IDENTITY_NUMBER);
        Mockito.doReturn(true).when(pizzaRepository).existsByName(Mockito.anyString());
        Mockito.doReturn(false).when(pizzaCrustRepository).existsByCrust(Mockito.anyString());
        //When
        Executable executable = () -> orderService.createOrder(TestConstant.IDENTITY_NUMBER,
                OrderFactory.createSuccessOrderDtoList());
        //Then
        Assertions.assertThrows(CrustNotSupportedException.class, executable);
    }

    @Test
    void GivenIdentityNumberAndOrderDtoListIncludeUnSupportedSizeWhenCreateOrderThenThrowSizeNotSupportedException() {
        //Given
        Mockito.doReturn(optionalUser).when(userRepository).findByIdentityNumber(TestConstant.IDENTITY_NUMBER);
        Mockito.doReturn(true).when(pizzaRepository).existsByName(Mockito.anyString());
        Mockito.doReturn(false).when(pizzaSizeRepository).existsBySize(Mockito.anyString());
        Mockito.doReturn(true).when(pizzaCrustRepository).existsByCrust(Mockito.anyString());
        //When
        Executable executable = () -> orderService.createOrder(TestConstant.IDENTITY_NUMBER,
                OrderFactory.createSuccessOrderDtoList());
        //Then
        Assertions.assertThrows(SizeNotSupportedException.class, executable);
    }

    @Test
    void GivenIdentityIsInvalidNumberAndOrderDtoListWhenCreateOrderThenThrowUserNotFoundException() {
        //When
        Executable executable = () -> orderService.createOrder(TestConstant.IDENTITY_NUMBER,
                OrderFactory.createSuccessOrderDtoList());
        //Then
        Assertions.assertThrows(UserNotFoundException.class, executable);
    }


}