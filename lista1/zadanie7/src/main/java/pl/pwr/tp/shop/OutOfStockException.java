package pl.pwr.tp.shop;

public class OutOfStockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OutOfStockException(String sku, int requested, int available) {
        super("Not enough " + sku + ": requested " + requested + ", available " + available);
    }
}
