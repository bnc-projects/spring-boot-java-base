package com.bnc.sbjb.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;
import javax.servlet.annotation.WebListener;

@WebListener
public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

    private final HealthCheckRegistry healthCheckRegistry;

    public HealthCheckServletContextListener(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return healthCheckRegistry;
    }

}
