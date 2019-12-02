package leetcode;

public class LE_557_Reverse_Words_In_A_String_III {
    /**
     * Given a string, you need to reverse the order of characters in each word within a sentence
     * while still preserving whitespace and initial word order.
     *
     * Example 1:
     * Input: "Let's take LeetCode contest"
     * Output: "s'teL ekat edoCteeL tsetnoc"
     *
     * Note: In the string, each word is separated by single space and there will not be any extra
     * space in the string.
     *
     * Easy
     */

    public class Solution1 {
        public String reverseWords(String s) {
            String[] words = s.split(" ");
            StringBuilder sb = new StringBuilder();
            for(String word : words) {
                sb.append(new StringBuffer(word).reverse() + " ");
            }

            return sb.toString().trim();
        }
    }

    /**
     * Simplified version of LE_151_Reverse_Words_In_A_String
     * Almost the same as LE_186_Reverse_Words_In_String_II
     */
    class Solution2 {
        public String reverseWords(String s) {
            if (s == null || s.length() == 0) return s;

            char[] ch = s.toCharArray();
            int n = ch.length;

            int l = 0, r = 0;
            while (r < n) {
                while (r < n && ch[r] != ' ') r++;
                reverse(ch, l, r - 1);
                r++;
                l = r;
            }

            return new String(ch);
        }

        private void reverse(char[] ch, int l, int r){
            while (l < r) {
                char temp = ch[l];
                ch[l] = ch[r];
                ch[r] = temp;
                l++;
                r--;
            }
        }
    }
}
