package com.example.lord_tyler.meettoeat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.tech.NfcBarcode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    String groupText;
    ViewPager vp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Yes");
            }
        });
        // Inflate the layout for this fragment
        if (currentUser != null) {
            //Adding listeners to each textview
            TextView testView = (TextView) view.findViewById(R.id.textView);
            vp = (ViewPager) getActivity().findViewById(R.id.viewpager); //test sc
            testView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Put the pass into group based on position in the arrayList
                    System.out.println("First Group Selected");
                    if (groups != null) {
                        System.out.println(groups.get(0));
                        try {
                            ParseQuery<ParseObject> getGroup = ParseQuery.getQuery("Group");
                            SearchFragment.setGroup(getGroup.get(groups.get(0)));
                            System.out.println("GF " + groups.get(0));

                        } catch (Exception e) {
                            System.out.println("CATCH BLOCK");
                        }
                    }
                    vp.setCurrentItem(1);
                }
            });
            view.findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Second Group Selected");
                    if (groups.size() >= 2) {
                        System.out.println(groups.get(1));
                        try {
                            ParseQuery<ParseObject> getGroup = ParseQuery.getQuery("Group");
                            SearchFragment.setGroup(getGroup.get(groups.get(1)));
                        } catch (Exception e) {

                        }
                    }
                }
            });
            view.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                System.out.println("Third Group Selected");
                    if(groups.size() >= 3){
                        System.out.println(groups.get(2));
                    }
                }
            });
            view.findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                System.out.println("Fourth Group Selected");
                    if(groups.size() >= 4){
                        System.out.println(groups.get(3));
                    }
                }
            });

            groupText = "";
            ParseQuery<ParseObject> query = null;
            groups = currentUser.getList("groups");
            users = new ArrayList<String>();
            if (groups != null) {
                for (String g : groups) {
                    System.out.println(g);//Test Line

                    query = ParseQuery.getQuery("Group");//TODO Add check for if the ParseObject is null because it does not match
                    try {
                        ParseObject obj = query.get(g);
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
                        groupText += obj.get("name");
                        groupText += ", ";
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                groupText = groupText.substring(0, groupText.length() - 2);
                System.out.println("GroupText " + groupText);
                testView.setText(groupText);
                groupText = "";
            } else System.out.println("No Valid Group");//TODO Add to UI
        } else System.out.println("No Valid User");//TODO Add to UI


        return view;
    }


}
