package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 11/29/18.
 */
public class LE_745_Prefix_And_Suffix_Search {
    /**
         Given many words, words[i] has weight i.

         Design a class WordFilter that supports one function, WordFilter.f(String prefix,
         String suffix). It will return the word with given prefix and suffix with maximum weight.
         If no word exists, return -1.

         Examples:
         Input:
         WordFilter(["apple"])
         WordFilter.f("a", "e") // returns 0
         WordFilter.f("b", "") // returns -1

         Note:
         words has length in range [1, 15000].
         For each test case, up to words.length queries WordFilter.f may be made.
         words[i] has length in range [1, 10].
         prefix, suffix have lengths in range [0, 10].
         words[i] and prefix, suffix queries consist of lowercase letters only.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-745-prefix-and-suffix-search/
     *
     * HashMap Trie Design
     */

    /**
     * Brutal Force + HashMap
     *
     * Time  : O(n * l ^ 3) + O(QL)
     * Space : O(n * l ^ 3)
     *
     * Use substring(), 443 ms
     */
    class WordFilter1 {
        Map<String, Integer> map;

        public WordFilter1(String[] words) {
            map = new HashMap<>();

            for (int k = 0; k < words.length; k++) {
                for (int i = 0; i <= words[k].length(); i++) {//!!! "<="
                    for (int j = 0; j <= words[k].length(); j++) {
                        String key = words[k].substring(0, i) + "_" + words[k].substring(j);
                        map.put(key, k);
                    }
                }
            }
        }

        public int f(String prefix, String suffix) {
            String key = prefix + "_" + suffix;
            return map.containsKey(key) ? map.get(key) : -1;
        }
    }

    /**
     * Brutal Force
     * Without substring(), 294 ms
     */
    class WordFilter2 {
        Map<String, Integer> map;

        public WordFilter2(String[] words) {
            map = new HashMap<>();

            for (int k = 0; k < words.length; k++) {
                int n = words[k].length();
                char[] w = words[k].toCharArray();
                String prefix = "";

                for (int i = 0; i <= n; i++) {
                    if (i > 0) {//!!!
                        prefix += w[i - 1];
                    }

                    String suffix = "";
                    for (int j = n; j >= 0; j--) {//!!! move backward
                        if (j != n) {//!!!
                            suffix = w[j] + suffix;
                        }

                        map.put(prefix + "_" + suffix, k);
                    }
                }
            }
        }

        public int f(String prefix, String suffix) {
            String key = prefix + "_" + suffix;
            return map.containsKey(key) ? map.get(key) : -1;
        }
    }

    /**
     * Trie
     *
     * Time  : O(n * l ^ 2)
     * Space : O(n * l ^ 2)
     *
     * The key step to reduce time complexity is the key inserted into Trie is suffix + "{" _ word.
     * Because of the natrue of Trie, we save the work of generating prefix, when search down Trie,
     * it will go by each char of the word hence the prefix.
     *
     * 389 ms
     */
    class WordFilter3 {
        /**
         * Trie class adapted for this problem
         *
         * 1."index" : save the index value which is returned by search(), also pass it to insert()
         * 2.Has 27 children instead of 26. The extra one is for separator between suffix and the word.
         *   Here we use '{' as separator since it is the next char to 'z' in ASCII table, so '{' - 'a' = 26,
         *   it simplifies the logic of tracking children (or next)
         *
         */
        public class Trie{
            class TrieNode {
                TrieNode[] next;
                int index;

                public TrieNode() {
                    next = new TrieNode[27];
                }
            }

            TrieNode root;
            public Trie() {
                root = new TrieNode();
            }

            public void insert(String s, int index) {
                if (s == null || s.length() == 0) {
                    return;
                }

                TrieNode cur = root;
                for (int i = 0; i < s.length(); i++) {
                    int idx = s.charAt(i) - 'a';


                    if (cur.next[idx] == null) {
                        cur.next[idx] = new TrieNode();
                    }
                    cur = cur.next[idx];
                    /**
                     * !!!add index value here
                     */
                    cur.index = index;
                }
            }

            public int search(String key) {
                if (key == null || key.length() == 0) {
                    return -1;
                }

                TrieNode cur = root;
                int n = key.length();
                for (int i = 0; i < n; i++) {
                    int idx = key.charAt(i) - 'a';
                    if (cur.next[idx] == null) {
                        return -1;
                    }
                    cur = cur.next[idx];
                }

                return cur.index;
            }

        }

        Trie trie;
        public WordFilter3(String[] words) {
            trie = new Trie();

            for (int i = 0; i < words.length; i++) {
                char[] w = words[i].toCharArray();
                int n = w.length;
                String suffix = "";
                for (int j = n; j >= 0; j--) {
                    if (j != n) {
                        suffix = w[j] + suffix;
                    }
                    String key = suffix + "{" + words[i];
                    trie.insert(key, i);
                }
            }
        }

        public int f(String prefix, String suffix) {
            /**
             * !!!
             * suffix在前
             */
            String key = suffix + "{" + prefix;
            return trie.search(key);
        }
    }

}
