package com.sh.service.db_dao;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;


@Slf4j
public final class SessionFactoryInitializer {

    @Getter
    private final SessionFactory sessionFactory;

    /**
     * Change this to a static method?
     * @param dbConfig
     */
    public SessionFactoryInitializer(DatabaseConfiguration dbConfig) {
        final Configuration configuration = new Configuration();
        configuration.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        configuration.setProperty(AvailableSettings.USE_SQL_COMMENTS, dbConfig.isAutoCommentsEnabled() + "");
        configuration.setProperty(AvailableSettings.USE_GET_GENERATED_KEYS, "true");
        configuration.setProperty(AvailableSettings.GENERATE_STATISTICS, "true");
        configuration.setProperty(AvailableSettings.USE_REFLECTION_OPTIMIZER, "true");
        configuration.setProperty(AvailableSettings.ORDER_UPDATES, "true");
        configuration.setProperty(AvailableSettings.ORDER_INSERTS, "true");
        configuration.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "true");
        configuration.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
        for (Map.Entry<String, String> property : dbConfig.getProperties().entrySet()) {
            configuration.setProperty(property.getKey(), property.getValue());
        }

        addAnnotatedClasses(configuration);

        final ServiceRegistry registry = new ServiceRegistryBuilder()
                .addService(ConnectionProvider.class, buildConnectionProvider(dbConfig))
                .applySettings(dbConfig.getProperties())
                .buildServiceRegistry();

        this.sessionFactory = configuration.buildSessionFactory(registry);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                sessionFactory.close();
            }
        }));
    }

    private void addAnnotatedClasses(Configuration configuration) {
        Set<Class<?>> entities = new Reflections("com.sh.service").getTypesAnnotatedWith(Entity.class);
        final SortedSet<String> entityClasses = Sets.newTreeSet();
        for (Class<?> klass : entities) {
            configuration.addAnnotatedClass(klass);
            entityClasses.add(klass.getCanonicalName());
        }
        log.info("Entity classes: {}", entityClasses);
    }

    private ConnectionProvider buildConnectionProvider(DatabaseConfiguration dbConfig) {

        final Properties properties = new Properties();
        for (Map.Entry<String, String> property : dbConfig.getProperties().entrySet()) {
            properties.setProperty(property.getKey(), property.getValue());
        }

        properties.setProperty("user", dbConfig.getUser());
        properties.setProperty("password", dbConfig.getPassword());

        final DriverManagerConnectionFactory factory = new DriverManagerConnectionFactory(
                dbConfig.getUrl(),
                properties);
        GenericObjectPool pool = buildPool(dbConfig);

        PoolableConnectionFactory connectionFactory = new PoolableConnectionFactory(factory,
                pool,
                null,
                dbConfig.getValidationQuery(),
                dbConfig.getConnectionInitializationStatements(),
                dbConfig.isDefaultReadOnly(),
                true);


        DataSource dataSource = new PoolingDataSource(connectionFactory.getPool());

        final DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(dataSource);
        connectionProvider.configure(properties);
        return connectionProvider;
    }

    private GenericObjectPool buildPool(DatabaseConfiguration configuration) {
        final GenericObjectPool pool = new GenericObjectPool(null);

        pool.setMaxWait(configuration.getMaxWaitForConnectionMs());
        pool.setMinIdle(configuration.getMinSize());
        pool.setMaxActive(configuration.getMaxSize());
        pool.setMaxIdle(configuration.getMaxSize());
        pool.setTestWhileIdle(configuration.isCheckConnectionWhileIdle());
        pool.setTimeBetweenEvictionRunsMillis(configuration.getCheckConnectionHealthWhenIdleForMs());
        pool.setMinEvictableIdleTimeMillis(configuration.getCloseConnectionIfIdleForMs());
        pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);

        return pool;
    }

}
