package Interviews.Apple;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Kth_Smallest_In_Two_Sorted_Array {
    /**
     * 两个排序数组，返回kth smallest, a variation of LE_21_Merge_Two_Sorted_Lists
     *
     * Time  : O(l1 + l2)
     * Space : O(1)
     */
    public static int getKthSmallest(int[] a1, int[] a2, int k) {
        if (k <= 0) throw new IllegalArgumentException();

        int l1 = a1.length;
        int l2 = a2.length;

        if (k > l1 + l2) throw new IllegalArgumentException();

        int p1 = 0, p2 = 0;

        int count = 0;
        int res = 0;

        /**
         * Same iteration logic as in LE_21_Merge_Two_Sorted_Lists, Solution2
         *
         * !!!
         * #1.
         * 只需要处理两种情况：
         * a1当前值 < a2当前值, a1当前值被采用，count++
         * otherwise (a1当前值 >= a2当前值), a2当前值被采用, count++
         */
        while (p1 < l1 && p2 < l2) {
            if (a1[p1] < a2[p2]) {
                count++;
                System.out.println(a1[p1] + ", count="+count);

                /**
                 * !!!
                 * #2.
                 * Must return here, different from LE_21_Merge_Two_Sorted_Lists,
                 * when we get the answer here, a1 and a2 may not go to the end,
                 * so the two "if" after the "while" loop won't be valid.
                 */
                if (count == k) {
                    return a1[p1];
                }

                p1++;
            } else {
                count++;
                System.out.println(a2[p2] + ", count="+count);

                if (count == k) {
                    return a2[p2];
                }

                p2++;
            }
        }


        System.out.println("p1=" + p1 + ", p2="+ p2);

        if (p1 < l1) {
            /**
             * !!!
             * #3
             * "p1 - 1": p1 already move to the next position, need to backoff one step.
             */
            int idx = k - count + p1 - 1;
            res = a1[idx];
        }

        if (p2 < l2) {
            int idx = k - count + p2 - 1;
            res = a2[idx];
        }

        return res;
    }

    public static void main(String[] args) {
        int[] a2 = {1, 1, 1, 7, 9, 10, 11, 12, 13};
        int[] a1 = {2, 4, 4, 8};
//        int[] a1 = {};

        System.out.println(getKthSmallest(a1, a2, 7));
    }
}
