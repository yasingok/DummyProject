package com.example.order_adapter.config.ExternalServices.dao;

import redis.clients.jedis.JedisPubSub;

interface RedisRepository {
    public String getValue(String key) throws Exception;
    public void psubscribe(JedisPubSub subscriber, String ... channels) throws Exception;
}
