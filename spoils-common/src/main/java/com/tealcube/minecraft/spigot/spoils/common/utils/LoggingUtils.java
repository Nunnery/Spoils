package com.tealcube.minecraft.spigot.spoils.common.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class LoggingUtils {

    private LoggingUtils() {
        // do nothing
    }

    public static Logger createLoggerFor(Class<?> clazz, File logDirectory) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();

        ple.setPattern("%-5level %logger{35} - %msg%n");
        ple.setContext(loggerContext);
        ple.start();

        TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy();
        timeBasedRollingPolicy.setMaxHistory(10);
        timeBasedRollingPolicy.setFileNamePattern("debug-%d{yyyy-MM-dd}.log");

        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
        rollingFileAppender.setFile(new File(logDirectory, "debug.log").getAbsolutePath());
        rollingFileAppender.setEncoder(ple);
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        rollingFileAppender.start();
        rollingFileAppender.setAppend(true);

        Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(clazz);
        logger.addAppender(rollingFileAppender);
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(false);

        return logger;
    }

}
