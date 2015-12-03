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
 * Created by lord_tyler on 11/3/15.
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

    public static void changeText(final String result) {
        theResult = result;
        textView.setText(result);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Send restaurant result to parse
     * @param v
     * Method Programmed By Shubaan Taheri
     */
    public void onClick(View v) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.getInBackground(group.getObjectId(), new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    group.put("YelpLocation", theResult);
                    group.saveInBackground();
                    Toast.makeText(getActivity(), "Your search result was sent to your group",
                                Toast.LENGTH_SHORT).show();
                } else {
                        // something went wrong
                }
            }
        });
    }

    /**
     * Sets group selected
     * @param g group selected
     * Method Programmed By Shubaan Taheri
     */
    public static void setGroup(ParseObject g)
    {
        group = g;
        if (group == null)
        {
            sendResultButton.setVisibility(View.INVISIBLE);
        }
    }

    

}

