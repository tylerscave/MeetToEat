package com.example.lord_tyler.meettoeat;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.parse.ParseACL;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

/**
 *COPYRIGHT (C) 2015 Tyler Jones. All Rights Reserved.
 * The ProfileActivity class shows the user profile. This simple activity can function regardless
 * of whether the user is currently logged in. This class also gets the user location so that it
 * happens only once at the beginning to avoid using too many resources
 * Solves CS151-05 Group Project MeetToEat
 * @author Tyler Jones
 * @version 1.01 12/08/2015
 */
public class ProfileActivity extends Activity {
    private static final int LOGIN_REQUEST = 0;
    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;
    private double latitude;
    private double longitude;
    private ParseUser currentUser;

    /**
     * onCreate is called when app first begins and sets up user profile and login info
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        titleTextView.setText(R.string.profile_title_logged_in);

        /**
         * anonymous inner class to listen for click on login or logout
         */
        loginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            ProfileActivity.this);
                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
                }
            }
        });
    }

    /**
     * onStart sets user login state on start
     */
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            showProfileLoggedIn();
        } else {
            showProfileLoggedOut();
        }
    }

    /**
     * showProfileLoggedIn sets up the user with parse as logged in. this method is also responsible
     * for getting the user location on start
     */
    private void showProfileLoggedIn() {

        ParseACL.setDefaultACL(new ParseACL(), true);
        Intent intent = new Intent(this, BasicActivity.class);
        startActivity(intent);
        // keep trying to get location until it has been accessed
        while (latitude == 0.0) {
            // Get the location of the parse user and send to database
            LocationActivity myLocation = new LocationActivity();
            LocationActivity.LocationResult locationResult = new LocationActivity.LocationResult() {
                @Override
                public void gotLocation(Location location) {
                    try{
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }catch (Exception e){
                        latitude = 37.00;
                        longitude = -121.00;
                    }

                }
            };
            myLocation.getLocation(this, locationResult);
            // add in a small sleep to minimize use of system resources
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // send the location to parse as a geopoint
        ParseGeoPoint geoPoint = new ParseGeoPoint(latitude,longitude);
        currentUser.put("userLocation", geoPoint);
        currentUser.saveInBackground();
    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        titleTextView.setText(R.string.profile_title_logged_out);
        emailTextView.setText("");
        nameTextView.setText("");
        loginOrLogoutButton.setText(R.string.profile_login_button_label);
    }
}