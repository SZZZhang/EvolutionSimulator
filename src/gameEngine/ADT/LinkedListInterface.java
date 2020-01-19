package gameEngine.ADT;

public interface LinkedListInterface<T> {
	/**
	 * Adds a node to the end of this linked list.
	 * @param n
	 */
	public void addNode(Node<T> n);

	/*
	 * Insert a ADT.Node n at the ith position of this list
	 */
	public void insertNode(Node<T> n, int i);

	/*
	 * Removes a given node from the linked list
	 */
	public void removeNode(Node<T> n);

	/*
	 * Removes the ith node from the linked list and return it
	 */
	public Node<T> removeNode(int i);

	/*
	 * Returns the ith node from the linked list
	 */
	public Node<T> getNode(int i);

	public Node<T> getFirstNode();

	public Node<T> getLastNode();

	public int size();

	/*
	 * Returns a String representation of this linked list, each node is separated by a space
	 */
	public String toString();

}