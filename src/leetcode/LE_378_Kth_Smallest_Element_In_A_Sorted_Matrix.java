package leetcode;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by yuank on 10/4/18.
 */
public class LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix {
    /**
         Given a n x m matrix where each of the rows and columns are sorted in ascending order,
         find the kth smallest element in the matrix.

         Note that it is the kth smallest element in the sorted order, not the kth distinct element.

         Example:

         matrix = [
             [ 1,  5,  9],
             [10, 11, 13],
             [12, 13, 15]
         ],
         k = 8,

         return 13.
         Note:
         You may assume k is always valid, 1 ≤ k ≤ n x m.

         Solve it in O(k log n) time where n is the bigger one between row size and column size.

         Medium

         变形题 LE_373_Find_K_Pairs_With_Smallest_Sums
     */

    /**
     * Solution 1
     * Time : O((n + k)log(n)))
     * Space : O(n)
     *
     * 实际上是“LE_23_Merge_k_Sorted_Lists”中的算法，K-way merge sort.
     * 把第一行（或列）的数先放入HEAP，拿出一个，根据“rows and columns are sorted in ascending order”，
     * 这实际上就是n个sorted list, 放入当前“list”的下一个。用MIN HEAP来从小到大找k个数。
     */
    public class Solution1 {
        class Element{
            int x, y, val;
            public Element(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        public int kthSmallest(int[][] matrix, int k) {
            if (matrix == null || matrix.length == 0) return 0;

            int m = matrix.length;
            int n = matrix[0].length;

            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
            for (int i = 0; i < n; i++) {
                pq.offer(new Element(0, i, matrix[0][i]));
            }

            for (int i = 0; i < k - 1; i++) {
                Element e = pq.poll();

                if (e.x == m - 1) {
                    continue;
                }

                pq.offer(new Element(e.x + 1, e.y, matrix[e.x + 1][e.y]));
            }

            return pq.poll().val;
        }
    }

    /**
     * Solution 2
     *
     * Time  : O(klogk)
     * Space : O(k + mn)
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
     * 行和列长度的更小者（如：很高或者很宽的矩阵）；还有一点是当k比行和列长度的更小者小时，
     * 队列的最大长度其实是k。
     *
     * Space Complexity: O(min(m,n,k) + mn)，visited数组和pq的大小
     */

    public class Solution2 {
        private  class Node {
            int x, y, val;
            public Node(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        private final int[][] DIR = new int[][]{{1, 0}, {0, 1}};

        public int kthSmallest(int[][] matrix, int k) {
            if (matrix == null || matrix.length == 0 ||
                    matrix[0] == null || matrix[0].length == 0 || k <= 0) {
                throw new IllegalArgumentException();
            }

            int m = matrix.length, n = matrix[0].length;
            boolean[][] visited = new boolean[m][n];
            Queue<Node> pq = new PriorityQueue<>((a,b) -> a.val - b.val);
            pq.offer(new Node(0, 0, matrix[0][0]));

            for (int i = 0; i < k - 1; i++) {
                Node curr = pq.poll();
                for (int dir = 0; dir < 2; dir++) {
                    int x = curr.x + DIR[dir][0];
                    int y = curr.y + DIR[dir][1];

                    //!!!
                    if (x < m && y < n && !visited[x][y]) {
                        visited[x][y] = true;
                        pq.offer(new Node(x, y, matrix[x][y]));
                    }
                }
            }

            return pq.poll().val;
        }
    }

    /**
     * Solution 3
     * Binary Search
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code
     */
    public class Solution3 {

        public int kthSmallest(int[][] matrix, int k) {
            int lo = matrix[0][0], hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1;//[lo, hi)

            while(lo < hi) {
                int mid = lo + (hi - lo) / 2;
                int count = 0,  j = matrix[0].length - 1;
                for(int i = 0; i < matrix.length; i++) {
                    while(j >= 0 && matrix[i][j] > mid) {
                        j--;
                    }

                    count += (j + 1);
                }
                if(count < k) lo = mid + 1;
                else hi = mid;
            }
            return lo;
        }
    }

    /**
     * Solution 4
     * Binary Search version by JiuZhang
     */
    public class Solution4 {
        class ResultType {
            public int num;
            public boolean exists;

            public ResultType(boolean e, int n) {
                exists = e;
                num = n;
            }
        }

        public ResultType check(int value, int[][] matrix) {
            int n = matrix.length;
            int m = matrix[0].length;

            boolean exists = false;
            int num = 0;
            int i = n - 1, j = 0;
            while (i >= 0 && j < m) {
                if (matrix[i][j] == value)
                    exists = true;

                if (matrix[i][j] <= value) {
                    num += i + 1;
                    j += 1;
                } else {
                    i -= 1;
                }
            }

            return new ResultType(exists, num);
        }

        public int kthSmallest(int[][] matrix, int k) {
            int n = matrix.length;
            int m = matrix[0].length;

            int left = matrix[0][0];
            int right = matrix[n - 1][m - 1];

            // left + 1 < right
            while (left <= right) {
                int mid = left + (right - left) / 2;
                ResultType type = check(mid, matrix);
                if (type.exists && type.num == k) {
                    return mid;
                } else if (type.num < k) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return left;
        }
    }
}
