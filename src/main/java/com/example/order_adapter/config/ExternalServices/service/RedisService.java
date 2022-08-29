package com.example.order_adapter.config.ExternalServices.service;

import redis.clients.jedis.JedisPubSub;

interface RedisService {
    public String getEntry(String key) throws Exception;
    public void psubscribe(JedisPubSub subscriber, String... channels) throws Exception;
}
