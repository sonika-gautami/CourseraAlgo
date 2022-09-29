package w1;

import java.util.stream.IntStream;

public class QuickUnionUF {
    private int[] array;

    public QuickUnionUF(int n) {
        array = new int[n];
        IntStream.of(0, n).forEach(i -> array[i] = i);
    }

    public int root(int e) {
        if (array[e] != e) return root(array[e]);
        return e;
    }

    public boolean connected(int e1, int e2) {
        return root(e1) == root(e2);
    }

    public void union(int e1, int e2) {
        array[root(e1)] = root(e2);
    }
}
