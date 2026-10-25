package pl.pwr.tp.library.repository.memory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.pwr.tp.library.domain.Reader;
import pl.pwr.tp.library.repository.ReaderRepository;

// GRASP Pure Fabrication: not a domain concept, it only keeps readers in memory.
public class InMemoryReaderRepository implements ReaderRepository {

    private final Map<Integer, Reader> readers = new LinkedHashMap<>();
    private int lastId;

    @Override
    public int nextId() {
        lastId++;
        return lastId;
    }

    @Override
    public void save(Reader reader) {
        readers.put(reader.id(), reader);
    }

    @Override
    public Optional<Reader> findById(int id) {
        return Optional.ofNullable(readers.get(id));
    }

    @Override
    public List<Reader> findAll() {
        return List.copyOf(readers.values());
    }
}
