package leetcode;

import java.util.*;

/**
 * Created by yuank on 7/17/18.
 */
public class LE_126_Word_Ladder_II {
    /**
         Given two words (beginWord and endWord), and a dictionary's word list, find ALL
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
     * http://zxi.mytechroad.com/blog/searching/leetcode-126-word-ladder-ii/
     *
         The basic idea is:

         1). Use BFS to find the shortest dirs between start and end, tracing the dirs of crossing nodes
             from start node to end node, and store node's next level neighbors to HashMap;

         2). Use DFS to output paths with the same dirs as the shortest dirs from dirs HashMap: compare if
             the dirs of the next level node equals the dirs of the current node + 1.

         Since we need to construct the complete graph from start to end, we can't remove word from dict, as we did in 127
     **/
    class Solution1 {
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

            //neighbour list for each node (parents and the next level nodes)
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            //dirs from a given string to start
            Map<String, Integer> distance = new HashMap<String, Integer>();

            dict.add(start);
            // dict.add(end);//for lintcode 121 since end may not be in given dict


            List<String> path = new ArrayList<String>();

            // bfs(dist, dirs, start, end, dict);
            // dfs(ladders, path, end, start, dirs, dist);

            /**
             * Improvement :
             * Calling bfs from end to start, the dirs is from cur to end
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

                /**
                 * here, it is different from 127, we don't care about the levels,
                 * we just construct the graph formed by the words. So we don't have
                 * the for loop to loop through all elements in queue for the current
                 * level.
                 */
                List<String> nextList = expand(crt, dict);
                for (String next : nextList) {
                    map.get(next).add(crt);//create neighbor lists for all nodes
                    /**
                     in BFS , we can be sure that the dirs of each node is the shortest one ,
                     because once we have visited a node, we update the dirs , if we first met
                     one node ,it must be the shortest dirs. if we met the node again ,its dirs
                     must not be less than the dirs we first met and set.
                     */
                    if (!distance.containsKey(next)) {//Distance from current node to start node
                        distance.put(next, distance.get(crt) + 1);
                        if (end.equals(next)) {// Found the shortest path
                            /**
                             * elements in nextList is generated by expand() and
                             * they are unique, so once we find the end word, no
                             * need to go further, stop the iteration
                             */
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

        void dfs(List<List<String>> ladders, List<String> path,
                 String crt, String target,
                 Map<String, Integer> distance,
                 Map<String, List<String>> map) {
            //!!!
            path.add(crt);

            if (crt.equals(target)) {//base case
//            Collections.reverse(path);
                ladders.add(new ArrayList<String>(path));
//            Collections.reverse(path);
            } else {
                for (String next : map.get(crt)) {
                    /**
                     in dfs , the reason for "if (dirs.get(next) == dirs.get(cur) + 1)" is just in case that the next node is
                     the next level of current node， otherwise it can be one of the parent nodes of current node，or it is not the
                     shortest node . Since in BFS, we record both the next level nodes and the parent node as neighbors of current node.
                     use dirs.get(cur)+1 we can make sure the path is the shortest one.
                     */
                    if (distance.containsKey(next) && distance.get(crt) == distance.get(next) + 1) {
                        dfs(ladders, path, next, target, distance, map);
                    }
                }
            }

            path.remove(path.size() - 1);//backtracking
        }
    }

    /**
     *           dot - dog
     *          /         \
     *  hit - hot          cog
     *          \         /
     *          lot - log
     *
     *  bfs starts from cog
     *
     *  dist:
     *  cog -
     *  dog - cog
     *  log - cog
     *  lot - log
     *  dot - dog
     *  hot - dot, lot
     *  hit - hot
     *
     *  dirs:
     *  cog : 1
     *  dog : 2
     *  log : 2
     *  lot : 3
     *  dot : 3
     *  hot : 4
     *  hit : 5
     *
     *
     *
     */

    class Solution2 {
        public List<List<String>> findLadders(String start, String end, List<String> wordList) {
            List<List<String>> ladders = new ArrayList<List<String>>();

            if (wordList == null || wordList.size() == 0 || start == null || end == null) {
                return ladders;
            }


            HashSet<String> dict = new HashSet<>();
            for (String word : wordList) {
                dict.add(word);
            }

            if (dict.contains(start)) {
                dict.remove(start);
            }

            if (!dict.contains(end)) {
                return ladders;
            }

            //Map : node -> all of its parents
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            /**
             * dirs from a given node to start
             *
             * It also acts as the role of visited, help us to check if a given node
             * has already appeared in bfs traversal
             */
            Map<String, Integer> distance = new HashMap<String, Integer>();

            dict.add(start);
            // dict.add(end);//for lintcode 121 since end may not be in given dict

            /**
                !!! "(..end, start,..)" :we do bfs() from end to start, so when we do dfs(), we can do it from start to end
             **/
            if (bfs(map, distance, end, start, dict)) {
                List<String> path = new ArrayList<String>();
                // path.add();

                // dfs(ladders, path, end, start, dirs, dist);
                dfs(ladders, path, start, end, distance, map);
            }

            return ladders;
        }

        private boolean bfs(Map<String, List<String>> map, Map<String, Integer> distance,
                            String start, String end, Set<String> dict) {
            boolean foundEnd = false;

            Queue<String> q = new LinkedList<String>();
            q.offer(start);
            distance.put(start, 1);

            //init dist, put all valid strings from dict into dist
            for (String s : dict) {
                map.put(s, new ArrayList<String>());
            }

            while (!q.isEmpty()) {
                String crt = q.poll();

                List<String> nextList = expand(crt, dict);

                for (String next : nextList) {
                    map.get(next).add(crt);//update next node's parent list

                    /**
                     the node "next" appear for the first time in BFS traveral,
                     with BFS, we know this is the shortest path from start node
                     to this node.

                     Here dirs acts as visited to tell if a node is visited
                     **/
                    if (!distance.containsKey(next)) {
                        distance.put(next, distance.get(crt) + 1);

                        if (end.equals(next)){//
                            foundEnd = true;
                            /**
                             * this only breaks from the for loop on nextList,
                             * it is all the valid nodes expanded from an element in q,
                             * once we hit the target node, no need to continue this loop
                             *
                             * Meanwhile, outer while loop on q still goes on, this guaranteed
                             * it will process all nodes at the current level - then the case
                             * that multiple nodes in this level points to the target end is
                             * covered.
                             */
                            break;
                        } else {
                            q.offer(next);
                        }
                    }
                }
            }

            return foundEnd;
        }

        /**
            return all valid strings expaned from the given string
            optimized by removing substring operations
         **/
        List<String> expand(String s, Set<String> dict) {
            List<String> expansion = new ArrayList<String>();

            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char temp = chars[i];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == temp) {
                        continue;
                    }

                    chars[i] = ch;
                    String expanded = new String(chars);
                    if (dict.contains(expanded)) {
                        expansion.add(expanded);
                    }

                    chars[i] = temp;
                }
            }

            return expansion;
        }

        /**
             这个dfs的写法有些特殊：
             1.For backtracking, add and remove at the very beginning and end.
             2.Can't return right after adding a path to ladder, we also need to remove last element for backtracking,
               therefore, if ... else...
             3." if (dirs.get(cur) == dirs.get(parent) + 1)"
               Only recurse to the next level if cur is expanded from parent and its path length is
               parent's path length + 1.
         **/
        private void dfs (List<List<String>> ladders,
                          List<String> path,
                          String cur,
                          String target,
                          Map<String, Integer> distance,
                          Map<String, List<String>> map) {
            path.add(cur);//!!!

            if (cur.equals(target)) {
                ladders.add(new ArrayList<>(path));
            } else {
                for (String next : map.get(cur)) {
                    /**
                     * !!! "dirs.get(cur) == dirs.get(next) + 1":
                     * this makes sure "next" is the on the shortest path to target
                     */
                    if (distance.get(cur) == distance.get(next) + 1) {//!!!
                        dfs(ladders, path, next, target, distance, map);
                    }
                }
            }

            path.remove(path.size() - 1);//!!!
        }
    }
}
