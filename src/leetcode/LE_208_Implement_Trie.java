package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_208_Implement_Trie {
    /*
        Implement a trie (Prefix Tree) with insert, search, and startsWith methods.

        Note:
        You may assume that all inputs are consist of lowercase letters a-z.
     */

    //A simplified version of LE_208
    private TrieNode root;

    /** Initialize your data structure here. */
    public LE_208_Implement_Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
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

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        if (word == null || word.length() == 0) return false;

        TrieNode temp = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (temp.children[idx] == null) {
                return false;
            }
            temp = temp.children[idx];
        }
        return temp.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.length() == 0) return false;

        TrieNode temp = root;
        for (int i = 0; i < prefix.length(); i++) {
            int idx = prefix.charAt(i) - 'a';
            if (temp.children[idx] == null) {
                return false;
            }
            temp = temp.children[idx];
        }
        return true;
    }
}
