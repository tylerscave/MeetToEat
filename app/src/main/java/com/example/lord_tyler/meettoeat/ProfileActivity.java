package com.example.lord_tyler.meettoeat;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lord_tyler.meettoeat.LocationActivity.LocationResult;
import com.parse.ParseACL;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;


/**
 * Shows the user profile. This simple activity can function regardless of whether the user
 * is currently logged in.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        titleTextView.setText(R.string.profile_title_logged_in);

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

    private void showProfileLoggedIn() {

        ParseACL.setDefaultACL(new ParseACL(), true);
        Intent intent = new Intent(this, BasicActivity.class);
        startActivity(intent);
        while (latitude == 0.0) {
            // Get the location of the parse user and send to database
            LocationActivity myLocation = new LocationActivity();
            LocationResult locationResult = new LocationResult() {
                @Override
                public void gotLocation(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            };
            myLocation.getLocation(this, locationResult);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ParseGeoPoint geoPoint = new ParseGeoPoint(latitude,longitude);
        currentUser.put("userLocation", geoPoint);
        currentUser.saveInBackground();

        //test to see if location is captured delete later
        System.out.println("location = " + latitude + " " + longitude);

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