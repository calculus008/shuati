package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 11/2/18.
 */
public class LI_402_Continuous_Subarray_Sum {
    /**
         Given an integer array, find a continuous subarray where the sum of numbers is the biggest.
         Your code should return the index of the first number and the index of the last number.
         (If their are duplicate answer, return the firstone you find)

         Example
         Give [-3, 1, 3, -3, 4], return [1,4].

         Medium
     */

    public List<Integer> continuousSubarraySum(int[] A) {
        List<Integer> res = new ArrayList<>();
        if (A == null || A.length == 0) return res;

        int max = Integer.MIN_VALUE;
        int sum = 0;
        res.add(0);
        res.add(0);
        int start = 0, end = 0;

        for (int i = 0; i < A.length; i++) {
            if (sum < 0) {
                sum = A[i];
                start = end = i;
            } else {
                sum += A[i];
                end = i;
            }

            if (sum > max) {
                max = sum;
                res.set(0, start);
                res.set(1, end);
            }
        }

        return res;
    }
}
