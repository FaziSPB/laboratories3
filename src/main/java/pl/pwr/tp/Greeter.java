package pl.pwr.tp;

/** Builds greeting messages. */
public class Greeter {

    private final String greeting;

    public Greeter(String greeting) {
        this.greeting = greeting;
    }

    public String greet(String name) {
        return greeting + ", " + name + "!";
    }
}
