package Interviews.Google;

import java.util.*;

public class BicyblePeopleMatching {
    /**
     * 2D平面上，有m个人（P），n辆自行车(B)，还有空白（O）满足以下条件
     * 1.m < n.
     * 2.不存在两个人，到同一辆自行车距离相等, 距离用abs(x1-x2) + abs(y1-y2)定义
     * 3.每个人尽量找离自己最近的自行车，一旦某辆自行车被占，其他人只能找别的自行车。
     *
     * 例
     * OPOBOOP
     * OOOOOOO
     * OOOOOOO
     * OOOOOOO
     * BOOBOOB
     *
     * 红色的人找到第一行的自行车，距离最近。
     * 蓝色的人离第一行自行车最近，但自行车已经被红色人占有，所以他只能找离他第二近的，右下角的自行车。
     * 问：把人和自行车配对，输出vector<pair<int, int>>每个人对应的自行车. {i, j} 是人i对应自行车j.
     *
     * 各种不同的要求:
     * 1.我面试时候，面试官的要求是每个人都同时开始找，尽量找自己近的。
     *   假设每个人向四周的搜索速度是一样的，算是局部最优.
     *
     * 2.我面试的时候是尽量多匹配，优先安排距离最近的，并不考虑全局最优.
     *
     * 3.可能加条件，用1代表obstacles。
     */

    /**
     * Summary:
     * 需要跟面试官讨论清楚他需要的最佳匹配是什么 (!!!)
     * 1.如果是要求全局人车距离最短
     *   二分图的最佳匹配问题，使用匈牙利算法，参考题目https://blog.csdn.net/u011721440/article/details/38169201
     *   发现这里不能使用max flow， 因为这个bipartite is a weighted graph.
     *   参考 :
     *   https://www.topcoder.com/community/competitive-programming/tutorials/assignment-problem-and-hungarian-algorithm/
     *   https://github.com/aalmi/HungarianAlgorithm/blob/master/HungarianAlgorithm.java
     *
     * 2.如果是要求最佳匹配只是给每个人匹配到车，可以用PQ + Map :
     *   车比人多 enum出所有人车匹配并排序 后从小往大排序输出 用双set去重就好 不是难题
     *   所以这道题跟bfs/dfs没关系，就直接heap不停poll,然后用set来记录bike和people被visited 即可
     *
     *   P代表people，B代表bike，0就是过道，那么这种情况下就只需要enumerate出每一个B和每一P 的abs distance，
     *   放进priorityqueue即可。然后用一个char[][]来记录visited。这是我的看法。但如果出现了“1”作为obstacles，
     *   就不能直接abs了，因为有障碍物，这个时候我能想到的就是以P为中心用bfs来计算到每一个B的距离，然后再放进heap。
     *   不晓得我的想法是否正确。
     *   Editor: 对的
     *   有障碍物就不需要heap了吧？从车开始BFS就行了吧，当然前提是没有tie
     *
     * 原题：一组坐标表示人，另一组表示车，车比人多，给每个人匹配最近的车。其中人和车的距离没有tie。
     * 原题还比较简单，最笨的bfs也可以做，坐标数值很大的时候，时间复杂度可能会很高，稍微好一点的是用pq存所有的人车距离，
     * 每次poll最小的距离，如果这个人已经匹配到车了继续poll，直到所有人都匹配到车为止。
     *
     * follow up版本1:
     * 一组坐标表示人，另一组表示车，车比人多，其中人和车的距离有tie（距离两个人最近的车可能是同一辆），给每个人匹配一辆车，
     * 要求所有匹配的人车曼哈顿距离加起来最小（全局最优）。
     * 这一问原题的两种方法基本全部gg，因为要求全局最优并且有tie，于是每个人不一定是匹配到距离自己最近的车子。pq方法完全失效，
     * bfs方法无法保证全局最优（距离一个人最近的车可能有多辆，然而单凭bfs无法确定给此人匹配哪辆可以全剧最优）。
     * 暴力搜索全部匹配方式，找最小总距离的匹配方式可以确保正确性，但是车和人很多的时候，时间复杂度会很高。
     * 目前个人认为这一题面试官的期待做法，应该就是二分图最小带权匹配，KM算法，但是鉴于面试的时候可能很难写出，
     * 所以在此希望大家讨论一下有没有其他稍微简单点的办法，因为和正常的二分图匹配不一样，这个已经告诉你那些节点属于哪一边了。
     *
     * https://algs4.cs.princeton.edu/65reductions/Hungarian.java.html
     * http://www.hungarianalgorithm.com/examplehungarianalgorithm.php
     *
     * follow up版本2:
     * 一组坐标表示人，另一组表示车，车比人多，其中人和车的距离有tie（距离两个人最近的车可能是同一辆），给每个人匹配一辆车，
     * 要求匹配后最大的人车距离最小。
     * 这一问和前面的关系似乎不是很大，不过万能的暴力dfs还是能做，全部匹配方法写出来，找最长距离最小的那个，就是答案，
     * 不过和前面一样，没有非常有效的剪枝方法，复杂度很高，所以面试官也不会满意（我同学面试答了这种方法挂掉了）。感觉可以用dp来做，
     * 但是没有想出很好的状态表示和转移方程，希望大家讨论！！
     *
     *  二分？
     *  1。计算出距离矩阵dist[][], find min and max, 二分范围为min ~ max
     *  2. 二分，猜一个最大距离，如果在dist[][]里存在，看能否找到：
     *    a.所有其他人能配上车（出去这个最大距离的配对）
     *    b.其他配对距离<=当前猜的值。
     *  3。如果找到，可以尝试更小的值 (r = m)
     *     如果没找到，尝试更大的值 （l = m + 1)
     */

