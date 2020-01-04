package leetcode;

import java.util.*;

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
     * Concise preferred solution
     *
     * Key : limit the size of pq to k, time : O(klogk)
     */
    class Solution_Preferred {
        public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> (nums1[a[0]] + nums2[a[1]]) - (nums1[b[0]] + nums2[b[1]]));
            List<int[]> res = new ArrayList<>();

            if (nums1.length == 0 || nums2.length == 0 || k == 0){
                return res;
            }

            for (int i = 0; i < nums1.length && i < k; i++){
                pq.offer(new int[]{i, 0});
            }

            while (k-- > 0 && !pq.isEmpty()){
                int[] cur = pq.poll();
                res.add(new int[]{nums1[cur[0]], nums2[cur[1]]});
                if (cur[1] == nums2.length - 1) {
                    continue; //Dont' add the next index if there is no more left in 2nd array
                }

                pq.offer(new int[]{cur[0], cur[1] + 1});
            }

            return res;
        }
    }

    class Solution_Return_ListOfList {
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0 || k <0) return res;


            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (nums1[a[0]] + nums2[a[1]]) - (nums1[b[0]] + nums2[b[1]]));

            int n1 = nums1.length;
            int n2 = nums2.length;

            for (int i = 0; i < n1; i++) {
                pq.offer(new int[]{i, 0});
                if (i == k - 1) break;
            }

            while (k != 0 && !pq.isEmpty()) {
                int[] cur = pq.poll();
                List<Integer> l = new ArrayList<>();
                l.add(nums1[cur[0]]);
                l.add(nums2[cur[1]]);
                res.add(l);
                /**
                 * !!!
                 * can't do it after the next if
                 */
                k--;

                if (cur[1] == n2 - 1) continue;

                cur[1]++;
                pq.offer(cur);
            }

            return res;
        }
    }

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
                if(e.y == 0 && e.x < len1 - 1) {
                    pq.offer(new Element(e.x + 1, e.y, nums1[e.x + 1]+nums2[e.y]));
                }

                //Whenever a pair is chosen into the output result, the next pair in the row gets added
                //to the priority queue of current options.
                if(e.y < len2 - 1) {
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
     * 其实是利用优先级队列做BFS，搜索直到第k小，对于
     * [
     *  [1 ,5 ,7],
     *  [3 ,7 ,8],
     *  [4 ,8 ,9],
     * ]
     *
     * 1是第一层，斜着的3，5是第二层，再斜着的4，7，7是第三层，8，8是第四层, 9是最后一层。
     * 利用方向数组进行向下和向右搜索，利用visited数组记录访问过的位置（可以用hashset,如x*103 + y），
     * minheap移除的都是较小的那个。
     *
     * Time Complexity: O(klog(min(m,n,k)))，队列最大长度是最长的那个对角线的元素个数，which is
     * 行和列长度的更小者（如：很高或者很宽的矩阵）(??) ；还有一点是当k比行和列长度的更小者小时，
     * 队列的最大长度其实是k。
     *
     * Space Complexity: O(min(m,n,k) + mn)，visited数组和pq的大小
     *
     */
    class Solution_Follow_Up {
        class Element{
            int x, y;

            public Element(int x, int y) {
                this.x = x;
                this.y = y;
            }

//            public boolean equals(Object o) {
//                 if (o == this) {
//                     return true;
//                 }

//                 if (!(o instanceof Element)) {
//                     return false;
//                 }

//                 Element c = (Element) o;

//                 // Compare the data members and return accordingly
//                 return c.x == this.x && c.y == this.y;
//             }

            public int hashCode() {
                return x * 103 + y;
            }
        }

        public  List<int[]> kSmallestPairs(int[] A, int[] B, int k) {
            List<int[]> res = new ArrayList<>();
            if (A == null || B == null || A.length == 0 || B.length == 0) return res;

            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> (A[a.x] + B[a.y]) - (A[b.x] + B[b.y]));
            Set<Integer> visited = new HashSet<>();

            Element first = new Element(0, 0);
            pq.offer(first);
            visited.add(first.hashCode());
            int count = k;

            int[][] dir = new int[][] {{1, 0}, {0, 1}};

            while (count > 0 && !pq.isEmpty()) {
                Element e = pq.poll();
                res.add(new int[]{A[e.x], B[e.y]});
                count--;

                for (int i = 0; i < 2; i++) {
                    int nx = e.x + dir[i][0];
                    int ny = e.y + dir[i][1];

                    //!!!Check array boundary
                    if (nx < A.length && ny < B.length ) {
                        Element next = new Element(nx, ny);
                        if (!visited.contains(next.hashCode())) {
                            pq.offer(next);
                            visited.add(next.hashCode());
                        }
                    }
                }
            }

            return res;
        }
    }
}
