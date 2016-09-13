package fr.pjthin.root.functional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainTest {

	public static void main(String[] args) {
		List<Integer> operations = new Random().ints().limit(10).map(o -> o % 250).boxed()
				.collect(Collectors.toList());
		LispStream<Integer> streamOperation = new LispStream<Integer>(operations);
		int solde = 1000;
		LispStream<Integer> streamSolde = LispStream.createFrom(solde, streamOperation,
				(init, operation) -> init.orElse(0) - operation.orElse(0));
		System.out.println(streamSolde.value(2));
		streamSolde.forEach(mSolde -> mSolde.ifPresent(s -> System.out.println(s)));
		streamSolde.limit(2).forEach(mSolde -> mSolde.ifPresent(s -> System.out.println("limit=" + s)));

		// streamSolde.stream().limit(20).forEach(s -> System.out.println(s));
	}

}
