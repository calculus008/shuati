package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuank on 10/16/18.
 */
public class LE_425_Word_Squares {
    /**
         Given a set of words (without duplicates), find all word squares you can build from them.

         A sequence of words forms a valid word square if the kth row and column read the exact
         same string, where 0 ≤ k < max(numRows, numColumns).

         For example, the word sequence ["ball","area","lead","lady"] forms a word square because
         each word reads the same both horizontally and vertically.

         b a l l
         a r e a
         l e a d
         l a d y

         Note:
         There are at least 1 and at most 1000 words.
         All words will have the exact same length.
         Word length is at least 1 and at most 5.
         Each word contains only lowercase English alphabet a-z.

         Example 1:

         Input:
         ["area","lead","wall","lady","ball"]

         Output:
         [
             [ "wall",
               "area",
              "lead",
              "lady"
             ],
             [ "ball",
              "area",
              "lead",
              "lady"
             ]
         ]

         Explanation:
         The output consists of two word squares. The order of output does not matter
         (just the order of words in each word square matters).

         Example 2:

         Input:
         ["abat","baba","atan","atal"]

         Output:
         [
             [ "baba",
               "abat",
               "baba",
               "atan"
             ],
             [ "baba",
               "abat",
               "baba",
             "  atal"
             ]
         ]

         Explanation:
         The output consists of two word squares. The order of output does not matter
         (just the order of words in each word square matters).

         Hard
     */

    /**
     * Solution 1
     * Use HashMap to get the list of words that share the given common prefix
     * Time  : O()
     * Space : O(n * l)
     */
    public class Solution1 {
        public List<List<String>> wordSquares(String[] words) {
            List<List<String>> res = new ArrayList<>();
            if (words == null || words.length == 0) return res;

            int size = words[0].length();
            HashMap<String, List<String>> map = new HashMap<>();
            buildMap(words, map);

            //!!! it's temp, not res, that goes into helper
            List<String> temp = new ArrayList<>();
            for (String word : words) {
                temp.add(word);
                helper(res, map, size, temp);
                temp.remove(temp.size() - 1);//!!!
            }

            return res;
        }

        private void helper(List<List<String>> res, HashMap<String, List<String>> map, int size, List<String> temp) {
            //!!! temp.size(), NOT res.size()
            int n = temp.size();

            //!!! base case
            if (temp.size() == size) {
                res.add(new ArrayList<>(temp));
                return;
            }

            //!!! prefix is the nth char of every word that is already in temp list
            StringBuilder sb = new StringBuilder();
            for (String s : temp) {
                sb.append(s.charAt(n));
            }
            String key = sb.toString();

            //!!!
            if (!map.containsKey(key)) return;

            for (String s : map.get(key)) {
                temp.add(s);
                helper(res, map, size, temp);
                temp.remove(temp.size() - 1);
            }
        }

        private void buildMap(String[] words, HashMap<String, List<String>> map) {
            for (String word : words) {
                //!!!only "< word.length - 1", only add prefix, not the complete word
                for (int i = 0; i < word.length() - 1; i++) {
                    String key = word.substring(0, i + 1);

                    if (!map.containsKey(key)) {
                        map.put(key, new ArrayList<>());
                    }

                    //!!!
                    map.get(key).add(word);
                    //Stupid!!!
                    //map.put(key, map.get(key).add(i));

                    //or use one line
                    //map.computeIfAbsent(key, k -> new HashSet()).add(word);
                }
            }
        }
    }

    /**
     * Solution 2
     * Use Trie, copied from leetcode, very clear explanation here:
     *
     * https://leetcode.com/problems/word-squares/discuss/91333/Explained.-My-Java-solution-using-Trie-126ms-1616
     *
     * Main logic is the same as Solution1, the only difference is that it stores prefix in Trie.
     *Call findByPrefix() in Trie class to get list of of words that share the given common prefix
     *
     * Compare with HashMap, Trie saves space, Time complexcity is the same.
     * Implementing Trie takes time, so if not required, use HashMap saves time.
     */
    public class Solution2 {
        class TrieNode {
            List<String> startWith;
            TrieNode[] children;

            TrieNode() {
                startWith = new ArrayList<>();
                children = new TrieNode[26];
            }
        }

        class Trie {
            TrieNode root;

            Trie(String[] words) {
                root = new TrieNode();
                for (String w : words) {
                    TrieNode cur = root;
                    for (char ch : w.toCharArray()) {
                        int idx = ch - 'a';

                        if (cur.children[idx] == null) {
                            cur.children[idx] = new TrieNode();
                        }

                        cur.children[idx].startWith.add(w);
                        cur = cur.children[idx];
                    }
                }
            }

            /**
             * !!! unique method for this problem, get list of of words that share the given common prefix
             */
            List<String> findByPrefix(String prefix) {
                List<String> ans = new ArrayList<>();
                TrieNode cur = root;
                for (char ch : prefix.toCharArray()) {
                    int idx = ch - 'a';

                    //!!!prefix does not exist
                    if (cur.children[idx] == null) {
                        return ans;
                    }

                    cur = cur.children[idx];
                }
                ans.addAll(cur.startWith);
                return ans;
            }
        }

        public List<List<String>> wordSquares(String[] words) {
            List<List<String>> ans = new ArrayList<>();
            if (words == null || words.length == 0)
                return ans;
            int len = words[0].length();
            Trie trie = new Trie(words);
            List<String> ansBuilder = new ArrayList<>();
            for (String w : words) {
                ansBuilder.add(w);
                search(len, trie, ans, ansBuilder);
                ansBuilder.remove(ansBuilder.size() - 1);
            }

            return ans;
        }

        private void search(int len, Trie tr, List<List<String>> ans,
                            List<String> ansBuilder) {
            if (ansBuilder.size() == len) {
                ans.add(new ArrayList<>(ansBuilder));
                return;
            }

            int idx = ansBuilder.size();
            StringBuilder prefixBuilder = new StringBuilder();
            for (String s : ansBuilder) {
                prefixBuilder.append(s.charAt(idx));
            }

            List<String> startWith = tr.findByPrefix(prefixBuilder.toString());

            for (String sw : startWith) {
                ansBuilder.add(sw);
                search(len, tr, ans, ansBuilder);
                ansBuilder.remove(ansBuilder.size() - 1);
            }
        }
    }
}
