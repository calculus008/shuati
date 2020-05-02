package leetcode;

import java.util.*;

public class LE_1268_Search_Suggestions_System {
    /**
     * Given an array of strings products and a string searchWord. We want to design
     * a system that suggests at most three product names from products after each
     * character of searchWord is typed. Suggested products should have common prefix
     * with the searchWord. If there are more than three products with a common prefix
     * return the three lexicographically minimums products.
     *
     * Return list of lists of the suggested products after each character of searchWord
     * is typed.
     *
     * Example 1:
     * Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
     * Output: [
     * ["mobile","moneypot","monitor"],
     * ["mobile","moneypot","monitor"],
     * ["mouse","mousepad"],
     * ["mouse","mousepad"],
     * ["mouse","mousepad"]
     * ]
     * Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
     * After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
     * After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
     *
     * Example 2:
     * Input: products = ["havana"], searchWord = "havana"
     * Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
     *
     * Example 3:
     * Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
     * Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
     *
     * Example 4:
     * Input: products = ["havana"], searchWord = "tatiana"
     * Output: [[],[],[],[],[],[],[]]
     *
     * Constraints:
     * 1 <= products.length <= 1000
     * There are no repeated elements in products.
     * 1 <= Σ products[i].length <= 2 * 10^4
     * All characters of products[i] are lower-case English letters.
     * 1 <= searchWord.length <= 1000
     * All characters of searchWord are lower-case English letters.
     *
     * Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/algorithms/binary-search/leetcode-1268-search-suggestions-system/
     *
     * Binary Search
     *
     * Intuition
     * In a sorted list of words, for any word A[i],
     * all its suggested words must following this word in the list.
     *
     * For example, if A[i] is a prefix of A[j],
     * A[i] must be the prefix of A[i + 1], A[i + 2], ..., A[j]
     *
     * Explanation
     * With this observation,
     * we can binary search the position of each prefix of search word,
     * and check if the next 3 words is a valid suggestion.
     *
     *
     * Complexity
     * Init : O(nlogn)
     * Query : O(len(searchWord)logn)
     *
     * Query is more expensive then TrieNode solution. Since we only need to search one searchWord, so
     * this solution avoids the high cost of init for TrieNode (we just need to do a sort here), so
     * even query is more expensive, it works faster overall.
     *
     * 10ms, 95%
     */

    /**
     * Binary Search (using Arrays.binarySearch())
     */
    class Solution {
        public List<List<String>> suggestedProducts(String[] products, String searchWord) {
            List<List<String>> res = new ArrayList<>();
            if (searchWord == null) return res;

            Arrays.sort(products);

            for (int i = 1; i <= searchWord.length(); i++) {
                String key = searchWord.substring(0, i);
                int index = Arrays.binarySearch(products, key);

                /**
                 * !!!
                 */
                if (index < 0) {
                    index = -index - 1;
                }

                List<String> list = new ArrayList<>();

                /**
                 * !!!
                 * 1.for loop condition : check 3 words that are within the total length boundary
                 * 2.Must check if the word has current prefix.
                 * 3.If not, must break.
                 *
                 * We do it this way so we don't have to use the logic of checking floor boundary.
                 */
                for (int j = 0; j < 3 && index + j < products.length; j++) {
                    if (products[index + j].startsWith(key)) {
                        list.add(products[index + j]);
                    } else {
                        break;
                    }
                }

                res.add(list);
            }

            return res;
        }
    }

    /**
     * Binary Search using TreeMap
     */
    class Solution2 {
        public List<List<String>> suggestedProducts(String[] products, String searchWord) {
            List<List<String>> res = new ArrayList<>();
            if (searchWord == null) return res;

            /**
             * Need to sort input strings and put them in a list,
             * so that we can get sublist from it based on binary search
             * result.
             */
            Arrays.sort(products);
            List<String> productList = Arrays.asList(products);

            /**
             * binary search using TreeMap
             */
            TreeMap<String, Integer> map = new TreeMap<>();

            for (int i = 0; i < products.length; i++) {
                map.put(products[i], i);
            }

            String key = "";
            for (char c : searchWord.toCharArray()) {
                key += c;
                String ceiling = map.ceilingKey(key);

                /**
                 * Find upper bound
                 *
                 * prefix + "~" helps you find the upper bound, '~' is one option,
                 * any character after 'z' should be also working such as '{'.
                 */
                String floor = map.floorKey(key + "~");

                if (ceiling == null || floor == null) break;

                /**
                 * subList is similar to substring, right side is not inclusive.
                 *
                 * Example:
                 * ["bags","baggage","banner","box","cloths"]
                 *
                 * when prefix is "bag", ceiling key is "bags",
                 * floor key of "bag~" is "baggage", therefore,
                 * sublist if between index 0 and min(0 + 3, 1 + 1) = 2,
                 * [0, 2) -> ["bags", "baggage"]
                 *
                 * when prefix is "bags", ceiling key is "bags",
                 * floor key of "bags~" is "bags",
                 * [0, 1) -> ["bags"]
                 */
                res.add(productList.subList(map.get(ceiling),
                        Math.min(map.get(ceiling) + 3, map.get(floor) + 1)));
            }

            while (res.size() < searchWord.length()) {
                res.add(new ArrayList<>());
            }

            return res;
        }
    }

