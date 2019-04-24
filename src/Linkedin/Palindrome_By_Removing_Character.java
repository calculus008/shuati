package Linkedin;

import java.util.*;

public class Palindrome_By_Removing_Character {
    /**
     * Given a string, we need to check whether it is possible to make this
     * string a palindrome after removing exactly ONE character from this.
     *
     * Examples:
     *
     * Input  : str = “abcba”
     * Output : Yes
     * we can remove character ‘c’ to make string palindrome
     *
     * Input  : str = “abcbea”
     * Output : Yes
     * we can remove character ‘e’ to make string palindrome
     *
     * Input : str = “abecbea”
     * It is not possible to make this string palindrome
     * just by removing one character
     */

    /**
     * https://stackoverflow.com/questions/37401155/check-if-string-is-a-palindrome-after-removing-one-character-my-working-solutio
     *
     * An few examples of how this code works:
     *
     * A B C D E F E D C B A
     * |                   | portion that runs inside isPalindrome_ByRemoveOneChar
     *
     * 0 1 2 3 4 5 6 7 8 9
     * A B D E F E D C B A
     * | |             | |  portion that can be checked inside isPalindrome_ByRemoveOneChar
     *     |       |        isPalindrome(s , i , s.length() - i - 2)
     *       |       |      isPalindrome(s , i + 1 , s.length() - i - 1)
     *
     *
     * This code runs in-place - there are never made any copies of the input-string.
     * Runtime is O(n) (O(2 * n) to be precise).
     *
     * Time  : O(n)
     * Space : O(1)
     */

    public static boolean isPalindrome_ByRemoveOneChar(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt((s.length() - 1) - i)) {
                return isPalindrome(s, i + 1, s.length() - i - 1) ||
                        isPalindrome(s, i, s.length() - i - 2);
            }
        }

        return true;
    }



    public static String getPalindrome_ByRemoveOneChar(String s) {
        if (null == s) return "";
        System.out.println("========");
        System.out.println(s);

        System.out.println("len=" + s.length());

        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                System.out.println("i=" + i);

                //remove left char
                if ( isPalindrome(s, i + 1, s.length() - i - 1) ) {
                    System.out.println("1 -" + s.substring(0, i));
                    System.out.println("2 -" + s.substring(i + 1));
                    return s.substring(0, i) + s.substring(i + 1);
                }

                //remove right char
                if (isPalindrome(s, i, s.length() - i - 2)) {
                    System.out.println("1 -" + s.substring(0, s.length() - i - 1));
                    System.out.println("2 -" + s.substring(s.length() - i));


                    return s.substring(0, s.length() - i - 1) + s.substring(s.length() - i);
                }
            }
        }

        return "";
    }

    private static boolean isPalindrome(String s, int lower, int upper) {
        System.out.println("l=" + lower + ", h=" + upper);
        while (lower < upper) {
            if (s.charAt(lower) != s.charAt(upper)) {
                System.out.println("return false");
                return false;
            }

            lower++;
            upper--;
        }

        System.out.println("return true");

        return true;
    }

    public static void main(String[] args) {
        String s = "ABDEFEDCBA";
        String s1 = "ABDEEEEEBA";


        System.out.println("remove left : " + getPalindrome_ByRemoveOneChar(s1));

        System.out.println("remove right : " + getPalindrome_ByRemoveOneChar(s));

    }

}
