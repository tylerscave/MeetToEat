package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by lord_tyler on 11/3/15.
 */
public class SearchResultFragment extends Fragment {
    private static TextView textView;
    private static Button sendResultButton;
    private static ParseObject group;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_searchresult,
                container, false);
        // set up textview for yelp result
        textView = (TextView) view.findViewById(R.id.textViewResult);
        // set up button to send result to other users
        sendResultButton = (Button) view.findViewById(R.id.btn_send);

        return view;
    }

    public static void changeText(final String result) {
        textView.setText(result);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        sendResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
                query.getInBackground(group.getObjectId(), new GetCallback<ParseObject>() {

                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            group.put("YelpLocation",result);
                            group.saveInBackground();
                        } else {
                            // something went wrong
                        }
                    }
                });            }
        });

    }

    public static void setGroup(ParseObject g)
    {
        group = g;
    }

    

}

