package pl.pwr.tp.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import pl.pwr.tp.library.domain.Book;
import pl.pwr.tp.library.domain.Copy;
import pl.pwr.tp.library.domain.Reader;
import pl.pwr.tp.library.repository.memory.InMemoryBookRepository;
import pl.pwr.tp.library.repository.memory.InMemoryReaderRepository;

class LibraryControllerTest {

    private final LibraryController library = new LibraryController(
            new InMemoryBookRepository(), new InMemoryReaderRepository());

    @Test
    void addBookAssignsIdsAndTrimsInput() {
        Book first = library.addBook(" Lalka ", " Bolesław Prus ");
        Book second = library.addBook("Quo vadis", "Henryk Sienkiewicz");

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
        assertEquals("Lalka", first.getTitle());
        assertEquals("Bolesław Prus", first.getAuthor());
        assertEquals(List.of(first, second), library.getBooks());
    }

    @Test
    void addBookRejectsDuplicateTitle() {
        library.addBook("Lalka", "Bolesław Prus");

        assertThrows(IllegalArgumentException.class, () -> library.addBook("lalka", "Someone"));
    }

    @Test
    void findBookIgnoresCase() {
        Book book = library.addBook("Lalka", "Bolesław Prus");

        assertEquals(book, library.findBook("LALKA").orElseThrow());
        assertTrue(library.findBook("Faraon").isEmpty());
    }

    @Test
    void addCopyAddsCopiesToTheTitle() {
        Book book = library.addBook("Lalka", "Bolesław Prus");

        library.addCopy(book.getId());
        Copy second = library.addCopy(book.getId());

        assertEquals(2, second.getNumber());
        assertEquals(2, book.getCopies().size());
    }

    @Test
    void addCopyToUnknownTitleFails() {
        assertThrows(IllegalArgumentException.class, () -> library.addCopy(1));
    }

    @Test
    void addReaderAssignsIds() {
        Reader jan = library.addReader("Jan Kowalski");
        Reader anna = library.addReader("Anna Nowak");

        assertEquals(1, jan.id());
        assertEquals(2, anna.id());
        assertEquals(List.of(jan, anna), library.getReaders());
    }

    @Test
    void lendGivesAnAvailableCopyToTheReader() {
        Reader reader = library.addReader("Jan Kowalski");
        Book book = library.addBook("Lalka", "Bolesław Prus");
        library.addCopy(book.getId());

        Copy copy = library.lend(reader.id(), book.getId());

        assertTrue(copy.isBorrowedBy(reader));
        assertEquals(0, book.availableCount());
    }

    @Test
    void readerCanBorrowSeveralBooks() {
        Reader reader = library.addReader("Jan Kowalski");
        Book lalka = library.addBook("Lalka", "Bolesław Prus");
        Book quoVadis = library.addBook("Quo vadis", "Henryk Sienkiewicz");
        library.addCopy(lalka.getId());
        library.addCopy(lalka.getId());
        library.addCopy(quoVadis.getId());

        Copy first = library.lend(reader.id(), lalka.getId());
        Copy second = library.lend(reader.id(), lalka.getId());
        Copy third = library.lend(reader.id(), quoVadis.getId());

        assertEquals(List.of(first, second, third), library.getLoans(reader.id()));
    }

    @Test
    void lendFailsWhenNoCopyIsAvailable() {
        Reader reader = library.addReader("Jan Kowalski");
        Book book = library.addBook("Lalka", "Bolesław Prus");

        assertThrows(IllegalStateException.class, () -> library.lend(reader.id(), book.getId()));
    }

    @Test
    void lendToUnknownReaderFails() {
        Book book = library.addBook("Lalka", "Bolesław Prus");
        library.addCopy(book.getId());

        assertThrows(IllegalArgumentException.class, () -> library.lend(7, book.getId()));
    }

    @Test
    void giveBackMakesTheCopyAvailableAgain() {
        Reader reader = library.addReader("Jan Kowalski");
        Book book = library.addBook("Lalka", "Bolesław Prus");
        library.addCopy(book.getId());
        Copy lent = library.lend(reader.id(), book.getId());

        Copy returned = library.giveBack(reader.id(), book.getId());

        assertEquals(lent, returned);
        assertEquals(1, book.availableCount());
        assertTrue(library.getLoans(reader.id()).isEmpty());
    }

    @Test
    void giveBackFailsWhenReaderHasNoCopyOfTheTitle() {
        Reader jan = library.addReader("Jan Kowalski");
        Reader anna = library.addReader("Anna Nowak");
        Book book = library.addBook("Lalka", "Bolesław Prus");
        library.addCopy(book.getId());
        library.lend(jan.id(), book.getId());

        assertThrows(IllegalStateException.class, () -> library.giveBack(anna.id(), book.getId()));
    }
}
