package leetcode;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_186_Reverse_Words_In_String_II {
    /*
        Given an input string, reverse the string word by word. A word is defined as a sequence of non-space characters.

        The input string does not contain leading or trailing spaces and the words are always separated by a single space.

        For example,
        Given s = "the sky is blue",
        return "blue is sky the".

        Could you do it in-place without allocating extra space?
     */

    //A simpler version of LE_151

    //Time : O(n), Space : O(1)
    public void reverseWords(char[] str) {
        if (str == null || str.length < 2) return;

        swap(str, 0, str.length - 1);

        int i = 0, j = 0;
        while (j < str.length) {
            while (j < str.length && str[j] != ' ') {
                j++;
            }
            swap(str, i, j - 1);
            j++;
            i = j;
        }
    }

    private void swap(char[] str, int i, int j) {
        while (i < j) {
            char temp = str[i];
            str[i++] = str[j];
            str[j--] = temp;
        }
    }
}