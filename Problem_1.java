// Time Complexity : O(nlogk) --adding 10 elements in priority queue for news feed
// Space Complexity :O(k) --getNewsFeed
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : Yes

class Twitter {
    class Tweet{
        int tweetId;
        int timeStamp;
        public Tweet(int tweetid, int timestamp){
            this.tweetId = tweetid;
            this.timeStamp = timestamp;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> users = userMap.get(userId);
        //minHeap as we need 10 tweets with highest timestamp
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);
        for(Integer user : users){
            List<Tweet> tw = tweetMap.get(user);
            if(tw != null){
                for(Tweet t : tw){
                    pq.add(t);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> feed = new ArrayList<>();
        while(!pq.isEmpty()){
            //Arranging it most recent to least recent
            feed.add(0, pq.poll().tweetId);
        }
        return feed;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);

    }
    
    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId) && followerId != followeeId){
            if(userMap.get(followerId).contains(followeeId)){
                userMap.get(followerId).remove(followeeId);
            }
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */