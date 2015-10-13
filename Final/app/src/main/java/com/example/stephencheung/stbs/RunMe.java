package com.example.stephencheung.stbs;

import com.google.gson.Gson;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.Random;

public class RunMe {



	public String start(String category, Double lat, Double lng) {
		// Define your keys, tokens and secrets.  These are available from the Yelp website.
		API_Static_Stuff key = new API_Static_Stuff();
		String CONSUMER_KEY = key.getYelpConsumerKey();
		String CONSUMER_SECRET = key.getYelpConsumerSecret();
		String TOKEN = key.getYelpToken();
		String TOKEN_SECRET = key.getYelpTokenSecret();
		String result = "";
		
		// Some example values to pass into the Yelp search service.  
		//String lat = "30.361471";
		//String lng = "-87.164326";
		//String category = "KBBQ";
		//String location = "San Jose";
		
		// Execute a signed call to the Yelp service.  
		OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		Token accessToken = new Token(TOKEN, TOKEN_SECRET);
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter("term", category);
		//request.addQuerystringParameter("location", location);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawData = response.getBody();
		 
		// Sample of how to turn that text into Java objects.  
		try {
			YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
			
			System.out.println();

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
			System.out.println();

			
			
		} catch(Exception e) {
			System.out.println("Error, could not parse returned data!");
			System.out.println(rawData);			
		}
		return result;
	}

}
