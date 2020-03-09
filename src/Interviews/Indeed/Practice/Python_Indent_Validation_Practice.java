package Interviews.Indeed.Practice;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1.第一行无缩进
 * 2.前一行是冒号结尾，下一行缩进要比这一行多
 * 3.同一个块里面缩进相同
 * 4.如果下一行缩进变少，必须要变少到之前出现过的有效缩进。
 * */

public class Python_Indent_Validation_Practice {
    class Node {
        int indent;
        boolean isControl;

        public Node(int indent, boolean isControl) {
            this.indent = indent;
            this.isControl = isControl;
        }
    }

    public boolean isValid(String[] strs) {
        if (strs == null || strs.length == 0) return false;

        Deque<Node> stack = new ArrayDeque<>();

        int line = 0;
        for (String str : strs) {
            String temp = removeComment(str);

            System.out.println(line + " : " + temp);

//            System.out.println("line " + line + " len : "  + temp.length());
            if (temp.isEmpty()) {
                System.out.println("bypass " + line);
                line++;
                continue;
            }

            line++;

            boolean isControl = isControl(temp);
            int indent = getIndent(temp);

            if (stack.isEmpty()) {
                if (indent != 0) return false;
            } else if (stack.peek().isControl) {
                if (indent <= stack.peek().indent) {
                    System.out.println("#1");
                    return false;
                }
            } else {
                /**
                 * ">="
                 */
                int tmp = 0;
                while (!stack.isEmpty() && stack.peek().indent >= indent) {
                    tmp = stack.pop().indent;
                }
                if (!stack.isEmpty() && !stack.peek().isControl) return false;

                if (!stack.isEmpty() && (stack.peek().indent != indent && tmp != indent)) return false;
            }

            stack.push(new Node(indent, isControl));

        }

        if (!stack.isEmpty() && stack.peek().isControl) return false;

        return true;
    }

    /**
     * !!!
     *  "
     *    int idx = s.indexOf('#');
     *    return s.substring(0, idx);
     *  "
     *
     *  Can not do this, if '#' is at index 0, substring(0, 0) returns null
     */
//    private String removeComment1(String s) {
//        if (s == null || s.length() == 0) return "";
//
//        int idx = s.indexOf('#');
//        return s.substring(0, idx).trim();
//    }

    private String removeComment(String s) {
        if (s == null || s.length() == 0) return "";

        char[] chs = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (char c : chs) {
            if (c == '#') {
                break;
            } else {
                sb.append(c);

                /**
                 * count none-space char before running into '#',
                 * if count is 0, it means there's no useful char, or
                 * in other words, all char before '#' is space, therefore
                 * we need to return an empty string.
                 */
                if (c != ' ') count++;
            }
        }

        if (count > 0) {
            return sb.toString();
        }

        return "";
    }

    private int getIndent(String s) {
        char[] chs = s.toCharArray();
        int count = 0;
        for (char c : chs) {
            if (c == ' ') {
                count++;
            } else {
                /**
                 * !!!
                 * only count space at the start of string before there's any other kind of character,
                 * once we run into a none-space char, we break
                 */
                break;
            }
        }

        return count;
    }

    private boolean isControl(String s) {
        char[] chs = s.toCharArray();
        for (int i = chs.length - 1; i >= 0; i--) {
            if (chs[i] == ' ') {
                continue;
            } else if (chs[i] == ':') {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Python_Indent_Validation_Practice validator = new Python_Indent_Validation_Practice();

        String[] strs = {"def function():",
                "  print(\"Hello world\")",
                "  print(\"Hello world\")",
                "  if i==1: #test",
                "#comment",
                "   #comment",
                "         if i == 2:        ",
                "           print(\"asdf\")",
                "           print(\"asdf\")",
                "   ",
                "def func1():"
        };
        System.out.print(validator.isValid(strs));
    }

}
