package com.bnc.sbjb.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ServiceConfiguration {

    private final BuildProperties buildProperties;

    private final Environment environment;

    @Autowired
    public ServiceConfiguration(final BuildProperties buildProperties, final Environment environment) {
        this.buildProperties = buildProperties;
        this.environment = environment;
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            try {
                registry.config()
                    .commonTags("application", buildProperties.getName(), "container",
                        InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                registry.config()
                    .commonTags("application", buildProperties.getName(), "container", "unknown");
            }
        };
    }
}
