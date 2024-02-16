package org.example.Logging;

import java.time.LocalDateTime;

public class Logger {
    private String className;

    public Logger(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARNING", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    private void log(String level, String message) {
        LocalDateTime timestamp = LocalDateTime.now();
        System.out.println(timestamp + " [" + level + "] " + className + ": " + message);
    }
}
