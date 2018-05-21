package com.bnc.sbjb.rest.v1;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The coordination of events is a concern. This controller provides insight as to the current
 * timing and perhaps offers an opportunity to update the time from a centralized orchestrator.
 */
@RestController
@RequestMapping(value = "/1/time")
public class TimeController {

    private final Meter requests;

    @Autowired
    public TimeController(MetricRegistry metrics) {
        requests = metrics.meter("v1.time requests");
    }

    @RequestMapping(method = RequestMethod.GET)
    public long getTime() {
        requests.mark();
        return System.currentTimeMillis();
    }

    @RequestMapping(value = "/pretty", method = RequestMethod.GET)
    public String getPrettyTime() {
        requests.mark();
        return DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String getPong() {
        requests.mark();
        return "Pong " + DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }
}
