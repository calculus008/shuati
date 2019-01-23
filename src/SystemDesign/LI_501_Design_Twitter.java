package SystemDesign;

import java.util.*;
import common.Tweet;

public class LI_501_Design_Twitter {
    /**
     * Implement a simple twitter. Support the following method:
     *
     * postTweet(user_id, tweet_text). Post a tweet.
     *
     * getTimeline(user_id). Get the given user's most recently 10 tweets posted by himself,
     * order by timestamp from most recent to least recent.
     *
     * getNewsFeed(user_id). Get the given user's most recently 10 tweets in his news feed
     * (posted by his friends and himself). Order by timestamp from most recent to least recent.
     *
     * follow(from_user_id, to_user_id). from_user_id followed to_user_id.
     *
     * unfollow(from_user_id, to_user_id). from_user_id unfollowed to to_user_id.
     *
     *
     * Example
     * postTweet(1, "LintCode is Good!!!")
     * >> 1
     * getNewsFeed(1)
     * >> [1]
     * getTimeline(1)
     * >> [1]
     * follow(2, 1)
     * getNewsFeed(2)
     * >> [1]
     * unfollow(2, 1)
     * getNewsFeed(2)
     * >> []
     */

    public class MiniTwitter {
        public class TweetComparator implements Comparator<Tweet> {
            public int compare(Tweet t1, Tweet t2){
                return t1.id - t2.id;
            }
        }

        Map<Integer, List<Tweet>> tweetMap;
        Map<Integer, List<Integer>> followerMap;


        public MiniTwitter() {
            // do intialization if necessary
            tweetMap = new HashMap<>();
            followerMap = new HashMap<>();
        }

        /*
         * @param user_id: An integer
         * @param tweet_text: a string
         * @return: a tweet
         */
        public Tweet postTweet(int user_id, String tweet_text) {
            // write your code here
            Tweet newTweet = Tweet.create(user_id, tweet_text);
            if(!tweetMap.containsKey(user_id)){
                List<Tweet> l = new ArrayList<>();
                tweetMap.put(user_id, l);
            }
            List<Tweet> currList = tweetMap.get(user_id);
            currList.add(0,newTweet);
            if(currList.size() > 10){
                currList.remove(currList.size()-1);
            }

            if(!followerMap.containsKey(user_id)){
                follow(user_id, user_id);
            }

            return newTweet;
        }

        /*
         * @param user_id: An integer
         * @return: a list of 10 new feeds recently and sort by timeline
         */
        public List<Tweet> getNewsFeed(int user_id) {


            if(!followerMap.containsKey(user_id)){
                return new ArrayList<>();
            }

            List<Integer> followList = followerMap.get(user_id);


            PriorityQueue<Tweet> pq = new PriorityQueue<>(new TweetComparator());
            for(Integer followId: followList)
            {
                if(tweetMap.get(followId) == null){
                    continue;
                }
                for(Tweet t: tweetMap.get(followId)){
                    pq.offer(t);
                    if(pq.size() > 10)
                    {
                        pq.poll();
                    }
                }
            }

            List<Tweet> results = new ArrayList<>();
            while(!pq.isEmpty()){
                results.add(0, pq.poll());
            }

            return results;

        }

        /*
         * @param user_id: An integer
         * @return: a list of 10 new posts recently and sort by timeline
         */
        public List<Tweet> getTimeline(int user_id) {
            // write your code here
            if(!tweetMap.containsKey(user_id)){
                return new ArrayList<>();
            }
            return tweetMap.get(user_id);
        }

        /*
         * @param from_user_id: An integer
         * @param to_user_id: An integer
         * @return: nothing
         */
        public void follow(int from_user_id, int to_user_id) {

            if(!followerMap.containsKey(from_user_id)){
                List<Integer> l = new ArrayList<>();
                l.add(from_user_id);
                followerMap.put(from_user_id, l);
            }
            List<Integer> curList = followerMap.get(from_user_id);
            if(curList.contains(to_user_id))
            {
                return;
            }
            curList.add(to_user_id);
            followerMap.put(from_user_id, curList);

        }

        /*
         * @param from_user_id: An integer
         * @param to_user_id: An integer
         * @return: nothing
         */
        public void unfollow(int from_user_id, int to_user_id) {
            // write your code here
            if(!followerMap.containsKey(from_user_id)){
                List<Integer> l = new ArrayList<>();
                l.add(from_user_id);
                followerMap.put(from_user_id, l);
            }
            List<Integer> curList = followerMap.get(from_user_id);
            if(!curList.contains(to_user_id))
            {
                return;
            }
            curList.remove(new Integer(to_user_id));
            followerMap.put(from_user_id, curList);
        }
    }
}