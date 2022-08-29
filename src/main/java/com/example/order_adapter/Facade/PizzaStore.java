package com.example.order_adapter.Facade;

import com.example.order_adapter.Facade.facadeutil.PizzaStoreConverter;
import com.example.order_adapter.Facade.resourse.PizzaPostRequestBody;
import com.example.order_adapter.Facade.resourse.PizzaOrderResponseBody;
import com.example.order_adapter.Facade.resourse.PizzaStoreAuthRequestBody;
import com.example.order_adapter.Facade.resourse.PizzaStoreAuthResponseBody;
import com.example.order_adapter.common.OrderStatus;
import com.example.order_adapter.config.ConfigValues;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class PizzaStore {
    private static final Logger logger = LoggerFactory.getLogger(PizzaStore.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ConfigValues configValues;
    private String authorization;

    public List<PizzaOrderResponseBody> createOrder(List<PizzaPostRequestBody> pizzaPostRequestBodyList,
                                                    String userIdentity) throws JsonProcessingException, OrderException, ServiceException {
        logger.trace("Order is being created for user:{} with:{}", pizzaPostRequestBodyList.toString(), userIdentity);
        setAuthorization(getAuthorization());
        List<PizzaOrderResponseBody> pizzaOrderResponseBodyList = new ArrayList<>();
        for (PizzaPostRequestBody pizzaPostRequestBody : pizzaPostRequestBodyList) {
            ResponseEntity<PizzaOrderResponseBody> responseEntity = null;
            HttpStatus httpStatus = HttpStatus.OK;
            try {
                responseEntity = sendRequest(configValues.getOrderUrlValue(),
                        pizzaPostRequestBody, PizzaOrderResponseBody.class, true, false);
            } catch (HttpClientErrorException exception) {
                httpStatus = exception.getStatusCode();
            }

            if (HttpStatus.UNAUTHORIZED.equals(httpStatus)) {
                logger.info("UnAuthorization Failure");
                PizzaOrderResponseBody pizzaOrderResponseBody = PizzaStoreConverter.pizzaOrderResponseBody(
                        pizzaPostRequestBody, OrderStatus.FAILED, "Unexpected error occured");
                pizzaOrderResponseBodyList.add(pizzaOrderResponseBody);
            }
            if (HttpStatus.CONFLICT.equals(httpStatus)) {
                logger.debug("Order is exist");
                PizzaOrderResponseBody pizzaOrderResponseBody = PizzaStoreConverter.pizzaOrderResponseBody(
                        pizzaPostRequestBody, OrderStatus.FAILED, "order already exist for this Table_number");
                pizzaOrderResponseBodyList.add(pizzaOrderResponseBody);
            } else {
                PizzaOrderResponseBody pizzaOrderResponseBody = responseEntity.getBody();
                pizzaOrderResponseBody.setOrderStatus(OrderStatus.ACTIVE);
                pizzaOrderResponseBodyList.add(pizzaOrderResponseBody);
            }

        }

        if (pizzaOrderResponseBodyList.isEmpty()) {
            logger.error("Response list is empty");
            throw new OrderException("Error occurred When creating a order, Pls try again later");
        }
        return pizzaOrderResponseBodyList;
    }

    public PizzaOrderResponseBody[] getOrder() throws JsonProcessingException, ServiceException, OrderException {
        logger.trace("Orders are being received from PizzaStore");
        setAuthorization(getAuthorization());
        ResponseEntity<PizzaOrderResponseBody[]> responseEntity = sendRequest(configValues.getOrderUrlValue(),
                null, PizzaOrderResponseBody[].class, true, false);
        assert responseEntity != null;
        validateResponse(responseEntity);
        return responseEntity.getBody();
    }

    public void deleteOrder(String orderId,
                            String userIdentity) throws JsonProcessingException, OrderException, ServiceException {
        logger.trace("Order are being deleted for user:{} orderId:{}", orderId, userIdentity);
        setAuthorization(getAuthorization());
        sendRequest(configValues.getOrderUrlValue().concat("/").concat(orderId),
                null, Void.class, true, true);
    }

    private String getAuthorization() throws JsonProcessingException {
        logger.trace("Token is being received from Pizza Store");
        PizzaStoreAuthResponseBody pizzaStoreAuthResponseBody = sendRequest(configValues.getAuthUrlValue(),
                new PizzaStoreAuthRequestBody(configValues.getPassword(), configValues.getUserName()),
                PizzaStoreAuthResponseBody.class, false, false).getBody();
        assert pizzaStoreAuthResponseBody != null;
        return pizzaStoreAuthResponseBody.getAccessToken();
    }

    private <T, U> ResponseEntity<U> sendRequest(String url, T requestBody,
                                                 Class<U> responseType,
                                                 boolean hasToken, boolean isDelete) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (hasToken) {
            headers.setBearerAuth(getAuthorization());
        }
        String body = objectMapper.writeValueAsString(requestBody);
        logger.debug("Sending rest request to : {} with the following body : {}", url, body);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        if (isDelete) {
            restTemplate.delete(url);
            return null;
        } else
            return restTemplate.postForEntity(url, request, responseType);
    }


    private void validateResponse(ResponseEntity<?> responseEntity) throws OrderException, ServiceException {
        if (HttpStatus.UNAUTHORIZED.equals(responseEntity.getStatusCode())) {
            logger.info("UnAuthorization Failure");
            throw new ServiceException("We are unable to serve now. Pls try again later :)");
        }
        if (HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode())) {
            logger.debug("Order can not found.");
            throw new OrderException("Order can not found");
        }
        if (HttpStatus.CONFLICT.equals(responseEntity.getStatusCode())) {
            logger.debug("Order is exist");
            throw new OrderException("Order is exist");
        }
    }


}
