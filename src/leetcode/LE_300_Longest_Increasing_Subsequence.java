package leetcode;

import java.util.ArrayList;

/**
 * Created by yuank on 4/22/18.
 */
public class LE_300_Longest_Increasing_Subsequence {
    /**
         Given an unsorted array of integers, find the length of longest increasing subsequence.

         For example,
         Given [10, 9, 2, 5, 3, 7, 101, 18],
         The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4.
         Note that there may be more than one LIS combination, it is only necessary for you to return the length.

         Your algorithm should run in O(n2) complexity.

         Follow up: Could you improve it to O(n log n) time complexity?

         Medium
     */

    /**
     Time :  O(nlogn)
     Space : O(n)

     https://segmentfault.com/a/1190000003819886

     思路
     在1,3,5,2,8,4,6这个例子中，当到6时，我们一共可以有四种
     (1)不同长度
     (2)且保证该升序序列在同长度升序序列中末尾最小
     的升序序列

     1
     1,2
     1,3,4
     1,3,5,6
     这些序列都是未来有可能成为最长序列的候选人。这样，每来一个新的数，我们便按照以下规则更新这些序列

     1.如果nums[i]比所有序列的末尾都大，或等于最大末尾，说明有一个新的不同长度序列产生，我们把最长的序列复制一个，并加上这个nums[i]。

     2.如果nums[i]比所有序列的末尾都小，说明长度为1的序列可以更新了，更新为这个更小的末尾。

     3.如果在中间，则更新那个末尾数字刚刚大于等于自己的那个序列，说明那个长度的序列可以更新了。

     比如这时，如果再来一个9，那就是第1种情况，更新序列为

     1
     1,2
     1,3,4
     1,3,5,6
     1,3,5,6,9

     如果再来一个0，那就是第2种情况，更新序列为

     0
     1,2
     1,3,3
     1,3,5,6

     如果再来一个3，那就是第3种情况，更新序列为

     1
     1,2
     1,3,3
     1,3,5,6


     前两种都很好处理，O(1)就能解决，主要是第三种情况，实际上我们观察直到6之前这四个不同长度的升序序列，他们末尾是递增的，所以可以用二分搜索来找到适合的更新位置。
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int[] tails = new int[nums.length];

        tails[0] = nums[0];
        int tailIdx = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < tails[0]) {//#2
                tails[0] = nums[i];
            } else if (nums[i] > tails[tailIdx]) {//#1
                tailIdx++;
                tails[tailIdx] = nums[i];
            } else {//#3
                int l = 0;
                int r = tailIdx;
                //!!!
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

        return tailIdx + 1;
    }

    //Solution 2, use list instead of array
    public int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        ArrayList<Integer> lis = new ArrayList<>();

        for (int n : nums) {
            if (lis.size() == 0 || n > lis.get(lis.size() - 1)) {
                lis.add(n);
            } else {
                int l = 0;
                int r = lis.size() - 1;

                while (l < r) {
                    int mid = l + (r - l) / 2;

                    if (lis.get(mid) < n) {
                        l = mid + 1;
                    } else {
                        r = mid;
                    }
                }

                lis.set(r, n);
            }
        }

        return lis.size();
    }
}