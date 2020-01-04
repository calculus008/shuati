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
     * 14 ms
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

            /**
             * loop i - 1 times, each time remove the smallest element and add one from the
             * same list that the smallest element is in. After it, the one at the top of
             * pq is the ith smallest element.
             */
            for (int i = 0; i < k - 1; i++) {
                Element e = pq.poll();

                if (e.x == m - 1) {
                    continue;
                }

                pq.offer(new Element(e.x + 1, e.y, matrix[e.x + 1][e.y]));
            }

            return pq.poll().val;//0R pq.peek().val
        }
    }

    /**
     * Solution 2
     *
     * Time  : O(klogk)
     * Space : O(k + mn)
     *
     * 54 ms
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
     * Use int[] instead of Node object in pq
     * In the updated description, size of matrix is n * n.
     */
    public class Solution2_Pratice {
        public int kthSmallest(int[][] matrix, int k) {
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> matrix[a[0]][a[1]] - matrix[b[0]][b[1]]);

            int n = matrix.length;
            int[][] dirs = {{1, 0}, {0, 1}};
            boolean[][] visited = new boolean[n][n];

            pq.offer(new int[]{0, 0});

            while (k-- > 1 && !pq.isEmpty()) {
                int[] cur = pq.poll();

                for (int[] dir : dirs) {
                    int x = cur[0] + dir[0];
                    int y = cur[1] + dir[1];

                    /**
                     * !!!
                     * Whenever use dirs moving around, MUST check boundary conditions!!!
                     */
                    if (x < 0 || x >= n || y < 0 || y >= n || visited[x][y]) continue;

                    pq.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
            }

            int[] res = pq.peek();
            return matrix[res[0]][res[1]];
        }
    }

    /**
     * Solution 3
     * Binary Search
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code
     *
     * Time  : O(n * log(Max)), binary search on range [lo, hi) -> log(Max), for each iteration, O(n) to get count.
     * Space : O(1)
     *
     * 1 ms
     */
    public class Solution3 {
        public int kthSmallest(int[][] matrix, int k) {
            int lo = matrix[0][0];
            int hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1;//[lo, hi), use huahua's binary search template

            while(lo < hi) {
                int mid = lo + (hi - lo) / 2;

                /**
                 * For each row, start from the end of the row, find
                 * the first element that is <= mid, sum of them, so
                 * count is the total number of element that is <= mid.
                 */
                int count = 0;
                int j = matrix[0].length - 1;

                for(int i = 0; i < matrix.length; i++) {
                    while(j >= 0 && matrix[i][j] > mid) {
                        j--;
                    }

                    count += (j + 1);//j is index, so the number of elements is j + 1
                }

                if(count < k) {//if count < k, means there are not enough elements on the left side of mid, move to right
                    lo = mid + 1;
                } else {//if count >= k, there are more elements than needed on left, move to left.
                    hi = mid;
                }
            }

            return lo;
        }
    }

    /**
     * Tricky part:
     *
     * The lo we returned is guaranteed to be an element in the matrix is because:
     * Let us assume element m is the kth smallest number in the matrix, and x is the number of element m in the matrix.
     * When we are about to reach convergence, if mid=m-1, its count value (the number of elements which are <= mid)
     * would be k-x, so we would set lo as (m-1)+1=m, in this case the hi will finally reach lo; and if mid=m+1, its
     * count value would be k+x-1, so we would set hi as m+1, in this case the lo will finally reach m.
     *
     * To sum up, because the number lo found by binary search find is exactly the element which has k number of elements
     * in the matrix that are <= lo, The equal sign guarantees there exists and only exists one number in range satisfying
     * this condition. So lo must be the only element satisfying this element in the matrix.
     */
    class Solution3_Practice {
        public int kthSmallest(int[][] matrix, int k) {
            if (null == matrix || matrix.length == 0) return -1;

            int m = matrix.length;
            int n = matrix[0].length;

            int l = matrix[0][0];
            int h = matrix[m - 1][n - 1] + 1;

            while (l < h) {
                int mid = l + (h - l) / 2;

                int count = 0;
                int j = n - 1;
                for (int i = 0; i < m; i++) {
                    /**
                     * !!!
                     * "j >= 0"
                     */
                    while (j >= 0 && matrix[i][j] > mid) {
                        j--;
                    }
                    count += (j + 1);
                }


                if (count < k) {
                    /**
                     * !!!
                     * "l = mid + 1"
                     */
                    l = mid + 1;
                } else {
                    h = mid;
                }
            }

            return l;
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

    /**
     * Binary Search Solution based on the same algorithm of LE_668_Kth_Smallest_Number_In_Multiplication_Table
     *
     * Same as Solution3
     */
    public class Solution {
        public int kthSmallest(int[][] matrix, int k) {
            int m = matrix.length;
            int n = matrix[0].length;

            int l = matrix[0][0];
            int r = matrix[m - 1][n - 1];// matrix[m - 1][n - 1] + 1 also works

            while (l < r) {
                int mid = l + (r - l) / 2;
                if (count(matrix, k, mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            return l;
        }

        private boolean count(int[][] matrix, int k, int x) {
            int n = matrix[0].length;
            int m = matrix.length;
            int count = 0;

            for (int i = 0; i < m; i++) {
                int j = n - 1;
                while (j >= 0 && matrix[i][j] > x) {
                    j--;
                }
                count += j + 1;

                /**
                 * !!!
                 * ">= k"
                 */
                if (count >= k) {
                    return true;
                }
            }

            return false;
        }
    }
}
