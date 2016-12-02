package fr.pjthin.root.functional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainTest {

	public static void main(String[] args) {
//		List<Integer> operations = new Random().ints().limit(100000).map(o -> o % 250).boxed()
//				.collect(Collectors.toList());
		List<Integer> operations = Stream.iterate(1, (i)->i+1).limit(10).collect(Collectors.toList());
		LispStream<Integer> streamOperation = new LispStream<Integer>(operations);
		int solde = 1000;
		LispStream<Integer> streamSolde = LispStream.createFrom(solde, streamOperation,
				(init, operation) -> init.orElse(0) - operation.orElse(0));
		System.out.println("getValue(2)="+streamSolde.value(2));
		streamSolde.forEach(s -> System.out.println("mega="+s));
		streamSolde.limit(1).forEach(s -> System.out.println("limit=" + s));
		
	}

}
