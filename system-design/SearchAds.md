Google, Bing Search Ads
https://www.youtube.com/watch?v=_cpzQ7URREo&t=3524s

1.Advertiser creates adds (keywords, landing page)

2.Query understand :
  Query clean - remove "a", "the" etc
  Stemming - "boy's toy car" -> "boy toy car"
  Tokenize
  Query rewrite
  Category

3.Select Ads
  Inverted index
  Forward index - key : Ad Id, value : Ad info
  Relevance score - Naive : percentage of matching key words
                    Learning to rank : use human to label, then do supervised learning with more features : ctr, impressions etc
                    Relevance feature : look up features extracted from click logs and store in key-value store
                                        Term similarity feature
  pClick - "Clickability", probability of click or estimated CTR
           Given {query, ad, user context}, determine its "clickability"
           A classification problem using supervised learning where label is 1:click and 0:not click
           pClick training diagram (machine learning)

4.Filter Ads
  Filter out ads with extremely low Relevance score and pClick
  Filter out ads with price lower than minReservePrice

5.Rank Ads
  Rank Score : Quality Score * bid
                     |
               0.75 * pClick + 0.25 * Relevance score

6.Select Top K Ads
  Select by Rank Score

7.Pricing and Allocate Ads
  CPC : (next quality score / current quality score) * next bid price + 0.01
  Allocate : mainline or sidebar
             category based threshold mainlineReservePrice and minReservePrice
