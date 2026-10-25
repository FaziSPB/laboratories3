package pl.pwr.tp.library.repository;

import java.util.List;
import java.util.Optional;
import pl.pwr.tp.library.domain.Book;

// GRASP Pure Fabrication + Dependency Inversion: the controller depends on this abstraction,
// so a database only needs another implementation (e.g. JdbcBookRepository).
public interface BookRepository {

    int nextId();

    void save(Book book);

    Optional<Book> findById(int id);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();
}
