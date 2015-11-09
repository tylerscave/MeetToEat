package com.example.lord_tyler.meettoeat;

import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lord_tyler.meettoeat.YelpClasses.RunMe;

/**
 * Created by lord_tyler on 11/3/15.
 */
public class SearchFragment extends Fragment {

    private TextView textout;
    private String yelpsearch2;

    public SearchFragment()
    {

        searchYelp();
        //Button button = (Button) v;
        //textout = (TextView) findViewById(R.id.result);
        //textout.setText(yelpsearch2);
        System.out.print(yelpsearch2);
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
       // LocationManager manager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
       // android.location.Location location =new android.location.Location(manager.NETWORK_PROVIDER);
        //double longitude = location.getLongitude();
        //double latitude = location.getLatitude();
        RunMe run = new RunMe();
        String searching;
        searching = run.start("Restaurant",37.3382,-121.8863);
        //searching = new RunMe().start("Restaurant", latitude,longitude);
        return searching;
    }
}
