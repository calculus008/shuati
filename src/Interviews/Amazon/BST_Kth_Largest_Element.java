package Interviews.Amazon;

public class BST_Kth_Largest_Element {
    /**
     * binary search tree，每个node包含value和number of left tree nodes，返回第K大的element
     * (or kth smallest??)
     */

    static class Node {
        int val;
        int left_children;//number of children under current node + node itself

        Node left, right;

        public Node(int val) {
            this.val = val;
            left = right = null;
        }
    }

    public static int getKthLargest(Node root, int k) {
        int right_children = countNode(root.right);
        int total = root.left_children + 1 + right_children;

        int n = total - k + 1;
        return getKthSmallest(root, n);
    }

    public static int getKthSmallest(Node root, int k) {
        if (root == null) return -1;

        int l = (root.left == null ? 0 : root.left_children);

        if (l >= k) {
            return getKthSmallest(root.left, k);
        }

        if (l + 1 == k) return root.val;

        return getKthSmallest(root.right, k - l - 1);
    }

    public static int countNode(Node root) {
        if (root == null) return 0;

        int right_children = countNode(root.right);
        return root.left_children + 1 + right_children;
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
//            root.left_children = 1;
            return root;
        }

        /**
         * update left_children in root while insert,
         * since the requirement is to have number of left
         * children, we only update when inserting node
         * on the left side.
         */
        if (val < root.val) {
            root.left = insertRec(root.left, val);
            if (root.left != null) {
                root.left_children++;
            }
        } else if (val > root.val) {
            root.right = insertRec(root.right, val);
//            if (root.right != null) {
//                root.left_children++;
//            }
        }

        return root;
    }

    public static Node search(Node root, int key) {
        if (root == null || root.val == key) return root;
        if (root.val > key) return search(root.left, key);

        return search(root.right, key);
    }

    public static void inorder(Node root) {
        if (root == null) return;

        inorder(root.left);
        System.out.println("val : " + root.val + ", children :" + root.left_children);
        inorder(root.right);
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

        /**
         * Manually set left_children in each node
         */
//        Node n1 = search(root, 10);
//        n1.left_children = 0;
//        Node n2 = search(root, 20);
//        n2.left_children = 1;
//        Node n3 = search(root, 30);
//        n3.left_children = 0;
//        Node n4 = search(root, 40);
//        n4.left_children = 3;
//        Node n5 = search(root, 45);
//        n5.left_children = 0;
//        Node n6 = search(root, 50);
//        n6.left_children = 1;
//        Node n7 = search(root, 60);
//        n7.left_children = 0;
//        Node n8 = search(root, 70);
//        n8.left_children = 0;


        inorder(root);
        for (int i = 1; i < 10; i++) {
            System.out.println(i +" smallest : " + getKthSmallest(root, i));
        }

        for (int i = 1; i < 10; i++) {
            System.out.println(i +" largest : " + getKthLargest(root, i));
        }
    }
}
