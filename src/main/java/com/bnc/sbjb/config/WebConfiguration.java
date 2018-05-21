package com.bnc.sbjb.config;

import com.bnc.sbjb.metrics.HealthCheckServletContextListener;
import com.bnc.sbjb.metrics.MetricsServletContextListener;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlets.AdminServlet;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public HealthCheckRegistry healthCheckRegistry() {
        return new HealthCheckRegistry();
    }

    @Bean
    public MetricRegistry metricRegistry() {
        MetricRegistry registry = new MetricRegistry();
        // Register memory usage metrics.
        registry.register("gc", new GarbageCollectorMetricSet());
        registry.register("memory", new MemoryUsageGaugeSet());
        return registry;
    }

    @Bean
    public HealthCheckServletContextListener healthCheckServletContextListener(
        HealthCheckRegistry healthCheckRegistry) {
        return new HealthCheckServletContextListener(healthCheckRegistry);
    }

    @Bean
    public MetricsServletContextListener metricsServletContextListener(
        MetricRegistry metricRegistry) {
        return new MetricsServletContextListener(metricRegistry);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new AdminServlet(), "/1/metrics/*");
    }

    /**
     * Builds a reporter for logging metrics.
     */
    @Bean
    public Slf4jReporter slf4jReporter(MetricRegistry metricRegistry) {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
            .outputTo(LoggerFactory.getLogger(MetricRegistry.class))
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .filter((name, metric) -> name.startsWith("memory") || name.startsWith("gc"))
            .build();

        reporter.start(5, TimeUnit.MINUTES);
        return reporter;
    }
}
