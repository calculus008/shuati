package Interviews.Indeed;

import java.util.Stack;

public class Python_Indent_Validation {
    /**
     * 1.第一行无缩进
     * 2.前一行是冒号结尾，下一行缩进要比这一行多
     * 3.同一个块里面缩进相同
     * 4.如果下一行缩进变少，必须要变少到之前出现过的有效缩进。
     *
     * The input is String[], each line is a string
     * if it's valid, return -1, otherwise return the line number.
     *
     * follow up1: what is the last line is control block?
     * follow up2: what if there is comment line (" #")
     *
     * follow-up是stack装不下会自动扩展，自动扩展需要把原来的数据复制到一个新的大一些的stack里面，
     * 问这个复制的过程会影响时间复杂度吗？
     * 当然不会，X insertions take O(2X) time, 所以insertion的amortized time仍然是O(1).
     * 面试官还问了我几遍are you sure.我sure的一笔，怎么会上你的当。
     *
     * n - number line lines
     * m - average length of lines
     *
     * Time  : O(n * m)
     * Space : O(n)
     */

    static int base = 0;

    public static int checkValid(String[] strs) {
        Stack<Node> stack = new Stack<>();

        int lastline = 0;

        for (int i = 0; i < strs.length; i++) {
            String temp = removeComment(strs[i]);

            if (temp.isEmpty()) continue;

            int level = getIndent(temp);
            boolean isControl = isControl(temp);

            System.out.println("i=" + i + ", level=" + level + ", isControl=" + isControl);

            if (stack.isEmpty()) {
                /**
                 * #1.First line has no indent
                 */
                if (level != 0) return i;
            } else if (stack.peek().isControl) {
                /**
                 * #2.The last line is control, current level should be last level + 1
                 */
                if (level != stack.peek().level + 1) return i;
            } else {
                /**
                 * #4.Back to smaller level, pop up levels bigger than or EQUAL to the current level.
                 */
                while (!stack.isEmpty() && stack.peek().level >= level) {
                    stack.pop();
                }

                /**
                 * Leagl case : either no line left in stack. OR, a control block on a smaller level.
                 */
                if (!stack.isEmpty() && !stack.peek().isControl) return i;
            }

            stack.push(new Node(level, isControl));
            lastline = i;
        }

        /**
         * the last line is control block
         */
        if (!stack.isEmpty() && stack.peek().isControl) return lastline;

        return -1;
    }

    /**
     * Must remove comments before we process each line, for example :
     *
     * if a is none:  #this is a comment
     *
     * Comment is after ":", if we don't remove comment and just check the last char
     * of the string for ":", this line will not be deemed as control black.
     */
    private static String removeComment(String s) {
        StringBuilder sb = new StringBuilder();
        int valid = 0;

        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] == '#') break;

            sb.append(chs[i]);

            if (chs[i] != ' ') valid++;
        }

        if (valid == 0) return "";

        return sb.toString();
    }

    /**
     * Here the assumption is that for each level, the indent spaces will increase by
     * the same number (base). For example, each level will be indented for two more spaces.
     *
     * "这里如果它说n个空格算一次tab的话，就最后返回的时候res/n好了"
     */
    private static int getIndent(String s) {
        char[] chs = s.toCharArray();

        int count = 0;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] == ' ') {
                count++;
            } else {
                break;
            }
        }

        if (count > 0 && base == 0) {
            base = count;
        }

        if (base != 0) {
            return count / base;
        }

        return 0;
    }

    /**
     * Need to consider case like :
     * 1.Spaces after ':'
     *    "if a is none:            "
     * 2.Line with only empty space:
     *    "            "
     */
    private static boolean isControl(String s) {
        char[] chs = s.toCharArray();

        for (int i = chs.length - 1; i >= 0; i--) {
            if (chs[i] == ' ') continue;
            if (chs[i] == ':') return true;
            return false;
        }

        return false;
    }

    public static void main(String[] args) {
        String[] strs = {"def function():",
                "  print(\"Hello world\")",
                "  print(\"Hello world\")",
                "  if i==1:",
                "#comment",
                "       print(\"asdf\")",
                "       if a = 1:"};
        System.out.print(checkValid(strs));
    }
}
