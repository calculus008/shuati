package Interviews.Apple;

import java.util.*;
import java.util.concurrent.*;

public class Concurrent_Request_Limit {
    /**
     * Design a system, each user can only send out n concurrent requests
     */

    /**
     * To design a system where each user can only send out n concurrent requests, you can implement the following approach:
     *
     * Approach:
     * Rate Limiting with Semaphores:
     *
     * Use a semaphore to limit the number of concurrent requests per user. Each user has a unique semaphore initialized
     * with n permits, where n is the maximum allowed concurrent requests.
     * Before processing a request, the semaphore is acquired. Once the request is processed, the semaphore is released,
     * allowing new requests.
     *
     * Tracking Users:
     * Maintain a map (e.g., Map<User, Semaphore>) where each user is associated with their semaphore. This ensures that
     * the semaphore management is specific to each user.
     *
     * Handling New Requests:
     * When a user makes a request, the system checks their associated semaphore:
     * If permits are available (i.e., fewer than n requests are running), the request is allowed and processed.
     * If all permits are used (i.e., the user is at their limit), the request is either rejected or queued.
     */

    class RequestManager {
        private final int maxConcurrentRequests;
        private final Map<String, Semaphore> userLimitMap;

        public RequestManager(int maxRequestsPerUser) {
            this.maxConcurrentRequests = maxRequestsPerUser;
            this.userLimitMap = new ConcurrentHashMap<>();
        }

        public void handleRequest(String userId, Runnable requestTask) throws InterruptedException {
            Semaphore semaphore = userLimitMap.computeIfAbsent(userId, id -> new Semaphore(maxConcurrentRequests)); // Get or create a semaphore for the user

            if (semaphore.tryAcquire()) {// Try acquiring a permit
                try {
                    requestTask.run();// Process the request
                } finally {
                    semaphore.release();// Release the permit after the request is done
                }
            } else {
                System.out.println("Request limit reached for user: " + userId);
                // Optionally queue the request or reject it
            }
        }
    }

}
