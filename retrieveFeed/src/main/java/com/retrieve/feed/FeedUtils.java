package com.retrieve.feed;

import java.io.BufferedReader;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;
import com.retrieve.feed.model.Asset;
import com.retrieve.feed.model.Feed;

public class FeedUtils {
	
	public static int RECENT_POSTS_DISPLAY_COUNT = 9;
	private static final Logger logger = Logger.getLogger(RetrievePosts.class.getName());

	/**
	 * This method gets a URL Response in the form of string for given URL
	 * @param url : input url
	 * @return : return reponse in the form of string
	 * @throws IOException : If the operation is not successful
	 */
	public String getURlResponse(String url) throws IOException {
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

	/**
	 * This method prints recent 9 posts on console
	 * @param feedList : list of Feed posts to be printed
	 * @param feedId : Feed Id to fetch the asset url in order to obtain post body
	 * @throws ParseException :  this exception occurs while parsing invalid date
	 * @throws IOException : connection issues when trying to get Url response.
	 */
	public void printRecentPosts(ArrayList<Feed> feedList, String feedId) throws ParseException, IOException {

		int count = 0;
		if (feedList.size() > RECENT_POSTS_DISPLAY_COUNT) {
			count = RECENT_POSTS_DISPLAY_COUNT;
		} else {
			count = feedList.size();
		}
		for (int i = 0; i < count; i++) {
			Feed f = feedList.get(i);
			String pattern = "yyyy-MM-dd'T'HH:mm:ss";
			Date publishDate = new SimpleDateFormat(pattern).parse(f.getPublish_date());
			SimpleDateFormat displayFormat = new SimpleDateFormat("MM-dd-yyyy");
			String assetId = f.getAsset_url().substring(f.getAsset_url().lastIndexOf('/') + 1).trim();
			String asset = "";
			if (assetId != null && assetId != "") {
				String assetUrl = "http://app.compendium.com/api/publishers/" + feedId + "/feed/" + assetId;
				String assetJson = getURlResponse(assetUrl);
				if (assetJson != null && assetJson != "") {
					Asset assetObj = parseAssetJson(assetJson);
					if (assetObj != null) {
						asset = assetObj.getBody();
					}
				}
			}
			System.out.format("%s) %-17s%-35s\n", i + 1, "Title:", f.getTitle());
			System.out.format("  %-18s%-35s\n", " Author:", f.getAuthor().getName());
			System.out.format("  %-18s%-35s\n", " Publish Date:", displayFormat.format(publishDate));
			System.out.format("  %-18s%-35s\n", " Content Body:", StringEscapeUtils.escapeJava(asset));

		}
	}

	/**
	 * this method will parse the json string for asset model object and convert it into Asset Object
	 * @param jsonString
	 * @return : Asset model mapped with values from input json String
	 */
	public Asset parseAssetJson(String jsonString) {
		Gson gson = new Gson();
		Asset asset = gson.fromJson(jsonString, Asset.class);
		return asset;
	}

	/**
	 * this method parses the jsonString into list of Feed objects
	 * @param jsonString
	 * @return : List of Feeds with values populated from input jsonString
	 */
	public ArrayList<Feed> parseFeedList(String jsonString) {
		Gson gson = new Gson();
		Feed[] feedList = gson.fromJson(jsonString, Feed[].class);
		return new ArrayList<Feed>(Arrays.asList(feedList));
	}

	/**
	 * this method sorts the given feedlist is descending order based on publish_date
	 * @param feedList : List of feeds from user
	 */
	public void sortFeedList(ArrayList<Feed> feedList) {
		Collections.sort(feedList, new Comparator<Feed>() {
			public int compare(Feed lhs, Feed rhs) {
				if (lhs.getPublish_date() != null && rhs.getPublish_date() != null) {
					try {
						String pattern = "yyyy-MM-dd'T'HH:mm:ss";
						Date lhsdate = new SimpleDateFormat(pattern).parse(lhs.getPublish_date());
						Date rhsdate = new SimpleDateFormat(pattern).parse(rhs.getPublish_date());
						if (lhsdate.after(rhsdate)) {
							return -1;
						} else {
							return 1;
						}
					} catch (ParseException e) {

						logger.log(Level.SEVERE, "Parse Exception for publish date format", e);
					}
				}

				return 0;

			}
		});
	}
}
