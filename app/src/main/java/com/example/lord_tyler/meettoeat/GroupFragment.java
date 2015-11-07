package com.example.lord_tyler.meettoeat;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
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
    public GroupFragment(ParseUser a){
        if(a != null){
            groups = a.getList("groups");
            if(groups != null){
                System.out.println(groups.get(0));//Test Line
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");//TODO Add check for if the ParseObject is null because it does not match
                query.getInBackground(groups.get(0), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e != null) {
                            System.out.println("Couldn't find the group");//TODO Need to add to UI
                        } else {
                            users = object.getList("users"); //TODO This would only work if part of one group, need to make it a list
                        }
                        Log.println(0,"test", "test group");
                        System.out.println(users);
                    }
                });//Depends on valid group, need to add failsafe as mentioned above if group doesn't exist on server
            }
            else System.out.println("No Valid Group");//TODO Add to UI
        }
        else System.out.println("No Valid User");//TODO Add to UI

    }


}
