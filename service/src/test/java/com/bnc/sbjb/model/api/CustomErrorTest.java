package com.bnc.sbjb.model.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CustomErrorTest {

    private CustomError customError;

    @BeforeEach
    void setup() {
        customError = new CustomError(HttpStatus.NOT_FOUND, "not found");
    }

    @Test
    void testHttpStatusIdempotent() {
        customError.setStatus(HttpStatus.CONFLICT);
        assertThat(customError.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testTimestampIdempotent() {
        Instant now = Instant.now();
        customError.setTimestamp(now);
        assertThat(customError.getTimestamp()).isEqualTo(now);
    }

    @Test
    void testMessageIdempotent() {
        customError.setMessage("test-message");
        assertThat(customError.getMessage()).isEqualTo("test-message");
    }

    @Test
    void testSubErrorIdempotent() {
        List<ValidationError> errors = Collections.singletonList(new ValidationError("test", "test"));
        customError.setSubErrors(errors);
        assertThat(customError.getSubErrors().size()).isEqualTo(1);
    }
}
