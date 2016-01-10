package com.sh.service;

import com.sh.service.config.EntityServiceConfig;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by ankurgupta.p on 30/12/15.
 */
public class EntityDefService extends Application<EntityServiceConfig> {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            args = new String[]{"server", "src/main/resources/config.yml"};
        }
        new EntityDefService().run(args);
    }

    @Override
    public String getName() {
        return "schema-service";
    }

    @Override
    public void initialize(Bootstrap<EntityServiceConfig> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(EntityServiceConfig configuration,
                    Environment environment) {
        System.out.println(configuration);
        try {
            environment.jersey().register(new EntityResource(configuration));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        environment.jersey().register(new FileUploadApi());
//        environment.jersey().register(new BulkMigrationRun(configuration));
//        environment.jersey().register(MultiPartFeature.class);
//        environment.jersey().register(new BulkMigrationResource(configuration));
    }
}
