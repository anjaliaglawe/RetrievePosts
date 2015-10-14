package com.retrieve.feed;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;
import com.retrieve.feed.model.Asset;
import com.retrieve.feed.model.Feed;


public class RetrievePosts {
	
	public static int RECENT_POSTS_DISPLAY_COUNT = 9;

    public static void main(String[] args) {

		
        try{	
        	
        	Scanner input = new Scanner(System.in);
        	System.out.println("Enter a valid Feed ID : ");
            String feedId = input.nextLine();
            input.close();
            
            String url = "http://app.compendium.com/api/publishers/" + feedId + "/feed";
            String responseString = "";
            try{
            	responseString = getURlResponse(url);
            }catch(FileNotFoundException fe){
            	System.out.println("Given Feed ID is not valid. Please Enter Valid Feed ID");
            	System.exit(0);
            }
			ArrayList<Feed> feedList = parseFeedList(responseString);
			sortFeedList(feedList);
			printRecentPosts(feedList, feedId);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
    }
    private static String getURlResponse(String url) throws IOException{
    	StringBuffer response = new StringBuffer();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine = "";
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
    	
    }
    private static void printRecentPosts(ArrayList<Feed> feedList ,String feedId) throws ParseException, IOException{

    	//String printPattern = "%-35s%-16s%-13s%-100s\n";
    	//System.out.format(printPattern, "Title", "Author Name", "Publish Date", "Post Body");
		//System.out.format(printPattern, "-----", "-----------", "------------", "---------");
		int count =0;
		if(feedList.size() > RECENT_POSTS_DISPLAY_COUNT){
			count = RECENT_POSTS_DISPLAY_COUNT;
		}
		else{
			count = feedList.size();
		}
		for(int i=0 ; i< count ; i++){
			Feed f  = feedList.get(i);
			String pattern = "yyyy-MM-dd'T'HH:mm:ss";
			Date publishDate = new SimpleDateFormat(pattern).parse(f.getPublish_date());
			SimpleDateFormat displayFormat = new SimpleDateFormat("MM-dd-yyyy");
			String assetId = f.getAsset_url().substring(f.getAsset_url().lastIndexOf('/') + 1).trim();
			String asset = "";
			if(assetId !=null && assetId != ""){
		        String assetUrl = "http://app.compendium.com/api/publishers/" + feedId + "/feed/" + assetId;
		        String assetJson = getURlResponse(assetUrl);
		        if(assetJson!=null && assetJson!= ""){
		        	Asset assetObj = parseAssetJson(assetJson);
		        	if(assetObj!=null)
		        	{
		        		asset = assetObj.getBody();
		        	}
		        }
			}
			System.out.format("%s) %-17s%-35s\n", i+1, "Title:", f.getTitle());
			System.out.format("  %-18s%-35s\n"," Author:" , f.getAuthor().getName());
			System.out.format("  %-18s%-35s\n"," Publish Date:", displayFormat.format(publishDate));
			System.out.format("  %-18s%-35s\n"," Content Body:", StringEscapeUtils.escapeJava(asset));

		}	
    }
    
    private static Asset parseAssetJson(String jsonString){

		Gson gson = new Gson();
		Asset asset = gson.fromJson(jsonString, Asset.class);
		return asset;
    }
    
    private static ArrayList<Feed> parseFeedList(String jsonString){

		Gson gson = new Gson();
		Feed[] feedList = gson.fromJson(jsonString, Feed[].class);
		return new ArrayList<Feed>(Arrays.asList(feedList));
    }
    
    private static void sortFeedList(ArrayList<Feed> feedList){
    	Collections.sort(feedList, new Comparator<Feed>() {
		    public int compare(Feed lhs, Feed rhs) {
		    	if(lhs.getPublish_date()!=null && rhs.getPublish_date()!=null){
		    		try {
						String pattern = "yyyy-MM-dd'T'HH:mm:ss";
						Date lhsdate = new SimpleDateFormat(pattern)
						           .parse(lhs.getPublish_date());
						Date rhsdate = new SimpleDateFormat(pattern)
						           .parse(rhs.getPublish_date());
						 if(lhsdate.after(rhsdate))
						 { 
							 return -1;
					        }
					        else
					        {
					           return 1;
					        }
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
				
		    	return 0;
		       
		         
		    }
		});
    }
}
