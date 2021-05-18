package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_78_Subsets {
    /**
        Given a set of DISTINCT integers, nums, return all possible subsets (the power set).

        Note: The solution set must not contain duplicate subsets.

        For example,
        If nums = [1,2,3], a solution is:

        [
          [3],
          [1],
          [2],
          [1,2,3],
          [1,3],
          [2,3],
          [1,2],
          []
        ]
     */

    /**
     * Soltuion 1 : DFS
     * Time : O(2 ^ n)
       Example : [1, 2, 3]
         []

         [1]
         [1,2]
         [1,2,3]

         [1,3]

         [2]
         [2,3]

         [3]

         把给定元素看成图的结构：
         1-2-3
         | |
         2 3
         |
         3
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length == 0) return res;
        helper(res, nums, new ArrayList<Integer>(), 0);
        return res;
    }

    public static void helper(List<List<Integer>> res, int[] nums, List<Integer> temp, int index) {
        res.add(new ArrayList<Integer>(temp));

        for (int i = index; i < nums.length; i++) {
            temp.add(nums[i]);
            helper(res, nums, temp, i + 1);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * Solution 2 : BFS
     * 用 BFS 来解决该问题时，层级关系如下：
         第一层: []
         第二层: [1] [2] [3]
         第三层: [1, 2] [1, 3], [2, 3]
         第四层: [1, 2, 3]


         每一层的节点都是上一层的节点拓展而来。
     */
    public List<List<Integer>> subsets2_JiuZhang(int[] nums) {
        // List vs ArrayList （Interviews.Google）
        List<List<Integer>> results = new LinkedList<>();

        if (nums == null) {
            return results; // 空列表
        }

        //!!! "subset.get(subset.size() - 1) < nums[i]" is based on the fact that nums is sorted.
        Arrays.sort(nums);

        // BFS
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.offer(new LinkedList<Integer>());

        while (!queue.isEmpty()) {
            List<Integer> subset = queue.poll();
            results.add(subset);

            for (int i = 0; i < nums.length; i++) {
                /**
                 * 每次在cur上加一个元素 - 先deep copy，然后加。再把新的list加入res.
                 */
                if (subset.size() == 0 || subset.get(subset.size() - 1) < nums[i]) {
                    List<Integer> nextSubset = new LinkedList<Integer>(subset); //!!!Deep copy here
                    nextSubset.add(nums[i]);
                    queue.offer(nextSubset);
                }
            }
        }

        return results;
    }

    /**
     * Solution 3 : 非递归版本，利用二进制的方式逐个枚举 subsets。
     */
    public List<List<Integer>> subsets3_JiuZhang(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int n = nums.length;
        Arrays.sort(nums);

        // 1 << n is 2^n
        // each subset equals to an binary integer between 0 .. 2^n - 1
        // 0 -> 000 -> []
        // 1 -> 001 -> [1]
        // 2 -> 010 -> [2]
        // ..
        // 7 -> 111 -> [1,2,3]
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> subset = new ArrayList<Integer>();
            for (int j = 0; j < n; j++) {
                // check whether the jth digit in i's binary representation is 1
                if ((i & (1 << j)) != 0) {//!!! "!=0", NOT "==1"
                    subset.add(nums[j]);//!!! nums[j], NOT nums[i]
                }
            }
            result.add(subset);
        }
        return result;
    }
}
