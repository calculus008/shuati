package Interviews.Amazon;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class Implement_BST_Insert_Find_GetRandom {
    /**
     * implement BST with Insert/Find/GetRandom
     *
     * 可以在tree node里加一个subtree size，这样复杂度就是O(logN）了
     */

    /**
     * BST insertion and search
     * https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
     *
     * GetRaondom
     * https://www.geeksforgeeks.org/select-random-node-tree-equal-probability/
     *
     * Modify tree structure. We store count of children in every node.
     * Consider the above tree. We use inorder traversal here also.
     * We generate a number smaller than or equal count of nodes.
     * We traverse tree and go to the node at that index. We use counts
     * to quickly reach the desired node. With counts, we reach in O(h)
     * time where h is height of tree.
     *
     *       10,6
     *     /      \
     *   20,2       30,2
     *  /   \       /   \
     * 40,0 50,0  60,0  70,0
     *
     * The first value is node and second value is count of children.
     *
     * We start traversing the tree, on each node we either go to left subtree
     * or right subtree considering whether the count of children is less than
     * random count or not.
     *
     * If the random count is less than the count of children then we go left else we go right.
     *
     * Time Complexity of randomNode is O(h) where h is height of tree.
     */

    static class Node {
        int val;
        int children;//number of children under current node + node itself

        Node left, right;

        public Node(int val) {
            this.val = val;
            left = right = null;
        }
    }

    /**
     * Search a Node in BST with given value
     */
    public static Node search(Node root, int key) {
        if (root == null || root.val == key) return root;
        if (root.val > key) return search(root.left, key);

        return search(root.right, key);
    }

    /**
     * A recursive function to insert a new val in BST
     **/
    public static Node insertRec(Node root, int val) {
        /**
         *  If the tree is empty, return a new node
         **/
        if (root == null) {
            root = new Node(val);
            root.children = 1;
            return root;
        }

        if (val < root.val) {
            root.left = insertRec(root.left, val);
            if (root.left != null) {
                root.children++;
            }
        } else if (val > root.val) {
            root.right = insertRec(root.right, val);
            if (root.right != null) {
                root.children++;
            }
        }

        return root;
    }

    public static void inorder(Node root) {
        if (root == null) return;

        inorder(root.left);
        System.out.println("val : " + root.val + ", children :" + root.children);
        inorder(root.right);
    }

//    public static int getKthSmallest(Node root, int count) {
//        if (root == null) return -1;
//
//        int l = root.left != null ? root.left.children + 1 : 0;
//        if (l == count - 1) {
//            return root.val;
//        }
//
//        if (count <= l) return getKthSmallest(root.left, count);
//
//        /**
//         * if root.left is not null, subtract root.left.children + 1 (root.left itself) + 1 (root).
//         * otherwise, subtract 1 (root)
//         */
//        int num = root.left != null ? count -  (root.left.children + 2) : count - 1;
//
//        return getKthSmallest(root.right, num);
//    }

    public static int getRandom_3(Node root, int count) {
        if (root == null) return -1;

        int l = (root.left == null ? 0 : root.left.children);

        if (l >= count) {
            return getRandom_3(root.left, count);
        }

        if (l + 1 == count) return root.val;

        return getRandom_3(root.right, count - l - 1);
    }

    /**
     * From LE_230_Kth_Smallest_Element_In_BST
     *
     * Iterative
     * Time  : O(h + k)
     * Space : O(h)
     */
    public static int getRandom_1(Node root, int count) {
        Stack<Node> stack = new Stack<>();
        Node cur = root;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();

            count--;
            if (count == 0) return cur.val;

            cur = cur.right;
        }

        return -1;
    }

    /**
     * From LE_230_Kth_Smallest_Element_In_BST
     *
     * First count the number of nodes in each subtree, then do Quick Select in BST.
     *
     * Time and Space : O(n) (building map for number of nodes under each node)
     */
    static Map<Node, Integer> map;

    public static int getRandom_2(Node root, int k) {
        map = new HashMap<>();
        getNodes(root);
        return helper(root, k);
    }

    /**
     * postorder, count number of nodes in each subtree starting from a given node.
     * Time : O(n)
     */
    private static int getNodes(Node root) {
        if (root == null) return 0;

        int l = getNodes(root.left);
        int r = getNodes(root.right);

        int sum = l + r + 1;
        map.put(root, sum);

        return sum;
    }

    /**
     * inorder, quick select, get the kth value
     * Time : O(logn)
     */
    private static int helper(Node root, int k) {
        if (root == null) return -1;

        int l = (root.left == null ? 0 : map.get(root.left));

        if (l >= k) {
            return helper(root.left, k);
        }

        if (l + 1 == k) return root.val;

        return helper(root.right, k - l - 1);
    }


    /**
     * Returns Random node
     **/
    static int randomNode(Node root) {
        Random rand = new Random();
        int count = rand.nextInt(root.children + 1) + 1;
        System.out.println("count : " + count);
        return getRandom_2(root, count);
    }

    public static void main(String args[]) {
        /**
         * Test case :
         *
         *            40
         *          /    \
         *        20     50
         *       / \    / \
         *      10 30  45 60
         *                 \
         *                 70
         */
        Node root = insertRec(null, 40);
        insertRec(root, 20);
        insertRec(root, 30);
        insertRec(root, 10);
        insertRec(root, 50);
        insertRec(root, 60);
        insertRec(root, 70);
        insertRec(root, 45);

        inorder(root);
//        System.out.println("A Random Node From Tree : " + randomNode(root));
        for (int i = 1; i < 10; i++) {
            System.out.println("A Random Node From Tree : " + getRandom_3(root, i));
        }
    }
}



