package leetcode;

/**
 * Created by yuank on 3/22/16.
 */
public class MinSubarray {
    public static int minSubArrayLen(int s, int[] nums) {
        int len = nums.length;

        if(len == 0)
            return 0;

        int start = 0;
        int end = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;
        int j = 1;

        while(end < len){
            while(end < len && sum < s){
                sum +=nums[end++];
                System.out.println("sum="+sum + ", end="+end);
            }

            if(sum < s) {
                System.out.println("break , start="+start + ", end="+end);
                break;
            }

            while(start < end && sum >= s){
                System.out.println("sum(" + sum + ") - nums[" +  start + "]("+nums[start]+")");
                sum -=nums[start++];
                System.out.println("start="+start);
            }

            if(minLen >  end - start + 1) {
                minLen = end - start + 1;
            }
            System.out.println("iteration " + j + ": start="+start + ", end="+end + " minLen="+minLen + " sum="+sum);
            j++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String [] args)
    {
        int test[] ={2,3,1,2,4,3};
        int s = 7;

        minSubArrayLen(s, test);
    }
}
