package fr.pjthin.root.pattern.specification.impl;

import java.util.List;

import fr.pjthin.root.pattern.specification.LeafSpecification;
import fr.pjthin.root.pattern.specification.impl.pojo.GenericElement;

public abstract class AbstractRules<T> extends LeafSpecification<GenericElement> {

    protected abstract Class<T> getElementClass();

    protected abstract boolean isRuleSatisfiedBy(List<T> list);

    @SuppressWarnings("unchecked")
    @Override
    public boolean isSatisfiedBy(GenericElement candidate) {
        return isRuleSatisfiedBy((List<T>) candidate.get(getElementClass()));
    }

}
