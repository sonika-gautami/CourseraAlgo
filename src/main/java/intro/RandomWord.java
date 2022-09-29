package intro;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {

        String word = StdIn.readString();

        String champion = word;
        Integer i = 1;
        while (!StdIn.isEmpty()) {
            if (StdRandom.bernoulli(1 / (i))) {
                champion = word;
            }
            word = StdIn.readString();
            i++;
        }
        StdOut.println(champion);
    }
}
