package com.example.lord_tyler.meettoeat;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lord_tyler on 11/3/15.
 * Fragment programmed by Benjamin Gottheil
 */
public class GroupFragment extends Fragment {
    List<String> groups;//contains ObjectIDs of all groups current user is part of
    List<String> users;//Contains Strings of all users in a given group
    List<String> textViewText;//Contains text for each of the textview boxes
    ParseUser currentUser = ParseUser.getCurrentUser();
    String groupText;
    ViewPager vp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        textViewText = new ArrayList<String>();
        //Creating add group button and its functionality
        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter UserName");
                final EditText inputText = new EditText(getContext());
                builder.setView(inputText);
                builder.setNegativeButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        String input = inputText.getText().toString();
                        if (input != null) {
                            System.out.println(input);//TODO COMMENT THIS TEST LINE OUT
                            ParseObject newGroup = new ParseObject("Group");
                            ParseUser matchedUser = null;
                            //Change the input of the email into the corresponding user ID
                            ParseQuery getUsers = ParseUser.getQuery();
                            getUsers = getUsers.whereMatches("username", input);
                            try {
                                matchedUser = (ParseUser) getUsers.getFirst();
                                matchedUser.add("groups", newGroup.getObjectId());
                                //matchedUser.save(); //TODO DON'T HAVE PERMISSIONS TO SAVE CHANGES TO USER, WOULD HAVE USE CLOUD FUNCTION AND MASTERKEY
                            } catch (ParseException e) {
                                System.out.println("Something wrong with user search");
                            }

                            newGroup.add("users", matchedUser.getObjectId());
                            try {
                                newGroup.save();
                            } catch (ParseException e) {
                                System.out.println("Couldn't Save");
                            }
                            ParseUser.getCurrentUser().add("groups", newGroup.getObjectId());
                            try {
                                ParseUser.getCurrentUser().save();
                            } catch (ParseException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        // Inflate the layout for this fragment
        if (currentUser != null) {
            //Adding listeners to each textview
            TextView testView = (TextView) view.findViewById(R.id.textView);
            vp = (ViewPager) getActivity().findViewById(R.id.viewpager); //test sc
            //Setting TextView as buttons
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
            ParseQuery query2 = ParseUser.getQuery();

            groups = currentUser.getList("groups");
            users = new ArrayList<String>();
            if (groups != null) {
                //g is a group ID
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
                                groupText += query2.get(s.toString()).get("name") + ", ";
                            }
                            textViewText.add(groupText.substring(0, groupText.length() - 2));
                            groupText = "";
                        }
                    } catch (Exception e) {
                        System.out.println("Something Went Wrong");
                    }
                }


                ;//Depends on valid group, need to add failsafe as mentioned above if group doesn't exist on server
                //Testing setting users to the group
                System.out.println("Out of loop" + users.get(0));
                query.cancel();

                //Cycle through users list. u is a user ID
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
                //Ensures no out of bounds is thrown when populating the text boxes. If less than 4 groups, the other boxes will be given blank text
                for(int x = textViewText.size(); x < 4; x++){
                    textViewText.add("");
                }

                //Setting all the textbox's text
                testView.setText(textViewText.get(0));
                testView = (TextView) view.findViewById(R.id.textView2);
                testView.setText(textViewText.get(1));
                testView = (TextView) view.findViewById(R.id.textView3);
                testView.setText(textViewText.get(2));
                testView = (TextView) view.findViewById(R.id.textView4);
                testView.setText(textViewText.get(3));

                groupText = "";
            } else System.out.println("No Valid Group");//TODO Add to UI
        } else System.out.println("No Valid User");//TODO Add to UI


        return view;
    }


}
