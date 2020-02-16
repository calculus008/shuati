package Interviews.Karat;

import java.util.ArrayList;
import java.util.List;

public class Text_Wrap {
    /**
     * 题目并没有在面经里见过，都是给定行距做文字line wrapper。第一题是保证词的完整性进行wrap，
     * 第二题比较麻烦，需要先在词与词之间加“-”，
     * 如果还有位置再加“--”，最后一行平均分配，在词与词之间加任意多个“-”。
     * # We are building a word processor and we would like to implement a "word-wrap" functionality.
     *
     * # Given a maximum number of characters in a line followed by a list of words,
     * return a collection of strings where each string element represents a line that
     * contains as many words as possible, with the words in each line being concatenated
     * with a single '-' (representing a space, but easier to see for testing).
     * The length of each string must not exceed the maximum character length per line.
     *
     * # Your function should take in the maximum characters per line and return a data structure
     * representing all lines in the indicated max length.
     * # Note: built-in functions like Python textwrap module should not be used as solutions to this problem.
     *
     * # Examples:
     *
     * # words1 = [ "The", "day", "began", "as", "still", "as", "the", "night", "abruptly", "lighted", "with", "brilliant", "flame" ]
     * # wrapLines(words1, 13) ... "wrap words1 to line length 13" =>
     * #   [ "The-day-began", "as-still-as", "the-night", "abruptly", "lighted-with", "brilliant", "flame" ]
     *
     * # wrapLines(words1, 20) ... "wrap words1 to line length 20" =>
     * #   [ "The-day-began-as", "still-as-the-night", "abruptly-lighted", "with-brilliant-flame" ]
     *
     * # words2 = [ "Hello" ]
     * # wrapLines(words2, 5) ... "wrap words2 to line length 5" =>
     * #   [ "Hello" ]
     *
     * # words3 = [ "Hello", "world" ]
     * # wrapLines(words3, 5) ... "wrap words3 to line length 5" =>
     * #   [ "Hello",  "world" ]
     *
     * # n = number of words / total characters
     *
     * ####
     * 给一个word list比如["I", "am", "so" "sad"], 和最长字符数比如4， 把这些单词用下划线wrap起来输出：
     * [“I_am”, "so", "sad"] 保持输出的每个string的长度都小于等于最长字符数。 这一题大家可以看我的答案之前试一试
     * ，我的感受是很容易跪在最后一个单词无法输出的情况
     *
     * ####
     * 第一题 word wrap：给一个word list 和最大的长度，要求把这些word用 - 串联起来，但不能超过最大的长度。
     * 第二题 word processor: 当时有点晕，半天没搞懂题意。。。面试官说可以用第一题的function。
     * We are building a word processor and we would like to implement a "reflow" functionality that
     * also applies full justification to the text.
     *
     * Given an array containing lines of text and a new maximum width, re-flow the text to fit the new
     * width. Each line should have the exact specified width. If any line is too short, insert '-'
     * (as stand-ins for spaces) between words as equally as possible until it fits.
     *
     * Note: we are using '-' instead of spaces between words to make testing and visual verification
     * of the results easier.
     *
     *
     * lines = [ "The day began as still as the",
     * "night abruptly lighted with",
     * "brilliant flame" ]
     *
     * reflowAndJustify(lines, 24) ... "reflow lines and justify to length 24" =>
     *
     * [ "The--day--began-as-still",
     * "as--the--night--abruptly",
     * "lighted--with--brilliant",
     * "flame" ] // <--- a single word on a line is not padded with spac
     *
     *
     * ####
     * 第一题: 是要求把尽量多的词用‘-’串起来，但要求长度在某个数之内。我的主要时间就卡在了这道题，
     * 在最后5分钟做出来了，哎，手太生疏了。。。要想清楚需要维护的变量是什么就会很简单。。
     * 第二题: 就是基于第一题把给的句子拆开，然后再用类似task schedule的方式逐一插入‘-’直到达到最大长度。。
     *
     *
     * Reference : LE_68_Text_Justification
     */

    public static List<String> processText(String[] words, int max) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;

        for (int i = 0, w; i < words.length; i = w) {
            int len = -1;
            for (w = i; w < words.length && len + words[w].length() + 1 <= max; w++) {
                len += words[w].length() + 1;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(words[i]);
            for (int j = i + 1; j < w; j++) {
                sb.append("-").append(words[j]);
            }

            res.add(sb.toString());
        }

        return res;
    }

    public static List<String> reflowLine(String[] lines, int max) {
        List<String> res = new ArrayList<>();

        List<String[]> temp = new ArrayList<>();
        int size = 0;
        for (String line : lines) {
            String[] w = line.split(" ");
            temp.add(w);
            size += w.length;
        }

        String[] processedWords = new String[size];
        int idx = 0;
        for (String[] t : temp) {
            for (String s : t) {
                processedWords[idx++] = s;
            }
        }

        List<String> texts = processText(processedWords, max);
        if (texts.size() == 0) return res;

        for (String text : texts) {
            String[] parts = text.split("-");
            int numberOfWords = parts.length;

            if (numberOfWords == 1) {
                System.out.println(text);
                res.add(text);
                continue;
            }

            int numberOfGap = parts.length - 1;
            int numberOfPadding = max - text.length() + numberOfGap;

            int averagePadding = numberOfPadding / numberOfGap;
            int extraPadding = numberOfPadding % numberOfGap;

            StringBuilder sb = new StringBuilder();
            sb.append(parts[0]);

            for (int i = 1; i < parts.length; i++) {
                for (int j = 0; j < averagePadding; j++) {
                    sb.append("-");
                }

                if (extraPadding > 0) {
                    sb.append("-");
                    extraPadding--;
                }

                sb.append(parts[i]);
            }

            res.add(sb.toString());
        }

        return res;
    }

    public static void printRes1(String title, List<String> list) {
        System.out.println("---" + title + "----");
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        String[] words1 = {"The", "day", "began", "as", "still", "as", "the", "night",
                "abruptly", "lighted", "with", "brilliant", "flame"};
        printRes1("max 13", processText(words1, 13));
        printRes1("max 20", processText(words1, 20));

        String[] words2 = {"Hello"};
        String[] words3 = {"Hello", "World"};

        printRes1("max 5", processText(words2, 5));
        printRes1("max 5", processText(words3, 5));

        String[] lines = {"The day began as still as the",
                "night abruptly lighted with",
                "brilliant flame"

        };
        printRes1("max 24, justify line", reflowLine(lines, 24));
    }

}


