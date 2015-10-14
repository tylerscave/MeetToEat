package com.example.lord_tyler.yelptest;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.lord_tyler.yelptest.MyLocation.LocationResult;


public class MainActivity extends AppCompatActivity {

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchYelp();
    }

    private TextView textout;
    private String yelpsearch2;

    public void buttonOnClick(View v)
    {
        searchYelp();
        Button button = (Button) v;
        textout = (TextView) findViewById(R.id.result);
        textout.setText(yelpsearch2);
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

        LocationResult locationResult = new LocationResult(){
            @Override
            public void gotLocation(Location location){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);

        RunMe run = new RunMe();
        String searching;
        searching = new RunMe().start("Restaurant", latitude,longitude);
        return searching;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}