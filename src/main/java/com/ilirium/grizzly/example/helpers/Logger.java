package com.ilirium.grizzly.example.helpers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public class Logger
{
    public static void initialize()
    {
        // ROOT LOGGER
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();
        loggerContext.reset();
        
        // PATERN
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d [%thread] %highlight(%-5level) %cyan(%logger{0}) - %msg%n");
        encoder.start();

        // CONSOLE APPENDER
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
        appender.setContext(loggerContext);
        appender.setEncoder(encoder); 
        appender.start();
        rootLogger.addAppender(appender);
        
        // SET LOG LEVEL
        rootLogger.setLevel(Level.INFO);

        // HIBERNATE LOGGER
        ch.qos.logback.classic.Logger hiberLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("org.hibernate");
        hiberLogger.setLevel(Level.INFO);

        // WELD LOGGER
        ch.qos.logback.classic.Logger weldLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("org.jboss.weld");
        weldLogger.setLevel(Level.INFO);
    }
}
