package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lord_tyler.meettoeat.YelpClasses.RunMe;
import com.parse.ParseGeoPoint;

/**
 * Created by lord_tyler on 11/3/15.
 */
public class SearchFragment extends Fragment {

    private static String yelpsearch2;
    private ParseGeoPoint geoPoint;
    private ViewPager vp;

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
                searchYelp();
                boolean waiting = true;
                while(waiting) {
                    if (yelpsearch2 != null) {
                        // move to the searchResultFragment when the button is clicked
                        SearchResultFragment.changeText(yelpsearch2);
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

        /*
        // This code is wrong and not working but it gives the general idea
        ParseQuery query = ParseUser.getQuery();
        query.getInBackground(String.valueOf(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    geoPoint = user.getParseGeoPoint("userLocation");
                } else
                    Log.d("userLocation", e.getMessage());
            }
        }));
        System.out.println("geoPoint latitude = " + geoPoint.getLatitude());
        */


        RunMe run = new RunMe();
        String searching;
        searching = run.start("Restaurant",37.3382,-121.8863);
        //searching = new RunMe().start("Restaurant", geoPoint.getLatitude(), geoPoint.getLongitude());
        return searching;
    }

}
