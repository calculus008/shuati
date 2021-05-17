package Interviews.Nextdoor;

public class Good_Tuples {
    /**
     * Given a list of integers, count the number of 'good tuples' that can be created.
     * A 'good tuple' is defined as consecutive triplets having exactly 2 duplicate elements.
     *
     * For eg.
     * nums = [4,4,6,1,2,2,2,3]
     * Here good tuples are: [4,4,6], [1,2,2], [2,2,3] because here in nums[i-1], nums[i], nums[i+1]
     * exactly 2 numbers are equal, however [2,2,2] isn't a good tuple because nums[i-1]==num[i]==nums[i+1].
     * Count of good tuples is 3.
     *
     * Another example:
     * nums = {4,6,4,1,3,4}
     * Here there is only one good tuple: [4,6,4]. Count of good tuples is 1.
     *
     * Note: I could come up with two pointers with sliding window solution for this problem,
     * however codesignal reported it as a poor solution. I am wondering if there is any better
     * approach with fewer comparisons, may be using XOR or any other data structure.
     */


    /**
     * nums[i-1] ==  nums[i] && nums[i] != nums[i+1]
     * nums[i-1] !=  nums[i] && nums[i] == nums[i+1]
     * nums[i-1] !=  nums[i] && nums[i] != nums[i+1]  && nums[i-1] ==  nums[i + 1]
     */

    public static int goodTuples(int[] a) {
        int res = 0;
        for(int i = 1; i < a.length - 1; i++) {
            res += check(a[i-1], a[i], a[i+1]);
        }
        return res;
    }

    public static int check(int a, int b, int c) {
        if(a == b && a != c) {
            return 1;
        } else if (a == c && a != b) {
            return 1;
        } else if (b == c && a != b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int countGoodTuple1(int[] nums) {
        int count = 0;
        for (int i = 1; i < nums.length -1; i++) {
            if ((nums[i-1] == nums[i] && nums[i] != nums[i+1]) ||
                    (nums[i-1] != nums[i] && nums[i] == nums[i+1]) ||
                    (nums[i-1] !=  nums[i] && nums[i] != nums[i+1]  && nums[i - 1] ==  nums[i + 1])) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
//        int[] nums = {4,4,6,1,2,2,2,3};
        int[] nums = {4,6,4,1,3,4};
        System.out.println(countGoodTuple1(nums));
    }
}
