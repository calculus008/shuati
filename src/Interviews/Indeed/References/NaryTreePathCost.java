package Interviews.Indeed.References;

import java.util.ArrayList;
import java.util.List;

public class NaryTreePathCost {
	private static int minCost = Integer.MAX_VALUE;
    public List<Integer> findMinCostFromRootToLeaf(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
         
        List<Integer> curr = new ArrayList<>();
        curr.add(root.val);
        findMinCostHelper(root, root.val, curr, result);
         
        return result;
    }
     
     
    private void findMinCostHelper(TreeNode root, int cost, List<Integer> 
                                   curr, List<Integer> result) {
 
        if (root.children == null || root.children.length == 0) {
            if (cost < minCost) {
                minCost = cost;
                result.clear();
                result.addAll(curr);
            }
            return;
        }
         
        for (TreeNode child : root.children) {
            curr.add(child.val);
            findMinCostHelper(child, cost + child.val, curr, result);
            curr.remove(curr.size() - 1);
        }
    }
     
     
    public static void main(String[] args) {
    	NaryTreePathCost sol = new NaryTreePathCost();
         
        TreeNode root = new TreeNode(1, 2);
        root.children[0] = new TreeNode(2, 2);
        root.children[1] = new TreeNode(3, 3);
        root.children[0].children[0] = new TreeNode(4, 0);
        root.children[0].children[1] = new TreeNode(2, 0);
        root.children[1].children[0] = new TreeNode(3, 0);
        root.children[1].children[1] = new TreeNode(2, 0);
        root.children[1].children[2] = new TreeNode(0, 0);
         
        List<Integer> result = sol.findMinCostFromRootToLeaf(root);
         
        for (Integer e : result) {
            System.out.print(e + " ");
        }
         
        System.out.println("");
        System.out.println(minCost);
        List<Integer> result2 = sol.findMinCost(root);
        for (Integer e : result2) {
            System.out.print(e + " ");
        }
         
       
    }
     public  List<Integer> findMinCost(TreeNode root){
    	 ResultWrapper result = new ResultWrapper();
    	 if(root == null){
    		 return result.path;
    	 }
    	 ResultWrapper current =  new ResultWrapper();
    	 current.path.add(root.val);
    	 current.cost = root.val;
    	 helper(root,result,current);
    	 System.out.println("");
         System.out.println(result.cost);
    	 return result.path;
     }
    
   private void helper(TreeNode root, ResultWrapper result, ResultWrapper current) {
		if(root.children == null || root.children.length == 0){
			if(result.cost > current.cost){
				result.cost = current.cost;
				result.path = new ArrayList<Integer>(current.path);
			}
			return;
		}
		TreeNode[] children = root.children;
		for(TreeNode node : children){
			current.path.add(node.val);
			current.cost += node.val;
			helper(node,result,current);
			current.path.remove(current.path.size()-1);
			current.cost -= node.val;
		}
	}

class ResultWrapper { 
	   List<Integer> path;
	   int cost;
	   public ResultWrapper(){
		   path = new ArrayList<Integer>();
		   cost = Integer.MAX_VALUE;
	   }
   }
    static class TreeNode {
        int val;
        TreeNode[] children;
         
        public TreeNode(int val, int n) {
            this.val = val;
            this.children = new TreeNode[n];
        }
    }
}
