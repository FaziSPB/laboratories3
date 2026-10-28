package pl.pwr.tp.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void addedTitleCanBeFoundAndGetsCopies() {
        Book book = library.addBook("Lalka", "Bolesław Prus");
        library.addCopy(book.getId());
        library.addCopy(book.getId());

        assertEquals(book, library.findBook("Lalka").orElseThrow());
        assertEquals(2, book.getCopies().size());
    }

    @Test
    void readerCanBorrowSeveralBooks() {
        Reader reader = library.addReader("Jan Kowalski");
        Book lalka = library.addBook("Lalka", "Bolesław Prus");
        Book quoVadis = library.addBook("Quo vadis", "Henryk Sienkiewicz");
        library.addCopy(lalka.getId());
        library.addCopy(quoVadis.getId());

        Copy first = library.lend(reader.id(), lalka.getId());
        Copy second = library.lend(reader.id(), quoVadis.getId());

        assertEquals(reader, first.getBorrower().orElseThrow());
        assertEquals(reader, second.getBorrower().orElseThrow());
    }

    @Test
    void lendFailsWhenNoCopyIsAvailable() {
        Reader reader = library.addReader("Jan Kowalski");
        Book book = library.addBook("Lalka", "Bolesław Prus");

        assertThrows(IllegalStateException.class, () -> library.lend(reader.id(), book.getId()));
    }
}
