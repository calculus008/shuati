package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LE_1130_Minimum_Cost_Tree_From_Leaf_Values {
    /**
     * Given an array arr of positive integers, consider all binary trees such that:
     *
     * Each node has either 0 or 2 children;
     * The values of arr correspond to the values of each leaf in an in-order traversal
     * of the tree.  (Recall that a node is a leaf if and only if it has 0 children.)
     * The value of each non-leaf node is equal to the product of the largest leaf value
     * in its left and right subtree respectively.
     *
     * Among all possible binary trees considered, return the smallest possible sum of
     * the values of each non-leaf node.  It is guaranteed this sum fits into a 32-bit
     * integer.
     *
     *
     *
     * Example 1:
     *
     * Input: arr = [6,2,4]
     * Output: 32
     * Explanation:
     * There are two possible trees.  The first has non-leaf node sum 36, and the second
     * has non-leaf node sum 32.
     *
     *     24            24
     *    /  \          /  \
     *   12   4        6    8
     *  /  \               / \
     * 6    2             2   4
     *
     *
     * Constraints:
     *
     * 2 <= arr.length <= 40
     * 1 <= arr[i] <= 15
     * It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values
     */

    class Solution_clean {
        //https://www.youtube.com/watch?v=xcYkzSrgOmY
        public int mctFromLeafValues(int[] arr) {
            Stack<Integer> stack = new Stack<>();
            stack.push(Integer.MAX_VALUE);
            int res = 0;

            for (int n : arr) {
                while (stack.peek() <= n) {
                    res += stack.pop() * Math.min(stack.peek(), n);
                }
                stack.push(n);
            }

            while (stack.size() > 2) {
                res += stack.pop() * stack.peek();
            }

            return res;
        }
    }

    /**
     * https://www.youtube.com/watch?v=xcYkzSrgOmY
     *
     * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/discuss/339959/One-Pass-O(N)-Time-and-Space
     *
     * https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/discuss/340489/Summary-and-reasoning-of-different-solutions
     *
     * The problem can be translated as following:
     * Given an array A, choose two neighbors in the array a and b,
     * we can remove the smaller one min(a,b) and the cost is a * b.
     * What is the minimum cost to remove the whole array until only one left?
     *
     * To remove a number a, it needs a cost a * b, where b >= a.
     * So a has to be removed by a bigger number.
     * We want to minimize this cost, so we need to minimize b.
     *
     * b has two candidates, the first bigger number on the left,
     * the first bigger number on the right.
     *
     * The cost to remove a is a * min(left, right).
     *
     * lee215's O(N) solution
     * This solution removes the less of pair arr[i] and arr[i + 1] which has minimum product during each iteration.
     * Now we think about it, each iteration actually removes a local minimum value.
     *   For elements arr[i - 1], arr[i] and arr[i + 1] where arr[i] is the local minium.
     *   The product added to final result is arr[i] * min(arr[i - 1], arr[i + 1])
     *
     * The problem can be translated into removing all local minimum values while finding the first greater element
     * on the left and on the right.
     *
     * To me, the observation that leads to method 3 from method 2, is that the cost of removing an element a[i]
     * with an element after a[i+1] or before a[i-1] will be higher or same as those elements will have to be at
     * least as big as the min(a[i-1], a[i+1]) - otherwise they will not survive as the max leaf.
     *
     * O(n)
     */
    class Solution1 {
        public int mctFromLeafValues(int[] arr) {
            Stack<Integer> stack = new Stack<>();
            stack.push(Integer.MAX_VALUE);

            int res = 0;
            for (int n : arr) {
                while (stack.peek() <= n) {//"<="
                    int a = stack.pop();
                    res += a * Math.min(stack.peek(), n);
                }
                stack.push(n);
            }

            while (stack.size() > 2) {
                res += stack.pop() * stack.peek();
            }

            return res;
        }
    }

    /**
     * O(n ^ 2)
     *
     * 1.From the first solution, we blindly try all possible ways
     *   to divide the array.But do we really have no idea of the optimal division?
     * 2.We actually know a little better. In the final tree, value
     *   of non-leaf nodes arefrom maximum leaf values from left and right subtrees.
     * 3.If in the final tree, the maximum value from the array is at deepest level of the tree,
     *   its value will be used multiple times for multiple parent nodes, which is obviously not ideal.
     * 4.So the finding here is we should place the smallest value from array at deepest level.
     *   Smallest value here means the product of pair arr[i] and arr[i + 1] where the product is smallest.
     * 5.What happens to the pair after that? The smaller of the pair will never be used again and
     *   we can simply discard it.
     * 6.The O(N^2) solution is to go through the array, find the index i so that arr[i] * arr[i + 1]
     *   is the smallest (arr[i] * arr[i + 1] is added to the result) and discard the less of the pair.
     *   Keep doing so until arr size is 1.
     */
    class Solution2 {
        public int mctFromLeafValues(int[] arr) {
            List<Integer> list = new ArrayList<>();
            for (int num : arr) {
                list.add(num);
            }

            int sum = 0;
            while (list.size() > 1) {
                int min = Integer.MAX_VALUE;
                int idx = -1;

                for (int i = 1; i < list.size(); i++) {
                    if (min > list.get(i) * list.get(i - 1)) {
                        idx = list.get(i - 1) < list.get(i) ? i - 1 : i;
                        min = list.get(i) * list.get(i - 1);
                    }
                }

                sum += min;
                list.remove(idx);
            }

            return sum;
        }
    }
}
