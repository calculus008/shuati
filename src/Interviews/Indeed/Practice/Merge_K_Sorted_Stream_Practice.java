package Interviews.Indeed.Practice;

import java.util.*;

public class Merge_K_Sorted_Stream_Practice {
    static class Stream {
        Iterator<Integer> it;

        public Stream(Iterator<Integer> it) {
            this.it = it;
        }

        public boolean move() {
            return it.hasNext();
        }

        public int getValue() {
            return it.next();
        }
    }

    class Node {
        Stream stream;
        int val;

        public Node(Stream stream, int val) {
            this.stream = stream;
            this.val = val;
        }
    }

    public List<Integer> merge(List<Stream> streams, int k) {
        List<Integer> res = new ArrayList<>();
        if (k <= 0 || k > streams.size()) return res;

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        for (Stream stream : streams) {
            if (stream == null || !stream.move()) continue;

            Node n = new Node(stream, stream.getValue());
            pq.offer(n);
        }

        if (pq.size() < k) return res;

        Integer pre = null;
        int count = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (pre == null) {
                pre = cur.val;
                count = 1;
            } else {
                if (cur.val == pre) {
                    count++;
                } else {
                    count = 1;
                    pre = cur.val;
                }
            }

            if (count == k) {
                res.add(pre);
            }

            while (cur.stream.move()) {
                int val = cur.stream.getValue();
                if (val != pre) {
                    cur.val = val;
                    pq.offer(cur);
                    break;
                }
            }


            /**
             * follow up point, when we can stop?
             * pq size will keep decreasing, once it is smaller than k,
             * it will not grow back.
             */
            if (pq.size() < k) break;
        }

        return res;
    }

    public static void main(String[] args) {
        Merge_K_Sorted_Stream_Practice test = new Merge_K_Sorted_Stream_Practice();

        Integer[] arr11 = {3, 4, 4, 4, 12, 14, 18};
        Integer[] arr22 = {1, 4, 7, 8, 8, 8, 8, 14, 18};
        Integer[] arr33 = {1, 2, 4, 6, 12, 13, 17, 17, 17, 18, 18, 18, 18, 18, 18};
        Integer[] arr44 = {7, 14, 17, 18, 120, 124, 129};
        List<Integer> list11 = Arrays.asList(arr11);
        List<Integer> list22 = Arrays.asList(arr22);
        List<Integer> list33 = Arrays.asList(arr33);
        List<Integer> list44 = Arrays.asList(arr44);
        List<Stream> source1 = new ArrayList<>();
        Stream s1 = new Stream(list11.iterator());
        Stream s2 = new Stream(list22.iterator());
        Stream s3 = new Stream(list33.iterator());
        Stream s4 = new Stream(list44.iterator());

        source1.add(s1);
        source1.add(s2);
        source1.add(s3);
        source1.add(s4);

        System.out.println(Arrays.toString(test.merge(source1, 1).toArray()));
    }
}
