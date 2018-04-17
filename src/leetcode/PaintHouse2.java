package leetcode;

/**
 * Created by yuank on 1/31/18.
 */
public class PaintHouse2 {

    public static int  minCostII(int[][] costs) {
        if (costs == null || costs.length == 0) return 0;

        int n = costs.length, k = costs[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                System.out.println("costs["+i+"]["+j+"]="+ costs[i][j]);
            }
        }
        // min1 is the index of the 1st-smallest cost till previous house
        // min2 is the index of the 2nd-smallest cost till previous house
        int min1 = -1, min2 = -1;

        for (int i = 0; i < n; i++) {
            System.out.println("i=" + i+ ", min1=" + min1 +", min2=" + min2);
            int last1 = min1, last2 = min2;
            min1 = -1; min2 = -1;


            //last1 and last2 is unchanged during the inner loop since it represent the min and second min cost for the last house, etc i-1th house,
            // from the last run of outer loop. When i=0, it is -1.

            //min1 and min2, you can't look at the original 2D arrays for min value except the first row, because we are using the input array and constantly update it, the values are all changed
            //For example:
            //i=2, min1=1, min2=2
            //here, row 2 (costs[1]) has bee changed to {10, 4, 6}, not the original {9, 3, 2}, after last iteration.

            for (int j = 0; j < k; j++) {
                System.out.println("  j="+j+", last1="+last1+", last2="+last2 + ", min1=" + min1 +", min2=" + min2 + " ,costs["+i+"]["+j+"]="+ costs[i][j]);

                //
                if (j != last1) {
                    // current color j is different to last min1
                    // " last1 < 0" : when i=0 (first house), no previous house, so it is 0.
                    costs[i][j] += last1 < 0 ? 0 : costs[i - 1][last1];
                } else {
                    System.out.println("  costs["+i+"]["+j+"]="+ costs[i][j]);

                    costs[i][j] += last2 < 0 ? 0 : costs[i - 1][last2];
                }

                System.out.println("  costs["+i+"]["+j+"]="+costs[i][j]);

                // find the indices of 1st and 2nd smallest cost of painting current house i
                if (min1 < 0 || costs[i][j] < costs[i][min1]) {
//                    System.out.println( "   min1=" + min1 +", min2=" + min2);
                    min2 = min1;
                    min1 = j;
//                    System.out.println( "   min1=" + min1 +", min2=" + min2);
                } else if (min2 < 0 || costs[i][j] < costs[i][min2]) {
                        min2 = j;
                }
            }
        }

        return costs[n - 1][min1];
    }

    public static int  minCostII2(int[][] costs) {
        if (costs == null || costs.length == 0) return 0;

        int n = costs.length, k = costs[0].length;
        // min1 is the index of the 1st-smallest cost till previous house
        // min2 is the index of the 2nd-smallest cost till previous house
        int min1 = -1, min2 = -1;
        int[][] dp = new int[costs.length][costs[0].length];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                System.out.println("costs["+i+"]["+j+"]="+ costs[i][j]);
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println("i=" + i+ ", min1=" + min1 +", min2=" + min2);
            int last1 = min1, last2 = min2;
            min1 = -1; min2 = -1;


            for (int j = 0; j < k; j++) {
                System.out.println("  j="+j+", last1="+last1+", last2="+last2 + ", min1=" + min1 +", min2=" + min2 + " ,costs["+i+"]["+j+"]="+ costs[i][j]);

                //
                if (j != last1) {
                    dp[i][j] = (last1 < 0 ? 0 : dp[i - 1][last1]) + costs[i][j];
                } else {
                    System.out.println("  dp[i - 1][last2]="+dp[i - 1][last2]+" ,costs["+i+"]["+j+"]="+ costs[i][j]);
                    dp[i][j] = (last2 < 0 ? 0 : dp[i - 1][last2]) + costs[i][j];
                }

                System.out.println("  dp["+i+"]["+j+"]="+dp[i][j]);

                if (min1 < 0 || dp[i][j] < dp[i][min1]) {
                    min2 = min1;
                    min1 = j;
                } else if(min2 < 0 || dp[i][j] < dp[i][min2]) {
                    min2 = j;
                }
            }
        }

        return dp[n - 1][min1];
    }

    public static void main(String [] args) {
//        int[][] costs = {
//                {10, 4, 1},
//                {9, 3, 2},
//                {11, 6, 5}
//        };

        int[][] costs = {
                {1,5,3},
                {2,9,4}
        };
//
//        System.out.println("Result = " + minCostII(costs));
//        System.out.println("-----------");
        System.out.println("Result = " + minCostII2(costs));
    }
}
