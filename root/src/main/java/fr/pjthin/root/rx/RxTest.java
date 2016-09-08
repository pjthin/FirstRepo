package fr.pjthin.root.rx;

import java.util.Arrays;
import java.util.Date;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

public class RxTest {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new Date());
		Observable
				.from(Arrays.asList("a", "b", "c").toArray())
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.computation())
				.map(value -> {
					return String.format("map(%s, %s)", Thread.currentThread().getName(), value);
				})
				.subscribe(
						value -> System.out.println(String.format("subscribe(%s, %s)",
								Thread.currentThread().getName(), value)), error -> System.err.println(error),
						() -> System.out.println("complete"));
		Subscription s = Observable
				.create(subscriber -> {
					subscriber.onStart();
					subscriber.onNext(String.format("onNext(%s)", Thread.currentThread().getName()));
					subscriber.onCompleted();
				})
				//
				.subscribeOn(Schedulers.io())
				//
				.observeOn(Schedulers.computation())
				//
				.map(value -> {
					return String.format("map(%s, %s)", Thread.currentThread().getName(), value);
				})
				.subscribe(
						value -> System.out.println(String.format("subscribe(%s, %s)",
								Thread.currentThread().getName(), value)), error -> System.err.println(error),
						() -> System.out.println("complete"));
		Async.start(() -> {
			try {
				Thread.sleep(1500);
			}
			catch (Exception e) {
			}
			return String.format("Async.start() : loooooong treatement");
		}, Schedulers.io()).map(data -> String.format("map(%s)", data)).subscribe(System.out::println);
		System.out.println("s.isUnsubscribed()="+s.isUnsubscribed());
		Thread.sleep(5000);
	}
}
