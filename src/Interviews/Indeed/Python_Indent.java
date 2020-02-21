package Interviews.Indeed;

import java.util.Stack;

public class Python_Indent {
    /**
     * https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=216457&highlight=indeed
     *
     * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=210786&extra=page%3D1%26filter%3Dsortid%26sortid%3D311%26searchoption%5B3089%5D%5Bvalue%5D%5B2%5D%3D2%26searchoption%5B3089%5D%5Btype%5D%3Dcheckbox%26searchoption%5B3046%5D%5Bvalue%5D%3D26%26searchoption%5B3046%5D%5Btype%5D%3Dradio%26sortid%3D311
     * http://www.1point3acres.com/bbs/thread-176045-1-1.html
     *
     * 我看Indeed onsite面经有一道题，给一段Python代码，还有一些列规则，然后写程序检测该代码是否符合该规则
     * （主要是Python的缩进规则），1.如果一行代码以':'（冒号）结尾，那么说明它是一个control statement，
     * 紧接着的下一行需要缩进（起码要比‘：’这一行要缩进）2.代码开头这一行（第一行）不能缩进，据说一共有四个规则
     *
     * 1.第一行无缩进
     * 2.前一行是冒号结尾，下一行缩进要比这一行多
     * 3.同一个块里面缩进相同
     * 4. 如果下一行缩进变少，必须要变少到之前出现过的有效缩进。
     *
     * 做法应该就是stack
     *
     * 我的作法使用stack 记录每个 block的indentation, 还有一个 flag 确定前一个statement是不是control statement.
     * For loop, 一行一行 scan, 是control statement, 下一行的indentation就是这个block的规定indentation.
     * 此外当然也要确定indentation是递增.
     *
     * 问下楼主怎么处理if里面还有if这种情况？
     * 我使用一个flag记录前一行是否为control flag.
     *
     * 标准情况下是每次退一个tab但是我面试的时候面试官说如果进了一个block只要缩进空格数比前一行多即可（多一个空格多十个都行）。
     * 这种情况下就必须用stack。
     */

    /**
     * 你的code有两个test cases过不了。
     * 1. 最后一行是':'结尾的
     * 2. 有注释行的，就是'#'为第一个字符的x`
     *
     * 嗯，好厉害，总结：加上前面的四个规则，主要注意以':"结尾的是不是以'#'开头的，比如最后一行不是以#开头，
     * 但以冒号结尾的话，就是false，不知道总结的对不对
     */
    public static boolean identification(String[] strs) {
        if (strs == null || strs.length == 0)   return true;
        if (countTab(strs[0]) != 0)     return false;
        Stack<Integer> stack = new Stack<>();
        stack.push(countTab(strs[0]));
        for (int i = 1; i < strs.length; i++) {
            int cur = countTab(strs[i]);
            // 需要缩进
            if (strs[i-1].charAt(strs[i-1].length()-1) == ':') {
                if (cur <= stack.peek())   return false;
            }
            else {
                // 同一个块，不用缩进
                if (cur > stack.peek())     return false;
                // 比之前的tab少，需要把stack之前的strs pop出来
                while (!stack.isEmpty() && stack.peek() > cur) {
                    stack.pop();
                }
                if (stack.peek() != cur)    return false;
            }
            stack.push(cur);
        }
        return true;
    }

    public static int countTab(String s) {
        char[] arr = s.toCharArray();
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ')     cnt++;
            else    break;
        }
        return cnt;
    }

    public static void main(String[] args) {
        String[] strs = {"def function():",
                "  print(\"Hello world\")",
                "  print(\"Hello world\")",
                "  if i==1:",
                "    print(\"asdf\")"};
        System.out.print(identification(strs));
    }
}
