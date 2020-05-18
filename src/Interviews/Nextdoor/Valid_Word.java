package Interviews.Nextdoor;

import java.util.HashSet;

public class Valid_Word {
    /**
     *     输入一组words和一组valid letters，判断有多少个words是valid。
     *      判断条件是words里的所有upper and lower letter必须在valid letters里面。
     *      如果word里面有special character不用管。注意valid letter只有小写，
     *      但是words里面有大写的也算valid。
     *      比如words = [hEllo##, This^^],
     *      valid letter = [h, e, l, 0, t, h, s];
     *      "hello##" 就是valid，因为h，e，l，o都在valid letter 里面，
     *      “This^^” 不valid, 因为i不在valid letter里面
     */


    public static int word_is_valid(String[] words, char[] letters) {
        HashSet<Character> letter_dict = new HashSet();

        for (char l : letters) {
            letter_dict.add(l);
        }

        //System.out.println(letter_dict.size());
        int count = 0;
        for (String word : words) {
            boolean valid = true;

            for (int i = 0; i < word.length(); i++) {
                char l = word.charAt(i);
                if (!Character.isLowerCase(l) && !Character.isUpperCase(l)) {
                    continue;
                }

                l = Character.toLowerCase(l);

                if (letter_dict.contains(l) == false) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int result = word_is_valid(new String[]{"h", "wo!@rld", "test#@", "rest@"}, new char[]{'a', 'b', 'w', 'l', 'o', 'h', 'e', 't', 'r', 'd', 's'});
        System.out.println(result);
    }
}
