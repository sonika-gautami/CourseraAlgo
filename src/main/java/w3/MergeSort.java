package w3;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MergeSort {

    public static void main(String[] args) {
        int[] a = new int[]{
                3, 4, 7, 9, 2, 6, 8, 12, 11, 1, 5,
        };

        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));
        sort(a);
        System.out.println(Arrays.stream(a).mapToObj(e1 -> e1 + "").collect(Collectors.joining(", ")));

    }

    public static void sort(int[] a) {
        int mid = a.length / 2;
        int low = 0;
        int high = a.length;
        internalSort(a, 0, mid + 1);
        internalSort(a, mid + 1, a.length);
        merge(a, 0, mid + 1, a.length);
    }

    private static void merge(int[] a, int low, int mid, int high) {
        //if already sorted
        if (a[mid - 1] < a[mid]) return;

        int left = low;
        int right = mid;

        int[] sorted = new int[high - low];
        int i = 0;
        while (left < mid && right < high) {
            if (a[left] < a[right]) sorted[i++] = a[left++];
            else sorted[i++] = a[right++];
        }
        System.out.println(high + " -- " + low + " -- " + mid + " >>" + sorted.length);
        while (right < high) {
            sorted[i++] = a[right++];
        }
        while (left < mid) {
            sorted[i++] = a[left++];
        }

        int k = 0;
        for (i = low; i < high; i++) {
            a[i] = sorted[k];
            k++;
        }
    }

    private static void internalSort(int[] a, int low, int high) {
        if (high - low > 2) {
            int newMid = ((high - low) / 2) + 1;
            internalSort(a, low, low + newMid);
            internalSort(a, low + newMid, high);
            merge(a, low, low + newMid, high);
        } else {
            merge(a, low, low + 1, high);
        }
    }
}
