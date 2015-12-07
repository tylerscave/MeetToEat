package com.example.lord_tyler.meettoeat;

import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.security.acl.Group;


/**
 * Created by shubaantaheri on 12/1/15.
 */
public class SearchResultFragmentTest extends TestCase {

    public void testOnClick() throws Exception {
        final int groupID = 1234;
        ParseObject group;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.getInBackground("hN3rYXPoZa", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    assertSame("Wendy's\n" +
                            "1940 S Ohio St\n" +
                            "Salina\n" +
                            "http://www.yelp.com/biz/wendys-salina-4\n" +
                            "\n", object.getString("YelpLocation"));

                }
                else
                    assertTrue(e==null);
            }});}}

