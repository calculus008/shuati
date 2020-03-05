package Interviews.Indeed.References;

import java.util.HashMap;
import java.util.Map;
/**
 写一个函数float sumPossibility(int dice, int target)，就是投dice个骰子，求最后和为target的概率。因为总共的可能性是6^dice，所以其实就是combination sum，
 求dice个骰子有多少种组合，使其和为target。先用brute force的dfs来一个O(6^dice)指数复杂度的，然后要求优化，用dp，最后结束代码写的是两者结合的memorized search吧
 ，面试官走的时候还说了句such a good solution。

Solution:A classic backpack problem. Using DP would solve the problem.
 */
public class DiceSum {
	int count;
	int totaldfs;
	Map<String,Integer> cache = new HashMap<>();
	Map<String,Integer> dfsMap = new HashMap<>();
	public float sumPossibilityButtomUpDP(int dice, int target) {
		int total = (int) Math.pow(6,dice);

		int[][] dp = new int[dice+1][target+1];
		for(int i = 1; i <= Math.min(target,6); i++) {
			  dp[1][i] = 1;
		}
		for (int i = 2; i <= dice; i++)
	      for (int j = 1; j <= target; j++)
	          for (int k = 1; k <= 6 && k < j; k++) 
	            	dp[i][j] += dp[i-1][j-k];
	            
		return (float)dp[dice][target]/total;
	}
	
	public float sumPossibilityTopDownDP(int dice, int target) {
		int total = (int) Math.pow(6,dice);
		int res = helperTopDownDP(target,dice);
		return (float)res/total;
	}
	
	public int helperTopDownDP(int target, int dice) {
		if(dice == 0) {
			return target == 0 ? 1:0;
		}
		if(cache.containsKey(String.valueOf(dice) + "*" + String.valueOf(target))) {
			return cache.get(String.valueOf(dice) + "*" + String.valueOf(target));
		}
		int res = 0;
		for(int k = 1; k<=6; k++) {
			int tmp = helperTopDownDP(target-k,dice-1);
			res+=tmp;
		}
		cache.put(String.valueOf(dice) + "*" + String.valueOf(target), res);
		return res;
	}
	
	public float sumPossibility(int dice, int target) {
		int total = (int) Math.pow(6,dice);
		 helper(target,0,dice, 0);
		return (float)count/total;
	}
	public float sumWithDFS(int dice,int target) {
		int total = (int) Math.pow(6,dice);
		 dfs(dice, target);
		return (float)totaldfs/total;
	}
	public void helper(int target, int trial, int dice, int sum) {
		if(trial == dice) {
			if(target == sum)
				count++;
			return;
		}
		for(int i = 1; i<=6; i++) {
			helper(target, trial+1, dice, sum+i);
		}
	}
	
	
	
	public void dfs(int dice, int target) {
		if(target == 0 && dice == 0) {
			totaldfs++;
			return;
		}
		if(dice == 0 || target <= 0){
			return;
		}
		for(int i=1; i<=6;i++){
			dfs(dice-1,target-i);
		}
	}
	
	public   float sumwithDFSMap(int dice, int target){
		int totalsol = dfsMapApp(dice,target);
		int total = (int) Math.pow(6,dice);
		return (float)totalsol/total;
	}
	private int dfsMapApp(int dice, int target) {
		 if(6*dice < target){
			 return 0;
		 }
		if(dice == 0 ) {
			return target ==0?1:0;
		}
		if(target<=0){
			return 0;
		}
		String  key =  dice+"*"+ target;
		
		if(dfsMap.containsKey(key)){
			System.out.println(key);
				return dfsMap.get(key);
		}
		int result = 0;
		for(int i = 1; i <= 6;i++){
			int temp = dfsMapApp(dice-1,target-i);
			result+=temp;
		}
		dfsMap.put(key, result);
		return result;
	}
  
	public static void main(String[] args) {
		DiceSum dc = new DiceSum();
		System.out.println(dc.sumPossibility(10,35));
		System.out.println(dc.sumPossibilityTopDownDP(10,35));
		System.out.println(dc.sumPossibilityButtomUpDP(10,25));
		System.out.println(dc.sumWithDFS(10,25));
		System.out.println(dc.sumwithDFSMap(10,25));
		System.out.println(dc.sumwithDFSMap(10,35));
		
	}
}
