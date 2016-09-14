package fr.pjthin.root.functional;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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

	private final Optional<T> first;
	private Supplier<LispStream<T>> next;

	private LispStream() {
		this(Optional.empty(), null);
	}

	private LispStream(Optional<T> first, Supplier<LispStream<T>> next) {
		this.first = first;
		this.next = memoize(next);
	}

	public LispStream(T first, Supplier<LispStream<T>> next) {
		this(Optional.ofNullable(first), next);
	}

	public LispStream(List<T> list) {
		this(list.isEmpty() ? null : list.get(0), () -> list.isEmpty() ? empty() : new LispStream<T>(list.subList(1,
				list.size())));
	}

	public LispStream(Iterator<T> iterator) {
		this(iterator.hasNext() ? iterator.next() : null, () -> iterator.hasNext() ? new LispStream<>(iterator)
				: empty());
	}

	public Stream<T> stream() {
		if (first.isPresent() && next != null) {
			return Stream.concat(Stream.of(first.get()),
					Stream.generate(next).flatMap(lispStream -> lispStream.stream()));
		}
		return Stream.empty();
	}

	public Optional<T> value(int position) {
		return value(this, position);
	}

	private Optional<T> value(LispStream<T> lispStream, int position) {
		if (lispStream == null) {
			return Optional.empty();
		}
		if (position == 0) {
			return lispStream.first;
		}
		return value(lispStream.next.get(), position - 1);
	}

	public void forEach(Consumer<Optional<T>> consumer) {
		forEach(this, consumer);
	}

	private void forEach(LispStream<T> lispStream, Consumer<Optional<T>> consumer) {
		if (lispStream.next == null) {
			return;
		}
		consumer.accept(lispStream.first);
		forEach(lispStream.next.get(), consumer);
	}

	public LispStream<T> limit(int max) {
		limit(this, max);
		return this;
	}

	private void limit(LispStream<T> lispStream, int max) {
		if (max == 0 || lispStream.next == null) {
			lispStream.next = null;
			return;
		}
		limit(lispStream.next.get(), max - 1);
	}

	public static <T> LispStream<T> empty() {
		return new LispStream<T>();
	}

	/**
	 * Create a LispStream from an initial value, a LispStream and a
	 * transformer.
	 * <p>
	 * It is like building an arithmetic progression
	 * <code>U(n+1)=transformer(U(n),V(n))</code> and putting the
	 * <code>U(n)</code> in a LispStream.
	 * 
	 * @param initialNewStream
	 *            the first value of the new LispStream
	 * @param otherStream
	 *            the stream used to create the new one
	 * @param transformer
	 *            the function that take the {@link Optional} n-value of the new
	 *            stream with the {@link Optional} n-value of the other stream
	 *            and produce the (n+1)-value of the new stream
	 * @return
	 */
	public static <T> LispStream<T> createFrom(T initialNewStream, LispStream<T> otherStream,
			BiFunction<Optional<T>, Optional<T>, T> transformer) {
		if (otherStream == null) {
			return empty();
		}
		Optional<T> nextValue = Optional.ofNullable(transformer.apply(Optional.ofNullable(initialNewStream),
				otherStream.first));
		if (!nextValue.isPresent()) {
			return empty();
		}
		if (otherStream.next == null) {
			return new LispStream<T>(initialNewStream, null);
		}
		LispStream<T> nextStream = otherStream.next.get();
		return new LispStream<T>(initialNewStream, () -> createFrom(nextValue.get(), nextStream, transformer));
	}

	public static <T> Supplier<T> memoize(Supplier<T> delegate) {
		if (delegate == null) {
			return null;
		}
		AtomicReference<T> value = new AtomicReference<>();
		return () -> {
			T val = value.get();
			if (val == null) {
				val = value.updateAndGet(cur -> cur == null ? Objects.requireNonNull(delegate.get()) : cur);
			}
			return val;
		};
	}
}
