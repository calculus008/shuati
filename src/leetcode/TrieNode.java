package leetcode;

/**
 * Created by yuank on 3/27/18.
 */
class TrieNode {
    TrieNode[] children;
    boolean isWord;
    String word;

    public TrieNode() {
        children = new TrieNode[26];
        isWord = false;
        word = "";
    }
}
