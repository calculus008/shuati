package leetcode;

import common.*;

import java.util.*;

public class LE_1028_Recover_A_Tree_From_Preorder_Traversal {
    /**
     * We run a preorder depth-first search (DFS) on the root of a binary tree.
     *
     * At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value
     * of this node.  If the depth of a node is D, the depth of its immediate child is D + 1.  The depth of the root node is 0.
     *
     * If a node has only one child, that child is guaranteed to be the left child.
     *
     * Given the output traversal of this traversal, recover the tree and return its root.
     *
     * Example 1:
     * Input: traversal = "1-2--3--4-5--6--7"
     * Output: [1,2,5,3,4,6,7]
     *
     * Example 2:
     * Input: traversal = "1-2--3---4-5--6---7"
     * Output: [1,2,5,3,null,6,null,4,null,7]
     *
     * Example 3:
     * Input: traversal = "1-401--349---90--88"
     * Output: [1,401,null,349,88,90]
     *
     * Constraints:
     * The number of nodes in the original tree is in the range [1, 1000].
     * 1 <= Node.val <= 109
     *
     * Hard
     */

    /**
     * HashMap solution
     *
     * Key
     *
     * 1.规则：root is at level 0, no "-" in front of it. For the rest of the node, first is the level info, then node value.
     *   Number can be multiple digits, so you need to assemble multiple digits into a single number.
     * 2.Preorder的特性, traversal一定是沿着一直走到底。
     *         1
     *        /
     *      401
     *      / \
     *    349  88
     *    /
     *   90
     *
     *   按照题目中的规则： 1-401--349---90--88
     *                      ｜__________｜     沿着1的left chid, all the way down
     *
     *                                    |_|  right child
     *
     *  This is the reason why we can use a Hasahmp which key is level and value in TreeNode.
     *  When we go all the way down from a node, we put level and its node in map, whenever we
     *  have a new node, we retrieve its parent by curLevel - 1, then connect the parent with
     *  this new node (left child has priority). After each the bottom, it goes back to the lowest
     *  level of node that has a right child, from there, we will do the same, the key of level
     *  will be overwritten for the node on right.
     *
     *
     *  Time : O(n) (n is length of input string)
     *  Space : O(k) (k is number of nodes)
     */
    class Solution1 {
        public TreeNode recoverFromPreorder(String traversal) {
            if (traversal == null || traversal.length() == 0) return null;

            char[] chars = traversal.toCharArray();
            int n = chars.length;
            Map<Integer, TreeNode> map = new HashMap<>();

            int i = 0;
            while (i < n) {
                int curLevel = 0;
                while (i < n && chars[i] == '-') {
                    i++;
                    curLevel++;
                }

                int curNum = 0;
                while (i < n && Character.isDigit(chars[i])) {
                    curNum = curNum * 10 + chars[i] - '0';
                    i++;
                }

                TreeNode curNode = new TreeNode(curNum);
                map.put(curLevel, curNode);

                if (curLevel > 0) {
                    TreeNode parent = map.get(curLevel - 1);

                    if (parent.left == null) {
                        parent.left = curNode;
                    } else {
                        parent.right = curNode;
                    }
                }
            }

            return map.get(0);
        }
    }

    /**
     * Stack solution
     */
    class Solution2 {
        public TreeNode recoverFromPreorder(String traversal) {
            if (traversal == null || traversal.length() == 0) return null;

            char[] chars = traversal.toCharArray();
            int n = chars.length;

            Stack<TreeNode> stack = new Stack<>();

            int i = 0;
            while (i < n) {
                int curLevel = 0;
                while (i < n && chars[i] == '-') {
                    i++;
                    curLevel++;
                }

                int curNum = 0;
                while (i < n && Character.isDigit(chars[i])) {
                    curNum = curNum * 10 + chars[i] - '0';
                    i++;
                }

                /**
                 * !!!
                 */
                while (stack.size() > curLevel) {
                    stack.pop();
                }

                TreeNode curNode = new TreeNode(curNum);

                if (!stack.isEmpty()) {
                    if (stack.peek().left == null) {
                        stack.peek().left = curNode;
                    } else {
                        stack.peek().right = curNode;
                    }
                }

                stack.push(curNode);
            }

            while (stack.size() > 1) {
                stack.pop();
            }

            return stack.pop();
        }
    }
}
