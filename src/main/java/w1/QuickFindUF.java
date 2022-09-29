package w1;

import java.util.stream.IntStream;

public class QuickFindUF {
    private int[] array;

    public QuickFindUF(int n) {
        array = new int[n];
        IntStream.of(0, n).forEach(i -> array[i] = i);
    }

    public boolean connected(int e1, int e2) {
        return array[e1] == array[e2];
    }

    public void union(int e1, int e2) {
        if (!connected(e1, e2)) {
            var tobeReplace = array[e1];
            array[e1] = array[e2];
            IntStream.of(0, array.length)
                    .filter(i -> array[i] == tobeReplace)
                    .forEach(i -> array[i] = array[e2]);
        }
    }
}
