package fr.pjthin.root.functional;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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
public class LispStream<T> implements Iterable<T> {

	private final T first;
	private Supplier<LispStream<T>> next;

	private LispStream() {
		this(null, null);
	}

	public LispStream(T first, Supplier<LispStream<T>> next) {
		this.first = first;
		this.next = next;
	}

	public LispStream(List<T> list) {
		this(list.isEmpty() ? null : list.get(0),
				() -> list.isEmpty() ? empty() : new LispStream<T>(list.subList(1, list.size())));
	}

	public LispStream(Iterator<T> iterator) {
		this(iterator.hasNext() ? iterator.next() : null,
				() -> iterator.hasNext() ? new LispStream<>(iterator) : empty());
	}

	public Stream<T> stream() {
		if (first != null && next != null) {
			return Stream.concat(Stream.of(first), Stream.generate(next).flatMap(lispStream -> lispStream.stream()));
		}
		return Stream.empty();
	}

	public T value(int position) {
		int i = 0;
		for (T current : this) {
			if (i == position) {
				return current;
			}
			i++;
		}
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		final LispStream<T> that = this;

		return new Iterator<T>() {

			LispStream<T> current = that;

			@Override
			public boolean hasNext() {
				return current.next != null;
			}

			@Override
			public T next() {
				T tmp = current.first;
				current = current.next.get();
				return tmp;
			}
		};
	}

	public LispStream<T> skip(long n) {
		
		return skip(this, n);
	}

	private LispStream<T> skip(LispStream<T> lispStream, long n) {
		if (lispStream.next == null) {
			return empty();
		}
		if (n == 0) {
			return next.get();
		}
		return skip(lispStream.next.get(), n - 1);
	}

	public LispStream<T> limit(long maxSize) {
		limit(this, maxSize);
		return this;
	}

	private void limit(LispStream<T> lispStream, long max) {
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
		Optional<T> nextValue = Optional.ofNullable(
				transformer.apply(Optional.ofNullable(initialNewStream), Optional.ofNullable(otherStream.first)));
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
