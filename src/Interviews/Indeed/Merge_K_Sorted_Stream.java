package Interviews.Indeed;

import java.util.*;

public class Merge_K_Sorted_Stream {
    /**
     * Given n sorted stream, and a constant number k. The stream type is like iterator
     * and it has two functions, move() and getValue(), find a list of numbers that each
     * of them appears at least k times in these streams. Duplicate numbers in a stream
     * should be counted as once.
     */

    /**
     * n - number of streams
     * m - average length of streams
     *
     * Time  : O(n * m * logn)
     * Space : O(n)
     */
    class Node {
        int val;
        Stream stream;

        public Node(int val, Stream stream) {
            this.val = val;
            this.stream = stream;
        }
    }

    public List<Integer> getElementsWithKFrequence(List<Stream> streams, int k) {
        List<Integer> res = new ArrayList<>();
        if (null == streams || streams.size() == 0 || k <= 0 || k > streams.size()) return res;

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        int numOfStreams = 0;
        for (Stream s : streams) {
            if (!s.move()) continue;

            Node n = new Node(s.getValue(), s);
            pq.offer(n);
            numOfStreams++;
        }

        if (k > numOfStreams) return res;

        Integer pre = null;
        int count = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (pre == null) {
                pre = cur.val;
                count = 1;
            } else {
                if (pre == cur.val) {
                    count++;
                } else {
                    pre = cur.val;
                    count = 1;
                }
            }

            if (count == k) {
                res.add(pre);
            }

            if (pq.size() + count < k) {
                System.out.println("pre="+pre + ", count=" + count + ", size=" + pq.size());
                break;
            }

            while (cur.stream.move()) {
                int num = cur.stream.getValue();
                if (num != pre) {
                    cur.val = num;
                    pq.offer(cur);
                    break;//!!!
                }
            }

            /**
             * if one stream is really long, we can break here when size of pq is smaller than k.
             * Assume each stream has finite number of integers, once number of streams in pq
             * is smaller than k, it will never increase.
             */
//            if (pq.size() - 1 + count < k) break;
//            if (pq.size() + count < k) {
//                System.out.println("pre="+pre + ", count=" + count + ", size=" + pq.size());
//                break;
//            }
        }

        return res;
    }

    public static void main(String[] args) {
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

        Merge_K_Sorted_Stream mkss = new Merge_K_Sorted_Stream();

        List<Integer> res = mkss.getElementsWithKFrequence(source1, 2);
        System.out.println(Arrays.toString(res.toArray()));
    }
}
