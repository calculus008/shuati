package leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yuank on 2/15/18.
 */
public class LongestIncreaseSubsequence {
    public static int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        ArrayList<Integer> lis = new ArrayList<>();

        for (int n : nums) {
            if (lis.size() == 0 || n > lis.get(lis.size() - 1)) {
                lis.add(n);
            } else {
                int l = 0;
                int r = lis.size() - 1;

                System.out.println("l=" + l + ", r=" + r + ", n="+n);
                while (l < r) {
                    //!!!
                    int mid = l + (r - l) / 2;

                    System.out.println("mid="+mid);
                    if (lis.get(mid) < n) {
                        l = mid + 1;
                    } else {
                        r = mid;
                    }

                }
                System.out.println("l=" +l);

                lis.set(l, n);
            }
            System.out.println(Arrays.toString(lis.toArray()));
        }

        System.out.println(Arrays.toString(lis.toArray()));

        return lis.size();
    }

    public static int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int[] tails = new int[nums.length];

        tails[0] = nums[0];
        int len = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < tails[0]) {
                tails[0] = nums[i];
            } else if (nums[i] > tails[len]) {
                len++;
                tails[len] = nums[i];
            } else {
                int l = 0;
                int r = len;
                while (l < r) {
                    int mid = l + (r - l) / 2;
                    if (tails[mid] < nums[i]) {
                        l = mid + 1;
                    } else {
                        r = mid;
                    }
                }
                tails[l] = nums[i];
            }
        }

        return len + 1;
    }

    /*Get leetcode.LIS, not just the length
    * Key : both tails and parent array save the index, not the value itself.
     */
    public static int[] getLIS(int[] nums) {
        if (nums == null || nums.length == 0)
            return new int[]{};

        int[] tails = new int[nums.length];
        int[] parent = new int[nums.length];

        int len = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < tails[0]) {
                tails[0] = i;
            } else if (nums[i] >= nums[tails[len]]) {
                parent[i] = tails[len];
                len++;
                tails[len] = i;
            } else {
                int l = 0;
                int r = len;
                while (l < r) {
                    int mid = l + (r - l) / 2;
                    if (nums[tails[mid]] < nums[i]) {
                        l = mid + 1;
                    } else {
                        r = mid;
                    }
                }

                parent[i] = parent[tails[l]];
                tails[l] = i;
          }
            System.out.println(Arrays.toString(tails));
            System.out.println(Arrays.toString(parent));
            System.out.println("----");
        }

        System.out.println("len=" + len);

        int[] res = new int[len + 1];
        int k = tails[len];

        for (int i = len; i >= 0; i--) {
            res[i] = nums[k];
            k = parent[k];
        }

        System.out.println(Arrays.toString(res));
        return res;
    }

    public static void LIS(int nums[])
    {
        int parent[]= new int[nums.length]; //Tracking the predecessors/parents of elements of each subsequence.
        int tails[]= new int[nums.length + 1]; //Tracking ends of each increasing subsequence.
        int length = 0; //Length of longest subsequence.

        for(int i=0; i<nums.length; i++)
        {
            //Binary search
            int low = 1;
            int high = length;
            while(low <= high)
            {
                int mid = low + (high - low) / 2;

                if(nums[tails[mid]] < nums[i])
                    low = mid + 1;
                else
                    high = mid - 1;
            }

            int pos = low;

            System.out.println("low=" + low);
            System.out.println("set parent["+i+"] to " + "tails[" + pos+"-1], " + tails[pos-1]);
            //update parent/previous element for leetcode.LIS
            parent[i] = tails[pos-1];
            //Replace or append
            tails[pos] =  i;

            //Update the length of the longest subsequence.
            if(pos > length)
                length=pos;

            System.out.println(Arrays.toString(tails));
            System.out.println(Arrays.toString(parent));
        }

        //Generate leetcode.LIS by traversing parent array
        int LIS[] = new int[length];
        int k 	= tails[length];
        for(int j=length-1; j>=0; j--)
        {
            LIS[j] =  nums[k];
            k = parent[k];
        }


        for(int i=0; i<length; i++)
        {
            System.out.println(LIS[i]);
        }
    }

//    private static int binarySearch(int[] nums, int start, int end, int target) {
//        while (start + 1 < end) {
//            int mid = (end - start) / 2 + start;
//            if (nums[mid] == target) return mid;
//            if (nums[mid] < target) {
//                start = mid + 1;
//            } else {
//                end = mid - 1;
//            }
//        }
//        return start;
//    }

    private static int binarySearch(int[] tails, int l, int r, int target) {
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (tails[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }


        return l;
    }



        public static void main(String[] args) {
//        int[] input = {3, 1, 5, 2, 6, 4, 9, 10, 7};
        int[] input = {10, 9, 2, 5, 3, 7, 101, 18};

//        lengthOfLIS1(input);
//        System.out.println("res = " +lengthOfLIS2(input));
          getLIS(input);
//          leetcode.LIS(input);
    }
}
