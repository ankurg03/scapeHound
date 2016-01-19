package com.sh.service.health;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ankurgupta.p on 31/12/15.
 */
public class CombinedHealthStatus implements HealthStatus {

    private final List<HealthStatus> healths = new LinkedList<HealthStatus>();
    private final String healthName;
    private Optional<Boolean> healthOverride = Optional.absent();

    public CombinedHealthStatus(String healthName) {
        this.healthName = healthName;
    }

    @Override
    public boolean isHealthy() {
        return this.healthOverride.or(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                boolean healthy = true;
                for (HealthStatus health : healths)
                    healthy &= health.isHealthy();
                return healthy;
            }
        });
    }

    public void setHealthOverride(Boolean health) {
        this.healthOverride = Optional.of(health);
    }

    @Override
    public String message() {
        Collections.sort(healths, new Comparator<HealthStatus>() {
            @Override
            public int compare(HealthStatus o1, HealthStatus o2) {
                return o1.isHealthy() == o2.isHealthy()
                        ? o1.message().compareTo(o2.message())
                        : (o1.isHealthy() ? 1 : -1);
            }
        });

        return
            "  " + this.healthName + "\n" + Joiner.on("\n\t").join(healths);
    }

    public CombinedHealthStatus addHealth(HealthStatus health) {
        this.healths.add(health);
        return this;
    }

    public String toString() {
        return message();
    }
}
