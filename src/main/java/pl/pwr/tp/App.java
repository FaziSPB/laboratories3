package pl.pwr.tp;

/** Entry point: prints a greeting for each command-line argument. */
public class App {

    public static void main(String[] args) {
        Greeter greeter = new Greeter("Hello");
        if (args.length == 0) {
            System.out.println(greeter.greet("World"));
            return;
        }
        for (String name : args) {
            System.out.println(greeter.greet(name));
        }
    }
}
