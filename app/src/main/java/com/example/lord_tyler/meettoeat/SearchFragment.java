package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lord_tyler.meettoeat.YelpClasses.RunMe;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lord_tyler on 11/3/15.
 */
public class SearchFragment extends Fragment {

    private static String yelpsearch2;
    private ParseGeoPoint geoPoint;
    private ViewPager vp;
    private static ParseObject group; //Find way to pass value without using static
    private double latitude;
    private double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        Button searchButton = (Button) view.findViewById(R.id.btn_search);
        vp = (ViewPager) getActivity().findViewById(R.id.viewpager);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGeo(); // set geoPoint
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

    public String getSearch(){

        RunMe run = new RunMe();
        //String searching = run.start("Restaurant",37.3382,-121.8863);
        String searching = new RunMe().start("Restaurant", latitude, longitude);
        return searching;
    }

    public void getGeo()
    {
        double templat = 0;
        double templong = 0;
        ParseQuery user = ParseUser.getQuery();
        ParseUser currentUser = ParseUser.getCurrentUser();
        geoPoint = currentUser.getParseGeoPoint("userLocation");
        if (group != null)
        {
            List<String> groupmembers = group.getList("users");

            for (String member: groupmembers)
            {
                try {
                    ParseObject mUser = user.get(member);
                    templat += mUser.getParseGeoPoint("userLocation").getLatitude();
                    templong += mUser.getParseGeoPoint("userLocation").getLongitude();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            latitude = templat / groupmembers.size();
            System.out.println("Final " + latitude);
            longitude = templong / groupmembers.size();
            System.out.println("Final " + longitude);
        }
        else
        {
            latitude = geoPoint.getLatitude();
            longitude = geoPoint.getLongitude();
        }

    }

    public static void setGroup(ParseObject g)
    {
        group = g;
    }

}
