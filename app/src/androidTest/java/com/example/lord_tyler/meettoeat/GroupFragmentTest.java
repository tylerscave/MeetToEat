package com.example.lord_tyler.meettoeat;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import junit.framework.TestCase;

/**
 * Created by shubaantaheri on 12/1/15.
 */
public class GroupFragmentTest extends TestCase {
    public void test(){
        final String users[]= {"Pvl935T2zb","ITCmoYeP1H"};

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.getInBackground("hN3rYXPoZa", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) { //Successfully retrieve object
                    assertSame(users, object.getJSONArray("users"));

                }
                else
                    assertTrue(false);//test fails
            }});

}
}
