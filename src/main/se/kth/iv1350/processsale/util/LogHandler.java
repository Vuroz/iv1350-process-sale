package se.kth.iv1350.processsale.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents a log handler that logs exceptions to a file.
 */
public class LogHandler {
    private static LogHandler LOG_HANDLER;
    private static final String LOG_FILE_NAME = "process-sale-log.txt";
    private PrintWriter logFile;

    /**
     * Creates a new instance of a log handler.
     *
     * @throws IOException if the log file cannot be created
     */
    private LogHandler() throws IOException {
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
    }

    /**
     * Gets the only instance of this class
     * @return instance of this log handler
     * @throws IOException if the log file cannot be created.
     */
    public static LogHandler getInstance() throws IOException {
        if (LOG_HANDLER == null) {
            LOG_HANDLER = new LogHandler();
        }
        return LOG_HANDLER;
    }

    /**
     * Logs an exception to the log file.
     *
     * @param exception the exception to log
     */
    public void logException(Exception exception) {
        String logMessageBuilder = createTime() +
                "> The following exception was thrown: " +
                exception.getMessage();
        logFile.println(logMessageBuilder);
        exception.printStackTrace(logFile);
    }

    /**
     * Creates a formatted string representation of the current time.
     * 
     * @return a formatted string representation of the current time
     */
    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
