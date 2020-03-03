package src.Interviews.Indeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question Description
 * Say I'm typing on a phone. Given a prefix String,and a dictionary.
 * Find all auto-complete word for the given prefix string
 */

/**
 * Trie.insert() : O(L), L is length of the inserted word
 */
class TrieNode {
    TrieNode[] children;
    boolean isWord;
    String word;
    List<String> startWith;

    public TrieNode() {
        children = new TrieNode[26];
        isWord = false;
        word = "";
        startWith = new ArrayList<>();
    }
}

class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String w) {
        if (w == null || w.length() == 0) return;

        TrieNode cur = root;
        for (char c : w.toCharArray()) {
            int idx = c - 'a';

            if (cur.children[idx] == null) {
                cur.children[idx] = new TrieNode();
            }

            /**
             * !!!
             * We start from root, root has no value, set startWith for the next level, move to that level
             */
            cur.children[idx].startWith.add(w);
            cur = cur.children[idx];
        }
        cur.isWord = true;
        cur.word = w;
    }



    /**
     * LE_425_Word_Squares
     */
    public List<String> findByPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) return res;

        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (cur.children[idx] == null) {
                return res;
            }

            cur = cur.children[idx];
        }

        res.addAll(cur.startWith);

        return res;
    }
}

/**
 * Version that uses HashMap as children
 */
class TrieNode1 {
    Map<Character, TrieNode1> children;
    List<String> startWith;

    public TrieNode1() {
        children = new HashMap<>();
        startWith = new ArrayList<>();
    }
}

class Trie1 {
    TrieNode1 root;

    public Trie1() {
        root = new TrieNode1();
    }

    public void insert(String s) {
        if (s == null || s.length() == 0) return;

        TrieNode1 cur = root;
        for (char c : s.toCharArray()) {
            TrieNode1 next = cur.children.get(c);

            if (next == null) {
                next = new TrieNode1();
                cur.children.put(c, next);
            }

            cur = next;
            cur.startWith.add(s);
        }
    }

    public List<String> findByPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) return res;

        TrieNode1 cur = root;
        for (char c : prefix.toCharArray()) {
            TrieNode1 next = cur.children.get(c);
            if (next == null) {
                return res;
            }

            cur = next;
        }

        res.addAll(cur.startWith);

        return res;
    }
}


public class Auto_Complete {
    Trie trie;

    /**
     * O(N * l) : N is total number of words, l is the average length of the word, upper bound is 26 ^ l.
     */
    public Auto_Complete(String[] words) {
        trie = new Trie();

        if (words == null || words.length == 0) return;

        for (String w : words) {
            trie.insert(w);
        }
    }


    public List<String> find(String prefix) {
        return trie.findByPrefix(prefix);
    }
}
