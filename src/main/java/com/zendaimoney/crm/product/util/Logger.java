package com.zendaimoney.crm.product.util;

/**
 */
public class Logger {

    public static void debug(Class<?> c,String message){
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(c);
        logger.debug(message);
    }

    public static void info(Class<?> c,String message){
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(c);
        logger.info(message);
    }
}
