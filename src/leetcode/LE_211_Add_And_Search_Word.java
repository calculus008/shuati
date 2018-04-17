package leetcode;

/**
 * Created by yuank on 3/27/18.
 */
public class LE_211_Add_And_Search_Word {
    /*
        Design a data structure that supports the following two operations:

        void addWord(word)
        bool search(word)
        search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

        For example:

        addWord("bad")
        addWord("dad")
        addWord("mad")
        search("pad") -> false
        search("bad") -> true
        search(".ad") -> true
        search("b..") -> true
        Note:
        You may assume that all words are consist of lowercase letters a-z.
     */

    private TrieNode root;

    /** Initialize your data structure here. */
    public LE_211_Add_And_Search_Word() {
        //注意!!!, 不是 “leetcode.TrieNode root = new leetcode.TrieNode();"
        root = new TrieNode();
    }

    /** Adds a word into the data structure. */
    //Time : O(n), n is word length
    //Space : O(Number of Words * word length * 26)
    public void addWord(String word) {
        if (word == null || word.length() == 0) return;

        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (cur.children[idx] == null) {
                cur.children[idx] = new TrieNode();
            }
            cur = cur.children[idx];
        }
        cur.isWord = true;
        cur.word = word;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        if (word == null || word.length() == 0) return false;

        return helper(word, 0, root);
    }

    //DFS helper
    private boolean helper(String word, int start, TrieNode cur) {
        if (start == word.length()) return cur.isWord; //or cur.word.equals(word)

        if (word.charAt(start) == '.') {//因为'.'可以代表任何char,所以需要在下一层对所有字母遍历。
            // for (int i = 0; i < 26; i++) {
            for (TrieNode child : cur.children) {
                if (child != null && helper(word, start + 1, child)) {
                    return true;
                }
            }
            //要在这里返回false, for loop执行万后仍然没有返回，必定为false.
            return false;
        } else {
            int idx = word.charAt(start) - 'a';
            TrieNode temp = cur.children[idx];
            return temp != null && helper(word, start + 1, temp);
        }
    }
}
