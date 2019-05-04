package Interviews.Linkedin;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer_Consumer {
    final static ReentrantLock lock = new ReentrantLock();
    final static Condition removeCondition = lock.newCondition();
    final static Condition addCondition = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static AtomicInteger capacity = new AtomicInteger();

    public static void main(String[] args) {
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                try {
                    while (queue.size() == capacity.intValue()) {
                        addCondition.await();
                    }

                    queue.add("elem");
                    removeCondition.signalAll();
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }

            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                try {
                    while (queue.size() == 0) {
                        removeCondition.await();
                    }

                    queue.remove("elem");
                    addCondition.signalAll();
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }

            }
        });

        producer.start();
        consumer.start();
    }


}
