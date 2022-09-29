package w2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ShellSort {

    public static void main(String[] args) {
        int[] a = new int[]{
                3, 4, 7, 9, 2, 6, 8, 12, 11, 1, 5,
        };

        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        sort(a);
        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
    }

    private static void sort(int[] a) {
        int n = a.length;


        var h_sorting = 3;
        for (int h = ((n % h_sorting != 0) ? h_sorting * (n / h_sorting) + 1 : n / h_sorting); h >= 1; h = h / h_sorting) { //h-sorting until 1
            System.out.println("h: " + h);
            for (int i = h; i < n; i += 1) {    //every hth must be sorted

                for (int j = i; j - h >= 0 && a[j] < a[j - h]; j -= h) {
                    int t = a[j];
                    a[j] = a[j - h];
                    a[j - h] = t;
                }

                System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));

            }
        }

    }
}
