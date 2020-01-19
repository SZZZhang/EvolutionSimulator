package gameEngine.ADT;

public interface ArrayStack<T> {
	/**
	 * Implements a LIFO stack using an array
	 *
	 * @param <T>
	 */

	/*
	 * Places (or pushes) a node on the stack
	 */
	public void push(Node<T> n);

	/*
	 * Removes (or pops) a node from the top of the stack
	 */
	public Node<T> pop();

	/*
	 * Returns the node at the top of the stack but does not remove it
	 */
	public Node<T> peek();

	/*
	 * returns the number of nodes on the stack
	 */
	public int size();

	/*
	 * Returns true if the stack is empty
	 */
	public boolean isEmpty();

	public String toString();
}