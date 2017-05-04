package com.stjepano.website;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;

/**
 * RequestContextListener ...
 */
@Configuration
@WebListener
public class MyRequestContextListener extends RequestContextListener {
    private static final Logger logger = LoggerFactory.getLogger(MyRequestContextListener.class);
    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        super.requestInitialized(requestEvent);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        super.requestDestroyed(requestEvent);
    }
}
