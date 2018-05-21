package com.bnc.sbjb.rest.v1;

import com.bnc.sbjb.domain.v1.ApplicationInfo;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/1/info")
public class InfoController {

    private final Meter requests;

    @Autowired
    public InfoController(MetricRegistry metrics) {
        requests = metrics.meter("v1.info requests");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApplicationInfo getInfo() throws IOException {
        requests.mark();

        Properties properties = getApplicationInfoProperties();

        return new ApplicationInfo(
            properties.getProperty("commit"),
            properties.getProperty("timestamp"),
            properties.getProperty("version"),
            properties.getProperty("branch"),
            properties.getProperty("timestampUtc"));
    }

    private Properties getApplicationInfoProperties() throws IOException {
        try (InputStream inputStream = InfoController.class.getResourceAsStream("/version.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        }
    }
}
