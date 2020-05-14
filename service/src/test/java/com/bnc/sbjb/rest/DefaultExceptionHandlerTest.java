package com.bnc.sbjb.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

class DefaultExceptionHandlerTest {

    @Test
    void testHandleExceptionWithBigQueryString() {
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getQueryString()).thenReturn(
            "This is a string with a lot of text and several extra whitespaces\n"
                + "This is a string with a lot of text and several extra whitespaces\n"
                + "This is a string with a lot of text and several extra whitespaces\n"
                + "This is a string with a lot of text and several extra whitespaces\n"
                + "This is a string with a lot of text and several extra whitespaces\n");
        new DefaultExceptionHandler().handleException(mockRequest, new Exception("Just testing"));
        // Just to include a test.
        verify(mockRequest).getQueryString();
        verifyNoMoreInteractions(mockRequest);
    }

    @Test
    void testHandleExceptionWithLittleQueryString() {
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getQueryString()).thenReturn("\n");
        new DefaultExceptionHandler().handleException(mockRequest, new Exception("Just testing"));
        // Just to include a test.
        verify(mockRequest).getQueryString();
        verifyNoMoreInteractions(mockRequest);
    }

    @Test
    void testHandleExceptionWithNoQueryString() {
        final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getQueryString()).thenReturn(null);
        new DefaultExceptionHandler().handleException(mockRequest, new Exception("Just testing"));
        // Just to include a test.
        verify(mockRequest).getQueryString();
    }
}
