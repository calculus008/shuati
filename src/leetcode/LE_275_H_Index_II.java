package leetcode;

/**
 * Created by yuank on 4/16/18.
 */
public class LE_275_H_Index_II {
    /**
     * Follow up for H-Index: What if the citations array is sorted in ascending order? Could you optimize your algorithm?
     */

    //Time : O(logn)
    //https://leetcode.com/problems/h-index/solution/, understand it as finding the largest square in the histogram chart
    public int hIndex(int[] citations) {
        int len = citations.length;
        int start = 0, end = len - 1;

        while (start <= end) {
            int mid = (end - start) / 2 + start;
            if (citations[mid] == len - mid) {
                return len - mid;
            } else if (citations[mid] < len - mid) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return len - start;
    }
}
