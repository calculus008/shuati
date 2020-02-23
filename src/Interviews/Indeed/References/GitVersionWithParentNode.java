package Interviews.Indeed.References;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GitVersionWithParentNode {
	 public List<Integer> findCommits(GitNode root) {  
	        List<Integer> result = new ArrayList<>();
	        Set<GitNode> visited = new HashSet<>();
	        Queue<GitNode> queue = new LinkedList<>();
	         
	        findCommitsHelper(root, visited, queue, result);
	         
	        return result;
	    }
	     
	    private void findCommitsHelper(GitNode root, Set<GitNode> visited, 
	                                   Queue<GitNode> queue, List<Integer> result) {
	        if (root == null) {
	            return;
	        }
	         
	        queue.offer(root);
	         
	        while (!queue.isEmpty()) {
	            GitNode curr = queue.poll();
	            if (!visited.contains(curr)) {
	                visited.add(curr);
	                result.add(curr.val);
	                 
	                for (GitNode neighbor : curr.neighbors) {
	                    queue.offer(neighbor);
	                }
	            }
	        }
	    }
	 
	    public int findLCA(GitNode node1, GitNode node2) {
	        if (node1 == null || node2 == null) {
	            throw new NullPointerException();
	        }
	         
	        List<Integer> result1 = new ArrayList<>();
	        bfs(node1, result1);
	         
	        List<Integer> result2 = new ArrayList<>();
	        bfs(node2, result2);
	         
	        int i = result1.size() - 1;
	        int j = result2.size() - 1;
	         
	        for (; i >= 0 && j >= 0; i--, j--) {
	            if (result1.get(i) == result2.get(j)) {
	                continue;
	            } else {
	                break;
	            }
	        }
	         
	        return result1.get(i + 1);
	    }
	     
	    
	   

		private void bfs(GitNode root, List<Integer> result) {
	        if (root == null) {
	            return;
	        }
	         
	        Set<GitNode> visited = new HashSet<>();
	        Queue<GitNode> queue = new LinkedList<>();
	         
	        queue.offer(root);
	         
	        while (!queue.isEmpty()) {
	            GitNode curr = queue.poll();
	             
	            if (!visited.contains(curr)) {
	                result.add(curr.val);
	                visited.add(curr);
	                 
	                for (GitNode parent : curr.parents) {
	                    queue.offer(parent);
	                }
	            }
	        }
	    }
	     
	    public static void main(String[] args) {
	    	GitVersionWithParentNode sol = new GitVersionWithParentNode();
	        int[][] commits = new int[][]{{0, 1}, {1, 3}, {3, 5}, {0, 2}, {2, 4}, {4, 5}};
	         
	        GitNode node1 = null;
	        GitNode node2 = null;
	         
	        // step 1: constrcut the graph
	        Map<Integer, GitNode> map = new HashMap<>();
	         
	        for (int[] commit : commits) {
	            int from = commit[0];
	            int to = commit[1];
	             
	            GitNode fromNode = null;
	            GitNode toNode = null;
	            if (map.containsKey(from)) {
	                fromNode = map.get(from);
	            } else {
	                fromNode = new GitNode(from);
	            }
	             
	            if (map.containsKey(to)) {
	                toNode = map.get(to);
	                fromNode.neighbors.add(toNode);
	            } else {
	                toNode = new GitNode(to);
	                fromNode.neighbors.add(toNode);
	                map.put(to, toNode);
	            }
	             
	            toNode.parents.add(fromNode);
	            map.put(from, fromNode);
	            map.put(to, toNode);
	        }
	         
	        // Step 2: find out the root of the graph
	        GitNode root = null;
	        Map<GitNode, Integer> inDegree = new HashMap<>();
	        for (GitNode node : map.values()) {
	            if (!inDegree.containsKey(node)) {
	                inDegree.put(node, 0);
	            }
	             
	            for (GitNode neighbor : node.neighbors) {
	                if (inDegree.containsKey(neighbor)) {
	                    int degree = inDegree.get(neighbor);
	                    inDegree.put(neighbor, degree + 1);
	                } else {
	                    inDegree.put(neighbor, 1);
	                }
	            }
	        }
	         
	        for (GitNode node : inDegree.keySet()) {
	            if (inDegree.get(node) == 0) {
	                root = node;
	                break;
	            }
	        }
	         
	        System.out.println("Root is " + root.val);
	         
	        node1 = map.get(3);
	        node2 = map.get(4);
	         
	         
	        List<Integer> result = sol.findCommits(root);
	         
	        System.out.println("Node 1 is " + node1.val + " node 2 is " + node2.val);
	        int lca = sol.findLCA(node1, node2);
	         
	        System.out.println("LCA is " + lca);
	         
	        for (Integer elem : result) {
	            System.out.println(elem);
	        }
	    }
	     
	    static class GitNode {
	        int val;
	        List<GitNode> neighbors;
	        List<GitNode> parents;
	         
	        public GitNode(int val) {
	            this.val = val;
	            this.neighbors = new ArrayList<>();
	            this.parents = new ArrayList<>();
	        }
	    }
}
