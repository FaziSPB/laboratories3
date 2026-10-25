package pl.pwr.tp.library.domain;

import java.util.Optional;

// GRASP Information Expert: a copy knows whether it is lent and to whom.
public class Copy {

    private final Book book;
    private final int number;
    private Optional<Reader> borrower = Optional.empty();

    /* default */ Copy(Book book, int number) {
        this.book = book;
        this.number = number;
    }

    public void lendTo(Reader reader) {
        if (!isAvailable()) {
            throw new IllegalStateException(this + " is already lent");
        }
        borrower = Optional.of(reader);
    }

    public void giveBack() {
        if (isAvailable()) {
            throw new IllegalStateException(this + " is not lent");
        }
        borrower = Optional.empty();
    }

    public boolean isAvailable() {
        return borrower.isEmpty();
    }

    public boolean isBorrowedBy(Reader reader) {
        return borrower.map(current -> current.id() == reader.id()).orElse(false);
    }

    public Book getBook() {
        return book;
    }

    public int getNumber() {
        return number;
    }

    public Optional<Reader> getBorrower() {
        return borrower;
    }

    @Override
    public String toString() {
        return "\"" + book.getTitle() + "\" #" + number;
    }
}
