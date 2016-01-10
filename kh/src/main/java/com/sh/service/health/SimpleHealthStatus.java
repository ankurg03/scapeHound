package com.sh.service.health;

import lombok.RequiredArgsConstructor;

/**
 * Created by ankurgupta.p on 31/12/15.
 */
@RequiredArgsConstructor(staticName = "of")
public class SimpleHealthStatus implements HealthStatus {

    private final boolean healthy;
    private final String message;

    @Override
    public boolean isHealthy() {
        return healthy;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "\t" + message + ": " + healthy;
    }
}
