package lintcode;

import leetcode.NestedInteger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 11/9/18.
 */
public class LI_22_Flatten_List {
    /**
         Given a list, each element in the list can be a list or integer. flatten it into a simply list with integers.

         Example
         Given [1,2,[1,2]], return [1,2,1,2].

         Given [4,[3,[2,[1]]]], return [4,3,2,1].

         Challenge
         Do it in non-recursive.

         Notice
         If the element in the given list is a list, it can contain list too.

         Easy
     */

    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     *
     *     // @return true if this NestedInteger holds a single integer,
     *     // rather than a nested list.
     *     public boolean isInteger();
     *
     *     // @return the single integer that this NestedInteger holds,
     *     // if it holds a single integer
     *     // Return null if this NestedInteger holds a nested list
     *     public Integer getInteger();
     *
     *     // @return the nested list that this NestedInteger holds,
     *     // if it holds a nested list
     *     // Return null if this NestedInteger holds a single integer
     *     public List<NestedInteger> getList();
     * }
     */
    public class Solution {
        public List<Integer> flatten(List<NestedInteger> nestedList) {
            List<Integer> res = new ArrayList<>();
            if (null == nestedList) {
                return res;
            }

            Stack<Iterator> stack = new Stack<>();
            stack.push(nestedList.iterator());

            while (!stack.isEmpty()) {
                Iterator<NestedInteger> cur = stack.peek();
                if (!cur.hasNext()) {
                    stack.pop();
                    continue;
                }

                NestedInteger n = cur.next();
                if (n.isInteger()) {
                    res.add(n.getInteger());
                } else {
                    stack.push(n.getList().iterator());
                }
            }

            return res;
        }
    }
}
