package gameEngine.ADT;

public interface ArrayListInterface<T> {
	/**
	 * Adds a node to the end of this array list.
	 * @param n
	 */
	public void addNode(Node<T> n);

	/*
	 * Insert a ADT.Node n at the ith position of this list
	 */
	public void insertNode(Node<T> n, int i);

	/*
	 * Removes a given node from the array list
	 */
	public void removeNode(Node<T> n);

	/*
	 * Removes the ith node from the array list and return it
	 */
	public Node<T> removeNode(int i);

	/*
	 * Returns the ith node from the array list
	 */
	public Node<T> getNode(int i);

	public Node<T> getFirstNode();

	public Node<T> getLastNode();

	public int size();

	/*
	 * Returns a String representation of this array list, each node is separated by a space
	 */
	public String toString();
}