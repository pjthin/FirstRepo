package fr.pjthin.root.pattern.specification.impl.example1;

import java.util.List;

import fr.pjthin.root.pattern.specification.impl.AbstractRules;

public class RuleMandatoryForElement extends AbstractRules<ElementPOJO> {

    @Override
    protected Class<ElementPOJO> getElementClass() {
        return ElementPOJO.class;
    }

    @Override
    protected boolean isRuleSatisfiedBy(List<ElementPOJO> list) {
        return list.get(0).getName() != null && list.get(0).getName() != "";
    }

}
