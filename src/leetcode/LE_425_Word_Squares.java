package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Use HashMap to get the list of words that share the given common prefix.
     *
     * 53 ms, 75%
     **
     * 较为简短的DFS算法，不需要用到Trie。
     * 先将所有的prefixes存到Map中，然后在DFS中根据prefix来查询下一个可以被放在square中的词。
     * 当square中包含的词数目达到正确的size时，加入进res中
     *
     * 整体思路是：
     * 从给的单词列表中依次拿出一个单词来，作为word squares的头单词，然后根据当前的单词，
     * 找到word squares中下一个单词的前缀， 然后再找到相应的单词，分别进行尝试， 依次尝试下去所以相当于有两类尝试 :
     *
     *   第一部分的尝试是在给的单词列表中，挨个找到word square打头的单词，即以当前单词打头；
     *   第二部分的尝试是，基于已经选定的打头单词，接下来word square中应该放哪些单词才能使整个结果构成合法的word square.
     *
     * 这就是为什么有两个dfs出现。每一种尝试，就是一次dfs.每次对应的dfs,都需要backtracking把错误的尝试删除掉，再继续进行新一轮的尝试。
     * 在一开始选定了第一个打头单词之后，调用Dfs的时候，temp.size()值是1， 而不是0.
     * Trie和map都可以存储前缀和单词的关系， 哪个写起来顺手就写哪个。hashmap的版本相对写起来简单轻巧。
     *
     * Time  : O(n!) ???
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
     * Call findByPrefix() in Trie class to get list of of words that share the given common prefix
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

    /**
     * 46 ms, 81.42%
     */
    class Solution_JiuZhang {
        void initPrefix(String[] words, Map<String, List<String>> hash) {
            for (String item : words) {
                hash.putIfAbsent("", new ArrayList<>());
                hash.get("").add(item);

                String prefix = "";
                for (char c : item.toCharArray()) {
                    prefix += c;
                    hash.putIfAbsent(prefix, new ArrayList<>());
                    hash.get(prefix).add(item);
                }
            }
        }

        boolean checkPrefix(int l, String nextWord, int wordLen, Map<String, List<String>> hash, List<String> squares) {
            for (int j = l + 1; j < wordLen; j++) {
                String prefix = "";
                for (int k = 0; k < l; k++) {
                    prefix += squares.get(k).charAt(j);
                }
                prefix += nextWord.charAt(j);
                if (!hash.containsKey(prefix)) {
                    return false;
                }
            }
            return true;
        }

        void dfs(int l, int wordLen, Map<String, List<String>> hash, List<String> squares, List<List<String>> ans) {
            if (l == wordLen) {
                ans.add(new ArrayList<>(squares));
                return;
            }
            String prefix = "";
            for (int i = 0; i < l; i++) {
                prefix += squares.get(i).charAt(l);
            }

            for (String item : hash.get(prefix)) {
                if (!checkPrefix(l, item, wordLen, hash, squares)) {
                    continue;
                }
                squares.add(item);
                dfs(l + 1, wordLen, hash, squares, ans);
                squares.remove(squares.size() - 1);
            }
        }

        public List<List<String>> wordSquares(String[] words) {
            // Write your code here
            List<List<String>> ans = new ArrayList<>();
            if (words.length == 0) {
                return ans;
            }
            Map<String, List<String>> hash = new HashMap<>();
            initPrefix(words, hash);

            List<String> squares = new ArrayList<>();
            dfs(0, words[0].length(), hash, squares, ans);
            return ans;
        }

    }
}
