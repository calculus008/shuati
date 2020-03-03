package src.leetcode;

import java.util.*;

public class LE_642_Design_Search_Autocomplete_System_Mine {
    class AutocompleteSystem {
        class Pair {
            String s;
            int count;

            public Pair(String s, int count) {
                this.s = s;
                this.count = count;
            }
        }

        class TrieNode {
            /**
             * Since in a sentence we have more than just 26 alphabets, we need to use a HashMap
             * to record next level chilrden
             */
            Map<Character, TrieNode> children;

            /**
             * key : sentence
             * val : number of time this sentence that has appeared
             *
             * This HashMap also functions as "startWith" list in Auto_Commit, it saves the list of
             * sentences that have appeared.
             */
            Map<String, Integer> count;

            public TrieNode() {
                children = new HashMap<>();
                count = new HashMap<>();
            }
        }

        class Trie {
            TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

            public void insert(String s, int count) {
                if (s == null || s.length() == 0) return;

                TrieNode cur = root;
                for (char c : s.toCharArray()) {
                    TrieNode next = cur.children.get(c);
                    if (next == null) {
                        next = new TrieNode();
                        cur.children.put(c, next);
                    }
                    /**
                     * !!!
                     * We start from root, root has no value, so move to the node of next char,
                     * then set startWith list at that level
                     * !!!
                     */
                    cur = next;

                    cur.count.put(s, cur.count.getOrDefault(s, 0) + count);
                }
            }

            public List<Pair> searchByPrefix(String prefix) {
                List<Pair> res = new ArrayList<>();
                if (prefix == null || prefix.length() == 0) return res;

                TrieNode cur = root;
                for (char c : prefix.toCharArray()) {
                    TrieNode next = cur.children.get(c);
                    if (next == null) {
                        return res;
                    }

                    cur = next;
                }

                for (String key : cur.count.keySet()) {
                    res.add(new Pair(key, cur.count.get(key)));
                }

                return res;
            }
        }

        String prefix;
        Trie trie;

        public AutocompleteSystem(String[] sentences, int[] times) {
            prefix = "";
            trie = new Trie();

            int n = sentences.length;
            for (int i = 0; i < n; i++) {
                trie.insert(sentences[i], times[i]);
            }
        }

        public List<String> input(char c) {
            /**
             *
             */
            if (c == '#') {
                /**
                 * "the previously typed sentence should be recorded in your system."
                 */
                trie.insert(prefix, 1);
                prefix = "";
                return new ArrayList<String>();
            }

            prefix += c;
            List<Pair> list = trie.searchByPrefix(prefix);

            /**
             * !!!
             * "new PriorityQueue<>(...)", Don't forget "<>"!!!
             *
             * Compareator:
             * "If several sentences have the same degree of hot, you need to use ASCII-code order (smaller one appears first)."
             */
            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.count == b.count ? a.s.compareTo(b.s) : b.count - a.count);

            for (Pair p : list) {
                pq.offer(p);
            }

            List<String> res = new ArrayList<>();

            /**
             * "If less than 3 hot sentences exist, then just return as many as you can."
             */
            for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
                res.add(pq.poll().s);
            }

            return res;
        }
    }
}
