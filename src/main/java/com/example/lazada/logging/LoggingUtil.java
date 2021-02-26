/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.lazada.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.text.MessageFormat;

/**
 * @author waichun
 * Logging is important to monitor system performance and debug
 */
@Slf4j
public class LoggingUtil {

    private static Logger unWrapperLogger(Logger logger) {
        Logger result = logger;
        return result;
    }

    public static void debug(Logger logger, String message) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isDebugEnabled()) {
            unwrapperLogger.debug(message);
        }
    }

    public static void debug(Logger logger, String template, Object... messages) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isDebugEnabled()) {
            unwrapperLogger.debug(MessageFormat.format(template, messages));
        }
    }

    public static void info(Logger logger, String message) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isInfoEnabled()) {
            unwrapperLogger.info(message);
        }
    }

    public static void info(Logger logger, String template, Object... messages) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isInfoEnabled()) {
            unwrapperLogger.info(MessageFormat.format(template, messages));
        }
    }

    public static void warn(Logger logger, String message) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isWarnEnabled()) {
            unwrapperLogger.warn(message);
        }
    }

    public static void warn(Logger logger, String template, Object... messages) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isWarnEnabled()) {
            unwrapperLogger.warn(template, messages);
        }
    }

    public static void error(Logger logger, String message) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isErrorEnabled()) {
            unwrapperLogger.error(message);
        }
    }

    public static void error(Logger logger, String template, Object... messages) {
        Logger unwrapperLogger = unWrapperLogger(logger);
        if (unwrapperLogger.isErrorEnabled()) {
            unwrapperLogger.error(template, messages);
        }
    }

}

