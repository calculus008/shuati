package Interviews.Karat;

public class Similar_Users {
    /**
     * We are building a social network where users can like and share content from the web.
     *
     * Given a list of users and content that each user liked, find the user most similar to User X.
     * "Most similar to X" means the person who liked the greatest number of unique pieces of content
     * that User X also liked.
     *
     * For example, User1 liked 3 distinct pages. User3 liked all 3 of those pages, more than anyone
     * else, making them the most similar user to User1.
     *
     * Sample input:
     *
     * # Content, user ID, timestamp
     * user_content_likes = [
     *     ["http://yahoo.com", "user3", "201999"],
     *     ["http://google.com/maps", "user2", "201004"],
     *     ["http://google.com/maps", "user1", "201015"],
     *     ["http://google.com", "user4", "201004"],
     *     ["http://google.com", "user2", "201012"],
     *     ["http://google.com/maps", "user2", "201008"],
     *     ["http://google.com/maps", "user2", "201013"],
     *     ["http://google.com/maps", "user2", "201030"],
     *     ["http://altavista.net/q?f=12344", "user3", "100002"],
     *     ["http://google.com/maps", "user3", "201015"],
     *     ["http://yahoo.com", "user2", "201035"],
     *     ["http://altavista.net/q?f=12344", "user1", "99999"],
     *     ["http://altavista.net/q?f=12344", "user1", "100004"],
     *     ["http://geocities.com", "user1", "100007"],
     *     ["http://geocities.com", "user3", "100009"]]
     *
     * Expected output:
     *
     *
     * find_similar_user(user_content_likes, "user1") => user3
     * find_similar_user(user_content_likes, "user2") => user3
     * find_similar_user(user_content_likes, "user3") => user1
     * find_similar_user(user_content_likes, "user4") => user2
     *
     * Expected output (camelCase):
     *
     * findSimilarUser(userContentLikes, "user1") => user3
     * findSimilarUser(userContentLikes, "user2") => user3
     * findSimilarUser(userContentLikes, "user3") => user1
     * findSimilarUser(userContentLikes, "user4") => user2
     *
     *   input: user1
     *       {"http://google.com/maps", "user1", "201015"},
     *       {"http://altavista.net/q?f=12344", "user1", "99999"},
     *       {"http://geocities.com", "user1", "100007"},
     *
     *   input: user1 {goog, yahoo, sthelse}
     *   ?
     *   get all other users, get their like list, and compare the list with user1
     *
     *   user1:  {goog, yahoo} Map<user, Set(url)
     *
     *   1
     *   ----------------------------
     *   user2:  {goog, yahoo, altavista, hjhgjhg,hjjjj, yyy} +1
     *     user22:  {goog, yahoo } +1
     *
     *   user3:  {goog } +1
     */
}
