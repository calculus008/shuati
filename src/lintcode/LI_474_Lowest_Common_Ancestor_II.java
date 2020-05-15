package lintcode;

import common.ParentTreeNode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 7/25/18.
 */
public class LI_474_Lowest_Common_Ancestor_II {
    /**
         Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.

         The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.

         The node has an extra attribute parent which point to the father of itself. The root's parent is null.

         Example
         For the following binary tree:

             4
            / \
           3   7
          / \
         5   6
         LCA(3, 5) = 4

         LCA(5, 6) = 7

         LCA(6, 7) = 7

         Easy
     */

    /**
     * For case that A and B may not exist
     *
     * Time and Space :  O(h), h is height of the tree
     *
     * Solution 1
     * Use Set
     */
    public ParentTreeNode lowestCommonAncestorII_1(ParentTreeNode root, ParentTreeNode A, ParentTreeNode B) {
        Set<ParentTreeNode> set = new HashSet<>();

        ParentTreeNode cur = A;
        while (cur != null) {
            set.add(cur);
            cur = cur.parent;
        }

        cur = B;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            cur = cur.parent;
        }

        return null;
    }

    /**
     * Solution 2
     *
     * This works for the case both A and B exist.
     *
     * Space O(1)
     * 借鉴 LE_160_Intersection_Of_Tow_Linked_List:
     * p1, p2分别从A，B出发， 向root方向遍历。 p1达到root之后， 从B开始重新向root遍历。p2达到root之后， 从A开始重新向root遍历。
     * p1和p2在第二次遍历时，一定会在第一个intersection（i.e LCA）相遇。 时间复杂度O(h)，h是数的最大高度。
     *
     */
    public ParentTreeNode lowestCommonAncestorII_2(ParentTreeNode root, ParentTreeNode A, ParentTreeNode B) {
        ParentTreeNode p1 = A, p2 = B;
        while (p1 != p2) {
            p1 = p1.parent == null ? B : p1.parent;
            p2 = p2.parent == null ? A : p2.parent;
        }
        return p1;
    }
}
