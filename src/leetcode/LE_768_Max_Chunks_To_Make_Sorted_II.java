package leetcode;

import java.util.*;

public class LE_768_Max_Chunks_To_Make_Sorted_II {
    /**
     * You are given an integer array arr.
     *
     * We split arr into some number of chunks (i.e., partitions), and individually sort each chunk.
     * After concatenating them, the result should equal the sorted array.
     *
     * Return the largest number of chunks we can make to sort the array.
     *
     * Example 1:
     * Input: arr = [5,4,3,2,1]
     * Output: 1
     * Explanation:
     * Splitting into two or more chunks will not return the required result.
     * For example, splitting into [5, 4], [3, 2, 1] will result in [4, 5, 1, 2, 3], which isn't sorted.
     *
     * Example 2:
     * Input: arr = [2,1,3,4,4]
     * Output: 4
     * Explanation:
     * We can split into two chunks, such as [2, 1], [3, 4, 4].
     * However, splitting into [2, 1], [3], [4], [4] is the highest number of chunks possible.
     *
     *
     * Constraints:
     *
     * 1 <= arr.length <= 2000
     * 0 <= arr[i] <= 108
     *
     * Hard
     *
     * https://leetcode.com/problems/max-chunks-to-make-sorted-ii
     */

    /**
     * 方法二：单调栈
     * 思路
     * 对于已经分好块的数组，若块数大于 1，则可以得到以下结论：右边的块的所有数字均大于或等于左边的块的所有数字。
     * 考虑这个问题：对于已经分好块的数组，若在其末尾添加一个数字，如何求得新数组的分块方式？
     *
     * 新添加的数字可能会改变原数组的分块方式。如果新添加的数字大于或等于原数组最后一个块的最大值，则这个新添加的数字可以自己形成一个块。
     * 如果新添加的数字小于原数组最后一个块的最大值，则它必须融入最后一个块。如果它大于或等于原数组倒数第二个块（如果有）的最大值，
     * 那么这个过程可以停止，新数组的分块方式已经求得。否则，它将继续融合原数组倒数第二个块，直到遇到一个块，使得该块的最大值小于或等于这个新添加的数，
     * 或者这个数字已经融合了所有块。
     *
     * 上述分析过程中，我们只用到了块的最大值来进行比较，比较过程又是从右到左，符合栈的思想，因此可以用类似单调栈的数据结构来存储块的最大值
     *
     * 复杂度分析
     * 时间复杂度： O(n)，其中n 是输入数组arr的长度。需要遍历一遍数组，入栈的操作最多为n次。
     * 空间复杂度： O(n)。栈的长度最多为n。
     */
    class Solution_mono_stack {
        public int maxChunksToSorted(int[] arr) {
            Deque<Integer> stack = new ArrayDeque<Integer>();
            for (int num : arr) {
                if (stack.isEmpty() || num >= stack.peek()) {
                    stack.push(num);
                } else {
                    int top = stack.pop();//!!!
                    while (!stack.isEmpty() && stack.peek() > num) {
                        stack.pop();
                    }
                    stack.push(top);//!!!
                }
            }
            return stack.size();
        }
    }

    /**
     * 方法一：排序 + 哈希表
     * 思路
     *
     * 记数组  arr 长度为  nn，排完序的数组为sortedArr。 首先，将原数组分为一块，肯定是可行的。原数组直接排序，
     * 和将它分为一块后再排序，得到的数组是相同的。那么，如何判断一个数组是否能分为符合题意的两块呢？如果一个数组能分为两块，
     * 那么一定能找到一个下标  kk，这个下标将数组分为两个非空子数组arr[0,…,k] 和 a r r [ k + 1 , … , n − 1 ]，
     * 使得  a r r [ 0 , … , k ] 和  s o r t e d A r r [ 0 , … , k ] 的元素频次相同，
     * a r r [ k + 1 , … , n − 1 ] 和  s o r t e d A r r [ k + 1 , … , n − 1 ] 的元素频次相同。
     * 判断能否分为更多的块时同理。这个判断过程可以从左至右同时遍历  arrarr 和  sortedArr，并用一个哈希表  cnt 来记录两个数组元素
     * 频次之差。当遍历到某个下标时，如果  cnt 内所有键的值均为  00，则表示划分出了一个新的块，最后记录有多少下标可以使得 cnt 内所有键的值
     * 均为0 即可。
     *
     * 复杂度分析
     * 时间复杂度： O(nlogn)，其中  nn 是输入数组  arr 的长度。排序需要消耗 O(nlogn) 的时间复杂度，遍历一遍消耗 (n) 的时间复杂度。
     * 空间复杂度： )O(n)。排序完的数组和哈希表均需要消耗  O(n) 的空间复杂度。
     */

    class Solution_hashmap {
        public int maxChunksToSorted(int[] arr) {
            Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
            int res = 0;
            int[] sortedArr = new int[arr.length];
            System.arraycopy(arr, 0, sortedArr, 0, arr.length);
            Arrays.sort(sortedArr);

            for (int i = 0; i < sortedArr.length; i++) {
                int x = arr[i], y = sortedArr[i];
                cnt.put(x, cnt.getOrDefault(x, 0) + 1);
                if (cnt.get(x) == 0) {
                    cnt.remove(x);
                }
                cnt.put(y, cnt.getOrDefault(y, 0) - 1);
                if (cnt.get(y) == 0) {
                    cnt.remove(y);
                }
                if (cnt.isEmpty()) {
                    res++;
                }
            }
            return res;
        }
    }


}
