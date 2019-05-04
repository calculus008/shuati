package Interviews.Linkedin;

public class Lexicographical_Maximum_Substring_Of_String {
    /**
     * Given a string s we have to find the lexicographical maximum substring of a string
     *
     * Examples:
     *
     * Input : s = "ababaa"
     * Output : babaa
     * Explanation : "babaa" is the maximum lexicographic susbtring formed from this string
     *
     * Input : s = "asdfaa"
     * Output : sdfaa
     */

    /**
     * Time Complexity : O(n)
     * Space Complexity : O(n)
     *
     * Optimization :
     * We find largest character and all its indexes. Now we simply traverse through all
     * instances of the largest character to find lexicographically maximum substring.
     */


    public static String LexicographicalMaxString(String str) {
        // loop to find the max leicographic
        // substring in the substring array
        String mx = "";
        for (int i = 0; i < str.length(); ++i) {
            if (mx.compareTo(str.substring(i)) <= 0) {
                mx = str.substring(i);
            }
        }

        return mx;
    }

    // Driver code
    public static void main(String[] args) {
//        String str = "ababaa";
        String str = "asdfaa";
        System.out.println(LexicographicalMaxString(str));
    }
}


