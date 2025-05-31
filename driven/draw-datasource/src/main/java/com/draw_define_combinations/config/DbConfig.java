package com.draw_define_combinations.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(basePackages = "com.drawevaluateprobabilities.repositories")
@ComponentScan(basePackages = "com.drawevaluateprobabilities.mappers")
public class DbConfig {

    @Autowired
    private Environment env;

    //@Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // El entorno se lee en función de las clases contenidas en esta, y la clase se "elige" según lo definido en
        // el /resources/application.properties.
        // Ejemplo. En application.properties se indica spring.profiles.default=h2:
        // Se utiliza la clase DbConfig.H2Config, en la que se define @PropertySource("classpath:persistence-h2.properties")
        // Se utiliza la configuración persistence-h2.properties, definida en el tag anterior
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.drivefilereader.models" });
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        }
        if (env.getProperty("hibernate.dialect") != null) {
            hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        }
        if (env.getProperty("hibernate.show_sql") != null) {
            hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        }
        return hibernateProperties;
    }

}

@Configuration
@Profile("h2")
@PropertySource("classpath:persistence-h2.properties")
class H2Config {
}

@Configuration
@Profile("hsqldb")
@PropertySource("classpath:persistence-hsqldb.properties")
class HsqldbConfig {
}

@Configuration
@Profile("derby")
@PropertySource("classpath:persistence-derby.properties")
class DerbyConfig {
}

@Configuration
@Profile("sqlite")
@PropertySource("classpath:persistence-sqlite.properties")
class SqliteConfig {
}