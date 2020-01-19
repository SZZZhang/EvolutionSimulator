package gameEngine.ADT;

public class MyArrayStack implements ArrayStack {

    MyArrayList stack = new MyArrayList();

    @Override
    public void push(Node n) {
        stack.addNode(n);
    }

    @Override
    public Node pop() {
        Node n = stack.getLastNode();
        stack.removeNode(stack.size() - 1);
        return n;
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
        if (stack.size() == 0) return false;
        return true;
    }
}
