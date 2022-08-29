package com.example.order_adapter.config.initializer;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import redis.clients.jedis.Jedis;

import java.time.Duration;

@TestConfiguration
public class RedisInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static Jedis jedis;
    public static int redisPort = 6379;
    private static final String REDIS_LOG_REGEX = ".*Ready to accept connections.*";
    private static final Duration CONTAINER_WAIT_TIMEOUT = Duration.ofMinutes(2);
    public static String SERVICE_NAME = "cpaas-com";

    @Container
    public static final GenericContainer<?> redisContainer;

    @Rule
    public static EnvironmentVariables env = new EnvironmentVariables();

    static {
        redisContainer = new GenericContainer<>("redis:5.0.4")
                .withExposedPorts(redisPort)
                .withStartupTimeout(CONTAINER_WAIT_TIMEOUT)
                .waitingFor(Wait.forLogMessage(REDIS_LOG_REGEX, 1));
        redisContainer.start();

        String REDIS_HOST = redisContainer.getContainerIpAddress();
        int REDIS_PORT = redisContainer.getFirstMappedPort();

        env.set("REDIS_HOST", REDIS_HOST);
        env.set("REDIS_PORT", String.valueOf(REDIS_PORT));

        // Configure clients to interact with the containers
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        Assertions.assertNotNull(jedis);
    }

    @Override
    public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {}
}
