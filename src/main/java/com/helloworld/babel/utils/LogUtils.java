package com.helloworld.babel.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogUtils {

    public static Logger getLogger(Class c) {
        return LogManager.getLogger(c);
    }

}
