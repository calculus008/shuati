package src.Interviews.Servicenow;

import java.util.ArrayList;
import java.util.List;

public class MergeList {
    /**
     * Given two lists of nodes, merge them, end result :
     *
     * In given list, nodeId is sorted with duplicates.
     *
     * return:
     * All node has the same values should be sumed together.
     * Node Id is unique.
     *
     * example:
     *
     * l1
     * id  :  1 1 1 4 5 7 7
     * val :  2 3 4 5 1 1 1
     *
     * l2
     * id  :  1 3 4 4 6 7
     * val :  1 1 2 2 1 1
     *
     * res
     * id  :  1  3  4  5  6  7
     * val :  10 1  9  1  1  3
     *
     */

    /**
     * Similar problem
     * LE_809_Expressive_Words
     * LE_844_Backspace_String_Compare
     */
    class Node {
        int id;
        int val;

        public Node(int id, int val) {
            this.id = id;
            this.val = val;
        }
    }

    private List<Node> isMatch(List<Node> l1, List<Node> l2) {
        List<Node> res = new ArrayList<>();

        if (l1 == null && l2 == null) return res;

        int i = 0, j = 0;
        while (i < l1.size() && j < l2.size()) {
            if (l1.get(i).id == l2.get(j).id) {
                int len1 = getLen(l1, i);
                int len2 = getLen(l2, j);

                Node cur = l1.get(i);
                cur.val = l2.get(j).val;
                res.add(cur);

                i += len1;
                j += len2;
            } else if (l1.get(i).id < l2.get(j).id) {
                res.add(l1.get(i));
                i++;
            } else {
                res.add(l2.get(j));
                j++;
            }
        }

        while (i == l1.size() && j < l2.size()) {
            int k = getLen(l1, i);
            res.add(l1.get(i));
            i += k;
        }

        while (j == l2.size() && i < l1.size()) {
            int x = getLen(l2, j);
            res.add(l1.get(j));
            j += x;
        }

        return res;
    }

    private int getLen(List<Node> l, int start) {
        int idx = start;
        Node n = l.get(start);
        while (idx < l.size() && l.get(start).id == l.get(idx).id) {
            if (idx != start) {
                n.val += l.get(idx).val;
            }
            idx++;
        }

        return idx - start;
    }
}
