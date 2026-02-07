package com.example.todo.reporting;

import net.serenitybdd.core.Serenity;
import org.openqa.selenium.LogEntry;
import org.openqa.selenium.LogEntries;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public final class BrowserConsoleLogReporter {

    private static final DateTimeFormatter TIME_STAMP
            = DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    private BrowserConsoleLogReporter() {
        // Utility class
    }

    public static void attachLogs(WebDriver driver) {
        if (driver == null) {
            return;
        }
        try {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            List<LogEntry> entries = logEntries.getAll();

            if (entries.isEmpty()) {
                return;
            }

            String formatted = entries.stream()
                    .map(BrowserConsoleLogReporter::format)
                    .collect(Collectors.joining(System.lineSeparator()));

            Serenity.recordReportData()
                    .withTitle("Browser console logs")
                    .andContents(formatted);
        } catch (Exception e) {
            Serenity.recordReportData()
                    .withTitle("Browser console logs (capture failed)")
                    .andContents("Could not capture browser logs: " + e.getMessage());
        }
    }

    private static String format(LogEntry entry) {
        String timestamp = TIME_STAMP.format(Instant.ofEpochMilli(entry.getTimestamp()));
        return String.format("[%s][%s] %s", timestamp, entry.getLevel(), entry.getMessage());
    }
}
