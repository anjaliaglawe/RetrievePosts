package com.retrieve.feed;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.retrieve.feed.model.Feed;

public class RetrievePostsTest {

	ArrayList<Feed> feedList = new ArrayList<Feed>();
	
    
    @Before
    public void initInputs(){
    	Feed f1 = new Feed();
    	f1.setId("1");
    	f1.setPublish_date("2012-03-17T15:41:00+00:00");
    	Feed f2  = new Feed();
    	f2.setPublish_date("2014-11-26T17:57:00+00:00");
    	f2.setId("2");
    	Feed f3 = new Feed();
    	f3.setPublish_date("2013-12-26T17:57:00+00:00");
    	f3.setId("3");
    	feedList.add(f1);
    	feedList.add(f2);
    	feedList.add(f3);
    }
     
    @Test
    public void sortFeedListTest(){
      FeedUtils util = new FeedUtils();
      util.sortFeedList(feedList);
      assertEquals(feedList.get(0).getId() , "2");
      assertEquals(feedList.get(1).getId() , "3");
      assertEquals(feedList.get(2).getId() , "1");
    }

    
    @Test
    public void parseFeedListTest(){
      String jsonString = "[{'id':'7a26a22b-7a26-4248-a844-5738c13e26d3','title':'Yummy','author':{'name':'Nemo Fish'}}]";

      FeedUtils util = new FeedUtils();
      ArrayList<Feed> postList =  util.parseFeedList(jsonString);
      assertEquals(postList.size() , 1);
      assertEquals(postList.get(0).getTitle() , "Yummy");
      assertEquals(postList.get(0).getAuthor().getName() , "Nemo Fish");

    }
}