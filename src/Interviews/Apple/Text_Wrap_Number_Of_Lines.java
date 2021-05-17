package Interviews.Apple;

import java.util.ArrayList;
import java.util.List;

public class Text_Wrap_Number_Of_Lines {
    /**
     * 给你一个段落，放进一个固定宽度的doc，问需要多少行，利口原题。秒了之后问，如果这个段落非常大，放不进memory怎么办。
     * 需要知道每个字符占多少内存，给定一台机器的内存，问需要多少台机器，如果需要多台，怎么load balance
     *
     * A simplified question from LE_68_Text_Justification, just count number of lines, no need to
     * output the final paragraph.
     */

    public int getNumberOfLines(String[] words, int maxWidth) {
        if (null == words || words.length == 0 || maxWidth <= 0) {
            return 0;
        }

        int res = 0;

        for (int i = 0, w = 0; i < words.length; i = w) {
            int len = -1;
            for (w = i; w < words.length && len + words[w].length() + 1 <= maxWidth; w++) {
                len += words[w].length() + 1;
            }

            res++;
        }

        return res;
    }
}
