package gameEngine.ADT;

public class MyArrayList implements ArrayListInterface {

    private int DEFAULT_ARRAY_SIZE = 8;

    private int arrayLength = DEFAULT_ARRAY_SIZE;
    private int size = 0;
    private Node array[] = new Node[DEFAULT_ARRAY_SIZE];

    @Override
    public void addNode(Node n) {
        if (size + 1 > arrayLength) {
            doubleArraySize();
        }
        array[size++] = n;
    }

    @Override
    public void insertNode(Node n, int i) {
        if (size + 1 > arrayLength) {
            doubleArraySize();
        }
        array[i] = n;
        size++;
    }

    @Override
    public void removeNode(Node n) {
        for (int i = 0; i < size; i++) {
            if (array[i] == n) {
                removeNode(i);
            }
        }
        size--;
    }

    @Override
    public Node removeNode(int i) {
        Node n = array[i];
        for (int j = i; j < size; j++) {
            array[j] = array[j + 1];
        }
        size--;
        return n;
    }

    @Override
    public Node getNode(int i) {
        return array[i];
    }

    @Override
    public Node getFirstNode() {
        if (size == 0) return null;
        return array[0];
    }

    @Override
    public Node getLastNode() {
        if (size == 0) return null;
        return array[size - 1];
    }

    @Override
    public int size() {
        return size;
    }

    private void doubleArraySize() {
        arrayLength *= 2;
        Node newArray[] = new Node[arrayLength];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
    }
}
