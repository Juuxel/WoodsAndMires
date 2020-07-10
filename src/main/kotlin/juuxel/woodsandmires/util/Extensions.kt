package juuxel.woodsandmires.util

import com.google.common.base.Predicates
import java.util.stream.Stream

@Suppress("UNCHECKED_CAST")
fun <T, U : Any> Stream<T>.mapNotNull(mapper: (T) -> U?): Stream<U> =
    map(mapper).filter(Predicates.notNull()) as Stream<U>
