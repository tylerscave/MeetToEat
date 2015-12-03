package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lord_tyler.meettoeat.YelpClasses.RunMe;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * New fragment for searching for restaurant through yelps database.
 * Gathers the latitude and longitude of each group member to find a central geopoint
 * Randomizes a restaurant based on the area of the central geopint
 * Solves CS151-05 Group Project MeetToEat
 * Created by Tyler Jones
 * @author Stephen Cheung
 * @version 1.01 12/08/2015
 */
public class SearchFragment extends Fragment {

    private static String yelpsearch2; // Result of the yelp search
    private ParseGeoPoint geoPoint; //geopoint of the central location
    private ViewPager vp; //
    private static ParseObject group; //group selected
    private double latitude; //central latitude
    private double longitude; //central longitude

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        Button searchButton = (Button) view.findViewById(R.id.btn_search);
        vp = (ViewPager) getActivity().findViewById(R.id.viewpager);
        // Sets button to search when clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGeo(); // sets geoPoint
                searchYelp();
                boolean waiting = true;
                while (waiting) {
                    if (yelpsearch2 != null) {
                        // move to the searchResultFragment when the button is clicked
                        SearchResultFragment.setGroup(group);
                        SearchResultFragment.changeText(yelpsearch2);
                        group = null;
                        yelpsearch2 = null;
                        vp.setCurrentItem(2);
                        waiting = false;
                    }
                }
            }
        });
        return view;
    }

    /**
     * Creates a new thread to send query to yelp
     * Method programmed by Stephen Cheung
     */
    public void searchYelp()
    {
        Thread myThread = new Thread() {
            @Override
            public void run() {

                yelpsearch2 = getSearch();
            }
        };
        myThread.start();
    }

    /**
     * Sends a query to yelp for restaurant based on central latitude and longitude
     * @return restaurant name,address, and yelp link
     * Method programmed by Stephen Cheung
     */
    public String getSearch(){

        RunMe run = new RunMe();
        String searching = new RunMe().start("Restaurant", latitude, longitude);
        return searching;
    }

    /**
     * Sets the central latitude and longitude of the group, or individual
     * Method programmed by Stephen Cheung
     */
    public void getGeo()
    {
        double templat = 0;
        double templong = 0;
        ParseQuery user = ParseUser.getQuery();
        ParseUser currentUser = ParseUser.getCurrentUser();
        geoPoint = currentUser.getParseGeoPoint("userLocation");
        if (group != null)
        {
            List<String> groupmembers = group.getList("users"); //gets arrays of all group members

            for (String member: groupmembers) //loop through each group member
            {
                try {
                    ParseObject mUser = user.get(member); // a member of the group
                    templat += mUser.getParseGeoPoint("userLocation").getLatitude(); // latitude of group member
                    templong += mUser.getParseGeoPoint("userLocation").getLongitude(); // longitude of group member
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            latitude = templat / groupmembers.size(); //averages central latitude
            System.out.println("Final " + latitude);
            longitude = templong / groupmembers.size();
            System.out.println("Final " + longitude); //averages central longitude
        }
        else
        {
            latitude = geoPoint.getLatitude();
            longitude = geoPoint.getLongitude();
        }

    }

    /**
     * Sets the group selected
     * @param g the group to be selected
     * Method programmed by Stephen Cheung
     */
    public static void setGroup(ParseObject g)
    {
        group = g;
    }

}
