package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by yuank on 10/4/18.
 */
public class LE_373_Find_K_Pairs_With_Smallest_Sums {
    /**
         You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
         Define a pair (u,v) which consists of one element from the first array and one element from the second array.

         Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.

         Example 1:
         Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
         Output: [[1,2],[1,4],[1,6]]
         Explanation: The first 3 pairs are returned from the sequence:
         [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]

         Example 2:
         Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
         Output: [1,1],[1,1]
         Explanation: The first 2 pairs are returned from the sequence:
         [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]

         Example 3:
         Input: nums1 = [1,2], nums2 = [3], k = 3
         Output: [1,3],[2,3]
         Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]

         Medium

         LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix
     */

    /**
     * Solution 1
     * Exact same as Solution 1 in LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix
     * K-way merge sort
     *
     * val(i, j) = nums1[i] + nums2[j]
     * There will be m * n vals which form a arrays of sorted arrays (matrix),
     * just like in LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix
     *
     * For example :
     * nums1 = [1,7,11], nums2 = [2,4,6],
     *
     * Matrix formed:
     * [
     *  [1 + 2, 7 + 2, 11 + 2],
     *  [1 + 4, 7 + 4, 11 + 4],
     *  [1 + 6, 7 + 6, 11 + 6]
     * ]
     *
     * Time  : O((n + k)logk)
     * Space : O(n))
     */
    class Solution1 {

        class Element {
            int x, y, val;

            public Element(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<int[]> res = new ArrayList<int[]>();
            if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) return res;

            int m = nums1.length, n = nums2.length;
            PriorityQueue<Element> pq = new PriorityQueue<Element>((a, b) -> a.val - b.val);

            for (int j = 0; j <= n - 1; j++) {
                pq.offer(new Element(0, j, nums1[0] + nums2[j]));
            }

            for (int i = 0; i < Math.min(k, m * n); i++) {
                Element e = pq.poll();
                res.add(new int[]{nums1[e.x], nums2[e.y]});

                if (e.x == m - 1) {
                    continue;
                }

                pq.offer(new Element(e.x + 1, e.y, nums1[e.x + 1] + nums2[e.y]));
            }
            return res;
        }
    }


    /**
     * Solution 2
     * A variation of Solution 2 for LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix
     */
    public class Solution2 {
        public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            int len1 = nums1.length;
            int len2 = nums2.length;
            List<int[]> result = new ArrayList<>();

            if(len1 == 0 || len2 == 0 || k < 1) {
                return result;
            }

            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
            pq.offer(new Element(0, 0, nums1[0]+nums2[0]));

            while(k > 0 && !pq.isEmpty()) {
                Element e = pq.poll();
                int[] pair = {nums1[e.x], nums2[e.y]};
                result.add(pair);
                k--;

                //Also, if the chosen pair is the first one in its row, then the first pair in the next row
                //is added to the queue.
                if(e.y == 0 && e.x < len1 -1) {
                    pq.offer(new Element(e.x + 1, e.y, nums1[e.x + 1]+nums2[e.y]));
                }

                //Whenever a pair is chosen into the output result, the next pair in the row gets added
                //to the priority queue of current options.
                if(e.y < len2 -1) {
                    pq.offer(new Element(e.x, e.y + 1, nums1[e.x] + nums2[e.y + 1]));
                }
            }


            return result;
        }

        class Element {
            int x;
            int y;
            int val;

            public Element(int x, int y, int z) {
                this.x = x;
                this.y = y;
                val = z;
            }
        }
    }

    /**
     * Solution 3
     * Same as Solution2 in LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix
     * Time  : O(klogk)
     * Space : O(k + mn)
     *
     * 适用于follow up：
     * 假设是两个超大的sorted stream.
     *
     * 唯一要改变的就是， 不能用visited[][]去判断是否访问过。
     *
     * Set<Pair>? (Pair has x and y coordinates) ??
     */
    public class Solution3 {

        class Element{
            int x, y, val;
            public Element(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        public int kthSmallestSum(int[] A, int[] B, int k) {
            if (A == null && B == null) return 0;

            //!!! Never forget to add comparator lambda here
            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
            pq.offer(new Element(0, 0, A[0] + B[0]));

            //!!! Define 2D array
            boolean[][] visited = new boolean[A.length][B.length];
            int[][] dir = new int[][] {{1, 0}, {0, 1}};

            while (k > 1 && !pq.isEmpty()) {
                Element e = pq.poll();
                k--;

                for (int i = 0; i < 2; i++) {
                    int a = e.x + dir[i][0];
                    int b = e.y + dir[i][1];

                    //!!!Check array boundary
                    if (a < A.length && b < B.length && !visited[a][b]) {
                        pq.offer(new Element(a, b, A[a] + B[b]));
                        visited[a][b] = true;
                    }
                }
            }

            return pq.poll().val;
        }
    }

}
