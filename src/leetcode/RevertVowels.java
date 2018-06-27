package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 6/9/16.
 */
public class RevertVowels {
    public static String reverseVowels(String s) {
        if(s==null || s.length() < 2)
            return s;

        int start = 0;
        int end = s.length() - 1;
        char[] c = s.toLowerCase().toCharArray();

        while(start < end){
            while( !(c[start] == 'a' || c[start] == 'e' || c[start] == 'i' || c[start] == 'o' || c[start] == 'u')  && start < end ){
                start++;
            }

            System.out.println("start=" + start);

            while(!(c[end] == 'a' || c[end] == 'e' || c[end] == 'i' || c[end] == 'o' || c[end] == 'u')  && start < end ){
                end--;
            }
            System.out.println("end=" + end);


            char temp = c[start];
            c[start] = c[end];
            c[end] = temp;
            start++;
            end--;
        }

        return new String(c);
    }

    private static int[] maxValue(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[k];
        for (int i = 0, j = 0; i < n; i++) {
            /**
             if we don't have "n - i > k - j":

             Input:
             [6,7]
             [6,0,4]
             5

             Output:
             [7,6,4,0,0]
             Expected:
             [6,7,6,0,4]
             **/

            while (n - i > k - j && j > 0 && res[j - 1] < nums[i]) {
                System.out.println(j + "--");
                j--;
            }
            if (j < k) { //!!! "j < k"
                res[j++] = nums[i];
            }

            System.out.println(Arrays.toString(res));
        }

        return res;
    }

    public static void main(String [] args)
    {
//        String s="hello";
//
//        String s1 = reverseVowels(s);
//        System.out.println(s1);

        int[] input = {4, 3, 6, 2, 5, 3};
        maxValue(input, 3);
    }

    public int strStr(String haystack, String needle) {
        for (int i = 0; ; i++) {
            for (int j = 0; ; j++) {
                if (j == needle.length()) return i;
                if (i + j == haystack.length()) return -1;
                if (needle.charAt(j) != haystack.charAt(i + j)) break;
            }
        }
    }
}
