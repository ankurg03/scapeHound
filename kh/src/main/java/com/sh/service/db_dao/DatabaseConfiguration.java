package com.sh.service.db_dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sh.service.config.ConfigProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by ankurgupta.p on 31/12/15.
 * Dropwizard's DatabaseConfiguration.
 */
@EqualsAndHashCode
@ToString
public class DatabaseConfiguration {
    @NotNull
    @ConfigProperty("db.driver.class")
    private String driverClass = "com.mysql.jdbc.Driver";

    @NotNull
    @ConfigProperty("db.user")
    private String user = "root";

    @ConfigProperty("db.password")
    private String password = "";

    @NotNull
    @ConfigProperty("db.url")
    private String url = "jdbc:mysql://localhost:3306/sh";

    @NotNull
    //@ConfigProperty("db.properties") // TODO
    private ImmutableMap<String, String> properties = ImmutableMap.of();

    @NotNull
    @ConfigProperty("db.max.wait.for.connect.ms")
    @Getter
    @Setter
    private int maxWaitForConnectionMs = 1000;

    @NotNull
    @ConfigProperty("db.validation.query")
    private String validationQuery = "/* Health Check */ SELECT 1";

    @Min(1)
    @Max(1024)
    @ConfigProperty("db.min.size")
    private int minSize = 1;

    @Min(1)
    @Max(1024)
    @ConfigProperty("db.max.size")
    private int maxSize = 8;

    @ConfigProperty("db.check.connection.while.idle")
    private boolean checkConnectionWhileIdle = false;

    @NotNull
    @ConfigProperty("db.check.connection.health.when.idle.for.ms")
    @Getter @Setter
    private int checkConnectionHealthWhenIdleForMs = 10*1000;

    @NotNull
    @ConfigProperty("db.close.connection.if.idle.for.ms")
    @Getter @Setter
    private int closeConnectionIfIdleForMs = 60*1000;

    @ConfigProperty("db.default.read.only")
    private boolean defaultReadOnly = false;

    @ConfigProperty("db.connection.initialization.statements")
    private ImmutableList<String> connectionInitializationStatements = ImmutableList.of();

    @ConfigProperty("db.auto.comments.enabled")
    private boolean autoCommentsEnabled = true;

    public boolean isAutoCommentsEnabled() {
        return autoCommentsEnabled;
    }

    public void setAutoCommentsEnabled(boolean autoCommentsEnabled) {
        this.autoCommentsEnabled = autoCommentsEnabled;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImmutableMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = ImmutableMap.copyOf(properties);
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isCheckConnectionWhileIdle() {
        return checkConnectionWhileIdle;
    }

    public void setCheckConnectionWhileIdle(boolean checkConnectionWhileIdle) {
        this.checkConnectionWhileIdle = checkConnectionWhileIdle;
    }

    public boolean isDefaultReadOnly() {
        return defaultReadOnly;
    }

    public void setDefaultReadOnly(boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }

    public ImmutableList<String> getConnectionInitializationStatements() {
        return connectionInitializationStatements;
    }

    public void setConnectionInitializationStatements(List<String> statements) {
        this.connectionInitializationStatements = ImmutableList.copyOf(statements);
    }

}
