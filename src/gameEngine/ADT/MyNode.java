package gameEngine.ADT;

public class MyNode<T> implements Node<T> {

    private T value;
    Node<T> prev;
    Node<T> next;

    public MyNode(T value) {
        this.value = value;
    }

    public MyNode(){

    };

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T n) {
        value = n;
    }

    @Override
    public void setNext(Node n) {
        next = n;
    }

    @Override
    public void setPrev(Node n) {
        prev = n;
    }

    @Override
    public Node<T> getNext() {
        return next;
    }

    @Override
    public Node<T> getPrev() {
        return prev;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
