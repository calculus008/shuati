package leetcode;

import java.util.*;

public class LE_472_Concatenated_Words {
    /**
     * Given a list of words (without duplicates), please write a program
     * that returns all concatenated words in the given list of words.
     * A concatenated word is defined as a string that is comprised entirely
     * of at least two shorter words in the given array.
     *
     * Example:
     * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
     *
     * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
     *
     * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     *  "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
     * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
     *
     * The number of elements of the given array will not exceed 10,000
     * The length sum of elements in the given array will not exceed 600,000.
     * All the input string will only include lower case letters.
     * The returned elements order does not matter.
     *
     * Hard
     *
     * https://leetcode.com/problems/concatenated-words
     */

    class Solution_clean {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            Set<String> set = new HashSet<>(10000);
            List<String> ans = new ArrayList<>();
            int min = Integer.MAX_VALUE;

            for (String word : words) {
                if (word.length() == 0) continue;
                set.add(word);
                min = Math.min(min, word.length()); //use min to optimize runtime
            }

            for (String word : words) {
                if (check(set, word, 0, min)) {
                    ans.add(word);
                }
            }

            return ans;
        }

        private boolean check(Set<String> set, String word, int start, int min) {
            for (int i = start + min; i <= word.length() - min; i++) {
                if (set.contains(word.substring(start, i)) &&
                        (set.contains(word.substring(i)) || check(set, word, i, min))) {
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 24ms 99.85# solution from leetcode
     *
     * Optimized from Solution_lc_2:
     * Detect min length of the words, use it to minimize for loop times.
     */
    class Solution_lc_1 {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            Set<String> set = new HashSet<>(10000);
            List<String> ans = new ArrayList<>();
            int min = Integer.MAX_VALUE;

            for (String word : words) {
                if (word.length() == 0) {
                    continue;
                }

                set.add(word);
                min = Math.min(min, word.length());
            }

            for (String word : words) {
                if (check(set, word, 0, min)) {
                    ans.add(word);
                }
            }

            return ans;
        }

        private boolean check(Set<String> set, String word, int start, int min) {
            /**
             * !!!
             * "int i = start + min; i <= word.length() - min; i++"
             */
            for (int i = start + min; i <= word.length() - min; i++) {
                /**
                 * !!!
                 * 类似于word break, break the word into left and right.
                 * if left is contained in set
                 * AND
                 * right is also contained in set OR calling check() on right returns true
                 *
                 * we have an answer
                 */
                if (set.contains(word.substring(start, i)) &&
                        (set.contains(word.substring(i)) || check(set, word, i, min))) {
                    return true;
                }
            }

            return false;
        }
    }

    /***
     * A simplified version of word break.
     */
    class Solution_lc_2 {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            Set<String> set = new HashSet<>();
            for (String word : words) {
                set.add(word);
            }

            List<String> result = new ArrayList<>();
            for (String word : words) {
                if (dfs(word, set)) {
                    result.add(word);
                }
            }

            return result;
        }

        private boolean dfs(String word, Set<String> set) {
            for (int i = 1; i < word.length(); i++) {
                String firstHalf = word.substring(0, i);

                if (set.contains(firstHalf)) {
                    String secondHalf = word.substring(i);
                    if (set.contains(secondHalf) || dfs(secondHalf, set))
                        return true;
                }
            }
            return false;
        }
    }

    /**
     * TrieNode + DFS
     *
     * Preferred Solution
     *
     * Since we have an array of words, we can pre-process words to create a trie, which will help us
     * to save processing time later on.
     *
     * It's O(n * k) for validation as we always needs to travel the Tire once
     *
     * TrieNode vs HashMap
     * https://stackoverflow.com/questions/245878/how-do-i-choose-between-a-hash-table-and-a-trie-prefix-tree
     *
     * 49 ms
     * 70.31%
     * 49.7 MB
     *
     */
    class Solution_Trie {
        class TrieNode {
            TrieNode[] children;
            // String word;
            boolean isWord;

            public TrieNode() {
                children = new TrieNode[26];
            }
        }

        TrieNode root;

        private void addWord(String s) {
            TrieNode cur = root;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int idx = c - 'a';
                if (cur.children[idx] == null) {
                    cur.children[idx] = new TrieNode();
                }
                cur = cur.children[idx];
            }
            // cur.word = s;
            cur.isWord = true;
        }


        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> res = new ArrayList<>();
            if (words == null || words.length == 0) return res;

            //init TrieNode
            root = new TrieNode();
            for (String word : words) {
                if (word == null || word.length() == 0) {
                    continue;
                }
                addWord(word);
            }

            //check each word to see if it meets requirement
            for (String word : words) {
                if (word == null || word.length() == 0) {
                    continue;
                }

                if (helper(word.toCharArray(), 0, 0)) {
                    res.add(word);
                }
            }

            return res;
        }

