package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE_364_Nested_List_Weight_Sum_II {
    /**
     * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
     *
     * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
     *
     * Different from the previous question where weight is increasing from root to leaf, now the weight
     * is defined from bottom up. i.e., the leaf level integers have weight 1, and the root level integers
     * have the largest weight.
     *
     * Example 1:
     *
     * Input: [[1,1],2,[1,1]]
     * Output: 8
     * Explanation: Four 1's at depth 1, one 2 at depth 2.
     * Example 2:
     *
     * Input: [1,[4,[6]]]
     * Output: 17
     * Explanation: One 1 at depth 3, one 4 at depth 2, and one 6 at depth 1; 1*3 + 4*2 + 6*1 = 17.
     */

    /**
     * BFS one pass with Queue, without modifying input.
     * Preferred Solution
     */
    class Solution_BFS {
        public int depthSumInverse(List<NestedInteger> nestedList) {
            if (nestedList == null || nestedList.size() == 0) {
                return 0;
            }

            int res = 0;
            int prev = 0;
            Queue<NestedInteger> queue = new LinkedList<NestedInteger>(nestedList);

            while (!queue.isEmpty()) {
                int size = queue.size();
                int levelSum = 0;

                for (int i = 0; i < size; i++) {
                    NestedInteger ni = queue.poll();
                    if (ni.isInteger()) {
                        levelSum += ni.getInteger();
                    } else {
                        queue.addAll(ni.getList()); // Same to offer()
                    }
                }

                prev += levelSum;
                res += prev; // The secret is this levelSum will be added many times
            }

            return res;
        }
    }

    /**
     * Solution 1
     * Two passes DFS
     *
     * Time : O(n)
     */
    class Solution1 {
        public int depthSumInverse(List<NestedInteger> nestedList) {
            if (null == nestedList || nestedList.size() == 0) {
                return 0;
            }

            int depth = getDepth(nestedList);

            return helper(nestedList, depth);
        }

        private int getDepth(List<NestedInteger> nestedList) {
            if (null == nestedList || nestedList.size() == 0) {
                return 0;
            }

            int max = 0;
            for (NestedInteger n : nestedList) {
                if (n.isInteger()) {
                    max = Math.max(max, 1);
                } else {
                    max = Math.max(max, getDepth(n.getList()) + 1);
                }
            }
            return max;
        }

        private int helper(List<NestedInteger> nestedList, int level) {
            if (nestedList == null) {
                return 0;
            }

            int res = 0;
            for (NestedInteger n : nestedList) {
                if (n.isInteger()) {
                    res += n.getInteger() * level;
                } else {
                    res += helper(n.getList(), level - 1);
                }
            }

            return res;
        }
    }

    /**
     * BFS one pass
     *
     * Each integer get added one extra time for the mere existence of each one level under it.
     * The concept of weight here is implemented with repeated addition;
     *
     * Shortcoming : this solution modifies input "nestedList"
     */
    class Solution2 {
        public int depthSumInverse(List<NestedInteger> nestedList) {
            if (null == nestedList || nestedList.size() == 0) {
                return 0;
            }

            int res = 0;
            int unweight = 0;

            while (!nestedList.isEmpty()) {
                List<NestedInteger> nextLevel = new ArrayList<>();

                for(NestedInteger n : nestedList) {
                    if (n.isInteger()) {
                        unweight += n.getInteger();
                    } else {
                        /**
                         * !!!
                         * addAll(n.getList())
                         */
                        nextLevel.addAll(n.getList());
                    }
                }

                nestedList = nextLevel;
                res += unweight;
            }

            return res;
        }
    }


}
