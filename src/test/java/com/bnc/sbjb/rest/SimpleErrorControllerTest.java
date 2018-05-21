package com.bnc.sbjb.rest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import org.junit.Test;

public class SimpleErrorControllerTest {

    @Test
    public void error() {
        assertThat(new SimpleErrorController().error(mock(HttpServletRequest.class)), equalTo("404"));
    }
}
