package pl.pwr.tp.shop;

/**
 * Thrown when an order asks for more units than the inventory holds.
 */
public class OutOfStockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the exception with a message describing the shortage.
     *
     * @param sku       product code
     * @param requested requested units
     * @param available units in stock
     */
    public OutOfStockException(String sku, int requested, int available) {
        super("Not enough " + sku + ": requested " + requested + ", available " + available);
    }
}
