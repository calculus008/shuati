package Linkedin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ascending_Binary_Sorting {
    /**
     * 给一个十进制表示的数组 根据它们的二进制表示中‘1’的个数进行从小到大排序，若‘1’的个数相同，则十进制小的在前。
     * 例如[7,8,6,5]变成[8,5,6,7].
     */

    /**
     * https://www.geeksforgeeks.org/sort-array-according-count-set-bits/
     *
     * This is solution with counting sort.(Bucket sorting)x
     *
     * If no output order required, it should be O(n).
     * Here , "若‘1’的个数相同，则十进制小的在前", so we still need to do sorting.
     * So it is still O(nlogn)
     */

    public static int countBits(int a) {
        int count = 0;
        while (a > 0) {
            if ((a & 1) > 0) {
                count++;
            }
            a = a >> 1;
        }
        return count;
    }

     /**
      * Function to sort according to bit count
      * This function assumes that there are 32
      * bits in an integer.
      **/
    public static int[] sortBySetBitCount(int arr[]) {
        int[] res = new int[arr.length];
        List<List<Integer>> buckets = new ArrayList<>();

        int n = arr.length;

        /**
         * 1.init buckets
         */
        for (int i = 0; i < 32; i++) {
            buckets.add(new ArrayList<>());
        }

        /**
         * 2.count bits and put number into buckets
         */
        int idx = 0;
        for (int i = 0; i < n ; i++) {
            idx = countBits(arr[i]);
            buckets.get(idx - 1).add(arr[i]);
        }

        /**
         * 3.sort number in each bucket and put them into res
         */
        for (int i = 0, j = 0; i < 32; i++) {
            List<Integer> l = buckets.get(i);
            if (l.size() == 0) {
                continue;
            }

            Collections.sort(l);
            for (int num : l) {
                res[j++] = num;
            }
        }
        
        return res;
    }

    public static void main (String[] args) {
        int[] nums = {7,8,6,5};
        int[] res = sortBySetBitCount(nums);

        System.out.println(Arrays.toString(res));
    }

}
