package lintcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 8/12/18.
 */
public class LI_051_Previous_Permutation {
    /**
         Given a list of integers, which denote a permutation.

         Find the previous permutation in ascending order.

         Example
         For [1,3,2,3], the previous permutation is [1,2,3,3]

         For [1,2,3,4], the previous permutation is [4,3,2,1]

         Notice
         The list may contains duplicate integers.

         Medium
     */

    public List<Integer> previousPermuation(List<Integer> nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null) {
            return res;
        }

        int len = nums.size();
        int i = len - 1;
        while (i > 0 && nums.get(i) >= nums.get(i -1)) {
            i--;
        }

        if (i != 0) {//!!!
            int j = len - 1;
            while (nums.get(j) >= nums.get(i - 1)) {
                j--;
            }

            swap(nums, i - 1, j);
        }

        reverse(nums, i, len - 1);

        return nums;
    }

    private void swap(List<Integer> nums, int i, int j) {
        Collections.swap(nums, i, j);
    }

    private void reverse(List<Integer> nums, int i, int j) {
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
}
