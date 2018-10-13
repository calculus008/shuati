package lintcode;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by yuank on 10/13/18.
 */
public class LI_559_Trie_Service {
    /**
         Build tries from a list of <word, freq> pairs. Save top 10 for each node.

         Example
         Given a list of <word, freq>

         <"abc", 2>
         <"ac", 4>
         <"ab", 9>
         Return <a[9,4,2]<b[9,2]<c[2]<>>c[4]<>>>, and denote the following tree structure:

             Root
              /
           a(9,4,2)
            /    \
         b(9,2) c(4)
          /
         c(2)

         Medium
     */


      public class TrieNode {
          public NavigableMap<Character, TrieNode> children;
          public List<Integer> top10;
          public TrieNode() {
              children = new TreeMap<Character, TrieNode>();
              top10 = new ArrayList<Integer>();
          }
      }

    public class TrieService {

        private TrieNode root = null;

        public TrieService() {
            root = new TrieNode();
        }

        public TrieNode getRoot() {
            // Return root of trie root, and
            // lintcode will print the tree struct.
            return root;
        }

        // @param word a string
        // @param frequency an integer
        public void insert(String word, int frequency) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (!cur.children.containsKey(c)) {
                    cur.children.put(c, new TrieNode());
                }

                cur = cur.children.get(c);
                add(cur, frequency);
            }
        }

        private void add(TrieNode cur, int frequency) {
            List<Integer> list = cur.top10;
            list.add(frequency);

            /**
             * check from tail to head, swap if out of order
             **/
            int n = list.size();
            int idx = n - 1;
            while (idx > 0) {
                if (list.get(idx) > list.get(idx - 1)) {
                    int temp = list.get(idx);
                    list.set(idx, list.get(idx - 1));
                    list.set(idx - 1, temp);
                    idx--;
                } else {
                    /**
                     Each time just insert one element in add(), once it is in place,
                     no need to continue the while loop.
                     !!! Without this break, will TLE
                     **/
                    break;
                }
            }

            //now list is sorted, remove the one at the tail if there's more than 10 elements
            if (n > 10) {
                list.remove(n - 1);
            }
        }
    }
}
