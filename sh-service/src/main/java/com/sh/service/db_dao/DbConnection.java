package com.sh.service.db_dao;

import com.sh.service.config.EntityServiceConfig;
import lombok.Getter;
import org.hibernate.SessionFactory;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ankurgupta.p on 31/12/15.
 */

public class DbConnection {

    @Getter
    private SessionFactory sessionFactory =null;


    public DbConnection(EntityServiceConfig configuration) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        configuration.getDbUrl();
        if(sessionFactory == null) {
            DatabaseConfiguration dbInstance = DatabaseConfiguration.class.getConstructor().newInstance();
            dbInstance.setUrl(configuration.getDbUrl());
            dbInstance.setUser(configuration.getDbUser());
            dbInstance.setPassword(configuration.getDbPassword());
            sessionFactory = new SessionFactoryInitializer(dbInstance).getSessionFactory();
        }

    }

}
