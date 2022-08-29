package com.example.order_adapter.config;

import com.example.order_adapter.config.ExternalServices.service.RedisServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Getter
public class ConfigValues {
    private static final Logger logger = LoggerFactory.getLogger(ConfigValues.class);
    private static final String VALUE_ERROR = "{}: value can not get from Redis. Default value is:{}";
    private static final  String AUTH_URL_KEY ="auth_url";
    private static final  String ORDER_URL_KEY="order_url";
    private static final  String NAME_KEY="name";
    private static final  String PASSWORD_KEY="password";

    public static final String REDIS_KEYSPACE = "__keyspace@0__:";

    private final RedisServiceImpl redisService;

    String authUrlValue = "https://order-pizza-api.herokuapp.com/api/auth";    //default Value
    String orderUrlValue = "https://order-pizza-api.herokuapp.com/api/orders";
    String userName = "test";
    String password = "test";

    String[] channels = {REDIS_KEYSPACE.concat(AUTH_URL_KEY)};

    @PostConstruct
    public void construct(){
        setAuthUrlValue();
        setOrderUrlValue();
        setUserName();
        setPassword();
    }

    private void setPassword() {
        try {
            this.password = !StringUtils.isEmpty(redisService.getEntry(PASSWORD_KEY)) ? redisService.getEntry(PASSWORD_KEY): this.password;
        } catch (Exception e) {
            logger.error(VALUE_ERROR, PASSWORD_KEY, getAuthUrlValue());
        }
    }

    private void setUserName() {
        try {
            this.userName = !StringUtils.isEmpty(redisService.getEntry(NAME_KEY)) ? redisService.getEntry(NAME_KEY): this.userName;
        } catch (Exception e) {
            logger.error(VALUE_ERROR, NAME_KEY, getAuthUrlValue());
        }
    }

    private void setOrderUrlValue() {
        try {
            this.orderUrlValue = !StringUtils.isEmpty(redisService.getEntry(ORDER_URL_KEY)) ? redisService.getEntry(ORDER_URL_KEY): this.orderUrlValue;
        } catch (Exception e) {
            logger.error(VALUE_ERROR, ORDER_URL_KEY, getAuthUrlValue());
        }
    }

    public void setAuthUrlValue() {
        try {
            this.authUrlValue = !StringUtils.isEmpty(redisService.getEntry(AUTH_URL_KEY)) ? redisService.getEntry(AUTH_URL_KEY): this.authUrlValue;
        } catch (Exception e) {
            logger.error(VALUE_ERROR, AUTH_URL_KEY, getAuthUrlValue());
        }
    }

    public void updateValues(String key){
        logger.info("Update key:{}", key);
        switch (key){
            case AUTH_URL_KEY:
                setAuthUrlValue();
                break;
            case ORDER_URL_KEY:
                setOrderUrlValue();
                break;
            case PASSWORD_KEY:
                setPassword();
                break;
            case NAME_KEY:
                setUserName();
                break;
            default:
                logger.warn("Key:{} can not found", key);
                break;
        }
    }
}
