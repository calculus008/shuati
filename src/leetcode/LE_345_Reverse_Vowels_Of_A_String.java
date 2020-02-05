package leetcode;

/**
 * Created by yuank on 5/15/18.
 */
public class LE_345_Reverse_Vowels_Of_A_String {
    /**
         Write a function that takes a string as input and reverse only the vowels of a string.

         Example 1:
         Given s = "hello", return "holle".

         Example 2:
         Given s = "leetcode", return "leotcede".

         Easy
     */

    /**
     * 2 pointers, Time and Space O(n)
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        if (s == null || s.length() == 0) return "";

        String vowels = "aeiouAEIOU";
        int start = 0;
        int end = s.length() - 1;
        char[] chars = s.toCharArray();

        while (start < end) {
            while (start < end && !isVowel(chars[start])) {
                start++;
            }

            while (start < end && !isVowel(chars[end])) {
                end--;
            }

            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;

            start++;
            end--;
        }

        return new String(chars);
    }

    private boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    class Solution_Practice {
        public String reverseVowels(String s) {
            if (s == null || s.length() == 0) return s;

            char[] chs = s.toCharArray();
            int n = chs.length;

            int i = 0, j = n - 1;

            /**
             * !!!
             * " i < j" !!!
             */
            while (i < j) {
                while (i < j && !isVowel(chs[i])) i++;
                while (i < j && !isVowel(chs[j])) j--;

                char temp = chs[i];
                chs[i] = chs[j];
                chs[j] = temp;

                i++;
                j--;
            }

            return new String(chs);
        }

        private boolean isVowel(char input) {
            char c = Character.toLowerCase(input);
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }
    }
}
