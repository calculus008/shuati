package Interviews.Amazon;

import java.util.*;

public class K_Way_Merge_Iterator {
    static class Node {
        Iterator<Integer> it;
        Integer val;

        public Node(Iterator<Integer> it, Integer val) {
            this.it = it;
            this.val = val;
        }
    }

    static class KWayIterator {
        static Integer next;
        static PriorityQueue<Node> pq;

        public KWayIterator(List<Iterator<Integer>> its) {
            pq = new PriorityQueue<>((a, b) -> a.val - b.val);

            for (Iterator<Integer> it : its) {
                if (it == null || !it.hasNext()) continue;
                pq.offer(new Node(it, it.next()));
            }

            next = null;
        }

        public boolean hasNext() {
            if (pq == null || pq.size() == 0) {
                return false;
            }

            /**
             * "if", not "while"
             */
            if (!pq.isEmpty()) {
                Node cur = pq.poll();
                next = cur.val;

                //In the same iterator, move to the next unique number
                if (cur.it.hasNext()) {
                    int val = cur.it.next();
                    cur.val = val;
                    pq.offer(cur);
                }
            }

            return true;
        }

        public Integer next() {
            Integer res = next;
            /**
             * if we do not set next to null after each call, the value will
             * stay, even there's no more number left, next() will still return
             * the value.
             */
            next = null;
            return res;
        }
    }

    public static void main(String[] args) {
        List<Integer> l1 = new ArrayList<>(Arrays.asList(1, 2, 6, 8, 9));
        List<Integer> l2 = new ArrayList<>(Arrays.asList(2, 2, 3, 3, 5, 7, 10, 10));
        List<Integer> l3 = new ArrayList<>(Arrays.asList(9, 11));

        List<Iterator<Integer>> input = new ArrayList<>();
        input.add(l1.iterator());
        input.add(l2.iterator());
        input.add(l3.iterator());

        KWayIterator it = new KWayIterator(input);

        for (int i = 0; i < 18; i++) {
            System.out.println(it.hasNext());
            System.out.println(it.next());
        }
    }
}
