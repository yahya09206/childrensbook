package com.example.yhussein.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.yhussein.myapplication.MainActivityTest.withIndex;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ReadActivityTest {
    private TextView tvtitle,tvdescription,tvcategory;
    private ImageView img;

    @Rule
    public ActivityTestRule<ReadActivity> activityTestRule
            = new ActivityTestRule<ReadActivity>(ReadActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent testIntent = new Intent();
            testIntent.putExtra("Id", 1);
            testIntent.putExtra("Title", "Great Book Title 1");
            testIntent.putExtra("Author", "Mekone Tolrom");
            testIntent.putExtra("Bookmark", 0);
            testIntent.putExtra("Language", "English");
            testIntent.putExtra("Sound", "On");
            return testIntent;
        }
    };

    @Test
    public void testReadLanding() {
        // Receive data
        /*Intent intent = getIntent();
        int Id = intent.getExtras().getInt("Id");
        String Title = intent.getExtras().getString("Title");
        String Author = intent.getExtras().getString("Author");
        int Bookmark = intent.getExtras().getInt("Bookmark");
        String language = intent.getExtras().getString("Language");
        String Sound = intent.getExtras().getString("Sound");
        int Section = Bookmark + 1;
        String Intro = "***** START READ OR LISTEN ******";

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);

        onView(withId(R.id.txttitle))
                .check(matches(withText("Great Book Title 1 by Mekone Tolrom")));
        onView(withId(R.id.txtCat))
                .check(matches(withText("[0/26]")));
        onView(withId(R.id.txtDesc))
                .check(matches(withText("***** START READ OR LISTEN ******")));
        onView(withIndex(withId(R.id.next_button), 0)).perform(click());
        onView(withIndex(withId(R.id.profile_button), 0)).perform(click());*/
    }
}
