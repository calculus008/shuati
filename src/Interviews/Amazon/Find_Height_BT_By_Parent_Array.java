package Interviews.Amazon;

public class Find_Height_BT_By_Parent_Array {
    /**
     * 然后给你一个数组表示的数 数组的index表示一个节点 index里面的值表示这个index节点的parent， 对于根结点 他的parent就是-1
     * 求这个树的高度
     * 比如这样的一棵树 4，1，3，2，0， 那么用数组就表示成：
     * index : 0，1，2，3，4
     * val     : 1，4，1，4，-1
     * 用DFS加一个dp数组优化就行了
     *
     *
     * A given array represents a tree in such a way that the array value gives the parent node
     * of that particular index. The value of the root node index would always be -1. Find the
     * height of the tree.
     *
     * Height of a Binary Tree is number of nodes on the path from root to the deepest leaf node,
     * the number includes both root and leaf.
     *
     * Input: parent[] = {1 5 5 2 2 -1 3}
     * Output: 4
     * The given array represents following Binary Tree
     *          5
     *         /  \
     *        1    2
     *       /    / \
     *      0    3   4
     *          /
     *         6
     *
     *
     * Input: parent[] = {-1, 0, 0, 1, 1, 3, 5};
     * Output: 5
     * The given array represents following Binary Tree
     *          0
     *        /   \
     *       1     2
     *      / \
     *     3   4
     *    /
     *   5
     *  /
     * 6
     */

    /**
     * An efficient solution can solve the above problem in O(n) time. The idea is to first calculate depth
     * of every node and store in an array depth[]. Once we have depths of all nodes, we return maximum of
     * all depths.
     * 1) Find depth of all nodes and fill in an auxiliary array depth[].
     * 2) Return maximum value in depth[].
     *
     * Following are steps to find depth of a node at index i.
     * 1) If it is root, depth[i] is 1.
     * 2) If depth of parent[i] is evaluated, depth[i] is depth[parent[i]] + 1.
     * 3) If depth of parent[i] is not evaluated, recur for parent and assign depth[i] as depth[parent[i]] + 1
     *
     * Time and Space : O(n)
     */
    void fillDepth(int parent[], int i, int depth[]) {
        /** If depth[i] is already filled **/
        if (depth[i] != 0) {
            return;
        }

        /** If node at index i is root **/
        if (parent[i] == -1) {
            depth[i] = 1;
            return;
        }

        /** If depth of parent is not evaluated before, then evaluate depth of parent first **/
        if (depth[parent[i]] == 0) {
            fillDepth(parent, parent[i], depth);
        }

        /** Depth of this node is depth of parent plus 1 **/
        depth[i] = depth[parent[i]] + 1;
    }


    int findHeight(int parent[], int n) {

        /**
         * Create an array to store depth of all nodes/ and initialize depth
         * of every node as 0 (an invalid value). Depth of root is 1
         **/
        int depth[] = new int[n];

        for (int i = 0; i < n; i++) {
            fillDepth(parent, i, depth);
        }

        /**
         * The height of binary tree is maximum of all depths.
         * Find the maximum value in depth[] and assign it to ht.
         **/
        int res = depth[0];
        for (int i = 1; i < n; i++) {
            res = Math.max(res, depth[i]);
        }
        return res;
    }

    public static void main(String args[]) {
        Find_Height_BT_By_Parent_Array tree = new Find_Height_BT_By_Parent_Array();

        // int parent[] = {1, 5, 5, 2, 2, -1, 3};
        int parent[] = new int[]{-1, 0, 0, 1, 1, 3, 5};

        int n = parent.length;
        System.out.println("Height is  " + tree.findHeight(parent, n));
    }
}
