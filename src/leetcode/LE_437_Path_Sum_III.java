package leetcode;

import common.TreeNode;

import java.util.HashMap;

public class LE_437_Path_Sum_III {
    /**
     * You are given a binary tree in which each node contains an integer value.
     *
     * Find the number of paths that sum to a given value.
     *
     * The path does not need to start or end at the root or a leaf, but it must
     * go downwards (traveling only from parent nodes to child nodes).
     *
     * The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
     *
     * Example:
     *
     * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
     *
     *       10
     *      /  \
     *     5   -3
     *    / \    \
     *   3   2   11
     *  / \   \
     * 3  -2   1
     *
     * Return 3. The paths that sum to 8 are:
     *
     * 1.  5 -> 3
     * 2.  5 -> 2 -> 1
     * 3. -3 -> 11
     *
     * Medium
     *
     * https://leetcode.com/problems/path-sum-iii
     *
     * Related:
     *  LE_560_Subarray_Sum_Equals_K
     */

    class Solution_clean {
        int count = 0;
        int k;
        HashMap<Long, Integer> map = new HashMap();

        public int pathSum(TreeNode root, int sum) {
            k = sum;
            helper(root, 0L);
            return count;
        }
        public void helper(TreeNode node, long currSum) {
            if (node == null) return;

            currSum += node.val;

            if (currSum == k) count++;

            count += map.getOrDefault(currSum - k, 0);
            map.put(currSum, map.getOrDefault(currSum, 0) + 1);

            helper(node.left, currSum);
            helper(node.right, currSum);

            map.put(currSum, map.get(currSum) - 1);
        }
    }

    class Solution {
        // https://leetcode.com/problems/path-sum-iii/editorial/
        int count = 0;
        int k;
        HashMap<Long, Integer> h = new HashMap();

        public void preorder(TreeNode node, long currSum) {
            if (node == null)
                return;

            // The current prefix sum
            currSum += node.val;

            // Here is the sum we're looking for
            if (currSum == k)
                count++;

            // The number of times the curr_sum − k has occurred already,
            // determines the number of times a path with sum k
            // has occurred up to the current node
            count += h.getOrDefault(currSum - k, 0);

            //Add the current sum into the hashmap
            // to use it during the child node's processing
            h.put(currSum, h.getOrDefault(currSum, 0) + 1);

            // Process the left subtree
            preorder(node.left, currSum);

            // Process the right subtree
            preorder(node.right, currSum);

            // Remove the current sum from the hashmap
            // in order not to use it during
            // the parallel subtree processing
            h.put(currSum, h.get(currSum) - 1);
        }

        public int pathSum(TreeNode root, int sum) {
            k = sum;
            preorder(root, 0L);
            return count;
        }
    }


    /**
     * Space: O(n) due to recursion.
     * Time: O(n^2) in worst case (no branching); O(nlogn) in best case (balanced tree).
     */
//    class Solution1 {
//        public int pathSum(TreeNode root, int sum) {
//            if (root == null) return 0;
//
//            return helper(root, sum)
//                    + pathSum(root.left, sum)
//                    + pathSum(root.right, sum);
//        }
//
//        private int helper(TreeNode node, int sum) {
//            if (node == null) {
//                return 0;
//            }
//
//            return (node.val == sum ? 1 : 0)
//                    + helper(node.left, sum - node.val)
//                    + helper(node.right, sum - node.val);
//        }
//    }
//
//    /**
//     * Time : O(n)
//     *
//     * https://leetcode.com/problems/path-sum-iii/discuss/91878/17-ms-O(n)-java-Prefix-sum-method
//     */
//    class Solution2 {
//        public int pathSum(TreeNode root, int sum) {
//            //key : the prefix sum,
//            //value : how many ways get to this prefix sum
//            HashMap<Integer, Integer> preSum = new HashMap();
//            preSum.put(0,1);
//            return helper(root, 0, sum, preSum);
//        }
//
//        public int helper(TreeNode root, int currSum, int target, HashMap<Integer, Integer> preSum) {
//            if (root == null) {
//                return 0;
//            }
//
//            currSum += root.val;
//            int res = preSum.getOrDefault(currSum - target, 0);
//            preSum.put(currSum, preSum.getOrDefault(currSum, 0) + 1);
//
//            res += helper(root.left, currSum, target, preSum) + helper(root.right, currSum, target, preSum);
//
//            preSum.put(currSum, preSum.get(currSum) - 1);//backtrack
//
//            return res;
//        }
//    }

}
