package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 10/1/18.
 */
public class LI_249_Count_Of_Smaller_Number_Before_Itself {
    /**
         Give you an integer array (index from 0 to n-1, where n is the size of this array, data value from 0 to 10000).
         For each element Ai in the array, count the number of element before this element Ai is smaller than it and
         return count number array.

         Example
         For array [1,2,7,8,5], return [0,1,2,3,2]

         Medium
     */

    /**
     * Binary Indexed Tree
     * Time : O(nlog)
     * Space : O(n)
     *
     * Similar problem : LE_315_Count_Of_Smaller_Numbers_After_Self
     *                   LE_307_Range_Sum_Query_Mutable
     */

    public List<Integer> countOfSmallerNumberII(int[] A) {
        List<Integer> res = new ArrayList<>();
        if (A == null || A.length == 0) return res;

        discretization(A);

        int[] bit = new int[A.length + 1];
        for (int i = 0; i < A.length; i++) {
            res.add(query(bit, A[i] - 1));
            update(bit, A[i]);//here nums[i] value is rank),it's already 1-based
        }

        return res;
    }

    private void discretization(int[] A) {
        /**
         * !!! sort on the cloned array, not input array itself
         * */
        int[] B = A.clone();
        Arrays.sort(B);
        for (int i = 0; i < A.length; i++) {
            A[i] = Arrays.binarySearch(B, A[i]) + 1;
        }
    }

    private int query(int[] bit, int i) {
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            i -= lowbit(i);
        }
        return sum;
    }

    //!!!here delta is 1
    private void update(int[] bit, int i) {
        while (i < bit.length) {
            bit[i]++;
            i += lowbit(i);
        }
    }

    private int lowbit(int x) {
        return x & (-x);
    }
}
