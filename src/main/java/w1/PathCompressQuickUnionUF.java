package w1;

import java.util.stream.IntStream;

/*
 lg* n (read "log star") is the iterated logarithm.

It is defined as recursively as
lg* n =       0                if n <= 1
              1 + lg*(lg n)    if n > 1

it is the number of times that you have to iterate logarithm
 before the result is less than or equal to 1.

 log start N can be ~ considered as 1
*/
//weighted by depth/height size; flatten the nodes with (grand)root; N* log*N(log star N)
public class PathCompressQuickUnionUF {
    private int[] array;

    public PathCompressQuickUnionUF(int n) {
        array = new int[n];
        IntStream.of(0, n).forEach(i -> array[i] = i);
    }

    public int root(int e) {
        if (array[e] != e) {
            var t = array[e];
            array[e] = array[array[e]]; //Change Root of this ele to its parent's (grand-parent)
            return root(t);
        }
        return e;
    }

    public boolean connected(int e1, int e2) {
        return root(e1) == root(e2);
    }

    public void union(int e1, int e2) {
        array[root(e1)] = root(e2);
    }
}
