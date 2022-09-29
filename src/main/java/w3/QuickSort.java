package w3;

import java.util.Arrays;
import java.util.stream.Collectors;

public class QuickSort {
    public static void main(String[] args) {
        int[] a = new int[]{
                3, 4, 7, 9, 2, 6, 8, 12, 11, 1, 5,
                //0, 1, 2, 3, 4, 5, 6, 7, 8, 9
        };

        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        sort(a);
        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
    }

    private static void sort(int[] a) {
        internalSort(a, 0, a.length - 1, 3);
        System.out.println("-------------------------Top 3 Sorted-------------------------");
        internalSort(a, 0, a.length - 1);
        System.out.println("-------------------------All Sorted-------------------------");
        System.out.println(kthElement(a, 5 - 1));
        System.out.println("-------------------------Finding kth largest element-------------------------");
    }

    private static int kthElement(int[] a, int k) {
        var lo = 0;
        var hi = a.length - 1;
        var pivot = hi;

        while (hi > lo) {
            pivot = partition(a, lo, hi);
            if (pivot < k) hi = pivot - 1;
            else if (pivot > k) lo = pivot + 1;
            else break;
        }
        return a[k];
    }

    private static void internalSort(int[] a, int lo, int hi, int top) {
        if (lo < hi) {
            int pivot = partition(a, lo, hi);
            System.out.println(pivot);
            internalSort(a, lo, pivot - 1, top);
            if (top > (pivot + 1)) internalSort(a, pivot + 1, hi, top);
        }
    }

    private static void internalSort(int[] a, int lo, int hi) {
        if (lo < hi) {
            int pivot = partition(a, lo, hi);
            System.out.println(pivot);
            internalSort(a, lo, pivot - 1);
            internalSort(a, pivot + 1, hi);
        }
    }

    private static int partition(int[] a, int lo, int hi) {
        int pivot = hi;
        int left = lo - 1;
        int right = hi;

        while (true) {
            while (a[++left] < a[pivot]) {
                if (left >= hi) break;
            }
            while (a[--right] > a[pivot]) {
                if (right <= lo) break;
            }
            if (left >= right) break;

            int t = a[left];
            a[left] = a[right];
            a[right] = t;
            System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        }
        int t = a[pivot];
        a[pivot] = a[left];
        a[left] = t;

        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        return left;
    }


}