        private boolean helper(char[] chars, int index, int count) {
            /**
             * every time calling on helper is for a new word, so we
             * start from root here.
             */
            TrieNode cur = root;
            int n = chars.length;

            for (int i = index; i < n; i++) {
                if (cur.children[chars[i] - 'a'] == null) {
                    return false;
                }

                /**
                 * check if substring from index "index" to "i" is a word
                 */
                if (cur.children[chars[i] - 'a'].isWord) {
                    /**
                     * if we get to the end of current word, because of the requirement -
                     * "comprised entirely of at least two shorter words", we only return
                     * TRUE if count is bigger than 1. Here count value not increased for
                     * the current newly found word, if we have a 2 words combination, here
                     * count is 1, so it's ">= 1"
                     */
                    if (i == n - 1) {
                        return count >= 1;
                    }

                    /**
                     * there are more chars in current word, recurse with index
                     * i + 1. Also count should be increased, before we know
                     * we already have a matching word with the outter if condition.
                     */
                    if (helper(chars, i + 1, count + 1)) {
                        return true;
                    }
                }

                /**
                 * !!!
                 */
                cur = cur.children[chars[i] - 'a'];
            }
            return false;
        }
    }

    /**
     * Recursion with mem
     *
     * https://leetcode.com/problems/concatenated-words/discuss/348972/Java-Common-template-Word-Break-I-Word-Break-II-Concatenated-Words
     *
     * Preferred Solution
     *
     * Exact the same algorithm as LE_139_Word_Break. "helper()" function is copied
     * from LE_139_Word_Break with no modification.
     *
     * Two ways of dealing with word itself in set:
     * 1.Pass word as original and make sure it is not compared with itself
     * 2.Remove current word from set before calling helper, then add it back
     *   to the set after helper() returns.
     *
     * Time  : O(k * n ^ 2), k is number of words, n is average length of a single word.
     * Space : O(n ^ 2)
     *
     * 88 ms
     * 58 M
     */
    class Solution_Recursion_With_Mem {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> list = new ArrayList<String>();
            Set<String> set = new HashSet(Arrays.asList(words));

            for(String word : words) {
                if (word == null || word.length() == 0) {
                    continue;
                }

                // set.remove(word);

                if (helper(word, set, new HashMap<>(), word)) {
                    list.add(word);
                }

                // set.add(word);
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

    class Solution1_1 {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {

            //sort the array in asc order of word length, since longer words are formed by shorter words.
            Arrays.sort(words, (a, b) -> a.length() - b.length());
            List<String> result = new ArrayList<>();

            //list of shorter words
            HashSet<String> preWords = new HashSet<>();

            for (int i = 0; i < words.length; i++) {
                //Word Break-I problem.
                if (wordBreak(words[i], preWords)) result.add(words[i]);
                preWords.add(words[i]);
            }
            return result;
        }

        private boolean wordBreak(String s, HashSet<String> preWords) {
            if (preWords.isEmpty()) return false;

            boolean[] dp = new boolean[s.length() + 1];
            dp[0] = true;

            for (int i = 1; i <= s.length(); i++) {
                for (int j = 0; j < i; j++) {
                    if (dp[j] && preWords.contains(s.substring(j, i))) {
                        dp[i] = true;
                        break;
                    }
                }
            }
            return dp[s.length()];
        }
    }

    /**
     * DP, same logic as DP solution for LE_139_Word_Break
     */
    class Solution2 {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> list = new ArrayList<String>();
            Set<String> set = new HashSet(Arrays.asList(words));

            int maxLen = getMaxLen(set);

            for(String word : words) {
                set.remove(word);

                if (wordBreak(word, set, maxLen)) {
                    list.add(word);
                }

                set.add(word);
            }
            return list;
        }

        public boolean wordBreak(String s, Set<String> dict, int maxLen) {
            if (s == null || s.length() == 0) {
                return false;
            }

            int max = 0;
            if (s.length() == maxLen) {
                max = getMaxLen(dict);
            } else {
                max = maxLen;
            }

            int n = s.length();
            boolean[] dp = new boolean[n + 1];
            dp[0] = true;


            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= max && j <= i; j++) {
                    if (dp[i - j] && dict.contains(s.substring(i - j, i))) {
                        dp[i] = true;
                        break;
                    }
                }
            }

            return dp[n];
        }

        private int getMaxLen(Set<String> dict) {
            int max = 0;
            for (String word : dict) {
                max = Math.max(max, word.length());
            }
            return max;
        }
    }
}
