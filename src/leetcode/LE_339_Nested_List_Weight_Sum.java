package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

}
