package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_271_Encode_And_Decode_Strings {
    /**
     * Design an algorithm to encode a list of strings to a string.
     * The encoded string is then sent over the network and is decoded back to the original list of strings.
     *
     * The string may contain any possible characters out of 256 valid ascii characters.
     * Your algorithm should be generalized enough to work on any possible characters.
     *
     Do not use class member/global/static variables to store states.
     Your encode and decode algorithms should be stateless.

     Do not rely on any library method such as eval or serialize methods. You should implement your own encode/decode algorithm.
     */

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length()).append("/").append(str);
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> res = new ArrayList<>();
        if(s == null || s.length() == 0) return res;

        int i = 0;
        while (i < s.length()) {
            int j = s.indexOf("/", i);
            int len = Integer.valueOf(s.substring(i, j));
            res.add(s.substring(j + 1, j + 1 + len));
            i = j + 1 + len;
        }

        return res;
    }
}

