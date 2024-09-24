package Interviews.Apple;

import java.util.*;

public class Zig_Zag_Iterator {
    /**
     * Given a List of iterators, write a function that outputs the values in a zigzag.
     * ex: input :
     * iterator 1 -> [1,2]
     * iterator 2 -> [5,6,7,8]
     * iterator 3 -> [9,10,11]
     * output: 1,5,9,2,‍‌‍‍‍‍‌‌‌‍‌‌‍‌‍‌‌‌‌6,10,7,11,8
     */

    public class ZigzagIterator {
        private Queue<Iterator<Integer>> iteratorQueue;

        public ZigzagIterator(List<Iterator<Integer>> iterators) {
            iteratorQueue = new LinkedList<>();
            for (Iterator<Integer> it : iterators) {
                if (it.hasNext()) {
                    iteratorQueue.offer(it);
                }
            }
        }

        public List<Integer> zigzagOrder() {
            List<Integer> result = new ArrayList<>();

            while (!iteratorQueue.isEmpty()) {
                // Get the next iterator from the queue
                Iterator<Integer> current = iteratorQueue.poll();

                // Fetch the next value from the iterator and add to result
                result.add(current.next());

                // If the iterator still has elements, add it back to the queue
                if (current.hasNext()) {
                    iteratorQueue.offer(current);
                }
            }

            return result;
        }

//        public static void main(String[] args) {
//            List<Integer> list1 = Arrays.asList(1, 2);
//            List<Integer> list2 = Arrays.asList(5, 6, 7, 8);
//            List<Integer> list3 = Arrays.asList(9, 10, 11);
//
//            // Create iterators for the lists
//            List<Iterator<Integer>> iterators = Arrays.asList(list1.iterator(), list2.iterator(), list3.iterator());
//
//            ZigzagIterator zigzagIterator = new ZigzagIterator(iterators);
//            List<Integer> result = zigzagIterator.zigzagOrder();
//
//            // Print the result in zigzag order
//            System.out.println(result);  // Output: [1, 5, 9, 2, 6, 10, 7, 11, 8]
//        }
    }

}
