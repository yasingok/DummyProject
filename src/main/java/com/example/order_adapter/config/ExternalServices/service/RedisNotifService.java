package com.example.order_adapter.config.ExternalServices.service;

import com.example.order_adapter.config.ConfigValues;
import com.example.order_adapter.config.ExternalServices.dao.JedisConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class RedisNotifService extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisNotifService.class);
    public static final String REDIS_KEYSPACE = "__keyspace@0__:*";

    private final RedisServiceImpl redisService;
    private final ConfigValues configValues;

    @PostConstruct
    public void construct(){
        try {
            redisService.psubscribe(RedisNotifService.this, REDIS_KEYSPACE);
        } catch (Exception e) {
            logger.error("Subscription to Redis has been failed");
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        logger.info("Redis channel: {} has been updated", channel);
        configValues.updateValues(channel.replace("__keyspace@0__:",""));
    }
}
