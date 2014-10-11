package fr.pjthin.root.pattern.specification.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import fr.pjthin.root.pattern.specification.impl.example1.ElementPOJO;
import fr.pjthin.root.pattern.specification.impl.example1.RuleLimittedPrice;
import fr.pjthin.root.pattern.specification.impl.example1.RuleMandatoryForElement;
import fr.pjthin.root.pattern.specification.impl.example2.MyCarPOJO;
import fr.pjthin.root.pattern.specification.impl.example2.RuleExistingBrand;
import fr.pjthin.root.pattern.specification.impl.pojo.GenericElement;

public class RuleTest {

    @Test
    public void testRuleLimittedPrice() {
        Assert.assertFalse(isRuleLimittedPrice(new BigDecimal(5)));

        Assert.assertTrue(isRuleLimittedPrice(new BigDecimal(50)));
    }

    private boolean isRuleLimittedPrice(BigDecimal number) {
        ElementPOJO elem = new ElementPOJO();
        elem.setPrice(number);

        GenericElement element = new GenericElement();
        element.put(elem);

        return new RuleLimittedPrice().isSatisfiedBy(element);
    }

    @Test
    public void testMandatoryForElement() {
        Assert.assertFalse(isRuleMandatory(""));

        Assert.assertTrue(isRuleMandatory("Apple"));
    }

    private boolean isRuleMandatory(String name) {
        ElementPOJO elem = new ElementPOJO();
        elem.setName(name);

        GenericElement element = new GenericElement();
        element.put(elem);

        return new RuleMandatoryForElement().isSatisfiedBy(element);
    }

    @Test
    public void testMandatoryAndLimitted() {
        Assert.assertTrue(isMandatoryAndLimitted("Apple", new BigDecimal(50)));

        Assert.assertFalse(isMandatoryAndLimitted("", new BigDecimal(50)));

        Assert.assertFalse(isMandatoryAndLimitted("", new BigDecimal(5)));

        Assert.assertFalse(isMandatoryAndLimitted("Apple", new BigDecimal(5)));
    }

    private boolean isMandatoryAndLimitted(String name, BigDecimal price) {
        ElementPOJO elem = new ElementPOJO();
        elem.setName(name);
        elem.setPrice(price);

        GenericElement element = new GenericElement();
        element.put(elem);

        return new RuleLimittedPrice().and(new RuleMandatoryForElement()).isSatisfiedBy(element);
    }

    @Test
    public void testMultiObjectOnRules() {

        // First element to controle
        ElementPOJO elem = new ElementPOJO();
        elem.setName("Apple");
        elem.setPrice(new BigDecimal(5));

        // Second element to controle
        MyCarPOJO car = new MyCarPOJO();
        car.setCarBrand("FIAT");
        car.setName("PANDA");
        car.setPlaceNumber(5);
        car.setPower(3.4F);

        GenericElement element = new GenericElement();
        element.put(elem);
        element.put(car);

        Assert.assertTrue(new RuleLimittedPrice().and(new RuleMandatoryForElement()).or(new RuleExistingBrand())
                .isSatisfiedBy(element));

    }
}
