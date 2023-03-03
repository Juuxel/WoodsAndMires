package juuxel.woodsandmires.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class RegistryCollector<E> {
    private final List<E> values = new ArrayList<>();

    public <F extends E> F add(F value) {
        values.add(value);
        return value;
    }

    public Stream<E> stream() {
        return values.stream();
    }
}
