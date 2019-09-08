package leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by yuank on 3/23/18.
 */
public class LE_179_Largest_Number {
    //LE_175 - LE_179 are SQL qestions

    /**
        Given a list of non negative integers, arrange them such that they form the largest number.

        For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.

        Note: The result may be very large, so you need to return a string instead of an integer.
     */

    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) return "0";

        String[] str = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            str[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(str, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));

//        Comparator<String> strComparator = (o1, o2) -> (o1 + o2).compareTo(o2 + o1);
//        Arrays.sort(str, strComparator.reversed());

        //!!! 注意， 要特殊处理这样的case - [0, 0, 0, 0], 输出不能是“0000”， 应该是“0”。
        //after sorting, the first elemnet is the largest one
        //it's given "a list of non negative integers", therefore just check the first element is enough to tell if the result is "0"
        if (str[0].equals("0")) return "0";

        return String.join("", str);
    }

    public String largestNumber_practice(int[] nums) {
        if (nums == null || nums.length == 0) return "0";

        String[] strs = new String[nums.length];

        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        /**
         * "9", "23" : "923" > "239"
         */
        Arrays.sort(strs, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));

        /**
         * strs[0] is String, use "equals", not "==" !!!
         */
        if (strs[0].equals("0")) return "0";

        /**
         * String.join() !!!
         */
        return String.join("", strs);
    }
}
