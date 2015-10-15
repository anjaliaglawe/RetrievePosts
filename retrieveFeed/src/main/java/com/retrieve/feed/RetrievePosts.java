package com.retrieve.feed;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.retrieve.feed.model.Feed;

public class RetrievePosts {

	public static void main(String[] args) {

		try {
			//Accept input 
			Scanner input = new Scanner(System.in);
			System.out.println("Enter a valid Feed ID : ");
			String feedId = input.nextLine();
			//validate if entered feedId is not empty
			if(feedId == null || feedId == ""){
				System.out.println("Feed ID cannot be Empty. Please Enter Valid Feed ID");
				System.exit(0);
			}
			
			System.out.println("Enter search term (eg:searchterm1 searchterm2): ");
			String searchParams = input.nextLine();
			input.close();
			
			String url = "http://app.compendium.com/api/publishers/" + feedId + "/feed";
			String responseString = "";
			
			FeedUtils utility = new FeedUtils();
			String urlString;
			try {
				if (searchParams != null && !searchParams.isEmpty()) {
					urlString = url + "?search_terms=" + searchParams;

				} else {
					urlString = url;
				}
				
				// convert all the spaces into %20. 
				urlString = urlString.replaceAll(" ", "%20");
				responseString = utility.getURlResponse(urlString);

			} catch (FileNotFoundException fe) {
				System.out.println("Given Feed ID is not valid. Please Enter Valid Feed ID");
				System.exit(0);
			}

			ArrayList<Feed> feedList = utility.parseFeedList(responseString);
			if (feedList.size() == 0) {
				System.out.println("There are no feeds available for given input");
				System.exit(0);
			}
			
			utility.sortFeedList(feedList);
			utility.printRecentPosts(feedList, feedId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
