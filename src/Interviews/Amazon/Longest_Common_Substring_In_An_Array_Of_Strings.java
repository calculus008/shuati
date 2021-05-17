package Interviews.Amazon;

public class Longest_Common_Substring_In_An_Array_Of_Strings {
    /**
     * https://www.geeksforgeeks.org/longest-common-substring-array-strings/
     *
     * We are given a list of words sharing a common stem i.e the words originate
     * from same word for ex: the words sadness, sadly and sad all originate from
     * the stem ‘sad’.
     *
     * Our task is to find and return the Longest Common Substring also known as
     * stem of those words. In case there are ties, we choose the smallest one in
     * alphabetical order.
     *
     * Examples:
     *
     * Input : grace graceful disgraceful gracefully
     * Output : grace
     *
     * Input : sadness sad sadly
     * Output : sad
     */

    /**
     * The idea is to take any word from the list as reference and form all its
     * substrings and iterate over the entire list checking if the generated
     * substring occurs in all of them.
     */

    // function to find the stem (longest common
    // substring) from the string  array
    public static String findstem(String arr[]) {
        // Determine size of the array
        int n = arr.length;

        // Take first word from array as reference
        String s = arr[0];
        int len = s.length();

        String res = "";

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                // generating all possible substrings
                // of our reference string arr[0] i.e s
                String stem = s.substring(i, j);
                int k = 1;

                for (k = 1; k < n; k++) {
                    // Check if the generated stem is
                    // common to all words
                    if (!arr[k].contains(stem))
                        break;
                }

                // If current substring is present in
                // all strings and its length is greater
                // than current result
                if (k == n && res.length() < stem.length()) {
                    res = stem;
                }
            }
        }

        return res;
    }

    // Driver Code
    public static void main(String args[]) {
        String arr[] = {"grace", "graceful", "disgraceful",
                "gracefully"};
        String stems = findstem(arr);
        System.out.println(stems);
    }
}
