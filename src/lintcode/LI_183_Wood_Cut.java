package lintcode;

/**
 * Created by yuank on 7/6/18.
 */
public class LI_183_Wood_Cut {
    /**
         Given n pieces of wood with length L[i] (integer array). Cut them into small pieces to
         guarantee you could have equal or more than k pieces with the same length. What is the
         longest length you can get from the n pieces of wood? Given L & k, return the maximum
         length of the small pieces.

         Example
         For L=[232, 124, 456], k=7, return 114.

         Challenge
         O(n log Len), where Len is the longest length of the wood

         Hard
     */

    /**
     * Binary Search
     *
     * Time : O(n * log(Len))
     *
     */
    public int woodCut(int[] L, int k) {
        int max = 0;
        for (int l : L) {
            max = Math.max(max, l);
        }

        int start = 1;
        int end = max;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (count(L, mid) >= k) {//if we can have more than k pieces of woods of length mid, we can increase length
                start = mid;
            } else {//if we can have fewer than k pieces of woods of length mid, we should decrease length
                end = mid;
            }
        }

        /**
         * we want max length, so check end first.
         */
        if (count(L, end) >= k) {
            return end;
        }

        if (count(L, start) >= k) {
            return start;
        }

        return 0;//!!!
    }

    /**
     * count how many pieces of woods we can get if we cut them into length len.
     */
    private int count(int[] L, int len) {
        int sum = 0;
        for (int l : L) {
            sum += l / len; //!!! 必须是（l/len), l可以被cut成多块长度为len的木头。
        }
        return sum;
    }
}
