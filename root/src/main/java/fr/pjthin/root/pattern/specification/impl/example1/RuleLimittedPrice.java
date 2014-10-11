package fr.pjthin.root.pattern.specification.impl.example1;

import java.math.BigDecimal;
import java.util.List;

import fr.pjthin.root.pattern.specification.impl.AbstractRules;

public class RuleLimittedPrice extends AbstractRules<ElementPOJO> {

    private static final BigDecimal MINIMAL_PRICE = new BigDecimal(10L);

    @Override
    protected Class<ElementPOJO> getElementClass() {
        return ElementPOJO.class;
    }

    @Override
    protected boolean isRuleSatisfiedBy(List<ElementPOJO> list) {
        return MINIMAL_PRICE.compareTo(list.get(0).getPrice()) <= 0 ? Boolean.TRUE : Boolean.FALSE;
    }

}
