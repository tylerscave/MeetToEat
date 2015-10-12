package com.example.stephencheung.stbs;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
        LocationManager manager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        Location location =new Location(manager.NETWORK_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        RunMe run = new RunMe();
        String searching;
        //searching = run.start("Restaurant",37.3382,-121.8863);
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
