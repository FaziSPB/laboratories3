package pl.pwr.tp.library.repository;

import java.util.List;
import java.util.Optional;
import pl.pwr.tp.library.domain.Reader;

// GRASP Pure Fabrication + Dependency Inversion: storage of readers hidden behind an abstraction.
public interface ReaderRepository {

    int nextId();

    void save(Reader reader);

    Optional<Reader> findById(int id);

    List<Reader> findAll();
}
