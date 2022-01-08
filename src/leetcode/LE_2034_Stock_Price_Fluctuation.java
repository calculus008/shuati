package leetcode;

import java.util.*;

public class LE_2034_Stock_Price_Fluctuation {
    /**
     * You are given a stream of records about a particular stock. Each record contains a timestamp and the corresponding
     * price of the stock at that timestamp.
     *
     * Unfortunately due to the volatile nature of the stock market, the records do not come in order. Even worse, some
     * records may be incorrect. Another record with the same timestamp may appear later in the stream correcting the
     * price of the previous wrong record.
     *
     * Design an algorithm that:
     *
     * Updates the price of the stock at a particular timestamp, correcting the price from any previous records at the timestamp.
     * Finds the latest price of the stock based on the current records. The latest price is the price at the latest timestamp recorded.
     * Finds the maximum price the stock has been based on the current records.
     * Finds the minimum price the stock has been based on the current records.
     * Implement the StockPrice class:
     *
     * StockPrice() Initializes the object with no price records.
     * void update(int timestamp, int price) Updates the price of the stock at the given timestamp.
     * int current() Returns the latest price of the stock.
     * int maximum() Returns the maximum price of the stock.
     * int minimum() Returns the minimum price of the stock.
     *
     * Example 1:
     * Input
     * ["StockPrice", "update", "update", "current", "maximum", "update", "maximum", "update", "minimum"]
     * [[], [1, 10], [2, 5], [], [], [1, 3], [], [4, 2], []]
     * Output
     * [null, null, null, 5, 10, null, 5, null, 2]
     *
     * Explanation
     * StockPrice stockPrice = new StockPrice();
     * stockPrice.update(1, 10); // Timestamps are [1] with corresponding prices [10].
     * stockPrice.update(2, 5);  // Timestamps are [1,2] with corresponding prices [10,5].
     * stockPrice.current();     // return 5, the latest timestamp is 2 with the price being 5.
     * stockPrice.maximum();     // return 10, the maximum price is 10 at timestamp 1.
     * stockPrice.update(1, 3);  // The previous timestamp 1 had the wrong price, so it is updated to 3.
     *                           // Timestamps are [1,2] with corresponding prices [3,5].
     * stockPrice.maximum();     // return 5, the maximum price is 5 after the correction.
     * stockPrice.update(4, 2);  // Timestamps are [1,2,4] with corresponding prices [3,5,2].
     * stockPrice.minimum();     // return 2, the minimum price is 2 at timestamp 4.
     *
     *
     * Constraints:
     * 1 <= timestamp, price <= 109
     * At most 105 calls will be made in total to update, current, maximum, and minimum.
     * current, maximum, and minimum will be called only after update has been called at least once.
     *
     * Medium
     *
     * https://leetcode.com/problems/stock-price-fluctuation/
     */

    /**
     * TreeMap
     *
     * Since we need to update existing record and this change will also impact min and max value, we need to use a TreeMap
     * to keep the current price in sorted order.
     */
    class StockPrice1 {
        Map<Integer, Integer> map;
        TreeMap<Integer, Integer> count;
        int curTime;

        public StockPrice1() {
            map = new HashMap<>();
            count = new TreeMap<>();
            curTime = 0;
        }

        //O(nlogn) : n is the number of unique price numbers.
        public void update(int timestamp, int price) {
            if (map.containsKey(timestamp)) {
                int curPrice = map.get(timestamp);

                count.put(curPrice, count.get(curPrice) - 1);
                if (count.get(curPrice) == 0) {
                    count.remove(curPrice);
                }
            }

            map.put(timestamp, price);
            count.put(price, count.getOrDefault(price, 0) + 1);

            curTime = Math.max(curTime, timestamp);
        }

        //O(1)
        public int current() {
            return map.get(curTime);
        }

        //O(1)
        public int maximum() {
            return count.lastKey();
        }

        //O(1)
        public int minimum() {
            return count.firstKey();
        }
    }

    /**
     * Heap
     *
     * Use minHeap and maxHeap so we can get min and max, the tricky part is that we need to verify if the top element in
     * the heap is the valid one - the price AND timestamp pair exists in map, otherwise, it means it has been updated and
     * not valid, it should be removed.
     *
     * The clever part for this solution is that we don't actually update max and min by removing anything, it just keeps
     * adding Stock object into minHeap and maxHeap, we remove the out-of-date element when min() and max() are called.
     *
     * Not sure why this solution runs faster than solution above.
     *
     */
    class StockPrice {
        class Stock{
            int timestamp;
            int price;

            public Stock(int timestamp, int price) {
                this.timestamp = timestamp;
                this.price = price;
            }
        }

        PriorityQueue<Stock> maxHeap;
        PriorityQueue<Stock> minHeap;
        HashMap<Integer, Integer> map;
        int price = 0;
        int timestamp = 0;

        public StockPrice() {
            map = new HashMap<>();
            maxHeap = new PriorityQueue<>((s1, s2) -> s2.price - s1.price);
            minHeap = new PriorityQueue<>((s1, s2) -> s1.price - s2.price);
        }

        //O(nlogn) : n is number of all streamed in records.
        public void update(int timestamp, int price) {
            map.put(timestamp, price);

            Stock newS = new Stock(timestamp, price);
            maxHeap.offer(newS);
            minHeap.offer(newS);

            if(this.timestamp <= timestamp){
                this.timestamp = timestamp;
                this.price = price;
            }
        }

        public int current() {
            return this.price;
        }

        public int maximum() {
            while(!maxHeap.isEmpty()){
                Stock top = maxHeap.peek();
                if(map.get(top.timestamp) == top.price){
                    return top.price;
                }
                maxHeap.poll();
            }
            return -1;
        }

        public int minimum() {
            while(!minHeap.isEmpty()){
                Stock top = minHeap.peek();
                if(map.get(top.timestamp) == top.price){
                    return top.price;
                }
                minHeap.poll();
            }
            return -1;
        }
    }


}
