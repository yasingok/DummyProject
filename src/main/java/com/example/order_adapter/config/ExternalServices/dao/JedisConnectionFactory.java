package com.example.order_adapter.config.ExternalServices.dao;

import com.example.order_adapter.config.ExternalServices.redisconstant.RedisConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConnectionFactory {

    @Bean
    public JedisPool createJedisPool(){
        return new JedisPool(new JedisPoolConfig(),
                RedisConfigProperties.getConfigConnectionHost(), RedisConfigProperties.getConfigConnectionPort(),
                RedisConfigProperties.getConnectionTimeout());
    }
}
