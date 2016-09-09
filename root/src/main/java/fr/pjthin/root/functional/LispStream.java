package fr.pjthin.root.functional;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Stream define as Lisp language.
 * 
 * @author Pidji
 *
 * @param <T>
 */
public class LispStream<T> {

    private Optional<T> first;
    private Supplier<LispStream<T>> next;

    private LispStream() {
        this.first = Optional.empty();
        this.next = () -> null;
    }

    public LispStream(Optional<T> first, Supplier<LispStream<T>> next) {
        super();
        this.first = first;
        this.next = next;
    }

    public LispStream(T first, Supplier<LispStream<T>> next) {
        this(Optional.ofNullable(first), next);
    }

    public LispStream(List<T> list) {
        this(list.isEmpty() ? null : list.get(0), () -> list.isEmpty() ? empty() : new LispStream<T>(list.subList(1,
                list.size())));
    }

    public Stream<T> stream() {
        if (first.isPresent() && next != null) {
            return Stream.concat(Stream.of(first.get()), next.get().stream());
        }
        if (first.isPresent()) {
            return Stream.of(first.get());
        }
        return Stream.empty();
    }

    public Supplier<LispStream<T>> next() {
        return next;
    }

    public void next(Supplier<LispStream<T>> next) {
        this.next = next;
    }

    public Optional<T> first() {
        return first;
    }

    public void first(T first) {
        first(Optional.ofNullable(first));
    }

    public void first(Optional<T> first) {
        this.first = first;
    }

    public static <T> LispStream<T> empty() {
        return new LispStream<T>();
    }

    /**
     * Create a LispStream from an initial value, a LispStream and a transformer.
     * <p>
     * It is like building an arithmetic progression <code>U(n+1)=transformer(U(n),V(n))</code> and putting it in a
     * LispStream.
     * 
     * @param initialNewStream
     *            the first value of the new LispStream
     * @param otherStream
     *            the stream used to create the new one
     * @param transform
     *            the function that take the {@link Optional} (n-1)-value of the new stream with the {@link Optional}
     *            n-value of the other stream and produce the n-value of the new stream
     * @return
     */
    public static <T> LispStream<T> createFrom(T initialNewStream, LispStream<T> otherStream,
            BiFunction<Optional<T>, Optional<T>, T> transform) {
        if (otherStream == null) {
            return empty();
        }
        Optional<T> nextValue = Optional.ofNullable(transform.apply(Optional.ofNullable(initialNewStream),
                otherStream.first));
        if (!nextValue.isPresent()) {
            return empty();
        }
        LispStream<T> nextStream = otherStream.next.get();
        return new LispStream<T>(initialNewStream, () -> createFrom(nextValue.get(), nextStream, transform));
    }
}
