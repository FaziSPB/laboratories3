package pl.pwr.tp.library.ui;

import java.io.PrintStream;
import java.util.Scanner;
import pl.pwr.tp.library.controller.LibraryController;
import pl.pwr.tp.library.domain.Book;
import pl.pwr.tp.library.domain.Copy;

// GRASP Pure Fabrication: console input and output kept out of the domain and the controller.
public class ConsoleUi {

    private static final String MENU = """

            1. Add title
            2. Add copy
            3. Add reader
            4. Lend a copy
            5. List titles and copies
            6. List readers
            0. Exit
            Choice:\s""";

    private final LibraryController library;
    private final Scanner in;
    private final PrintStream out;

    public ConsoleUi(LibraryController library, Scanner in, PrintStream out) {
        this.library = library;
        this.in = in;
        this.out = out;
    }

    public void run() {
        String choice = readText(MENU);
        while (!"0".equals(choice)) {
            execute(choice);
            choice = readText(MENU);
        }
    }

    private void execute(String choice) {
        try {
            switch (choice) {
                case "1" -> out.println("Added title " + library.addBook(
                        readText("Title: "), readText("Author: ")));
                case "2" -> addCopy();
                case "3" -> out.println("Added reader " + library.addReader(readText("Name: ")));
                case "4" -> out.println("Lent copy #" + library.lend(
                        readNumber("Reader id: "), readNumber("Book id: ")).getNumber());
                case "5" -> listBooks();
                case "6" -> library.getReaders().forEach(out::println);
                default -> out.println("Unknown option");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    private void addCopy() {
        String title = readText("Title: ");
        Book book = library.findBook(title)
                .orElseGet(() -> library.addBook(title, readText("New title, author: ")));
        out.println("Added copy #" + library.addCopy(book.getId()).getNumber() + " of " + book);
    }

    private void listBooks() {
        for (Book book : library.getBooks()) {
            out.println(book);
            for (Copy copy : book.getCopies()) {
                String state = copy.getBorrower()
                        .map(reader -> "lent to " + reader)
                        .orElse("available");
                out.println("    #" + copy.getNumber() + " " + state);
            }
        }
    }

    private String readText(String prompt) {
        out.print(prompt);
        return in.nextLine();
    }

    private int readNumber(String prompt) {
        return Integer.parseInt(readText(prompt));
    }
}
