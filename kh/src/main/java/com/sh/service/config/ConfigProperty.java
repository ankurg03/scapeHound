package com.sh.service.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ankurgupta.p on 31/12/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {
    String value();
    boolean jsonDeserialize() default false;
}
