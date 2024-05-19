package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.model.RevenueObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents a class that writes the total revenue to a file.
 */
public class TotalRevenueFileOutput implements RevenueObserver {
    private static final String REVENUE_FILE_NAME = "process-sale-revenue-log.txt";
    private final PrintWriter revenueFile;

    /**
     * Creates a new instance of a total revenue observer.
     *
     * @throws IOException if the revenue file cannot be created
     */
    public TotalRevenueFileOutput() throws IOException {
        revenueFile = new PrintWriter(new FileWriter(REVENUE_FILE_NAME), true);
    }

    /**
     * Called when the total revenue has changed.
     */
    @Override
    public void update(double newTotalRevenue) {
        String revenueMessage = createTime() + "> The total revenue was updated to: %.2f SEK";
        revenueMessage = String.format(revenueMessage, newTotalRevenue);
        revenueFile.println(revenueMessage);
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
