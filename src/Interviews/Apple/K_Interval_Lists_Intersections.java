package Interviews.Apple;

import java.util.*;

public class K_Interval_Lists_Intersections {
    /**
     * LE_986_Interval_List_Intersections variation
     *
     * 利口酒吧溜变种。输入是多个list (sorted??)，不是两个list.
     */
    static class Pair {
        Iterator<int[]> it;
        int[] val;

        public Pair(Iterator<int[]> it, int[] val) {
            this.it = it;
            this.val = val;
        }
    }

    /**
     * Two steps merge:
     * 1.K-way heap pick out the intersections to temp list (there are possible duplicates and intersections)
     * 2.Run merge on temp list, output to res.
     *
     * Time : O(sum(l)logk), k is the number of lists
     * Space : O(k)
     */
    public static List<int[]> getIntersections(List<List<int[]>> intervals) {
        List<int[]> res = new ArrayList<>();
        if (intervals == null || intervals.size() == 0) return res;

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.val[0] - b.val[0]);

        for (List<int[]> l : intervals) {
            if (l == null || l.size() == 0) continue;

            Collections.sort(l, (a, b) -> a[0] - b[0]);
            Iterator<int[]> it = l.iterator();
            int[] val = it.next();
            pq.offer(new Pair(it, val));

            System.out.println(Arrays.toString(val));
        }

        List<int[]> temp = new ArrayList<>();
        int[] pre = null;
        while (!pq.isEmpty()) {
            Pair p = pq.poll();

            int[] cur = p.val;

            System.out.println("cur : " + Arrays.toString(cur));

            if (pre == null || pre[1] < cur[0]) {
                pre = cur;
            } else {
                int maxStart = Math.max(pre[0], cur[0]);
                int minEnd = Math.min(pre[1], cur[1]);

                if (maxStart <= minEnd) {
                    temp.add(new int[]{maxStart, minEnd});
                }

                pre[1] = Math.max(pre[1], cur[1]);
            }

            if (p.it.hasNext()) {
                p.val = p.it.next();
                pq.offer(p);
            }
        }

        int[] last = null;
        for (int[] cur : temp) {
            if (last == null || last[1] < cur[0]) {
                res.add(cur);
                last = cur;
            } else {
                last[1] = Math.max(last[1], cur[1]);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<int[]> l1 = new ArrayList();
        l1.add(new int[]{1, 4});
        l1.add(new int[]{5, 7});
        l1.add(new int[]{10, 15});

        List<int[]> l2 = new ArrayList();
        l2.add(new int[]{3, 4});
        l2.add(new int[]{8, 9});
        l2.add(new int[]{11, 12});

        List<int[]> l3 = new ArrayList();
        l3.add(new int[]{3, 4});
        l3.add(new int[]{5, 6});
        l3.add(new int[]{12, 13});

        List<List<int[]>> input = new ArrayList<>();
        input.add(l1);
        input.add(l2);
        input.add(l3);

        List<int[]> res = getIntersections(input);

        for (int[] i : res) {
            System.out.println(Arrays.toString(i));
        }
    }
}
