package com.example.lord_tyler.meettoeat;

import android.support.v4.app.Fragment;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lord_tyler on 11/3/15.
 */
public class GroupFragment extends Fragment {
    List<String> group;
    public GroupFragment(ParseUser a){
        if(a != null){
            group = a.getList("groups");
            if(group != null){
                System.out.println(group.get(0));

            }
            else System.out.println("No Valid Group");
        }
        else System.out.println("No Valid User");

    }


}
