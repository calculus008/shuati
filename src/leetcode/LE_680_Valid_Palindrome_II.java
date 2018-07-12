package leetcode;

/**
 * Created by yuank on 7/9/18.
 */
public class LE_680_Valid_Palindrome_II {
    /**
         Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

         Example 1:
         Input: "aba"
         Output: True
         Example 2:
         Input: "abca"
         Output: True
         Explanation: You could delete the character 'c'.
         Note:
         The string will only contain lowercase characters a-z. The maximum length of the string is 50000.

         Easy

         Related : LE_125_Valid_Palindrome
     */

    /**
     正确的算法如下：

     依然用相向双指针的方式从两头出发，两根指针设为 L 和 R。
     如果 s[L] 和 s[R] 相同的话，L++, R--
     如果 s[L] 和 s[R] 不同的话，停下来，此时可以证明，如果能够通过删除一个字符使得整个字符串变成回文串的话，那么一定要么是 s[L]，要么是 s[R]。
     简单的来说，这个算法就是依然按照原来的算法走一遍，然后碰到不一样的字符的时候，从总选一个删除，如果删除之后的字符换可以是 Palindrome 那就可以，
     都不行的话，那就不行。

     证明：

         假设从两边往中间比较的过程中，找到了第一对 s[L] != s[R]，L的左边和R的右边都一样：

         xyz...?...?...zyx
         ^   ^
         L   R
         我们总共需要证明两件事情：

         L和R中间不存在任何字符，删除之后可以使得字符串变为回文串。
         L左侧（R右侧同理）不存在任何字符，删除之后可以使得字符串变为回文串。
         先证明 1
         假如被删除的字符在中间，我们用 $ 来表示（$ 可以是任何字符）：

         xyz...?.$.?...zyx
         ^   ^
         L   R
         既然 $ 删除之后，整个字符串是回文串，那么这个字符串左右两边必然包含 xyz..L 和 R...zyx 的部分（xyz 只是一个例子，可以是任何其他的对称字符串），
         那又因为 s[L] != s[R]，所以可以知道这个字符串并不是轴对称的，也就是并不是回文串。

         再证明 2
         假如 L 左侧存在一个字符 （是个变量，可以是任何字符），删除之后，使得整个字符串为回文串：

         xyz.$$'.?...?..$.zyx
         ^   ^
         L   R
         我们将其对称的右边的位置也标记出来。如果 $ 被删除之后，那么他后面紧随而来的字符 $' 就有义务和 $ 的对称字符，也就是 $ 相等。
         也就是说，=='，那么此时，我们删除 $ 和 删除 $’ 的效果应该是一样的。那么我们就认为这次删除相当于删除了 $'，那么同理我们可以证明，
         如果 $ 后面的字符分别是 $', $'', $'''。。可以得到 $ == $' == $'' == $''' ... 一直到 $ == L。那么此时也就是说，删除 $ 的效果和删除 L 的效果是一样的。那么就证明了，删除任何 L 左侧的字符，和删除 L 没有区别，那么就证明了仍然是在 L 和 R 中去选一个删除就行了。


     */

    //Time : O(n), Space : O(1)
    public boolean validPalindrome(String s) {
        int l = 0, h = s.length() - 1;
        while (l < h) {
            if (s.charAt(l) != s.charAt(h)) {
                return isPalindrome(s, l, h - 1) || isPalindrome(s, l + 1, h);
            }
            l++;
            h--;
        }
        return true;
    }

    private boolean isPalindrome(String s, int l, int h) {
        while (l < h) {
            if (s.charAt(l) != s.charAt(h)) return false;
            l++;
            h--;
        }
        return true;
    }
}
