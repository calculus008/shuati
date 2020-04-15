package Interviews.Lyft.lc;

import java.util.ArrayList;
import java.util.List;

public class Buffered_Read {
    /**
     * 包装过的 LE_158_Read_N_Char_Given_Read4_II
     *
     * 给了一个third party pagination API，有两个function：
     * 两个private parameter：PAGE_SIZE=10 和 MAX_VALUE=103。
     * PAGE_SIZE是取一页的Result数目，MAX_VALUE是总共有多少数。
     * 比如MAX_VALUE是103，PAGE_SIZE是10，取11次就可以取完
     *
     * 1. GetResult(int page)：page是起始页，返回值是一个Result class有两个parameter：
     *    int nextPage和ArrayList<Integer> resultList，这个function需要在你的code里被调用
     * 2. GetRange(int min, int max)：从resultList里面取一个range。比如取7-12，就需要调用两
     *    次GetResult function，因为11-12在第二页。这个function只是用于写测试的，写程序不需要
     *
     * 然后因为这个内置的GetRange function不好用需要做一个wrapper就是一个fetcher class，写一个function fetch(int nums). 使用方式如下：
     * fetch(5) 应该对应  GetRange(0, 4)
     * fetch(7) 应该对应  GetRange(5, 11)
     * fetch(200) 应该对应 GetRange(12, 102) 但是因为MAX_VALUE只有103，所以应该就算要200个result也只能返回12-102的数据
     * fetch(10) 应该对应 GetRange(0, 0) 因为没有page了返回空list
     *
     * 第一次是从零开始的，GetResult的输入可以从它上一次的nextPage得到。但是如果检查到nextPage是-1就停止不要再取了要不然就是无限循环
     *
     * 看题花了好久，最后发现就是变形的原题，就开始讲思路和coding。但是这些API还是比较不好写的，写完还有16分钟就开始follow up：
     * 1. 如何处理third party pagination API的exception，答retry based on exception type
     * 2. 如何处理rate limit exception，我开始讲了一通才发现她想问retry导致的rate limit exception，于是又是一通讲。
     *    Reference : https://thoughtbot.com/blog/handling-api-rate-limits
     *
     *    Example :
     *    1.On failure, the job is scheduled again in 5 seconds + N ** 4, where N is the number of retries.
     *    2.Retry our jobs every minute if they fail due to rate limiting
     *
     * 3. 如何改进代码，我多写了一个没用的if，删掉就好了
     */

    class Fetcher {
        class Result {
            int nextPage;
            List<Integer> resultList;
        }

        //dummy
        private Result getResult(int page) {
            return new Result();
        }

        final int PAGE_SIZE = 10;
        final int MAX_VALUE = 103;

        int buffPtr = 0;
        Result curResult;

        public List<Integer> fetch(int n) {
            int count = 0;
            boolean isEnd = false;
            List<Integer> res = new ArrayList<>();

            int size = Math.min(n, MAX_VALUE - ((curResult.nextPage - 2 + 1) * PAGE_SIZE + buffPtr));

            while (!isEnd && count < size) {
                if (buffPtr == 0) {
                    curResult = getResult(curResult.nextPage);
                }

                if (curResult.nextPage == -1) isEnd = true;

                while (count < size && buffPtr < PAGE_SIZE) {
                    res.add(curResult.resultList.get(buffPtr++));
                    count++;
                }

                if (buffPtr >= PAGE_SIZE) {
                    buffPtr = 0;
                }
            }

            return res;
        }
    }

    /**
     * LE_158_Read_N_Char_Given_Read4_II
     *
     *     public class Solution_Practice {
     *         int buffPtr = 0;
     *         int buffCount = 0;
     *         char[] buff = new char[4];
     *
     *         public int read(char[] buf, int n) {
     *             int count = 0;
     *
     *             while (count < n) {
     *                 if (buffPtr == 0) {
     *                     buffCount = read4(buff);
     *                 }
     *
     *                 if (buffCount == 0)  break;
     *
     *                 while (count < n && buffPtr < buffCount) {
     *                     buf[count++] = buff[buffPtr++];
     *                 }
     *
     *                 if (buffPtr >= buffCount) {
     *                     buffPtr = 0;
     *                 }
     *             }
     *
     *             return count;
     *         }
     */
}
