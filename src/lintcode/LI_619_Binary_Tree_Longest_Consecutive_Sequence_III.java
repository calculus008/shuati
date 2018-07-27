package lintcode;

import common.MultiTreeNode;

/**
 * Created by yuank on 7/27/18.
 */
public class LI_619_Binary_Tree_Longest_Consecutive_Sequence_III {
    /**
         Binary Tree Longest Consecutive Sequence III
         It's follow up problem for Binary Tree Longest Consecutive Sequence II

         Given a k-ary tree, find the length of the longest consecutive sequence path.
         The path could be start and end at any node in the tree

         Example
         An example of test data: k-ary tree 5<6<7<>,5<>,8<>>,4<3<>,5<>,3<>>>, denote the following structure:


         5
         /   \
         6     4
         /|\   /|\
         7 5 8 3 5 3

         Return 5, // 3-4-5-6-7

         Medium
     */

    /**
     * Follow up question for LE_549_Binary_Tree_Longest_Consecutive_Sequence_II
     */

    class ResultType {
        public int max_len, max_down, max_up;
        ResultType(int len, int down, int up) {
            max_len = len;
            max_down = down;
            max_up = up;
        }
    }

    public int longestConsecutive3(MultiTreeNode root) {
        return helper(root).max_len;
    }

    ResultType helper(MultiTreeNode root) {
        if (root == null) {
            return new ResultType(0, 0, 0);
        }

        int down = 0, up = 0, max_len = 1;
        for (MultiTreeNode node : root.children) {
            ResultType type = helper(node);
            if (node.val + 1 == root.val)
                down = Math.max(down, type.max_down + 1);
            if (node.val - 1 == root.val)
                up = Math.max(up, type.max_up + 1);
            max_len = Math.max(max_len, type.max_len);
        }

        max_len = Math.max(down + 1 + up, max_len);
        return new ResultType(max_len, down, up);
    }
}
