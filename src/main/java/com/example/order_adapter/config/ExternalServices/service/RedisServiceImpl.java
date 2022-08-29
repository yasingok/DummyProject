package com.example.order_adapter.config.ExternalServices.service;

import com.example.order_adapter.config.ExternalServices.dao.RedisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
    private final RedisRepositoryImpl redisRepository;

    @Override
    public String getEntry(String key) throws Exception {
        return redisRepository.getValue(key);
    }

    @Override
    public void psubscribe(JedisPubSub subscriber, String... channels) throws Exception {
        redisRepository.psubscribe(subscriber, channels);
    }
}
