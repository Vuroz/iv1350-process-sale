package se.kth.iv1350.processsale.startup;

import se.kth.iv1350.processsale.controller.Controller;
import se.kth.iv1350.processsale.integration.TotalRevenueFileOutput;
import se.kth.iv1350.processsale.util.LogHandler;
import se.kth.iv1350.processsale.view.TotalRevenueView;
import se.kth.iv1350.processsale.view.View;

import java.io.IOException;

/**
 * Contains the <code>main</code> method. Performs all startup of the application.
 */
public class ProcessSale {

    /**
     * Starts the application
     *
     * @param args The application does not take any command line parameters
     */
    public static void main(String[] args) {
        TotalRevenueView revenueViewObserver = new TotalRevenueView();
        TotalRevenueFileOutput revenueFileOutputObserver = null;
        try {
            revenueFileOutputObserver = new TotalRevenueFileOutput();
        } catch (IOException e) {
            System.out.println("Could not create revenue file for total revenue file output observer, exiting...");
            System.exit(1);
        }

        Controller controller = new Controller();
        controller.addRevenueObserver(revenueViewObserver);
        controller.addRevenueObserver(revenueFileOutputObserver);
        LogHandler logHandler = null;
        try {
            logHandler = new LogHandler();
        } catch (IOException e) {
            System.out.println("Could not create log file, exiting...");
            System.exit(1);
        }
        View view = new View(controller, logHandler);
        view.simulateSale();
        view.simulateSale();
        view.simulateSale();
    }
}