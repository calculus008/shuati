package leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LE_1188_Design_Bounded_Blocking_Queue {
    /**
     * Implement a thread safe bounded blocking queue that has the following methods:
     *
     * BoundedBlockingQueue(int capacity) The constructor initializes the queue with a maximum capacity.
     * void enqueue(int element) Adds an element to the front of the queue. If the queue is full,
     * the calling thread is blocked until the queue is no longer full.
     * int dequeue() Returns the element at the rear of the queue and removes it. If the queue is empty,
     * the calling thread is blocked until the queue is no longer empty.
     * int size() Returns the number of elements currently in the queue.
     * Your implementation will be tested using multiple threads at the same time. Each thread will either
     * be a producer thread that only makes calls to the enqueue method or a consumer thread that only makes
     * calls to the dequeue method. The size method will be called after every test case.
     *
     * Please do not use built-in implementations of bounded blocking queue as this will not be accepted in an interview.
     *
     * Example 1:
     *
     * Input:
     * 1
     * 1
     * ["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
     * [[2],[1],[],[],[0],[2],[3],[4],[]]
     *
     * Output:
     * [1,0,2,2]
     *
     * Explanation:
     * Number of producer threads = 1
     * Number of consumer threads = 1
     *
     * BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // initialize the queue with capacity = 2.
     *
     * queue.enqueue(1);   // The producer thread enqueues 1 to the queue.
     * queue.dequeue();    // The consumer thread calls dequeue and returns 1 from the queue.
     * queue.dequeue();    // Since the queue is empty, the consumer thread is blocked.
     * queue.enqueue(0);   // The producer thread enqueues 0 to the queue. The consumer thread is unblocked and returns 0 from the queue.
     * queue.enqueue(2);   // The producer thread enqueues 2 to the queue.
     * queue.enqueue(3);   // The producer thread enqueues 3 to the queue.
     * queue.enqueue(4);   // The producer thread is blocked because the queue's capacity (2) is reached.
     * queue.dequeue();    // The consumer thread returns 2 from the queue. The producer thread is unblocked and enqueues 4 to the queue.
     * queue.size();       // 2 elements remaining in the queue. size() is always called at the end of each test case.
     *
     *
     * Example 2:
     *
     * Input:
     * 3
     * 4
     * ["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
     * [[3],[1],[0],[2],[],[],[],[3]]
     *
     * Output:
     * [1,0,2,1]
     *
     * Explanation:
     * Number of producer threads = 3
     * Number of consumer threads = 4
     *
     * BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // initialize the queue with capacity = 3.
     *
     * queue.enqueue(1);   // Producer thread P1 enqueues 1 to the queue.
     * queue.enqueue(0);   // Producer thread P2 enqueues 0 to the queue.
     * queue.enqueue(2);   // Producer thread P3 enqueues 2 to the queue.
     * queue.dequeue();    // Consumer thread C1 calls dequeue.
     * queue.dequeue();    // Consumer thread C2 calls dequeue.
     * queue.dequeue();    // Consumer thread C3 calls dequeue.
     * queue.enqueue(3);   // One of the producer threads enqueues 3 to the queue.
     * queue.size();       // 1 element remaining in the queue.
     *
     * Since the number of threads for producer/consumer is greater than 1, we do not know how the threads will be
     * scheduled in the operating system, even though the input seems to imply the ordering. Therefore, any of the
     * output [1,0,2] or [1,2,0] or [0,1,2] or [0,2,1] or [2,0,1] or [2,1,0] will be accepted.
     *
     * Medium
     */

    /**
     * ReentrantLock + Condition Solution
     *
     * Lock is an interface. It defines a set of methods that all locks should have.
     *
     * ReentrantLock is a concrete class that implements the Lock interface. It implements all the methods
     * defined in Lock, plus much more. Additionally, as mentioned in the name, the lock is re-entrant which
     * means the same thread can acquire the lock as many times as it wants. This is essentially the same
     * behavior as the native object monitor locks provided by the synchronized keyword.
     *
     * Though ReentrantLock provides same visibility and orderings guaranteed as implicit lock, acquired
     * by synchronized keyword in Java, it provides more functionality and differ in certain aspect. As stated
     * earlier,  main difference between synchronized and ReentrantLock is ability to trying for lock interruptibly,
     * and with timeout. Thread doesn’t need to block infinitely, which was the case with synchronized.
     *
     * 1) Another significant difference between ReentrantLock and synchronized keyword is fairness. synchronized
     *    keyword doesn't support fairness. Any thread can acquire lock once released, no preference can be specified,
     *    on the other hand you can make ReentrantLock fair by specifying fairness property, while creating instance
     *    of ReentrantLock. Fairness property provides lock to longest waiting thread, in case of contention.
     *
     * 2) Second difference between synchronized and Reentrant lock is tryLock() method. ReentrantLock provides
     *    convenient tryLock() method, which acquires lock only if its available or not held by any other thread.
     *    This reduce waiting for lock in Java application.
     *
     * 3) One more worth noting difference between ReentrantLock and synchronized keyword in Java is, ability to
     *    interrupt Thread while waiting for Lock. In case of synchronized keyword, a thread can be blocked waiting
     *    for lock, for an indefinite period of time and there was no way to control that. ReentrantLock provides a
     *    method called lockInterruptibly(), which can be used to interrupt thread when it is waiting for lock.
     *    Similarly tryLock() with timeout can be used to timeout if lock is not available in certain time period.
     *
     * 4) ReentrantLock also provides convenient method to get List of all threads waiting for lock.
     *
     * Summary of Reentrantlock advantage over synchronized:
     * 1) Ability to lock interruptibly.
     * 2) Ability to timeout while waiting for lock.
     * 3) Power to create fair lock.
     * 4) API to get list of waiting thread for lock.
     * 5) Flexibility to try for lock without blocking.
     *
     * Major drawback of using ReentrantLock in Java is wrapping method body inside try-finally block, which makes
     * code unreadable and hides business logic. It’s really cluttered.
     *
     * Another disadvantage is that, now programmer is responsible for acquiring and releasing lock, which is a power
     * but also opens gate for new subtle bugs, when programmer forget to release the lock in finally block.
     *
     * Read more: https://javarevisited.blogspot.com/2013/03/reentrantlock-example-in-java-synchronized-difference-vs-lock.html#ixzz6LQPKiBMO
     *
     * Condition variables are instance of java.util.concurrent.locks.Condition class, which provides inter
     * thread communication methods similar to wait, notify and notifyAll e.g. await(), signal() and signalAll().
     * So if one thread is waiting on a condition by calling condition.await() then once that condition changes,
     * second thread can call condition.signal() or condition.signalAll() method to notify that its time to wake-up,
     * condition has been changed.
     *
     * Read more: https://javarevisited.blogspot.com/2015/06/java-lock-and-condition-example-producer-consumer.html#ixzz6LQRuTZ2b
     */
    class BoundedBlockingQueue {
        private ReentrantLock lock = new ReentrantLock();
        private Condition full = lock.newCondition();
        private Condition empty = lock.newCondition();
        private int[] queue;
        private int tail = 0;
        private int head = 0;
        private int size = 0;

        public BoundedBlockingQueue(int capacity) {
            queue = new int[capacity];
        }

        public void enqueue(int element) throws InterruptedException {
            lock.lock();
            try {
                while(size == queue.length) {
                    full.await();
                }
                queue[tail++] = element;
                tail %= queue.length;
                size++;
                empty.signal();
            } finally {
                lock.unlock();
            }
        }

        public int dequeue() throws InterruptedException {
            lock.lock();
            try {
                while(size == 0) {
                    empty.await();
                }
                int res = queue[head++];
                head %= queue.length;
                size--;
                full.signal();

                return res;
            } finally {
                lock.unlock();
            }
        }

        public int size() throws InterruptedException {
            lock.lock();
            try {
                return this.size;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * https://itsallbinary.com/java-synchronized-vs-semaphore/
     *
     * While initializing Semaphore, we can mention how many locks/permits we want
     * to allow on particular resource. In this case 3 i.e. new Semaphore(3);
     *
     * Unlike synchronized, in case of Semaphores, code needs to handle acquiring & releasing
     * locks/permits. In case of synchronized this was taken care by JVM itself.
     *
     * https://www.geeksforgeeks.org/producer-consumer-solution-using-semaphores-java/
     */
    class BoundedBlockingQueue_Semaphore {
        private Semaphore enq;
        private Semaphore deq;

        /**
         * use a collection that is thread safe here because,
         * up to capacity number of concurrent threads would
         * are able to add values into the queue
         */
        ConcurrentLinkedDeque<Integer> q;

        public BoundedBlockingQueue_Semaphore(int capacity) {
            q =  new ConcurrentLinkedDeque<>();
            enq = new Semaphore(capacity);

            /**
             * This ensures that enqueue( ) executes first. The ability to set the initial
             * synchronization state is one of the more powerful aspects of a semaphore.
             */
            deq = new Semaphore(0);
        }

        public void enqueue(int element) throws InterruptedException {
            try {
                enq.acquire();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }

            q.add(element);

            /**
             * After producer produces the item,
             * it releases deq to notify consumer
             */
            deq.release();
        }

        public int dequeue() throws InterruptedException {
            try {
                deq.acquire();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }

            int val = q.poll();

            /**
             * After consumer consumes the item,
             * it releases enq to notify producer
             */
            enq.release();
            return val;
        }

        public int size() {
            return q.size();
        }
    }

    class BoundedBlockingQueue_Lock {
        Deque<Integer> deq;
        int size;
        Object lock;

        public BoundedBlockingQueue_Lock(int capacity) {
            deq = new LinkedList<>();
            size = capacity;
            lock = new Object();
        }

        public void enqueue(int element) throws InterruptedException {
            synchronized(lock) {
                while(deq.size() == size) {
                    lock.wait();
                }
                deq.addLast(element);
                lock.notify();
            }
        }

        public int dequeue() throws InterruptedException {
            int val = 0;

            synchronized(lock) {
                while(deq.isEmpty()) {
                    lock.wait();
                }
                val = deq.removeFirst();
                lock.notify();
            }

            return val;
        }

        public int size() {
            return deq.size();
        }
    }
}
