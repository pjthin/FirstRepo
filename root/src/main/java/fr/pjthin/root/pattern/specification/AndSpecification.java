package fr.pjthin.root.pattern.specification;

public class AndSpecification<T> extends AbstractSpecification<T> {

    @SafeVarargs
    public AndSpecification(Specification<T>... specifications) {
        super(specifications);
    }

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        boolean result = true;

        for (Specification<T> specification : this.specifications) {
            result &= specification.isSatisfiedBy(candidate);

            if (!result) {
                break;
            }
        }

        return result;
    }

}
