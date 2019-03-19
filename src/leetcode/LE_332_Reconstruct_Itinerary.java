package leetcode;

import java.util.*;

/**
 * Created by yuank on 5/9/18.
 */
public class LE_332_Reconstruct_Itinerary {
    /**
         Given a list of airline tickets represented by pairs of departure and arrival airports [from, to],
         reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK.
         Thus, the itinerary must begin with JFK.

         Note:
         If there are multiple valid itineraries, you should return the itinerary that has the smallest
         lexical order when read as a single string.

         For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
         All airports are represented by three capital letters (IATA code).

         You may assume all tickets form at least one valid itinerary.
         Example 1:
         tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
         Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
         Example 2:
         tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
         Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
         Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.

         Medium
     */

    /**
     * DFS + Heap
     * Time  : O(nlogn)
     * Space : O(n)
     *
     *The nodes which have odd degrees (int and out) are the entrance or exit. In your example it's JFK and A.
     *If there are no nodes have odd degrees, we could follow any path without stuck until hit the last exit node
     *The reason we got stuck is because that we hit the exit
     */

    Map<String, PriorityQueue<String>> map;
    List<String> res;

    public List<String> findItinerary(String[][] tickets) {
        res = new LinkedList<>();
        map = new HashMap<>();
        for (String[] ticket : tickets) {
            //!!!
            map.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
        }
        helper("JFK");
        return res;
    }

    private void helper(String s) {
        while (map.containsKey(s) && !map.get(s).isEmpty()) {
            helper(map.get(s).poll());
        }
        res.add(0, s);
    }
}
