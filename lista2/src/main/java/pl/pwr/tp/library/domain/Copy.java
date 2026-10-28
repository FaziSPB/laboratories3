package pl.pwr.tp.library.domain;

import java.util.Optional;

// GRASP Information Expert: a copy knows whether it is lent and to whom.
public class Copy {

    private final int number;
    private Reader borrower;

    /* default */ Copy(int number) {
        this.number = number;
    }

    public void lendTo(Reader reader) {
        borrower = reader;
    }

    public boolean isAvailable() {
        return borrower == null;
    }

    public int getNumber() {
        return number;
    }

    public Optional<Reader> getBorrower() {
        return Optional.ofNullable(borrower);
    }
}
