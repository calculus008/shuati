package leetcode;

import common.TreeNode;

import java.util.*;

/**
 * Created by yuank on 5/3/18.
 */
public class LE_314_Binary_Tree_Vertical_Order_Traversal {
    /**
     * Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
     *
     * If two nodes are in the same row and column, the order should be from left to right.
     *
     * Examples 1:
     *
     * Input: [3,9,20,null,null,15,7]
     *
     *    3
     *   /\
     *  /  \
     *  9  20
     *     /\
     *    /  \
     *   15   7
     *
     * Output:
     *
     * [
     *   [9],
     *   [3,15],
     *   [20],
     *   [7]
     * ]
     * Examples 2:
     *
     * Input: [3,9,8,4,0,1,7]
     *
     *       3
     *      /\
     *     /  \
     *     9   8
     *    /\  /\
     *   /  \/  \
     *  4  01   7
     *
     * Output:
     *
     * [
     *   [4],
     *   [9],
     *   [3,0,1],
     *   [8],
     *   [7]
     * ]
     * Examples 3:
     *
     * Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5)
     *
     *      3
     *     /\
     *    /  \
     *    9   8
     *   /\  /\
     *  /  \/  \
     *  4  01   7
     *     /\
     *    /  \
     *    5   2
     *
     * Output:
     *
     * [
     *   [4],
     *   [9,5],
     *   [3,0,1],
     *   [8,2],
     *   [7]
     * ]
     *
     * Medium
     *
     * A variation of the problem : LE_987_Vertical_Order_Traversal_Of_A_Binary_Tree
     */

    /**
     * Similar to LE_655_Print_Binary_Tree, but not the same, can't use its solution for this problem directly,
     * because of the root position alignment.
     *
     * The similarity is that we need to first run a pass for the whole tree to get dimension of the tree.
     * Here, we start from root, get the width of the left subtree wl and right subtree wr, then we know how
     * many levels we need to process (wl + wr). For 655, we need to find depth of the tree, then we know the
     * max length of the list (2 ^ h - 1)
     *
     * Time and Space : O(n)
     **/
    class Solution1 {
        public List<List<Integer>> verticalOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;

            int[] range = new int[2];
            findRange(root, range, 0);

            //!!!
            for (int i = range[0]; i <= range[1]; i++) {
                res.add(new ArrayList<Integer>());
            }

            /**
             * In BFS, need both the node object and its col index
             * we can create a class Pair and use one queue, Or
             * we just use 2 queues and offer and poll elements from
             * those 2 queues at the same time
             */
            Queue<TreeNode> nodes = new LinkedList<>();
            Queue<Integer> cols = new LinkedList<>();

            nodes.offer(root);

            /**
             Once we find the min we create n number of lists starting from min till max. Now when doing BFS,
             we need to start adding the elements to the correct lists which will be between 0 to n.

             If you think about where the root will end up (which index in the cols list), it will be in the index
             at Math.abs(min).

             If min = -3, that means we have 3 columns before the root node (0,1,2 will be occupied by nodes of
             the root's left subtree), hence root will be in the |min| index.

             So -range[0] is simply a shift of index positions so all nodes end up in their corresponding lists.
             **/
            cols.offer(-range[0]);

            while (!nodes.isEmpty()) {
                TreeNode curNode = nodes.poll();
                int curCol = cols.poll();
                res.get(curCol).add(curNode.val);

                /**
                 * !!!
                 * "curl - 1" for left, and "cur + 1" for right
                 */
                if (curNode.left != null) {
                    nodes.offer(curNode.left);
                    cols.offer(curCol - 1);
                }

                if (curNode.right != null) {
                    nodes.offer(curNode.right);
                    cols.offer(curCol + 1);
                }
            }

            return res;
        }

