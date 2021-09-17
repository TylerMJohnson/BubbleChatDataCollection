# BubbleChatDataCollection

The idea is that potential clients benefit from bubblechat because they receive a raw unfiltered stream of information. They can view what is popular among a crowd of people and how events change the input of the community over time. The value this information provides allows for clients to know exactly how to interact with their community. We live in a world that is always changing, always progressing. What was popular 10+ years ago is more than likely not popular today. The ability to tap into the conscious of the community see what’s important is extremely valuable. A top candidate for a potential client would be the NBA. The NBA uses platforms like twitter to interact with fans always interested in seeing what people are interested in or their reactions. With this new bubble graph, the NBA can see the entirety of their fan bases input over the course of time. 

The goal of this project for our potential customer is to visualize fan feedback in real time as well as the ability to replay. There are many available options where fans can talk about a game. One of these is Reddit. The NBA subreddit at www.reddit.com/r/nba hosts game threads for live games. A WebCrawler will scrape comments and their timestamps and stored in JSON format. This data will need to be cleaned or formatted to properly fit the input restrictions. For example, in the string “it’s a great game!” The word “it’s” and “game!” will be a problem due their punctuation. “It's” the same as “its”.   

**src.org.bubblechat.RedditRipper** - Loads a reddit thread and proceeds to load all comments one by one and save them to Json. This was implemented in a way to avoid using the reddit API or agreeing to their terms of service.

**src.org.bubblechat.DataCleaner** - Cleans and formats the game comments. 

**src.org.bubblechat.GraphGenerator** - Converts data to a format that can easily be inserted into the Front-End (bubblechat.html) you can find this at nba.txt

The rest of the BubbleChat project can be found at https://github.com/TylerMJohnson/WGU_BubbleChat
