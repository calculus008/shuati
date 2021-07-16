package leetcode;

public class LE_1032_Stream_Of_Characters {
    /**
     * Implement the StreamChecker class as follows:
     *
     * StreamChecker(words): Constructor, init the data structure with the given words.
     * query(letter): returns true if and only if for some k >= 1, the last k characters queried (in order from oldest
     * to newest, including this letter just queried) spell one of the words in the given list.
     *
     * Example:
     * Consider the following words input:
     * ["baa","aa","aaaa","abbbb","aba"]
     *
     * With the following queries input:
     * ['a'],['a'],['a'],['b'],['a'],['b'],['a']
     *
     * The expected output is:
     * false,true,true,false,true,false,true
     *
     * Let's focus on the reason behind each true result:
     *
     * The first true is expected because the first query and the second query spell the given word aa.
     * The second true is expected because the second query and the third query spell the given word aa.
     * The third true is expected because the 3rd, 4th and 5th queries spell the given word aba.
     * The fourth true is expected because the 5th, 6th and 7th queries spell the given word aba.
     *
     * Note:
     * 1 <= words.length <= 2000
     * 1 <= words[i].length <= 2000
     * Words will only consist of lowercase English letters.
     * Queries will only consist of lowercase English letters.
     * The number of queries is at most 40000.
     *
     * Hard
     */

    /**
     * Trie Solution
     *
     * Important Trick:
     * Store the words in the trie with reverse order, and check the query string from the end !!!
     */
    class StreamChecker {
        class TrieNode {
            boolean isWord;
            TrieNode[] next;

            public TrieNode() {
                isWord = false;
                next = new TrieNode[26];
            }
        }

        TrieNode root;
        StringBuilder sb;

        private void createTrie(String[] words) {
            root = new TrieNode();
            sb = new StringBuilder();

            for (String word : words) {
                int n = word.length();
                TrieNode cur = root;

                /**
                 * !!!
                 * Store backword : i = n - 1
                 */
                for (int i = n - 1; i >= 0; i--) {
                    char c = word.charAt(i);
                    int idx = c - 'a';
                    if (cur.next[idx] == null) {
                        cur.next[idx] = new TrieNode();
                    }
                    cur = cur.next[idx];
                }
                /**
                 * !!!
                 */
                cur.isWord = true;
            }
        }

        public StreamChecker(String[] words) {
            createTrie(words);
        }

        public boolean query(char letter) {
            /**
             * !!!
             * Easy to forget this step
             */
            sb.append(letter);

            TrieNode cur = root;

            int n = sb.length();
            /**
             * !!!
             * Search backward
             */
            for (int i = n - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                int idx = c - 'a';
                if (cur.next[idx] == null) return false;

                cur = cur.next[idx];
                if (cur.isWord) return true;
            }

            return false;
        }
    }

}
