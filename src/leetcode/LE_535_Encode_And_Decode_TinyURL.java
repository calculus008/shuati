package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LE_535_Encode_And_Decode_TinyURL {
    /**
     * TinyURL is a URL shortening service where you enter a URL such as
     * https://leetcode.com/problems/design-tinyurl and it returns a short
     * URL such as http://tinyurl.com/4e9iAk.
     *
     * Design the encode and decode methods for the TinyURL service.
     * There is no restriction on how your encode/decode algorithm should work.
     * You just need to ensure that a URL can be encoded to a tiny URL and the
     * tiny URL can be decoded to the original URL.
     *
     * Companion problem for System Design problem:
     * https://leetcode.com/discuss/interview-question/124658/Design-a-URL-Shortener-(-TinyURL-)-System/
     */

    /**
     * Variable-length Encoding
     *
     * In this case, we make use of variable length encoding to encode the given URLs.
     * For every \text{longURL}longURL, we choose a variable code length for the input URL,
     * which can be any length between 0 and 61. Further, instead of using only numbers as
     * the Base System for encoding the URLSs, we make use of a set of integers and alphabets
     * to be used for encoding.
     *
     * Performance Analysis
     * 1.The number of URLs that can be encoded is, again, dependent on the range of \text{int}int,
     * since, the same countcount will be generated after overflow of integers.
     *
     * 2.The length of the encoded URLs isn't necessarily short, but is to some extent dependent on
     * the order in which the incoming \text{longURL}longURL's are encountered. For example,
     * the codes generated will have the lengths in the following order: 1(62 times), 2(62 times)
     * and so on.
     *
     * 3.The performance is quite good, since the same code will be repeated only after the integer
     * overflow limit, which is quite large.
     *
     * 4.In this case also, the next code generated could be predicted by the use of some calculations.
     */
    public class Codec1 {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        HashMap<String, String> map = new HashMap<>();
        int count = 1;

        public String getString() {
            int c = count;
            StringBuilder sb = new StringBuilder();
            while (c > 0) {
                c--;
                sb.append(chars.charAt(c % 62));
                c /= 62;
            }
            return sb.toString();
        }

        public String encode(String longUrl) {
            String key = getString();
            map.put(key, longUrl);
            count++;

            return "http://tinyurl.com/" + key;
        }

        public String decode(String shortUrl) {
            return map.get(shortUrl.replace("http://tinyurl.com/", ""));
        }
    }

    /**
     * Random fixed-length encoding
     *
     * the length of the code is fixed to 6 only. Further, random characters from the string to form the
     * characters of the code. In case, the code generated collides with some previously generated code,
     * we form a new random code.
     *
     * Performance Analysis
     * 1.The number of URLs that can be encoded is quite large in this case, nearly of the order (10 + 26 * 2) ^ 6
     * (10 digits + 26 lower case letters + 26 upper case letters, used for each position of length 6)
     *
     * 2.he length of the encoded URLs is fixed to 6 units, which is a significant reduction for very large URLs.
     *
     * 3.The performance of this scheme is quite good, due to a very less probability of repeated same codes generated.
     *
     * 4.We can increase the number of encodings possible as well, by increasing the length of the encoded strings.
     * Thus, there exists a tradeoff between the length of the code and the number of encodings possible.
     *
     * 5.Predicting the encoding isn't possible in this scheme since random numbers are used.
     */

    public class Codec2 {
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        HashMap<String, String> map = new HashMap<>();
        Random rand = new Random();
        String key = getRand();

        public String getRand() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(alphabet.charAt(rand.nextInt(62)));
            }
            return sb.toString();
        }

        public String encode(String longUrl) {
            while (map.containsKey(key)) {
                key = getRand();
            }
            map.put(key, longUrl);
            return "http://tinyurl.com/" + key;
        }

        public String decode(String shortUrl) {
            return map.get(shortUrl.replace("http://tinyurl.com/", ""));
        }
    }



}
