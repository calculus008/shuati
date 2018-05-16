package leetcode;

/**
 * Created by yuank on 5/15/18.
 */
public class LE_344_Reverse_String {
    /**
         Write a function that takes a string as input and returns the string reversed.

         Example:
         Given s = "hello", return "olleh".

         Easy
     */
    public String reverseString(String s) {
        if(null == s || s.length() == 0) return "";

        int i=0;
        int j = s.length() - 1;
        char[] ch = s.toCharArray();

        while(i<j) {
            char temp = ch[i];
            ch[i] = ch[j];
            ch[j] = temp;
            i++;
            j--;
        }

        return String.valueOf(ch);
    }
}
