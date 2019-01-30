package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_722_Remove_Comments {
    /**
     * https://leetcode.com/problems/remove-comments/
     */

    /**
     * We only need to check for two things:
     *
     * If we see '//' we stop reading the current line, and add whatever characters we have seen to the result.
     * If we see '/*' then we start the multiline comment mode and we keep on ignoring characters until we see end mark.
     * If the current character is neither of the above two and the multiline comment mode is off, then we add that
     * character to the current line. Once we parse one line (source[i]), then if the mode is off, we add the currently
     * generated line (StringBuilder) to the result and repeat for source[i + 1].
     *
     * We need to be careful not to insert empty lines in the result.
     */
    class Solution {
        public List<String> removeComments(String[] source) {
            List<String> res = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            boolean mode = false;

            for (String s : source) {
                for (int i = 0; i < s.length(); i++) {
                    if (mode) {
                        if (s.charAt(i) == '*' && i < s.length() - 1 && s.charAt(i + 1) == '/') {
                            mode = false;
                            i++;        //skip '/' on next iteration of i
                        }
                    } else {
                        if (s.charAt(i) == '/' && i < s.length() - 1 && s.charAt(i + 1) == '/') {
                            break;      //ignore remaining characters on line s
                        } else if (s.charAt(i) == '/' && i < s.length() - 1 && s.charAt(i + 1) == '*') {
                            mode = true;
                            i++;           //skip '*' on next iteration of i
                        } else {
                            sb.append(s.charAt(i));     //not a comment
                        }
                    }
                }

                if (!mode && sb.length() > 0) {
                    res.add(sb.toString());
                    sb = new StringBuilder();   //reset for next line of source code
                }
            }
            return res;
        }
    }
}
