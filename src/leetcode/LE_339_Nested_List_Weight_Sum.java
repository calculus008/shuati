package leetcode;

import java.util.*;

/**
 * Created by yuank on 5/18/18.
 */
public class LE_339_Nested_List_Weight_Sum {
    /**
         Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

         Each element is either an integer, or a list -- whose elements may also be integers or other lists.

         Example 1:
         Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)

         Example 2:
         Given the list [1,[4,[6]]], return 27. (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)

         Easy
     */

    /**
     * Use the same BFS as LE_364_Nested_List_Weight_Sum_II.
     * Easy to remember
     */
    class Solution_BFS {
        public int depthSum(List<NestedInteger> nestedList) {
            if (nestedList == null || nestedList.size() == 0) {
                return 0;
            }

            int level = 0;
            int res = 0;
            Queue<NestedInteger> queue = new LinkedList<NestedInteger>(nestedList);

            while (!queue.isEmpty()) {
                int size = queue.size();
                level++;

                for (int i = 0; i < size; i++) {
                    NestedInteger ni = queue.poll();
                    if (ni.isInteger()) {
                        res += ni.getInteger() * level;
                    } else {
                        queue.addAll(ni.getList()); // Same to offer()
                    }
                }
            }

            return res;
        }
    }

    /**
         Solution 1 DFS
         Time :  O(n)
         Space : O(k) k is the max level of depth
     **/
    public int depthSumDFS(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0) {
            return 0;
        }

        return helper(nestedList, 1);
    }

    private int helper(List<NestedInteger> nestedList, int depth) {
        //!!! base case
        if (nestedList == null) return 0;

        int res = 0;
        for (NestedInteger element : nestedList) {
            if (element.isInteger()) {
                res += element.getInteger() * depth;
            } else {
                res += helper(element.getList(), depth + 1);
            }
        }

        return res;
    }


    //Solution 2 : BFS
    public int depthSumBFS(List<NestedInteger> nestedList) {
        if(nestedList == null || nestedList.size() == 0) return 0;

        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        int depth = 1;
        int res = 0;

        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i=0; i<size; i++) {
                NestedInteger nest = queue.poll();
                if(nest.isInteger()) {
                    res += nest.getInteger() * depth;
                } else {
                    queue.addAll(nest.getList());
                }
            }
            depth++;
        }

        return res;
    }

    /**
     * Solution 3, iterative using Iterator, the same logic as LE_341_Flatten_Nested_List_Iterator
     */
    public int depthSum(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0) return 0;

        Stack<Iterator<NestedInteger>> stack = new Stack<>();
        stack.push(nestedList.iterator());
        int res = 0;
        int level = 1;

        while (!stack.isEmpty()) {
            Iterator<NestedInteger> top = stack.peek();
            if (!top.hasNext()) {
                stack.pop();
                level--;
                continue;
            }

            NestedInteger n = top.next();
            if (n.isInteger()) {
                res += level * n.getInteger();
            } else {
                stack.push(n.getList().iterator());
                level++;
            }
        }

        return res;
    }

    /**
     * Solution 4
     * Same logic as Solution 3, we simply use stack.size() to get the current level,
     * not need to maintain it with variable 'level'
     */
    public int depthSum4(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return 0;
        }

        //!!!"Stack<Iterator>"
        Stack<Iterator> stack = new Stack<>();
        stack.push(nestedList.iterator());

        int res = 0;
        while (!stack.isEmpty()) {
            Iterator<NestedInteger> cur = stack.peek();
            if (!cur.hasNext()) {
                stack.pop();
                continue;
            }

            NestedInteger n = cur.next();
            if (n.isInteger()) {
                res += stack.size() * n.getInteger();
            } else {
                stack.push(n.getList().iterator());//!!! "n.getList.iterator()"
            }
        }

        return res;
    }

}
