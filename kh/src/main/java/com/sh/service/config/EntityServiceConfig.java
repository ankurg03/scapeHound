package com.sh.service.config;

/**
 * Created by ankurgupta.p on 30/12/15.
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityServiceConfig extends Configuration {

    @JsonProperty
    private String dbUrl = "jdbc:mysql://localhost:3306/sh";
    @JsonProperty
    private String dbUser = "root";
    @JsonProperty
    private String dbPassword = "";

}
