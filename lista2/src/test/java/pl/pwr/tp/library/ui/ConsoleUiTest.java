package pl.pwr.tp.library.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import pl.pwr.tp.library.controller.LibraryController;
import pl.pwr.tp.library.repository.memory.InMemoryBookRepository;
import pl.pwr.tp.library.repository.memory.InMemoryReaderRepository;

class ConsoleUiTest {

    private String run(String... lines) {
        LibraryController library = new LibraryController(
                new InMemoryBookRepository(), new InMemoryReaderRepository());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        new ConsoleUi(library, new Scanner(String.join("\n", lines)), out).run();
        return bytes.toString(StandardCharsets.UTF_8);
    }

    @Test
    void addCopyCreatesMissingTitleThenCopyIsLent() {
        String output = run(
                "3", "Jan Kowalski",
                "2", "Lalka", "Bolesław Prus",
                "2", "lalka",
                "4", "1", "1",
                "6", "7", "0");

        assertTrue(output.contains("Title not found, adding it."));
        assertTrue(output.contains("Added title [1] \"Lalka\" by Bolesław Prus"));
        assertTrue(output.contains("Added copy \"Lalka\" #2"));
        assertTrue(output.contains("Lent \"Lalka\" #1 to Jan Kowalski"));
        assertTrue(output.contains("#1 lent to [1] Jan Kowalski"));
        assertTrue(output.contains("[1] Jan Kowalski - loans: \"Lalka\" #1"));
    }

    @Test
    void returnedCopyIsAvailableAgain() {
        String output = run(
                "3", "Jan Kowalski",
                "2", "Lalka", "Bolesław Prus",
                "4", "1", "1",
                "5", "1", "1",
                "6", "7", "0");

        assertTrue(output.contains("Returned \"Lalka\" #1"));
        assertTrue(output.contains("#1 available"));
        assertTrue(output.contains("[1] Jan Kowalski - loans: none"));
    }

    @Test
    void errorsAndInvalidNumbersAreReported() {
        String output = run("4", "abc", "1", "1", "9", "0");

        assertTrue(output.contains("Please enter a number"));
        assertTrue(output.contains("Error: No reader with id 1"));
        assertTrue(output.contains("Unknown option"));
    }

    @Test
    void endOfInputStopsTheProgram() {
        String output = run("1", "Lalka");

        assertTrue(output.contains("Author: "));
    }
}
