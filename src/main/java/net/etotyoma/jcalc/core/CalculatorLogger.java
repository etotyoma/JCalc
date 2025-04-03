package net.etotyoma.jcalc.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalculatorLogger {
    private static final String LOG_FILE = "latest.log";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum LogLevel {
        INFO, ERROR
    }

    static {
        clearLogFile();
        log("Calculator started", LogLevel.INFO);
    }

    public static void log(String message, LogLevel logLevel) {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] [%s] %s%n", timestamp, logLevel, message);

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logMessage);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    public static void clearLogFile() {
        File file = new File(LOG_FILE);
        if (file.exists())
            file.delete();
    }
}
