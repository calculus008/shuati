package leetcode;

import java.util.*;

public class LE_472_Concatenated_Words_1 {
    /**
     * Time  : O(n * k)   k is number of words, n is average length of a single word.
     * Space : O(26 ^ n),
     *         OR
     *         Let k be the amount of words in the trie. Then the boundary O(n * k) is much more useful,
     *         since it simply represents the max amount of characters in the trie, which is obviously
     *         also it's space boundary.
     *
     * https://softwareengineering.stackexchange.com/questions/348444/what-is-the-space-complexity-for-inserting-a-list-of-words-into-a-trie-data-stru
     */
    class Solution_Trie {
        class TrieNode {
            TrieNode[] children;
            boolean isWord;

            public TrieNode() {
                children = new TrieNode[26];
                isWord = false;
            }
        }

        TrieNode root = new TrieNode();

        private void addWord(String s) {
            TrieNode cur = root;
            for (char c : s.toCharArray()) {
                int idx = c - 'a';
                if (cur.children[idx] == null) {
                    cur.children[idx] = new TrieNode();
                }
                cur = cur.children[idx];
            }
            cur.isWord = true;
        }

        private boolean query(char[] chs, int start, int count) {
            TrieNode cur = root;
            int n = chs.length;

            for (int i = start; i < n; i++) {
                int idx = chs[i] - 'a';
                if (cur.children[idx] == null) return false;

                if (cur.children[idx].isWord) {
                    if (i == n - 1) {
                        return count >= 1;
                    }

                    if (query(chs, i + 1, count + 1)) return true;
                }

                cur = cur.children[idx];
            }

            return false;
        }

        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> res = new ArrayList<>();

            for (String w : words) {
                if (w == null || w.length() == 0) continue;
                addWord(w);
            }

            for (String w : words) {
                if (w == null || w.length() == 0) continue;
                if (query(w.toCharArray(), 0, 0)) {
                    res.add(w);
                }
            }

            return res;
        }
    }

    /**
     * Time  : O(k * n ^ 2), k is number of words, n is average length of a single word.
     * Space : O(n ^ 2)
     */
    class Solution_Recursion_With_Mem {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> list = new ArrayList<String>();
            Set<String> set = new HashSet(Arrays.asList(words));

            for(String word : words) {
                if (word == null || word.length() == 0) {
                    continue;
                }

                if (helper(word, set, new HashMap<>(), word)) {
                    list.add(word);
                }
            }
            return list;
        }

        private boolean helper(String s, Set<String> dict, Map<String, Boolean> mem, String original) {
            if (mem.containsKey(s)) return mem.get(s);

            if (!s.equals(original) && dict.contains(s)) {
                // if (dict.contains(s)) {
                mem.put(s, true);
                return true;
            }

            for (int i = 1; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                if (dict.contains(r)) {
                    if (helper(l, dict, mem, original)) {
                        mem.put(s, true);
                        return true;
                    }
                }
            }

            mem.put(s, false);

            return false;
        }
    }
}