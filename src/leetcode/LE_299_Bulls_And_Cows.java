package leetcode;

/**
 * Created by yuank on 4/21/18.
 */
public class LE_299_Bulls_And_Cows {
    /**
         You are playing the following Bulls and Cows game with your friend: You write down a number and ask your friend to guess what the number is.
         Each time your friend makes a guess, you provide a hint that indicates how many digits in said guess match your secret number exactly in both
         digit and position (called "bulls") and how many digits match the secret number but locate in the wrong position (called "cows").
         Your friend will use successive guesses and hints to eventually derive the secret number.

         For example:

         Secret number:  "1807"
         Friend's guess: "7810"
         Hint: 1 bull and 3 cows. (The bull is 8, the cows are 0, 1 and 7.)
         Write a function to return a hint according to the secret number and friend's guess, use A to indicate the bulls and B to indicate the cows.
         In the above example, your function should return "1A3B".

         Please note that both secret number and friend's guess may contain duplicate digits, for example:

         Secret number:  "1123"
         Friend's guess: "0111"
         In this case, the 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow, and your function should return "1A1B".
         You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.

         Medium
     */

    /** Time : O(n), Space : O(1)
     Secret number:  "1807"
     Friend's guess: "7810"
     ----------
     start : i=0, s : count[1]=0, g : count[7]=0
     end :   i=0, s : count[1]=1, g : count[7]=-1
     ----------
     ----------
     start : i=2, s : count[0]=0, g : count[1]=1
     cow++,  i=2, g : count[1]=1
     end :   i=2, s : count[0]=1, g : count[1]=0
     ----------
     ----------
     start : i=3, s : count[7]=-1, g : count[0]=1
     cow++,  i=3, s : count[7]=-1
     cow++,  i=3, g : count[0]=1
     end :   i=3, s : count[7]=0, g : count[0]=0
     ----------

     关键： 每个循环如不等，先判断，再加和减。if逻辑保证每次s和g都是不同的。所以，每次s加g减，那么在后面的循环中，
     如果count[s]小于0,则必然是该值被guess修改过，也就是说曾在guess中不同的位置出现过，所以cow++.反之亦然。

     */
    public String getHint(String secret, String guess) {
        int[] count = new int[10];
        int bull = 0;
        int cow = 0;

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                bull++;
            } else {
                if (count[secret.charAt(i) - '0']++ < 0) cow++;
                if (count[guess.charAt(i) - '0']-- > 0) cow++;

//                 int s = secret.charAt(i) - '0';
//                 int g = guess.charAt(i) - '0';

//                 System.out.println("----------");
//                 System.out.println("start : i=" + i + ", s : count[" + s + "]=" + count[s] + ", g : count[" + g + "]=" + count[g]);

//                 if (count[s] < 0) {
//                     System.out.println("cow++, i=" + i + ", s : count[" + s + "]=" + count[s]);
//                     cow++;
//                 }
//                 if (count[g] > 0) {
//                     System.out.println("cow++, i=" + i + ", g : count[" + g + "]=" + count[g]);
//                     cow++;
//                 }
//                 count[s]++;
//                 count[g]--;

//                 System.out.println("end : i=" + i + ", s : count[" + s + "]=" + count[s] + ", g : count[" + g + "]=" + count[g]);
//                 System.out.println("----------");
            }
        }

        return bull + "A" + cow + "B";
    }
}
