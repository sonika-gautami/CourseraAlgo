package w1;

import java.util.stream.IntStream;

//weighted by nodes-size (not depth/height) N*logN (instead N*N)
public class WeightedQuickUnionUF {
    private int[] array;
    private int[] sizeOf;

    public WeightedQuickUnionUF(int n) {
        array = new int[n];
        sizeOf = new int[n];
        IntStream.of(0, n).forEach(i -> {
            array[i] = i;
            sizeOf[i] = 1;
        });
    }

    public int root(int e) {
        if (array[e] != e) return root(array[e]);
        return e;
    }

    public boolean connected(int e1, int e2) {
        return root(e1) == root(e2);
    }

    public void union(int e1, int e2) {
        var rootE1 = root(e1);
        var rootE2 = root(e2);
        if (sizeOf[rootE1] > sizeOf[rootE2]) {
            array[rootE2] = rootE1;
            sizeOf[e1] += sizeOf[e2];
        } else {
            array[rootE1] = rootE2;
            sizeOf[e2] += sizeOf[e1];
        }
    }
}
