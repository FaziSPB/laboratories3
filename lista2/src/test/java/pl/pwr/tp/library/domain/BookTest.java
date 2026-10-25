package pl.pwr.tp.library.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class BookTest {

    private final Book book = new Book(1, "Lalka", "Bolesław Prus");
    private final Reader reader = new Reader(1, "Jan Kowalski");

    @Test
    void addCopyNumbersCopiesFromOne() {
        Copy first = book.addCopy();
        Copy second = book.addCopy();

        assertEquals(1, first.getNumber());
        assertEquals(2, second.getNumber());
        assertEquals(List.of(first, second), book.getCopies());
        assertEquals(book, second.getBook());
    }

    @Test
    void findAvailableCopySkipsLentCopies() {
        book.addCopy().lendTo(reader);
        Copy free = book.addCopy();

        assertEquals(free, book.findAvailableCopy().orElseThrow());
        assertEquals(1, book.availableCount());
    }

    @Test
    void noAvailableCopyWhenAllAreLent() {
        book.addCopy().lendTo(reader);

        assertTrue(book.findAvailableCopy().isEmpty());
        assertEquals(0, book.availableCount());
    }

    @Test
    void copiesBorrowedByReturnsOnlyThatReadersCopies() {
        Copy mine = book.addCopy();
        mine.lendTo(reader);
        book.addCopy().lendTo(new Reader(2, "Anna Nowak"));
        book.addCopy();

        assertEquals(List.of(mine), book.copiesBorrowedBy(reader));
    }

    @Test
    void hasTitleIgnoresCaseAndSurroundingSpaces() {
        assertTrue(book.hasTitle("  lalka "));
        assertFalse(book.hasTitle("Faraon"));
    }
}
