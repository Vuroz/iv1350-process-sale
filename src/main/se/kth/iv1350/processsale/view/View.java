package se.kth.iv1350.processsale.view;

import se.kth.iv1350.processsale.controller.Controller;

/**
 * This class acts as a stand-in for the view. It has a fixed sequence of operations, calling all the system functions in the controller.
 */
public class View {
    private Controller controller;

    /**
     * Creates a new instance of this class.
     *
     * @param controller The controller to be used in all the operations
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Simulates the user actions from the Process Sale scenario.
     */
    public void simulateSale() {
        controller.startNewSale();

        controller.addItemToSale("abc123", 1.0);
        controller.addItemToSale("abc123", 1.0);
        controller.addItemToSale("def456", 1.0);
        controller.addItemToSale("1337", 3.0);
        controller.addItemToSale("1", 5.0);
        controller.addItemToSale("42", 2.0);
        controller.addItemToSale("5", 4.0);
        controller.addItemToSale("3op", 3.0);
        controller.addItemToSale("1337", 5.0);


        controller.endSale();

        controller.pay(1000.0);
    }
}
