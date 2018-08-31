package lintcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 8/31/18.
 */
public class LI_494_Implement_Stack_By_Two_Queues {
    /**
         Implement a stack by two queues. The queue is first in first out (FIFO).
         That means you can not directly pop the last element in a queue.
     */

    public class Stack {
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        /*
         * @param x: An integer
         * @return: nothing
         */
        public void push(int x) {
            q1.offer(x);
        }

        /*
         * @return: nothing
         */
        public void pop() {
            moveItems();
            q1.poll();
            swap();
        }

        /*
         * @return: An integer
         */
        public int top() {
            moveItems();

            int res = q1.peek();
            q2.offer(q1.poll());

            swap();

            return res;
        }

        /*
         * @return: True if the stack is empty
         */
        public boolean isEmpty() {
            return q1.isEmpty() && q2.isEmpty();
        }

        private void moveItems() {
            while (q1.size() > 1) {
                q2.offer(q1.poll());
            }
        }

        //!!!
        private void swap() {
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;
        }

    }
}
