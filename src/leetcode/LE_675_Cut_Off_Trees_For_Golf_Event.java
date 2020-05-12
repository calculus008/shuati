package leetcode;

import java.util.*;

public class LE_675_Cut_Off_Trees_For_Golf_Event {
    /**
         You are asked to cut off trees in a forest for a golf event.
         The forest is represented as a non-negative 2D array, in this array:

         0 represents the obstacle can't be reached.
         1 represents the ground can be walked through.

         The place with number bigger than 1 represents a tree can be walked through,
         and this positive number represents the tree's height.

         You are asked to cut off all the trees in this forest in the order of tree's height -
         always cut off the tree with lowest height first. And after cutting, the original place
         has the tree will become a grass (value 1).

         You will start from the point (0, 0) and you should output the minimum steps you need
         to walk to cut off all the trees. If you can't cut off all the trees, output -1 in that situation.

         You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off.

         Example 1:
         Input:
         [
         [1,2,3],
         [0,0,4],
         [7,6,5]
         ]
         Output: 6

         Example 2:
         Input:
         [
         [1,2,3],
         [0,0,0],
         [7,6,5]
         ]
         Output: -1

         Example 3:
         Input:
         [
         [2,3,4],
         [0,0,5],
         [8,7,6]
         ]
         Output: 6

         Explanation: You started from the point (0,0) and you can cut off the tree in (0,0) directly without walking.
         Hint: size of the given matrix will not exceed 50x50.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-675-cut-off-trees-for-golf-event/
     *
     * Greedy + Shortest path (BFS)
     *
     * Identify and sort the trees by its heights, then find shortest paths between
     *
     * 0,0 to tree[1]
     * tree[1] to tree[2]
     * …
     * tree[n-1] to tree[n]
     *
     * Time complexity: O(m ^2 * n ^ 2)
     *
     * Space complexity: O(m * n)
     */

    /**
     * 407 ms
     * Use int[] to save x, y and height
     */
    class Solution1 {
        List<List<Integer>> f;
        List<int[]> heights;
        int n;
        int m;
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};


