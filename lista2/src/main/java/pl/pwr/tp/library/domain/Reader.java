package pl.pwr.tp.library.domain;

// GRASP Low Coupling: a reader only identifies a person and knows nothing about copies.
public record Reader(int id, String name) {

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}
