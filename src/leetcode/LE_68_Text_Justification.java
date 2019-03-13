package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_68_Text_Justification {
    /**
     * Given an array of words and a length L, format the text such that each line has exactly L
     * characters and is fully (left and right) justified.
     * <p>
     * You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
     * Pad extra spaces ' ' when necessary so that each line has exactly L characters.
     * <p>
     * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on
     * a line do not divide evenly between words, the empty slots on the left will be assigned more spaces
     * than the slots on the right.
     * <p>
     * For the last line of text, it should be left justified and no extra space is inserted between words.
     * <p>
     * For example,
     * words: ["This", "is", "an", "example", "of", "text", "justification."]
     * L: 16.
     * <p>
     * Return the formatted lines as:
     * [
     * "This    is    an",
     * "example  of text",
     * "justification.  "
     * ]
     * Note: Each word is guaranteed not to exceed L in length.
     * <p>
     *
     * Example 2:
     *
     * Input:
     * words = ["What","must","be","acknowledgment","shall","be"]
     * maxWidth = 16
     * Output:
     * [
     *   "What   must   be",
     *   "acknowledgment  ",
     *   "shall be        "
     * ]
     * Explanation: Note that the last line is "shall be    " instead of "shall     be",
     *              because the last line must be left-justified instead of fully-justified.
     *              Note that the second line is also left-justified becase it contains only one word.
     *
     * Note:
     * A word is defined as a character sequence consisting of non-space characters only.
     * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.(!!!)
     * The input array words contains at least one word.
     *
     * Hard
     *
     *
     * 有变化，输入的除了行宽还有一组字体大小，要求选择最大可用的字体，并输出排版结果。最大可用字体的意思就是，
     * 例如有一个单词特别长，abcdefghijklmn，这个单词不能拆到两行里面去. 就是说上面的 "Each word's length
     * is guaranteed to be greater than 0 and not exceed maxWidth" 有可能不成立。
     *
     * 调节字体使得这个常单词能在一行放下？是否能保证最长的字体在最小的字体下 <= maxWidth?
     */

    class Solution {

        public List<String> fullJustify(String[] words, int maxWidth) {
            List<String> res = new ArrayList<>();
            if (null == words || words.length == 0 || maxWidth == 0) {
                res.add("");
                return res;
            }

            for (int i = 0, w; i < words.length; i = w) {
                /**
                 * We need to skip the space for last word
                 * (last word has no space after it, fully (left and right) justified)
                 * hence start len = -1 (提前减掉最后一个space)
                 * **/
                int len = -1;
                for (w = i; w < words.length && len + words[w].length() + 1 <= maxWidth; w++) {
                    len += words[w].length() + 1;
                }

                /**
                 * After for loop above :
                 * w is the index of the first word of the next line,
                 * i is the index of the start word
                 *
                 * The number of words : (w - 1) - i + 1 = w - i
                 *                        w - 1 : 当前行最后一个word在words[]中的下标
                 *                        i     : 当前行第一个word在words[]中的下标
                 *
                 * The number of gaps : 每一个word有一个gap, 除了最后一个word, 所以：
                 *                      The number of words - 1 = w - i - 1
                 *                      Example: "word[0], word[1], word[2], word[3]", "word[4]....", i=0, w=4.
                 *
                 * The number of padding spaces : maxWidth - len
                 *
                 * How many padding spaces each gap gets by evenly distribution : (maxWidth - len) / The number of gaps + 1
                 * "+ 1" - this is the default one space each word (except the last word) gets.
                 *
                 * How many extra padding spaces left after evenly distribution : (maxWidth - len) % The number of gaps
                 *
                 *
                 **/

                int numberOfGaps = w - i - 1;
                int extraSpace = 0;

                /**
                 * !!!
                 * at least one space in each gap
                 */
                int evenlyDistSpace = 1;

                /**
                 * Only do this if it is NOT the last word and it is NOT the case that
                 * only one word fits the line
                 *
                 * 1.w != words.length" :
                 *   The last word in current line is the last word in words[]
                 *
                 * 2."w != i + 1" :
                 *   if w == i + 1
                 *   w is the index of the first word of the next line,
                 *   i is the index of the start word
                 *   so it means words[i] is the only word in current line.
                 *
                 * For those 2 cases, no need to do evenly distribution and fully-justified.
                 * we need to pad spaces after the last word.
                 **/
                if (w != words.length && w != i + 1) {
                    evenlyDistSpace = (maxWidth - len) / numberOfGaps + 1;
                    extraSpace = (maxWidth - len) % numberOfGaps;
                }

                /**
                 * init with the first word, so that in for loop we can do appending
                 * space first and then end with appending word
                 **/
                StringBuilder sb = new StringBuilder(words[i]);
                for (int j = i + 1; j < w; j++) {
                    for (int m = 0; m < evenlyDistSpace; m++) {
                        sb.append(" ");
                    }

                    //extra space is just one space added for the leftmost gaps
                    if (extraSpace > 0) {
                        sb.append(" ");
                        extraSpace--;
                    }

                    sb.append(words[j]);
                }

                /**
                 * deal with the case that it is the last word OR only one word fits the line,
                 * we pad spaces after the word.
                 **/
                int remain = maxWidth - sb.length();
                while (remain > 0) {
                    sb.append(" ");
                    remain--;
                }
                res.add(sb.toString());
            }

            return res;
        }

    }

    class Solution_Practice {
        public List<String> fullJustify(String[] words, int maxWidth) {
            List<String> res = new ArrayList<>();
            if (null == words || words.length == 0 || maxWidth <= 0) {
                return res;
            }

            /**
             * pay attention to how those for loops are written
             */
            for (int i = 0, w = 0; i < words.length; i = w) {
                int len = -1;
                for (w = i; w < words.length && len + words[w].length() + 1 <= maxWidth; w++) {
                    len += words[w].length() + 1;
                }

                int numberOfWords = w - i - 1 + 1;
                int numberOfGaps =  numberOfWords - 1;
                int numberOfSpaces = maxWidth - len;

                int evenlyDistSpaces = 1;
                int extraSpaces = 0;

                if (w != words.length && i + 1 != w) {
                    evenlyDistSpaces = numberOfSpaces / numberOfGaps + 1;
                    extraSpaces = numberOfSpaces % numberOfGaps;
                }

                StringBuilder sb = new StringBuilder(words[i]);
                for (int j = i + 1; j < w; j++) {
                    for (int k = 0; k < evenlyDistSpaces; k++) {
                        sb.append(" ");
                    }

                    if (extraSpaces > 0) {
                        sb.append(" ");
                        extraSpaces--;
                    }

                    sb.append(words[j]);
                }

                int remains = maxWidth - sb.length();
                while (remains > 0) {
                    sb.append(" ");
                    remains--;
                }

                res.add(sb.toString());
            }

            return res;
        }
    }
}
