package com.newsnow.imagewrapper.config;

import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.newsnow.imagewrapper.repository.generated.tables"})
@EnableTransactionManagement
public class DataBaseConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(dataSource);
        jooqConfiguration.set(SQLDialect.POSTGRES);

        return jooqConfiguration;
    }

}
