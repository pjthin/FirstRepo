package fr.pjthin.root.pattern.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(final T candidate);

    Specification<T> and(Specification<T> specification);

    Specification<T> or(Specification<T> specification);

    Specification<T> not(Specification<T> specification);

}