    /**
     * https://www.cnblogs.com/lightwindy/p/9808666.html
     *
     * 这道题很开放,需要跟面试官积极交流,厘清条件。
     * - 比如是人多还是自行车多?如果是人多、自行车少,那么从自行车出发做计算会比较高
     * 效。
     * - 如果出现相等距离怎么办?例如自行车a到两个人x、y的距离相同,match谁呢?
     *
     * 之后follow up是如果有的时候很多人到同一辆车距离相同,怎么assign,要求全局最优,
     *
     * 我理解的全剧最优就是让每个人走的路加起来和最小,这样找到一个解使得匹配之后所有人和车
     * 的距离之和是最短的。 可以用Greedy,每次都match到最短的pair,但这种未必能确保达到最优解。
     *
     * 或者可以用人为起点做了个bfs,然后得出<Node, distance>的preference list。然后做dfs,找出
     * 最短的distance之和。烙印说可以,不过没写代码。
     *
     * 我问他还有什么更好的解法吗,他说有个汉密尔顿什么什么的算法
     */
    class Element {
        int dist;
        int[] personLoc;
        int[] bicycleLoc;

        public Element(int dist, int[] p, int[] b) {
            this.dist = dist;
            this.personLoc = p;
            this.bicycleLoc = b;
        }
    }

    class Element1 {
        int dist;
        int personId;
        int bicycleId;

        public Element1(int dist, int p, int b) {
            this.dist = dist;
            this.personId = p;
            this.bicycleId = b;
        }
    }

    /**
     * 局部最优
     *
     * 实际上，如果只是求局部最优，根本没有必要用什么DFS/BFS，只要对每一个P，算出他到
     * 所有B的距离，放入pq, 就有了他的匹配的bike了。
     *
     * pq + set
     *
     * Time  : O(m * n * log (m * n))
     * Space : O(m * n)
     *
     * Follow up :
     * 如果加条件，用1代表obstacles ：
     * 只能对每个P, 用BFS找最近的bike.
     *
     */
    public List<Element> matching(char[][] map) {
        List<Element> res = new ArrayList<>();

        if (null == map || map.length == 0 || map[0].length == 0) {
            return res;
        }

        int m = map.length;
        int n = map[0].length;

        boolean[][] taken = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'P') {
                    PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);

                    /**
                     * !!!
                     */
                    boolean[][] visited = new boolean[m][n];

                    helper(map, pq, visited, new int[]{i, j}, new int[]{i, j});
                    while (!pq.isEmpty() && isTaken(pq, taken)) {
                        Element e = pq.poll();
                    }

