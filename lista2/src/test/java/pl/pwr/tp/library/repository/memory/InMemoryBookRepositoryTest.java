package pl.pwr.tp.library.repository.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import pl.pwr.tp.library.domain.Book;

class InMemoryBookRepositoryTest {

    @Test
    void savedBookCanBeFoundByIdAndTitle() {
        InMemoryBookRepository repository = new InMemoryBookRepository();
        Book book = new Book(repository.nextId(), "Lalka", "Bolesław Prus");
        repository.save(book);

        assertEquals(book, repository.findById(1).orElseThrow());
        assertEquals(book, repository.findByTitle("Lalka").orElseThrow());
        assertTrue(repository.findByTitle("Faraon").isEmpty());
    }
}
