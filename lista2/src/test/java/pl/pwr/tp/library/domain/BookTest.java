package pl.pwr.tp.library.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BookTest {

    private final Book book = new Book(1, "Lalka", "Bolesław Prus");

    @Test
    void addCopyNumbersCopiesFromOne() {
        book.addCopy();
        Copy second = book.addCopy();

        assertEquals(2, second.getNumber());
        assertEquals(2, book.getCopies().size());
    }

    @Test
    void findAvailableCopySkipsLentCopies() {
        book.addCopy().lendTo(new Reader(1, "Jan Kowalski"));
        Copy free = book.addCopy();

        assertEquals(free, book.findAvailableCopy().orElseThrow());
        free.lendTo(new Reader(2, "Anna Nowak"));
        assertTrue(book.findAvailableCopy().isEmpty());
    }
}
