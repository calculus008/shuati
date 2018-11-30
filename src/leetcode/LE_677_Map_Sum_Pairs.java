package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 11/30/18.
 */
public class LE_677_Map_Sum_Pairs {
    /**
         Implement a MapSum class with insert, and sum methods.

         For the method insert, you'll be given a pair of (string, integer).
         The string represents the key and the integer represents the value.
         If the key already existed, then the original key-value pair will be overridden to the new one.

         For the method sum, you'll be given a string representing the prefix,
         and you need to return the sum of all the pairs' value whose key starts with the prefix.

         Example 1:
         Input: insert("apple", 3), Output: Null
         Input: sum("ap"), Output: 3
         Input: insert("app", 2), Output: Null
         Input: sum("ap"), Output: 5

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-677-map-sum-pairs/
     *
     * HashMap + Trie
     * 98 ms
     *
     * Key Insights :
     * 1.We still need a HashMap to work with Trie, because we need to update the latest value for a given key.
     *   Then when we insert new pair into Trie, we actually insert the delta between new value and the old value.
     * 2.We add delta with current value in each TrieNode when doing insert. Then when we query with given prefix,
     *   we just need to search the prefix in Trie and return the sum value saved in the last node on the path,
     *   no calculation is needed.
     *
     *
     * Solution 1 and Solution 2 should have the same query Time complexity : O(l), l is length of prefix
     * Solution 2 is faster because Solution 1 has cost for managing TrieNode
     *
     * Space for Solution 1 : O(n * l)
     * Space for Solution 2 : O(n * l * 26), but since there are many shared TrieNode, its space should be better than Solution 1.
     *
     **/
    class MapSum1 {
        class Trie {
            class TrieNode {
                TrieNode[] next;
                boolean isWord;
                int sum;

                public TrieNode() {
                    next = new TrieNode[26];
                }
            }

            //!!!
            TrieNode root = new TrieNode();

            public void insert(String s, int val) {
                if (s == null || s.length() == 0) {
                    return;
                }

                TrieNode cur = root;
                System.out.println(s + ",val=" + val);
                for (int i = 0; i < s.length(); i++) {
                    int idx = s.charAt(i) - 'a';//!!! " - 'a'"
                    if (cur.next[idx] == null) {
                        cur.next[idx] = new TrieNode();
                    }
                    cur = cur.next[idx];

                    /**
                     * Add values together
                     */
                    cur.sum += val;
                }

                cur.isWord = true;
            }

            public int getPrefixSum(String s) {
                if (s == null || s.length() == 0) {
                    return 0;
                }

                int res;
                TrieNode cur = root;
                for (int i = 0; i < s.length(); i++) {
                    int idx = s.charAt(i) - 'a';
                    if (cur.next[idx] == null) {
                        return 0;
                    }

                    cur = cur.next[idx];
                }

                return cur.sum;
            }
        }

        Trie trie;
        Map<String, Integer> map;

        /** Initialize your data structure here. */
        public MapSum1() {
            trie = new Trie();
            map = new HashMap<>();
        }

        public void insert(String key, int val) {
            int delta = map.containsKey(key) ? val - map.get(key) : val;

            map.put(key, val);//!!!
            trie.insert(key, delta);
        }

        public int sum(String prefix) {
            return trie.getPrefixSum(prefix);
        }
    }

    /**
     * Two HashMap Solution
     * 63 ms
     *
     */
    class MapSum2 {
        Map<String, Integer> map;
        Map<String, Integer> sum;

        /** Initialize your data structure here. */
        public MapSum2() {
            map = new HashMap<>();
            sum = new HashMap<>();
        }

        public void insert(String key, int val) {
            if (key == null || key.length() == 0) return;

            char[] c = key.toCharArray();
            String prefix = "";
            int delta = map.containsKey(key) ? val - map.get(key) : val;

            for (int i = 0; i < c.length; i++) {
                prefix += c[i];
                map.put(key, val);
                sum.put(prefix, sum.getOrDefault(prefix, 0) + delta);
            }
        }

        public int sum(String prefix) {
            if (prefix == null || prefix.length() == 0) return 0;

            return sum.getOrDefault(prefix, 0);
        }
    }
}
