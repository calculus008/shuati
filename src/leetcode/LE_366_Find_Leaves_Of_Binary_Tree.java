package leetcode;

import common.TreeNode;

import java.util.*;

/**
 * Created by yuank on 7/28/18.
 */
public class LE_366_Find_Leaves_Of_Binary_Tree {
    /**
         Given a binary tree, collect a tree's nodes as if you were doing this:
         Collect and remove all leaves, repeat until the tree is empty.

         Example:
         Given binary tree
             1
            / \
           2   3
          / \
         4   5
         Returns [4, 5, 3], [2], [1].

         Explanation:
         1. Removing the leaves [4, 5, 3] would result in this tree:

           1
          /
         2

         2. Now removing the leaf [2] would result in this tree:

         1

         3. Now removing the leaf [1] would result in the empty tree:

         []
         Returns [4, 5, 3], [2], [1].

         Medium
     */

    class Solution1 {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }

            helper(root, res);
            return res;
        }


        /**
         * helper() 所求是树的高度
         */
        public int helper(TreeNode root, List<List<Integer>> res) {
            if (root == null) return -1;

            int left = helper(root.left, res);
            int right = helper(root.right, res);

            /**
             * 求出每个Node的height就是它在list中相对应的位置
             *
             * !!!
             * 这是这道题的关键，实际上，这里的level是height(!!!),
             * 也就是，从叶子节点往上，依次返回 0, 1, 2..., 所以它可以被
             * 用作res里的下标。
             *
             * !!!
             * "Math.max" not "Math.min"
             */
            int level = Math.max(left, right) + 1;

            /**
             * common trick for using DFS on level related problem
             *
             * !!!
             * "res.size() == level"
             *
             * Can't use "res.get(i) == null", it will throw index out of range exception
             */
            if (res.size() == level) {
                res.add(new ArrayList<>());
            }

            /**
             * DFS的性质保证叶子节点被一层层移除
             */
            res.get(level).add(root.val);
            /**
             * !!!
             * 题意要求是remove, 所以在postorder的最后一步把左右指针设为零
             **/
            root.left = null;
            root.right = null;

            /**
             * level returned : 0, 1, 2,...., it is the index in res.
             **/
            return level;
        }
    }


    /**
     * It seems this solution only collects the leaf nodes, not doing removal.
     */
    class Solution2 {
        public List<List<Integer>> findLeaves2_JiuZhang(TreeNode root) {
            List<List<Integer>> ans = new ArrayList<>();

            Map<Integer, List<Integer>> depth = new HashMap<>();
            int max_depth = dfs(root, depth);

            for (int i = 1; i <= max_depth; i++) {
                ans.add(depth.get(i));
            }
            return ans;
        }


        /**
         * This method is modified from finding depth of a binary tree,
         * while it finds depth, it also saves leaves list of each level in a map
         */
        int dfs(TreeNode cur, Map<Integer, List<Integer>> depth) {
            if (cur == null) {
                return 0;
            }
            int d = Math.max(dfs(cur.left, depth), dfs(cur.right, depth)) + 1;

            /**
             * Map depth : key - current level or height, value - list of leaves nodes.
             */
            depth.putIfAbsent(d, new ArrayList<>());
            depth.get(d).add(cur.val);

            //removal
            cur.left = null;
            cur.right = null;

            return d;
        }
    }

    class Solution_Practice {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }

            helper(root, res);
            return res;
        }

        /**
         * 这个helper()实际上就是用来找tree的reversed height。
         */
        private int helper(TreeNode root, List<List<Integer>> res) {
            if (root == null) {
                return -1;//!!!
            }

            int left = helper(root.left, res);
            int right = helper(root.right, res);

            int level = Math.max(left, right) + 1;

            if (res.size() == level) {
                res.add(new ArrayList<>());
            }
            res.get(level).add(root.val);

            return level;
        }
    }

    /**
     * 变形题，把Tree变成Graph, 求同样的叶子按层输出 - Find_Leaves
     */
    /**It is graph, rather than tree
     * One big difference is, tree only has 1 path between 2 nodes, while graph can have
     * several paths, which forms a circle
     *
     * @param graph could be isolated, but in our case, it is a connected graph
     * @return
     *
     * !!!
     * 1.只用一个Set<Node> visited 不行，因为graph里边可以重复visit 但是不能有环。
     * 2.所以用Map, 上次还没计算出结果来呢，又来了， 说明有环,Tree上随便两个Node连起来，肯定都是环吧
     */
    List<List<Integer>> findLeavesInGraph(TreeNode graph) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        // Use a map to remember not only the height, but also track loop
        Map<TreeNode, Integer> map = new HashMap<TreeNode, Integer>();
        helper(res, map, graph);
        return res;
    }

    private int helper(List<List<Integer>> res, Map<TreeNode, Integer> map, TreeNode node) {
        if (node == null) {
            return 0;
        }

        if (map.containsKey(node)) {
            if (map.get(node) == -1) {
                throw new RuntimeException("There is loop, done");
            }
            return map.get(node);
        }

        /**
         * !!!
         * -1 : Seen this node, but not finalized yet, it is still too high
         */
        map.put(node, -1);

        int height = Math.max(helper(res, map, node.left), helper(res, map, node.right)) + 1;

        if (height == res.size()) {
            res.add(new ArrayList<Integer>());
        }

        //heigth - 1??, first level returns 1, the index should be 0
        res.get(height - 1).add(node.val);
        map.put(node, height);

        return height;
    }
}
