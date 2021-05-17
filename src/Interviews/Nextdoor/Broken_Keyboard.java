package Interviews.Nextdoor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Broken_Keyboard {
    /**
     * input: a = "Hello, my dear friend!", b = ['h', 'e', 'l', 'o', 'm']
     * output: 1
     * 题目是键盘坏了，只剩下b中的字母按键和所有的数字和符号案件能用，同时shift键是好的，
     * 所以可以切换大小写。问a中的单词有几个可以用当前坏掉的键盘打出来。
     */

    public static int getNumberwords(String s, char[] chs) {
        int count = 0;

        String[] words = s.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            /**
             * You may want to check for a non-word character before blindly performing
             * a replacement. It may also be necessary to adjust the character class
             **/
            words[i] = words[i].replaceAll("[^\\w]", "");
            words[i] = words[i].toLowerCase();
        }

        System.out.println(Arrays.toString(words));

        Set<Character> set = new HashSet<>();
        for (char c : chs) {
            set.add(Character.toLowerCase(c));
        }

        boolean canType = true;

        for (String w : words) {
            char[] chars = w.toCharArray();
            for (char c : chars) {
                if (Character.isLetter(c)) {
                    if (!set.contains(c)) {
                        canType = false;
                        break;
                    }
                }
            }
            if (canType) {
                count++;
            }
            canType = true;
        }

        return count;
    }

    public static void main(String[] args) {
        String s = "Hello, my dear 123m friend!";
        char[] b = {'h', 'e', 'l', 'o', 'm'};

        System.out.println(getNumberwords(s, b));
    }
}
