package leetcode;

/**
 * Created by yuank on 11/29/18.
 */
public class LE_748_Shortest_Completing_Word {
    /**
         Find the minimum length word from a given dictionary words, which has all the letters from the
         string licensePlate. Such a word is said to complete the given string licensePlate

         Here, for letters we ignore case. For example, "P" on the licensePlate still matches "p" on the word.

         It is guaranteed an answer exists. If there are multiple answers, return the one that occurs first in the array.

         The license plate might have the same letter occurring multiple times.
         For example, given a licensePlate of "PP", the word "pair" does not complete the licensePlate, but the word "supper" does.

         Example 1:
         Input: licensePlate = "1s3 PSt", words = ["step", "steps", "stripe", "stepple"]
         Output: "steps"
         Explanation: The smallest length word that contains the letters "S", "P", "S", and "T".
         Note that the answer is not "step", because the letter "s" must occur in the word twice.
         Also note that we ignored case for the purposes of comparing whether a letter exists in the word.

         Example 2:
         Input: licensePlate = "1s3 456", words = ["looks", "pest", "stew", "show"]
         Output: "pest"
         Explanation: There are 3 smallest length words that contains the letters "s".
         We return the one that occurred first.

         Note:
         licensePlate will be a string with length in range [1, 7].
         licensePlate will contain digits, spaces, or letters (uppercase or lowercase).
         words will have a length in the range [10, 1000].
         Every words[i] will consist of lowercase letters, and have length in range [1, 15].

         Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/string/leetcode-748-shortest-completing-word/
     *
     * HashMap + String
     *
     * Time  : O(n * 26) -> O(n)
     * Space : O(26) -> O(1)
     */
    public String shortestCompletingWord(String licensePlate, String[] words) {
        char[] c1 = licensePlate.toLowerCase().toCharArray();
        int[] m1 = new int[26];

        for (char c : c1) {
            if (Character.isLetter(c)) {
                m1[c - 'a']++;
            }
        }

        int minLen = Integer.MAX_VALUE;
        String res = "";
        for (String word : words) {
            int n = word.length();
            if (n >= minLen) {
                continue;
            }

            if (isValid(word, m1)) {
                minLen = n;
                res = word;
            }
        }

        return res;
    }

    private boolean isValid(String word, int[] m1) {
        char[] w = word.toLowerCase().toCharArray();
        int[] m2 = new int[26];

        for (char c: w) {
            m2[c - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (m2[i] < m1[i]) {
                return false;
            }
        }

        return true;
    }
}
