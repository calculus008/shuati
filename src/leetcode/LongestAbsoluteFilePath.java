package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 11/9/17.
 */
public class LongestAbsoluteFilePath {
    public static int lengthLongestPath(String input) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        stack.push(0);
        int level = 0;

        for(String s : input.split("\n")) {
            //!!! "\t" represents tab, it counts as one char in string.
            level = s.lastIndexOf("\t") + 1;

            //find the length of parent dir path in stack
            while(level+1 < stack.size()) {
                stack.pop();
            }

            //stack.pee() : parent dir path length;
            // - level : "\t" represents tab, level 1 has 1 tab, level 2 has 2 tabs..., so remove it since the path we want has no tab
            // + 1 : the result we want has backslash for each level except the last one (the file name which has "."
            int len = stack.peek() + s.length() - level + 1;
            stack.push(len);

            //at the end of the path
            if(s.contains(".")) {
                //len - 1 : the last one is a file, it does not need a backslash, so remove it.
                res = Math.max(res, len - 1);
            }
        }

        return res;
    }

    public static void main(String [] args)
    {
        lengthLongestPath("");
    }
}
