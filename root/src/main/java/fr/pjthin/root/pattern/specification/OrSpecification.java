package fr.pjthin.root.pattern.specification;

public class OrSpecification<T> extends AbstractSpecification<T> {

    @SafeVarargs
    public OrSpecification(Specification<T>... specifications) {
        super(specifications);
    }

    @Override
    public boolean isSatisfiedBy(final T candidate) {
        boolean result = false;

        for (Specification<T> specification : this.specifications) {
            result |= specification.isSatisfiedBy(candidate);

            if (result) {
                break;
            }
        }

        return result;
    }

}
