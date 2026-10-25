package pl.pwr.tp.library.repository.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import pl.pwr.tp.library.domain.Book;

class InMemoryBookRepositoryTest {

    private final InMemoryBookRepository repository = new InMemoryBookRepository();

    @Test
    void nextIdGivesConsecutiveNumbers() {
        assertEquals(1, repository.nextId());
        assertEquals(2, repository.nextId());
    }

    @Test
    void savedBookCanBeFoundByIdAndTitle() {
        Book book = new Book(repository.nextId(), "Lalka", "Bolesław Prus");
        repository.save(book);

        assertEquals(book, repository.findById(book.getId()).orElseThrow());
        assertEquals(book, repository.findByTitle("LALKA").orElseThrow());
        assertTrue(repository.findById(99).isEmpty());
        assertTrue(repository.findByTitle("Faraon").isEmpty());
    }

    @Test
    void findAllKeepsInsertionOrder() {
        Book first = new Book(repository.nextId(), "Lalka", "Bolesław Prus");
        Book second = new Book(repository.nextId(), "Quo vadis", "Henryk Sienkiewicz");
        repository.save(first);
        repository.save(second);

        assertEquals(List.of(first, second), repository.findAll());
    }
}
