package Interviews.Lyft.lc;

public class Second_Larget_Element_In_Binary_Search_Tree {
    /**
     * Given a Binary Search Tree(BST), find the second largest element.
     *
     * Examples:
     *
     * Input: Root of below BST
     *     10
     *    /
     *   5
     *
     * Output:  5
     *
     *
     * Input: Root of below BST
     *         10
     *       /   \
     *     5      20
     *              \
     *               30
     *
     * Output:  20
     *
     * https://www.geeksforgeeks.org/second-largest-element-in-binary-search-tree-bst/
     * https://www.geeksforgeeks.org/kth-largest-element-in-bst-when-modification-to-bst-is-not-allowed/
     *
     * https://medium.com/@johnathanchen/find-the-2nd-largest-element-in-a-binary-search-tree-interview-question-f566c52188a1
     *
     */

    /**
     * The second largest element is second last element in inorder traversal and second element
     * in reverse inorder traversal. We traverse given Binary Search Tree in reverse inorder and
     * keep track of counts of nodes visited. Once the count becomes 2, we print the node.
     */
    static class Node {
        int data;
        Node left, right;

        Node(int d) {
            data = d;
            left = right = null;
        }
    }

    static class BinarySearchTree {
        // Root of BST
        Node root;

        // Constructor
        BinarySearchTree() {
            root = null;
        }

        // function to insert new nodes
        public void insert(int data) {
            this.root = this.insertRec(this.root, data);
        }

        /* A utility function to insert a new node with given
        key in BST */
        Node insertRec(Node node, int data) {
            /* If the tree is empty, return a new node */
            if (node == null) {
                this.root = new Node(data);
                return this.root;
            }

            /* Otherwise, recur down the tree */
            if (data < node.data) {
                node.left = this.insertRec(node.left, data);
            } else {
                node.right = this.insertRec(node.right, data);
            }
            return node;
        }

        // class that stores the value of count
        public class count {
            int c = 0;
        }

        // Function to find 2nd largest element
        void secondLargestUtil(Node node, count C) {
            // Base cases, the second condition is important to
            // avoid unnecessary recursive calls
            if (node == null || C.c >= 2)
                return;

            // Follow reverse inorder traversal so that the
            // largest element is visited first
            this.secondLargestUtil(node.right, C);

            // Increment count of visited nodes
            C.c++;

            // If c becomes k now, then this is the 2nd largest
            if (C.c == 2) {
                System.out.print("2nd largest element is " +
                        node.data);
                return;
            }

            // Recur for left subtree
            this.secondLargestUtil(node.left, C);
        }

        // Function to find 2nd largest element
        void secondLargest(Node node) {
            // object of class count
            count C = new count();
            this.secondLargestUtil(this.root, C);
        }

        int count;
        int res;

        public void secondLargest1(Node node) {
            if (node == null || count == 2) return;

            secondLargest1(node.right);

            count++;
            if (count == 2) {
                res = node.data;
                System.out.println(res);
            }

            secondLargest1(node.left);
        }

        // Driver function
        public static void main(String[] args) {
            BinarySearchTree tree = new BinarySearchTree();

        /* Let us create following BST
              50
           /     \
          30      70
         /  \    /  \
       20   40  60   80 */

            tree.insert(50);
            tree.insert(30);
            tree.insert(20);
            tree.insert(40);
            tree.insert(70);
            tree.insert(60);
            tree.insert(80);

            tree.secondLargest1(tree.root);
        }
    }
}
