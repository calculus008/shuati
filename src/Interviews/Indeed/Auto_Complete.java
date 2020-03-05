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
 *
 * The worst-case runtime for creating a trie is a combination of m, the length of the longest key in the trie,
 * and n, the total number of keys in the trie. Thus, the worst case runtime of creating a trie is O(mn).
 *
 * The time complexity of searching, inserting, and deleting from a trie depends on the length of the word
 * a that’s being searched for, inserted, or deleted, and the number of total words, n, making the runtime
 * of these operations O(an).
 *
 * If the memory footprint of a single node is K references, and the trie has N nodes, then obviously its space
 * complexity is O(N*K). This accounts for the fact that null pointers do occupy their space. Actually whether
 * an array entry is null or any other value doesn't change anything in terms of memory consumption.
 *
 * O(K^L) is a completely different measure because it uses different parameters. Basically K^L is the estimate
 * on the number of nodes in a densely populated trie, whereas in O(N*K) the number of nodes is explicitly given.
 *
 * https://zhu45.org/posts/2018/Jun/02/trie/
 *
 * Tries are more space efficient when they contain a large number of short keys, because nodes are shared
 * between keys with common initial subsequences.
 *
 * Tries tend to be faster on average at insertion than hash tables because hash tables must rebuild their
 * index when it becomes full – a very expensive operation. Tries therefore have much better bounded worst
 * case time costs, which is important for latency sensitive programs.
 *
 * Tries facilitate longest-prefix matching, but hashing does not, as a consequence of the above. Performing
 * such a “closest fit” find can, depending on implementation, be as quick as an exact find.
 *
 * By avoiding the hash function, tries are generally faster than hash tables for small keys like integers
 * and pointers.
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

    public List<String> findByDFS(String prefix){
        List<String> res = new ArrayList<>();
        if (prefix == null || prefix.isEmpty()) return res;

        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (cur.children[idx] == null) {
                return res;
            }

            cur = cur.children[idx];
        }

        helper(res, cur, prefix);
        return res;
    }

    public void helper(List<String> res, TrieNode pRoot, String curS){
        if (pRoot == null){
            return;
        }

        if (pRoot.isWord) {
            res.add(curS);//!!!
        }

        String tempS = curS;
        for (int i = 0; i < 26; i++){
            char c = (char)('a' + i); //!!!
            helper(res, pRoot.children[i], tempS + c);
        }
    }
}

/**
 * Version that uses HashMap as children
 */
class TrieNode1 {
    Map<Character, TrieNode1> children;
    List<String> startWith;
    boolean isWord;

    public TrieNode1() {
        children = new HashMap<>();
        startWith = new ArrayList<>();
        isWord = false;
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

        cur.isWord = true;
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

    public List<String> findByDFS(String prefix) {
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

        helper(cur, prefix, res);
        return res;
    }

    private void helper(TrieNode1 cur, String prefix, List<String> res) {
        if (cur == null) return;

        if (cur.isWord) {
            res.add(prefix);
        }

        for (char c : cur.children.keySet()) {
            helper(cur.children.get(c), prefix + c, res);
        }
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
