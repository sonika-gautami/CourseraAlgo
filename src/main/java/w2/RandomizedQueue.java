package w2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] ds;
    private int end = -1;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        ds = (Item[]) new Object[20];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        nullCheck(item);
        validateSize();
        ds[++end] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        validateRemove();
        int index = getRandomIndex();
        Item i = ds[index];
        ds[index] = null;
        size--;
        return i;
    }

    private int getRandomIndex() {
        int index = StdRandom.uniform(0, end + 1);
        while (ds[index] == null) {
            index = StdRandom.uniform(0, end + 1);
        }
        return index;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateRemove();
        int index = getRandomIndex();
        return ds[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private final int[] dsIndex;
        private int cursor;

        public RandomizedIterator() {
            dsIndex = new int[end + 1];
            for (int i = 0; i <= end; i++) {
                dsIndex[i] = i;
            }
            StdRandom.shuffle(dsIndex);
            cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < dsIndex.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no elements to read");
            return ds[dsIndex[cursor++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove on iterator not allowed");
        }
    }

    private void nullCheck(Item item) {
        if (item == null) throw new IllegalArgumentException("null not allowed");
    }

    private void validateRemove() {
        if (isEmpty()) throw new NoSuchElementException("remove on empty");
    }

    private void validateSize() {
        if (end + 1 == ds.length) {
            Item[] t = (Item[]) new Object[ds.length * 2];

            for (int i = 0; i <= end; i++) {
                t[i] = ds[i];
            }
            ds = t;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        for (Integer i : q) {
            StdOut.println(i);
        }

        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        StdOut.println(q.dequeue());
        System.out.println(q.size());
        System.out.println(q.isEmpty());
    }

}
