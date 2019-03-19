package leetcode;

import common.TreeNode;
import common.BSTIterator;
import common.BSTReverseIterator;

import java.util.ArrayList;
import java.util.List;

public class LE_653_Two_Sum_IV_Input_Is_A_BST {
    /**
     * Given a Binary Search Tree and a target number, return true
     * if there exist two elements in the BST such that their sum
     * is equal to the given target.
     *
     * Example 1:
     *
     * Input:
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * Target = 9
     *
     * Output: True
     *
     *
     * Example 2:
     *
     * Input:
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * Target = 28
     *
     * Output: False
     *
     * Easy
     */

    /**
     * Inorder + Two pointers
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution1 {
        public boolean findTarget(TreeNode root, int k) {
            if (root == null) {
                return false;
            }

            List<Integer> list = new ArrayList<>();
            inorder(root, list);

            int l = 0;
            int r = list.size() - 1;

            while (l < r) {
                int sum = list.get(l) + list.get(r);
                if (sum == k) {
                    return true;
                } else if (sum > k) {
                    r--;
                } else if (sum < k) {
                    l++;
                }
            }

            return false;
        }

        private void inorder(TreeNode root, List<Integer> list) {
            if (root == null) {
                return;
            }

            inorder(root.left, list);
            list.add(root.val);
            inorder(root.right, list);
        }
    }

    /**
     * 他先让我别写code问我如果要在一个bst上做2 sum 怎么做，就是leetocode 653。我说了先in-order traversal + 2 pointer 的方法，
     * 他说能不能不用 O(n) space，我就说了traverse 同时binary search的方法，他说能不能还保持 O(n)的time complexity，我就有点懵。。。
     * 他说你看你第一种方法可不可以不用另外建array，我说不太知道，他提示说其实你只要in order traverse这个tree 和反过来 traverse
     * 这个tree，一定需要O(n) space 嘛？我终于明白他是希望我用iterative 的方法in-order-traversal, 这样space complexity 是 O(h),
     * h是tree的height。所以最后他让我写code的就是bst 的in-order iterator.
     *
     * LE_173_Binary_Search_Tree_Iterator
     *
     * Time  : O(n)
     * Space : O(h), h is height of BST
     */
    class Solution2 {
        public boolean findTarget(TreeNode root, int k) {
            if (root == null) {
                return false;
            }

            BSTIterator iterator1 = new BSTIterator(root);
            BSTReverseIterator iterator2 = new BSTReverseIterator(root);

            int l = 0;
            int r = 0;

            if (iterator1.hasNext()) {
                l = iterator1.next();
            } else {
                return false;
            }

            if (iterator2.hasNext()) {
                r = iterator2.next();
            } else {
                return false;
            }

            while (l < r) {
                int sum = l + r;
                if (sum == k) {
                    return true;
                } else if (sum > k) {
                    if(iterator2.hasNext()) {
                        r = iterator2.next();
                    } else {
                        break;
                    }
                } else if (sum < k) {
                    if (iterator1.hasNext()) {
                        l = iterator1.next();
                    } else {
                        break;
                    }
                }
            }

            return false;
        }
    }
}
