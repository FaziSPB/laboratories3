package pl.pwr.tp.library.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CopyTest {

    private final Copy copy = new Book(1, "Lalka", "Bolesław Prus").addCopy();
    private final Reader reader = new Reader(1, "Jan Kowalski");

    @Test
    void newCopyIsAvailable() {
        assertTrue(copy.isAvailable());
        assertTrue(copy.getBorrower().isEmpty());
    }

    @Test
    void lendToRemembersTheBorrower() {
        copy.lendTo(reader);

        assertFalse(copy.isAvailable());
        assertTrue(copy.isBorrowedBy(reader));
        assertEquals(reader, copy.getBorrower().orElseThrow());
    }

    @Test
    void isBorrowedByComparesReaderIds() {
        copy.lendTo(reader);

        assertTrue(copy.isBorrowedBy(new Reader(1, "Jan Kowalski-Nowak")));
        assertFalse(copy.isBorrowedBy(new Reader(2, "Jan Kowalski")));
    }

    @Test
    void cannotLendCopyThatIsAlreadyLent() {
        copy.lendTo(reader);

        assertThrows(IllegalStateException.class, () -> copy.lendTo(new Reader(2, "Anna Nowak")));
    }

    @Test
    void giveBackMakesCopyAvailable() {
        copy.lendTo(reader);
        copy.giveBack();

        assertTrue(copy.isAvailable());
        assertFalse(copy.isBorrowedBy(reader));
    }

    @Test
    void cannotGiveBackCopyThatIsNotLent() {
        assertThrows(IllegalStateException.class, copy::giveBack);
    }

    @Test
    void toStringShowsTitleAndNumber() {
        assertEquals("\"Lalka\" #1", copy.toString());
    }
}
