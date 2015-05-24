package fr.pjthin.root.pattern.specification;

import org.springframework.util.Assert;

public class NotSpecification<T> extends AbstractSpecification<T> {

    public NotSpecification(Specification<T> specification) {
        super(specification);
    }

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        Assert.notEmpty(specifications, "No specification set");

        return !specifications[0].isSatisfiedBy(candidate);
    }

}
