package leetcode;

import common.TreeNode;

import java.util.*;

public class LE_987_Vertical_Order_Traversal_Of_A_Binary_Tree {
    /**
     * Given a binary tree, return the vertical order traversal of its nodes values.
     *
     * For each node at position (X, Y), its left and right children respectively will
     * be at positions (X-1, Y-1) and (X+1, Y-1).
     *
     * Running a vertical line from X = -infinity to X = +infinity, whenever the vertical
     * line touches some nodes, we report the values of the nodes in order from top to
     * bottom (decreasing Y coordinates).
     *
     * If two nodes have the same position, then the value of the node that is reported
     * first is the value that is smaller.
     *
     * Return an list of non-empty reports in order of X coordinate.  Every report will
     * have a list of values of nodes.
     *
     *
     * Example 1:
     * Input: [3,9,20,null,null,15,7]
     * Output: [[9],[3,15],[20],[7]]
     * Explanation:
     * Without loss of generality, we can assume the root node is at position (0, 0):
     * Then, the node with value 9 occurs at position (-1, -1);
     * The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
     * The node with value 20 occurs at position (1, -1);
     * The node with value 7 occurs at position (2, -2).
     *
     * Example 2:
     * Input: [1,2,3,4,5,6,7]
     * Output: [[4],[2],[1,5,6],[3],[7]]
     * Explanation:
     * The node with value 5 and the node with value 6 have the same position according to the given scheme.
     * However, in the report "[1,5,6]", the node value of 5 comes first since 5 is smaller than 6.
     *
     *
     * Note:
     * The tree will have between 1 and 1000 nodes.
     * Each node's value will be between 0 and 1000.
     *
     * Medium
     */

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */

    /**
     * Adapted from huahua's C++ version
     *
     * Tricky part is to understand this requirement :
     * "If two nodes have the same position, then the value of the node that is reported
     * first is the value that is smaller."
     *
     * For example :
     *         1
     *        / \
     *       2   3
     *      / \ / \
     *     4  56  7
     *
     * Output : {{4} , {2}, {1, 5, 6}, {3}, {7}}
     * For col 3, 1 comes first since it is row index is smaller(0), 5 and 6 are at the same location
     * (row = 2, col = 2), 5 is smaller so it comes first in the list.
     *
     * So we need to maintain both row and col number. In traverse(), we get min_col and max_col,
     * which will be used to calculate the total number of columns. At the same time, we put all
     * values in a TreeMap, key is Pair(row, col) and value is a TreeSet. We provide comparator
     * to tell TreeMap to sort key by sorting on row first, then on col, for the Pairs that have
     * the same row and col, their vals are put into a TreeSet, which will be sorted.
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution1 {
        class Pair {
            int row, col;
            public Pair(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        int min_col;
        int max_col;

        public List<List<Integer>> verticalTraversal(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();

            if (root == null) {
                return res;
            }

            min_col = Integer.MAX_VALUE;
            max_col = Integer.MIN_VALUE;

            /**
             * !!!
             * "(a, b) -> a.row == b.row ? a.col - b.col : a.row - b.row"
             * First sort by row, then by col
             */
            TreeMap<Pair, TreeSet<Integer>> map = new TreeMap<>((a, b) -> a.row == b.row ? a.col - b.col : a.row - b.row);

            traverse(root, map, new Pair(0, 0));

            int col = max_col - min_col + 1;

            int offset = Math.abs(min_col);//!!!

            for (int i = 0; i < col; i++) {
                res.add(new ArrayList<Integer>());
            }

            for (Map.Entry<Pair, TreeSet<Integer>> e: map.entrySet()) {
                Pair p = e.getKey();
                int index = p.col + offset;//!!!
                res.get(index).addAll(e.getValue());
            }

            return res;
        }

        private void traverse(TreeNode root, TreeMap<Pair, TreeSet<Integer>> map, Pair p) {
            if (root == null) {
                return;
            }

            min_col = Math.min(p.col, min_col);
            max_col = Math.max(p.col, max_col);

            if (!map.containsKey(p)) {
                map.put(p, new TreeSet<>());
            }
            map.get(p).add(root.val);

            traverse(root.left,  map, new Pair(p.row + 1, p.col - 1));
            traverse(root.right, map, new Pair(p.row + 1, p.col + 1));
        }
    }

    /**
     * From leetocde, use TreeMap of TreeMap to do sorting by row, then by col
     */
    class Solution2 {
        public List<List<Integer>> verticalTraversal(TreeNode root) {
            TreeMap<Integer, TreeMap<Integer, TreeSet<Integer>>> map = new TreeMap<>();

            dfs(root, 0, 0, map);

            List<List<Integer>> list = new ArrayList<>();
            for (TreeMap<Integer, TreeSet<Integer>> ys : map.values()) {
                list.add(new ArrayList<>());
                for (TreeSet<Integer> nodes : ys.values()) {
                    for (int i : nodes) {
                        list.get(list.size() - 1).add(i);
                    }
                }
            }
            return list;
        }
        private void dfs(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer, TreeSet<Integer>>> map) {
            if (root == null) {
                return;
            }

            if (!map.containsKey(x)) {
                map.put(x, new TreeMap<>());
            }
            if (!map.get(x).containsKey(y)) {
                map.get(x).put(y, new TreeSet<>());
            }

            map.get(x).get(y).add(root.val);

            dfs(root.left, x - 1, y + 1, map);
            dfs(root.right, x + 1, y + 1, map);
        }
    }
}
