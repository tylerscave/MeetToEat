package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 *COPYRIGHT (C) 2015 Tyler Jones and Shubaan Taheri. All Rights Reserved.
 * The SearchResultsFragment class is responsible for displaying search results and sending
 * those results to the rest of any group that has been selected
 * Solves CS151-05 Group Project MeetToEat
 * @author Tyler Jones and Shubaan Taheri
 * @version 1.01 12/08/2015
 */
public class SearchResultFragment extends Fragment implements View.OnClickListener {
    private static TextView textView;
    private static Button sendResultButton;
    private static ParseObject group;
    private static String theResult = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult,
                container, false);
        // set up textview for yelp result
        textView = (TextView) view.findViewById(R.id.textViewResult);
        // set up button to send result to other users
        sendResultButton = (Button) view.findViewById(R.id.btn_send);
        sendResultButton.setOnClickListener(this);
        return view;
    }

    /**
     * changeText is a mutator to set the correct textview based on the result found in
     * the SearchFragment
     * @param result the search result
     */
    public static void changeText(final String result) {
        theResult = result;
        textView.setText(result);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Send restaurant result to parse
     * @param v the view
     * Method Programmed By Shubaan Taheri
     */
    public void onClick(View v) {
        // get the current group from Parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.getInBackground(group.getObjectId(), new GetCallback<ParseObject>() {

            /**
             * done is what takes place when the query is returned
             * @param object
             * @param e the exception
             */
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    group.put("YelpLocation", theResult);
                    group.saveInBackground();
                    Toast.makeText(getActivity(), "Your search result was sent to your group",
                                Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Error: something went wrong with send to group button");
                }
            }
        });
    }

    /**
     * Sets group selected
     * @param g group selected
     * Method Programmed By Shubaan Taheri
     */
    public static void setGroup(ParseObject g) {
        group = g;
        if (group == null) {
            sendResultButton.setVisibility(View.INVISIBLE);
        }
    }
}

