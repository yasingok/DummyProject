package com.example.order_adapter.config.ExternalServices.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Component
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private static final Logger logger = LoggerFactory.getLogger(RedisRepositoryImpl.class);
    private final JedisPool jedisPool;

    @Override
    public String getValue(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("Unexpected Error Occured. error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        } finally {
            releaseJedis(jedis);
        }
    }

    @Override
    public void psubscribe(JedisPubSub subscriber, String ... channels) throws Exception {
        logger.info("Subscribing to channel : {}", channels);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = getJedis();
                    jedis.psubscribe(subscriber, channels);
                    releaseJedis(jedis);
                }
            }).start();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private Jedis getJedis() {
        logger.debug(
                "Get new jedis resource from pool. Current pool stats: Active= {} Idle= {} Waiters= {} ",
                jedisPool.getNumActive(), jedisPool.getNumIdle(), jedisPool.getNumWaiters());
        return jedisPool.getResource();
    }

    private void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
