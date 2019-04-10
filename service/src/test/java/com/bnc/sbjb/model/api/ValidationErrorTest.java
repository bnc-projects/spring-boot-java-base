package com.bnc.sbjb.model.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationErrorTest {

    private ValidationError validationError;

    @BeforeEach
    void setup() {
        validationError = new ValidationError("junit", "test");
    }

    @Test
    void testObjectIdempotent() {
        validationError.setObject("test-object");
        assertThat(validationError.getObject()).isEqualTo("test-object");
    }

    @Test
    void testFieldIdempotent() {
        validationError.setField("test-field");
        assertThat(validationError.getField()).isEqualTo("test-field");
    }

    @Test
    void testRejectedFieldIdempotent() {
        validationError.setRejectedValue("test-value");
        assertThat(validationError.getRejectedValue().toString()).isEqualTo("test-value");
    }

    @Test
    void testMessageIdempotent() {
        validationError.setMessage("test-message");
        assertThat(validationError.getMessage()).isEqualTo("test-message");
    }
}