    /**
     * TrieNode : save matches words at each TrieNode, takes more space.
     *
     * Init : O(sum(len(products[i]))
     * Query : O(len(searchWord))
     *
     * Since we only have one searchWord, so it seems that init stage is a little expensive.
     * If we have more than 1 searchWord, TrieNode solution is better since we only do one init operation.
     *
     * Also, since we save list of matched words in each TrieNode, we spend more on memory to gain time
     * on search.
     *
     * 13ms 85%
     */
    class Solution_Trie_1{
        //trie node
        class TrieNode {
            TrieNode[] next;
            List<String> words;

            public TrieNode() {
                words = new ArrayList();
                next = new TrieNode[26];
            }
        }

        public List<List<String>> suggestedProducts(String[] products, String searchWord) {
            //sort words so they will be added in a sorted order to nodes
            Arrays.sort(products);

            /**
             * init TrieNode
             */
            TrieNode root = new TrieNode();

            for (String prod : products) {
                TrieNode cur = root;
                for (char ch : prod.toCharArray()) {
                    int i = ch - 'a';
                    if (cur.next[i] == null) {
                        cur.next[i] = new TrieNode();
                    }
                    cur = cur.next[i];

                    if (cur.words.size() < 3) {
                        cur.words.add(prod);
                    }
                }
            }

            /**
             * Query
             */
            List<List<String>> res = new ArrayList();
            TrieNode cur = root;

            for (char c : searchWord.toCharArray()) {
                cur = cur.next[c - 'a'];
                if (cur == null) break;
                res.add(cur.words);
            }

            while (res.size() < searchWord.length()) {
                res.add(new ArrayList<>());
            }

            return res;
        }
    }

    /**
     * Difference from Solution_Trie_1 :
     * We don't save list matched words in each TrieNode, so we need to do DFS
     * for search. Use less memory, but query performance is poor.
     *
     * If required by interviewer to use DFS, should be able to come up with this solution.
     *
     * findByDFS() and helper() are copied from Auto_Complete
     *
     */
    class Solution_Trie_2 {
        class TrieNode {
            TrieNode[] next;
            boolean isWord;

            public TrieNode() {
                next = new TrieNode[26];
                isWord = false;
            }
        }

        TrieNode root;

        public List<List<String>> suggestedProducts(String[] products, String searchWord) {
            Arrays.sort(products);

            root = new TrieNode();

            for (String prod : products) {
                TrieNode cur = root;
                for (char ch : prod.toCharArray()) {
                    int i = ch - 'a';
                    if (cur.next[i] == null) {
                        cur.next[i] = new TrieNode();
                    }
                    cur = cur.next[i];
                }
                cur.isWord = true;
            }

            List<List<String>> res = new ArrayList();
            TrieNode cur = root;

            String prefix ="";
            for (char c : searchWord.toCharArray()) {
                prefix += c;
                if (cur == null) break;
                List<String> list = findByDFS(prefix);
                if (list.size() > 0) {
                    res.add(list);
                }
            }

            while (res.size() < searchWord.length()) {
                res.add(new ArrayList<>());
            }

            return res;
        }

        /**
         * !!!
         * Given prefix, DFS search matching words.
         * 1.First, go level by level to the last char of the prefix String.
         * 2.Then recursively go deeper into Trie to find all words (isWord is TRUE)
         */
        public List<String> findByDFS(String prefix){
            List<String> res = new ArrayList<>();
            if (prefix == null || prefix.isEmpty()) return res;

            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (cur.next[idx] == null) {
                    return res;
                }

                cur = cur.next[idx];
            }

            helper(res, cur, prefix);
            return res;
        }

        public void helper(List<String> res, TrieNode pRoot, String curS){
            if (pRoot == null){
                return;
            }

            if (pRoot.isWord && res.size() < 3) {//"res.size() < 3"!!!
                res.add(curS);//!!!
            }

            String tempS = curS;
            for (int i = 0; i < 26; i++){
                char c = (char)('a' + i); //!!!
                helper(res, pRoot.next[i], tempS + c);
            }
        }
    }

}
