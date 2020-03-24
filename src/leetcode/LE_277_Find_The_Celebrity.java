package leetcode;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_277_Find_The_Celebrity {
    /**
     * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity.
     * The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.

     Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions
     like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one)
     by asking as few questions as possible (in the asymptotic sense).

     You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int findCelebrity(n),
     your function should minimize the number of calls to knows.

     Note: There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party.
     If there is no celebrity, return -1.
     */

    /**
     * The moment you realize a call to knows(i,j) eliminates either i or j,
     * the problem is solved. knows(i,j) == true then i can't be a celeb.
     * since a celeb knows nobody and knows(i,j) == false then j can't be
     * a celeb since everyone must know the celeb.
     *
     * After first pass, let's assume candidate = i;
     * (1)It means all the other people before i at least know someone or
     *    it will not pass the candidate position to i. Thus people before i
     *    won't be candidate.
     * (2)Also, people after i are those who i do not know, which does not
     *    satisfy the celebrity definition either. Thus people i is the only
     *    person who have chance to be celebrity.
     * (3)Thus, for second pass, what we need to do is just check i's qualification
     *    that he/she does not know anyone and everyone knows him/she.
     *
     * Same problem : LE_997_Find_The_Town_Judge
     */
    public int findCelebrity(int n) {
        int candidate = 0;
        for (int i = 1; i < n; i++) {
            //Since celebrity knows no none, so if the following check is true, then we know current candidate is not celebrity,
            // we change candidate to i
            if (knows(candidate, i)) {
                candidate = i;
            }
        }

        //Verify if candidate is celebrity
        for (int i = 0; i < n; i++) {
            if (i != candidate && (knows(candidate, i) || !knows(i, candidate))) {
                return -1;
            }
        }

        return candidate;
    }

    /**
     * 变种，把api改成了矩阵作为input表示i是否认识j，很简单自己写个函数当api就行了
     *
     *      boolean knows(boolean[][] r, int i, int j) {
     *         return r[i][j];
     *      }
     */

    //dummy method to remove compiler error
    boolean knows(int i, int j) {
        return true;
    }
}
