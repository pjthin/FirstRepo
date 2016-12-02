package fr.pjthin.root;

public class Aggregate<U, V> {

	private U left;
	private V right;

	public Aggregate() {
	}

	public Aggregate(U left, V right) {
		this.left = left;
		this.right = right;
	}

	public U getLeft() {
		return left;
	}

	public void setLeft(U left) {
		this.left = left;
	}

	public V getRight() {
		return right;
	}

	public void setRight(V right) {
		this.right = right;
	}
}
