package com.example.lord_tyler.meettoeat;

import android.app.Application;
import com.parse.Parse;

/**
 *COPYRIGHT (C) 2015 Tyler Jones. All Rights Reserved.
 * The MainActivity class is the entry point for the MeetToEat app. This is where the
 * Parse SDK is initialized
 * Solves CS151-05 Group Project MeetToEat
 * @author Tyler Jones
 * @version 1.01 12/08/2015
 */
public class MainActivity extends Application {

    /**
     * onCreate is called when app opens
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Required - Initialize the Parse SDK
        Parse.initialize(this);
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
    }
}