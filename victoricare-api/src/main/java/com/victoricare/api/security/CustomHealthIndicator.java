package com.victoricare.api.security;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Perform specific checks based on your business requirements.
        if (true /* checkSomething() */) {
            builder.up().withDetail("Item", "xxx").withDetail("error", "null");
        } else {
            builder.down().withDetail("Item", "xxx").withDetail("error", "xxxErrorCode");
        }
    }
}
