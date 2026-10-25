package pl.pwr.tp.library.ui;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import pl.pwr.tp.library.controller.LibraryController;
import pl.pwr.tp.library.domain.Book;
import pl.pwr.tp.library.domain.Copy;
import pl.pwr.tp.library.domain.Reader;

// GRASP Pure Fabrication: console input and output kept out of the domain and the controller.
public class ConsoleUi {

    private static final String MENU = """

            1. Add title
            2. Add copy
            3. Add reader
            4. Lend a copy
            5. Return a copy
            6. List titles and copies
            7. List readers and loans
            0. Exit""";

    private final LibraryController library;
    private final Scanner in;
    private final PrintStream out;

    public ConsoleUi(LibraryController library, Scanner in, PrintStream out) {
        this.library = library;
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            String choice = readText(MENU + "\nChoice: ");
            while (!"0".equals(choice)) {
                execute(choice);
                choice = readText(MENU + "\nChoice: ");
            }
        } catch (NoSuchElementException e) {
            out.println();
        }
    }

    private void execute(String choice) {
        try {
            switch (choice) {
                case "1" -> out.println("Added title " + library.addBook(
                        readText("Title: "), readText("Author: ")));
                case "2" -> addCopy();
                case "3" -> out.println("Added reader " + library.addReader(readText("Name: ")));
                case "4" -> {
                    Copy copy = library.lend(readNumber("Reader id: "), readNumber("Book id: "));
                    out.println("Lent " + copy + " to " + copy.getBorrower().orElseThrow().name());
                }
                case "5" -> out.println("Returned " + library.giveBack(
                        readNumber("Reader id: "), readNumber("Book id: ")));
                case "6" -> listBooks();
                case "7" -> listReaders();
                default -> out.println("Unknown option");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    private void addCopy() {
        String title = readText("Title: ");
        Book book = library.findBook(title).orElseGet(() -> {
            out.println("Title not found, adding it.");
            Book added = library.addBook(title, readText("Author: "));
            out.println("Added title " + added);
            return added;
        });
        out.println("Added copy " + library.addCopy(book.getId()));
    }

    private void listBooks() {
        if (library.getBooks().isEmpty()) {
            out.println("No titles");
        }
        for (Book book : library.getBooks()) {
            out.println(book + " - copies: " + book.getCopies().size()
                    + ", available: " + book.availableCount());
            for (Copy copy : book.getCopies()) {
                String state = copy.getBorrower()
                        .map(reader -> "lent to " + reader)
                        .orElse("available");
                out.println("    #" + copy.getNumber() + " " + state);
            }
        }
    }

    private void listReaders() {
        if (library.getReaders().isEmpty()) {
            out.println("No readers");
        }
        for (Reader reader : library.getReaders()) {
            String loans = library.getLoans(reader.id()).stream()
                    .map(Copy::toString)
                    .collect(Collectors.joining(", "));
            out.println(reader + " - loans: " + (loans.isEmpty() ? "none" : loans));
        }
    }

    private String readText(String prompt) {
        String text = "";
        while (text.isEmpty()) {
            out.print(prompt);
            text = in.nextLine().strip();
        }
        return text;
    }

    private int readNumber(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException e) {
                out.println("Please enter a number");
            }
        }
    }
}
