package com.bnc.sbjb.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Nothing served from this application server should be cached. Ever.
 */
@Component
public class NoCacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        // Intercept response header.
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Expires", "0");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        chain.doFilter(request, resp);
    }
}
