package leetcode;

import java.util.*;

public class LE_843_Guess_The_Word {
    /**
     * This problem is an interactive problem new to the LeetCode platform.
     *
     * We are given a word list of unique words, each word is 6 letters long,
     * and one word in this list is chosen as secret.
     *
     * You may call master.guess(word) to guess a word.  The guessed word
     * should have type string and must be from the original list with 6
     * lowercase letters.
     *
     * This function returns an integer type, representing the number of
     * exact matches (value and position) of your guess to the secret word.
     * Also, if your guess is not in the given wordlist, it will return -1 instead.
     *
     * For each test case, you have 10 guesses to guess the word. At the end
     * of any number of calls, if you have made 10 or less calls to master.guess
     * and at least one of these guesses was the secret, you pass the testcase.
     *
     * Besides the example test case below, there will be 5 additional test cases,
     * each with 100 words in the word list.  The letters of each word in those
     * testcases were chosen independently at random from 'a' to 'z', such that
     * every word in the given word lists is unique.
     *
     * Example 1:
     * Input: secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"]
     *
     * Explanation:
     *
     * master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
     * master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
     * master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
     * master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
     * master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
     *
     * We made 5 calls to master.guess and one of them was the secret, so we pass the test case.
     * Note:  Any solutions that attempt to circumvent the judge will result in disqualification.
     *
     * Hard
     */

    /**
     *  The probability of two words with 0 match is (25/26)^6 = 80%. That is to say, for a candidate
     *  word, we have 80% chance to see 0 match with the secret word. In this case, we had 80% chance
     *  to eliminate the candidate word and its "family" words which have at least 1 match.
     *
     *  Additionally, in order to delete a max part of words, we select a candidate who has a big "family"
     *  (fewest 0 match with other words).
     */

    /**
     * // This is the Master's API interface.
     * // You should not implement it, or speculate about its implementation
     * interface Master {
     *     public int guess(String word) {}
     * }
     */

    class Solution {
        /**
         * dummy class to resolve compile error
         */
        class Master {
            public int guess(String g) {
                return g.length();
            }
        }

        class Pair {
            public String key;
            public int val;

            public Pair(String key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        public void findSecretWord(String[] wordlist, Master master) {

            for (int i = 0; i < 10; i++) {
                String guess = makeGuess(wordlist);
                int x = master.guess(guess);

                if (x == 6) return;

                List<String> next = new ArrayList<>();
                for (String w : wordlist) {
                    if (match(guess, w) == x) {
                        next.add(w);
                    }
                }

                wordlist = next.toArray(new String[next.size()]);
            }
        }

        private String makeGuess(String[] wordlist) {
            Map<String, Integer> count = new HashMap<>();
            for (String w1 : wordlist) {
                for (String w2 : wordlist) {
                    if (match(w1, w2) == 0) {
                        count.put(w1, count.getOrDefault(w1, 0) + 1);
                    }
                }
            }

            if (count.size() == 0) {
                return wordlist[new Random().nextInt(wordlist.length)];
            } else {
                Pair p = new Pair("", 1000);
                for (Map.Entry<String, Integer> e : count.entrySet()) {
                    System.out.println(e.getKey() + " - " + e.getValue());
                    if (e.getValue() < p.val) {
                        p = new Pair(e.getKey(), e.getValue());
                    }
                }

                return p.key;
            }
        }

        private int match(String a, String b) {
            int matches = 0;
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) == b.charAt(i)) {
                    matches++;
                }
            }
            return matches;
        }
    }
}