                    if (!pq.isEmpty()) {
                        Element match = pq.peek();
                        res.add(match);
                        taken[match.bicycleLoc[0]][match.bicycleLoc[1]] = true;
                    } else {
                        throw new IllegalArgumentException("Not enough bicycles");
                    }
                }
            }
        }

        return res;
    }

    private void helper(char[][] map,  PriorityQueue<Element> pq, boolean[][] visited, int[] p, int[] cur) {
        int x = cur[0];
        int y = cur[1];
        int m = map.length;
        int n = map[0].length;

        if (x >= m || x < 0 || y >= n || y < 0) {
            return;
        }

        if (visited[x][y]) {
            return;
        }

        visited[cur[0]][cur[1]] = true;
        helper(map, pq, visited, p, new int[]{cur[0] + 1, cur[1]});
        helper(map, pq, visited, p, new int[]{cur[0] - 1, cur[1]});
        helper(map, pq, visited, p, new int[]{cur[0], cur[1] + 1});
        helper(map, pq, visited, p, new int[]{cur[0], cur[1] - 1});

//        System.out.println(map[cur[0]][cur[1]]);
        if (map[cur[0]][cur[1]] == 'B') {
            System.out.println("Bicycle at [" + cur[0] + "][" + cur[1] + "]");
            int dist = Math.abs(p[0] - cur[0]) + Math.abs(p[1] - cur[1]);
            pq.offer(new Element(dist, p, cur));
        }
    }

    private static boolean isTaken(PriorityQueue<Element> pq, boolean[][] taken) {
        Element e = pq.peek();
        return taken[e.bicycleLoc[0]][e.bicycleLoc[1]];
    }

    /**===============================**/

    public List<Element1> matching_hashmap_version(char[][] map) {
        List<Element1> res = new ArrayList<>();

        if (null == map || map.length == 0 || map[0].length == 0) {
            return res;
        }

        int m = map.length;
        int n = map[0].length;

        HashMap<Integer, Boolean> taken = new HashMap<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'P') {
                    PriorityQueue<Element1> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);

                    /**
                     * !!!
                     */
                    boolean[][] visited = new boolean[m][n];

                    helper_hashmap_version(map, pq, visited, new int[]{i, j}, new int[]{i, j});
                    
                    while (!pq.isEmpty() && isTaken_hashmap_version(pq, taken)) {
                        Element1 e = pq.poll();
                    }

                    if (!pq.isEmpty()) {
                        Element1 match = pq.peek();
                        res.add(match);
                        taken.put(match.bicycleId, true);
                    } else {
                        throw new IllegalArgumentException("Not enough bicycles");
                    }
                }
            }
        }

        return res;
    }

    private void helper_hashmap_version(char[][] map,  PriorityQueue<Element1> pq, boolean[][] visited, int[] p, int[] cur) {
        int x = cur[0];
        int y = cur[1];
        int m = map.length;
        int n = map[0].length;

        if (x >= m || x < 0 || y >= n || y < 0) {
            return;
        }

        if (visited[x][y]) {
            return;
        }

        visited[cur[0]][cur[1]] = true;
        helper_hashmap_version(map, pq, visited, p, new int[]{cur[0] + 1, cur[1]});
        helper_hashmap_version(map, pq, visited, p, new int[]{cur[0] - 1, cur[1]});
        helper_hashmap_version(map, pq, visited, p, new int[]{cur[0], cur[1] + 1});
        helper_hashmap_version(map, pq, visited, p, new int[]{cur[0], cur[1] - 1});

//        System.out.println(map[cur[0]][cur[1]]);
        if (map[cur[0]][cur[1]] == 'B') {
            System.out.println("Bicycle at [" + cur[0] + "][" + cur[1] + "]");
            int dist = Math.abs(p[0] - cur[0]) + Math.abs(p[1] - cur[1]);
            pq.offer(new Element1(dist, p[0] * n + p[1], cur[0] * n + cur[1]));

        }
    }

    private static boolean isTaken_hashmap_version(PriorityQueue<Element1> pq, HashMap<Integer, Boolean> taken) {
        Element1 e = pq.peek();
        return taken.getOrDefault(e.bicycleId, false);
    }

    /**===============================**/

    public List<Element1> matching_no_dfs(char[][] map) {
        List<Element1> res = new ArrayList<>();

        if (null == map || map.length == 0 || map[0].length == 0) {
            return res;
        }

        int m = map.length;
        int n = map[0].length;

        Set<Integer> bikes = new HashSet<>();
        Set<Integer> persons = new HashSet<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'P') {
                    persons.add(i * n + j);
                } else if (map[i][j] == 'B') {
                    bikes.add(i * n + j);
                }
            }
        }

        for (Integer person : persons)  {
            PriorityQueue<Element1> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
            calculateDist(pq, bikes, m, n, person);

            if (!pq.isEmpty()) {
                Element1 match = pq.peek();
                res.add(match);
                bikes.remove(match.bicycleId);
            } else {
                throw new IllegalArgumentException("Not enough bicycles");
            }
        }

        return res;
    }

    private void calculateDist(PriorityQueue<Element1> pq, Set<Integer> bikes, int m, int n, int pid) {
        int i = pid / n;
        int j = pid % n;
        for (Integer bike : bikes) {
            int dist = Math.abs(bike / n - i) + Math.abs(bike % n - j);
            pq.offer(new Element1(dist, pid, bike));
        }
    }

    /**===============================**/

    public static void main(String[] args) {
        char[][] map = {
                {'O','P', 'O', 'B', 'O', 'O', 'P'},
                {'O','O', 'O', 'O', 'O', 'O', 'O'},
                {'O','O', 'O', 'O', 'O', 'O', 'O'},
                {'O','O', 'O', 'O', 'O', 'O', 'O'},
                {'B','O', 'O', 'B', 'O', 'O', 'O'}
        };

        BicyblePeopleMatching test = new BicyblePeopleMatching();

//        List<Element> res = test.matching(map);
//        for (Element e : res) {
//            System.out.println("dist : " + e.dist + ", Person : " + Arrays.toString(e.personLoc)
//            + ", Bicycle : " + Arrays.toString(e.bicycleLoc));
//        }
//
//        List<Element1> res1 = test.matching_hashmap_version(map);
//        for (Element1 e : res1) {
//            System.out.println("dist : " + e.dist + ", Person : " + e.personId
//                    + ", Bicycle : " + e.bicycleId);
//        }

        List<Element1> res2 = test.matching_no_dfs(map);
        for (Element1 e : res2) {
            System.out.println("dist : " + e.dist + ", Person : " + e.personId
                    + ", Bicycle : " + e.bicycleId);
        }
    }
}
