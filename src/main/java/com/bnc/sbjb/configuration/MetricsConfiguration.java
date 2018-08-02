package com.bnc.sbjb.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MetricsConfiguration.class);

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(BuildProperties buildProperties) {
        return registry -> {
            try {
                registry.config()
                    .commonTags("application", buildProperties.getName(), "container",
                        InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException ex) {
                logger.warn("Could not get hostname for metrics");
                registry.config()
                    .commonTags("application", buildProperties.getName());
            }
        };
    }
}
