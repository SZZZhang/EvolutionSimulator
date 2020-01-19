package gameEngine.ADT;

public class MyArrayQueue implements ArrayQueue {

    MyArrayList queue = new MyArrayList();

    @Override
    public void enqueue(Node node) {
        queue.addNode(node);
    }

    @Override
    public Node dequeue() {
        if (queue.size() > 1) {
            Node n = queue.getFirstNode();
            queue.removeNode(0);
            return n;
        }
        return null;
    }

    @Override
    public Node peek() {
        return queue.getFirstNode();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        if (queue.size() > 0) return false;
        return true;
    }
}
