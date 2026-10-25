package pl.pwr.tp.library.repository.memory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.pwr.tp.library.domain.Book;
import pl.pwr.tp.library.repository.BookRepository;

// GRASP Pure Fabrication: not a domain concept, it only keeps books in memory.
public class InMemoryBookRepository implements BookRepository {

    private final Map<Integer, Book> books = new LinkedHashMap<>();
    private int lastId;

    @Override
    public int nextId() {
        lastId++;
        return lastId;
    }

    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Optional<Book> findById(int id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return books.values().stream().filter(book -> book.hasTitle(title)).findFirst();
    }

    @Override
    public List<Book> findAll() {
        return List.copyOf(books.values());
    }
}
