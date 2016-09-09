package fr.pjthin.root.functional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainTest {

    public static void main(String[] args) {
        Random r = new Random();
        List<Integer> operations = r.ints().limit(20).map(o -> o % 250).boxed().collect(Collectors.toList());
        LispStream<Integer> streamOperation = new LispStream<Integer>(operations);
        int solde = 1000;
        LispStream.createFrom(solde, streamOperation, (init, operation) -> init.orElse(0) - operation.orElse(0))
                .stream().limit(5).forEach(s -> System.out.println(s));
    }

}
