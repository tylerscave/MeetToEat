package com.example.lord_tyler.meettoeat.YelpClasses;

import com.google.gson.Gson;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.Random;

/**
 * Sends a request to Yelp for restaurants
 * Randomize restaurants for the user
 * Created by stephencheung on 10/7/15.
 * Programmed by Stephen Cheung
 */
public class RunMe {

	/**
	 * Sends a request to yelp and randomize a restaurant
	 * @param category type of search
	 * @param lat latitude to search
	 * @param lng longitude to search
	 * @return the randomized restaurant
	 */
	public String start(String category, Double lat, Double lng) {

		API_Static_Stuff key = new API_Static_Stuff();
		String CONSUMER_KEY = key.getYelpConsumerKey();
		String CONSUMER_SECRET = key.getYelpConsumerSecret();
		String TOKEN = key.getYelpToken();
		String TOKEN_SECRET = key.getYelpTokenSecret();
		String result = "";

		// Execute a call to the Yelp service.
		OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		Token accessToken = new Token(TOKEN, TOKEN_SECRET);
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter("term", category);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawData = response.getBody(); // Data retrieved from yelp to be parsed
		 
		try {
			YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
			
			System.out.println();

			// Randomize all the searched restaurants (first 20 restaurants)
			Random rand = new Random();
			int store = 0;
			if (places.getBusinesses().size() < 20)
			{
				store = rand.nextInt(places.getBusinesses().size());
			}
			else
			{
				store = rand.nextInt(20);
			}
			Business bus = places.getBusinesses().get(store);
			result = result + bus.getName() + "\n";
			for(String address : bus.getLocation().getAddress()) {
				result = result + address + "\n";
			}
			result = result + bus.getLocation().getCity() + "\n";
			result = result + bus.getUrl() + "\n";
			System.out.println(result);

			
			
		} catch(Exception e) {
			System.out.println("Error, could not parse returned data!");
			System.out.println(rawData);			
		}
		return result;
	}

}