        /**
         * !!!
         * range[0] : offset from root location to the left most child (negative number)
         * range[1] : offset from root location fo the right most child (positive number)
         *
         * example:
         * 5 vertical levels in range[] and its index
         *
         * 0  1  2 3  4
         * -2,-1,0 1, 2
         *
         *         3            list Idx [2]
         *        /\
         *       /  \
         *      9   8              [1, 3]
         *     /\  /\
         *    /  \/  \
         *   4   01   7            [0, 2] [2, 4]
         *  /\
         * /  \
         *5   2                   [1] [3]
         * <p>
         * answer:
         * [
         * [4],
         * [9,5],
         * [3,0,1],
         * [8,2],
         * [7]
         * ]
         */
        public void findRange(TreeNode root, int[] range, int col) {
            if (root == null) return;

            range[0] = Math.min(col, range[0]);
            range[1] = Math.max(col, range[1]);

            findRange(root.left, range, col - 1);
            findRange(root.right, range, col + 1);
        }
    }

    /**
     *  Copied from LE_987_Vertical_Order_Traversal_Of_A_Binary_Tree, solution 1.
     *
     *  Since this problem does not require values in the same level are sorted,
     *  we can simply use set or list, instead of TreeSet
     */
    class Solution2 {
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
             *
             * The order of the elements is guaranteed by TreeSet
             */
            TreeMap<Pair, List<Integer>> map = new TreeMap<>((a, b) -> a.row == b.row ? a.col - b.col : a.row - b.row);

            //find range
            traverse(root, map, new Pair(0, 0));

            int col = max_col - min_col + 1;

            int offset = Math.abs(min_col);//!!!

            for (int i = 0; i < col; i++) {
                res.add(new ArrayList<Integer>());
            }

            for (Map.Entry<Pair, List<Integer>> e: map.entrySet()) {
                Pair p = e.getKey();
                int index = p.col + offset;//!!!
                res.get(index).addAll(e.getValue());
            }

            return res;
        }

        private void traverse(TreeNode root, TreeMap<Pair, List<Integer>> map, Pair p) {
            if (root == null) {
                return;
            }

            min_col = Math.min(p.col, min_col);
            max_col = Math.max(p.col, max_col);

            if (!map.containsKey(p)) {
                map.put(p, new ArrayList<>());
            }
            map.get(p).add(root.val);

            traverse(root.left,  map, new Pair(p.row + 1, p.col - 1));
            traverse(root.right, map, new Pair(p.row + 1, p.col + 1));
        }
    }

    /**
     * Adapt from LE_987_Vertical_Order_Traversal_Of_A_Binary_Tree
     *
     * Changes :
     * TreeMap<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
     *
     * Use list instead of TreeSet, the values in the trees are not unique.
     *
     *         1
     *        / \
     *       2   3
     *      / \ / \
     *     4  56  7
     *
     * TreeMap:
     * 0  0   [1]
     * 1 -1   [2]
     *    1   [3]
     * 2 -2   [4]
     *    0   [5,6]
     *    2   [7]
     */
    class Solution3 {
        class Solution {
            public List<List<Integer>> verticalOrder(TreeNode root) {
                TreeMap<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();

                dfs(root, 0, 0, map);

                //?? may not be correct
                List<List<Integer>> list = new ArrayList<>();
                for (TreeMap<Integer, List<Integer>> ys : map.values()) {
                    list.add(new ArrayList<>());
                    for (List<Integer> nodes : ys.values()) {
                        for (int i : nodes) {
                            list.get(list.size() - 1).add(i);
                        }
                    }
                }
                return list;
            }

            private void dfs(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer, List<Integer>>> map){
                if (root == null) {
                    return;
                }

                if (!map.containsKey(x)) {
                    map.put(x, new TreeMap<>());
                }
                if (!map.get(x).containsKey(y)) {
                    map.get(x).put(y, new ArrayList<>());
                }

                map.get(x).get(y).add(root.val);

                dfs(root.left, x - 1, y + 1, map);
                dfs(root.right, x + 1, y + 1, map);
            }
        }
    }
}
