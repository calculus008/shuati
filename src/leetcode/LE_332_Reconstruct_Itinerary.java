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
     * In this problem, the path we are going to find is an itinerary which:
     *
     *  #1.uses all tickets to travel among airports
     *  #2.preferably in ascending lexical order of airport code
     *
     * Keep in mind that requirement 1 must be satisfied before we consider 2.
     * If we always choose the airport with the smallest lexical order, this
     * would lead to a perfectly lexical-ordered itinerary, but pay attention
     * that when doing so, there can be a "dead end" somewhere in the tickets
     * such that we are not able visit all airports (or we can't use all our
     * tickets), which is bad because it fails to satisfy requirement 1 of
     * this problem. Thus we need to take a step back and try other possible
     * airports, which might not give us a perfectly ordered solution, but
     * will use all tickets and cover all airports.
     *
     * Thus it's natural to think about the "backtracking" feature of DFS.
     * We start by building a graph and then sorting vertices in the adjacency
     * list so that when we traverse the graph later, we can guarantee the
     * lexical order of the itinerary can be as good as possible. When we have
     * generated an itinerary, we check if we have used all our airline tickets.
     * If not, we revert the change and try another ticket. We keep trying until
     * we have used all our tickets.
     *
     * The nodes which have odd degrees (int and out) are the entrance or exit. In your example it's JFK and A.
     * If there are no nodes have odd degrees, we could follow any path without stuck until hit the last exit node
     * The reason we got stuck is because that we hit the exit
     *
     * [
     * ["JFK","SFO"],
     * ["JFK","ATL"],
     * ["SFO","ATL"],
     * ["ATL","JFK"],
     * ["ATL","SFO"]
     * ]
     *
     * JFK : ATL, SFO
     * SFO : ATL
     * ATL : JFK, SFO
     *
     * ["JFK","ATL","JFK","SFO","ATL","SFO"].
     */
    class Solution1 {
        Map<String, PriorityQueue<String>> map;
        List<String> res;

        public List<String> findItinerary(String[][] tickets) {
            res = new LinkedList<>();
            map = new HashMap<>();

            /**
             * Build adjacent list
             */
            for (String[] ticket : tickets) {
                map.computeIfAbsent(ticket[0], k -> new PriorityQueue<>()).add(ticket[1]);
            }

            helper("JFK");
            return res;
        }

        private void helper(String s) {
            while (map.containsKey(s) && !map.get(s).isEmpty()) {
                /**
                 * "poll()", try all destinations that can be reached from s,
                 * pq in map guaranteed lexicon order.
                 */
                helper(map.get(s).poll());
            }

            /**
             * DFS, go all the way down to the last, should add at index 0.
             */
            res.add(0, s);
        }
    }

    class Solution_Practice {
        Map<String, PriorityQueue<String>> map;
        List<String> res;

        public List<String> findItinerary(List<List<String>> tickets) {
            res = new ArrayList<>();
            if (tickets == null || tickets.size() == 0) return res;

            map = new HashMap<>();

            for (List<String> ticket : tickets) {
                map.putIfAbsent(ticket.get(0), new PriorityQueue<>());
                map.get(ticket.get(0)).offer(ticket.get(1));
            }

            dfs("JFK");

            return res;
        }

        private void dfs(String s) {
            /**
             * !!!
             * Can't use the "if" line below, because it fails to add the airport
             * that does not have a destination ("dead end"), for example:
             *
             * [["MUC","LHR"],
             *  ["JFK","MUC"],
             *  ["SFO","SJC"],
             *  ["LHR","SFO"]]
             *
             *  It will generate wrong answer : ["JFK","MUC","LHR","SFO"].
             *
             *  The correct answer is ["JFK","MUC","LHR","SFO","SJC"]
             *
             */
//            if (!map.containsKey(s)) return;

            while (map.containsKey(s) && !map.get(s).isEmpty()) {
                String e = map.get(s).poll();
                dfs(e);
            }

            /**
             * !!!
             */
            res.add(0, s);
        }
    }
}
