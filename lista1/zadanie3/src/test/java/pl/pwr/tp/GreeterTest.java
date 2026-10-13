package pl.pwr.tp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GreeterTest {

    @Test
    void greetsByName() {
        assertEquals("Hello, Alice!", new Greeter("Hello").greet("Alice"));
    }
}
