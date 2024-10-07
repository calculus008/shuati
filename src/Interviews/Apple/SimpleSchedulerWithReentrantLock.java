package Interviews.Apple;

import java.util.PriorityQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleSchedulerWithReentrantLock {
    private final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>((a, b) -> Long.compare(a.scheduledTime, b.scheduledTime));
    private final ReentrantLock lock = new ReentrantLock();      // ReentrantLock for thread-safe access to the queue
    private final Condition newTaskCondition = lock.newCondition(); // Condition for scheduling new tasks

    // Constructor starts the scheduler thread
    public SimpleSchedulerWithReentrantLock() {
        Thread schedulerThread = new Thread(this::runScheduler);
        schedulerThread.start();
    }

    public void schedule(Long time, Runnable task) {
        lock.lock();
        try {
            taskQueue.offer(new ScheduledTask(time, task));  // Add the task to the priority queue
            newTaskCondition.signal();   // Signal the scheduler that a new task is added
        } finally {
            lock.unlock();
        }
    }

    private void runScheduler() { // The scheduler's main loop to check and run tasks
        while (true) {
            lock.lock();
            try {
                while (taskQueue.isEmpty()) {
                    try {
                        newTaskCondition.await();    // Wait until a new task is scheduled
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                ScheduledTask nextTask = taskQueue.peek();
                long currentTime = System.currentTimeMillis();

                if (nextTask != null && nextTask.scheduledTime <= currentTime) { // If the next task is ready to run, remove it from the queue and execute it
                    taskQueue.poll(); // Remove the task from the queue
                    executeTask(nextTask); // Execute the task
                } else {
                    if (nextTask != null) {  // Wait until the next task is ready
                        long waitTime = nextTask.scheduledTime - currentTime;
                        if (waitTime > 0) {
                            newTaskCondition.awaitNanos(waitTime * 1_000_000); // Wait until the task's scheduled time
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Re-interrupt if interrupted
            } finally {
                lock.unlock();
            }
        }
    }

    private void executeTask(ScheduledTask task) {
        new Thread(task.task).start(); // Run the task in a new thread
    }

    static class ScheduledTask {
        long scheduledTime; // Time when the task should be run (epoch time)
        Runnable task;

        public ScheduledTask(Long time, Runnable task) {
            this.scheduledTime = time;
            this.task = task;
        }
    }

    // Test the scheduler
    public static void main(String[] args) {
        SimpleSchedulerWithReentrantLock scheduler = new SimpleSchedulerWithReentrantLock();

        // Schedule tasks with delays
        scheduler.schedule(System.currentTimeMillis() + 2000, () -> System.out.println("Task 1 executed"));
        scheduler.schedule(System.currentTimeMillis() + 1000, () -> System.out.println("Task 2 executed"));
        scheduler.schedule(System.currentTimeMillis() + 3000, () -> System.out.println("Task 3 executed"));
        scheduler.schedule(System.currentTimeMillis() - 1000, () -> System.out.println("Task 4 (late) executed"));

        // Sleep to give time for tasks to run
        try {
            Thread.sleep(5000); // Sleep for 5 seconds to allow tasks to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


