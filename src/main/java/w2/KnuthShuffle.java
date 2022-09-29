package w2;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class KnuthShuffle {

    public static void main(String[] args) {
        int[] a = new int[]{
                3, 4, 7, 9, 2, 6, 8, 12, 11, 1, 5,
        };

        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        shuffle(a);
        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
    }

    private static void shuffle(int[] a) {
        for (int i = 0; i < a.length; i++) {
            var j = new Random().nextInt(i + 1);
            var t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
    }
}
