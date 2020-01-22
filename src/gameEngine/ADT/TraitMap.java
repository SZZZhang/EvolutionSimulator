/*
Description: ADT that uses an array to implement a list of Traits. It sorts the
traits by the names of the traits lexicographically  and uses binary search to look up traits.
 */

package gameEngine.ADT;

import java.util.Arrays;

public class TraitMap {

    private int DEFAULT_ARRAY_SIZE = 8;

    private int arrayLength = DEFAULT_ARRAY_SIZE;
    private int size = 0;
    private Trait array[] = new Trait[DEFAULT_ARRAY_SIZE];

    public TraitMap(Trait[] arr) {
        array = arr;
        size = arr.length;
        sortTraits();
    }

    public void addTrait(Trait n) {
        if (size + 1 > arrayLength) {
            doubleArraySize();
        }
        array[size++] = n;

    }

    public void insertTrait(Trait n, int i) {
        if (size + 1 > arrayLength) {
            doubleArraySize();
        }
        array[i] = n;
        size++;
    }

    public void removeTrait(Trait n) {
        for (int i = 0; i < size; i++) {
            if (array[i] == n) {
                removeTrait(i);
            }
        }
        size--;
    }

    public Trait removeTrait(int i) {
        Trait n = array[i];
        for (int j = i; j < size; j++) {
            array[j] = array[j + 1];
        }
        size--;
        return n;
    }

    public Trait getTrait(int i) {
        return array[i];
    }

    public Trait getTrait(String name) {
        return array[getTraitIndex(name)];
    }

    public int getTraitIndex(String name) {
        return binSearch(name);
    }

    public Trait getFirstTrait() {
        if (size == 0) return null;
        return array[0];
    }


    public Trait getLastTrait() {
        if (size == 0) return null;
        return array[size - 1];
    }


    public int size() {
        return size;
    }

    private void doubleArraySize() {
        arrayLength *= 2;
        Trait newArray[] = new Trait[arrayLength];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
    }

    private void sortTraits() {
        Arrays.sort(array);
    }

    //binary searches for the index of a trait by its name
    private int binSearch(String name) {
        int high = size - 1, low = 0;

        while (low <= high) {
            int mid = (high + low) / 2;

            if (array[mid].getName().equals(name))
                return mid;

            //if the current element(mid) is lexigraphically less than name
            if (array[mid].getName().compareTo(name) < 0)
                low = mid + 1;

            else high = mid - 1;

        }

        //if element is not found
        return -1;

    }
}
