package pl.pwr.tp.library;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import pl.pwr.tp.library.controller.LibraryController;
import pl.pwr.tp.library.repository.memory.InMemoryBookRepository;
import pl.pwr.tp.library.repository.memory.InMemoryReaderRepository;
import pl.pwr.tp.library.ui.ConsoleUi;

// Composition root: the only place that picks the storage. To use a database, pass other
// repository implementations here; nothing else changes (Dependency Inversion).
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        LibraryController library = new LibraryController(
                new InMemoryBookRepository(), new InMemoryReaderRepository());
        try (Scanner in = new Scanner(System.in, StandardCharsets.UTF_8)) {
            new ConsoleUi(library, in, System.out).run();
        }
    }
}
