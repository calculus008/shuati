package lintcode;

import common.UndirectedGraphNode;

import java.util.*;

/**
 * Created by yuank on 7/18/18.
 */
public class LI_531_Six_Degrees {
    /**
         Six degrees of separation is the theory that everyone and everything is six or fewer steps away,
         by way of introduction, from any other person in the world, so that a chain of "a friend of a friend"
         statements can be made to connect any two people in a maximum of six steps.

         Given a friendship relations, find the degrees of two people, return -1 if they can not been connected by friends of friends.

         Example
         Gien a graph:

         1------2-----4
         \          /
         \        /
         \--3--/
         {1,2,3#2,1,4#3,1,4#4,2,3} and s = 1, t = 4 return 2

         Gien a graph:

         1      2-----4
         /
         /
         3
         {1#2,4#3,4#4,2,3} and s = 1, t = 4 return -1

         Medium
     */

    public int sixDegrees(List<UndirectedGraphNode> graph, UndirectedGraphNode s, UndirectedGraphNode t) {
        if (graph == null) {
            return -1;
        }

        if (s.equals(t)) {// "s == t" also works
            return 0;
        }

        Set<UndirectedGraphNode> visited = new HashSet<>();
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.offer(s);

        int res = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            res++;

            for (int i = 0; i < size; i++) {
                UndirectedGraphNode cur = q.poll();
                for (UndirectedGraphNode n : cur.neighbors) {
                    if (n.equals(t)) { //"n == t" also works
                        return res;
                    }

                    if (!visited.contains(n)) {
                        visited.add(n);
                        q.offer(n);
                    }
                }
            }
        }

        return -1;
    }
}
