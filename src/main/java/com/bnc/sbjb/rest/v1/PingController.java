package com.bnc.sbjb.rest.v1;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provide an application specific ping response, based on exchange load status.
 */
@RestController
@RequestMapping(value = "/1/ping")
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);
    private static final AtomicBoolean first = new AtomicBoolean(true);

    private final Meter requests;

    @Autowired
    public PingController(MetricRegistry metrics) {
        requests = metrics.meter("v1.ping requests");
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPong() {
        requests.mark();

        logFirstPingResponse();

        return "pong " + DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    private void logFirstPingResponse() {
        // Check if this is the first response, if so turn off pong logging.
        if (first.getAndSet(false)) {
            logger.info("Returning pong. Service is ready.");
        }
    }
}
