package lintcode;

/**
 * Created by yuank on 7/6/18.
 */
public class LI_437_Copy_Books {
    /**
         Given n books and the ith book has A[i] pages. You are given k people to copy the n books.

         n books list in a row and each person can claim a continous range of the n books.
         For example one copier can copy the books from ith to jth continously, but he can not copy the 1st book,
         2nd book and 4th book (without 3rd book).

         They start copying books at the same time and they all cost 1 minute to copy 1 page of a book.
         What's the best strategy to assign books so that the slowest copier can finish at earliest time?

         给出一个数组A包含n个元素，表示n本书以及各自的页数。现在有个k个人复印书籍，每个人只能复印连续一段编号的书，
         比如A[1],A[2]由第一个人复印，但是不能A[1],A[3]由第一个人复印，求最少需要的时间复印所有书。

         Example
         Given array A = [3,2,4], k = 2.

         Return 5( First person spends 5 minutes to copy book 1 and book 2 and second person spends 4 minutes to copy book 3. )

         Hard
     */

    public int copyBooks(int[] pages, int k) {
        if (pages == null || pages.length == 0) {
            return 0;
        }

        int max = 0;
        int sum = 0;
        for (int p : pages) {
            max = Math.max(max, p);
            sum += p;
        }

        int start = max;
        int end = sum;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (numberOfWorker(pages, mid) <= k) {
                end = mid; //以当前的mid为总共完成copy的时间，用人数目在限定之内，去尝试以更少的时间完成工作。
            } else {
                start = mid; //以当前的mid为总共完成copy的时间，用人数目超出限定，必须去尝试以更多的时间完成工作。
            }
        }

        if (numberOfWorker(pages, start) <= k) {
            return start;
        }

        return end;
    }

    /**
     * 给定时间t, 看需要用多少人copy
     */
    private int numberOfWorker(int[] pages, int t) {
        int sum = 0;
        int count = 1; //开始只有一个worker
        for (int p : pages) {
            if (sum + p > t) {//!!!当前用时超过t,于是我们必须找另外的人来copy
                sum = p;
                count++;
            } else {
                sum += p;
            }
        }

//        count++;//!!!
        return count;
    }
}
