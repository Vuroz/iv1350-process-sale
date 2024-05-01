package se.kth.iv1350.processsale.startup;

import se.kth.iv1350.processsale.controller.Controller;
import se.kth.iv1350.processsale.view.View;

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
        Controller controller = new Controller();
        View view = new View(controller);
        view.simulateSale();
    }
}