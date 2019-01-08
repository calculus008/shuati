package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 2/2/18.
 */

/**
  Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj)
  of elements in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.

  If there are multiple solutions, return any subset is fine.
 **/

public class LE_368_Largest_Devision_Set {

    public static List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        int[] count = new int[n];
        int[] pre = new int[n];
        Arrays.sort(nums);
        int max = 0, index = -1;

        for (int i = 0; i < n; i++) {
            System.out.println("i=" + i + ", init count["+i+"] to 1" ) ;
            count[i] = 1;
            pre[i] = -1;
            for (int j = i - 1; j >= 0; j--) {
                System.out.println("  i=" + i+ ", j=" + j);
                if (nums[i] % nums[j] == 0) {
                    System.out.println("  nums[" + i+ "]=" + nums[i] + " nums[" + j + "]=" + nums[j]);
                    System.out.println("  count[" + i+ "]=" + count[i] + " count[" + j + "]=" + count[j]);

                    if (1 + count[j] > count[i]) {
                        count[i] = count[j] + 1;
                        pre[i] = j;
                        System.out.println("   set count[" + i +"]=" +count[i] + ", pre[" +i+"]=" + pre[i]);
                    }
                }
            }
            if (count[i] > max) {
                max = count[i];
                index = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (index != -1) {
            res.add(nums[index]);
            index = pre[index];
        }
        return res;
    }

    public static void main(String [] args) {
//        int[]  input = {1, 2, 4, 6, 8};
        int[]  input = {1, 2, 7, 14};

        largestDivisibleSubset(input);
    }

}
