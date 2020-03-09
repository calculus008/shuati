package Interviews.Indeed.Practice;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *      * 1.第一行无缩进
 *      * 2.前一行是冒号结尾，下一行缩进要比这一行多
 *      * 3.同一个块里面缩进相同
 *      * 4.如果下一行缩进变少，必须要变少到之前出现过的有效缩进。
 */
public class Python_Indent_Validation_Practice_1 {
    class Pair {
        int indent;
        boolean isControl;

        public Pair(int indent, boolean isControl) {
            this.indent = indent;
            this.isControl = isControl;
        }
    }

    public int validate(String[] strs) {
        if (strs == null || strs.length == 0) return 0;

        Deque<Pair> stack = new ArrayDeque();

        int line = 0;
        for (int i = 0; i < strs.length; i++) {
            String temp = removeComment(strs[i]);

            if (temp.length() == 0) continue;

            int indent = getIndent(temp);
            boolean isControl = getControl(temp);

            if (stack.isEmpty()) {//1.第一行无缩进
                if (indent != 0) return i;
            } else if (stack.peek().isControl) {//2.前一行是冒号结尾，下一行缩进要比这一行多
                if (indent <= stack.peek().indent) return i;
            } else {//stack is not empty and previous line is not control line
                /**
                 * Since last line is not control, so current indent should be >= previous line.
                 * Pop all lines in that the same and higher level blocks. After it:
                 * 1.stack is empty
                 * 2.stack is not empty, legal case:
                 *   a.top element in stack is control
                 *   b.current indent is the same as the indent of the line that is popped last
                 *     (in the same block)
                 */
                int tmp = 0;
                while (!stack.isEmpty() && stack.peek().indent >= indent) {
                    tmp = stack.pop().indent;
                }
                //3.同一个块里面缩进相同
                if (!stack.isEmpty() && !stack.peek().isControl) return i;

                //4.如果下一行缩进变少，必须要变少到之前出现过的有效缩进。
                if (!stack.isEmpty() && (stack.peek().indent != indent && tmp != indent)) return i;
            }

            stack.push(new Pair(indent, isControl));
            line = i;
        }

        /**
         * !!! last line can not be control
         */
        if (!stack.isEmpty() && stack.peek().isControl) return line;

        return -1;
    }

    private String removeComment(String s) {
        if (s == null || s.length() == 0) return "";

        int n = s.length();
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '#') break;

            if (c != ' ') count++;
            sb.append(c);
        }

        if (count == 0) return "";

        return sb.toString();
    }

    private int getIndent(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                count++;
            } else {
                break;
            }
        }

        return count;
    }

    private boolean getControl(String s) {
        int n = s.length();
        char[] chs = s.toCharArray();
        for (int i = n - 1; i >= 0; i++) {
            if (chs[i] == ' ') {
                continue;
            } else if (chs[i] == ':') {
                return true;
            } else {//!!!
                return false;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Python_Indent_Validation_Practice_1 test = new Python_Indent_Validation_Practice_1();
        String[] strs = {
                "def function():",
                "  print(\"Hello world\")",
                "  print(\"Hello world\")",
                " print(\"Hello world\")",
                "  if i==1:",
                "    if i==1:",
                "      if i ==1:",
                "#comment",
                "         print(\"asdf\")",
                "         print(\"b\")",
                "  print(\"b\")",
                "  if a = 1:",
                "     print(\"a\")",
                "     print(\"a\")"};
        System.out.print(test.validate(strs));
    }

}
