package Interviews.Indeed.References;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * git commit 的题，也是面经题。第一问给一个 commit（node），BFS 输出所有 commits
 * (nodes)。第二问，两个 commits （nodes），找到他们的最近的公共 parent，就是先 BFS 一个，然后
 * 用 map 记录下其各个 parent 到这个 commit(node)的距离，然后 BFS 第二个 commit(node)，碰到在
 * map 里的 node，就算一个总距离，然后更新最短距离和的点，最后最短距离和的点就是结果了，写完
 * 面试官也表示很满意。这个注意解释下 BFS 的复杂度为什么是 O（V+E），他会问为什么不是 O(V)之
 * 类的。
 *
 * git version。找到全部 commits，让实现 bfs。然后让找两个 commit 最早的公共 commit，先
 * bfs 搜索其中一个 commit 的所有 ancestor，用 hashmap 存一下，然后 bfs 搜索第二个 commit 的祖先
 * 。这里有两个地方可以提前结束搜索，提出来应该很好。-go
 */

public class GitVersion {
	 public List<Integer> findCommits(GraphNode root) {  
	        List<Integer> result = new ArrayList<>();
	        Set<GraphNode> visited = new HashSet<>();
	        Queue<GraphNode> queue = new LinkedList<>();
	         
	        findCommitsHelper(root, visited, queue, result);
	         
	        return result;
	    }
	     
	    private void findCommitsHelper(GraphNode root, Set<GraphNode> visited, 
	                                   Queue<GraphNode> queue, List<Integer> result) {
	        if (root == null) {
	            return;
	        }
	         
	        queue.offer(root);
	         
	        while (!queue.isEmpty()) {
	            GraphNode curr = queue.poll();
	            if (!visited.contains(curr)) {
	                visited.add(curr);
	                result.add(curr.val);
	                 
	                for (GraphNode neighbor : curr.neighbors) {
	                    queue.offer(neighbor);
	                }
	            }
	        }
	    }
	     
	    private GraphNode buildGraph(int[][] commits) {
	          if (commits == null || commits.length == 0) {
	            return null;
	        }
	         
	        // step 1: constrcut the graph
	        Map<Integer, GraphNode> map = new HashMap<>();
	         
	        for (int[] commit : commits) {
	            int from = commit[0];
	            int to = commit[1];
	             
	            GraphNode fromNode = null;
	            if (map.containsKey(from)) {
	                fromNode = map.get(from);
	            } else {
	                fromNode = new GraphNode(from);
	            }
	             
	            if (map.containsKey(to)) {
	                GraphNode toNode = map.get(to);
	                fromNode.neighbors.add(toNode);
	                 
	            } else {
	                GraphNode toNode = new GraphNode(to);
	                fromNode.neighbors.add(toNode);
	                map.put(to, toNode);
	            }
	             
	            map.put(from, fromNode);
	        }
	         
	        // Step 2: find out the root of the graph
	        GraphNode root = null;
	        Map<GraphNode, Integer> inDegree = new HashMap<>();
	        for (GraphNode node : map.values()) {
	            if (!inDegree.containsKey(node)) {
	                inDegree.put(node, 0);
	            }
	             
	            for (GraphNode neighbor : node.neighbors) {
	                if (inDegree.containsKey(neighbor)) {
	                    int degree = inDegree.get(neighbor);
	                    inDegree.put(neighbor, degree + 1);
	                } else {
	                    inDegree.put(neighbor, 1);
	                }
	            }
	        }
	         
	        for (GraphNode node : inDegree.keySet()) {
	            if (inDegree.get(node) == 0) {
	                root = node;
	                break;
	            }
	        }
	         
	        System.out.println("Root is " + root.val);
	         
	        return root;
	    }
	     
	    public static void main(String[] args) {
	    	GitVersion sol = new GitVersion();
	        int[][] commits = new int[][]{{0, 1}, {1, 3}, {3, 5}, {0, 2}, {2, 4}, {4, 5}};
	         
	        GraphNode root = sol.buildGraph(commits);
	         
	        List<Integer> result = sol.findCommits(root);
	         
	        for (Integer elem : result) {
	            System.out.println(elem);
	        }
	    }
	     
	    class GraphNode {
	        int val;
	        List<GraphNode> neighbors;
	        GraphNode parent;
	         
	        public GraphNode(int val) {
	            this.val = val;
	            this.neighbors = new ArrayList<>();
	        }
	    }
}
