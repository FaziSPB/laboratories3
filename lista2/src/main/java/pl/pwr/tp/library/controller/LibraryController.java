package pl.pwr.tp.library.controller;

import java.util.List;
import java.util.Optional;
import pl.pwr.tp.library.domain.Book;
import pl.pwr.tp.library.domain.Copy;
import pl.pwr.tp.library.domain.Reader;
import pl.pwr.tp.library.repository.BookRepository;
import pl.pwr.tp.library.repository.ReaderRepository;

// GRASP Controller: receives every system operation from the UI and delegates the work to the
// domain. Low Coupling: it sees only the repository interfaces, never a concrete storage.
public class LibraryController {

    private final BookRepository books;
    private final ReaderRepository readers;

    public LibraryController(BookRepository books, ReaderRepository readers) {
        this.books = books;
        this.readers = readers;
    }

    public Book addBook(String title, String author) {
        if (books.findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("Title \"" + title + "\" already exists");
        }
        Book book = new Book(books.nextId(), title.strip(), author.strip());
        books.save(book);
        return book;
    }

    public Optional<Book> findBook(String title) {
        return books.findByTitle(title);
    }

    public Copy addCopy(int bookId) {
        Book book = book(bookId);
        Copy copy = book.addCopy();
        books.save(book);
        return copy;
    }

    public Reader addReader(String name) {
        Reader reader = new Reader(readers.nextId(), name.strip());
        readers.save(reader);
        return reader;
    }

    public Copy lend(int readerId, int bookId) {
        Reader reader = reader(readerId);
        Book book = book(bookId);
        Copy copy = book.findAvailableCopy().orElseThrow(
                () -> new IllegalStateException("No available copy of \""
                        + book.getTitle() + "\""));
        copy.lendTo(reader);
        books.save(book);
        return copy;
    }

    public Copy giveBack(int readerId, int bookId) {
        Reader reader = reader(readerId);
        Book book = book(bookId);
        Copy copy = book.copiesBorrowedBy(reader).stream().findFirst().orElseThrow(
                () -> new IllegalStateException(reader.name() + " has no copy of \""
                        + book.getTitle() + "\""));
        copy.giveBack();
        books.save(book);
        return copy;
    }

    public List<Book> getBooks() {
        return books.findAll();
    }

    public List<Reader> getReaders() {
        return readers.findAll();
    }

    public List<Copy> getLoans(int readerId) {
        Reader reader = reader(readerId);
        return books.findAll().stream()
                .flatMap(book -> book.copiesBorrowedBy(reader).stream())
                .toList();
    }

    private Book book(int id) {
        return books.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No book with id " + id));
    }

    private Reader reader(int id) {
        return readers.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No reader with id " + id));
    }
}
