package fr.pjthin.root.test;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

public class Java8Test {

    private static final int SECOND = 1000;

    @Test
    public void testStringSwitch() {
        System.out.println("---------------- STARTING SWITCH TEST ----------------");

        String myValue = "FR";

        switch (myValue) {
        case "FR":
            System.out.println("Welcome to France !");
            break;

        default:
            System.out.println("Your country is : " + myValue);
            break;
        }

        System.out.println("---------------- ENDING SWITCH TEST ----------------");
    }

    @Test
    public void testFuture() {

        System.out.println("---------------- STARTING FUTURE TEST ----------------");

        Object object = new Object();

        System.out.println("Before run Future");

        CompletableFuture.runAsync(() -> {
            doSomeThingLong(object);
        }).thenRun(() -> {
            doSomeThingShort(object);
        });

        System.out.println("After run Future");

        System.out.println("---------------- ENDING FUTURE TEST ----------------");
    }

    private void doSomeThingShort(Object object) {
        System.out.println("In doSomeThingShort");
    }

    private void doSomeThingLong(Object o) {
        System.out.println("Before doSomeThingLong");
        try {
            Thread.sleep(10 * SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("After doSomeThingLong");
        }
    }

    @Test
    public void testLambda() {

        System.out.println("---------------- STARTING LAMBDA TEST ----------------");

        java.util.List<String> list = Arrays.asList("AVION", "CAR", "MOTO", "TRAIN", "VOITURE");

        // First step : passing parameter
        list.forEach((vehicle) -> {
            System.out.println("A vehicle is there ! : " + vehicle);
        });

        // METHODE 1 (OLD ONE) : using interface
        createANewCar(new Function<Car, String>() {

            @Override
            public String apply(Car t) {
                t.name += "-Modified";
                return null;
            }

        });

        // METHODE 2 (MONO ACTION)
        createANewCar(car -> (car.name += "-Modified"));

        // METHODE 3 (MULTI ACTION BUT NEED RETURN STATEMENT)
        createANewCar((car) -> {
            car.name += "-Modified";
            // do other stuff
            return null;
        });

        System.out.println("---------------- ENDING LAMBDA TEST ----------------");

    }

    private void createANewCar(Function<Car, String> callBack) {

        Car c = new Car();
        c.name = "Panda";
        c.immatriculation = 123456678910L;
        c.nbCV = 5;

        System.out.println("Car created : " + c);

        if (callBack != null) {
            String result = callBack.apply(c);
            System.out.println("With callBack : " + c);
            System.out.println("Return callBack value : " + result);
        }

    }

    private static class Car {
        String name;
        int nbCV;
        long immatriculation;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

}
