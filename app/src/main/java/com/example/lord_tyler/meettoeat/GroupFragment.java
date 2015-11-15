package com.example.lord_tyler.meettoeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lord_tyler on 11/3/15.
 * Proto - functions added by Benjamin
 */
public class GroupFragment extends Fragment {
    List<String> groups;//contains ObjectIDs of all groups current user is part of
    List<String> users;//Contains Strings of all users in a given group
    ParseUser currentUser = ParseUser.getCurrentUser();
    String group1Text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        // Inflate the layout for this fragment
        if (currentUser != null) {
            TextView testView = (TextView) view.findViewById(R.id.textView);
            group1Text = "";
            groups = currentUser.getList("groups");
            users = new ArrayList<String>();
            if (groups != null) {
                System.out.println(groups.get(0));//Test Line
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");//TODO Add check for if the ParseObject is null because it does not match
                try {
                    ParseObject obj = query.get(groups.get(0));
                    if (obj == null) System.out.println("Couldn't find the group");
                    else {
                        ArrayList tmp = (ArrayList) obj.getList("users"); //TODO This would only work if part of one group, need to make it a list
                        for (Object s : tmp) {
                            users.add(s.toString());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Something Went Wrong");
                }


                    /*@Override
                    public void done(ParseObject object, ParseException e) {
                        if (e != null) {
                            ;//TODO Need to add to UI
                        } else {
                            ArrayList tmp = (ArrayList) object.getList("users"); //TODO This would only work if part of one group, need to make it a list
                            for (Object s : tmp) {
                                users.add(s.toString());
                            }
                        }*/

                ;//Depends on valid group, need to add failsafe as mentioned above if group doesn't exist on server
                //Testing setting users to the group
                System.out.println("Out of loop" + users.get(0));
                query.cancel();
                ParseQuery query2 = ParseUser.getQuery();
                for (String u : users) {
                    try {
                        ParseObject obj = query2.get(u);
                        System.out.println("Name 1 " + obj.get("name"));
                        group1Text += obj.get("name");
                        group1Text += ", ";
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                group1Text = group1Text.substring(0, group1Text.length() - 2);
                System.out.println("GroupText " + group1Text);
                testView.setText(group1Text);
            } else System.out.println("No Valid Group");//TODO Add to UI
        } else System.out.println("No Valid User");//TODO Add to UI


        return view;
    }


}
