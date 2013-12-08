package com.ilirium.grizzly.example.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LogFilter implements Filter
{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogFilter.class);

    public void init(FilterConfig fc) throws ServletException
    {
        log.info("init ...");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException
    {
        long start = System.nanoTime();
        log.info(">> doFilter");
        fc.doFilter(request, response);
        log.info("<< doFilter, execution time [{}ms]", ((System.nanoTime() - start) / 1000000));
    }

    public void destroy()
    {
        log.info("destroy ...");
    }
}
