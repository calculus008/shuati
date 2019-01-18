package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuank on 11/13/18.
 */
public class LE_894_All_Possible_Full_Binary_Trees {
    /**
         A full binary tree is a binary tree where each node has exactly 0 or 2 children.

         Return a list of all possible full binary trees with N nodes.
         Each element of the answer is the root node of one possible tree.

         Each node of each tree in the answer must have node.val = 0.

         You may return the final list of trees in any order.

         1 <= N <= 20

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/tree/leetcode-894-all-possible-full-binary-trees/
     *
     * Key observations:
     * 1.n must be odd, If n is even, no possible trees
     * 2.ans is the cartesian product (笛卡尔积) of trees(i) and trees(n-i-1).
     *   Ans = {Tree(0, l, r) for l, r in trees(i) X trees(N – i – 1)}.
     *
     * Recursion DP
     *
     * O(N ^ 2) ??
     */

    /**
     * Solution 1
     * Recursion without memization
     * 7 ms
     */
    class Solution1 {
        public List<TreeNode> allPossibleFBT(int N) {
            List<TreeNode> res = new ArrayList<>();
            if (N % 2 == 0) {
                return res;
            }

            if (N == 1) {
                res.add(new TreeNode(0));
                return res;
            }

            /**
             * !!!
             * Outer loop loops with number of nodes, starts from 1 and
             * ends with with N - 1, because the biggest number of nodes
             * that can go into left subtree (in the next loop) is N - 1.
             * If outer loop ends with N, we will see stack overflow.
             *
             * Increase step 2, because even number of
             * nodes does not have a solution.
             *
             * For right, N - i - 1 : subtract i in left subtree and the root node
             */
            for (int i = 1; i < N; i += 2) {
                for (TreeNode l : allPossibleFBT(i)) {
                    for (TreeNode r : allPossibleFBT(N - i - 1)) {
                        TreeNode root = new TreeNode(0);
                        root.left = l;
                        root.right = r;
                        res.add(root);
                    }
                }
            }

            return res;
        }
    }

    /**
     * Solution 2
     * Recursion with memization
     * 4 ms
     */
    class Solution2 {
        HashMap<Integer, List<TreeNode>> mem = new HashMap<>();

        public List<TreeNode> allPossibleFBT(int N) {
            List<TreeNode> res = new ArrayList<>();
            if (N % 2 == 0) {
                return res;
            }

            if (N == 1) {
                res.add(new TreeNode(0));
                return res;
            }

            if (mem.containsKey(N)) {
                return mem.get(N);
            }

            for (int i = 1; i < N; i += 2) {
                for (TreeNode l : allPossibleFBT(i)) {
                    for (TreeNode r : allPossibleFBT(N - i - 1)) {
                        TreeNode root = new TreeNode(0);
                        root.left = l;
                        root.right = r;
                        res.add(root);
                    }
                }
            }

            mem.put(N, res);

            return res;
        }
    }
}
