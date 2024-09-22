package leetcode;

import common.TreeNode;

import java.util.*;

/**
 * Created by yuank on 3/30/18.
 */
public class LE_230_Kth_Smallest_Element_In_BST {
    /**
        Given a binary search tree, write a function to find the kth smallest element in it.

        Note:
        You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

        Follow up:
        What if the BST is modified (insert/delete operations) often and you need to find
        the kth smallest frequently? How would you optimize the kthSmallest() routine?

        Medium

        https://leetcode.com/problems/kth-smallest-element-in-a-bst

        https://leetcode.com/problems/kth-smallest-element-in-a-bst/editorial/
     */

    /**
       For follow up:
       we can keep both the kth smallest element and (k-1)th smallest element. If we insert or
       delete an element larger than the kth smallest elemen, the result remains unaffected.
       If something smaller than is inserted, compare it with the (k-1)th smallest element.
       The larger one becomes the new kth smallest element and adjust (k-1)th element accordingly.
       We may also need to keep track of the (k+1)th smallest element in case of deleting a node
       smaller than the kth element.
     */


    /**
     Solution 1 : DFS inorder traversal
     Time and Space : O(n)
     **/
    class Solution1_DFS_inorder {
        private int count;
        private int res;

        public int kthSmallest(TreeNode root, int k) {
            if (root == null) return 0;
            count = k;
            helper(root);
            return res;
        }

        private void helper(TreeNode root) {
            if (root == null) return;

            helper(root.left);
            count--;
            if (count == 0) {
                res = root.val;
            }
            helper(root.right);
        }
    }

    /**
     * Iterative inorder
     * Time : O(n)
     */
    class Solution_Iterative_inorder_Practice {
        public int kthSmallest(TreeNode root, int k) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode cur = root;

            while (cur != null || !stack.isEmpty()) {
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }

                cur = stack.pop();

                k--;
                if (k == 0) return cur.val;

                cur = cur.right; //!!!
            }

            return -1;
        }
    }

    /**
     * Time and Space : O(n) (building map for number of nodes under each node)
     */
    class Solution_Follow_UP_Practice {
        Map<TreeNode, Integer> map;

        public int kthSmallest(TreeNode root, int k) {
            map = new HashMap<>();
            getNodes(root);
            return helper(root, k);
        }

        /**
         * postorder, count number of nodes in each subtree starting from a given node.
         * Time : O(n)
         */
        private int getNodes(TreeNode root) {
            if (root == null) return 0;

            int l = getNodes(root.left);
            int r = getNodes(root.right);

            int sum = l + r + 1;
            map.put(root, sum);

            return sum;
        }

        /**
         * inorder, quick select, get the kth value
         * Time : O(h), h is height of the BST, it's O(logn) if BST is balanced
         */
        private int helper(TreeNode root, int k) {
            if (root == null) return -1;

            int l = (root.left == null ? 0 : map.get(root.left));

            if (l >= k) {
                return helper(root.left, k);
            }

            if (l + 1 == k) return root.val;

            return helper(root.right, k - l - 1);
        }
    }



    /**
        Solution 2 : DFS inorder iterative
     **/
    class Solution2 {
        public int kthSmallest2(TreeNode root, int k) {
            Stack<TreeNode> st = new Stack<>();

            while (root != null) {
                st.push(root);
                root = root.left;
            }

            while (k != 0) {
                TreeNode n = st.pop();
                k--;
                if (k == 0) return n.val;
                TreeNode right = n.right;
                while (right != null) {
                    st.push(right);
                    right = right.left;
                }
            }

            return -1; // never hit if k is valid
        }
    }

    /**
     * Solution 3 : Time : O(k), Space : O(k)
     */
    class Solution3 {
        public int kthSmallest3(TreeNode root, int k) {
            ArrayList<Integer> buffer = new ArrayList<Integer>();
            inorderSearch(root, buffer, k);
            /**
             * !!!
             * return "buffer.get(k-1)", not "buffer(buffer.size() - 1)"
             * The answer is not necessarily the last one in the list.
             */
            return buffer.get(k - 1);
        }

        public void inorderSearch(TreeNode node, ArrayList<Integer> buffer, int k) {
            if (buffer.size() >= k)
                return;

            if (node.left != null) {
                inorderSearch(node.left, buffer, k);
            }

            buffer.add(node.val);

            if (node.right != null) {
                inorderSearch(node.right, buffer, k);
            }
        }
    }

    /**
     *  Solution 4
     *  时间复杂度 O(n) 最好最坏都是。
        算法思想类似于 Quick Select。
        这个算法的好处是，如果多次查询的话，给每个节点统计儿子个数这个过程只需要做一次。查询可以很快。
     */
    class Solution4 {
        public int kthSmallest4_JiuZhang(TreeNode root, int k) {
            Map<TreeNode, Integer> numOfChildren = new HashMap<>();
            countNodes(root, numOfChildren);
            return quickSelectOnTree(root, k, numOfChildren);
        }

        /**
         * !!!
         * Count total number of nodes under a given root
         */
        private int countNodes(TreeNode root, Map<TreeNode, Integer> numOfChildren) {
            if (root == null) {
                return 0;
            }

            int left = countNodes(root.left, numOfChildren);
            int right = countNodes(root.right, numOfChildren);
            numOfChildren.put(root, left + right + 1);
            return left + right + 1;
        }

        private int quickSelectOnTree(TreeNode root, int k, Map<TreeNode, Integer> numOfChildren) {
            if (root == null) {
                return -1;
            }

            int left = root.left == null ? 0 : numOfChildren.get(root.left);
            if (left >= k) {
                return quickSelectOnTree(root.left, k, numOfChildren);
            }

            if (left + 1 == k) {
                return root.val;
            }

            return quickSelectOnTree(root.right, k - left - 1, numOfChildren);
        }
    }

    /**
         Solution 5
         使用 Binary Search Tree Iterator 的方式（可以参考 binary search tree iterator 那个题）
         用 stack，从第一个点开始，走 k-1 步，就是第 k 个点了。
         时间复杂度是 O(h + k), h 是树的高度。

         same as Solution 2 , inorder iterative.
     */
    class Solution5 {
        public int kthSmallest5_JiuZhang(TreeNode root, int k) {
            Stack<TreeNode> stack = new Stack<>();

            /**
             * find the smallest elements in BST (left most leaf child)
             */
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            for (int i = 0; i < k - 1; i++) {
                TreeNode node = stack.peek();

                if (node.right == null) {
                    node = stack.pop();
                    while (!stack.isEmpty() && stack.peek().right == node) {
                        node = stack.pop();
                    }
                } else {
                    node = node.right;
                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
            }

            return stack.peek().val;
        }
    }

    /**
     * Variation , Kth largest element in BST
     * 只需要做reverse inorder traversal再带一个global counter）
     */

    /**
     * Recursive Inorder
     * Time : O(n)
     */
    class Solution3_Practice {
        List<Integer> list;
        public int kthSmallest(TreeNode root, int k) {
            list = new ArrayList<>();
            helper(root, k);

            /**
             * !!!
             * return "list.get(k-1)", not "list(list.size() - 1)"
             * The answer is not necessarily the last one in the list.
             */
            return list.get(k - 1);
        }

        private void helper(TreeNode root, int k) {
            if (root == null) return;

            if (list.size() >= k) return;

            helper(root.left, k);
            list.add(root.val);
            helper(root.right, k);
        }
    }

}
