package com.example.order_adapter.config.ExternalServices.redisconstant;

public class RedisConfigProperties {

    private RedisConfigProperties(){}

    private static final String CONFIG_CONNECTION_PASSWORD = System.getenv("REDIS_PASSWORD");
    private static final String CONFIG_CONNECTION_PORT = System.getenv("REDIS_PORT");
    private static final String CONFIG_CONNECTION_HOST = System.getenv("REDIS_HOST");

    public static String getConfigConnectionHost() {
        return CONFIG_CONNECTION_HOST;
    }

    public static int getConfigConnectionPort() {
        return Integer.parseInt(CONFIG_CONNECTION_PORT);
    }

    public static String getConfigConnectionPassword() {
        return CONFIG_CONNECTION_PASSWORD;
    }

    public static int getConnectionTimeout() {
        return 5000;
    }
}
