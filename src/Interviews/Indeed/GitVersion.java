package Interviews.Indeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
