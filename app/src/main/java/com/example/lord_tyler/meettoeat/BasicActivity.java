package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

/**
 *COPYRIGHT (C) 2015 Tyler Jones. All Rights Reserved.
 * The BasicActivity class is responsible for setting up basic skeleton of the app
 * and controlling tab behavior for the fragments
 * Solves CS151-05 Group Project MeetToEat
 * @author Tyler Jones
 * @version 1.01 12/08/2015
 */
public class BasicActivity extends AppCompatActivity {
    /**
     * onCreate creates the basic skeleton for the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        // Set custom logo for toolbar title
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Set up viewPager for fragment tab behavior
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * onCreateOptionsMenu sets up the menu
     * @param menu
     * @return true if executed
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    /**
     * onOptionsItemSelected handles menu selections
     * @param item
     * @return true when action performed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                //something
                return true;
            case R.id.logoff:
                ParseUser.logOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * setupViewPager sets up the fragment tabs
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new GroupFragment(), "Groups");
        adapter.addFragment(new SearchFragment(), "Find A Place");
        adapter.addFragment(new SearchResultFragment(), "Show Results");
        viewPager.setAdapter(adapter);
    }

    /**
     * Adaptor is an inner class that deals with the fragments
     */
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        /**
         * Constructor for the Adaptor class
         * @param fm
         */
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * addFragment adds the fragments and titles to appropriate arraylists
         * @param fragment to be added
         * @param title of the fragment added
         */
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        /**
         * getItem is the accessor for the fragments
         * @param position of fragment
         * @return the fragement
         */
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        /**
         * getCounts
         * @return the number of fragments
         */
        @Override
        public int getCount() {
            return mFragments.size();
        }

        /**
         * getPageTitle is the accessor for the tab title
         * @param position of the fragment title
         * @return title of fragment
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }


    }

}
