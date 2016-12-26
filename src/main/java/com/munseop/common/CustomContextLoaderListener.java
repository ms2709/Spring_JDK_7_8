package com.munseop.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

/**
 * Created by 심문섭 on 2016-12-23.
 */
public class CustomContextLoaderListener extends ContextLoaderListener {
    private static final Logger logger = LoggerFactory.getLogger( ContextLoaderListener.class );

    public CustomContextLoaderListener() throws Exception {
        logger.info( "Starting application..." );

    }
}