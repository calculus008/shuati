package leetcode;

import java.util.*;

/**
 * Created by yuank on 7/17/18.
 */
public class LE_126_Word_Ladder_II {
    /**
         Given two words (beginWord and endWord), and a dictionary's word list, find all
         shortest transformation sequence(s) from beginWord to endWord, such that:

         Only one letter can be changed at a time
         Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
         Note:

         Return an empty list if there is no such transformation sequence.
         All words have the same length.
         All words contain only lowercase alphabetic characters.
         You may assume no duplicates in the word list.
         You may assume beginWord and endWord are non-empty and are not the same.
         Example 1:

         Input:
         beginWord = "hit",
         endWord = "cog",
         wordList = ["hot","dot","dog","lot","log","cog"]

         Output:
         [
         ["hit","hot","dot","dog","cog"],
         ["hit","hot","lot","log","cog"]
         ]

         Example 2:

         Input:
         beginWord = "hit"
         endWord = "cog"
         wordList = ["hot","dot","dog","lot","log"]

         Output: []

         Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

         Hard
     */

    /**
     The basic idea is:

     1). Use BFS to find the shortest distance between start and end, tracing the distance of crossing nodes
         from start node to end node, and store node's next level neighbors to HashMap;

     2). Use DFS to output paths with the same distance as the shortest distance from distance HashMap: compare if
         the distance of the next level node equals the distance of the current node + 1.
     **/
    public List<List<String>> findLadders(String start, String end, List<String> wordList) {
        List<List<String>> ladders = new ArrayList<List<String>>();

        if (wordList == null || wordList.size() == 0 || start == null || end == null) {
            return ladders;
        }


        HashSet<String> dict = new HashSet<>();
        for (String word : wordList) {
            dict.add(word);
        }

        /**
         * Need to clarify if endWord exists in the given dictionary, here it must exist.
         */
        if (!dict.contains(end)) {
            return ladders;
        }

        //neighbors list for each node
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        //distance from a given string to start
        Map<String, Integer> distance = new HashMap<String, Integer>();

        dict.add(start);
        // dict.add(end);//for lintcode 121 since end may not be in given dict


        List<String> path = new ArrayList<String>();

        // bfs(map, distance, start, end, dict);
        // dfs(ladders, path, end, start, distance, map);

        /**
         * Improvement :
         * Calling bfs from end to start, the distance is from cur to end
         * Then calling dfs to from start to end.
         *
         * By doing this, we save the work of "Collections.reverse()" when we add the path in dfs()
         *
         */
        bfs(map, distance, end, start, dict);
        dfs(ladders, path, start, end, distance, map);

        return ladders;
    }

    void bfs(Map<String, List<String>> map, Map<String, Integer> distance,
             String start, String end, Set<String> dict) {
        Queue<String> q = new LinkedList<String>();
        q.offer(start);
        distance.put(start, 0);
        for (String s : dict) {
            map.put(s, new ArrayList<String>());
        }

        while (!q.isEmpty()) {
            String crt = q.poll();

            List<String> nextList = expand(crt, dict);
            for (String next : nextList) {
                map.get(next).add(crt);//create neighbor lists for all nodes
                /**
                     in BFS , we can be sure that the distance of each node is the shortest one ,
                     because once we have visited a node, we update the distance , if we first met
                     one node ,it must be the shortest distance. if we met the node again ,its distance
                     must not be less than the distance we first met and set.
                 */
                if (!distance.containsKey(next)) {//Distance from current node to start node
                    distance.put(next, distance.get(crt) + 1);
                    if (end.equals(next)){// Found the shortest path
                        break;
                    } else {
                        q.offer(next);
                    }
                }
            }
        }
    }

    List<String> expand(String crt, Set<String> dict) {
        List<String> expansion = new ArrayList<String>();

        for (int i = 0; i < crt.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch != crt.charAt(i)) {
                    String expanded = crt.substring(0, i) + ch
                              + crt.substring(i + 1);
                    if (dict.contains(expanded)) {
                        expansion.add(expanded);
                    }
                }
            }
        }

        return expansion;
    }

    void dfs(List<List<String>> ladders, List<String> path, String crt,
             String start, Map<String, Integer> distance,
             Map<String, List<String>> map) {
        path.add(crt);

        if (crt.equals(start)) {//base case
//            Collections.reverse(path);
            ladders.add(new ArrayList<String>(path));
//            Collections.reverse(path);
        } else {
            for (String next : map.get(crt)) {
                /**
                 in dfs , the reason for "if (distance.get(next) == distance.get(cur) + 1)" is just in case that the next node is
                 the next level of current node， otherwise it can be one of the parent nodes of current node，or it is not the
                 shortest node . Since in BFS, we record both the next level nodes and the parent node as neighbors of current node.
                 use distance.get(cur)+1 we can make sure the path is the shortest one.
                 */
                if (distance.containsKey(next) && distance.get(crt) == distance.get(next) + 1) {
                    dfs(ladders, path, next, start, distance, map);
                }
            }
        }

        path.remove(path.size() - 1);
    }
}
