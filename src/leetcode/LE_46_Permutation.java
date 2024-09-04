package leetcode;

import java.util.*;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_46_Permutation {
    /**
        Given a collection of distinct numbers, return all possible permutations.

        For example,
        [1,2,3] have the following permutations:
        [
          [1,2,3],
          [1,3,2],
          [2,1,3],
          [2,3,1],
          [3,1,2],
          [3,2,1]
        ]

        Variation :
        Parity_Permutation
     **/


    class Solution_DFS_clean {
        /**
         * Time : O(n * n!)
         * Given a set of length n, the number of permutations is n! (n factorial).
         * There are n options for the first number, n−1 for the second, and so on.
         * For each of the n! permutations, we need O(n) work to copy curr into the answer.
         */
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            helper(nums, res, new ArrayList<>());
            return res;
        }

        public void helper(int[] nums, List<List<Integer>> res, List<Integer> cur) {
            if (cur.size() == nums.length) {
                res.add(new ArrayList<>(cur));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (cur.contains(nums[i])) continue;
                cur.add(nums[i]);
                helper(nums, res, cur);
                cur.remove(cur.size() - 1);
            }
        }
    }


    /**
        Solution 1 : use visited[] to tell if current number is in the sequence.
     */
    public List<List<Integer>> permute_JiuZhang(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null) {
            return res;
        }


        helper(nums, res, new ArrayList<>(), new boolean[nums.length]);
        return res;
    }

    private void helper(int[] nums, List<List<Integer>> res, List<Integer> temp, boolean[] visited) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }

        //!!! start from 0
        for (int i = 0; i < nums.length; i++) {
            //!!! use visited[]
            if (visited[i])  {
                continue;
            }

            temp.add(nums[i]);
            visited[i] = true;
            helper(nums, res, temp, visited);
            temp.remove(temp.size() - 1);
            visited[i] = false;
        }
    }


    //Solution 2, Time : O(n!), Space : O(n)
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == nums || nums.length == 0) return res;

        helper(nums, new ArrayList<Integer>(), res);
        return res;
    }

    //O(n * n!)
    private static void helper(int[] nums, List<Integer> temp, List<List<Integer>> res) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (temp.contains(nums[i])) continue;
            temp.add(nums[i]);
            helper(nums, temp, res);
            temp.remove(temp.size() - 1);
        }
    }

    //Solution 3 : O(n!)
    private void helper1(int[] nums, int start, List<List<Integer>> res) {
        if (start == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int num : nums) {
                list.add(num);
            }
            res.add(list);
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            helper1(nums, start + 1, res);
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
