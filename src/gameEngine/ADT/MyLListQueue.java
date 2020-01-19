package gameEngine.ADT;

public class MyLListQueue<T> implements LListQueue {
    LinkedList list = new LinkedList();

    @Override
    public void enqueue(Node node) {
        list.addNode(node);
    }

    @Override
    public Node dequeue() {
        return list.removeNode(0);
    }

    @Override
    public Node peek() {
        return list.getFirstNode();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        if (list.size() == 0) return true;
        return false;
    }
}
