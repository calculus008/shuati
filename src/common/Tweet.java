package common;

public class Tweet {
      public int id;
      public int user_id;
      public String text;
      public static Tweet create(int user_id, String tweet_text) {
          // This will create a new tweet object,
          // and auto fill id
          return new Tweet();
      }
}