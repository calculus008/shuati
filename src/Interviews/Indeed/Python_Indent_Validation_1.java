package Interviews.Indeed;

import java.util.Stack;

public class Python_Indent_Validation_1 {
    /**
     * We don't assume each indent level should be indented with the same extra number of spaces
     */

    public static int checkValid(String[] strs) {
        Stack<Node> stack = new Stack<>();

        int lastline = 0;

        for (int i = 0; i < strs.length; i++) {
            String temp = removeComment(strs[i]);

            if (temp.isEmpty()) continue;

            int numberOfIndenets = getIndent(temp);
            boolean isControl = isControl(temp);

            System.out.println("i=" + i + ", level=" + numberOfIndenets + ", isControl=" + isControl);

            if (stack.isEmpty()) {
                if (numberOfIndenets != 0) return i;
            } else if (stack.peek().isControl) {
                if (numberOfIndenets <= stack.peek().level) return i;
            } else {
                while (!stack.isEmpty() && stack.peek().level >= numberOfIndenets) {
                    stack.pop();
                }
                if (!stack.isEmpty() && !stack.peek().isControl) return i;
            }

            stack.push(new Node(numberOfIndenets, isControl));
            lastline = i;
        }

        /**
         * !!!
         * Follow up : the last line is control block
         */
        if (!stack.isEmpty() && stack.peek().isControl) return lastline;

        return -1;
    }

    private static boolean isControl(String s) {
        char[] chs = s.toCharArray();

        for (int i = chs.length - 1; i >= 0; i--) {
            if (chs[i] == ' ') continue;
            if (chs[i] == ':') return true;
            return false;
        }

        return false;
    }

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

        return count;
    }

    public static void main(String[] args) {
        String[] strs = {"def function():",
                "  print(\"Hello world\")",
                "  print(\"Hello world\")",
                "  if i==1: #test",
                "#comment",
                "       print(\"asdf\")",
                };
        System.out.print(checkValid(strs));
    }
}
