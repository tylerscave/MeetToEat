package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by lord_tyler on 11/3/15.
 */
public class SearchResultFragment extends Fragment {
    private static TextView textView;
    private static Button sendResultButton;

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

    public static void changeText(String result) {
        textView.setText(result);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        sendResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo put the result onto parse for other members of the group to access
            }
        });

    }

    

}

