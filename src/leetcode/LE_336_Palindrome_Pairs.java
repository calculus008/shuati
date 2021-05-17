package leetcode;

import java.util.*;

/**
 * Created by yuank on 5/19/18.
 */
public class LE_336_Palindrome_Pairs {
    /**
         Given a list of unique words, find all pairs of distinct indices (i, j) in the given list,
         so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

         Example 1:
         Given words = ["bat", "tab", "cat"]
         Return [[0, 1], [1, 0]]
         The palindromes are ["battab", "tabbat"]

         Example 2:
         Given words = ["abcd", "dcba", "lls", "s", "sssll"]
         Return [[0, 1], [1, 0], [3, 2], [2, 4]]
         The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]

         Hard
     */

    /**
         Time  : O(n * (m ^ 2)), n -> length of words[i], m -> length of each word
         Space : O(k), k - number of pairs ???
     **/
    class Solution1 {
        public List<List<Integer>> palindromePairs(String[] words) {
            List<List<Integer>> res = new ArrayList<>();
            if (words == null || words.length == 0) return res;

            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                map.put(words[i], i);
            }

            for (int i = 0; i < words.length; i++) {
                /**
                 !!!
                 "j <= words[i].length()", here "<=" is to deal with case that words[i] is "", it is a valid palindrome,
                 we need to go into the if logics below to process. Say words : ["", "abba"], then {0, 1} and {1, 0} are
                 valid answers. When we process "abba", we need to check the case j = 4, str2 = "" -> {1, 0}
                 **/
                for (int j = 0; j <= words[i].length(); j++) {
                    String str1 = words[i].substring(0, j);
                    String str2 = words[i].substring(j);

                    /**
                     Case 1 :
                     words[i] - "aabccde" - "aabcc" is palindrome, find if "ed" is in words, if yes, add to the front of words[i],
                     form new palindrome string "edaabccde"
                     **/
                    if (isPalindrome(str1)) {
                        String target1 = (new StringBuilder(str2)).reverse().toString();
                        /**
                         "dist.get(target1) != i" : exclude the case that self concatenation, for example:
                         Given ["abcd","dcba","lls","s","sssll"], if we don't have this check, we will get {3, 3} in answer ("ss")
                         **/
                        if (map.containsKey(target1) && map.get(target1) != i) {
                            List<Integer> list1 = new ArrayList<>();
                            list1.add(map.get(target1)); //!!!in front of words[i]
                            list1.add(i);
                            res.add(list1);
                        }
                    }

                    /**
                     Case 2 :
                     words[i] - "deacbca" - "acbca" is palindrome, find if "ed" is in words, if yes, append at the endof words[i],
                     form new palindrome string "edacbcade"
                     **/
                    if (isPalindrome(str2)) {
                        String target2 = (new StringBuilder(str1)).reverse().toString();
                        /**
                         !!!
                         "str2.length() != 0" : remove duplication.
                         Given ["abcd","dcba","lls","s","sssll"] :
                         For "abcd"
                         a1   -> str1 = "", str2 = "abcd", find target1 "dcba", {1, 0}
                         a2   -> str1 = "abcd", str2 = "", find target2 "dcba", {0, 1}

                         When we come to "dcba"
                         b1  -> str1 = "", str2 = "dcba", find target1 "abcd", {0, 1}
                         b2  -> str1 = "dcba", str2 = "", find target2 "abcd", {1, 0}

                         Now we have duplication.

                         Add "str2.length() != 0", we remove case a2 and b2, then we remove the possible duplication
                         **/
                        if (map.containsKey(target2) && map.get(target2) != i && str2.length() != 0) {
                            List<Integer> list2 = new ArrayList<>();
                            list2.add(i);
                            list2.add(map.get(target2)); //!!!append at the end of words[i]
                            res.add(list2);
                        }
                    }
                }
            }

            return res;
        }

        private boolean isPalindrome(String s) {
            if (null == s) return false;//!!!
            if (s.equals("")) return true;//!!!

            int l = 0;
            int r = s.length() - 1;
            while (l < r) {
                if (s.charAt(l) != s.charAt(r)) {
                    return false;
                }
                l++;
                r--;
            }

            return true;
        }
    }

    public class Solution_Practice {
        public List<List<Integer>> palindromePairs(String[] words) {
            List<List<Integer>> res = new ArrayList<>();
            if (words == null || words.length == 0) return res;

            Map<String, Integer> map = new HashMap<>();

            for (int i = 0; i < words.length; i++) {
                map.put(words[i], i);
            }

            for (int i = 0; i < words.length; i++) {
                for (int j = 0; j <= words[i].length(); j++) {
                    String str1 = words[i].substring(0, j);
                    String str2 = words[i].substring(j);

                    if (isPalindrome(str1)) {
                        String prefix = (new StringBuilder(str2)).reverse().toString();

                        if (map.containsKey(prefix) && map.get(prefix) != i) {
                            res.add(Arrays.asList(map.get(prefix), i));
                        }
                    }

                    if (isPalindrome(str2)) {
                        String postfix = (new StringBuilder(str1)).reverse().toString();

                        if (map.containsKey(postfix) && map.get(postfix) != i && str2.length() != 0) {
                            res.add(Arrays.asList(i, map.get(postfix)));
                        }
                    }
                }
            }

            return res;
        }

        private boolean isPalindrome(String s) {
            if (s == null) return false;
            if (s.equals("")) return true;

            int l = 0;
            int r = s.length() - 1;
            char[] chars = s.toCharArray();

            while (l < r) {
                if (chars[l] != chars[r]) return false;
                l++;
                r--;
            }

            return true;
        }
    }

    /**
     * TrieNode solution
     *
     * https://leetcode.com/problems/palindrome-pairs/discuss/79195/O(n-*-k2)-java-solution-with-TrieNode-structure
     */
    class Solution2 {
        private class TrieNode {
            TrieNode[] next;
            int index;
            List<Integer> list;

            TrieNode() {
                next = new TrieNode[26];
                index = -1;
                list = new ArrayList<>();
            }
        }

        public List<List<Integer>> palindromePairs(String[] words) {
            List<List<Integer>> res = new ArrayList<>();

            TrieNode root = new TrieNode();

            for (int i = 0; i < words.length; i++) {
                addWord(root, words[i], i);
            }

            for (int i = 0; i < words.length; i++) {
                search(words, i, root, res);
            }

            return res;
        }

        private void addWord(TrieNode root, String word, int index) {
            for (int i = word.length() - 1; i >= 0; i--) {
                int j = word.charAt(i) - 'a';

                if (root.next[j] == null) {
                    root.next[j] = new TrieNode();
                }

                if (isPalindrome(word, 0, i)) {
                    root.list.add(index);
                }

                root = root.next[j];
            }

            root.list.add(index);
            root.index = index;
        }

        private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
            for (int j = 0; j < words[i].length(); j++) {
                if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
                    res.add(Arrays.asList(i, root.index));
                }

                root = root.next[words[i].charAt(j) - 'a'];
                if (root == null) return;
            }

            for (int j : root.list) {
                if (i == j) continue;
                res.add(Arrays.asList(i, j));
            }
        }

        private boolean isPalindrome(String word, int i, int j) {
            while (i < j) {
                if (word.charAt(i++) != word.charAt(j--)) return false;
            }

            return true;
        }
    }
}
