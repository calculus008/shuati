package leetcode;

/**
 * Created by yuank on 4/16/18.
 */
public class LE_278_First_Bad_Version {
    /**
     * You are a product manager and currently leading a team to develop a new product. Unfortunately,
     * the latest version of your product fails the quality check. Since each version is developed based on the
     * previous version, all the versions after a bad version are also bad.

         Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.

         You are given an API bool isBadVersion(version) which will return whether version is bad.
         Implement a function to find the first bad version. You should minimize the number of calls to the API.
     */

    //Binary Search, Time : O(logn), Space : O(1)
    public int firstBadVersion1(int n) {
        int start = 1;
        int end = n;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (isBadVersion(start)) {
            return start;
        }

        return end;
    }

    //dummy method to remove compiler error
    boolean isBadVersion(int n) {
        return true;
    }

    /**
     * Use Huahua's template
     */
    public int firstBadVersion2(int n) {
        int l = 1;
        int r = n;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (isBadVersion(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }
}
