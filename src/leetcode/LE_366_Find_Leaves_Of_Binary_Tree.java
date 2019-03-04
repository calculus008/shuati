package leetcode;

import common.TreeNode;

import java.util.*;

/**
 * Created by yuank on 7/28/18.
 */
public class LE_366_Find_Leaves_Of_Binary_Tree {
    /**
         Given a binary tree, collect a tree's nodes as if you were doing this:
         Collect and remove all leaves, repeat until the tree is empty.

         Example:
         Given binary tree
             1
            / \
           2   3
          / \
         4   5
         Returns [4, 5, 3], [2], [1].

         Explanation:
         1. Removing the leaves [4, 5, 3] would result in this tree:

           1
          /
         2

         2. Now removing the leaf [2] would result in this tree:

         1

         3. Now removing the leaf [1] would result in the empty tree:

         []
         Returns [4, 5, 3], [2], [1].

         Medium
     */

    class Solution1 {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }

            helper(root, res);
            return res;
        }


        /**
         * helper() 所求是树的高度
         */
        public int helper(TreeNode root, List<List<Integer>> res) {
            if (root == null) return -1;

            int left = helper(root.left, res);
            int right = helper(root.right, res);

            /**
             * 求出每个Node的height就是它在list中相对应的位置
             *
             * 这是这道题的关键，实际上，这里的level是reverse height,
             * 也就是，从叶子节点往上，依次返回 0, 1, 2..., 所以它可以被
             * 用作res里的下标。
             */
            int level = Math.max(left, right) + 1;

            /**
             * common trick for using DFS on level related problem
             */
            if (res.size() == level) {
                res.add(new ArrayList<>());
            }

            /**
             * DFS的性质保证叶子节点被一层层移除
             */
            res.get(level).add(root.val);
            /**
             * !!!
             * 题意要求是remove, 所以在postorder的最后一步把左右指针设为零
             **/
            root.left = null;
            root.right = null;

            /**
             * level returned : 0, 1, 2,...., it is the index in res.
             **/
            return level;
        }
    }


    /**
     * It seems this solution only collects the leaf nodes, not doing removal.
     */
    class Solution2 {
        public List<List<Integer>> findLeaves2_JiuZhang(TreeNode root) {
            List<List<Integer>> ans = new ArrayList<>();

            Map<Integer, List<Integer>> depth = new HashMap<>();
            int max_depth = dfs(root, depth);

            for (int i = 1; i <= max_depth; i++) {
                ans.add(depth.get(i));
            }
            return ans;
        }


        /**
         * This method is modified from finding depth of a binary tree,
         * while it finds depth, it also saves leaves list of each level in a map
         */
        int dfs(TreeNode cur, Map<Integer, List<Integer>> depth) {
            if (cur == null) {
                return 0;
            }
            int d = Math.max(dfs(cur.left, depth), dfs(cur.right, depth)) + 1;

            /**
             * Map depth : key - current level or height, value - list of leaves nodes.
             */
            depth.putIfAbsent(d, new ArrayList<>());
            depth.get(d).add(cur.val);

            //removal
            cur.left = null;
            cur.right = null;

            return d;
        }
    }

    class Solution_Practice {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }

            helper(root, res);
            return res;
        }

        /**
         * 这个helper()实际上就是用来找tree的。
         */
        private int helper(TreeNode root, List<List<Integer>> res) {
            if (root == null) {
                return -1;//!!!
            }

            int left = helper(root.left, res);
            int right = helper(root.right, res);

            int level = Math.max(left, right) + 1;

            if (res.size() == level) {
                res.add(new ArrayList<>());
            }
            res.get(level).add(root.val);

            return level;
        }
    }

    /**
     * 变形题，把Tree变成Graph, 求同样的叶子借天输出。
     * 可以用以下Graph 里DFS function.
     *
     * https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
     *
     * http://www.algolist.net/Algorithms/Graph/Undirected/Depth-first_search
     */
    class Graph {
        private int V;   // No. of vertices

        // Array  of lists for Adjacency List Representation
        private LinkedList<Integer> adj[];

        // Constructor
        Graph(int v)
        {
            V = v;
            adj = new LinkedList[v];
            for (int i=0; i<v; ++i) {
                adj[i] = new LinkedList();
            }
        }

        //Function to add an edge into the graph
        void addEdge(int v, int w)
        {
            adj[v].add(w);  // Add w to v's list.
        }

        // A function used by DFS
        void DFSUtil(int v,boolean visited[])
        {
            // Mark the current node as visited and print it
            visited[v] = true;
            System.out.print(v+" ");

            // Recur for all the vertices adjacent to this vertex
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                    DFSUtil(n, visited);
            }
        }

        // The function to do DFS traversal. It uses recursive DFSUtil()
        void DFS(int v)
        {
            // Mark all the vertices as not visited(set as
            // false by default in java)
            boolean visited[] = new boolean[V];

            // Call the recursive helper function to print DFS traversal
            DFSUtil(v, visited);
        }

//        public static void main(String args[])
//        {
//            Graph g = new Graph(4);
//
//            g.addEdge(0, 1);
//            g.addEdge(0, 2);
//            g.addEdge(1, 2);
//            g.addEdge(2, 0);
//            g.addEdge(2, 3);
//            g.addEdge(3, 3);
//
//            System.out.println("Following is Depth First Traversal "+
//                    "(starting from vertex 2)");
//
//            g.DFS(2);
//        }
    }
}
