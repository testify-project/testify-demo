package org.testifyproject.fixture;

import static org.hibernate.cfg.AvailableSettings.DATASOURCE;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class TestModule {

    @Primary
    @Bean
    DataSource testDataSource(
        @Qualifier("resource:/postgres:9.4/resource") InetAddress inetAddress) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(inetAddress.getHostAddress());
        dataSource.setPortNumber(5432);

        //Default postgres image database name, user and postword
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean testEntityManagerFactory(
        DataSource dataSource,
        ApplicationContext applicationContext) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);
        properties.put("hibernate.ejb.entitymanager_factory_name", applicationContext.getId());

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPersistenceUnitName("test.example.greeter");
        bean.setJpaPropertyMap(properties);

        return bean;
    }

}
