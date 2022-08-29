package com.example.order_adapter.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;


@Configuration
public class DatabaseConfig {

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }

    @Bean("liquibase")
    public SpringLiquibase productionLiquibase() {
        return springLiquibase(productionDataSource(), LiquibaseProperties());
    }

    @Bean
    @ConfigurationProperties("spring.datasource.liquibase")
    public LiquibaseProperties LiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public DataSource productionDataSource() {
        return productionDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties productionDataSourceProperties() {
        return new DataSourceProperties();
    }
}
