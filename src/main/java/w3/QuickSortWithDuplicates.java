package w3;

import java.util.Arrays;
import java.util.stream.Collectors;

public class QuickSortWithDuplicates {
    public static void main(String[] args) {
        int[] a = new int[]{
                3, 4, 7, 9, 2, 6, 2, 8, 12, 11, 8, 2, 1, 5, 2
                //0, 1, 2, 3, 4, 5, 6, 7, 8, 9
        };

        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        sort(a);
        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
    }

    private static void sort(int[] a) {
        internalSort(a, 0, a.length - 1);
        System.out.println("-------------------------All Sorted-------------------------");
    }

    private static void internalSort(int[] a, int lo, int hi) {
        if (hi <= lo) return;

        var pivot = a[lo];

        var left = lo;
        var right = hi;
        var index = lo;

        while (index <= right) {
            System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
            if (pivot > a[index]) {
                exchange(a, left, index);
                left++;
                index++;
            } else if (pivot < a[index]) {
                exchange(a, right, index);
                right--;
            } else if (pivot == a[index]) {
                index++;
            }
        }

        internalSort(a, lo, left - 1);
        internalSort(a, right + 1, hi);
    }

    private static void exchange(int a[], int x, int y) {
        var t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    private static int pivot(int a[]) {
        var n = a.length;
        if (n < 50) return n / 2;   //small-size -> mid
        else if (n < 300) {         //medium-size -> median of 3
            var median_of_3 = new int[]{a[n / 2], a[n / 2 - 1], a[n / 2 + 1]};
            Arrays.sort(median_of_3);
            return median_of_3[1];
        } else {    //ninther median
            var median_of_3_1 = new int[]{a[n - 1], a[n - 2], a[n - 3]};
            var median_of_3_2 = new int[]{a[n / 2], a[n / 2 - 1], a[n / 2 + 1]};
            var median_of_3_3 = new int[]{a[1], a[2], a[0]};
            Arrays.sort(median_of_3_1);
            Arrays.sort(median_of_3_2);
            Arrays.sort(median_of_3_3);

            var median_of_3 = new int[]{
                    median_of_3_1[1],
                    median_of_3_2[1],
                    median_of_3_3[1],
            };
            Arrays.sort(median_of_3);
            return median_of_3[1];
        }
    }

}
