package com.bnc.sbjb.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;
import javax.servlet.annotation.WebListener;

@WebListener
public class MetricsServletContextListener extends MetricsServlet.ContextListener {

    private final MetricRegistry metricRegistry;

    public MetricsServletContextListener(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    protected MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

}
