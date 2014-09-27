package fr.pjthin.root.test;

import org.junit.Test;

public class Java8Test {

    @Test
    public void testStringSwitch() {
        String myValue = "FR";

        switch (myValue) {
        case "FR":
            System.out.println("Welcome to France !");
            break;

        default:
            System.out.println("Your country is : " + myValue);
            break;
        }
    }

}
