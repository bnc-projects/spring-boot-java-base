package com.bnc.sbjb.metrics;

import static io.micrometer.core.instrument.config.MeterFilterReply.ACCEPT;
import static io.micrometer.core.instrument.config.MeterFilterReply.DENY;
import static io.micrometer.core.instrument.config.MeterFilterReply.NEUTRAL;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfiguration {

    private static final List<String> DESIRED = List.of(
        "jvm.memory.max", "jvm.memory.used"
    );
    private static final List<String> JVM_MAX_TAGS = List.of(
        "G1 Old Gen", "Tenured Gen"
    );
    private static final List<String> JVM_TAGS = List.of(
        "G1 Old Gen", "G1 Eden Space", "Tenured Gen", "Eden Space"
    );

    @Bean
    public MeterFilter meterFilter() {
        return new MeterFilter() {
            @Nonnull
            @Override
            public MeterFilterReply accept(@Nonnull Meter.Id id) {
                final String name = id.getName();

                if ("jvm.memory.max".equals(name) && "heap".equals(id.getTag("area"))) {
                    return JVM_MAX_TAGS.contains(id.getTag("id")) ? ACCEPT : DENY;
                }
                if ("jvm.threads.live".equals(name)) {
                    return ACCEPT;
                }
                if ("process.cpu.usage".equals(name)) {
                    return ACCEPT;
                }
                if (name.startsWith("jvm.")) {
                    return DESIRED.contains(name) && "heap".equals(id.getTag("area")) && JVM_TAGS.contains(id.getTag("id")) ? ACCEPT
                        : DENY;
                }
                if (name.startsWith("system.")) {
                    return DENY;
                }
                return NEUTRAL;
            }
        };
    }
}
