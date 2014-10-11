package fr.pjthin.root.pattern.specification;

public abstract class AbstractSpecification<T> implements Specification<T> {

    protected Specification<T>[] specifications;

    @SafeVarargs
    public AbstractSpecification(Specification<T>... specifications) {
        this.specifications = specifications;
    }

    @Override
    public Specification<T> and(Specification<T> specification) {
        return new AndSpecification<T>(this, specification);
    }

    @Override
    public Specification<T> or(Specification<T> specification) {
        return new OrSpecification<T>(this, specification);
    }

    @Override
    public Specification<T> not(Specification<T> specification) {
        return new NotSpecification<T>(specification);
    }

}
