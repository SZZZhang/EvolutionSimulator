package gameEngine.ADT;

public class MyLListStack implements LListStack {

    LinkedList stack = new LinkedList();

    @Override
    public void push(Node n) {
        stack.addNode(n);
    }

    @Override
    public Node pop() {
        return stack.removeNode(stack.size());
    }

    @Override
    public Node peek() {
        return stack.getLastNode();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        if (stack.size() == 0) return true;
        return false;
    }
}
