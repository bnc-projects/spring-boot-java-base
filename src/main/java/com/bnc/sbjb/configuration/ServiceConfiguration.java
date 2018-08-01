package com.bnc.sbjb.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final BuildProperties buildProperties;

    @Autowired
    public ServiceConfiguration(final BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            try {
                registry.config()
                    .commonTags("application", buildProperties.getName(), "container",
                        InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException ex) {
                registry.config()
                    .commonTags("application", buildProperties.getName(), "container", "unknown");
            }
        };
    }
}
