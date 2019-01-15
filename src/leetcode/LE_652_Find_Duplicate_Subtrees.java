package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_652_Find_Duplicate_Subtrees {
    /**
         Given a binary tree, return all duplicate subtrees.
         For each kind of duplicate subtrees, you only need to
         return the root node of any one of them.

         Two trees are duplicate if they have the same structure with same node values.

         Example 1:

               1
              / \
             2   3
            /   / \
           4   2   4
          /
         4
         The following are two duplicate subtrees:

           2
          /
         4
         and

         4
         Therefore, you need to return above trees' root in the form of a list.

         Medium
     */

    /**
     * https://www.youtube.com/watch?v=JLK92dbTt8k&index=11&list=PLLuMmzMTgVK7ug02DDoQsf50OtwVDL1xd
     *
     * Time and Space : O(n ^ 2)
     *
     *
     */
    class Solution1 {
        List<TreeNode> res;
        Map<String, Integer> map;

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            res = new ArrayList<>();
            map = new HashMap<>();

            helper(root);
            return res;
        }

        private String helper(TreeNode root) {
            if (root == null) {
                return "#";
            }

            /**
             * !!!
             */
            String key = root.val + "," + helper(root.left) + "," + helper(root.right);

            /**
             * !!!
             * Must count++ first, then put, if map.put(key, count++), the value
             * put in map is not plus 1.
             */
            int count = map.getOrDefault(key, 0);
            count++;
            map.put(key, count);

            /**
             * !!!
             * "count == 2" ensures the substree is only added once, if "count > 1",
             * there will be duplicate tree nodes
             */
            if (count == 2) {
                res.add(root);
            }

            return key;
        }
    }

    /**
     * Huahua's version of using using ID for unique subtree
     *
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution2 {
        // key -> [id, count]
        Map<Long, int[]> counts = new HashMap<>();
        List<TreeNode> ans = new ArrayList<>();

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            getId(root);
            return ans;
        }

        private int getId(TreeNode root) {
            if (root == null) return 0;

            /**
             * !!!
             * save the time to convert int to String,
             * use bit operation to combine root, left and right into a single long value as Key
             */
            long key = ((long)root.val) << 32 | getId(root.left) << 16 | getId(root.right);

            int[] id_count = counts.get(key);

            if (id_count == null) {
                id_count = new int[]{counts.size() + 1, 1};
                counts.put(key, id_count);
            } else if (++id_count[1] == 2) {
                ans.add(root);
            }

            return id_count[0];
        }
    }

    /**
     * Leetcode answer of using ID for unique subtree
     *
     */
    class Solution3 {
        int t;
        Map<String, Integer> trees;
        Map<Integer, Integer> count;
        List<TreeNode> ans;

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            t = 1;
            trees = new HashMap();
            count = new HashMap();
            ans = new ArrayList();
            lookup(root);
            return ans;
        }

        public int lookup(TreeNode node) {
            if (node == null) {
                return 0;
            }

            /**
             * !!!
             * unique key to represent a subtree is constructed as string representation of
             * tuple :
             *   value of node
             *   subtree id of its left subtree
             *   subtree id of its right subtree
             */
            String key = node.val + "," + lookup(node.left) + "," + lookup(node.right);

            /**
             * Map key to unique subtree id
             */
            int uid = trees.computeIfAbsent(key, x-> t++);

            count.put(uid, count.getOrDefault(uid, 0) + 1);

            if (count.get(uid) == 2) {
                ans.add(node);
            }

            return uid;
        }
    }
}