package src.Interviews.Indeed;

import java.util.*;

public class K_Most_Frequent_Word {
    /**
     * 给List<List<Integer>> 要求出前k个最常出现的数字
     * 有点像merge k streams吧 但这个要求同个stream的也算
     * 这轮最后一轮了面试官看起来很累, 写白板的时候跟他交流感觉他已经在恍神了
     * 写完后跑case 并分析複杂度
     * 讨论了几个极值的的case  comparator会怎麽样 (min integer之类的)
     * 一时之前没想到 面试官提示了下回答出来了
     *
     */

    /**
     * We can use HashMap to count frequency of each number, it takes space m * n.
     * Guess that each list is sorted, then we can use k-way merge to solve the problem.
     *
     * Two PriorityQueue solution
     */
    class Node {
        Iterator<Integer> it;
        int val;

        public Node(Iterator<Integer> it, int val) {
            this.it = it;
            this.val = val;
        }
    }

    class Element {
        int val;
        /**
         * !!!
         */
        long freq;

        public Element(int val, long freq) {
            this.val = val;
            this.freq = freq;
        }
    }

    /**
     * Time  : O(m * n * logm + m * n * logk), m is number of lists, n is average number of elements in list
     * Space : O(m + k)
     */
    public List<Integer> getKMostFrequent(List<List<Integer>> lists, int k) {
        List<Integer> res = new ArrayList();
        if (lists == null || lists.size() == 0) return res;

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        PriorityQueue<Element> ans = new PriorityQueue<>((a, b) -> Long.compare(a.freq, b.freq));

        for (List<Integer> l : lists) {
            if (l == null) continue;

            Iterator<Integer> it = l.iterator();
            if (it.hasNext()) {
                pq.offer(new Node(it, it.next()));
            }
        }

        Integer pre = null;
        long count = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (pre == null) {
                pre = cur.val;
                count = 1;
            } else {
                if (cur.val == pre) {
                    count++;
                } else {
//                    System.out.println("val="+pre + ", count="+count);
                    ans.offer(new Element(pre, count));
                    if (ans.size() > k) {
                        ans.poll();
                    }

                    pre = cur.val;
                    count = 1;
                }
            }

            while (cur.it.hasNext()) {
                int num = cur.it.next();
                if (num != pre) {
                    cur.val = num;
                    pq.offer(cur);
                    break;
                } else {
                    count++;
                }
            }
        }

        while (!ans.isEmpty()) {
            res.add(0, ans.poll().val);
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
        List<List<Integer>> source = new ArrayList<>();
        source.add(list11);
        source.add(list22);
        source.add(list33);
        source.add(list44);

        K_Most_Frequent_Word test = new K_Most_Frequent_Word();
        System.out.println(Arrays.toString(test.getKMostFrequent(source, 4).toArray()));
    }
}
