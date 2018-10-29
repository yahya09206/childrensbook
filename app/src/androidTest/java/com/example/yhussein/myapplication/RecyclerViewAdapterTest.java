package com.example.yhussein.myapplication;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.yhussein.myapplication.MainActivityTest.withIndex;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewAdapterTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNavigateToReading() {
        onView(withId(R.id.recyclerView_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.txttitle))
                .check(matches(withText("Great Book Title 1 by Mekone Tolrom")));
        onView(withId(R.id.txtCat))
                .check(matches(withText("[0/26]")));
        onView(withId(R.id.txtDesc))
                .check(matches(withText("***** START READ OR LISTEN ******")));
        onView(withIndex(withId(R.id.next_button), 0)).perform(click());
        //test paragraph is not null
        //test sound is playing
        onView(withIndex(withId(R.id.profile_button), 0)).perform(click());
    }
}
