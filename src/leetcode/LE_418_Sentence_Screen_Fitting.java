package leetcode;

public class LE_418_Sentence_Screen_Fitting {
    /**
     * Given a rows x cols screen and a sentence represented by a list of non-empty words,
     * find how many times the given sentence can be fitted on the screen.
     *
     * Note:
     *
     * A word cannot be split into two lines.
     * The order of words in the sentence must remain unchanged.
     * Two consecutive words in a line must be separated by a single space.
     * Total words in the sentence won't exceed 100.
     * Length of each word is greater than 0 and won't exceed 10.
     * 1 ≤ rows, cols ≤ 20,000.
     *
     * Example 1:
     * Input:
     * rows = 2, cols = 8, sentence = ["hello", "world"]
     *
     * Output:
     * 1
     *
     * Explanation:
     * hello---
     * world---
     *
     * The character '-' signifies an empty space on the screen.
     *
     * Example 2:
     * Input:
     * rows = 3, cols = 6, sentence = ["a", "bcd", "e"]
     *
     * Output:
     * 2
     *
     * Explanation:
     * a-bcd-
     * e-a---
     * bcd-e-
     *
     * The character '-' signifies an empty space on the screen.
     *
     * Example 3:
     * Input:
     * rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]
     *
     * Output:
     * 1
     *
     * Explanation:
     * I-had
     * apple
     * pie-I
     * had--
     *
     * The character '-' signifies an empty space on the screen.
     *
     * Medium
     */

    /**
     * This looks similar to LE_68_Text_Justification, but it only asks for how many times the sentence can be shown
     * on the given screen, not ask for the exact display on screen, therefore it is relatively simpler.
     *
     * The idea is : imaging we have a string with unlimited length, in which the sentences concatenated together, move the
     * pointer in this string, adjust the position of the pointer base on if it can fit in each row. Then in the end,
     * the answer is pointer index divided by length of the sentence.
     *
     * https://leetcode.com/problems/sentence-screen-fitting/discuss/90845/21ms-18-lines-Java-solution
     *
     * Say sentence=["abc", "de", "f], rows=4, and cols=6.
     * The screen should look like
     *
     * "abc de"
     * "f abc "
     * "de f  "
     * "abc de"
     *
     * Consider the following repeating sentence string, with positions of the start character of each row on the screen.
     *
     * Image we have the sentences concatenated together (with space between):
     *   ________ _______
     *  |       ||       |
     * "abc de f abc de f abc de f ..."
     *  ^      ^     ^    ^      ^
     *  0      7     13   18     25
     *
     * Our goal is to find the start position of the row next to the last row on the screen,
     * Since actually it's the length of everything earlier, we can get the answer by dividing this number by
     * the length of (non-repeated) sentence string.
     *
     * !!!
     * Note that the non-repeated sentence string has a space at the end; it is "abc de f " in this example.
     *
     * Here is how we find that position. In each iteration, we need to adjust start based on spaces either added or removed.
     *
     * s = "abc de f ", length is 9.
     * "abc de f abc de f abc de f ..." // start=0
     *  012345                          // start=start + cols + adjustment = 0 + 6 + 1 = 7 (1 space removed in screen string)
     *         012345                   // start=7 + 6 + 0=13
     *               012345             // start=13+6-1=18 (1 space added)
     *                    012345        // start=18+6+1=25 (1 space added)
     *                           012345
     */
    public class Solution {
        public int wordsTyping(String[] sentence, int rows, int cols) {
            /**
             * Assemble the words to form the sentence, with extra space at the end
             * since there should be a space between two sentences.(!!!)
             */
            String s = String.join(" ", sentence) + " ";
            int start = 0, l = s.length();

            for (int i = 0; i < rows; i++) {
                start += cols;

                /**
                 * Check the start position of the start of the next sentences.
                 * we don't need an extra space for current row. The current row could be
                 * successfully fitted. So that we need to increase our counter by using start++.
                 */
                if (s.charAt(start % l) == ' ') {
                    start++;
                } else {
                    /**
                     *  the next word can't fit to current row. So that we need to remove extra
                     *  characters from next word.
                     *
                     *  !!!
                     *  "(start - 1) % l" :
                     *  "!= ' '"
                     *  need to check the previous char to decide if we can move back by one.
                     *  et, find the index of the char that its previous char is space =>
                     *  the index is the starting postition of a word.
                     */
                    while (start > 0 && s.charAt((start - 1) % l) != ' ') {
                        start--;
                    }
                }
            }

            /**
             * (# of valid characters) / our formatted sentence
             */
            return start / s.length();
        }
    }
}
