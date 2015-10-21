package fr.pjthin.root.test.spring.spel;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ELTest {

    private SpelExpressionParser parser;

    @Before
    public void init() {
        parser = new SpelExpressionParser();
    }

    @Test
    public void helloWorldTest() {
        sysout(parser.parseExpression("'hello world!'"));
    }

    @Test
    public void helloWorldBytesTest() {
        sysout(parser.parseExpression("'hello world!'.bytes"));
    }

    @Test
    public void withObjectTest() {
        Expression e = parser.parseExpression("name");
        Car car = new Car();
        car.setName("Ma Panda");
        System.out.println(e.getValue(new StandardEvaluationContext(car), String.class));
    }

    @Test
    public void sumTest() {
        sysout(parser.parseExpression("1+1"));
    }

    private void sysout(Expression e) {
        System.out.println(e.getExpressionString() + " = " + e.getValue());
    }

    private static class Car {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
