package com.bnc.sbjb.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MetricsConfiguration.class);

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${spring.application.name") String name) {
        return registry -> {
            try {
                registry.config().commonTags("application", name, "container", InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException ex) {
                logger.warn("Could not get hostname for metrics");
                registry.config().commonTags("application", name, "container", "unknown");
            }
        };
    }
}
