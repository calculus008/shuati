package lintcode;

/**
 * Created by yuank on 7/12/18.
 */
public class LI_464_Sort_Integers_II {
    /**
         Given an integer array, sort it in ascending order. Use quick sort, merge sort, heap sort or any O(nlogn) algorithm.

         Example
         Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
     */

    public void sortIntegers2(int[] A) {
        if (A == null || A.length == 0) {
            return;
        }

        quickSort(A, 0, A.length - 1);
    }

    public void quickSort(int[] A, int start, int end) {
        if (start >= end) {//!!!
            return;
        }

        int i = start;
        int j = end;
        int pivot = A[(i + j) / 2];

        //Partition
        while (i <= j) {
            while (i <= j && A[i] < pivot) {
                i++;
            }

            while (i <= j && A[j] > pivot) {
                j--;
            }

            if (i <= j) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                i++;
                j--;
            }
        }

        quickSort(A, start, j);
        quickSort(A, i, end);
    }
}
