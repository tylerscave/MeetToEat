package com.example.lord_tyler.meettoeat;

import android.test.InstrumentationTestCase;

import com.example.lord_tyler.meettoeat.YelpClasses.RunMe;

/**
 * Created by stephencheung on 12/2/15
 */
public class SearchTest extends InstrumentationTestCase {

    public void test() throws Exception {

        RunMe run = new RunMe();
        Thread myThread = new Thread() {
            @Override
            public void run() {
                String searching = new RunMe().start("Restaurant", 36.666, -121.444);
                assertEquals("String", searching);
            }
        };
        myThread.start();
    }
}