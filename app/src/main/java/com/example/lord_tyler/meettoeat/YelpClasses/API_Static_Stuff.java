package com.example.lord_tyler.meettoeat.YelpClasses;

/**
 * Sets the access keys for Yelp
 * Created by stephencheung on 10/7/15.
 * Programmed by Stephen Cheung
 */
public class API_Static_Stuff {
    private final String YELP_CONSUMER_KEY ="ShwF7ALLuZZvMhKMF0n_lw";
    private final String YELP_CONSUMER_SECRET = "gj16A--fs0B1CNN6NRJnZBTU0xA";
    private final String YELP_TOKEN = "49lI2VzMR_VTzo8rdQyfoGhkkS9lPTbs";
    private final String YELP_TOKEN_SECRET = "R9vYA3Ez88I4_83q0ja-l7xGEfs";


    public String getYelpConsumerKey(){
        return YELP_CONSUMER_KEY;
    }

    public String getYelpConsumerSecret(){
        return YELP_CONSUMER_SECRET;
    }

    public String getYelpToken(){
        return YELP_TOKEN;
    }

    public String getYelpTokenSecret(){
        return YELP_TOKEN_SECRET;
    }
}
