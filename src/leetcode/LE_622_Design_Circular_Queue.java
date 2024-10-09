package leetcode;

import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

public class LE_622_Design_Circular_Queue {
    /**
     * Design your implementation of the circular queue. The circular queue is a linear
     * data structure in which the operations are performed based on FIFO (First In First Out)
     * principle and the last position is connected back to the first position to make a circle.
     * It is also called "Ring Buffer".
     *
     * One of the benefits of the circular queue is that we can make use of the spaces in front
     * of the queue. In a normal queue, once the queue becomes full, we cannot insert the next
     * element even if there is a space in front of the queue. But using the circular queue,
     * we can use the space to store new values.
     *
     * Your implementation should support following operations:
     *
     * MyCircularQueue(k): Constructor, set the size of the queue to be k.
     * Front: Get the front item from the queue. If the queue is empty, return -1.
     * Rear: Get the last item from the queue. If the queue is empty, return -1.
     * enQueue(value): Insert an element into the circular queue. Return true if the operation is successful.
     * deQueue(): Delete an element from the circular queue. Return true if the operation is successful.
     * isEmpty(): Checks whether the circular queue is empty or not.
     * isFull(): Checks whether the circular queue is full or not.
     *
     *
     * Example:
     *
     * MyCircularQueue circularQueue = new MyCircularQueue(3); // set the size to be 3
     * circularQueue.enQueue(1);  // return true
     * circularQueue.enQueue(2);  // return true
     * circularQueue.enQueue(3);  // return true
     * circularQueue.enQueue(4);  // return false, the queue is full
     * circularQueue.Rear();  // return 3
     * circularQueue.isFull();  // return true
     * circularQueue.deQueue();  // return true
     * circularQueue.enQueue(4);  // return true
     * circularQueue.Rear();  // return 4
     *
     * Note:
     * All values will be in the range of [0, 1000].
     * The number of operations will be in the range of [1, 1000].
     * Please do not use the built-in Queue library.
     *
     * Medium
     */

    class MyCircularQueue_Array {
        final int[] a;
        int front, rear = -1, len = 0;

        public MyCircularQueue_Array(int k) {
            a = new int[k];
        }

        /**
         * enqueue puts new element at the end of the array, which rear pointer points to.
         *
         * rear pointer is where add() and offer() (Java Queue Interface) happen.
         */
        public boolean enQueue(int val) {
            if (!isFull()) {
                rear = (rear + 1) % a.length;
                a[rear] = val;
                len++;
                return true;
            } else {
                return false;
            }
        }

        /**
         * Delete an element from the circular queue. Return true if the operation is successful.
         *
         * With FIFO, deQueue() needs to delete element at the idx that front pointer points to.
         * front pointers is the where peek() and poll() (Java Queue Interface) happen.
         * */
        public boolean deQueue() {
            if (!isEmpty()) {
                front = (front + 1) % a.length;
                len--;
                return true;
            } else {
                return false;
            }
        }

        /**
         * Front here is the head of the queue
         */
        public int Front() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            } else {
                return a[front];
            }
//            return isEmpty() ? -1 : a[front];
        }

        /**
         * Rear here is the tail of the queue
         */
        public int Rear() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            } else {
                return a[rear];
            }
//            return isEmpty() ? -1 : a[rear];
        }

        public boolean isEmpty() {
            return len == 0;
        }

        public boolean isFull() {
            return len == a.length;
        }
    }

    /**
     * **********************************
     */

    class Node {
        public int value;
        public Node nextNode;

        public Node(int value) {
            this.value = value;
            this.nextNode = null;
        }
    }

    class MyCircularQueue_LinkedList {
        private Node head, tail;
        private int count;
        private int capacity;

        /** Initialize your data structure here. Set the size of the queue to be k. */
        public MyCircularQueue_LinkedList(int k) {
            this.capacity = k;
        }

        /** Insert an element into the circular queue. Return true if the operation is successful. */
        public boolean enQueue(int value) {
            if (this.count == this.capacity) return false;

            Node newNode = new Node(value);
            if (this.count == 0) {
                head = tail = newNode;
            } else {
                tail.nextNode = newNode;
                tail = newNode;
            }

            this.count++;
            return true;
        }

        /** Delete an element from the circular queue. Return true if the operation is successful. */
        public boolean deQueue() {
            if (this.count == 0) return false;

            this.head = this.head.nextNode;
            this.count--;
            return true;
        }

        /** Get the front item from the queue. */
        public int Front() {
            if (this.count == 0) {
//                return -1;
                throw new NoSuchElementException();
            } else {
                return this.head.value;
            }
        }

        /** Get the last item from the queue. */
        public int Rear() {
            if (this.count == 0) {
//                return -1;
                throw new NoSuchElementException();
            } else {
                return this.tail.value;
            }
        }

        /** Checks whether the circular queue is empty or not. */
        public boolean isEmpty() {
            return (this.count == 0);
        }

        /** Checks whether the circular queue is full or not. */
        public boolean isFull() {
            return (this.count == this.capacity);
        }
    }

    /**
     * Follow up - thread safe
     *
     * https://leetcode.com/problems/design-circular-queue/solution/
     *
     * Improvement: Thread-Safe
     * This time, it is not about the space or time complexity, but concurrency. Our circular queue
     * is NOT thread-safe. One could end up with corrupting our data structure in a multi-threaded environment.
     *
     * For example, here is an execution sequence where we exceed the designed capacity of the queue and
     * overwrite the tail element undesirably.
     */
    class MyCircularQueue_With_Lock {
        private Node head, tail;
        private int count;
        private int capacity;
        private final ReentrantLock lock = new ReentrantLock();  // Lock object for thread-safety

        public MyCircularQueue_With_Lock(int k) {
            this.capacity = k;
        }

        public boolean enQueue(int value) {
            lock.lock();  // Acquire the lock
            try {
                if (this.count == this.capacity) return false;

                Node newNode = new Node(value);
                if (this.count == 0) {
                    head = tail = newNode;
                } else {
                    tail.nextNode = newNode;
                    tail = newNode;
                }

                this.count++;
                return true;
            } finally {
                lock.unlock();  // Always release the lock
            }
        }

        public boolean deQueue() {
            lock.lock();
            try {
                if (this.count == 0) return false;

                this.head = this.head.nextNode;
                this.count--;
                return true;
            } finally {
                lock.unlock();
            }
        }

        public int Front() {
            lock.lock();
            try {
                if (this.count == 0) {
                    throw new NoSuchElementException();
                } else {
                    return this.head.value;
                }
            } finally {
                lock.unlock();
            }
        }

        public int Rear() {
            lock.lock();
            try {
                if (this.count == 0) {
                    throw new NoSuchElementException();
                } else {
                    return this.tail.value;
                }
            } finally {
                lock.unlock();
            }
        }

        public boolean isEmpty() {
            lock.lock();
            try {
                return (this.count == 0);
            } finally {
                lock.unlock();
            }
        }

        public boolean isFull() {
            lock.lock();
            try {
                return (this.count == this.capacity);
            } finally {
                lock.unlock();
            }
        }
    }

}
