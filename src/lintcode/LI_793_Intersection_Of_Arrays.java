package lintcode;

import java.util.*;

/**
 * Created by yuank on 10/2/18.
 */
public class LI_793_Intersection_Of_Arrays {
    /**
         Give a number of arrays, find their intersection, and output their intersection size.

         Example
         Given [[1,2,3],[3,4,5],[3,9,10]], return 1
         explanation:
         Only element 3 appears in all arrays, the intersection is [3], and the size is 1.


         Given [[1,2,3,4],[1,2,5,6,7][9,10,1,5,2,3]], return 2
         explanation:
         Only element 1,2 appear in all arrays, the intersection is [1,2], the size is 2.

         Notice
         The total number of all array elements is not more than 500000.
         There are no duplicated elements in each array.

         Medium
     */

    /**
     * Solution 1
     * "2" Sets
     *
     * Time : O(nk)
     * Space : O(nk)
     * 首先用一个set把第一行数字存进去,然后从第二行开始遍历，层级遍历，把每层和上一层一样的数字存进一个新的set里，
     * 然后更新set用于下一行查找，最后set里面的数字数目就是全部的交集
     */
    public int intersectionOfArrays1(int[][] arrs) {
        // write your code here\
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < arrs[0].length; i++){
            set.add(arrs[0][i]);
        }
        for(int i = 1; i < arrs.length; i++){
            Set<Integer> set1 = new HashSet<>();
            for(int j = 0; j < arrs[i].length; j++){
                if(set.contains(arrs[i][j])){
                    set1.add(arrs[i][j]);
                }
            }
            set = set1;
        }
        return set.size();
    }

    /**
     * Solution 2
     * HashMap
     * Time and Space : O(nk)
     */
    public int intersectionOfArrays(int[][] arrs) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i = 0;i < arrs.length;i++) {
            for (int j = 0;j < arrs[i].length;j++) {
                int t;

                if(map.containsKey(arrs[i][j])) {
                    t = map.get(arrs[i][j]) + 1;
                } else {
                    t = 1;
                }

                map.put(arrs[i][j], t);
            }
        }
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue() == arrs.length)
                ans++;
        }
        return ans;
    }

    /**
     * Solution 3
     * PriorityQueue
     *
     * 基于 Priority Queue 的版本。
     *
     * 实际上是用了“LI_486_Merge_K_Sorted_Arrays”的算法，keep counting number of elements with
     * the same value, sinc "There are no duplicated elements in each array",
     * once the count equals arrs.length, this value exists in all arrays, add 1 to result.
     *
     * 假设每个数组长度为 n, 一共 k 个数组。
     * 时间复杂度为 O(knlogn + nklogk)
     * 其中 knlogn 是 k 个数组进行分别排序的时间复杂度
     * nklogk 是 总共 nk 个数从 PriorityQueue 中进出，每次进出 logk。
     *
     * 相比使用 HashMap 的算法的时间复杂度 O(nk) 这个方法并没有什么时间上的优势。
     * 但是这个方法的空间复杂度很低，只有 O(k)，即多少个数组就花费多少的额外空间。
     *
     * !!!在面试中也是很有可能会被要求不用 HashMap 或者实现一个比 O(n)更低的空间复杂度的算法。因此这个程序的方法也是需要掌握的。
     */
    class Pair {
        public int row, col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public int intersectionOfArrays2(int[][] arrs) {
        if (arrs == null || arrs.length == 0 || arrs[0].length == 0) return 0;

        int res = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> arrs[a.row][a.col] - arrs[b.row][b.col]);

        for (int i = 0; i < arrs.length; i++) {
            if (arrs[i] == null || arrs[i].length == 0) {
                return 0;//!!!
            }

            //!!!
            Arrays.sort(arrs[i]);
            pq.offer(new Pair(i, 0));
        }

        int last = 0, count = 0;
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            if (arrs[p.row][p.col] != last || count == 0) {
                if (count == arrs.length) {
                    res++;
                }
                last = arrs[p.row][p.col];
                count = 1;
            } else {
                count++;
            }

            p.col++;
            if (p.col < arrs[p.row].length) {
                pq.offer(new Pair(p.row, p.col));
            }
        }

        /**
         * !!! Deal with the last element outside loop
         */
        if (count == arrs.length) {
            res++;
        }

        return res;
    }
}
