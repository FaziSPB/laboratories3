package pl.pwr.tp.library.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// GRASP Creator: a book aggregates its copies, so it creates them.
// GRASP Information Expert: it holds the copy list, so it finds an available copy.
public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final List<Copy> copies = new ArrayList<>();

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Copy addCopy() {
        Copy copy = new Copy(copies.size() + 1);
        copies.add(copy);
        return copy;
    }

    public Optional<Copy> findAvailableCopy() {
        return copies.stream().filter(Copy::isAvailable).findFirst();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Copy> getCopies() {
        return Collections.unmodifiableList(copies);
    }

    @Override
    public String toString() {
        return "[" + id + "] \"" + title + "\" by " + author;
    }
}
