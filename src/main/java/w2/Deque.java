package w2;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

//  double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports adding and removing items from either the front or the back
/*
    Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
    Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
    Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
    Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
 */
public class Deque<Item> implements Iterable<Item> {

    private Item[] ds;

    private int start = -1;
    private int end = -1;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 3;
        ds = (Item[]) new Object[size];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return start == -1 || end == -1;
    }

    // return the number of items on the deque
    public int size() {
        return isEmpty() ? 0 : 1 + ((end + size - start) % size);
    }

    // add the item to the front
    public void addFirst(Item item) {
        nullCheck(item);
        validateSize();
        incrementS();
        ds[start] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        nullCheck(item);
        validateSize();
        incrementE();
        ds[end] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateRemove();
        Item item = ds[start];
        if (start == end) {
            end = -1;
            start = -1;
        } else {
            decrementS();
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateRemove();
        Item item = ds[end];
        if (start == end) {
            end = -1;
            start = -1;
        } else {
            decrementE();
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no elements to read");
            cursor++;
            return ds[(start + (cursor - 1) + size) % size];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove on iterator not allowed");
        }
    }


    private void incrementS() {
        if (start == -1) {
            end = 0;
            start = 0;
        } else {
            start = (start - 1 + size) % size;
        }
    }

    private void decrementS() {
        start = (start + 1 + size) % size;
    }

    private void incrementE() {
        if (end == -1) {
            end = 0;
            start = 0;
        } else {
            end = (end + 1 + size) % size;
        }
    }

    private void decrementE() {
        end = (end - 1 + size) % size;
    }

    private boolean isFull() {
        return size - 1 == (end + size - start) % size;
    }

    private void validateSize() {
        if (isFull()) {
            Item[] t = (Item[]) new Object[ds.length * 2];

            for (int i = 0; i < size; i++) {
                t[i] = ds[(start + i + size) % size];
            }
            start = 0;
            end = ds.length - 1;
            size = t.length;
            ds = t;
        }
    }

    private void nullCheck(Item item) {
        if (item == null) throw new IllegalArgumentException("null not allowed");
    }

    private void validateRemove() {
        if (isEmpty()) throw new NoSuchElementException("remove on empty");
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(2);
        deque.addLast(3);
        deque.addFirst(1);
        deque.addFirst(0);
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());
    }

}