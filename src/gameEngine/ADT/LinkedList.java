package gameEngine.ADT;

public class LinkedList implements LinkedListInterface {

    private MyNode head = new MyNode();
    private MyNode tail = new MyNode();
    private int size = 0;

    public LinkedList() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    @Override
    public void addNode(Node n) {
        tail.getPrev().setNext(n);
        n.setPrev(tail.getPrev());
        tail.setPrev(n);
        n.setNext(tail);
        size++;
    }

    @Override
    public void insertNode(Node n, int i) {
        n.setPrev(getNode(i));
        n.setNext(getNode(i).getNext());

        getNode(i).setNext(n);
        getNode(i).getNext().setPrev(n);
        size++;
    }

    @Override
    public void removeNode(Node n) {
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());
        size--;
    }

    @Override
    public Node removeNode(int i) {
        Node node = getNode(i);
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        size--;
        return node;
    }

    @Override
    public Node getNode(int i) {
        Node currentNode = head.getNext();

        for (int j = 0; j < size; j++) {
            if (j == i) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }

        return null;
    }

    @Override
    public Node getFirstNode() {
        return head.getNext();
    }

    @Override
    public Node getLastNode() {
        return tail.getPrev();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        Node currentNode = head;
        String string = "";

        for (int i = 0; i < size; i++){
            currentNode = currentNode.getNext();
            string += currentNode.toString();
        }
        return string;

    }
}