        public int cutOffTree(List<List<Integer>> forest) {
            if(null == forest || forest.size() == 0) return -1;

            f = forest;
            n = forest.size();
            m = forest.get(0).size();
            heights = new ArrayList<>();

            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++) {
                    //!!! how to add a new int array this way, no dimention here!!!
                    //heights.add(new int[3]{forest.get(i).get(j), i, j});
                    if(forest.get(i).get(j)>1) {//only add trees
                        heights.add( new int[]{forest.get(i).get(j), i, j} );
                    }
                }
            }

            //sort by tree height
            Collections.sort(heights, new Comparator<int[]>() {
                public int compare(int[] t1, int[] t2) {
                    return t1[0] - t2[0];
                }
            });

            int steps = 0;
            int start_x = 0, start_y = 0;
            for(int i=0; i<heights.size(); i++){
                int end_x = heights.get(i)[1];
                int end_y = heights.get(i)[2];
                int res = bfs(start_x, start_y, end_x, end_y);
                if(res == -1) return -1;
                steps += res;
                start_x = end_x;
                start_y = end_y;
            }

            return steps;
        }

        //find a path from start to end
        private int bfs(int x, int y, int x1, int y1) {
            if (x == x1 && y == y1) return 0;
            int res = 0;

            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{x, y});
            int[][] visited = new int[n][m];
            visited[x][y] = 1;

            while (!queue.isEmpty()) {
                int size = queue.size();
                res++;

                for (int i = 0; i < size; i++) {
                    int[] cur = queue.poll();
                    int cur_x = cur[0], cur_y = cur[1];

                    for (int j = 0; j < 4; j++) {
                        int new_x = cur_x + dir[j][0];
                        int new_y = cur_y + dir[j][1];
                        if (new_x >= 0 && new_x < n && new_y >= 0 && new_y < m
                                && f.get(new_x).get(new_y) > 0 && visited[new_x][new_y] == 0) {
                            if (new_x == x1 && new_y == y1) return res;
                            visited[new_x][new_y] = 1;
                            queue.add(new int[]{new_x, new_y});
                        }
                    }
                }
            }

            return -1;
        }
    }

    /**
     * 465 ms
     * Use class Tuple to save x, y and height
     */
    class Solution2 {
        class Tuple {
            int x;
            int y;
            int h;

            public Tuple(int x, int y, int h) {
                this.x = x;
                this.y = y;
                this.h = h;
            }
        }

        int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int cutOffTree(List<List<Integer>> forest) {
            if (null == forest) {
                return -1;
            }

            int m = forest.size();
            int n = forest.get(0).size();

            List<Tuple> trees = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int h = forest.get(i).get(j);
                    /**
                     * !!!
                     * Only add trees
                     */
                    if (h > 1) {
                        trees.add(new Tuple(i, j, h));
                    }
                }
            }

            Collections.sort(trees, (a, b) -> a.h - b.h);

            int res = 0;
            Tuple start = new Tuple(0, 0, 1);

            for (Tuple tree : trees) {
                int steps = getSteps(forest, start, tree, m, n);
                if (steps == -1) {
                    return -1;
                }

                res += steps;
                start = tree;
            }

            return res;
        }

        private int getSteps(List<List<Integer>> forest, Tuple start, Tuple end, int m, int n) {
            Queue<Tuple> q = new LinkedList<>();
            q.offer(start);

            boolean[][] visited = new boolean[m][n];
            visited[start.x][start.y] = true;

            int steps = 0;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    Tuple cur = q.poll();

                    if (cur.x == end.x && cur.y == end.y) {
                        return steps;
                    }

                    for (int j = 0; j < 4; j++) {
                        int nx = cur.x + dirs[j][0];
                        int ny = cur.y + dirs[j][1];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                            continue;
                        }

                        /**
                         * Must write it after the first if, otherwise, there's possible index overflow
                         */
                        if (forest.get(nx).get(ny) == 0 || visited[nx][ny]) {
                            continue;
                        }

                        /**
                         * !!!
                         */
                        visited[nx][ny] = true;
                        q.offer(new Tuple(nx, ny, 1));
                    }
                }
                //!!!
                steps++;
            }

            return -1;
        }
    }

    /**
     * same as Solution2, only difference is to use 2D array in processing, make it faster
     *
     * 241 ms
     * 94.36%
     */
    class Solution3 {
        class Tuple {
            int x, y, val;
            public Tuple(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        public int cutOffTree(List<List<Integer>> forest) {
            if(null == forest) return -1;

            if (forest.get(0).get(0) == 0) return -1;

            int res = 0;
            List<Tuple> trees = new ArrayList<>();
            int m = forest.size();
            int n = forest.get(0).size();
            int[][] f = new int[m][n];

            for (int i = 0; i < forest.size(); i++) {
                List l = forest.get(i);
                for (int j = 0; j < l.size(); j++) {
                    /**
                     * 坑1
                     * Integer to int here must explicitly cast "(int)"
                     */
                    int val = (int)l.get(j);
                    f[i][j] = val;

                    if (val > 1) {
                        trees.add(new Tuple(i, j, val));
                    }
                }
            }

            Collections.sort(trees, (a, b) -> a.val - b.val);

            Tuple start = new Tuple(0, 0, 1);

            for (Tuple t : trees) {
                /**
                 * each time, destination or end is current t.
                 */
                int steps = getSteps(f, start, t);
                if (steps == -1) {
                    return -1;
                }

                /**
                 * !!!
                 * Don't forget
                 */
                res += steps;
                start = t;
            }

            return res;
        }

        private int getSteps(int[][] f, Tuple start, Tuple end) {
            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            Queue<Tuple> q = new LinkedList<>();
            q.offer(start);

            boolean[][] visited = new boolean[f.length][f[0].length];

            /**
             * 坑2
             * !!!
             * 别忘了把起点加入visited
             */
            visited[start.x][start.y] = true;

            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    Tuple cur = q.poll();

                    /**
                     * 坑3
                     * !!!
                     * 是在从队列里去除元素是判断是否到达终点
                     * 判断坐标相等，不是值相等
                     */
                    if (cur.x == end.x && cur.y == end.y) {
                        return steps;
                    }

                    for (int j = 0; j < 4; j++) {
                        int nx = cur.x + dirs[j][0];
                        int ny = cur.y + dirs[j][1];

                        if (nx < 0 || nx >= f.length || ny < 0 || ny >= f[0].length
                                || f[nx][ny] == 0 || visited[nx][ny]) {
                            continue;
                        }

                        // if (f[nx][ny] == dst) {
                        //     return steps;
                        // }

                        visited[nx][ny] = true;
                        q.offer(new Tuple(nx, ny, 1));
                    }
                }

                /**
                 * 坑4
                 * !!!
                 * 在最后steps加1.
                 */
                steps++;
            }

            return -1;
        }
    }
}