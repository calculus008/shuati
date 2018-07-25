https://www.youtube.com/watch?v=_cpzQ7URREo&t=3524s

Advertiser creates adds (keywords, landing page)

Query understand :
  Query clean - remove "a", "the" etc
  Stemming - "boy's toy car" -> "boy toy car"
  Tokenize
  Query rewrite
  Category

Select Ads
  Inverted index
  Forward index - key : adid, value : ad info
  Relevance score - Naive : percentage of matching key words
                    Learning to rank : use human to label, then do supervised learning with more features : ctr, impressions etc
                    Relevance feature : look up features extracted from click logs and store in key-value store
                                        Term similarity feature
  pClick - "Clickability", probability of click or estimated CTR
           Given {query, ad, user context}, determine its "clickability"
           A classification problem using supervised learning where label is 1:click and 0:not click
           pclick traning diagram (machine learning)

Filter Ads
  Filter out ads with extremlhy low Relevance score and pClick
  Filter out ads with price lower than minReservePrice

Rank Ads
  Rank Score : Quality Score * bid
                     |
               0.75 * pClick + 0.25 * Relevance score

Select Top K Ads
  Select by Rank Score

Pricing and Allocate Ads
  CPC : (next quality socre / current quality socre) * next bid price + 0.01
  Allocate : mainline or sidebar
             category based threshold mainlineReservePrice and minReservePrice
